/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.io.IOException;
import java.lang.System.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author gonzaloromanmarquez
 */
public class ComprobarEstructura {
    String url;
    
     String rojo = "\u001B[31m"; //color rojo del mensaje en consola
    String verde = "\u001B[32m"; //color rojo del mensaje en consola

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public ComprobarEstructura(String url) {
            this.url = url;
        }
    
    public void comprobar(){     





try {
            
            // Descargar y analizar el HTML de la página web
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
        .get();
            
            String title = doc.title();
           // Elements metaDescription = doc.select("meta[name=description]");
            
            
            System.out.println("Título de la página: \n" + title);
           // System.out.println("description de la página: " + description);
           
            Element metaDescription = doc.selectFirst("meta[name=description]");
            String description = (metaDescription != null) ? metaDescription.attr("content") : "No tiene meta descripción";

            System.out.println("Meta Descripion: "+description);
           String currentH1 = null;
           
           Elements h1Elements = doc.select("h1");
            if (h1Elements.isEmpty()) {
                System.out.println(rojo+"¡¡ERROR!! No se encontraron encabezados H1 en la página.");
            } else {
                System.out.println("\nEncabezados H1:");
                for (Element h1 : h1Elements) {
                    if (h1.text().length()==0){
                        System.out.println(rojo+"ERROR h1 está vacío");
                    }else{
                        System.out.println(verde+"Correcto");
                    }
                    System.out.println("-->" + h1.text());
                    currentH1 = h1.text();
                }
            }
            
           /* Elements h2Elements = doc.select("h2");
             Elements h3Elements = doc.select("h3");
              Elements h4Elements = doc.select("h4");
            System.out.println("\nEncabezados H2:");
            for (Element h2 : h2Elements) {
                System.out.println("h2 "+h2.text());
                
               
                for (Element h3 : h3Elements) {
                System.out.println("--h3-----"+h3.text());
                }
               
                for (Element h4 : h4Elements) {
                System.out.println("-------h4-------"+h4.text());
                
                }
            }
            
           // Elements h3Elements = doc.select("h3");
            System.out.println("\nEncabezados H3:");
            for (Element h3 : h3Elements) {
                System.out.println(h3.text());
            }*/
           
           // Seleccionamos todos los encabezados H2, H3 y H4
            Elements headers = doc.select("h2, h3, h4");

            // Variable para seguir el contexto del último H2
            String currentH2 = null;
            

            for (Element header : headers) {
                // Si encontramos un H2, lo guardamos como el encabezado actual
               /* if (header.tagName().equals("h2")) {
                    currentH2 = header.text(); // Guardamos el texto del H2
                    System.out.println("\nH2: " + currentH2);
                }*/
                
                if (header.tagName().equals("h2") ) {
                    System.out.println("   H2: " + header.text());
                    currentH2 = header.text();
                    if(currentH1 != null){
                        System.out.println(verde+"correcto");
                    }else{
                        System.out.println(rojo+"!!ERROR!!");
                    }
                }
                // Si encontramos un H3, lo mostramos como hijo del H2 actual
                else if (header.tagName().equals("h3") ) {
                    System.out.println("           H3: " + header.text());
                    if(currentH2 != null){
                        System.out.println("        "+verde+"correcto");
                    }else{
                        System.out.println("        "+rojo+"!!ERROR!!");
                    }
                } 
                // Si encontramos un H4, lo mostramos como hijo del último H3
                else if (header.tagName().equals("h4") ) {
                    System.out.println("      H4: " + header.text());
                }
            }
    
        } catch (IOException ex) {
            System.out.println("se ha producido un error en la clase comprobarEstructura");
        }


    }
    
    
}
