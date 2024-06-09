package controller.mejora;

import controller.ControllerContenedor;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.CambiarIdioma;
import modelo.ConexionBase;
import modelo.Data;
import modelo.Empresa;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerRegistro {

    @FXML
    private MFXButton btnLogin;

    @FXML
    private MFXButton btnLogin111;

    @FXML
    private MFXPasswordField introducirContraseña;

    @FXML
    private MFXTextField introducirCorreo;

    @FXML
    private MFXTextField introducirNombre;

    @FXML
    private MFXTextField introducirSector;
    private Data data;
    Map<String, String> columnasExpresiones = new HashMap<String, String>() {
        {
            put("correo", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
            put("nombre", "^.{5,15}$");
            put("sector", "^.{5,10}$");
            put("contraseña", "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        }

    };

    @FXML
    void registrarse(MouseEvent event) throws IOException {
        /** Comprobar datos y registrarse*/
        boolean error = false;
        String nombreEmpresa = introducirNombre.getText();
        String correoEmpresa = introducirCorreo.getText();
        String sectorEmpresa = introducirSector.getText();
        String contraseña = introducirContraseña.getText();

        if (this.data.getEmpresas().stream().anyMatch(empresa -> empresa.getNombre().equalsIgnoreCase(nombreEmpresa)) ||!this.validarContenido(columnasExpresiones.get("nombre"),nombreEmpresa)){
            /* No vale hay empresa con ese nombre, dar error  = true*/
            System.out.println("correo mal");
            error = true;
        }
        if (this.data.getEmpresas().stream().anyMatch(empresa -> empresa.getCorreo().equalsIgnoreCase(correoEmpresa)) || !this.validarContenido(columnasExpresiones.get("correo"),correoEmpresa)){
            /* No vale hay empresa con ese correo, dar error  = true*/
            System.out.println("nombre mal");
            error = true;
        }
        if (!this.validarContenido(columnasExpresiones.get("sector"),sectorEmpresa)){
            System.out.println("sector mal");
            error = true;
        }
        if (!this.validarContenido(columnasExpresiones.get("contraseña"),contraseña)){
            System.out.println("contraseña mal");
            error = true;
        }

        if (error){
            return;
        }

        /*Crear empresa*/
        /* poner imagenes por defecto, meter empresa en base de datos */

        //Integer id = ConexionBase.obtenerId("empresas");
        Integer id = 1;
        Empresa empresa = new Empresa(id,nombreEmpresa,correoEmpresa,contraseña,new Image("file:"+"src/main/resources/images/mejora/empresa/prueba.jpg"),new Image("file:"+"src/main/resources/images/usuarios/persona.png"),sectorEmpresa,"Descripcion por defecto para nueva empresa, modificame!!");
        this.data.getEmpresas().add(empresa);

        this.data.setEmpresaSeleccionada(empresa);

        /*Poner vista de empresa*/

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/contenedor.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerContenedor controllerContenedor = fxmlLoader.getController();
        controllerContenedor.recibirData(this.data,true,false);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("file:src/main/resources/images/menuLateral/logo.png"));
        stage.setTitle("Panel");
        stage.setScene(scene);
        stage.show();

        MFXButton button = (MFXButton) event.getSource();
        Stage stage1 =(Stage) button.getScene().getWindow();
        stage1.close();


    }

    @FXML
    void volverLogin(MouseEvent event) throws IOException {
        /* Volver al login */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/mejora/loginempresa.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        LoginEmpresa loginEmpresa = fxmlLoader.getController();
        loginEmpresa.recibirData(this.data);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("file:src/main/resources/images/menuLateral/logo.png"));
        stage.setTitle("Login de Empresa");
        stage.setScene(scene);
        stage.show();

        MFXButton button = (MFXButton) event.getSource();
        Stage stage1 =(Stage) button.getScene().getWindow();
        stage1.close();
    }
    public void recibirData(Data data){
        this.data = data;
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

