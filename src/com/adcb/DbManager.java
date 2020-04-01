package com.adcb;


import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;

public class DbManager {
	 private static final Logger LOGGER = Logger.getLogger(DbManager.class);
	private static InitialContext context = getInitialContext();
	public static synchronized InitialContext getInitialContext() {
	    if (context == null)
	      try {
	        context = new InitialContext();
	      } catch (NamingException ex) {
	        LOGGER.error("ERROR : Error while building the Initial Context ", ex);
	      }  
	    return context;
	  }
	
	
	public static DataSource getDataSource(String dataSourceName) {
	    DataSource dataSourceObject = null;
	    String actualJndiName = dataSourceName;
	    LOGGER.debug(" Inside Kony Central Server Mode");
	    if (StringUtils.isNotBlank(dataSourceName)) {
	      if (!dataSourceName.startsWith("java:comp/env/") && dataSourceName.indexOf(':') == -1) {
	        dataSourceName = "java:comp/env/" + dataSourceName;
	        dataSourceObject = getDataSourceObject(dataSourceName);
	      } 
	      if (dataSourceObject == null) {
	        dataSourceObject = getDataSourceObject(actualJndiName);
	        if (dataSourceObject == null)
	        LOGGER.error("Unable to get data source for " + dataSourceName); 
	      } 
	    } 
	    return dataSourceObject;
	  }
	
	public static DataSource getDataSourceObject(String dataSourceName) {
	    DataSource dsObject = null;
	    try {
	      dsObject = (DataSource)getInitialContext().lookup(dataSourceName);
	      LOGGER.debug("Middleware DB Data Source obtained for " + dataSourceName);
	    } catch (NamingException e) {
	    	System.out.println(e);
	    }
	    return dsObject;
	  }
	  
	public static Connection getConnection(DataSource dataSource) throws SQLException {
	    Connection dbConnection = null;
	    LOGGER.trace(" Getting DB connection");
	    if (dataSource != null) {
	      LOGGER.trace(" Datasource obtained");
	      try {
	        dbConnection = dataSource.getConnection();
	      } catch (SQLException e) {
	        LOGGER.error("Error getting the connection Object for the provided data source", e);
	        throw e;
	      } 
	    } 
	    return dbConnection;
	  }
	  
}
