package proyecto;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Clase para comprobar la meta descripción de una página web.
 * Utiliza Jsoup para analizar el HTML de una URL específica.
 * Autor: Gonzalo Román Márquez
 */
public class ComprobarDescription {
    // Atributos de la clase
    String url;               // URL de la página a analizar
    String description;       // Contenido de la meta descripción encontrada
    String estadoDescription; // Estado de la meta descripción (Correcto/Error)

    // Getters y setters para los atributos
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstadoDescription() {
        return estadoDescription;
    }

    public void setEstadoDescription(String estadoDescription) {
        this.estadoDescription = estadoDescription;
    }

    /**
     * Constructor que inicializa la clase con la URL a analizar.
     * 
     * @param url URL de la página web.
     */
    public ComprobarDescription(String url) {
        this.url = url;
    }

    /**
     * Método para comprobar la meta descripción de la página web.
     * - Descarga el HTML de la URL especificada.
     * - Busca el elemento `<meta name="description">`.
     * - Evalúa si existe, si está vacío o si contiene texto válido.
     */
    public void comprobar() {
        try {
            // Conecta a la URL y descarga el contenido HTML
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .get();

            // Selecciona el primer elemento <meta name="description">
            Element metaDescription = doc.selectFirst("meta[name=description]");

            // Verifica si el elemento meta descripción existe
            if (metaDescription != null) {
                // Obtiene el contenido del atributo "content"
                description = metaDescription.attr("content");

                // Evalúa si el contenido está vacío
                if (description.isEmpty()) {
                    estadoDescription = "Error"; // Meta descripción vacía
                    System.out.println("Error: Meta descripción vacía");
                } else {
                    estadoDescription = "Correcto"; // Meta descripción válida
                    System.out.println("Descripción de la página: " + description);
                    System.out.println("Estado: Correcto");
                }
            } else {
                // Si no se encuentra la meta descripción
                estadoDescription = "Error";
                System.out.println("Error: No se encontró la meta descripción");
            }
        } catch (IOException iOException) {
            // Manejo de errores al intentar conectar con la URL
            System.out.println("Error al comprobar la descripción");
        }
    }
}
