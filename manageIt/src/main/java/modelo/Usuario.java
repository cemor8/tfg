package modelo;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Usuario {
    private String correo;
    private Integer id;
    private String nombre;
    private String apellidos;
    private String contraseña;
    private String descripcion;
    private String puesto;
    private Integer idEmpresa;
    private String departamento;
    private String rutaImagen;
    private ArrayList<Usuario> contactos;
    private ArrayList<Nota> notas;
    private Image imagen;

    public Usuario(String correo, String nombre, String apellidos, String contraseña, String descripcion, String puesto, String rutaImagen, ArrayList<Usuario> contactos, ArrayList<Nota> notas) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.descripcion = descripcion;
        this.puesto = puesto;
        this.rutaImagen = rutaImagen;
        this.contactos = contactos;
        this.notas = notas;
    }

    public void setContactos(ArrayList<Usuario> contactos) {
        this.contactos = contactos;
    }

    public ArrayList<Usuario> getContactos() {
        return contactos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getApellidos() {
        return apellidos;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public ArrayList<Nota> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public Image getImagen() {
        return imagen;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "correo='" + correo + '\'' +
                ", id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", puesto='" + puesto + '\'' +
                ", IDempresa=" + idEmpresa +
                ", departamento='" + departamento + '\'' +
                ", rutaImagen='" + rutaImagen + '\'' +
                ", contactos=" + contactos +
                ", notas=" + notas +
                ", imagen=" + imagen +
                '}';
    }
}
