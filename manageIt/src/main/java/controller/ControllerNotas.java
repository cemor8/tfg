package controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import modelo.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.UnaryOperator;

public class ControllerNotas {

    @FXML
    private MFXButton btnGuardarCambios;

    @FXML
    private MFXButton btnMeter;

    @FXML
    private AnchorPane contenedorNota;

    @FXML
    private Label creador;

    @FXML
    private TextArea descripcion;

    @FXML
    private Label fecha;
    @FXML
    private ImageView img;

    @FXML
    private ImageView imgCreador;

    @FXML
    private MFXScrollPane scroll;

    @FXML
    private TextArea tituloNota;
    @FXML
    private ImageView imgEditar;
    private Data data;
    @FXML
    private Label creadorTexto;
    private ArrayList<Nota> notasRecorrer;
    private Nota notaSeleccionada;
    @FXML
    private ImageView calendario;
    private String rutaImagenElegida;

    @FXML
    void meter(MouseEvent event) throws IOException {
        LocalDate hoy = LocalDate.now();
        this.notasRecorrer.add(new Nota("","","",Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant()), this.data.getCurrentUser()));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/notas.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerNotas controllerNotas = fxmlLoader.getController();
        controllerNotas.recibirData(this.data,this.notasRecorrer);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }
    @FXML
    void guardarNota(MouseEvent event) {

        if (rutaImagenElegida != null && !rutaImagenElegida.isEmpty()){
            this.notaSeleccionada.setRutaImagen(rutaImagenElegida);
        }
        this.notaSeleccionada.setTitulo(this.tituloNota.getText());
        this.notaSeleccionada.setDescripcion(this.descripcion.getText());
    }
    public void inicializar() throws IOException {
        /* Recorrer notas y meter en scrolleable */
        this.btnMeter.setText("");
        this.creadorTexto.setVisible(false);
        this.descripcion.setVisible(false);
        this.btnGuardarCambios.setVisible(false);
        this.calendario.setVisible(false);

        this.imgEditar.setVisible(false);
        this.tituloNota.setEditable(false);

        VBox vBox = new VBox();
        for (Nota nota : notasRecorrer){
            AnchorPane anchorPane = new AnchorPane();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/cadaNota.fxml"), CambiarIdioma.getInstance().getBundle());
            Parent root = fxmlLoader.load();
            ControllerCadaNota controllerCadaNota = fxmlLoader.getController();
            controllerCadaNota.recibirData(this.data,nota,this.notasRecorrer);
            anchorPane.getChildren().setAll(root);
            vBox.getChildren().add(anchorPane);
            VBox.setMargin(anchorPane, new Insets(10, 0, 20, 20));
        }
        this.scroll.setContent(vBox);
        if (!this.notasRecorrer.isEmpty()){
            this.cargarNota(this.notasRecorrer.get(0));
        }

        final int maxLength = 25; // Máximo número de caracteres
        UnaryOperator<TextFormatter.Change> textFormatterFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.length() > maxLength) {
                return null; // Ignora este cambio
            } else {
                return change; // Permite este cambio
            }
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(textFormatterFilter);
        this.tituloNota.setTextFormatter(textFormatter);




    }

    public void recibirData(Data data, ArrayList<Nota> notas) throws IOException {
        this.notasRecorrer = notas;
        this.data = data;
        this.data.getListaControladores().setControllerNotas(this);
        this.inicializar();
    }
    public void cargarNota(Nota nota){
        this.notaSeleccionada = nota;

        this.imgEditar.setVisible(true);
        this.tituloNota.setEditable(true);
        this.creadorTexto.setVisible(true);
        this.descripcion.setVisible(true);
        this.btnGuardarCambios.setVisible(true);
        this.img.setImage(null);
        this.calendario.setVisible(true);

        this.tituloNota.setText("");
        this.tituloNota.setText(this.notaSeleccionada.getTitulo());


        this.descripcion.setText(this.notaSeleccionada.getDescripcion());

        this.imgCreador.setImage(new Image("file:"+this.notaSeleccionada.getUsuario().getRutaImagen()));
        this.imgCreador.setFitWidth(60);
        this.imgCreador.setFitHeight(60);
        this.imgCreador.setPreserveRatio(false);

        Circle circulo = new Circle(this.imgCreador.getFitWidth() / 2, this.imgCreador.getFitHeight() / 2, this.imgCreador.getFitWidth() / 2);
        this.imgCreador.setClip(circulo);
        Rectangle clip = new Rectangle(
                this.img.getFitWidth(), this.img.getFitHeight()
        );
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        if(this.notaSeleccionada.getRutaImagen()!=null && !this.notaSeleccionada.getRutaImagen().isEmpty()){
            this.img.setImage(new Image("file:"+this.notaSeleccionada.getRutaImagen()));
        }

        this.img.setClip(clip);

        SimpleDateFormat fechaCreacion = new SimpleDateFormat("dd-MM-yyyy");
        String mostrarCreacion = fechaCreacion.format(this.notaSeleccionada.getFechaCreacion());
        this.fecha.setText(mostrarCreacion);
        this.creador.setText(this.notaSeleccionada.getUsuario().getNombre()+" "+this.notaSeleccionada.getUsuario().getApellidos());

        if (!this.data.getCurrentUser().getCorreo().equalsIgnoreCase(this.notaSeleccionada.getUsuario().getCorreo())){
            this.imgEditar.setVisible(false);
            this.imgEditar.setDisable(true);
            this.tituloNota.setEditable(false);
            this.descripcion.setEditable(false);
        }else {
            this.imgEditar.setVisible(true);
            this.imgEditar.setDisable(false);
            this.tituloNota.setEditable(true);
            this.descripcion.setEditable(true);
        }

    }
    @FXML
    void editarImagen(MouseEvent event) {

        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.png","*.jpg","*.jpeg"));
        File selectedFile = filechooser.showOpenDialog(null);

        if(selectedFile != null){
            String imagePath = selectedFile.getAbsolutePath();
            this.rutaImagenElegida = imagePath;

            this.img.setImage(new Image("file:"+rutaImagenElegida));

        }
    }


}

