/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author gonzaloromanmarquez
 */
public class ComprobarTexto {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ComprobarTexto(String url) {
        this.url = url;
    }
    
    public void comprobar() {
        System.out.println("------------------TEXTO DE LA URL------------------------");

        try {
            Document doc = Jsoup.connect(url).get();
            // Extraer todo el texto visible de la página
            String texto_de_la_url = doc.text();
            
           
            
            System.out.println(texto_de_la_url);
            
          //   System.out.println(doc.html());
        } catch (IOException iOException) {
        }
   
    
    }
    
}
