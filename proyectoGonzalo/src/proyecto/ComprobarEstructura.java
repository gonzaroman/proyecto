package proyecto;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase para comprobar la estructura básica de una página web.
 * Analiza elementos clave como el título, meta descripción y encabezados (H1, H2, H3, H4).
 */
public class ComprobarEstructura {
    // Atributos principales
    String url; // URL que se analizará

    // Colores para mensajes en consola
    String rojo = "\u001B[31m"; // Mensaje en rojo para errores
    String verde = "\u001B[32m"; // Mensaje en verde para resultados correctos

    // Métodos getters y setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Constructor para inicializar el objeto con una URL específica
    public ComprobarEstructura(String url) {
        this.url = url;
    }

    /**
     * Método principal para analizar la estructura de la página web.
     * Comprueba el título, la meta descripción y la jerarquía de encabezados.
     */
    public void comprobar() {
        try {
            // Descargar y analizar el HTML de la página web
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .get();

            // Comprobar el título de la página
            String title = doc.title();
            if (title.isEmpty()) {
                System.out.println(rojo + "Error: El título de la página está vacío.");
            } else {
                System.out.println(verde + "Título de la página: " + title);
            }

            // Comprobar la meta descripción
            Element metaDescription = doc.selectFirst("meta[name=description]");
            String description = (metaDescription != null) ? metaDescription.attr("content") : "No tiene meta descripción";
            System.out.println("Meta Descripción: " + description);

            // Comprobar los encabezados H1
            String currentH1 = null; // Variable para almacenar el texto del H1 actual
            Elements h1Elements = doc.select("h1"); // Seleccionar todos los elementos H1
            if (h1Elements.isEmpty()) {
                System.out.println(rojo + "¡¡ERROR!! No se encontraron encabezados H1 en la página.");
            } else {
                System.out.println("\nEncabezados H1:");
                for (Element h1 : h1Elements) {
                    if (h1.text().length() == 0) {
                        System.out.println(rojo + "ERROR: El encabezado H1 está vacío.");
                    } else {
                        System.out.println(verde + "Correcto: " + h1.text());
                    }
                    currentH1 = h1.text(); // Guardar el texto del último H1 encontrado
                }
            }

            // Comprobar la jerarquía de encabezados (H2, H3, H4)
            Elements headers = doc.select("h2, h3, h4"); // Seleccionar H2, H3 y H4
            String currentH2 = null; // Variable para almacenar el texto del H2 actual

            for (Element header : headers) {
                // Comprobar encabezados H2
                if (header.tagName().equals("h2")) {
                    System.out.println("   H2: " + header.text());
                    currentH2 = header.text(); // Guardar el texto del último H2 encontrado
                    if (currentH1 != null) {
                        System.out.println(verde + "Correcto: El H2 sigue a un H1.");
                    } else {
                        System.out.println(rojo + "¡¡ERROR!! El H2 no está precedido por un H1.");
                    }
                }
                // Comprobar encabezados H3
                else if (header.tagName().equals("h3")) {
                    System.out.println("           H3: " + header.text());
                    if (currentH2 != null) {
                        System.out.println(verde + "Correcto: El H3 sigue a un H2.");
                    } else {
                        System.out.println(rojo + "¡¡ERROR!! El H3 no está precedido por un H2.");
                    }
                }
                // Comprobar encabezados H4
                else if (header.tagName().equals("h4")) {
                    System.out.println("      H4: " + header.text());
                    // En este caso, no se valida la jerarquía de H4
                }
            }

        } catch (IOException ex) {
            // Manejo de errores al intentar analizar la página
            System.out.println(rojo + "Se ha producido un error en la clase ComprobarEstructura: " + ex.getMessage());
        }
    }
}
