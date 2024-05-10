package modelo;

import java.util.ArrayList;
import java.util.Date;

public class Tarea {
    private String nombre;
    private String estado;
    private String campo;
    private String descripcion;
    private Date fechaCreacion;
    private Date fechaEntrega;
    private String rutaimagen;
    private ArrayList<Nota> notas;
    private ArrayList<Usuario> personasAsignadas;
    private Usuario creador;
    private String rutaVideo;

    public Tarea(String nombre, String estado, String campo, String descripcion, Date fechaCreacion, Date fechaEntrega, String rutaimagen, ArrayList<Nota> notas, ArrayList<Usuario> personasAsignadas, Usuario creador,String rutaVideo) {
        this.nombre = nombre;
        this.rutaVideo = rutaVideo;
        this.estado = estado;
        this.campo = campo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrega = fechaEntrega;
        this.rutaimagen = rutaimagen;
        this.notas = notas;
        this.personasAsignadas = personasAsignadas;
        this.creador = creador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }

    public Usuario getCreador() {
        return creador;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public String getRutaVideo() {
        return rutaVideo;
    }

    public void setRutaVideo(String rutaVideo) {
        this.rutaVideo = rutaVideo;
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

    public String getRutaimagen() {
        return rutaimagen;
    }

    public ArrayList<Nota> getNotas() {
        return notas;
    }

    public ArrayList<Usuario> getPersonasAsignadas() {
        return personasAsignadas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void setRutaimagen(String rutaimagen) {
        this.rutaimagen = rutaimagen;
    }

    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }

    public void setPersonasAsignadas(ArrayList<Usuario> personasAsignadas) {
        this.personasAsignadas = personasAsignadas;
    }
}
