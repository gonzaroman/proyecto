package proyecto;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Clase para comprobar y analizar el título de una página web.
 * Verifica si el título existe y si cumple con las condiciones esperadas.
 */
public class ComprobarTitle {
    // Atributos principales
    String url; // URL de la página web a analizar
    String title; // Título extraído de la página
    String estado_title; // Estado del título ("Correcto" o "Error")

    // Métodos getters y setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEstado_title() {
        return estado_title;
    }

    public void setEstado_title(String estado_title) {
        this.estado_title = estado_title;
    }

    // Constructor para inicializar la clase con la URL
    public ComprobarTitle(String url) {
        this.url = url;
    }

    /**
     * Método para comprobar el título de una página web.
     * Extrae el título de la página y determina su estado (correcto o error).
     */
    public void comprobar() {  
        try {
            // Descargar y analizar el HTML de la página web
            Document doc = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                                .get();
            
            // Extraer el título de la página
            title = doc.title();
            
            // Validar si el título está vacío o no
            if (title.isEmpty()) {
                // Si el título está vacío, marcar como error
                estado_title = "Error";
                System.out.println("Error Title");
            } else {
                // Si el título está presente, marcar como correcto
                estado_title = "Correcto";
                System.out.println("Título de la página: " + title);
                System.out.println("Correcto");
            }

        } catch (IOException ex) {
            // Manejar errores durante la conexión o extracción del título
            System.out.println("Se ha producido un error en la clase ComprobarTitle: " + ex.getMessage());
        }
    }
}
