package controller.mejora;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import modelo.Data;
import modelo.Empresa;

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

    @FXML
    void guardarDesc(MouseEvent event) {

    }

    @FXML
    void verTareas(MouseEvent event) {

    }
    public void recibirData(Data data, Empresa empresa){
        this.data = data;
        this.empresa = empresa;
        if (this.data.getEmpresaSeleccionada() == null){
            this.btn.setVisible(false);
        }
        this.cargarDatos();
    }
    public void cargarDatos(){
        this.imgFondo.setImage(this.empresa.getImagenFondo());
        this.imgFondo.setPreserveRatio(false);
        this.imgPerfil.setImage(this.empresa.getImagenPerfil());
        this.labelDescripcion.setText(this.empresa.getDescripcion());
        this.labelDescripcion.setEditable(false);
        this.nombreEmpresa.setText(this.empresa.getNombre());
        this.sectorEmpresa.setText(this.empresa.getSector());
        this.imgPerfil.setFitWidth(60);
        this.imgPerfil.setFitHeight(60);
        this.imgPerfil.setPreserveRatio(false);
        Circle clip = new Circle(this.imgPerfil.getFitWidth() / 2, this.imgPerfil.getFitHeight() / 2, this.imgPerfil.getFitWidth() / 2);
        this.imgPerfil.setClip(clip);
    }

}

