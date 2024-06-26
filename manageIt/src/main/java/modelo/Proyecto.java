package modelo;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Date;

public class Proyecto {
    private String nombre;
    private String cliente;
    private String rutaImagen;
    private Integer id;
    private String estado;
    private String descripcion;
    private Image imagen;
    private Date fechaCreacion;
    private Date fechaEntrega;
    private Usuario jefeProyecto;
    private ArrayList<Tarea> tareas;
    private ArrayList<Nota> notas;
    private ArrayList<Usuario> personasAsignadas;
    private String rutaVideo;

    public Proyecto(String nombre, String cliente, String rutaImagen, String estado, String descripcion, Date fechaCreacion, Date fechaEntrega, Usuario jefeProyecto, ArrayList<Tarea> tareas, ArrayList<Nota> notas, ArrayList<Usuario> personasAsignadas, String rutaVideo) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.rutaImagen = rutaImagen;
        this.estado = estado;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrega = fechaEntrega;
        this.jefeProyecto = jefeProyecto;
        this.tareas = tareas;
        this.notas = notas;
        this.personasAsignadas = personasAsignadas;
        this.rutaVideo = rutaVideo;
    }

    public Integer getId() {
        return id;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public ArrayList<Nota> getNotas() {
        return notas;
    }

    public ArrayList<Usuario> getPersonasAsignadas() {
        return personasAsignadas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRutaVideo() {
        return rutaVideo;
    }

    public void setRutaVideo(String rutaVideo) {
        this.rutaVideo = rutaVideo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public Usuario getJefeProyecto() {
        return jefeProyecto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setJefeProyecto(Usuario jefeProyecto) {
        this.jefeProyecto = jefeProyecto;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }

    public void setPersonasAsignadas(ArrayList<Usuario> personasAsignadas) {
        this.personasAsignadas = personasAsignadas;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "nombre='" + nombre + '\'' +
                ", cliente='" + cliente + '\'' +
                ", rutaImagen='" + rutaImagen + '\'' +
                ", id=" + id +
                ", estado='" + estado + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen=" + imagen +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaEntrega=" + fechaEntrega +
                ", jefeProyecto=" + jefeProyecto +
                ", tareas=" + tareas +
                ", notas=" + notas +
                ", personasAsignadas=" + personasAsignadas +
                ", rutaVideo='" + rutaVideo + '\'' +
                '}';
    }
}
