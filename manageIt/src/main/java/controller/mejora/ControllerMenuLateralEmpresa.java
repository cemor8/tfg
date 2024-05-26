package controller.mejora;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import modelo.Data;

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

    @FXML
    void cerrar(MouseEvent event) {

    }

    @FXML
    void mostrarConfig(MouseEvent event) {

    }

    @FXML
    void verEmpresa(MouseEvent event) {

    }

    @FXML
    void verUsuarios(MouseEvent event) {

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

        this.imagenPanel.getStyleClass().add("panel");
        this.imagenSalir.getStyleClass().add("cerrar");
        this.imagenProyectos.getStyleClass().add("proyectos");
        this.imagenAjustes.getStyleClass().add("config");
    }

    /**
     * Método que inicia el panel principal como clickado
     */
    public void iniciarPanel(){
        this.hboxPanel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.imagenPanel.getStyleClass().add("panelPresionado");

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

