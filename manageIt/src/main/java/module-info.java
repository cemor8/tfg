module com.example.manageit {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires MaterialFX;
    requires atlantafx.base;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;


    opens mainApp to javafx.fxml;
    exports mainApp;
    exports controller;
    exports modelo;
    opens controller to javafx.fxml;
}