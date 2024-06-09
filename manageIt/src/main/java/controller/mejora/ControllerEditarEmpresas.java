package controller.mejora;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.CambiarIdioma;
import modelo.Data;
import modelo.Empresa;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerEditarEmpresas {

    @FXML
    private MFXButton btnAtras;

    @FXML
    private Label errorContraseña;

    @FXML
    private Label errorCorreo;

    @FXML
    private Label errorDepartamento;

    @FXML
    private Label errorDescrp;

    @FXML
    private Label errorNombre;

    @FXML
    private Label errorPuesto;

    @FXML
    private ImageView imagenUsuario;

    @FXML
    private MFXTextField introducirContraseña;

    @FXML
    private MFXTextField introducirCorreo;

    @FXML
    private MFXTextField introducirDepartamento;

    @FXML
    private TextArea introducirDescripcion;

    @FXML
    private MFXTextField introducirNombre;

    @FXML
    private MFXTextField introducirPuesto;

    @FXML
    private Label labelTextoCrearProyecto;
    private Data data;
    private Empresa empresa;
    Map<String, String> columnasExpresiones = new HashMap<String, String>() {
        {
            put("descripcion", "^.{15,100}$");
            put("nombre", "^.{5,25}$");
            put("correo", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
            put("departamento", "^.{5,20}$");
            put("contraseña", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        }

    };

    @FXML
    void crear(MouseEvent event) {

    }

    @FXML
    void imagen(MouseEvent event) {

    }

    @FXML
    void volver(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/panelAdmin.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerPanelAdmin controllerPanelAdmin = fxmlLoader.getController();
        controllerPanelAdmin.recibirData(this.data);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }
    public void recibirData(Data data, Empresa empresa){
        this.data = data;
        this.empresa = empresa;
    }

}

