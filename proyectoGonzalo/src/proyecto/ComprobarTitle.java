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
public class ComprobarTitle {
     String url;
     String title;
     String estado_title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEstado_title() {
        return estado_title;
    }

    public void setEstado_title(String estado_title) {
        this.estado_title = estado_title;
    }
    
    

    public ComprobarTitle(String url) {
        this.url = url;
    }
    
    public void comprobar(){  
        try {
            
            // Descargar y analizar el HTML de la página web
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36").get();
        //.get();
            
            
            //control analisis H1
            
            title = doc.title();
            
            if (title.isEmpty()){
                //title vacio
                estado_title = "Error";
                System.out.println("Error Title");
            }else{
                //title correcto
                estado_title = "Correcto";
                System.out.println(title);
                System.out.println("Título de la página: " + title);
                 System.out.println("Correcto");
            }
             
              
    
        } catch (IOException ex) {
            System.out.println("se ha producido un error en la clase comprobarEstructura");
        }


    }
    
    
    
    
    
    
}
