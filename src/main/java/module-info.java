module bloks3 {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires java.sql;
  requires slf4j.api;
  requires java.ws.rs;
  requires org.eclipse.jetty.server;
  requires jersey.media.json.jackson;
  requires hk2.api;
  requires jersey.server;
  requires jersey.container.jetty.http;
  requires org.eclipse.jetty.servlet;
  requires org.eclipse.jetty.websocket.javax.websocket.server;
  requires javax.websocket.client.api;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires javax.websocket.api;

  opens game.controller to javafx.fxml;
  opens game.core to javafx.fxml;

  exports game.core;

}