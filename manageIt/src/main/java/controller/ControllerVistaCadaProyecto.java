package controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerVistaCadaProyecto {

    @FXML
    private MFXButton btnAtras;

    @FXML
    private TextArea labelDescripcion;
    @FXML
    private MediaView video;

    @FXML
    private ImageView imgGuardarDesc;
    @FXML
    private Label labelNombreJefe;

    @FXML
    private Label labelNombreProyecto;
    @FXML
    private Label cliente;
    @FXML
    private ImageView imagenJefe;

    @FXML
    private ImageView imagenProyecto;
    @FXML
    private Label creacion;

    @FXML
    private Label entrega;

    @FXML
    private ComboBox<String> opcionesEstado;
    @FXML
    private MFXButton btnGuardar;
    private Data data;
    private Proyecto proyecto;
    private ArrayList<Proyecto> listaDeProyecto;
    Map<String, String> columnasExpresiones = new HashMap<String, String>() {
        {
            put("descripcion", "^.{15,100}$");
            put("nombre", "^^.{5,25}$");
        }

    };

    /**
     * Método que se encarga de guardar el estado del proyecto
     * @param event
     */
    @FXML
    void btnGuardarEstado(MouseEvent event) {
        this.proyecto.setEstado(this.opcionesEstado.getSelectionModel().getSelectedItem());
        if (this.proyecto.getEstado().equalsIgnoreCase("Completado")){
            this.btnGuardar.setDisable(true);
            this.imgGuardarDesc.setDisable(true);
            this.labelDescripcion.setEditable(false);
        }
    }

    /**
     * Método que se encarga de ver la lista de contactos asignados
     * @param event
     * @throws IOException
     */
    @FXML
    void verAsignados(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/contactos.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerContactos controllerContactos = fxmlLoader.getController();
        controllerContactos.recibirData(this.data,this.proyecto.getPersonasAsignadas(),false,this.data.getCurrentUser().getContactos(),true);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que se encarga de ver las notas del proyeto
     * @param event
     * @throws IOException
     */
    @FXML
    void verNotas(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/notas.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerNotas controllerNotas = fxmlLoader.getController();
        controllerNotas.recibirData(this.data,this.proyecto.getNotas());
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que carga la vista de las tareas con la lista de tareas del proyecto
     * @param event
     * @throws IOException
     */
    @FXML
    void verTareas(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/tareas.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerTareas controllerTareas = fxmlLoader.getController();
        controllerTareas.recibirData(this.data,this.proyecto.getTareas(),true);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que se encarga de volver a la vista de proyectos
     * @param event
     * @throws IOException
     */
    @FXML
    void volver(MouseEvent event) throws IOException {
        /* Volver a vista de todos los proyectos */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/proyectos.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerProyectos controllerProyectos = fxmlLoader.getController();
        controllerProyectos.recibirData(this.data,this.listaDeProyecto);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }

    /**
     * Método que se encarga de cargar la informacion del proyecto en la vista
     */
    public void inicializar(){
        this.btnAtras.setText("");
        this.labelDescripcion.setText(this.proyecto.getDescripcion());
        this.labelNombreJefe.setText(this.proyecto.getJefeProyecto().getNombre()+" "+this.proyecto.getJefeProyecto().getApellidos());
        this.labelNombreProyecto.setText(this.proyecto.getNombre());
        this.imagenJefe.setImage(new Image("file:"+this.proyecto.getJefeProyecto().getRutaImagen()));
        this.imagenProyecto.setImage(new Image("file:"+this.proyecto.getRutaImagen()));

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
        this.opcionesEstado.setValue(this.proyecto.getEstado());
        if(this.proyecto.getEstado().equalsIgnoreCase("Completado") || !this.data.getCurrentUser().getCorreo().equalsIgnoreCase(this.proyecto.getJefeProyecto().getCorreo())){
            this.btnGuardar.setDisable(true);
        }
        this.cliente.setText(this.proyecto.getCliente());
        if (this.proyecto.getCliente().equalsIgnoreCase("netflix")){
            this.cliente.getStyleClass().add("pendiente");
        }else if(this.proyecto.getCliente().equalsIgnoreCase("uber")){
            this.cliente.getStyleClass().add("completado");
        }else if(this.proyecto.getCliente().equalsIgnoreCase("movistar")){
            this.cliente.getStyleClass().add("proceso");
        }else if(this.proyecto.getCliente().equalsIgnoreCase("Discord")){
            this.cliente.getStyleClass().add("pendiente");
        }else {
            this.cliente.getStyleClass().add("pendiente");
        }
        SimpleDateFormat fechaCreacion = new SimpleDateFormat("dd-MM-yyyy");
        String mostrarCreacion = fechaCreacion.format(this.proyecto.getFechaCreacion());
        this.creacion.setText(mostrarCreacion);

        SimpleDateFormat fechaEntrega = new SimpleDateFormat("dd-MM-yyyy");
        String mostrarEntrega = fechaEntrega.format(this.proyecto.getFechaEntrega());
        this.entrega.setText(mostrarEntrega);


        if (!this.proyecto.getJefeProyecto().getCorreo().equalsIgnoreCase(this.data.getCurrentUser().getCorreo())){
            this.btnGuardar.setDisable(true);
            this.imgGuardarDesc.setDisable(true);
            this.labelDescripcion.setEditable(false);
        }
        if (this.proyecto.getRutaVideo() != null && !this.proyecto.getRutaVideo().equalsIgnoreCase("")){
            File file = new File(this.proyecto.getRutaVideo());
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
    @FXML
    void guardarDesc(MouseEvent event) {
        if (!validarContenido(this.columnasExpresiones.get("descripcion"), this.labelDescripcion.getText())) {
            return;
        }
        this.proyecto.setDescripcion(this.labelDescripcion.getText());
    }

    /**
     * Método que se encarga de recibir informacin
     * @param data  clase con informacion
     * @param proyecto  proyecto del que cargar la vista
     * @param listaDeProyecto   lista de proyectos
     */
    public void recibirData(Data data,Proyecto proyecto, ArrayList<Proyecto> listaDeProyecto){
        this.data = data;
        this.proyecto = proyecto;
        this.listaDeProyecto = listaDeProyecto;
        this.inicializar();
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

