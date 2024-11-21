package proyecto;

import baseDatos.InsertarEnlaces;
import baseDatos.InsertarEnlacesResumen;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase para comprobar los enlaces de una página web.
 * Utiliza Jsoup para analizar el HTML y verifica el estado de cada enlace.
 * Autor: Gonzalo Román Márquez
 */
public class ComprobarEnlaces {

    // Atributos
    String url;                     // URL de la página web a analizar
    String dominio_y_protocolo;     // Dominio y protocolo base (ejemplo: https://www.ejemplo.com)
    String idAnalisis;              // ID del análisis actual para la base de datos
    String url_enlace, tipo, anchor_text, estadoResumen; // Variables para el enlace actual
    String estado = "Correcto";     // Estado inicial

    // Contadores para diferentes tipos de enlaces
    int internalLinks = 0, externalLinks = 0, relativeLinks = 0, enlacesRotos = 0, enlacesCorrectos = 0;

    // Colores para mensajes en consola
    String rojo = "\u001B[31m"; // Color rojo
    String verde = "\u001B[32m"; // Color verde

    // Constructor
    public ComprobarEnlaces(String url, String dominio_y_protocolo, String idAnalisis) {
        this.url = url;
        this.dominio_y_protocolo = dominio_y_protocolo;
        this.idAnalisis = idAnalisis;
    }

    /**
     * Método principal para comprobar enlaces.
     * - Descarga el contenido HTML de la URL.
     * - Identifica todos los enlaces (<a href>).
     * - Verifica el estado de cada enlace.
     */
    public void comprobar() throws InterruptedException {
        // Pool de hilos para procesar enlaces en paralelo
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            // Descargar y analizar la página HTML
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .timeout(5000).get();

            Elements links = doc.select("a[href]"); // Seleccionar todos los enlaces

            // Procesar cada enlace
            for (Element link : links) {
                String href = link.attr("href");

                // Ignorar enlaces no válidos (tel:, mailto:)
                if (href.startsWith("tel:") || href.startsWith("mailto:")) {
                    System.out.println("ENLACE NO VÁLIDO: " + href);
                    continue;
                }

                // Crear una tarea para procesar el enlace
                executor.submit(() -> {
                    try {
                        verificarEnlace(href, link); // Verificar el enlace
                    } catch (IOException e) {
                        System.out.println("Error al verificar enlace: " + href);
                    }
                });
            }

            executor.shutdown(); // No aceptar más tareas
            executor.awaitTermination(10, TimeUnit.SECONDS); // Esperar la finalización de todas las tareas

            // Imprimir resultados finales
            System.out.println("Enlaces internos: " + internalLinks);
            System.out.println("Enlaces externos: " + externalLinks);
            System.out.println("Enlaces internos relativos: " + relativeLinks);
            System.out.println("Enlaces rotos: " + enlacesRotos);
            System.out.println("Enlaces correctos: " + enlacesCorrectos);

            // Determinar estado general
            if (enlacesRotos > 0) {
                estadoResumen = "Error";
            }

            // Insertar resumen en la base de datos
            InsertarEnlacesResumen.insertaEnlacesResumen(idAnalisis, String.valueOf(internalLinks),
                    String.valueOf(externalLinks), String.valueOf(enlacesRotos), estadoResumen);

        } catch (IOException e) {
            System.out.println("Error al procesar la página: " + e.getMessage());
        }
    }

    /**
     * Verifica el estado de un enlace específico.
     *
     * @param href URL del enlace.
     * @param link Elemento del enlace (<a>).
     */
    private void verificarEnlace(String href, Element link) throws IOException {
        if (href.isEmpty()) {
            System.out.println("Enlace vacío ignorado.");
            return;
        }

        // Ajustar enlaces relativos y categorizarlos
        if ("/".equals(href)) {
            href = dominio_y_protocolo + href;
            internalLinks++;
            tipo = "INTERNO";
            url_enlace = href;
            anchor_text = link.text();
            estado = compruebaRoto(href);
        } else if (href.startsWith(dominio_y_protocolo) && !href.startsWith("#")) {
            internalLinks++;
            tipo = "INTERNO";
            url_enlace = href;
            anchor_text = link.text();
            estado = compruebaRoto(href);
        } else if (href.startsWith("http") && !href.startsWith("#") && !href.startsWith(dominio_y_protocolo)) {
            externalLinks++;
            tipo = "EXTERNO";
            url_enlace = href;
            anchor_text = link.text();
            estado = compruebaRoto(href);
        } else if (!href.startsWith("http") && !href.startsWith("#") && !href.startsWith(dominio_y_protocolo)) {
            relativeLinks++;
            internalLinks++;
            tipo = "INTERNO";
            url_enlace = dominio_y_protocolo + href;
            anchor_text = link.text();
            estado = compruebaRoto(dominio_y_protocolo + href);
        }

        // Insertar información del enlace en la base de datos
        InsertarEnlaces.insertaEnlaces(idAnalisis, url_enlace, tipo, anchor_text, estado);
    }

    /**
     * Comprueba si un enlace está roto.
     *
     * @param href URL del enlace.
     * @return "Correcto" si el enlace es válido, "Error" si está roto.
     */
    public String compruebaRoto(String href) {
        ComprobarEstado estadoURL = new ComprobarEstado(href);

        if (!estadoURL.comprobar()) {
            enlacesRotos++;
            System.out.println(rojo + "INCORRECTO: " + estadoURL.devolverCodigoEstado());
            return "Error";
        } else {
            enlacesCorrectos++;
            System.out.println(verde + "CORRECTO: " + estadoURL.devolverCodigoEstado());
            return "Correcto";
        }
    }
}
