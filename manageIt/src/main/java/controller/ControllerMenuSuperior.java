package controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import modelo.CambiarIdioma;
import modelo.Data;
import modelo.Proyecto;
import modelo.Usuario;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerMenuSuperior {

    @FXML
    private MFXTextField barraBusqueda;
    @FXML
    private Label nombre;

    @FXML
    private MFXButton btnBuscar;
    @FXML
    private AnchorPane menuSuperior;
    private Data data;
    @FXML
    private ImageView perfil;

    /**
     * Método que se encarga de recibir la información
     * @param data información
     */
    public void recibirData(Data data){
        this.data = data;
        this.btnBuscar.setText("");
        this.ponerImagen();
        Platform.runLater(() -> {
            this.menuSuperior.requestFocus();
        });
        this.ponerNombre();

    }
    public void ponerNombre(){
        this.nombre.setText(this.data.getCurrentUser().getNombre()+" "+this.data.getCurrentUser().getApellidos());
    }

    /**
     * Método que se encarga de poner la imagen del usuario
     */
    public void ponerImagen(){
        this.perfil.setImage(new Image("file:"+this.data.getCurrentUser().getRutaImagen()));
        this.perfil.setFitWidth(60);
        this.perfil.setFitHeight(60);
        this.perfil.setPreserveRatio(false);
        Circle clip = new Circle(this.perfil.getFitWidth() / 2, this.perfil.getFitHeight() / 2, this.perfil.getFitWidth() / 2);
        this.perfil.setClip(clip);
    }

    /**
     * Método que carga la vista del calendario
     * @param event
     * @throws IOException
     */
    @FXML
    void verCalendario(MouseEvent event) throws IOException {
        this.data.getListaControladores().getControllerMenuLateral().mostrarCalendario(null);
    }

    /**
     * Método que carga la vista de configuracion del perfil
     * @param event
     * @throws IOException
     */
    @FXML
    void verPerfil(MouseEvent event) throws IOException {
        this.data.getListaControladores().getControllerMenuLateral().mostrarConfig(null);
    }

    /**
     * Método que carga la vista de los proyectos
     * @param event
     * @throws IOException
     */
    @FXML
    void verProyectos(MouseEvent event) throws IOException {
        this.data.getListaControladores().getControllerMenuLateral().mostrarProyectos(null);
    }

    /**
     * Método que busca proyectos que tengan el texto de la barra de búsqueda
     * @param event
     * @throws IOException
     */
    @FXML
    void buscar(MouseEvent event) throws IOException {
        if (this.barraBusqueda.getText().isEmpty()){
            return;
        }
        String texto = this.barraBusqueda.getText();
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        for (Proyecto proyecto : this.data.getProyectos()) {
            if (proyecto.getJefeProyecto().getCorreo().equalsIgnoreCase(this.data.getCurrentUser().getCorreo()) && proyecto.getNombre().toLowerCase().contains(texto.toLowerCase())){
                proyectos.add(proyecto);
                continue;
            }
            for (Usuario usuario : proyecto.getPersonasAsignadas()) {
                if (usuario.getCorreo().equals(this.data.getCurrentUser().getCorreo()) && proyecto.getNombre().toLowerCase().contains(texto.toLowerCase()) ) {
                    proyectos.add(proyecto);
                    break;
                }
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/proyectos.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerProyectos controllerProyectos = fxmlLoader.getController();
        controllerProyectos.recibirData(this.data,proyectos);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);

    }

}

