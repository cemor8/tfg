package controller.mejora;

import controller.ControllerLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelo.CambiarIdioma;
import modelo.Data;

import java.io.IOException;

public class ControllerLateralAdmin {

    @FXML
    private HBox hboxCerrar;

    @FXML
    private HBox hboxConfiguracion;

    @FXML
    private HBox hboxPanel;

    @FXML
    private ImageView imagenAjustes;

    @FXML
    private ImageView imagenPanel;

    @FXML
    private ImageView imagenSalir;
    private Data data;

    @FXML
    void cerrar(MouseEvent event) throws IOException {
        this.data.setEmpresaSeleccionada(null);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/login.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerLogin controllerLogin = fxmlLoader.getController();
        controllerLogin.recibirData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/main/resources/images/menuLateral/logo.png"));
        stage.show();
        HBox hBox = (HBox) event.getSource();
        Stage stage1 = (Stage) hBox.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void verEmpresas(MouseEvent event) {

    }
    public void recibirData(Data data){
        this.data = data;
    }

}

