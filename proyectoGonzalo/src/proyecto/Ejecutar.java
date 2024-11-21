package proyecto;

import baseDatos.ConexionBaseDatos;
import baseDatos.InsertarAnalisis;
import baseDatos.InsertarEstadoUrl;
import baseDatos.InsertarMetaDescription;
import baseDatos.InsertarMetaTitle;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal encargada de ejecutar el análisis completo de una URL.
 * Realiza verificaciones de estado, metaetiquetas, encabezados, imágenes, enlaces y texto.
 */
public class Ejecutar {

    /**
     * Método principal para ejecutar el análisis completo de una URL.
     *
     * @param url la URL que será analizada
     * @return true si la URL es válida y se realiza el análisis correctamente, false si la URL no es válida o ocurre un error
     * @throws MalformedURLException si la URL no tiene un formato válido
     */
    public static boolean hacer(String url) throws MalformedURLException {

        String dominio = ""; // Dominio extraído de la URL
        String rojo = "\u001B[31m"; // Color rojo para mensajes de error en la consola
        String verde = "\u001B[32m"; // Color verde para mensajes de éxito en la consola

        // Crear y verificar la conexión a la base de datos
        ConexionBaseDatos.createNewTable();

        // Procesar la URL ingresada
        URL url1 = new URL(url);
        dominio = url1.getHost(); // Extraer el dominio de la URL
        System.out.println("dominio: " + dominio);

        String protocolo = url1.getProtocol(); // Obtener el protocolo (http/https)
        System.out.println("protocolo: " + protocolo);

        String dominio_y_protocolo = protocolo + "://" + dominio; // Construir el dominio completo con protocolo
        System.out.println("dominio y protocolo: " + dominio_y_protocolo);

        // Verificar el estado HTTP de la URL
        ComprobarEstado estadoURL = new ComprobarEstado(url);
        estadoURL.comprobar(); // Comprueba si la URL responde correctamente
        String codigoEstado = estadoURL.devolverCodigoEstado() + ""; // Obtener el código HTTP (ej. 200, 404)

        System.out.println("devuelve código estado: " + estadoURL.devolverCodigoEstado());
        System.out.println("devuelve mensaje: " + estadoURL.devolverMensaje());

        // Si la URL responde correctamente
        if (estadoURL.comprobar()) {
            System.out.println(verde + "Conexión correcta: 200 OK");

            // Insertar un nuevo análisis en la base de datos y obtener el ID generado
            int id_analisis = InsertarAnalisis.insertaAnalisis(url, dominio, protocolo, dominio_y_protocolo, "Correcto");
            String idAnalisis = String.valueOf(id_analisis); // Convertir el ID a String para uso en las claves foráneas

            // Guardar el estado HTTP de la URL en la base de datos
            InsertarEstadoUrl.insertaEstadoUrl(idAnalisis, codigoEstado, estadoURL.devolverMensaje(), "Correcto");

            // Analizar y guardar el meta title
            ComprobarTitle title = new ComprobarTitle(url);
            title.comprobar(); // Realiza el análisis del título
            InsertarMetaTitle.insertaMetaTitleUrl(idAnalisis, title.getTitle(), title.getEstado_title());

            // Analizar y guardar la meta description
            ComprobarDescription description = new ComprobarDescription(url);
            description.comprobar(); // Realiza el análisis de la descripción
            InsertarMetaDescription.insertaMetaDescription(idAnalisis, description.getDescription(), description.getEstadoDescription());

            // Analizar y guardar los encabezados
            ComprobarEncabezados encabezados = new ComprobarEncabezados(url, idAnalisis);
            encabezados.comprobar(); // Realiza el análisis de encabezados (H1, H2, etc.)

            // Analizar y guardar las imágenes
            ComprobarImagenes imagenesURL = new ComprobarImagenes(url, idAnalisis);
            imagenesURL.comprobar();

            // Analizar y guardar los enlaces
            ComprobarEnlaces enlaces = new ComprobarEnlaces(url, dominio_y_protocolo, idAnalisis);
            try {
                enlaces.comprobar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Ejecutar.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Analizar y guardar el texto y su densidad
            ComprobarTexto texto = new ComprobarTexto(url, idAnalisis);
            texto.calcularDensidad();

            // Retorna true si el análisis se realizó correctamente
            return true;

        } else {
            // Si la URL no responde correctamente (ej. 404), mostrar mensaje de error
            System.out.println(rojo + "Error: URL no encontrada: 404");
            return false;
        }
    }
}
