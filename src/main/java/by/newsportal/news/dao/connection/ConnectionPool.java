package by.newsportal.news.dao.connection;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public final class ConnectionPool {
    private static final ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return instance;
    }

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> giveAwayConQueue;

    private final String DRIVER_NAME;
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private int poolSize;

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.DRIVER_NAME = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.URL = dbResourceManager.getValue(DBParameter.DB_URL);
        this.USER = dbResourceManager.getValue(DBParameter.DB_USER);
        this.PASSWORD = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = 5;
        }
        try {
            initPoolData();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("Error : initPoolData", e);
        }
    }

    public void initPoolData() throws ConnectionPoolException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName(DRIVER_NAME);
            giveAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't find database driver class", e);

        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool", e);
        }

    }

    public void dispose() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionQueue(giveAwayConQueue);
            closeConnectionQueue(connectionQueue);
        } catch (SQLException e) {
            // TODO: handle exception
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connectionQueue.take();
            giveAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connection to the data source.", e);
        }
        return connection;
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs) {
        try {
            con.close();
        } catch (SQLException e) {
            // TODO: handle exception
        }
        try {
            rs.close();
        } catch (SQLException e) {
            // TODO: handle exception
        }
        try {
            st.close();
        } catch (SQLException e) {
            // TODO: handle exception
        }
    }

    public void closeConnection(Connection con, Statement st) {
        try {
            con.close();
        } catch (SQLException e) {
            // TODO: handle exception
        }
        try {
            st.close();
        } catch (SQLException e) {
            // TODO: handle exception
        }
    }

    public void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    private class PooledConnection implements Connection {
        private Connection connection;

        public PooledConnection(Connection c) throws SQLException {
            this.connection = c;
            this.connection.setAutoCommit(true);
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();

        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempting to close closed connection.");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!giveAwayConQueue.remove(this)) {
                throw new SQLException("Error deleting connection from the given "
                        + "away connection pool.");
            }
            if (!connectionQueue.offer(this)) {
                throw new SQLException(" Error allcating connection in the pool.");
            }
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
            // TODO Auto-generated method stub
        }


        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            // TODO Auto-generated method stub
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            // TODO Auto-generated method stub
            return connection.isWrapperFor(iface);
        }

        @Override
        public Statement createStatement() throws SQLException {
            // TODO Auto-generated method stub
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            // TODO Auto-generated method stub
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            // TODO Auto-generated method stub
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            // TODO Auto-generated method stub
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            // TODO Auto-generated method stub
            connection.setAutoCommit(autoCommit);

        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getAutoCommit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();// TODO Auto-generated method stub

        }

        @Override
        public boolean isClosed() throws SQLException {
            // TODO Auto-generated method stub
            return connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            // TODO Auto-generated method stub
            connection.setReadOnly(readOnly);

        }

        @Override
        public boolean isReadOnly() throws SQLException {
            // TODO Auto-generated method stub
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            // TODO Auto-generated method stub
            connection.setCatalog(catalog);

        }

        @Override
        public String getCatalog() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            // TODO Auto-generated method stub
            connection.setTransactionIsolation(level);

        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getWarnings();
        }


        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            // TODO Auto-generated method stub
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {
            // TODO Auto-generated method stub
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {
            // TODO Auto-generated method stub
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            // TODO Auto-generated method stub
            connection.setTypeMap(map);

        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            // TODO Auto-generated method stub
            connection.setHoldability(holdability);

        }

        @Override
        public int getHoldability() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            // TODO Auto-generated method stub
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            // TODO Auto-generated method stub
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            // TODO Auto-generated method stub
            connection.rollback(savepoint);

        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            // TODO Auto-generated method stub
            connection.releaseSavepoint(savepoint);

        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            // TODO Auto-generated method stub
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                                  int resultSetHoldability) throws SQLException {
            // TODO Auto-generated method stub
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                             int resultSetHoldability) throws SQLException {
            // TODO Auto-generated method stub
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            // TODO Auto-generated method stub
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            // TODO Auto-generated method stub
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            // TODO Auto-generated method stub
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            // TODO Auto-generated method stub
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            // TODO Auto-generated method stub
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            // TODO Auto-generated method stub
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            // TODO Auto-generated method stub
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            // TODO Auto-generated method stub
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            // TODO Auto-generated method stub
            connection.setClientInfo(name, value);

        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            // TODO Auto-generated method stub
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            // TODO Auto-generated method stub
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            // TODO Auto-generated method stub
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            // TODO Auto-generated method stub
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            // TODO Auto-generated method stub
            connection.setSchema(schema);

        }

        @Override
        public String getSchema() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            // TODO Auto-generated method stub
            connection.abort(executor);

        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            // TODO Auto-generated method stub
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            // TODO Auto-generated method stub
            return connection.getNetworkTimeout();
        }
    }
}