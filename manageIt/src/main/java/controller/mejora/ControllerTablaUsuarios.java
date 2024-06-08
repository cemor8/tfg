package controller.mejora;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Data;
import modelo.Usuario;

import java.awt.image.AreaAveragingScaleFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTablaUsuarios implements Initializable {

    @FXML
    private MFXButton btnMeter;

    @FXML
    private TableView<Usuario> tabla;
    private Data data;
    private ObservableList<Usuario> usuariosObservable= FXCollections.observableArrayList();



    @FXML
    void crear(MouseEvent event) {

    }

    @FXML
    void eliminar(MouseEvent event) {

    }

    @FXML
    void modificar(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Usuario,String> columnaNombre = new TableColumn<>("Nombre");
        this.tabla.getColumns().add(columnaNombre);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Usuario,String> columnaApellidos = new TableColumn<>("Apellidos");
        this.tabla.getColumns().add(columnaApellidos);
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

        TableColumn<Usuario,String> columnaCorreo = new TableColumn<>("Correo");
        this.tabla.getColumns().add(columnaCorreo);
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        TableColumn<Usuario,String> columnaPuesto = new TableColumn<>("Puesto");
        this.tabla.getColumns().add(columnaPuesto);
        columnaPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));

        TableColumn<Usuario,String> columnaDepartamento = new TableColumn<>("Departamento");
        this.tabla.getColumns().add(columnaDepartamento);
        columnaDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));

    }
    public void recibirData(Data data){
        this.data = data;
        this.btnMeter.setText("");
        for (Usuario usuario : this.data.getUsuarios()){
            if (usuario.getIdEmpresa() == this.data.getEmpresaSeleccionada().getId()){
                this.usuariosObservable.add(usuario);
            }
        }
        this.tabla.setItems(this.usuariosObservable);

    }
}

