package game.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Handles Config Properties, saving/loading from config.properties file
 *
 * @author tgutberl
 */
public class Config {

  /**
   * Path of config.properties path
   */
  private static final String configPath = "./src/main/resources/config.properties";
  /**
   * Properties Object, which will be used locally in the Class
   */
  private static final Properties property = new Properties();

  /**
   * Make Config private
   */
  private Config() {

  }

  /**
   * Void Method that loads the value of the StandardConfig Java Class. Will mainly be used, if the
   * Config File does not exist
   */
  public static void loadStandardConfig() {
    for (int i = 0; i < StandardConfig.standardConfig.length; i++) {
      property.setProperty(StandardConfig.standardConfig[i][0],
          StandardConfig.standardConfig[i][1]);
    }
  }

  /**
   * Returns value of Config, as String
   *
   * @param name String for that the value should be returned of
   * @return value
   */
  public static String getStringValue(String name) {
    if (property.containsKey(name)) {
      return property.getProperty(name);
    } else {
      for (int i = 0; i < StandardConfig.standardConfig.length; ++i) {
        if (StandardConfig.standardConfig[i][0].equals(name)) {
          property.setProperty(name, StandardConfig.standardConfig[i][1]);
          return StandardConfig.standardConfig[i][1];
        }
      }
      System.err.println(
          "Property Value, you try to get, does not exist. Name of the Value is: " + name);
      throw new IllegalArgumentException();
    }
  }

  public static Integer getIntValue(String name) {
    return Integer.parseInt(getStringValue(name));
  }

  /**
   * Saves Property to the config.properties File
   */
  public static void saveProperty() {
    try {
      OutputStream outStream = new FileOutputStream(configPath);
      property.store(outStream, "File for Config values");
      outStream.close();
    } catch (IOException e) {
      System.err.println("Error in Saving Property File");
      e.printStackTrace();
    }
  }

  /**
   * Loads the Properties Values from config.properties value. If the File does not exist, the
   * dafault properties will be loaded
   *
   * @author tgutberl
   */
  public static void loadProperty() {
    File propertyFile = new File(configPath);
    if (propertyFile.exists()) {
      try {
        BufferedInputStream inputStream = new BufferedInputStream(
            new FileInputStream(propertyFile));
        property.load(inputStream);
        inputStream.close();
        if (property.getProperty("VERSION") == null || !property.getProperty("VERSION")
            .equals(StandardConfig.standardConfig[0][1])) {
          loadStandardConfig();
          saveProperty();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        Files.createFile(Paths.get(configPath));
      } catch (IOException e) {
        e.printStackTrace();
      }
      loadStandardConfig();
      saveProperty();
    }
  }

  /**
   * The Method is used to store Config Values
   *
   * @param key   Identifier
   * @param value String value to store
   */
  public static void set(String key, String value) {
    property.setProperty(key, value);
  }

  /**
   * The Method is used to store Config Values
   *
   * @param key   Identifier
   * @param value Int value to store
   */
  public static void set(String key, Integer value) {
    property.setProperty(key, String.valueOf(value));
  }

  /**
   * The Method is used to store Config Values
   *
   * @param key   Identifier
   * @param value Double value to store
   */
  public static void set(String key, Double value) {
    property.setProperty(key, String.valueOf(value));
  }

  /**
   * The Method is used to store Config Values
   *
   * @param key   Identifier
   * @param value Boolean value to store
   */
  public static void set(String key, Boolean value) {
    property.setProperty(key, String.valueOf(value));
  }
}
