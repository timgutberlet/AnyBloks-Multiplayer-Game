package game.core;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

  private GameController controller;

  public static void main(String[] args) {

    launch();
  }

  @Override
  public void start(Stage stage) throws IOException {

    controller = new GameController(stage, this);
    stage.setTitle("Bloks3");

    stage.show();
  }
}