package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import modelo.CambiarIdioma;
import modelo.Data;
import modelo.Tarea;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ControllerCadaTarea {

    @FXML
    private Label descripcion;

    @FXML
    private Label fechaCreacion;

    @FXML
    private Label fechaEntrega;

    @FXML
    private ImageView fotoCreacion;

    @FXML
    private ImageView fotoEntrega;

    @FXML
    private ImageView imagen;

    @FXML
    private ImageView imagenBasura;

    @FXML
    private ImageView imagenCreador;

    @FXML
    private ImageView imagenVer;

    @FXML
    private Label nombre;
    @FXML
    private Label cliente;

    private Data data;
    private Tarea tarea;
    private boolean meter;
    private ArrayList<Tarea> tareas;

    @FXML
    void eliminarTarea(MouseEvent event) throws IOException {
        this.tareas.remove(tarea);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/tareas.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerTareas controllerTareas = fxmlLoader.getController();
        controllerTareas.recibirData(this.data,this.tareas,this.meter);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);

    }

    @FXML
    void verTarea(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/vistaCadaTarea.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerVistaCadaTarea controllerVistaCadaTarea = fxmlLoader.getController();
        controllerVistaCadaTarea.recibirData(this.data,this.tarea,this.tareas,this.meter);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }
    public void inicializar()  {
        this.nombre.setText(this.tarea.getNombre());
        this.imagen.setImage(new Image("file:"+this.tarea.getRutaimagen()));
        this.descripcion.setText(this.tarea.getDescripcion());
        SimpleDateFormat fechaCreacion = new SimpleDateFormat("dd-MM-yyyy");
        String mostrarCreacion = fechaCreacion.format(this.tarea.getFechaCreacion());
        String mostrarEntrega = fechaCreacion.format(this.tarea.getFechaEntrega());
        this.fechaCreacion.setText(mostrarCreacion);
        this.fechaEntrega.setText(mostrarEntrega);
        Rectangle clip = new Rectangle(
                this.imagen.getFitWidth(), this.imagen.getFitHeight()
        );
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        this.imagen.setClip(clip);

        this.imagenBasura.setFitWidth(30);
        this.imagenBasura.setFitHeight(30);

        this.imagenVer.setFitWidth(30);
        this.imagenVer.setFitHeight(30);

        this.fotoEntrega.setFitWidth(20);
        this.fotoEntrega.setFitHeight(20);

        this.fotoCreacion.setFitWidth(20);
        this.fotoCreacion.setFitHeight(20);

        this.imagenCreador.setImage(new Image("file:"+this.tarea.getCreador().getRutaImagen()));
        this.imagenCreador.setFitWidth(70);
        this.imagenCreador.setFitHeight(70);
        this.imagenCreador.setPreserveRatio(false);

        Circle circulo = new Circle(this.imagenCreador.getFitWidth() / 2, this.imagenCreador.getFitHeight() / 2, this.imagenCreador.getFitWidth() / 2);
        this.imagenCreador.setClip(circulo);


        this.cliente.setText(this.tarea.getCampo());
        if (this.tarea.getCampo().equalsIgnoreCase("WEB")){
            this.cliente.getStyleClass().add("pendiente");
        }else if(this.tarea.getCampo().equalsIgnoreCase("BBDD")){
            this.cliente.getStyleClass().add("completado");
        }else if(this.tarea.getCampo().equalsIgnoreCase("UX")){
            this.cliente.getStyleClass().add("proceso");
        }else if(this.tarea.getCampo().equalsIgnoreCase("UI")){
            this.cliente.getStyleClass().add("pendiente");
        }else {
            this.cliente.getStyleClass().add("pendiente");
        }
    }
    public void recibirData(Data data, Tarea tarea, ArrayList<Tarea> tareas, boolean meter){
        this.data = data;
        this.tarea = tarea;
        this.tareas =tareas;
        this.meter = meter;
        this.inicializar();
    }

}

