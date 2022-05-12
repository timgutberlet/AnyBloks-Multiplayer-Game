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
    requires org.eclipse.jetty.util;

    opens game.controller to javafx.fxml;
    opens game.core to javafx.fxml;
    opens net.packet.abstr;
    opens game.model;
    opens game.model.board;
    opens game.model.field;
    opens game.model.player;
    opens game.model.chat;
    opens game.model.gamemodes;


    exports game.core;
    exports net.transmission;
    exports net.packet.abstr;
    exports game.model;
    exports game.model.board;
    exports game.model.field;
    exports game.model.player;
    exports game.model.chat;
    exports game.model.gamemodes;
	  exports net.packet.chat;
	  opens net.packet.chat;
    exports net.packet.account;
    opens net.packet.account;
    opens net.server;
    exports net.server;
    opens net.transmission;
    //exports net.packet.abstr;
    //opens net.packet.abstr;
    exports net.packet.game;
    opens net.packet.game;
	exports net.tests;
	exports net.tests.chat;

}
