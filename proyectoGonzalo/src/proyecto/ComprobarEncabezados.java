/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import baseDatos.InsertarEncabezados;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author gonzaloromanmarquez
 */
public class ComprobarEncabezados {
     String url;
     String idAnalisis;
     String nivel,contenido,estado;

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

    public ComprobarEncabezados(String url, String idAnalisis) {
        this.url = url;
        this.idAnalisis = idAnalisis;
    }
     
    
     
     public void comprobar(){  
        
          try {

            // Descargar y analizar el HTML de la página web
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36").get();
                   // .get();

            //control analisis H1
            String currentH1 = null;
           
           Elements h1Elements = doc.select("h1");
            if (h1Elements.isEmpty()) {
                System.out.println("¡¡ERROR!! No se encontraron encabezados H1 en la página.");
                estado="Error";
                contenido="¡¡ERROR!! No se encontraron encabezados H1";
                InsertarEncabezados.insertaEncabezados(idAnalisis, "H1", contenido, estado);
            } else {
                System.out.println("Encabezados H1:");
                for (Element h1 : h1Elements) {
                    if (h1.text().length()==0){
                        System.out.println("ERROR h1 está vacío");
                        contenido = "ERROR H1 está vacío";
                        estado = "Error";
                    }else{
                        System.out.println("Correcto");
                        contenido = h1.text();
                    }
                    System.out.println("-->" + h1.text());
                    currentH1 = h1.text();
                    InsertarEncabezados.insertaEncabezados(idAnalisis, "H1", contenido, estado);
                }
            }
            
            
            
             // Seleccionamos todos los encabezados H2, H3 y H4
            Elements headers = doc.select("h2, h3, h4");

            // Variable para seguir el contexto del último H2
            String currentH2 = null;
            String currentH3 = null;
            String currentH4 = null;

            for (Element header : headers) {
                // Si encontramos un H2, lo guardamos como el encabezado actual
           
                
                if (header.tagName().equals("h2") ) {
                    System.out.println("   H2: " + header.text());
                    currentH2 = header.text();
                    nivel = "H2";
                    contenido = currentH2;
                    if(currentH1 != null){
                        System.out.println("Correcto");
                        estado = "Correcto";
                    }else{
                        System.out.println("!!ERROR!!");
                        estado = "Error";
                    }
                }
                // Si encontramos un H3, lo mostramos como hijo del H2 actual
                else if (header.tagName().equals("h3") ) {
                    System.out.println("           H3: " + header.text());
                    currentH3 = header.text();
                    nivel = "H3";
                    contenido = currentH3;
                    
                    if(currentH2 != null){
                        System.out.println("        Correcto");
                         estado = "Correcto";
                    }else{
                        System.out.println("        !!ERROR!!");
                        estado = "Error";
                    }
                } 
                // Si encontramos un H4, lo mostramos como hijo del último H3
                else if (header.tagName().equals("h4") ) {
                    System.out.println("      H4: " + header.text());
                    
                    currentH4 = header.text();
                    nivel = "H4";
                    contenido = currentH4;
                    
                    if(currentH3 != null){
                        System.out.println("        Correcto");
                         estado = "Correcto";
                    }else{
                        System.out.println("        !!ERROR!!");
                        estado = "Error";
                    }
                    
                    
                    
                }
                
                InsertarEncabezados.insertaEncabezados(idAnalisis, nivel, contenido, estado);
            }
            
           
            
            
        } catch (IOException iOException) {
              System.out.println("Errror al comprobar la Description");
        }
    
    } 
}
