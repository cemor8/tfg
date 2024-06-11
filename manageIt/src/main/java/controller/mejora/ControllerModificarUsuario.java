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
import modelo.CambiarIdioma;
import modelo.ConexionBase;
import modelo.Data;
import modelo.Usuario;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerModificarUsuario {

    @FXML
    private MFXButton btnAtras;

    @FXML
    private MFXTextField introducirPuesto;
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
    private MFXTextField introducirDepartamento;

    @FXML
    private TextArea introducirDescripcion;

    @FXML
    private MFXTextField introducirNombre;

    @FXML
    private Label labelTextoCrearProyecto;
    private String rutaImagen;
    private Data data;
    private Usuario usuario;
    Map<String, String> columnasExpresiones = new HashMap<String, String>() {
        {
            put("descripcion", "^.{15,100}$");
            put("nombre", "^.{5,25}$");
            put("correo", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
            put("departamento", "^.{5,20}$");
            put("contraseña", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        }

    };

    @FXML
    void crear(MouseEvent event) throws IOException {
        boolean error = false;

        String nombreUsuario = introducirNombre.getText();
        String correoUsuario= introducirCorreo.getText();
        String departatamento = introducirDepartamento.getText();
        String contraseña = introducirContraseña.getText();
        String descripcion = introducirDescripcion.getText();
        String puesto = introducirPuesto.getText();

        if ((this.data.getEmpresas().stream().anyMatch(empresa -> empresa.getNombre().equalsIgnoreCase(nombreUsuario)) ||!this.validarContenido(columnasExpresiones.get("nombre"),nombreUsuario)) && !nombreUsuario.isEmpty()){

            System.out.println("nombre mal");
            error = true;
            errorNombre.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.nombre"));
        }

        if ((this.data.getEmpresas().stream().anyMatch(empresa -> empresa.getCorreo().equalsIgnoreCase(correoUsuario)) || !this.validarContenido(columnasExpresiones.get("correo"),correoUsuario))&& !correoUsuario.isEmpty()){

            System.out.println("correo mal");
            error = true;
            errorCorreo.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.correo"));
        }
        if ((this.data.getUsuarios().stream().anyMatch(empresa -> empresa.getCorreo().equalsIgnoreCase(correoUsuario)) || !this.validarContenido(columnasExpresiones.get("correo"),correoUsuario))&& !correoUsuario.isEmpty()){

            System.out.println("correo mal");
            error = true;
            errorCorreo.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.correo"));
        }
        if (correoUsuario.equalsIgnoreCase("admin")){

            System.out.println("correo mal");
            error = true;
            errorCorreo.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.correo"));
        }
        if ((!this.validarContenido(columnasExpresiones.get("departamento"),departatamento))&&!departatamento.isEmpty()){
            System.out.println("departamento mal");
            error = true;
            errorDepartamento.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.departamento"));
        }
        if ((!this.validarContenido(columnasExpresiones.get("departamento"),puesto))&&!puesto.isEmpty()){
            System.out.println("puesto mal");
            error = true;
            errorPuesto.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.sector"));
        }
        if ((!this.validarContenido(columnasExpresiones.get("contraseña"),contraseña)) && !contraseña.isEmpty() ){
            System.out.println("contraseña mal");
            errorContraseña.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.contraseña"));
            error = true;
        }
        if ((!this.validarContenido(columnasExpresiones.get("descripcion"),descripcion))&& !descripcion.isEmpty()){
            System.out.println("descripcion mal");
            errorDescrp.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.descripcion"));
            error = true;
        }


        if (error){
            return;
        }

        if (this.usuario == null){
            /*Comprobar correo del usuarios*/
            if (correoUsuario.isEmpty() ||contraseña.isEmpty() || puesto.isEmpty() ||departatamento.isEmpty()){
                errorCorreo.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.correo"));
                errorPuesto.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.sector"));
                errorContraseña.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.contraseña"));
                errorPuesto.setText(CambiarIdioma.getInstance().getBundle().getString("errorU.sector"));
                return;
            }
            Usuario usuario1 = new Usuario(correoUsuario,nombreUsuario,"",contraseña,descripcion,puesto,"",new ArrayList<>(),new ArrayList<>());
            Integer id = ConexionBase.obtenerId("usuarios");
            usuario1.setId(id);
            usuario1.setIdEmpresa(this.data.getEmpresaSeleccionada().getId());
            usuario1.setDepartamento(departatamento);
            if (this.rutaImagen != null){
                usuario1.setImagen(new Image("file:"+this.rutaImagen));
            }
            this.data.getUsuarios().add(usuario1);
            ConexionBase.crearUsuario(usuario1);
            this.rutaImagen = "src/main/resources/images/mejora/placeholder.jpg";
            this.imagenUsuario.setImage(new Image("file:"+this.rutaImagen));
        }else {
            if (!correoUsuario.isEmpty()){
                this.usuario.setCorreo(correoUsuario);
            }
            if (!contraseña.isEmpty()){
                this.usuario.setContraseña(contraseña);
            }
            if (!descripcion.isEmpty()){
                this.usuario.setDescripcion(descripcion);
            }
            if (!departatamento.isEmpty()){
                this.usuario.setDepartamento(departatamento);
            }
            if (!puesto.isEmpty()){
                this.usuario.setPuesto(puesto);
            }
            if (!nombreUsuario.isEmpty()){
                this.usuario.setNombre(nombreUsuario);
            }

            if (this.rutaImagen != null){
                this.usuario.setImagen(new Image("file:"+this.rutaImagen));
            }


           ConexionBase.modificarUsuario(this.usuario);
        }
        this.introducirDescripcion.setText("");
        this.introducirNombre.setText("");
        this.introducirDepartamento.setText("");
        this.introducirCorreo.setText("");
        this.introducirContraseña.setText("");
        this.introducirPuesto.setText("");

    }

    @FXML
    void imagen(MouseEvent event) {
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.png","*.jpg","*.jpeg"));
        File selectedFile = filechooser.showOpenDialog(null);

        if(selectedFile != null){
            String imagePath = selectedFile.getAbsolutePath();
            this.rutaImagen = imagePath;
            System.out.println(this.rutaImagen);
            this.imagenUsuario.setImage(new Image("file:"+imagePath));

        }
    }
    @FXML
    void volver(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/tablaUsuariosEmpresa.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerTablaUsuarios controllerTablaUsuarios = fxmlLoader.getController();
        controllerTablaUsuarios.recibirData(this.data);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
    }
    public void recibirData(Data data, Usuario usuario){
        this.data = data;
        this.btnAtras.setText("");

        this.usuario = usuario;

        if (this.usuario == null){
            this.introducirCorreo.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirContraseña.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirDepartamento.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirDescripcion.setText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirNombre.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.introducirPuesto.setPromptText(CambiarIdioma.getInstance().getBundle().getString("login.prompt"));
            this.rutaImagen = "src/main/resources/images/mejora/placeholder.jpg";
            this.imagenUsuario.setImage(new Image("file:"+this.rutaImagen));
        }else {
            this.introducirCorreo.setPromptText(this.usuario.getCorreo());
            this.introducirContraseña.setPromptText(this.usuario.getContraseña());
            this.introducirDepartamento.setPromptText(this.usuario.getDepartamento());
            this.introducirDescripcion.setText(this.usuario.getDescripcion());
            this.introducirNombre.setPromptText(this.usuario.getNombre());
            this.imagenUsuario.setImage(this.usuario.getImagen());
            this.rutaImagen = null;
        }
        System.out.println(this.rutaImagen);
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

