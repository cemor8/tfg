package controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PointLight;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import modelo.CambiarIdioma;
import modelo.Data;
import modelo.Proyecto;
import modelo.Tarea;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerVistaCadaTarea {

    @FXML
    private MFXButton btnAtras;
    @FXML
    private MediaView video;
    @FXML
    private Label cliente;

    @FXML
    private MFXButton btnGuardar;
    @FXML
    private ImageView imgGuardarDesc;
    private boolean meter;
    @FXML
    private Label creacion;

    @FXML
    private Label entrega;

    @FXML
    private ImageView imagenJefe;

    @FXML
    private ImageView imagenProyecto;

    @FXML
    private TextArea labelDescripcion;

    @FXML
    private Label labelNombreJefe;

    @FXML
    private Label labelNombreTarea;

    @FXML
    private ComboBox<String> opcionesEstado;
    private Data data;
    private Tarea tarea;
    private ArrayList<Tarea> tareas;
    Map<String, String> columnasExpresiones = new HashMap<String, String>() {
        {
            put("descripcion", "^.{15,100}$");
            put("nombre", "^^.{5,25}$");
        }

    };

    /**
     * Método que guarda el estado de la tarea
     * @param event
     */
    @FXML
    void btnGuardarEstado(MouseEvent event) {
        this.tarea.setEstado(this.opcionesEstado.getSelectionModel().getSelectedItem());
        if (this.tarea.getEstado().equalsIgnoreCase("Completado")){
            this.btnGuardar.setDisable(true);
            this.imgGuardarDesc.setDisable(true);
            this.labelDescripcion.setEditable(false);
        }
    }

    /**
     * Método que se encarga de ver las personas asignadas de la tarea
     * @param event
     * @throws IOException
     */
    @FXML
    void verAsignados(MouseEvent event) throws IOException {
        Proyecto proyectoEncontrado = null;
        for (Proyecto proyecto : this.data.getProyectos()){
           if (proyecto.getTareas().contains(this.tarea)){
               proyectoEncontrado = proyecto;
               break;
           }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/contactos.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerContactos controllerContactos = fxmlLoader.getController();
        assert proyectoEncontrado != null;
        controllerContactos.recibirData(this.data,this.tarea.getPersonasAsignadas(),false,proyectoEncontrado.getPersonasAsignadas(),true);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que se encarga de ver las notas de la tarea
     * @param event
     * @throws IOException
     */
    @FXML
    void verNotas(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/notas.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerNotas controllerNotas = fxmlLoader.getController();
        controllerNotas.recibirData(this.data,this.tarea.getNotas());
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que se encarga de volver a la vista anterior
     * @param event
     * @throws IOException
     */
    @FXML
    void volver(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/tareas.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerTareas controllerTareas = fxmlLoader.getController();
        controllerTareas.recibirData(this.data,this.tareas,this.meter);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que se encarga de recibir la informacion
     * @param data  clase con informacion
     * @param tarea tarea seleccionada
     * @param tareas    lista de tareas
     */
    public void recibirData(Data data,Tarea tarea, ArrayList<Tarea> tareas,boolean meter){
        this.tarea = tarea;
        this.data = data;
        this.meter = meter;
        this.tareas = tareas;
        this.inicializar();
    }

    /**
     * Método que se encarga de inicializar la tarea
     */
    public void inicializar(){
        this.btnAtras.setText("");
        this.labelDescripcion.setText(this.tarea.getDescripcion());
        this.labelNombreJefe.setText(this.tarea.getCreador().getNombre()+" "+this.tarea.getCreador().getApellidos());
        this.labelNombreTarea.setText(this.tarea.getNombre());
        this.imagenJefe.setImage(new Image("file:"+this.tarea.getCreador().getRutaImagen()));
        this.imagenProyecto.setImage(new Image("file:"+this.tarea.getRutaimagen()));

        this.imagenJefe.setFitWidth(55);
        this.imagenJefe.setFitHeight(55);
        this.imagenJefe.setPreserveRatio(false);
        Circle clip = new Circle(this.imagenJefe.getFitWidth() / 2, this.imagenJefe.getFitHeight() / 2, this.imagenJefe.getFitWidth() / 2);
        this.imagenJefe.setClip(clip);

        Rectangle rect = new Rectangle(
                this.imagenProyecto.getFitWidth(), this.imagenProyecto.getFitHeight()
        );
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        this.imagenProyecto.setClip(rect);

        this.opcionesEstado.getItems().addAll("En Proceso","Completado","Bloqueado");
        this.opcionesEstado.setValue(this.tarea.getEstado());
        if(this.tarea.getEstado().equalsIgnoreCase("Completado") || !this.data.getCurrentUser().getCorreo().equalsIgnoreCase(this.tarea.getCreador().getCorreo())){
            this.btnGuardar.setDisable(true);
            this.imgGuardarDesc.setDisable(true);
            this.labelDescripcion.setEditable(false);
        }

        this.cliente.setText(this.tarea.getCampo());
        if (this.tarea.getCampo().equalsIgnoreCase("BBDD")){
            this.cliente.getStyleClass().clear();
            this.cliente.getStyleClass().add("pendiente");
        }else{
            this.cliente.getStyleClass().clear();
            this.cliente.getStyleClass().add("pendiente");
        }

        SimpleDateFormat fechaCreacion = new SimpleDateFormat("dd-MM-yyyy");
        String mostrarCreacion = fechaCreacion.format(this.tarea.getFechaCreacion());
        this.creacion.setText(mostrarCreacion);

        SimpleDateFormat fechaEntrega = new SimpleDateFormat("dd-MM-yyyy");
        String mostrarEntrega = fechaEntrega.format(this.tarea.getFechaEntrega());
        this.entrega.setText(mostrarEntrega);


        if (!this.tarea.getCreador().getCorreo().equalsIgnoreCase(this.data.getCurrentUser().getCorreo())){
            this.btnGuardar.setDisable(true);
            this.imgGuardarDesc.setDisable(true);
            this.labelDescripcion.setEditable(false);
        }
        if (this.tarea.getRutaVideo() != null && !this.tarea.getRutaVideo().equalsIgnoreCase("")){
            File file = new File(this.tarea.getRutaVideo());
            String mediaUrl = file.toURI().toString();
            Media media2 = new Media(mediaUrl);
            MediaPlayer mediaPlayer = new MediaPlayer(media2);
            this.video.setMediaPlayer(mediaPlayer);
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer.play();
            });

            mediaPlayer.play();
        }

    }

    /**
     * Método que guarda la descripcion de cada tarea
     * @param event
     */
    @FXML
    void guardarDesc(MouseEvent event) {
        if (!validarContenido(this.columnasExpresiones.get("descripcion"), this.labelDescripcion.getText())) {
            return;
        }
        this.tarea.setDescripcion(this.labelDescripcion.getText());
    }
    /**
     * Método que devuelve true si se cumple una expresion regular en una string
     *
     * @param patron       expresion regular
     * @param texto_buscar texto donde buscar el patron
     */
    public boolean validarContenido(String patron, String texto_buscar) {
        Pattern patronValidar = Pattern.compile(patron);
        Matcher matcher = patronValidar.matcher(texto_buscar);
        return matcher.matches();
    }

}

