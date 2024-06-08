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
import modelo.Usuario;

import java.io.IOException;

public class ControllerModificarUsuario {

    @FXML
    private Label errorCliente;
    @FXML
    private MFXButton btnAtras;

    @FXML
    private Label errorDescrip;

    @FXML
    private Label errorFecha;

    @FXML
    private Label errorImagen;

    @FXML
    private Label errorNombre;

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
    private Label labelTextoCrearProyecto;
    private Data data;
    private Usuario usuario;

    @FXML
    void crear(MouseEvent event) {
        /*comprobar datos*/
        if (this.usuario == null){
            /* creare usuario*/
        }else {
            /*modificar usuario*/
        }
    }

    @FXML
    void imagen(MouseEvent event) {

    }
    @FXML
    void volver(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/tablaUsuariosEmpresa.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerTablaUsuarios controllerTablaUsuarios = fxmlLoader.getController();
        controllerTablaUsuarios.recibirData(this.data);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }
    public void recibirData(Data data, Usuario usuario){
        this.data = data;
        this.btnAtras.setText("");
        this.usuario = usuario;
        if (this.usuario == null){
            this.introducirCorreo.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirContraseña.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirDepartamento.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirDescripcion.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirNombre.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
        }else {
            this.introducirCorreo.setPromptText(this.usuario.getCorreo());
            this.introducirContraseña.setPromptText(this.usuario.getContraseña());
            this.introducirDepartamento.setPromptText(this.usuario.getDepartamento());
            this.introducirDescripcion.setPromptText(this.usuario.getDescripcion());
            this.introducirNombre.setPromptText(this.usuario.getNombre());
            this.imagenUsuario.setImage(this.usuario.getImagen());
        }
    }

}

