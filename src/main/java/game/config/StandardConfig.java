package game.config;

/**
 * Class to Store default properties and make them accessible
 *
 * @author tgutberl
 */
public class StandardConfig {

	/**
	 * 2D Field, used for storing standard/default values for the config file. Will be used if the
	 * Config File is not used or outdated
	 */
	public static final String[][] standardConfig = new String[][]{
			{"VERSION", "7"},
			{"SCREEN_WIDTH", "1280"},
			{"SCREEN_HEIGHT", "720"},
			{"SCREEN_MINIMUM_WIDTH", "1280"},
			{"SCREEN_MINIMUM_HEIGHT", "720"},
			{"THEME", "Standard"},
			{"MAXIMUM_FPS", "60"},
			{"SHOW_FPS", "true"},
	};

	private StandardConfig() {

	}

}
