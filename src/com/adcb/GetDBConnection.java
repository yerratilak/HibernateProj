package com.adcb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;



@WebServlet("/GetDBConnection")
public class GetDBConnection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String CONFIG_PROP_NAME = "prop_name";
	  
	  public static String CONFIG_PROP_VALUE = "prop_value";
	  
	  public static String CONFIG_PROP_TYPE = "prop_type";
	 private static final Logger logger = Logger.getLogger(GetDBConnection.class);
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * DbManager dbManager=new DbManager(); DataSource
		 * ds=dbManager.getDataSource("jdbc/konyadmindb");
		 * 
		 * Map<String, Object> propMap = new HashMap<String, Object>(); Connection
		 * connection = null; ResultSet rs = null; PreparedStatement statement = null;
		 * try { connection = dbManager.getConnection(ds); if (connection != null) {
		 * StringBuilder sb = new StringBuilder();
		 * sb.append("select * from ").append("server_configuration"); statement =
		 * connection.prepareStatement(sb.toString());
		 * 
		 * rs = statement.executeQuery(); while (rs.next()) { String configPropName =
		 * rs.getString(CONFIG_PROP_NAME); String configPropValue =
		 * rs.getString(CONFIG_PROP_VALUE); String configPropType =
		 * rs.getString(CONFIG_PROP_TYPE); logger.debug("");
		 * System.out.println("configPropName->"+configPropName+": configPropValue"
		 * +configPropValue); } } } catch (SQLException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
	      
		 EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		    entityManager.getTransaction().begin();

		    // Check database version
		    String sql = "select version()";

		    String result = (String) entityManager.createNativeQuery(sql).getSingleResult();
		    System.out.println(result);

		    entityManager.getTransaction().commit();
		    entityManager.close();

		    JPAUtil.shutdown();

		
	}

}
