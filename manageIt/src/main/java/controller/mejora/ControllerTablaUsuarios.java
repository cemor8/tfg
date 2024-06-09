package controller.mejora;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modelo.CambiarIdioma;
import modelo.ConexionBase;
import modelo.Data;
import modelo.Usuario;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
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
    void crear(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/vistaModificacionUsuario.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerModificarUsuario controllerModificarUsuario = fxmlLoader.getController();
        controllerModificarUsuario.recibirData(this.data,null);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    @FXML
    void eliminar(MouseEvent event) {
        Usuario usuario = this.tabla.getSelectionModel().getSelectedItem();
        if (usuario == null){
            return;
        }
        this.data.getUsuarios().remove(usuario);
        this.usuariosObservable.remove(usuario);
        this.tabla.refresh();
        ConexionBase.eliminarUsuario(usuario.getId());

    }

    @FXML
    void modificar(MouseEvent event) throws IOException {
        Usuario usuario = this.tabla.getSelectionModel().getSelectedItem();
        if (usuario == null){
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/vistaModificacionUsuario.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerModificarUsuario controllerModificarUsuario = fxmlLoader.getController();
        controllerModificarUsuario.recibirData(this.data,usuario);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Usuario,String> columnaNombre = new TableColumn<>(CambiarIdioma.getInstance().getBundle().getString("tablaU.nombre"));
        this.tabla.getColumns().add(columnaNombre);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Usuario,String> columnaApellidos = new TableColumn<>(CambiarIdioma.getInstance().getBundle().getString("tablaU.apellidos"));
        this.tabla.getColumns().add(columnaApellidos);
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

        TableColumn<Usuario,String> columnaCorreo = new TableColumn<>(CambiarIdioma.getInstance().getBundle().getString("tablaU.correo"));
        this.tabla.getColumns().add(columnaCorreo);
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        TableColumn<Usuario,String> columnaPuesto = new TableColumn<>(CambiarIdioma.getInstance().getBundle().getString("tablaU.puesto"));
        this.tabla.getColumns().add(columnaPuesto);
        columnaPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));

        TableColumn<Usuario,String> columnaDepartamento = new TableColumn<>(CambiarIdioma.getInstance().getBundle().getString("tablaU.departamento"));
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

        this.data.getListaControladores().getControllerMenuSuperiorEmpresa().barraBusqueda.textProperty().addListener((observable, textoPrevio, textoIntroducido) -> {
            System.out.println(textoIntroducido);
            if (textoIntroducido.isEmpty()) {
                this.usuariosObservable = FXCollections.observableArrayList();
                for (Usuario usuario : this.data.getUsuarios()){
                    if (usuario.getIdEmpresa() == this.data.getEmpresaSeleccionada().getId()){
                        this.usuariosObservable.add(usuario);
                    }
                }
                this.tabla.setItems(this.usuariosObservable);
            } else {
                ObservableList<Usuario> filteredList = this.usuariosObservable.filtered(item -> item.getCorreo().toLowerCase().contains(textoIntroducido.toLowerCase()));
                this.tabla.setItems(filteredList);
            }
        });
    }
}

