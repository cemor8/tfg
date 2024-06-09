package controller.mejora;

import controller.ControllerVistaCadaContacto;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import modelo.CambiarIdioma;
import modelo.Data;
import modelo.Usuario;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerEmpleados {

    @FXML
    private MFXScrollPane scroll;
    private HBox hBoxMeter = new HBox();
    private Data data;
    private ArrayList<Usuario> empleados = new ArrayList<>();
    public void inicializar(){
        VBox vBox = new VBox();
        vBox.getStyleClass().add("vboxContactos");
        VBox vBox2 = new VBox();
        vBox2.getStyleClass().add("vboxContactos");
        int i = 1;
        HBox hBox = new HBox(10);
        for (Usuario empleado : empleados){
            hBox.getStyleClass().add("cartaContacto");

            //Image image = new Image("file:"+contacto.getRutaImagen());
            Image image = empleado.getImagen();
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.getStyleClass().add("imagenContacto");
            imageView.setPreserveRatio(false);
            Circle clip = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, imageView.getFitWidth() / 2);
            imageView.setClip(clip);




            Label nameLabel = new Label(empleado.getNombre()+" "+empleado.getApellidos());
            nameLabel.getStyleClass().add("nombreContactoCarta");


            ImageView imgVer = new ImageView();
            imgVer.getStyleClass().add("ojoVistaCadaProyecto");
            imgVer.setFitHeight(30);
            imgVer.setFitWidth(30);
            imgVer.setOnMouseClicked(this::verContacto);
            imgVer.setId(String.valueOf(this.empleados.indexOf(empleado)));
            HBox.setMargin(nameLabel,new Insets(0,50,0,65));
            HBox.setMargin(imgVer,new Insets(0,10,0,0));
            hBox.getChildren().addAll(imageView,nameLabel,imgVer);


            i++;
            if (i % 2 == 0){
                vBox.getChildren().add(hBox);

            }else {
                vBox2.getChildren().add(hBox);
            }
            hBox = new HBox(10);
            hBox.setPadding(new Insets(15, 12, 15, 12));
        }
        hBoxMeter.getChildren().addAll(vBox,vBox2);
        this.scroll.setContent(hBoxMeter);
    }
    /**
     * Método que se encarga de cargar la vista detallada de un contacto
     * @param event
     */
    private void verContacto(MouseEvent event) {
        ImageView img = (ImageView) event.getSource();
        int posicion = Integer.parseInt(img.getId());
        Usuario contacto = this.empleados.get(posicion);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/vistaCadaContacto.fxml"), CambiarIdioma.getInstance().getBundle());
            Parent root = fxmlLoader.load();
            ControllerVistaCadaContacto controllerVistaCadaContacto = fxmlLoader.getController();
            controllerVistaCadaContacto.recibirData(this.data,contacto,false,this.empleados,this.empleados);
            this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);
        }catch (IOException err){
            System.out.println(err.getMessage());
        }
    }

    /**
     * Método que se encarga de recibir informacion
     */
    public void recibirData(Data data, ArrayList<Usuario> empleados){
        this.data = data;
        this.empleados = empleados;
        this.inicializar();


    }

}

