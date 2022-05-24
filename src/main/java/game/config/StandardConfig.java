package game.config;

/**
 * Class to Store default properties and make them accessible.
 *
 * @author tgutberl
 */
public class StandardConfig {

  /**
   * 2D Field, used for storing standard/default values for the config file. Will be used if the
   * Config File is not used or outdated.
   */
  public static final String[][] standardConfig = new String[][]{
      {"VERSION", "15"},
      {"SCREEN_WIDTH", "1280"},
      {"SCREEN_HEIGHT", "720"},
      {"SCREEN_MINIMUM_WIDTH", "1280"},
      {"SCREEN_MINIMUM_HEIGHT", "720"},
      {"THEME", "DARK"},
      {"SHOW_FPS", "TRUE"},
      {"MAXIMUM_FPS", "25"},
      {"HOSTPLAYER", "HOST"},
      {"AIPLAYER1", "AlphaGo"},
      {"AIPLAYER2", "DeepMind"},
      {"AIPLAYER3", "Stockfish"},
      {"MUSIC", "ON"},
      {"TOOLTIPS", "ON"},
  };

  /**
   * Constructor.
   */
  private StandardConfig() {

  }

}
