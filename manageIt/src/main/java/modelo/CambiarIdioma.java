package modelo;

import java.util.Locale;
import java.util.ResourceBundle;

public class CambiarIdioma {
    public static CambiarIdioma instance;
    private ResourceBundle resourceBundle;

    public CambiarIdioma() {
        cargarIdioma("es","ES");
    }
    /**
     * Método que crea una nueva instancia de la clase si no existe una previa y la devuelve
     * */
    public static CambiarIdioma getInstance(){
        if (instance == null){
            instance = new CambiarIdioma();
        }
        return instance;
    }
    /**
     * Método que cambia el idioma del bundle de la clase
     * */
    public void cargarIdioma(String idioma, String pais){
        Locale locale = new Locale(idioma,pais);
        resourceBundle = ResourceBundle.getBundle("bundles.MessagesBundle",locale);
    }

    public ResourceBundle getBundle(){
        return resourceBundle;
    }
}
