package game.core;

import game.config.Config;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private GameController controller;

	public static void main(String[] args) {
		Config.loadProperty();
		launch();
	}

	@Override
	public void start(Stage stage) {
		controller = new GameController(stage, this);
		stage.setTitle("Bloks3");
		stage.show();
	}

	/**
	 * Method used to stop the Application
	 *
	 * @author tgutberl
	 */
	@Override
	public void stop() {
		Config.saveProperty();
		controller.close();
		try {
			super.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}