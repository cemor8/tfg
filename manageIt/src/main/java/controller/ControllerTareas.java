package controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.CambiarIdioma;
import modelo.Data;
import modelo.Proyecto;
import modelo.Tarea;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerTareas {

    @FXML
    private MFXButton btnMeter;

    @FXML
    private MFXScrollPane scroll;
    public boolean meter;
    private Data data;
    private ArrayList<Tarea> tareas;

    /**
     * Método que se encarga de meter una tarea nueva
     * @param event
     */
    @FXML
    void meter(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/vistaCrearTarea.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerVistaCrearTarea controllerVistaCrearTarea = fxmlLoader.getController();
        controllerVistaCrearTarea.recibirData(this.data,this.tareas);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que se encarga de inicializar la vista de tareas con las tareas correspondientes
     * @throws IOException
     */
    public void inicializar() throws IOException {
        int i = 0;
        HBox hBox = new HBox();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        for (Tarea tarea : this.tareas) {
            if (i % 3 == 0 && i > 0) {
                if (hBox != null) {
                    vbox.getChildren().add(hBox);
                }
                hBox = new HBox(10);
            }
            AnchorPane anchorPane = new AnchorPane();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/cadaTarea.fxml"), CambiarIdioma.getInstance().getBundle());
            Parent root = fxmlLoader.load();
            ControllerCadaTarea controllerCadaTarea = fxmlLoader.getController();
            controllerCadaTarea.recibirData(this.data, tarea, this.tareas,this.meter);
            anchorPane.getChildren().setAll(root);

            HBox.setMargin(anchorPane, new Insets(10, 0, 0, 10));

            hBox.getChildren().add(anchorPane);
            i++;


            if (i == this.tareas.size()) {
                vbox.getChildren().add(hBox);
            }

        }
        this.scroll.setContent(vbox);
        this.btnMeter.setText("");

    }

    /**
     * Método que se encarga de recibir informacion
     * @param data  clase con informacion
     * @param tareas    lista de tareas
     * @throws IOException
     */
    public void recibirData(Data data, ArrayList<Tarea> tareas,boolean meter) throws IOException {
        this.data = data;
        this.meter = meter;
        this.tareas = tareas;
        if (!meter){
            this.btnMeter.setVisible(false);
        }
        this.inicializar();
    }

}
