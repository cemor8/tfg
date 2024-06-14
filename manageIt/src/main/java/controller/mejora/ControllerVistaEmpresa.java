package controller.mejora;

import controller.ControllerVistaCadaContacto;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import modelo.CambiarIdioma;
import modelo.Data;
import modelo.Empresa;
import modelo.Usuario;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerVistaEmpresa {

    @FXML
    private ImageView imgFondo;
    @FXML
    private MFXButton btn;

    @FXML
    private ImageView imgGuardarDesc;

    @FXML
    private ImageView imgPerfil;

    @FXML
    private TextArea labelDescripcion;

    @FXML
    private Label nombreEmpresa;

    @FXML
    private Label sectorEmpresa;
    private Data data;
    private Empresa empresa;
    private ArrayList<Usuario> empleados = new ArrayList<>();

    /**
     * Método que abre la vista de los empleados de la empresa
     * @param event
     * @throws IOException
     */
    @FXML
    void verEmpleados(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/empleados.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerEmpleados controllerEmpleados = fxmlLoader.getController();
        controllerEmpleados.recibirData(this.data,this.empleados);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que recibe informacion
     * @param data
     * @param empresa
     */
    public void recibirData(Data data, Empresa empresa){
        this.data = data;
        this.empresa = empresa;
        if (this.data.getEmpresaSeleccionada() != null){
            this.btn.setVisible(false);
        }
        for (Usuario usuario : this.data.getUsuarios()){
            if (usuario.getIdEmpresa() == this.empresa.getId() && (this.data.getCurrentUser() == null ||  usuario.getId() != this.data.getCurrentUser().getId())){
                this.empleados.add(usuario);
            }
        }
        this.cargarDatos();
    }

    /**
     * Método que carga los datos
     */
    public void cargarDatos(){
        this.imgFondo.setImage(this.empresa.getImagenFondo());
        this.imgFondo.setPreserveRatio(false);
        this.imgPerfil.setImage(this.empresa.getImagenPerfil());
        this.labelDescripcion.setText(this.empresa.getDescripcion());
        this.labelDescripcion.setEditable(false);
        this.nombreEmpresa.setText(this.empresa.getNombre());
        this.sectorEmpresa.setText(this.empresa.getSector());
        this.imgPerfil.setFitWidth(100);
        this.imgPerfil.setFitHeight(100);
        this.imgPerfil.setPreserveRatio(false);
        Circle clip = new Circle(this.imgPerfil.getFitWidth() / 2, this.imgPerfil.getFitHeight() / 2, this.imgPerfil.getFitWidth() / 2);
        this.imgPerfil.setClip(clip);
    }

}

