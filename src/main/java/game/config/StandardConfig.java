package game.config;

/**
 * Class to Store default properties and make them accessible
 * @author tgutberl
 */
public class StandardConfig {

  /**
   * 2D Field, used for storing standard/default values for the config file. Will be used if the Config
   * File is not used or outdated
   */
  public static final String[][] standardConfig = new String[][]{
      {"VERSION", "4"},
      {"SCREEN_WIDTH", "800"},
      {"SCREEN_HEIGHT", "800"},
      {"SCREEN_MINIMUM_WIDTH", "640"},
      {"SCREEN_MINIMUM_HEIGHT", "400"},
      {"THEME", "Standard"},
  };

  private StandardConfig() {

  }

}
