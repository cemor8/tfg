package controller.mejora;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerPanelAdmin implements Initializable {

    @FXML
    private MFXButton btnMeter;

    @FXML
    private TableView<Empresa> tabla;
    private ObservableList<Empresa> empresaObservableList= FXCollections.observableArrayList();
    private Data data;

    @FXML
    void crear(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/editarEmpresas.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerEditarEmpresas controllerEditarEmpresas = fxmlLoader.getController();
        controllerEditarEmpresas.recibirData(this.data,null);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    @FXML
    void eliminar(MouseEvent event) {
        Empresa empresa = this.tabla.getSelectionModel().getSelectedItem();
        if (empresa == null){
            return;
        }
        this.data.getEmpresas().remove(empresa);
        this.empresaObservableList.remove(empresa);
        this.tabla.refresh();
        ConexionBase.eliminarEmpresa(empresa.getId());
    }

    @FXML
    void modificar(MouseEvent event) throws IOException {
        Empresa empresa = this.tabla.getSelectionModel().getSelectedItem();
        if (empresa == null){
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/editarEmpresas.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerEditarEmpresas controllerEditarEmpresas = fxmlLoader.getController();
        controllerEditarEmpresas.recibirData(this.data,empresa);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }
    public void recibirData(Data data){
        this.data = data;
        this.btnMeter.setText("");
        this.empresaObservableList.addAll(this.data.getEmpresas());

        this.tabla.setItems(this.empresaObservableList);

        this.data.getListaControladores().getControllerMenuSuperiorAdmin().barraBusqueda.textProperty().addListener((observable, textoPrevio, textoIntroducido) -> {

            if (textoIntroducido.isEmpty()) {
                this.empresaObservableList.addAll(this.data.getEmpresas());
                this.tabla.setItems(this.empresaObservableList);
            } else {
                ObservableList<Empresa> filteredList = this.empresaObservableList.filtered(item -> item.getCorreo().toLowerCase().contains(textoIntroducido.toLowerCase()));
                this.tabla.setItems(filteredList);
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Empresa,String> columnaNombre = new TableColumn<>(CambiarIdioma.getInstance().getBundle().getString("tablaE.nombre"));
        this.tabla.getColumns().add(columnaNombre);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));


        TableColumn<Empresa,String> columnaCorreo = new TableColumn<>(CambiarIdioma.getInstance().getBundle().getString("tablaE.correo"));
        this.tabla.getColumns().add(columnaCorreo);
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));


        TableColumn<Empresa,String> columnaSector = new TableColumn<>(CambiarIdioma.getInstance().getBundle().getString("tablaE.sector"));
        this.tabla.getColumns().add(columnaSector);
        columnaSector.setCellValueFactory(new PropertyValueFactory<>("sector"));

    }

}

