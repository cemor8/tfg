package controller.mejora;

import controller.ControllerLogin;
import javafx.css.PseudoClass;
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
        this.data.setOscuro(true);
        CambiarIdioma.getInstance().cargarIdioma("es", "ES");
        this.data.setEspañol(true);
        this.data = new Data();
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
    void verEmpresas(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/panelAdmin.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerPanelAdmin controllerPanelAdmin = fxmlLoader.getController();
        controllerPanelAdmin.recibirData(this.data);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);

    }
    /**
     * Método que inicia el panel principal como clickado
     */
    public void iniciarPanel(){
        this.hboxPanel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.imagenPanel.getStyleClass().add("empresapresionado");

    }

    public void recibirData(Data data){
        this.data = data;
        this.data.getListaControladores().setControllerLateralAdmin(this);
    }

}

