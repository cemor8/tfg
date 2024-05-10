package modelo;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Empresa {
    private Integer id;
    private String nombre;
    private String correo;
    private String contraseña;
    private Image imagenFondo;
    private Image imagenPerfil;
    private String sector;
    private String descripcion;

    public Empresa(Integer id, String nombre, String correo, String contraseña, Image imagenFondo, Image imagenPerfil, String sector, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.imagenFondo = imagenFondo;
        this.imagenPerfil = imagenPerfil;
        this.sector = sector;
        this.descripcion = descripcion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setImagenFondo(Image imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    public void setImagenPerfil(Image imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Image getImagenFondo() {
        return imagenFondo;
    }

    public Image getImagenPerfil() {
        return imagenPerfil;
    }

    public String getSector() {
        return sector;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
