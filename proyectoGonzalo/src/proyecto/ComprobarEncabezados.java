package proyecto;

import baseDatos.InsertarEncabezados;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase para comprobar la estructura de encabezados (H1, H2, H3, H4) de una página web.
 * Utiliza Jsoup para analizar el HTML de una URL específica.
 * Autor: Gonzalo Román Márquez
 */
public class ComprobarEncabezados {
    // Atributos de la clase
    String url;           // URL de la página a analizar
    String idAnalisis;    // ID del análisis para relacionar los datos en la base de datos
    String nivel;         // Nivel del encabezado (H1, H2, H3, H4)
    String contenido;     // Contenido del encabezado
    String estado;        // Estado del encabezado (Correcto/Error)
    String informacion;   // Información adicional sobre el estado del encabezado

    // Getters y setters para los atributos
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

    /**
     * Constructor que inicializa la clase con la URL a analizar y el ID del análisis.
     * 
     * @param url URL de la página web.
     * @param idAnalisis ID del análisis asociado.
     */
    public ComprobarEncabezados(String url, String idAnalisis) {
        this.url = url;
        this.idAnalisis = idAnalisis;
    }

    /**
     * Método para comprobar los encabezados (H1, H2, H3, H4) de la página web.
     * - Descarga el HTML de la URL especificada.
     * - Verifica la presencia y la estructura de los encabezados.
     * - Inserta los resultados en la base de datos.
     */
    public void comprobar() {
        try {
            // Conecta a la URL y descarga el contenido HTML
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .get();

            // Comprobar encabezados H1
            String currentH1 = null;
            Elements h1Elements = doc.select("h1");
            if (h1Elements.isEmpty()) {
                // No se encontró ningún H1
                System.out.println("¡¡ERROR!! No se encontraron encabezados H1 en la página.");
                estado = "Error";
                contenido = "No se encontraron encabezados H1";
                informacion = "No se encontró el H1";
                InsertarEncabezados.insertaEncabezados(idAnalisis, "H1", contenido, estado, informacion);
            } else {
                // Procesar cada H1 encontrado
                System.out.println("Encabezados H1:");
                for (Element h1 : h1Elements) {
                    if (h1.text().isEmpty()) {
                        // H1 vacío
                        System.out.println("ERROR: H1 está vacío");
                        contenido = "H1 vacío";
                        estado = "Error";
                        informacion = "El H1 está vacío";
                    } else {
                        // H1 correcto
                        System.out.println("Correcto");
                        contenido = h1.text();
                        estado = "Correcto";
                        informacion = "H1 correcto";
                    }
                    System.out.println("--> " + h1.text());
                    currentH1 = h1.text();
                    InsertarEncabezados.insertaEncabezados(idAnalisis, "H1", contenido, estado, informacion);
                }
            }

            // Comprobar encabezados H2, H3, H4
            Elements headers = doc.select("h2, h3, h4");
            String currentH2 = null; // Último H2 procesado
            String currentH3 = null; // Último H3 procesado
            String currentH4 = null; // Último H4 procesado

            for (Element header : headers) {
                if (header.tagName().equals("h2")) {
                    // Procesar H2
                    System.out.println("H2: " + header.text());
                    currentH2 = header.text();
                    nivel = "H2";
                    contenido = currentH2;
                    if (currentH1 != null) {
                        estado = "Correcto";
                        informacion = "Estructura H2 correcta";
                    } else {
                        estado = "Error";
                        informacion = "El H2 no es hijo de un H1";
                    }
                } else if (header.tagName().equals("h3")) {
                    // Procesar H3
                    System.out.println("H3: " + header.text());
                    currentH3 = header.text();
                    nivel = "H3";
                    contenido = currentH3;
                    if (currentH2 != null) {
                        estado = "Correcto";
                        informacion = "Estructura H3 correcta";
                    } else {
                        estado = "Error";
                        informacion = "El H3 no es hijo de un H2";
                    }
                } else if (header.tagName().equals("h4")) {
                    // Procesar H4
                    System.out.println("H4: " + header.text());
                    currentH4 = header.text();
                    nivel = "H4";
                    contenido = currentH4;
                    if (currentH3 != null) {
                        estado = "Correcto";
                        informacion = "Estructura H4 correcta";
                    } else {
                        estado = "Error";
                        informacion = "El H4 no es hijo de un H3";
                    }
                }
                // Insertar el encabezado en la base de datos
                InsertarEncabezados.insertaEncabezados(idAnalisis, nivel, contenido, estado, informacion);
            }
        } catch (IOException e) {
            // Manejo de errores al intentar conectar con la URL
            System.out.println("Error al comprobar los encabezados: " + e.getMessage());
        }
    }
}
