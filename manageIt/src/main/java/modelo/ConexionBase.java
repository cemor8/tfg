package modelo;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.image.Image;
import org.bson.Document;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ArrayList;

public class ConexionBase {
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;
    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create("mongodb+srv://cemor8:12q12q12@cluster0.3yldjuk.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
                database = mongoClient.getDatabase("manageit");
            }catch (Exception e){
                System.out.println("hola");
                System.out.println(e.getMessage());
                database = null;
            }

        }
        return database;
    }

    /**
     * Método que se encarga de recibir los usuarios de la base de datos
     * @return
     */
    public static ArrayList<Usuario> recibirUsuarios(){

        MongoCollection<Document> collection = database.getCollection("usuarios");
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Bson lookupContactos = new Document("$lookup",
                new Document("from", "usuarios")
                        .append("localField", "contactos")
                        .append("foreignField", "id")
                        .append("as", "contactosDetalles"));

        Bson lookupNotas = new Document("$lookup",
                new Document("from", "notas")
                        .append("localField", "notas")
                        .append("foreignField", "id")
                        .append("as", "notasDetalles"));

        Bson lookupEmpresa = new Document("$lookup",
                new Document("from", "empresas")
                        .append("localField", "empresa")
                        .append("foreignField", "id")
                        .append("as", "empresaDetalles"));
        List<Bson> stages = Arrays.asList(lookupContactos, lookupNotas, lookupEmpresa);
        AggregateIterable<Document> result = collection.aggregate(stages);
        System.out.println(result);
        for (Document doc : result) {
            Usuario usuario = new Usuario(null,null,null,null,null,null,null,null,null);
            usuario.setId(doc.getInteger("id"));
            usuario.setCorreo(doc.getString("correo"));
            usuario.setNombre(doc.getString("nombre"));
            usuario.setApellidos(doc.getString("apellidos"));
            usuario.setContraseña(doc.getString("contraseña"));
            usuario.setDescripcion(doc.getString("descripcion"));
            usuario.setDepartamento(doc.getString("departamento"));
            usuario.setImagen(ConexionBase.convertirImagen(doc.getString("imagen")));


            if (!doc.getList("empresaDetalles", Document.class).isEmpty()) {
                Document empresaDoc = doc.getList("empresaDetalles", Document.class).get(0);
                Empresa empresa = new Empresa(null,null,null,null,null,null,null,null);
                empresa.setId(Integer.valueOf(empresaDoc.getString("id")));
                empresa.setNombre(empresaDoc.getString("nombre"));
                usuario.setEmpresa(empresa);
            }

            ArrayList<Usuario> contactos = new ArrayList<>();
            List<Document> contactosDocs = (List<Document>) doc.get("contactosDetalles");
            if (contactosDocs != null) {

                for (Document contactoDoc : contactosDocs) {
                    Usuario contacto = new Usuario(null,null,null,null,null,null,null,null,null);
                    contacto.setId(contactoDoc.getInteger("id"));
                    contacto.setNombre(contactoDoc.getString("nombre"));
                    contacto.setApellidos(contactoDoc.getString("apellidos"));
                    contacto.setDepartamento(contactoDoc.getString("departamento"));
                    contacto.setCorreo(contactoDoc.getString("correo"));
                    contacto.setDescripcion(contactoDoc.getString("descripcion"));
                    contacto.setImagen(ConexionBase.convertirImagen(contactoDoc.getString("imagen")));
                    contactos.add(contacto);
                }
            }
            usuario.setContactos(contactos);

            ArrayList<Nota> notas = new ArrayList<>();
            List<Document> notasDocs = (List<Document>) doc.get("notasDetalles");
            if (notasDocs != null) {
                for (Document notaDoc : notasDocs) {

                    Nota nota = new Nota(null,null,null,null,null);
                    nota.setId(notaDoc.getInteger("id"));
                    nota.setDescripcion(notaDoc.getString("descripcion"));
                    nota.setUsuario(usuario);
                    nota.setTitulo(notaDoc.getString("titulo"));
                    nota.setImagen(ConexionBase.convertirImagen(notaDoc.getString("imagen")));
                    System.out.println("texto "+ notaDoc.getString("imagen"));
                    String fechaStr = notaDoc.getString("fecha_creacion");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date fecha = formatter.parse(fechaStr);
                        nota.setFechaCreacion(fecha);
                    } catch (ParseException e) {
                        System.err.println("Formato de fecha no válido: " + e.getMessage());
                        nota.setFechaCreacion(null);
                    }

                    notas.add(nota);
                }
            }
            usuario.setNotas(notas);
            usuarios.add(usuario);
        }
        //falta empresa
        System.out.println(usuarios);
        return usuarios;

    }


    /**
     * Método que recibe las notas
     */
    public static ArrayList<Nota> recibirNotas(){
        ArrayList<Nota> notas = new ArrayList<>();
        MongoCollection<Document> notasCollection = database.getCollection("notas");
        MongoCollection<Document> usuariosCollection = database.getCollection("usuarios");

        FindIterable<Document> documentos = notasCollection.find();
        System.out.println(documentos);
        for (Document notaDoc : documentos) {
            System.out.println(notaDoc);
            Integer usuarioCreadorId = notaDoc.getInteger("usuario_creador");

            // Preparar los $lookup stages para el usuario creador
            Bson lookupContactos = new Document("$lookup",
                    new Document("from", "usuarios")
                            .append("localField", "contactos")
                            .append("foreignField", "id")
                            .append("as", "contactosDetalles"));

            Bson lookupNotas = new Document("$lookup",
                    new Document("from", "notas")
                            .append("localField", "notas")
                            .append("foreignField", "id")
                            .append("as", "notasDetalles"));

            Bson lookupEmpresa = new Document("$lookup",
                    new Document("from", "empresas")
                            .append("localField", "empresa")
                            .append("foreignField", "id")
                            .append("as", "empresaDetalles"));

            List<Bson> stages = Arrays.asList(
                    Aggregates.match(Filters.eq("id", usuarioCreadorId)),
                    lookupContactos,
                    lookupNotas,
                    lookupEmpresa
            );

            AggregateIterable<Document> result = usuariosCollection.aggregate(stages);
            Document usuarioDoc = result.first();

            Usuario usuario = null;
            if (usuarioDoc != null) {
                usuario = new Usuario(null,null, null, null, null,null,null,null,null);

                ArrayList<Usuario> contactos = new ArrayList<>();
                List<Document> contactosDocs = (List<Document>) usuarioDoc.get("contactosDetalles");
                if (contactosDocs != null) {

                    for (Document contactoDoc : contactosDocs) {
                        Usuario contacto = new Usuario(null,null,null,null,null,null,null,null,null);
                        contacto.setId(contactoDoc.getInteger("id"));
                        contacto.setNombre(contactoDoc.getString("nombre"));
                        contacto.setApellidos(contactoDoc.getString("apellidos"));
                        contacto.setDepartamento(contactoDoc.getString("departamento"));
                        contacto.setCorreo(contactoDoc.getString("correo"));
                        contacto.setDescripcion(contactoDoc.getString("descripcion"));
                        contacto.setImagen(ConexionBase.convertirImagen(usuarioDoc.getString("imagen")));
                        contactos.add(contacto);
                    }
                }
                usuario.setContactos(contactos);

                ArrayList<Nota> notasUsuario = new ArrayList<>();
                List<Document> notasDocs = (List<Document>) usuarioDoc.get("notasDetalles");
                if (notasDocs != null) {
                    for (Document notaUsuaio : notasDocs) {

                        Nota nota = new Nota(null,null,null,null,null);
                        nota.setId(notaUsuaio.getInteger("id"));
                        nota.setDescripcion(notaUsuaio.getString("descripcion"));
                        nota.setImagen(ConexionBase.convertirImagen(notaUsuaio.getString("imagen")));
                        nota.setUsuario(usuario);
                        String fechaStr = notaUsuaio.getString("fecha_creacion");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date fecha = formatter.parse(fechaStr);
                            nota.setFechaCreacion(fecha);
                        } catch (ParseException e) {
                            System.err.println("Formato de fecha no válido: " + e.getMessage());
                            nota.setFechaCreacion(null);
                        }

                        notasUsuario.add(nota);
                    }
                }
                usuario.setNotas(notasUsuario);
            }

            String fechaStr = notaDoc.getString("fecha_creacion");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


            Nota nota = new Nota(null, null,null,null,null);

            try {
                Date fecha = formatter.parse(fechaStr);
                nota.setFechaCreacion(fecha);
            } catch (ParseException e) {
                System.err.println("Formato de fecha no válido: " + e.getMessage());
                nota.setFechaCreacion(null);
            }
            nota.setId(notaDoc.getInteger("id"));
            nota.setDescripcion(notaDoc.getString("descripcion"));
            nota.setTitulo(notaDoc.getString("titulo"));
            nota.setUsuario(usuario);
            nota.setImagen(ConexionBase.convertirImagen(notaDoc.getString("imagen")));

            notas.add(nota);
            System.out.println(nota.getUsuario());

            System.out.println(notas);

        }
        //Falta imagen
        return notas;

    }

    public static ArrayList<Proyecto> recibirProyectos(){
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        MongoCollection<Document> coleccionProyectos = database.getCollection("proyectos");

        Bson lookupTareas = new Document("$lookup",
                new Document("from", "tareas")
                        .append("localField", "tareas")
                        .append("foreignField", "id")
                        .append("as", "tareasDetalles"));

        Bson lookupNotas = new Document("$lookup",
                new Document("from", "notas")
                        .append("localField", "notas")
                        .append("foreignField", "id")
                        .append("as", "notasDetalles"));

        Bson lookupJefe = new Document("$lookup",
                new Document("from", "usuarios")
                        .append("localField", "jefe_proyecto")
                        .append("foreignField", "id")
                        .append("as", "jefe_proyectoDetalles"));

        Bson lookupContactos = new Document("$lookup",
                new Document("from", "usuarios")
                        .append("localField", "asignados")
                        .append("foreignField", "id")
                        .append("as", "asignadosDetalles"));

        List<Bson> stages = Arrays.asList(
                lookupContactos,
                lookupNotas,
                lookupTareas,
                lookupJefe
        );

        AggregateIterable<Document> result = coleccionProyectos.aggregate(stages);
        for (Document doc : result) {
            Proyecto proyecto = new Proyecto(null,null,null,null,null,null,null,null,null,null,null,null);

            ArrayList<Usuario> asignados = new ArrayList<>();
            List<Document> asignadosDocs = (List<Document>) doc.get("asignadosDetalles");
            if (asignadosDocs != null) {

                for (Document asginadoDoc : asignadosDocs) {
                    Usuario asignado = new Usuario(null,null,null,null,null,null,null,null,null);
                    asignado.setId(asginadoDoc.getInteger("id"));
                    asignado.setNombre(asginadoDoc.getString("nombre"));
                    asignado.setApellidos(asginadoDoc.getString("apellidos"));
                    asignado.setDepartamento(asginadoDoc.getString("departamento"));
                    asignado.setCorreo(asginadoDoc.getString("correo"));
                    asignado.setDescripcion(asginadoDoc.getString("descripcion"));
                    asignado.setImagen(ConexionBase.convertirImagen(asginadoDoc.getString("imagen")));
                    asignados.add(asignado);
                }
            }
            proyecto.setPersonasAsignadas(asignados);

            ArrayList<Nota> notasProyecto = new ArrayList<>();
            List<Document> notasDocs = (List<Document>) doc.get("notasDetalles");
            if (notasDocs != null) {
                for (Document notaUsuario : notasDocs) {

                    Nota nota = new Nota(null,null,null,null,null);
                    nota.setId(notaUsuario.getInteger("id"));
                    nota.setDescripcion(notaUsuario.getString("descripcion"));
                    nota.setUsuario(ConexionBase.recibirUsuario(notaUsuario.getInteger("usuario_creador")));
                    nota.setImagen(ConexionBase.convertirImagen(notaUsuario.getString("imagen")));
                    String fechaStr = notaUsuario.getString("fecha_creacion");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date fecha = formatter.parse(fechaStr);
                        nota.setFechaCreacion(fecha);
                    } catch (ParseException e) {
                        System.err.println("Formato de fecha no válido: " + e.getMessage());
                        nota.setFechaCreacion(null);
                    }

                    notasProyecto.add(nota);
                }
            }
            proyecto.setNotas(notasProyecto);
            proyecto.setJefeProyecto(ConexionBase.recibirUsuario(doc.getInteger("jefe_proyecto")));
            System.out.println(proyecto.getJefeProyecto());
            proyecto.setDescripcion(doc.getString("descripcion"));
            proyecto.setEstado(doc.getString("estado"));
            proyecto.setNombre(doc.getString("nombre"));
            proyecto.setCliente(doc.getString("cliente"));
            proyecto.setId(doc.getInteger("id"));
            proyecto.setImagen(ConexionBase.convertirImagen(doc.getString("imagen")));

            String fechaStr = doc.getString("fecha_creacion");
            String fechaStr2 = doc.getString("fecha_entrega");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date fecha = formatter.parse(fechaStr);
                Date fecha2 = formatter.parse(fechaStr2);
                proyecto.setFechaCreacion(fecha);
                proyecto.setFechaEntrega(fecha2);
            } catch (ParseException e) {
                System.err.println("Formato de fecha no válido: " + e.getMessage());
                proyecto.setFechaCreacion(null);
                proyecto.setFechaEntrega(null);
            }
            //faltan las tareas
            ArrayList<Tarea> tareasaProyecto = new ArrayList<>();
            List<Document> tareasDocs = (List<Document>) doc.get("tareasDetalles");
            if (notasDocs != null) {
                for (Document cadatarea : tareasDocs) {

                    Tarea tarea = new Tarea(null,null,null,null,null,null,null,null,null,null,null);
                    tarea.setId(cadatarea.getInteger("id"));
                    tarea.setDescripcion(cadatarea.getString("descripcion"));
                    tarea.setCreador(ConexionBase.recibirUsuario(cadatarea.getInteger("usuario_creador")));
                    tarea.setImagen(ConexionBase.convertirImagen(cadatarea.getString("imagen")));
                    String fechaStr5 = cadatarea.getString("fecha_creacion");
                    String fechaStr6 = cadatarea.getString("fecha_entrega");
                    SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
                    //faltan imagenes


                    try {
                        Date fecha = formatter2.parse(fechaStr5);
                        Date fecha2 = formatter2.parse(fechaStr6);
                        tarea.setFechaCreacion(fecha);
                        tarea.setFechaCreacion(fecha2);
                    } catch (ParseException e) {
                        System.err.println("Formato de fecha no válido: " + e.getMessage());
                        tarea.setFechaCreacion(null);
                        tarea.setFechaCreacion(null);
                    }

                    tareasaProyecto.add(tarea);
                }
                proyecto.setTareas(tareasaProyecto);
            }


            proyectos.add(proyecto);
        }
        return proyectos;

    }
    public static ArrayList<Tarea> recibirTareas(){
        ArrayList<Tarea> tareas = new ArrayList<>();
        MongoCollection<Document> coleccionTareas = database.getCollection("tareas");

        Bson lookupNotas = new Document("$lookup",
                new Document("from", "notas")
                        .append("localField", "notas")
                        .append("foreignField", "id")
                        .append("as", "notasDetalles"));

        Bson lookupCreador = new Document("$lookup",
                new Document("from", "usuarios")
                        .append("localField", "usuario_creador")
                        .append("foreignField", "id")
                        .append("as", "usuario_creadorDetalles"));

        Bson lookupContactos = new Document("$lookup",
                new Document("from", "usuarios")
                        .append("localField", "asignados")
                        .append("foreignField", "id")
                        .append("as", "asignadosDetalles"));

        List<Bson> stages = Arrays.asList(
                lookupContactos,
                lookupNotas,
                lookupCreador
        );
        AggregateIterable<Document> result = coleccionTareas.aggregate(stages);
        for (Document doc : result) {
            Tarea tarea = new Tarea(null,null,null,null,null,null,null,null,null,null,null);
            tarea.setId(doc.getInteger("id"));
            tarea.setNombre(doc.getString("nombre"));
            tarea.setDescripcion(doc.getString("descripcion"));
            tarea.setCampo(doc.getString("campo"));
            tarea.setEstado(doc.getString("estado"));
            tarea.setImagen(ConexionBase.convertirImagen(doc.getString("imagen")));
            tarea.setCreador(ConexionBase.recibirUsuario(doc.getInteger("usuario_creador")));

            ArrayList<Nota> notasTarea = new ArrayList<>();
            List<Document> notasDocs = (List<Document>) doc.get("notasDetalles");
            if (notasDocs != null) {
                for (Document notaUsuario : notasDocs) {

                    Nota nota = new Nota(null,null,null,null,null);
                    nota.setId(notaUsuario.getInteger("id"));
                    nota.setDescripcion(notaUsuario.getString("descripcion"));
                    nota.setUsuario(ConexionBase.recibirUsuario(notaUsuario.getInteger("usuario_creador")));
                    nota.setImagen(ConexionBase.convertirImagen(notaUsuario.getString("imagen")));
                    String fechaStr = notaUsuario.getString("fecha_creacion");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date fecha = formatter.parse(fechaStr);
                        nota.setFechaCreacion(fecha);
                    } catch (ParseException e) {
                        System.err.println("Formato de fecha no válido: " + e.getMessage());
                        nota.setFechaCreacion(null);
                    }

                    notasTarea.add(nota);
                }
            }
            tarea.setNotas(notasTarea);
            ArrayList<Usuario> asignados = new ArrayList<>();
            List<Document> asignadosDocs = (List<Document>) doc.get("asignadosDetalles");
            if (asignadosDocs != null) {

                for (Document asginadoDoc : asignadosDocs) {
                    Usuario asignado = new Usuario(null,null,null,null,null,null,null,null,null);
                    asignado.setId(asginadoDoc.getInteger("id"));
                    asignado.setNombre(asginadoDoc.getString("nombre"));
                    asignado.setApellidos(asginadoDoc.getString("apellidos"));
                    asignado.setDepartamento(asginadoDoc.getString("departamento"));
                    asignado.setCorreo(asginadoDoc.getString("correo"));
                    asignado.setDescripcion(asginadoDoc.getString("descripcion"));
                    asignado.setImagen(ConexionBase.convertirImagen(asginadoDoc.getString("imagen")));
                    asignados.add(asignado);
                }
            }
            tarea.setPersonasAsignadas(asignados);
            tareas.add(tarea);
        }
        return tareas;
    }
    public static Image convertirImagen(String base64Image){
        System.out.println(base64Image);
        if (base64Image == null || base64Image.isEmpty()) {
            System.out.println("usuario no tiene imagen");
            return new Image("file:"+"src/main/resources/images/proyectos/vistaCadaProyecto/fondoProyectoPrueba.png");
        }

        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return new Image(bis);
    }
    public static String transformarA64(Image image) throws IOException {
        if (image == null){
            return "";
        }
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String base64String = Base64.getEncoder().encodeToString(imageBytes);
        outputStream.close();
        return base64String;
    }




    public static Usuario recibirUsuario(Integer id){
        MongoCollection<Document> coleccionUsuarios = database.getCollection("usuarios");

        Bson lookupContactos = new Document("$lookup",
                new Document("from", "usuarios")
                        .append("localField", "contactos")
                        .append("foreignField", "id")
                        .append("as", "contactosDetalles"));

        Bson lookupNotas = new Document("$lookup",
                new Document("from", "notas")
                        .append("localField", "notas")
                        .append("foreignField", "id")
                        .append("as", "notasDetalles"));

        Bson lookupEmpresa = new Document("$lookup",
                new Document("from", "empresas")
                        .append("localField", "empresa")
                        .append("foreignField", "id")
                        .append("as", "empresaDetalles"));

        List<Bson> stages = Arrays.asList(
                Aggregates.match(Filters.eq("id", id)),
                lookupContactos,
                lookupNotas,
                lookupEmpresa
        );

        AggregateIterable<Document> result = coleccionUsuarios.aggregate(stages);
        Document usuarioDoc = result.first();

        Usuario usuario = null;
        if (usuarioDoc != null) {
            usuario = new Usuario(null,null, null, null, null,null,null,null,null);

            ArrayList<Usuario> contactos = new ArrayList<>();
            List<Document> contactosDocs = (List<Document>) usuarioDoc.get("contactosDetalles");
            if (contactosDocs != null) {

                for (Document contactoDoc : contactosDocs) {
                    Usuario contacto = new Usuario(null,null,null,null,null,null,null,null,null);
                    contacto.setId(contactoDoc.getInteger("id"));
                    contacto.setNombre(contactoDoc.getString("nombre"));
                    contacto.setApellidos(contactoDoc.getString("apellidos"));
                    contacto.setDepartamento(contactoDoc.getString("departamento"));
                    contacto.setCorreo(contactoDoc.getString("correo"));
                    contacto.setDescripcion(contactoDoc.getString("descripcion"));
                    contacto.setImagen(ConexionBase.convertirImagen(usuarioDoc.getString("imagen")));
                    contactos.add(contacto);
                }
            }
            usuario.setContactos(contactos);

            ArrayList<Nota> notasUsuario = new ArrayList<>();
            List<Document> notasDocs = (List<Document>) usuarioDoc.get("notasDetalles");
            if (notasDocs != null) {
                for (Document notaUsuaio : notasDocs) {

                    Nota nota = new Nota(null,null,null,null,null);
                    nota.setId(notaUsuaio.getInteger("id"));
                    nota.setDescripcion(notaUsuaio.getString("descripcion"));
                    nota.setUsuario(usuario);
                    String fechaStr = notaUsuaio.getString("fecha_creacion");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date fecha = formatter.parse(fechaStr);
                        nota.setFechaCreacion(fecha);
                    } catch (ParseException e) {
                        System.err.println("Formato de fecha no válido: " + e.getMessage());
                        nota.setFechaCreacion(null);
                    }

                    notasUsuario.add(nota);
                }
            }
            usuario.setNotas(notasUsuario);
            usuario.setApellidos(usuarioDoc.getString("apellidos"));
            usuario.setCorreo(usuarioDoc.getString("correo"));
            usuario.setNombre(usuarioDoc.getString("nombre"));
            usuario.setDescripcion(usuarioDoc.getString("descripcion"));
            usuario.setImagen(ConexionBase.convertirImagen(usuarioDoc.getString("apellidos")));
            usuario.setId(usuarioDoc.getInteger("id"));
            usuario.setDepartamento(usuarioDoc.getString("departamento"));
        }
        return usuario;
    }


    public static void eliminarTarea(Integer id){
        MongoCollection<Document> notas = database.getCollection("notas");
        MongoCollection<Document> tareas = database.getCollection("tareas");
        MongoCollection<Document> proyectos = database.getCollection("proyectos");

        Document tarea = tareas.find(Filters.eq("id", id)).first();

        ArrayList<Integer> idsNotasProyecto = (ArrayList<Integer>) tarea.get("notas");

        for (Integer cadaIdNotaProyecto : idsNotasProyecto) {

            notas.deleteOne(Filters.eq("id", cadaIdNotaProyecto));
        }

        proyectos.updateMany(
                new Document("tareas", id),
                new Document("$pull", new Document("tareas", id))
        );

        tareas.deleteOne(Filters.eq("id", id));

        //buscar el proyecto de la tarea y eliminar el id de la tarea de la lista del proyecto


    }



    public static void eliminarProyecto(Integer id){
        MongoCollection<Document> notas = database.getCollection("notas");
        MongoCollection<Document> proyectos = database.getCollection("proyectos");
        MongoCollection<Document> tareas = database.getCollection("tareas");
        Document project = proyectos.find(Filters.eq("id", id)).first();
        if (project != null) {
            System.out.println("Proyecto encontrado: " + project.toJson());
            ArrayList<Integer> idsTareas = (ArrayList<Integer>) project.get("tareas");


            ArrayList<Integer> idsNotasProyecto = (ArrayList<Integer>) project.get("notas");

            for (Integer cadaIdNotaProyecto : idsNotasProyecto) {

                notas.deleteOne(Filters.eq("id", cadaIdNotaProyecto));
            }


            for (Integer cadaIdTarea : idsTareas) {

                Document tareaDocumento = tareas.find(Filters.eq("id", cadaIdTarea)).first();

                ArrayList<Integer> idsNotasTarea = (ArrayList<Integer>) tareaDocumento.get("tareas");

                for (Integer cadaIdNotaTarea : idsNotasTarea){
                    notas.deleteOne(Filters.eq("id",cadaIdNotaTarea));
                }

                tareas.deleteOne(Filters.eq("id", cadaIdTarea));
            }
            proyectos.deleteOne(Filters.eq("id", id));

        } else {
            System.out.println("No se encontró ningún proyecto con el ID: " + id);
        }
        proyectos.deleteOne(Filters.eq("id", id));

        //eliminar las tareas del proyecto y las notas del proyecto



    }
    public static void eliminarNota(Integer id){
        // Colecciones
        MongoCollection<Document> notas = database.getCollection("notas");
        MongoCollection<Document> proyectos = database.getCollection("proyectos");
        MongoCollection<Document> tareas = database.getCollection("tareas");
        MongoCollection<Document> usuarios = database.getCollection("usuarios");

        proyectos.updateOne(Filters.eq("notas", id), new Document("$pull", new Document("notas", id)));
        tareas.updateOne(Filters.eq("notas", id), new Document("$pull", new Document("notas", id)));
        usuarios.updateOne(Filters.eq("notas", id), new Document("$pull", new Document("notas", id)));

        notas.deleteOne(Filters.eq("id", id));
    }
    public static void modificarUsuario(Usuario usuario) throws IOException {
        MongoCollection<Document> usuariosColeccion = database.getCollection("usuarios");
        //meter modificaciones
        String image64 = ConexionBase.transformarA64(usuario.getImagen());
        Document updateFields = new Document();
        updateFields.append("imagen",image64);
        updateFields.append("nombre",usuario.getNombre());
        updateFields.append("descripcion",usuario.getDescripcion());
        updateFields.append("contraseña",usuario.getContraseña());
        updateFields.append("correo",usuario.getCorreo());
        updateFields.append("departamento",usuario.getDepartamento());
        ArrayList<Integer> contactos = new ArrayList<>();
        for (Usuario contacto : usuario.getContactos()){
            contactos.add(contacto.getId());
        }
        updateFields.append("contactos",contactos);
        usuariosColeccion.updateOne(Filters.eq("id", usuario.getId()), new Document("$set", updateFields));

    }

    public static void crearUsuario(Usuario usuario){
        MongoCollection<Document> usuarios = database.getCollection("usuarios");

        Document nuevoUsuario = new Document("nombre", usuario.getNombre())
                .append("apellidos", usuario.getApellidos());

        // Añadir más campos según sean necesarios
        usuarios.insertOne(nuevoUsuario);

    }




    public static void eliminarUsuario(Integer id){
        MongoCollection<Document> usuarios = database.getCollection("usuarios");
        MongoCollection<Document> notas = database.getCollection("notas");
        MongoCollection<Document> proyectos = database.getCollection("proyectos");
        MongoCollection<Document> tareas = database.getCollection("tareas");
        usuarios.deleteOne(Filters.eq("id", id));
        //usuarios.updateMany(Filters.eq("contactos", id, Updates.pull("contactos", id));

        notas.deleteMany(Filters.eq("usuario_creador", id));
        tareas.deleteMany(Filters.eq("creador", id));
        proyectos.deleteMany(Filters.eq("creador", id));
    }




    public static void modificarTarea(Tarea tarea){
        MongoCollection<Document> tareas = database.getCollection("tareas");
        //meter modificaciones
        ArrayList<Integer> notasTarea = new ArrayList<>();
        for (Nota nota : tarea.getNotas()){
            notasTarea.add(nota.getId());
        }
        ArrayList<Integer> usuariosAsignados = new ArrayList<>();
        for (Usuario usuario : tarea.getPersonasAsignadas()){
            usuariosAsignados.add(usuario.getId());
        }
        Document updateFields = new Document();

        updateFields.append("estado",tarea.getEstado());
        updateFields.append("descripcion",tarea.getDescripcion());
        updateFields.append("notas",notasTarea);
        updateFields.append("asignados",usuariosAsignados);
        tareas.updateOne(Filters.eq("id", tarea.getId()), new Document("$set", updateFields));
    }

    public static void modificarProyecto(Proyecto proyecto){
        MongoCollection<Document> proyectos = database.getCollection("proyectos");
        //meter modificaciones
        ArrayList<Integer> notasProyecto = new ArrayList<>();
        for (Nota nota : proyecto.getNotas()){
            notasProyecto.add(nota.getId());
        }
        ArrayList<Integer> usuariosAsignados = new ArrayList<>();
        for (Usuario usuario : proyecto.getPersonasAsignadas()){
            usuariosAsignados.add(usuario.getId());
        }
        ArrayList<Integer> tareasProyecto = new ArrayList<>();
        for (Tarea tarea : proyecto.getTareas()){
            tareasProyecto.add(tarea.getId());
        }

        Document updateFields = new Document();
        updateFields.append("estado",proyecto.getEstado());
        updateFields.append("tareas",tareasProyecto);
        updateFields.append("notas",notasProyecto);
        updateFields.append("asignados",usuariosAsignados);
        updateFields.append("descripcion",proyecto.getDescripcion());
        proyectos.updateOne(Filters.eq("id", proyecto.getId()), new Document("$set", updateFields));
    }
    public static void modificarNota(Nota nota) throws IOException {
        MongoCollection<Document> notas = database.getCollection("notas");

        Document updateFields = new Document();
        updateFields.append("titulo",nota.getTitulo());
        updateFields.append("descripcion",nota.getDescripcion());
        updateFields.append("imagen",ConexionBase.transformarA64(nota.getImagen()));
        notas.updateOne(Filters.eq("id", nota.getId()), new Document("$set", updateFields));
    }

    public static void crearTarea(Tarea tarea) throws IOException {
        MongoCollection<Document> tareas = database.getCollection("tareas");
        ArrayList<Integer> usuariosTarea = new ArrayList<>();
        for (Usuario usuario : tarea.getPersonasAsignadas()){
            usuariosTarea.add(usuario.getId());
        }
        ArrayList<Integer> notasTarea = new ArrayList<>();
        for (Nota nota : tarea.getNotas()){
            notasTarea.add(nota.getId());
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(tarea.getFechaCreacion());
        String entrega = formatter.format(tarea.getFechaEntrega());
        Document nuevoUsuario = new Document("nombre", tarea.getNombre())
                .append("estado", tarea.getEstado()).append("campo",tarea.getCampo()).append("descripcion",tarea.getDescripcion()).append("notas",notasTarea).append("imagen",ConexionBase.transformarA64(tarea.getImagen()))
                .append("usuarios",usuariosTarea).append("fecha_entrega",entrega).append("fecha_creacion",dateString).append("usuario_creador",tarea.getCreador().getId());

        // Añadir más campos según sean necesarios
        tareas.insertOne(nuevoUsuario);
    }
    public static void crearProyecto(Proyecto proyecto) throws IOException {
        MongoCollection<Document> proyectos = database.getCollection("proyectos");
        ArrayList<Integer> usuariosProyecto = new ArrayList<>();

        for (Usuario usuario : proyecto.getPersonasAsignadas()){
            usuariosProyecto.add(usuario.getId());
        }
        ArrayList<Integer> notasProyecto = new ArrayList<>();
        for (Nota nota : proyecto.getNotas()){
            notasProyecto.add(nota.getId());
        }
        ArrayList<Integer> tareasProyecto = new ArrayList<>();
        for (Tarea tarea : proyecto.getTareas()){
            tareasProyecto.add(tarea.getId());
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(proyecto.getFechaCreacion());
        String entrega = formatter.format(proyecto.getFechaEntrega());

        Document nuevoUsuario = new Document("nombre", proyecto.getNombre())
                .append("estado", proyecto.getEstado()).append("cliente",proyecto.getCliente()).append("descripcion",proyecto.getDescripcion()).append("notas",notasProyecto)
                .append("usuarios",usuariosProyecto).append("fecha_entrega",entrega).append("fecha_creacion",dateString).append("jefe_proyecto",proyecto.getJefeProyecto().getId())
                .append("tareas",tareasProyecto).append("imagen",ConexionBase.transformarA64(proyecto.getImagen()));

        // Añadir más campos según sean necesarios
        proyectos.insertOne(nuevoUsuario);
    }
    public static void crearNota(Nota nota) throws IOException {
        MongoCollection<Document> notas = database.getCollection("notas");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(nota.getFechaCreacion());
        Document nuevoUsuario = new Document("titulo", nota.getTitulo()).append("imagen",ConexionBase.transformarA64(nota.getImagen()))
                .append("descripcion",nota.getDescripcion()).append("usuario_creador",nota.getUsuario().getId()).append("fecha_creacion",dateString);

        // Añadir más campos según sean necesarios
        notas.insertOne(nuevoUsuario);
    }
    public static Integer obtenerId(String coleccionNombre){
        MongoCollection<Document> coleccion = null;
        switch (coleccionNombre){
            case "notas":
                coleccion = database.getCollection("idnotas");
                break;
            case "proyectos":
                coleccion = database.getCollection("idproyectos");
                break;
            case "usuarios":
                coleccion = database.getCollection("idusuarios");
                break;
            case "tareas":
                coleccion = database.getCollection("idtareas");
                break;
        }
        Document updatedDocument = coleccion.findOneAndUpdate(
                new Document(),Updates.inc("sequence_value", 1)
        );
        assert updatedDocument != null;
        int currentValue = updatedDocument.getInteger("valor");
        return currentValue + 1;
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }

}
