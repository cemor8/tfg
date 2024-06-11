package modelo;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Data {
    private ArrayList<Nota> notas;
    private ArrayList<Proyecto> proyectos;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Tarea> tareas;
    private ArrayList<Empresa> empresas;
    private Empresa empresaSeleccionada;
    private Usuario currentUser;
    private ListaControladores listaControladores;
    private boolean oscuro = true;
    private boolean español = true;

    public Data() {
        this.listaControladores = new ListaControladores();
        ConexionBase.getDatabase();
        this.notas = ConexionBase.recibirNotas();
        this.usuarios = ConexionBase.recibirUsuarios();
        this.proyectos = ConexionBase.recibirProyectos();
        this.tareas = ConexionBase.recibirTareas();
        this.empresas = ConexionBase.recibirEmpresas();




    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Proyecto> getProyectos() {
        return proyectos;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }

    public void setProyectos(ArrayList<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public ArrayList<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(ArrayList<Empresa> empresas) {
        this.empresas = empresas;
    }

    public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public Empresa getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public ListaControladores getListaControladores() {
        return listaControladores;
    }

    public ArrayList<Nota> getNotas() {
        return notas;
    }

    public void setEspañol(boolean español) {
        this.español = español;
    }

    public boolean isEspañol() {
        return español;
    }

    public void setListaControladores(ListaControladores listaControladores) {
        this.listaControladores = listaControladores;
    }

    public boolean isOscuro() {
        return oscuro;
    }

    public void setOscuro(boolean oscuro) {
        this.oscuro = oscuro;
    }
}
