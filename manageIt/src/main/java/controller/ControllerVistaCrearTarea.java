package controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import modelo.Data;
import modelo.Proyecto;
import modelo.Tarea;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerVistaCrearTarea {

    @FXML
    private ImageView imagenProyecto;

    @FXML
    private ImageView imgVideo;
    @FXML
    private MediaView media;
    @FXML
    private DatePicker datePicker;


    @FXML
    private TextArea introducirDescripcion;

    @FXML
    private MFXTextField introducirFecha;

    @FXML
    private MFXTextField introducirNombre;

    @FXML
    private Label labelTextoCrearTarea;

    @FXML
    private ComboBox<String> seleccionarCampo;
    private Data data;
    @FXML
    private Label errorCliente;

    @FXML
    private Label errorDescrip;

    @FXML
    private Label errorFecha;

    @FXML
    private Label errorImagen;

    @FXML
    private Label errorNombre;
    private String imagenSeleccionada;
    private String videoSeleccionado = "";
    private ArrayList<Tarea> tareas;
    Map<String, String> columnasExpresiones = new HashMap<String, String>() {
        {
            put("descripcion", "^.{15,100}$");
            put("nombre", "^^.{5,25}$");
        }

    };

    /**
     * Método que se encarga de crear el proyecto
     * @param event
     */
    @FXML
    void crear(MouseEvent event) {
        errorFecha.setText("");
        errorCliente.setText("");
        errorDescrip.setText("");
        errorNombre.setText("");
        errorImagen.setText("");

        boolean error = false;
        if (imagenSeleccionada == null) {
            errorImagen.setText("Selecciona imagen");
            error = true;
        }
        if (!validarContenido(this.columnasExpresiones.get("descripcion"), this.introducirDescripcion.getText())) {
            errorDescrip.setText("Descripcion de 15 - 100 caracteres");
            error = true;
        }
        if (!validarContenido(this.columnasExpresiones.get("nombre"), this.introducirNombre.getText())) {
            errorNombre.setText("Nombre de 5 - 25 caracteres");
            error = true;
        }
        if(seleccionarCampo.getValue() == null){
            errorCliente.setText("Introduce un cliente");
            error = true;
        }
        if(this.datePicker.getValue() == null){
            errorFecha.setText("Introduce una fecha de entrega");
            error = true;
        }


        if (error){
            return;
        }


        LocalDate today = LocalDate.now();
        Date dateHoy = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateCrear = Date.from(this.datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        this.tareas.add(new Tarea(this.introducirNombre.getText(),"En proceso",this.seleccionarCampo.getValue(),this.introducirDescripcion.getText(),
                        dateHoy,dateCrear,this.imagenSeleccionada,new ArrayList<>(),new ArrayList<>(List.of(this.data.getCurrentUser())),this.data.getCurrentUser(),
                this.videoSeleccionado));
        try {
            this.data.getListaControladores().getControllerMenuLateral().mostrarTareas(null);
        }catch (IOException err){
            System.out.println(err.getMessage());
        }




    }

    /**
     * Método que se encarga de elegir la imagen para el proyecto
     * @param event
     */
    @FXML
    void imagen(MouseEvent event) {
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.png","*.jpg","*.jpeg"));
        File selectedFile = filechooser.showOpenDialog(null);

        if(selectedFile != null){
            String imagePath = selectedFile.getAbsolutePath();
            this.imagenSeleccionada = imagePath;

            this.imagenProyecto.setImage(new Image("file:"+imagenSeleccionada));

        }
    }

    /**
     * Método que selecciona el vídeo para el proyecto
     * @param event
     */
    @FXML
    void video(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a Video File", "*.mp4", "*.avi");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            this.videoSeleccionado = file.getAbsolutePath();
            Media media2 = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media2);
            this.media.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();

        }
    }

    /**
     * Método que recibe informacion
     * @param data
     */
    public void recibirData(Data data,ArrayList<Tarea> tareas){
        this.data = data;
        this.seleccionarCampo.getItems().addAll("BBDD","WEB", "UX", "UI");
        this.datePicker.setShowWeekNumbers(false);
        this.datePicker.getValue();
        this.tareas = tareas;



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

