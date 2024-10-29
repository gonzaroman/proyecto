/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author gonzaloromanmarquez
 */
public class ComprobarDescription {
    String url;
    String description;
    String estadoDescription;

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

    public ComprobarDescription(String url) {
        this.url = url;
    }
    
    public void comprobar(){  
        
          try {

            // Descargar y analizar el HTML de la página web
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36").get();
                  //  .get();

            //control analisis H1
            Element metaDescription = doc.selectFirst("meta[name=description]");
            
         if (metaDescription != null) {    
            
            description = metaDescription.attr("content");
            
            
        
            if (description.isEmpty() ) {
                //title vacio
                estadoDescription = "Error";
                System.out.println("Error description");
            } else {
                //title correcto
                estadoDescription = "Correcto";
                System.out.println(description);
                System.out.println("Description de la página: " + description);
                System.out.println("Correcto");
            }
        } else {
            estadoDescription = "Error";
            System.out.println("Error: No se encontró la meta descripción");
        }    
            
            
        } catch (IOException iOException) {
              System.out.println("Errror al comprobar la Description");
        }
    
    } 
    
}
