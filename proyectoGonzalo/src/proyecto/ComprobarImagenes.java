package proyecto;

import baseDatos.InsertarImagenes; // Importar la clase para insertar datos en la tabla de imágenes
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase para analizar y validar las imágenes de una página web.
 * Verifica atributos como "alt" y "src" para evaluar la accesibilidad y calidad de las imágenes.
 */
public class ComprobarImagenes {
    // Atributos principales
    String url; // URL de la página web que se analizará
    String idAnalisis; // ID del análisis para registrar los resultados en la base de datos
    String ruta_imagen; // Ruta de la imagen encontrada
    String alt_texto; // Texto alternativo (atributo "alt") de la imagen
    String estado_alt; // Estado del atributo "alt" (Correcto/Error)

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

    public String getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(String idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    // Constructor para inicializar la clase con una URL y un ID de análisis
    public ComprobarImagenes(String url, String idAnalisis) {
        this.url = url;
        this.idAnalisis = idAnalisis;
    }

    /**
     * Método para analizar las imágenes de una página web.
     * Verifica la presencia de atributos "alt" y "src", y registra los resultados.
     */
    public void comprobar() {
        try {
            // Descargar y analizar el HTML de la página web
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .get();

            // Seleccionar todos los elementos <img> de la página
            Elements imgElements = doc.select("img");

            System.out.println("Imágenes (alt y src):");

            // Iterar sobre cada elemento <img>
            for (Element img : imgElements) {
                // Obtener la ruta absoluta de la imagen
                ruta_imagen = img.absUrl("src");

                // Obtener el texto alternativo del atributo "alt"
                alt_texto = img.attr("alt");

                // Filtrar imágenes con formato Base64 o sin ruta
                if (ruta_imagen.startsWith("data:image/") || ruta_imagen.isEmpty()) {
                    System.out.println("Imagen en Base64 ignorada: " + ruta_imagen);
                    continue; // Saltar esta iteración si la imagen es Base64 o no tiene ruta
                }

                // Mostrar información de la imagen en la consola
                System.out.println("Ruta: " + ruta_imagen);
                System.out.println("Imagen: alt=\"" + alt_texto + "\"");

                // Validar si el atributo "alt" está vacío
                if (alt_texto.length() == 0) {
                    System.out.println(rojo + "Error: Alt vacío");
                    estado_alt = "Error"; // Marcar el estado como "Error" si está vacío
                } else {
                    System.out.println(verde + "Correcto");
                    estado_alt = "Correcto"; // Marcar el estado como "Correcto" si tiene contenido
                }

                // Registrar la imagen en la base de datos
                InsertarImagenes.insertaImagenes(idAnalisis, ruta_imagen, alt_texto, estado_alt);
            }
        } catch (IOException iOException) {
            // Manejo de errores durante el análisis
            System.out.println(rojo + "Error al analizar las imágenes: " + iOException.getMessage());
        }
    }
}
