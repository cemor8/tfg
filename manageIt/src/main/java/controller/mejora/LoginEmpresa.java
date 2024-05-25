package controller.mejora;

import controller.ControllerLogin;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.CambiarIdioma;
import modelo.Data;

import java.io.IOException;

public class LoginEmpresa {
    @FXML
    private MFXButton btnLogin;
    private Data data;

    @FXML
    private MFXButton btnLogin1;

    @FXML
    private MFXButton btnLogin11;

    @FXML
    private MFXPasswordField introducirContraseña;

    @FXML
    private MFXTextField introducirCorreo;

    @FXML
    void iniciarEmpresa(MouseEvent event) {
        /** Iniciar sesion como la empresa  */
    }

    @FXML
    void registrar(MouseEvent event) throws IOException {
        /*  Abrir formulario de registro */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/registro.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerRegistro controllerRegistro = fxmlLoader.getController();
        controllerRegistro.recibirData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("file:src/main/resources/images/menuLateral/logo.png"));
        stage.setTitle("Registro");
        stage.setScene(scene);
        stage.show();

        MFXButton button = (MFXButton) event.getSource();
        Stage stage1 =(Stage) button.getScene().getWindow();
        stage1.close();


    }

    @FXML
    void volverLogin(MouseEvent event) throws IOException {
        /*  volver login anterior */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/login.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerLogin controllerLogin = fxmlLoader.getController();
        controllerLogin.recibirData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.getIcons().add(new Image("file:src/main/resources/images/menuLateral/logo.png"));
        stage.setScene(scene);
        stage.show();

        MFXButton button = (MFXButton) event.getSource();
        Stage stage1 =(Stage) button.getScene().getWindow();
        stage1.close();


    }
    public void recibirData(Data data){
        this.data = data;
    }
}
