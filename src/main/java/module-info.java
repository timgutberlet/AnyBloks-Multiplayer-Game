module bloks3 {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires java.sql;

  opens game.controller to javafx.fxml;
  opens game.core to javafx.fxml;

  exports game.core;

}