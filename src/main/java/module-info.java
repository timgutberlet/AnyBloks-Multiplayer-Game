module bloks3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires org.eclipse.jetty.server;
    requires jersey.media.json.jackson;
    requires jersey.server;
    requires jersey.container.jetty.http;
    requires org.eclipse.jetty.servlet;
    requires org.eclipse.jetty.websocket.javax.websocket.server;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires jersey.common;
    requires org.eclipse.jetty.websocket.javax.websocket;
    requires java.ws.rs;
    requires jersey.hk2;
    requires hk2.api;
    requires org.slf4j;
    requires javax.websocket.all;

    opens game.controller to javafx.fxml;
    opens game.core to javafx.fxml;
    opens net.packet;

    exports game.core;
    exports net.transmission;
    exports net.packet;

    }
