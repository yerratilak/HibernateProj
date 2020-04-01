package com.adcb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author imssbora
 */
public class JPAUtil {
  private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";
  private static EntityManagerFactory factory;

  public static EntityManagerFactory getEntityManagerFactory() throws FileNotFoundException, IOException {
    if (factory == null) {
    	Properties props = new Properties();
    	props.load(new FileInputStream("D:/kony/persistence.properties"));
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, props);
    }
    return factory;
  }

  public static void shutdown() {
    if (factory != null) {
      factory.close();
    }
  }
}
