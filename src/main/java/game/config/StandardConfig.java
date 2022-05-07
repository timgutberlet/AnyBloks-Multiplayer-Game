package game.config;

/**
 * Class to Store default properties and make them accessible
 * @author timgutberlet
 */
public class StandardConfig {

  /**
   * 2D Field, used for storing standard/default values for the config file. Will be used if the Config
   * File is not used or outdated
   */
  public static final String[][] standardConfig = new String[][]{
      {"VERSION", "3"},
      {"SCREEN_WIDTH", "1280"},
      {"SCREEN_HEIGHT", "720"},
      {"SCREEN_MINIMUM_WIDTH", "1920"},
      {"SCREEN_MINIMUM_HEIGHT", "720"},
      {"THEME", "Standard"},
  };

  private StandardConfig() {

  }

}