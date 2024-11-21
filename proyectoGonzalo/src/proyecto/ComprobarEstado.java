package proyecto;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase para comprobar el estado HTTP de una URL.
 * Utiliza `HttpURLConnection` para realizar una solicitud HTTP y obtener
 * el código y mensaje de estado.
 * Autor: Gonzalo Román Márquez
 */
public class ComprobarEstado {

    // Atributos
    private String url;         // URL a comprobar
    private int statusCode;     // Código de estado HTTP (ejemplo: 200, 404)
    private String statusMessage; // Mensaje de estado HTTP (ejemplo: "OK", "Not Found")

    // Constructor
    public ComprobarEstado(String url) {
        this.url = url;
    }

    // Métodos getters y setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Comprueba el estado HTTP de la URL.
     *
     * @return true si el estado HTTP es 200 (OK), false en caso contrario.
     */
    public boolean comprobar() {
        try {
            // Crear objeto URL
            URL urlEstado = new URL(url);

            // Abrir la conexión
            HttpURLConnection connection = (HttpURLConnection) urlEstado.openConnection();
            connection.setRequestMethod("GET");

            // Configurar cabeceras HTTP para evitar bloqueos y simular un navegador real
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Upgrade-Insecure-Requests", "1");

            // Seguir redirecciones automáticamente
            connection.setInstanceFollowRedirects(true);

            // Obtener el código y mensaje de respuesta HTTP
            statusCode = connection.getResponseCode();
            statusMessage = connection.getResponseMessage();

            // Cerrar la conexión
            connection.disconnect();

            // Devolver true si el código de estado es 200 (OK)
            return statusCode == 200;

        } catch (IOException e) {
            // Manejar el error de conexión
            System.out.println("Error de conexión: " + e.getMessage());
            statusCode = -1; // Código de error no definido
            statusMessage = "Error de conexión";
            return false;
        }
    }

    /**
     * Devuelve el código de estado HTTP de la última comprobación.
     *
     * @return Código de estado HTTP.
     */
    public int devolverCodigoEstado() {
        return statusCode;
    }

    /**
     * Devuelve el mensaje de estado HTTP de la última comprobación.
     *
     * @return Mensaje de estado HTTP.
     */
    public String devolverMensaje() {
        return statusMessage;
    }
}
