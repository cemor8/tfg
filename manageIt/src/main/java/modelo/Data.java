package modelo;

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
    private Usuario currentUser;
    private ListaControladores listaControladores;
    private boolean oscuro = true;
    private boolean español = true;

    public Data() {
        this.notas = new ArrayList<>();
        this.proyectos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.tareas = new ArrayList<>();
        this.listaControladores = new ListaControladores();
        this.crearUsuarios();
        this.crearTareas();
        this.crearNotas();
        this.crearNotasProyectos();
        this.crearProyectos();




    }
    private void crearUsuarios(){
        Usuario usuario2 =  new Usuario("gonzalo@gmail.com","Gonzalo","Morais","12q12q12",
                "Hola","Informático","src/main/resources/images/usuarios/persona.png",new ArrayList<>(),new ArrayList<>());
        /*"src/main/resources/images/usuarios/persona.png"*/
        Usuario usuario = new Usuario("carlosmoraisblanco@gmail.com","Carlos","Morais","12q12q12",
                "Hola me llamo Carlos Morais","Informático","src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",new ArrayList<>(List.of()),new ArrayList<>());
        Usuario usuario3 = new Usuario("javier@gmail.com","Javier","Rodriguez","12q12q12","Hola, soy Javier, un entusiasta de la tecnología y profesional de la informática con 5 de experiencia en el corazón del mundo digital. Especializado en programacion, mi carrera está dedicada a diseñar, implementar y mejorar sistemas que hacen nuestra vida más fácil y segura.",
                "Informático","src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",new ArrayList<>(List.of(usuario)),new ArrayList<>());
        Usuario usuario4 = new Usuario("roberto@gmail.com","Roberto","Castro","12q12q12","Hola, soy Roberto, un entusiasta de la tecnología y profesional de la informática con 5 de experiencia en el corazón del mundo digital. Especializado en programacion, mi carrera está dedicada a diseñar, implementar y mejorar sistemas que hacen nuestra vida más fácil y segura.",
                "Informático","src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",new ArrayList<>(List.of(usuario3)),new ArrayList<>());
        Usuario usuario5 = new Usuario("alberto@gmail.com","Alberto","Rodriguez","12q12q12","Hola, soy Javier, un entusiasta de la tecnología y profesional de la informática con 5 de experiencia en el corazón del mundo digital. Especializado en programacion, mi carrera está dedicada a diseñar, implementar y mejorar sistemas que hacen nuestra vida más fácil y segura.",
                "Informático","src/main/resources/images/usuarios/persona.png",new ArrayList<>(),new ArrayList<>());
        usuario.getContactos().add(usuario5);
        usuario.getContactos().add(usuario4);
        usuario.getContactos().add(usuario2);
        this.usuarios.add(usuario);
        this.usuarios.add(usuario2);
        this.usuarios.add(usuario3);
        this.usuarios.add(usuario4);
        this.usuarios.add(usuario5);
    }
    public void crearProyectos(){

        Usuario usuario = this.usuarios.get(0);


        Proyecto proyecto = new Proyecto("Proyecto App Netflix", "Netflix", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                "Completado", "Descripcion de proyecto de prueba", Date.from(LocalDate.of(2024, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2024, 1, 25).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(List.of(usuario,usuarios.get(1),usuarios.get(2))),"");

        proyecto.getTareas().add(this.tareas.get(0));
        proyecto.getNotas().add(this.notas.get(0));
        proyecto.getNotas().add(this.notas.get(4));

        this.proyectos.add(proyecto);

        Proyecto proyecto2 = new Proyecto("Proyecto App Uber", "Uber", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                "Completado", "Descripcion de proyecto de prueba",  Date.from(LocalDate.of(2024, 4, 6).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2024, 4, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(List.of(usuario)),"");

        proyecto2.getTareas().add(this.tareas.get(1));
        proyecto2.getNotas().add(this.notas.get(1));

        this.proyectos.add(proyecto2);

        Proyecto proyecto3 = new Proyecto("Proyecto App Discord", "Discord", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                "En proceso", "Descripcion de proyecto de prueba",  Date.from(LocalDate.of(2024, 4, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2024, 4, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(List.of(usuario)),"");

        proyecto3.getTareas().add(this.tareas.get(2));
        proyecto3.getNotas().add(this.notas.get(2));

        this.proyectos.add(proyecto3);

        Proyecto proyecto4 = new Proyecto("Proyecto App Movistar", "Movistar", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                "Pendiente", "Descripcion de proyecto de prueba",  Date.from(LocalDate.of(2024, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2024, 3, 29).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(List.of(usuario)),"");

        proyecto4.getTareas().add(this.tareas.get(3));
        proyecto4.getNotas().add(this.notas.get(3));

        this.proyectos.add(proyecto4);



    }
    public void crearTareas(){

        Usuario usuario = this.usuarios.get(0);

        Tarea tarea = new Tarea("Tarea de prueba", "En proceso", "BBDD", "Descripcion de tarea de prueba",
                Date.from(LocalDate.of(2024, 1, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.of(2024, 1, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png", new ArrayList<>(), new ArrayList<>(List.of(usuario)), usuario,"");
        this.tareas.add(tarea);

        Nota nota = new Nota("Nota de prueba", "Descripcion de nota de prueba", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                Date.from(LocalDate.of(2024, 1, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario);

        tarea.getNotas().add(nota);

        Tarea tarea2 = new Tarea("Tarea de prueba 2", "En proceso", "Programacion", "Descripcion de tarea de prueba",
                Date.from(LocalDate.of(2024, 4, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()),  Date.from(LocalDate.of(2024, 4, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png", new ArrayList<>(), new ArrayList<>(List.of(usuario)), usuario,"");

        this.tareas.add(tarea2);

        Tarea tarea3 = new Tarea("Tarea de prueba 3", "Pendiente", "Web", "Descripcion de tarea de prueba",
                Date.from(LocalDate.of(2024, 4, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(LocalDate.of(2024, 4, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png", new ArrayList<>(), new ArrayList<>(List.of(usuario)), usuario,"");

        this.tareas.add(tarea3);
        Tarea tarea4 = new Tarea("Tarea de prueba 4", "En proceso", "Programacion", "Descripcion de tarea de prueba",
                Date.from(LocalDate.of(2024, 3, 24).atStartOfDay(ZoneId.systemDefault()).toInstant()),  Date.from(LocalDate.of(2024, 3, 28).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png", new ArrayList<>(), new ArrayList<>(List.of(usuario)), usuario,"");

        this.tareas.add(tarea4);



    }
    public void crearNotas(){
        Usuario usuario = this.usuarios.get(0);

        Nota nota = new Nota("Nota de pruebaP", "Descripcion de nota de prueba", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                Date.from(LocalDate.of(2024, 2, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario);
        usuario.getNotas().add(nota);
    }
    public void crearNotasProyectos(){
        Usuario usuario = this.usuarios.get(0);

        Nota nota = new Nota("Nota de prueba", "Descripcion de nota de prueba", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                Date.from(LocalDate.of(2024, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario);
        this.notas.add(nota);


        Nota nota2 = new Nota("Nota de prueba2 ", "Descripcion de nota de prueba", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                Date.from(LocalDate.of(2024, 4, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario);
        this.notas.add(nota2);


        Nota nota3 = new Nota("Nota de prueba3 ", "Descripcion de nota de prueba", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                Date.from(LocalDate.of(2024, 4, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario);
        this.notas.add(nota3);


        Nota nota4 = new Nota("Nota de prueba4 ", "Descripcion de nota de prueba", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                Date.from(LocalDate.of(2024, 3, 28).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuario);
        this.notas.add(nota4);

        Nota nota5 = new Nota("Nota de prueba4 ", "Descripcion de nota de prueba", "src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png",
                Date.from(LocalDate.of(2024, 1, 24).atStartOfDay(ZoneId.systemDefault()).toInstant()), usuarios.get(1));
        this.notas.add(nota5);
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    /*
    public ArrayList<Nota> getNotas() {
        return notas;
    }

     */

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
