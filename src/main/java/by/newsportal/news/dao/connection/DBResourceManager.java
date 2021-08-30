package by.newsportal.news.dao.connection;

import java.util.ResourceBundle;

public class DBResourceManager {
	private final static DBResourceManager INSTANCE = new DBResourceManager();
	
	ResourceBundle bundle = ResourceBundle.getBundle("database-sittings.db");
	
	private DBResourceManager() {}
	
	public static DBResourceManager getInstance() {
		return INSTANCE;
	}
	
	public String getValue(String key) {
		return bundle.getString(key);		
	}
}
