package mainApp;

import controller.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelo.CambiarIdioma;
import modelo.ConexionBase;
import modelo.Data;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CambiarIdioma.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/login.fxml"),CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerLogin controllerLogin = fxmlLoader.getController();
        controllerLogin.recibirData(new Data());
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.getIcons().add(new Image("file:src/main/resources/images/menuLateral/logo.png"));
        stage.setScene(scene);
        ConexionBase.getDatabase();
        ConexionBase.recibirNotas();
        ConexionBase.recibirUsuarios();
        ConexionBase.recibirProyectos();
        ConexionBase.recibirTareas();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}