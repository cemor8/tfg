package controller.mejora;

import controller.ControllerVistaConfiguracion;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.css.PseudoClass;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerConfiguracionEmpresa {

    @FXML
    private MFXButton btnClaro;

    @FXML
    private MFXButton btnEspañol;

    @FXML
    private MFXButton btnIngles;
    @FXML
    private Label errorImagen2;
    @FXML
    private ImageView imgBanner;

    @FXML
    private MFXButton btnOscuro;

    @FXML
    private Label errorApellidos;

    @FXML
    private Label errorBio;

    @FXML
    private Label errorImagen;

    @FXML
    private Label errorNombre;

    @FXML
    private Label errorNombre1;

    @FXML
    private Label errorNombre2;

    @FXML
    private ImageView imgEditar;

    @FXML
    private ImageView imgPersona;

    @FXML
    private TextArea introducirBio;

    @FXML
    private MFXTextField introducirContraseña;

    @FXML
    private MFXTextField introducirCorreo;

    @FXML
    private MFXTextField introducirNombre2;

    @FXML
    private MFXTextField introducirSector;
    public String rutaImagen;
    public String rutaImagen2;
    private Data data;
    Map<String, String> columnasExpresiones = new HashMap<String, String>() {
        {
            put("descripcion", "^.{15,100}$");
            put("nombre", "^.{5,25}$");
            put("correo", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
            put("sector", "^.{5,20}$");
            put("contraseña", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        }

    };

    /**
     * Método que se encarga de pedir una imagen para modificarla en el banner de la empresa
     * @param event
     */
    @FXML
    void editarBanner(MouseEvent event) {
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.png","*.jpg","*.jpeg"));
        File selectedFile = filechooser.showOpenDialog(null);

        if(selectedFile != null){
            String imagePath = selectedFile.getAbsolutePath();
            this.rutaImagen2 = imagePath;

            this.imgBanner.setImage(new Image("file:"+rutaImagen2));

        }
    }

    /**
     * Método que guarda la imagen seleccionada para el banner
     * @param event
     * @throws IOException
     */
    @FXML
    void guardarBanner(MouseEvent event) throws IOException {
        if (this.rutaImagen2!= null && !this.rutaImagen2.equalsIgnoreCase("")){
            this.data.getEmpresaSeleccionada().setImagenFondo(new Image("file:"+this.rutaImagen2));

        }
        ConexionBase.modificarEmpresa(this.data.getEmpresaSeleccionada());
    }

    /**
     * Método que cambia el modo a claro
     * @param event
     */
    @FXML
    void cambiarClaro(MouseEvent event) {
        if (!this.data.isOscuro()){
            return;
        }
        this.btnOscuro.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
        this.btnClaro.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.data.setOscuro(false);
        this.data.getListaControladores().getControllerContenedor().meterEstilo("/styles/claro.css");
    }

    /**
     * Método que cambia el idioma a español
     * @param event
     * @throws IOException
     */
    @FXML
    void cambiarEspañol(MouseEvent event) throws IOException {
        if (this.data.isEspañol()){
            return;
        }
        this.btnEspañol.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.btnIngles.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
        this.data.setEspañol(true);

        CambiarIdioma.getInstance().cargarIdioma("es", "ES");
        Locale.setDefault(new Locale("es"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/configuracionEmpresa.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        ControllerConfiguracionEmpresa controllerConfiguracionEmpresa = fxmlLoader.getController();
        controllerConfiguracionEmpresa.recibirData(this.data);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);

        if (this.rutaImagen == null || this.rutaImagen.equalsIgnoreCase("")){
            //controllerVistaConfiguracion.imgPersona.setImage(new Image("file:"+this.data.getCurrentUser().getRutaImagen()));
            controllerConfiguracionEmpresa.imgPersona.setImage(this.data.getEmpresaSeleccionada().getImagenPerfil());
        }else {
            controllerConfiguracionEmpresa.imgPersona.setImage(new Image("file:"+rutaImagen));
        }
        if (this.introducirBio.getText().isEmpty()){
            controllerConfiguracionEmpresa.introducirBio.setText(this.data.getEmpresaSeleccionada().getDescripcion());
        }else{
            controllerConfiguracionEmpresa.introducirBio.setText(this.introducirBio.getText());
        }
        if (this.introducirNombre2.getText().isEmpty()){
            controllerConfiguracionEmpresa.introducirNombre2.setPromptText(this.data.getEmpresaSeleccionada().getNombre());
        }else{
            controllerConfiguracionEmpresa.introducirNombre2.setText(this.introducirNombre2.getText());
        }
        if (this.introducirCorreo.getText().isEmpty()){
            controllerConfiguracionEmpresa.introducirCorreo.setPromptText(this.data.getEmpresaSeleccionada().getCorreo());
        }else{
            controllerConfiguracionEmpresa.introducirCorreo.setText(this.introducirCorreo.getText());
        }
        this.data.getListaControladores().getControllerContenedor().cargarLateralEmpresa();
    }

    /**
     * Método que cambia el idioma a ingles
     * @param event
     * @throws IOException
     */
    @FXML
    void cambiarIngles(MouseEvent event) throws IOException {
        if (!this.data.isEspañol()){
            return;
        }
        this.btnEspañol.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
        this.btnIngles.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.data.setEspañol(false);
        CambiarIdioma.getInstance().cargarIdioma("en", "US");
        Locale.setDefault(new Locale("en"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/configuracionEmpresa.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        ControllerConfiguracionEmpresa controllerConfiguracionEmpresa = fxmlLoader.getController();
        controllerConfiguracionEmpresa.recibirData(this.data);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);

        if (this.rutaImagen == null || this.rutaImagen.equalsIgnoreCase("")){
            //controllerVistaConfiguracion.imgPersona.setImage(new Image("file:"+this.data.getCurrentUser().getRutaImagen()));
            controllerConfiguracionEmpresa.imgPersona.setImage(this.data.getEmpresaSeleccionada().getImagenPerfil());
        }else {
            controllerConfiguracionEmpresa.imgPersona.setImage(new Image("file:"+rutaImagen));
        }
        if (this.introducirBio.getText().isEmpty()){
            controllerConfiguracionEmpresa.introducirBio.setText(this.data.getEmpresaSeleccionada().getDescripcion());
        }else{
            controllerConfiguracionEmpresa.introducirBio.setText(this.introducirBio.getText());
        }
        if (this.introducirNombre2.getText().isEmpty()){
            controllerConfiguracionEmpresa.introducirNombre2.setPromptText(this.data.getEmpresaSeleccionada().getNombre());
        }else{
            controllerConfiguracionEmpresa.introducirNombre2.setText(this.introducirNombre2.getText());
        }
        if (this.introducirCorreo.getText().isEmpty()){
            controllerConfiguracionEmpresa.introducirCorreo.setPromptText(this.data.getEmpresaSeleccionada().getCorreo());
        }else{
            controllerConfiguracionEmpresa.introducirCorreo.setText(this.introducirCorreo.getText());
        }
        this.data.getListaControladores().getControllerContenedor().cargarLateralEmpresa();
    }

    /**
     * método que cambia el tema a oscuro
     * @param event
     */
    @FXML
    void cambiarOscuro(MouseEvent event) {
        if (this.data.isOscuro()){
            return;
        }
        this.btnOscuro.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        this.btnClaro.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
        this.data.setOscuro(true);
        this.data.getListaControladores().getControllerContenedor().meterEstilo("/styles/oscuro.css");
    }

    /**
     * método que pide una imagen de perfil para cambiarla
     * @param event
     */
    @FXML
    void editarImagen(MouseEvent event) {
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.png","*.jpg","*.jpeg"));
        File selectedFile = filechooser.showOpenDialog(null);

        if(selectedFile != null){
            String imagePath = selectedFile.getAbsolutePath();
            this.rutaImagen = imagePath;

            this.imgPersona.setImage(new Image("file:"+rutaImagen));

        }
    }

    /**
     * Método que se encarga de cambiar la contraseña
     * @param event
     * @throws IOException
     */
    @FXML
    void guardarContraseña(MouseEvent event) throws IOException {
        if (!validarContenido(this.columnasExpresiones.get("contraseña"), this.introducirContraseña.getText())) {
            this.errorBio.setText("Bio de 15 - 100 caracteres");
            return;
        }
        this.data.getEmpresaSeleccionada().setContraseña(this.introducirContraseña.getText());
        ConexionBase.modificarEmpresa(this.data.getEmpresaSeleccionada());
    }

    /**
     * metodo que guarda el correo
     * @param event
     * @throws IOException
     */
    @FXML
    void guardarCorreo(MouseEvent event) throws IOException {
        if (!validarContenido(this.columnasExpresiones.get("correo"), this.introducirCorreo.getText())) {
            this.errorBio.setText("Bio de 15 - 100 caracteres");
            return;
        }
        this.data.getEmpresaSeleccionada().setCorreo(this.introducirCorreo.getText());
        ConexionBase.modificarEmpresa(this.data.getEmpresaSeleccionada());
    }

    /**
     * Método que guarda el sector
     * @param event
     * @throws IOException
     */
    @FXML
    void guardarSector(MouseEvent event) throws IOException {
        if (!validarContenido(this.columnasExpresiones.get("sector"), this.introducirSector.getText())) {
            this.errorBio.setText("Bio de 15 - 100 caracteres");
            return;
        }
        this.data.getEmpresaSeleccionada().setSector(this.introducirSector.getText());
        ConexionBase.modificarEmpresa(this.data.getEmpresaSeleccionada());
    }

    /**
     * Método que guarda la bio
     * @param event
     * @throws IOException
     */
    @FXML
    void guardarBio(MouseEvent event) throws IOException {
        if (!validarContenido(this.columnasExpresiones.get("descripcion"), this.introducirBio.getText())) {
            this.errorBio.setText("Bio de 15 - 100 caracteres");
            return;
        }
        this.data.getEmpresaSeleccionada().setDescripcion(this.introducirBio.getText());
        ConexionBase.modificarEmpresa(this.data.getEmpresaSeleccionada());
    }

    /**
     * Método que guarda la imagen
     * @param event
     * @throws IOException
     */
    @FXML
    void guardarImagen(MouseEvent event) throws IOException {
        if (this.rutaImagen!= null && !this.rutaImagen.equalsIgnoreCase("")){
            this.data.getEmpresaSeleccionada().setImagenPerfil(new Image("file:"+this.rutaImagen));
            this.data.getListaControladores().getControllerContenedor().cargarSuperiorEmpresa();

        }
        ConexionBase.modificarEmpresa(this.data.getEmpresaSeleccionada());
    }

    /**
     * Método que guarda el nombre
     * @param event
     * @throws IOException
     */
    @FXML
    void guardarNombre(MouseEvent event) throws IOException {
        if (!validarContenido(this.columnasExpresiones.get("nombre"), this.introducirNombre2.getText())) {
            this.errorNombre.setText("Nombre incorrecto");
            return;
        }
        this.data.getEmpresaSeleccionada().setNombre(this.introducirNombre2.getText());
        this.data.getListaControladores().getControllerContenedor().cargarSuperiorEmpresa();
        ConexionBase.modificarEmpresa(this.data.getEmpresaSeleccionada());
    }
    /**
     * Método que se encarga de recibir informacion
     * @param data
     */
    public void recibirData(Data data){
        this.data = data;
        this.inicializar();
    }
    /**
     * Método que se encarga de inicializar todos los datos en la configuracion
     */
    public void inicializar(){
        //this.imgPersona.setImage(new Image("file:"+this.data.getCurrentUser().getRutaImagen()));
        this.imgPersona.setImage(this.data.getEmpresaSeleccionada().getImagenPerfil());
        this.introducirBio.setText(this.data.getEmpresaSeleccionada().getDescripcion());
        this.introducirNombre2.setPromptText(this.data.getEmpresaSeleccionada().getNombre());
        this.introducirCorreo.setPromptText(this.data.getEmpresaSeleccionada().getCorreo());
        this.introducirContraseña.setPromptText(this.data.getEmpresaSeleccionada().getContraseña());
        this.introducirSector.setPromptText(this.data.getEmpresaSeleccionada().getSector());
        this.imgBanner.setImage(this.data.getEmpresaSeleccionada().getImagenFondo());


        if (this.data.isEspañol()){
            this.btnEspañol.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
            this.btnIngles.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
        }else {
            this.btnEspañol.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
            this.btnIngles.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
        }
        if (this.data.isOscuro()){
            this.btnOscuro.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
            this.btnClaro.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
        }else{
            this.btnOscuro.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),false);
            this.btnClaro.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),true);
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


