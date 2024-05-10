package controller;

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
import modelo.Data;
import modelo.Usuario;

import java.io.IOException;
import java.util.Optional;

public class ControllerLogin {
    @FXML
    private MFXButton btnLogin;

    @FXML
    private MFXPasswordField introducirContraseña;

    @FXML
    private MFXTextField introducirCorreo;
    private Data data;

    /**
     * Método que se encarga de logear a un usuario en la app
     * @param event
     * @throws IOException
     */
    @FXML
    void iniciar(MouseEvent event) throws IOException {

        Optional<Usuario> usuarioOptional = this.data.getUsuarios().stream().filter(usuario -> usuario.getCorreo().equalsIgnoreCase(introducirCorreo.getText()) &&
                usuario.getContraseña().equals(this.introducirContraseña.getText())).findAny();



        if(usuarioOptional.isPresent()){
            this.data.setCurrentUser(usuarioOptional.get());
        }else {
            return;
        }

        this.data.setCurrentUser(usuarioOptional.get());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/contenedor.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerContenedor controllerContenedor = fxmlLoader.getController();
        controllerContenedor.recibirData(this.data);
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
    public void recibirData(Data data){
        this.data = data;
    }
}