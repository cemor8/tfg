package controller.mejora;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import modelo.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerEditarEmpresas {

    @FXML
    private MFXButton btnAtras;

    @FXML
    private Label errorContraseña;

    @FXML
    private Label errorCorreo;

    @FXML
    private Label errorDepartamento;

    @FXML
    private Label errorDescrp;

    @FXML
    private Label errorNombre;

    @FXML
    private Label errorPuesto;

    @FXML
    private ImageView imagenUsuario;

    @FXML
    private MFXTextField introducirContraseña;

    @FXML
    private MFXTextField introducirCorreo;

    @FXML
    private TextArea introducirDescripcion;

    @FXML
    private MFXTextField introducirNombre;

    @FXML
    private MFXTextField introducirSector;

    @FXML
    private Label labelTextoCrearProyecto;
    @FXML
    private ImageView imagenBanner;

    @FXML
    private ImageView imagenPerfil;
    private Data data;
    private Empresa empresa;
    private String rutaImagenPerfil;
    private String rutaImagenBanner;
    Map<String, String> columnasExpresiones = new HashMap<String, String>() {
        {
            put("descripcion", "^.{15,100}$");
            put("nombre", "^.{5,25}$");
            put("correo", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
            put("sector", "^.{5,20}$");
            put("contraseña", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        }

    };

    @FXML
    void crear(MouseEvent event) throws IOException {
        boolean error = false;

        String nombreUsuario = introducirNombre.getText();
        String correoUsuario= introducirCorreo.getText();
        String sector = introducirSector.getText();
        String contraseña = introducirContraseña.getText();
        String descripcion = introducirDescripcion.getText();

        if ((this.data.getEmpresas().stream().anyMatch(empresa -> empresa.getNombre().equalsIgnoreCase(nombreUsuario)) ||!this.validarContenido(columnasExpresiones.get("nombre"),nombreUsuario)) && !nombreUsuario.isEmpty()){
            /* No vale hay empresa con ese nombre, dar error  = true*/
            System.out.println("nombre mal");
            error = true;
            errorNombre.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.nombre"));
        }

        if ((this.data.getEmpresas().stream().anyMatch(empresa -> empresa.getCorreo().equalsIgnoreCase(correoUsuario)) || !this.validarContenido(columnasExpresiones.get("correo"),correoUsuario))&& !correoUsuario.isEmpty()){
            /* No vale hay empresa con ese correo, dar error  = true*/
            System.out.println("correo mal");
            error = true;
            errorCorreo.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.correo"));
        }
        if ((!this.validarContenido(columnasExpresiones.get("departamento"),sector))&&!sector.isEmpty()){
            System.out.println("sector mal");
            error = true;
            errorDepartamento.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.departamento"));
        }
        if ((!this.validarContenido(columnasExpresiones.get("contraseña"),contraseña))&& !contraseña.isEmpty() ){
            System.out.println("contraseña mal");
            errorContraseña.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.contraseña"));
            error = true;
        }
        if ((!this.validarContenido(columnasExpresiones.get("descripcion"),descripcion))&& !descripcion.isEmpty()){
            System.out.println("contraseña mal");
            errorDescrp.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.descripcion"));
            error = true;
        }


        if (error){
            return;
        }

        if (this.empresa == null){
            if (correoUsuario.isEmpty() ||contraseña.isEmpty() || sector.isEmpty()){
                return;
            }
            Empresa empresa1 = new Empresa(ConexionBase.obtenerId("empresas"),nombreUsuario,correoUsuario,contraseña,
                    new Image("file:"+this.rutaImagenBanner),new Image("file:"+this.rutaImagenPerfil),sector,descripcion);
            ConexionBase.crearEmpresa(empresa1);
        }else {
            if (!correoUsuario.isEmpty()){
                this.empresa.setCorreo(correoUsuario);
            }
            if (!contraseña.isEmpty()){
                this.empresa.setContraseña(contraseña);
            }
            if (!descripcion.isEmpty()){
                this.empresa.setDescripcion(descripcion);
            }
            if (!sector.isEmpty()){
                this.empresa.setSector(sector);
            }
            if (!nombreUsuario.isEmpty()){
                this.empresa.setNombre(nombreUsuario);
            }

            if (this.rutaImagenPerfil != null){
                this.empresa.setImagenPerfil(new Image("file:"+this.rutaImagenPerfil));
            }
            if (this.rutaImagenBanner != null){
                this.empresa.setImagenFondo(new Image("file:"+this.rutaImagenBanner));
            }


            ConexionBase.modificarEmpresa(this.empresa);
        }
        this.introducirDescripcion.setText("");
        this.introducirNombre.setText("");
        this.introducirSector.setText("");
        this.introducirCorreo.setText("");
        this.introducirContraseña.setText("");

    }

    @FXML
    void imagen(MouseEvent event) {
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.png","*.jpg","*.jpeg"));
        File selectedFile = filechooser.showOpenDialog(null);

        if(selectedFile != null){
            String imagePath = selectedFile.getAbsolutePath();
            this.rutaImagenPerfil = imagePath;
            System.out.println(this.rutaImagenPerfil);
            this.imagenPerfil.setImage(new Image("file:"+imagePath));

        }
    }
    @FXML
    void banner(MouseEvent event) {
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.png","*.jpg","*.jpeg"));
        File selectedFile = filechooser.showOpenDialog(null);

        if(selectedFile != null){
            String imagePath = selectedFile.getAbsolutePath();
            this.rutaImagenBanner = imagePath;
            System.out.println(this.rutaImagenBanner);
            this.imagenBanner.setImage(new Image("file:"+imagePath));

        }
    }

    @FXML
    void volver(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/panelAdmin.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerPanelAdmin controllerPanelAdmin = fxmlLoader.getController();
        controllerPanelAdmin.recibirData(this.data);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }
    public void recibirData(Data data, Empresa empresa){
        this.data = data;
        this.empresa = empresa;
        this.btnAtras.setText("");
        if (this.empresa == null){
            this.introducirCorreo.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirContraseña.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirSector.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirDescripcion.setText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirNombre.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));

            this.rutaImagenPerfil = "src/main/resources/images/mejora/placeholder.jpg";
            this.rutaImagenBanner = "src/main/resources/images/mejora/empresa/prueba.jpg";

            this.imagenPerfil.setImage(new Image("file:"+this.rutaImagenPerfil));
            this.imagenBanner.setImage(new Image("file:"+this.rutaImagenBanner));
        }else {
            this.introducirCorreo.setPromptText(this.empresa.getCorreo());
            this.introducirContraseña.setPromptText(this.empresa.getContraseña());
            this.introducirSector.setPromptText(this.empresa.getSector());
            this.introducirDescripcion.setText(this.empresa.getDescripcion());
            this.introducirNombre.setPromptText(this.empresa.getNombre());
            this.imagenPerfil.setImage(this.empresa.getImagenPerfil());
            this.imagenBanner.setImage(this.empresa.getImagenFondo());
            this.rutaImagenPerfil = null;
            this.rutaImagenBanner = null;
        }
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

