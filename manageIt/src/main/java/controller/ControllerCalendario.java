package controller;

import atlantafx.base.controls.Calendar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.DateCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import modelo.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControllerCalendario {

    @FXML
    private Calendar calendario;
    private Data data;
    private VBox vBox;
    private ArrayList<Proyecto> proyectosRecorrer;

    @FXML
    private MFXScrollPane scroll;

    /**
     * Método que se encarga de recibir informacion y comprobar las entregas
     * @param data clase con informacion
     */
    public void recibirData(Data data){
        this.data = data;
        calendario.setValue(LocalDate.now());
        try {
            this.mostrarContenidoDia(null);
        }catch (IOException err){
            System.out.println(err.getMessage());
        }
        this.comprobarEntregas();


    }

    /**
     * Método que se encarga de comprobar las entregas y creaciones que han sucedido en un día,
     * si el día esta marcado en el calendario, se indica, las entregas en color rojo y las creaciones en color azul.
     */
    public void comprobarEntregas(){
        calendario.setDayCellFactory(new Callback<Calendar, DateCell>() {
            @Override
            public DateCell call(Calendar param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        // Marca la fecha de mañana de rojo
                        for (Proyecto proyecto : proyectosRecorrer){
                            if (proyecto.getFechaEntrega().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(item)){
                                this.setStyle("-fx-background-color: #C1402E;");
                            }else if(proyecto.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(item)){
                                this.setStyle("-fx-background-color: #28939A;");
                            } else {
                                for (Tarea tarea : proyecto.getTareas()){
                                    if (tarea.getFechaEntrega().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(item)){
                                        this.setStyle("-fx-background-color: #C1402E;");
                                        break;
                                    }else if(tarea.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(item)){
                                        this.setStyle("-fx-background-color: #28939A;");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                };
            }
        });
    }

    /**
     * Método que se encarga de cargar el contenido seleccionado para un día determinado
     * @param event
     * @throws IOException
     */
    @FXML
    void mostrarContenidoDia(MouseEvent event) throws IOException {
        this.scroll.setContent(null);
        LocalDate localDate = calendario.getValue();
        this.vBox = new VBox();

        ArrayList<Proyecto> proyectos = new ArrayList<>();
        for (Proyecto proyecto : this.data.getProyectos()) {
            if (proyecto.getJefeProyecto().getCorreo().equalsIgnoreCase(this.data.getCurrentUser().getCorreo())){
                proyectos.add(proyecto);
                continue;
            }
            for (Usuario usuario : proyecto.getPersonasAsignadas()) {
                if (usuario.getCorreo().equals(this.data.getCurrentUser().getCorreo())) {
                    proyectos.add(proyecto);
                    break;
                }
            }
        }
        this.proyectosRecorrer = proyectos;
        for (Proyecto proyecto : proyectosRecorrer){
            LocalDate fechaCreacion = proyecto.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaEntrega = proyecto.getFechaEntrega().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            if (fechaEntrega.equals(localDate) || fechaCreacion.equals(localDate)){
                AnchorPane anchorPane = new AnchorPane();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/cadaProyecto.fxml"), CambiarIdioma.getInstance().getBundle());
                Parent root = fxmlLoader.load();
                ControllerCadaProyecto controllerCadaProyecto = fxmlLoader.getController();
                controllerCadaProyecto.recibirData(this.data,proyecto,proyectos);
                anchorPane.getChildren().setAll(root);
                vBox.getChildren().add(anchorPane);
                VBox.setMargin(anchorPane, new Insets(10, 0, 20, 20));
                this.scroll.setContent(vBox);
            }else {
                for (Tarea tarea : proyecto.getTareas()){
                    LocalDate fechaEntregaTarea = tarea.getFechaEntrega().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaCreacionTarea = tarea.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (fechaEntregaTarea.equals(localDate) ||fechaCreacionTarea.equals(localDate)){
                        AnchorPane anchorPane = new AnchorPane();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/cadaProyecto.fxml"), CambiarIdioma.getInstance().getBundle());
                        Parent root = fxmlLoader.load();
                        ControllerCadaProyecto controllerCadaProyecto = fxmlLoader.getController();
                        controllerCadaProyecto.recibirData(this.data,proyecto,proyectos);
                        anchorPane.getChildren().setAll(root);
                        vBox.getChildren().add(anchorPane);
                        VBox.setMargin(anchorPane, new Insets(10, 0, 20, 20));
                        this.scroll.setContent(vBox);
                        break;

                    }
                }
            }
        }
    }

}

