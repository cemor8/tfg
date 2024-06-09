package controller.mejora;

import atlantafx.base.layout.InputGroup;
import controller.ControllerProyectos;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import modelo.CambiarIdioma;
import modelo.Data;
import modelo.Proyecto;
import modelo.Usuario;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerMenuSuperiorEmpresa {

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

    @FXML
    private ImageView perfil;
    private Data data;

    @FXML
    void buscar(MouseEvent event) throws IOException {

    }

    @FXML
    void verPerfil(MouseEvent event) {
        this.data.getListaControladores().getControllerMenuLateralEmpresa().mostrarConfig(null);
    }
    public void ponerNombre(){
        this.nombre.setText(this.data.getEmpresaSeleccionada().getNombre());
    }
    public void ponerImagen(){
        this.perfil.setImage(this.data.getEmpresaSeleccionada().getImagenPerfil());

        this.perfil.setFitWidth(60);
        this.perfil.setFitHeight(60);
        this.perfil.setPreserveRatio(false);
        Circle clip = new Circle(this.perfil.getFitWidth() / 2, this.perfil.getFitHeight() / 2, this.perfil.getFitWidth() / 2);
        this.perfil.setClip(clip);
    }
    public void recibirData(Data data){
        this.data = data;
        this.data.getListaControladores().setControllerMenuSuperiorEmpresa(this);
        this.btnBuscar.setText("");
        this.ponerImagen();
        Platform.runLater(() -> {
            this.menuSuperior.requestFocus();
        });
        this.ponerNombre();


    }

}

