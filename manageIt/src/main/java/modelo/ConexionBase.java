package modelo;
import com.mongodb.client.*;
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





import java.io.ByteArrayInputStream;
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
                    contacto.setApellidos(doc.getString("apellidos"));
                    contacto.setDepartamento(doc.getString("departamento"));
                    contacto.setCorreo(doc.getString("correo"));
                    contacto.setDescripcion(doc.getString("descripcion"));
                    contacto.setImagen(ConexionBase.convertirImagen(doc.getString("imagen")));
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

        System.out.println(usuarios.get(0).getNotas().get(0));
        return usuarios;

    }
    public static void recibirNotas(){

    }
    public static void recibirProyectos(){

    }
    public static void recibirTareas(){

    }
    public static Image convertirImagen(String base64Image){
        if (base64Image == null || base64Image.isEmpty()) {
            return new Image("file:"+"src/main/resources/images/usuarios/persona.png");
        }

        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return new Image(bis);
    }


    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}
