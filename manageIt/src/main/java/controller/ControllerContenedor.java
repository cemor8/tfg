package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import modelo.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ControllerContenedor {

    @FXML
    private AnchorPane menuLateral;

    @FXML
    private AnchorPane menuSuperior;

    @FXML
    private AnchorPane padre;

    @FXML
    private AnchorPane rellenar;
    private Data data;

    /**
     * Método que se encarga de recibir la información
     * @param data
     * @throws IOException
     */
    public void recibirData(Data data) throws IOException {
        this.data = data;
        this.data.getListaControladores().setControllerContenedor(this);
        this.cargarLateral();
        this.cargarSuperior();
        this.cargarContenido();
        this.meterEstilo("/styles/oscuro.css");
    }

    /**
     * Método que se encarga de cargar el menú superior
     * @throws IOException
     */
    public void cargarSuperior() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/menuSuperior.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerMenuSuperior controllerMenuSuperior = fxmlLoader.getController();
        controllerMenuSuperior.recibirData(this.data);
        this.menuSuperior.getChildren().setAll(root);
    }

    /**
     * Método que se encarga de cargar el menú lateral
     * @throws IOException
     */
    public void cargarLateral() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/menuLateral.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerMenuLateral controllerMenuLateralc = fxmlLoader.getController();
        controllerMenuLateralc.recibirData(this.data);
        this.menuLateral.getChildren().setAll(root);
    }

    /**
     * Método que se encarga de cargar el panel principal
     * @throws IOException
     */
    public void cargarContenido() throws IOException {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        for (Proyecto proyecto : this.data.getProyectos()) {
            for (Usuario usuario : proyecto.getPersonasAsignadas()) {
                if (usuario.getCorreo().equals(this.data.getCurrentUser().getCorreo())) {
                    proyectos.add(proyecto);
                    break;
                }
            }
        }
        ArrayList<Tarea> tareas = new ArrayList<>();
        for (Proyecto proyecto : this.data.getProyectos()){
            for (Tarea tarea : proyecto.getTareas()){
                if (tarea.getCreador().getCorreo().equalsIgnoreCase(this.data.getCurrentUser().getCorreo())){
                    tareas.add(tarea);
                    continue;
                }
                for (Usuario usuario : tarea.getPersonasAsignadas()){
                    if (usuario.getCorreo().equalsIgnoreCase(this.data.getCurrentUser().getCorreo())){
                        tareas.add(tarea);
                        break;
                    }
                }
            }
        }


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/panel.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerPanel controllerPanel = fxmlLoader.getController();
        controllerPanel.recibirData(this.data,proyectos,tareas);
        this.data.getListaControladores().getControllerMenuLateral().iniciarPanel();
        this.rellenarContenido(root);

    }

    /**
     * Método que carga el contenido en el contenedor
     * @param root
     */
    public void rellenarContenido(Parent root){
        this.rellenar.getChildren().setAll(root);
    }

    /**
     * Método que mete el estilo de la app
     * @param ruta
     */
    public void meterEstilo(String ruta){
        this.padre.getStylesheets().clear();
        this.padre.getStylesheets().add(Objects.requireNonNull(getClass().getResource(ruta)).toExternalForm());
    }

}

