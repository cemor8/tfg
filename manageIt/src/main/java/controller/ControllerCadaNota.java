package controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.CambiarIdioma;
import modelo.Data;
import modelo.Nota;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ControllerCadaNota {

    @FXML
    private ImageView imagen;

    @FXML
    private ImageView imagenOjo;

    @FXML
    private ImageView imagenPapelera;

    @FXML
    private Label labelDescripcion;

    @FXML
    private Label labelFecha;

    @FXML
    private Label labelTitulo;
    private ArrayList<Nota> notas;
    private Data data;
    private Nota nota;



    @FXML
    void borrarNota(MouseEvent event) throws IOException {
        ImageView img = (ImageView) event.getSource();
        String id = img.getId();
        this.notas.remove(this.notas.get(Integer.parseInt(id)));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/notas.fxml"), CambiarIdioma.getInstance().getBundle());
        Parent root = fxmlLoader.load();
        ControllerNotas controllerNotas = fxmlLoader.getController();
        controllerNotas.recibirData(this.data,this.notas);
        this.data.getListaControladores().getControllerContenedor().rellenarContenido(root);

    }

    @FXML
    void ver(MouseEvent event) {
        /* Cambiar el contenido del anterior controlador */

        this.data.getListaControladores().getControllerNotas().cargarNota(this.nota);

    }
    public void recibirData(Data data,Nota nota,ArrayList<Nota> notas){
        this.data = data;
        this.notas = notas;
        this.nota = nota;
        this.labelDescripcion.setText(this.nota.getDescripcion());
        this.labelTitulo.setText(this.nota.getTitulo());
        SimpleDateFormat fechaCreacion = new SimpleDateFormat("dd-MM-yyyy");
        String mostrarCreacion = fechaCreacion.format(this.nota.getFechaCreacion());
        this.labelFecha.setText(mostrarCreacion);
        this.imagenPapelera.setId(String.valueOf(this.notas.indexOf(this.nota)));
    }

}

