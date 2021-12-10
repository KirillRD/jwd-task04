package com.epam.library.dao.connection_pool;

import java.util.ResourceBundle;

public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();

    private final ResourceBundle bundle = ResourceBundle.getBundle(DBParameter.DB_PROPERTIES);

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
