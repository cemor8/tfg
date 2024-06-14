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

public class ControllerMenuLateralEmpresa {

    @FXML
    private HBox hboxCerrar;

    @FXML
    private HBox hboxConfiguracion;

    @FXML
    private HBox hboxPanel;

    @FXML
    private HBox hboxProyectos;

    @FXML
    private ImageView imagenAjustes;

    @FXML
    private ImageView imagenPanel;

    @FXML
    private ImageView imagenProyectos;

    @FXML
    private ImageView imagenSalir;
    private Data data;

    /**
     * Método que cierra sesion
     * @param event
     * @throws IOException
     */
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

    /**
     * Método que muestra la vista de la configuracion
     * @param event
     * @throws IOException
     */
    @FXML
    void mostrarConfig(MouseEvent event) throws IOException {
        this.reiniciarHbox();
        this.hboxConfiguracion.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.imagenAjustes.getStyleClass().add("configPresionado");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/configuracionEmpresa.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerConfiguracionEmpresa controllerConfiguracionEmpresa = fxmlLoader.getController();
        controllerConfiguracionEmpresa.recibirData(this.data);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);

    }

    /**
     * Método que muestra la vista de empresa
     * @param event
     * @throws IOException
     */
    @FXML
    void verEmpresa(MouseEvent event) throws IOException {
        this.reiniciarHbox();
        this.hboxPanel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.imagenPanel.getStyleClass().add("empresapresionado");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/vistaEmpresa.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerVistaEmpresa controllerVistaEmpresa = fxmlLoader.getController();
        controllerVistaEmpresa.recibirData(this.data,this.data.getEmpresaSeleccionada());
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que muestra el panel de administracion de usuarios
     * @param event
     * @throws IOException
     */
    @FXML
    void verUsuarios(MouseEvent event) throws IOException {
        this.reiniciarHbox();
        this.hboxProyectos.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.imagenProyectos.getStyleClass().add("usuariospresionado");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/tablaUsuariosEmpresa.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerTablaUsuarios controllerTablaUsuarios = fxmlLoader.getController();
        controllerTablaUsuarios.recibirData(this.data);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }
    /**
     * Método que se encarga de recibir la información
     * @param data
     */
    public void recibirData(Data data){
        this.data = data;
        this.data.getListaControladores().setControllerMenuLateralEmpresa(this);
    }

    /**
     * Método que se encarga de reiniciar el color de los hboxes
     * */
    public void reiniciarHbox(){
        this.hboxCerrar.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
        this.hboxConfiguracion.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
        this.hboxPanel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
        this.hboxProyectos.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);


        this.limpiarClasesImagenes();

        this.imagenPanel.getStyleClass().add("empresa");
        this.imagenSalir.getStyleClass().add("cerrar");
        this.imagenProyectos.getStyleClass().add("usuarios");
        this.imagenAjustes.getStyleClass().add("config");
    }

    /**
     * Método que inicia el panel principal como clickado
     */
    public void iniciarPanel(){
        this.hboxPanel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.imagenPanel.getStyleClass().add("empresapresionado");

    }

    /**
     * Método que limpia las clases de la imagenes
     */
    public void limpiarClasesImagenes(){
        this.imagenPanel.getStyleClass().clear();
        this.imagenSalir.getStyleClass().clear();
        this.imagenProyectos.getStyleClass().clear();
        this.imagenAjustes.getStyleClass().clear();
    }

}

