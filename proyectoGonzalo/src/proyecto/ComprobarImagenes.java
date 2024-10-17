/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author gonzaloromanmarquez
 */
public class ComprobarImagenes {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ComprobarImagenes(String url) {
        this.url = url;
    }
    
    public void comprobar(){
        // 5. Imágenes con el atributo "alt" o "title"
        // Descargar y analizar el HTML de la página web
            try {
            Document doc = Jsoup.connect(url).get();
            Elements imgElements = doc.select("img");
            System.out.println("\nImágenes (alt y title):");
            for (Element img : imgElements) {
                
                String absUrl = img.absUrl("src");
                String altText = img.attr("alt");
                String titleText = img.attr("title");
                System.out.println("ruta: " + absUrl);
                System.out.println("Imagen: alt=\"" + altText + "\", title=\"" + titleText + "\"");
            }
        } catch (IOException iOException) {
        }
    
    }
    
}
