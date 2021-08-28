package by.newsportal.news.dao.connection;

import java.util.ResourceBundle;

public class DBResurceManager {
	private final static DBResurceManager instance = new DBResurceManager();
	
	ResourceBundle bundle = ResourceBundle.getBundle("database-sittings.db");
	
	private DBResurceManager() {}
	
	public static DBResurceManager getInstance() {
		return instance;
	}
	
	public String getValue(String key) {
		return bundle.getString(key);		
	}
}
