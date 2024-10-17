/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author gonzaloromanmarquez
 */
public class ComprobarEstado {
    
    private String url;
    int statusCode;
    String statusMessage;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ComprobarEstado(String url) {
        this.url = url;
    }
    
    
    
    
    public boolean comprobar() {

        // Obtener el código de estado HTTP
        //para conocer el estatus code la url 404, 200, etc
        try {

            // Crear objeto URL
            URL urlEstado = new URL(url);

            // Abrir la conexión
            HttpURLConnection connection = (HttpURLConnection) urlEstado.openConnection();
            connection.setRequestMethod("GET");
            statusMessage = connection.getResponseMessage();
            connection.setInstanceFollowRedirects(true); // Para seguir redirecciones

            // Obtener el código de respuesta
            statusCode = connection.getResponseCode();

            // Mostrar el código y mensaje de estado
            //System.out.println("Código de estado: " + statusCode);
            //System.out.println("Mensaje de estado: " + statusMessage);
            
            
            // Cerrar la conexión
            connection.disconnect();

            if (statusCode == 200) {
                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            // Manejar el error de conexión
            System.out.println("Error de conexión: " + e.getMessage());
            return false;
        }

    }//fin comprobar
    
     public int devolverCodigoEstado(){
            
            return statusCode;
     }
     
     public String devolverMensaje(){
          
            return statusMessage;
     }
    
    
}


/*

            


*/