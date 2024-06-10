package controller.mejora;

import atlantafx.base.layout.InputGroup;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import modelo.Data;

public class ControllerMenuSuperiorAdmin {

    @FXML
    public MFXTextField barraBusqueda;

    @FXML
    private MFXButton btnBuscar;

    @FXML
    private InputGroup inputGroup;

    @FXML
    private AnchorPane menuSuperior;

    @FXML
    private Label nombre;
    private Data data;

    @FXML
    void buscar(MouseEvent event) {

    }
    public void ponerNombre(){
        this.nombre.setText("Administrador");
    }

    public void recibirData(Data data){
        this.data = data;
        this.data.getListaControladores().setControllerMenuSuperiorAdmin(this);
        this.btnBuscar.setText("");
        Platform.runLater(() -> {
            this.menuSuperior.requestFocus();
        });
        this.ponerNombre();


    }

}

