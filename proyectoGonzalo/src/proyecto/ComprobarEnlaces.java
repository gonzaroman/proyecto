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
public class ComprobarEnlaces {
    String url;
    String dominio_y_protocolo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDominio_y_protocolo() {
        return dominio_y_protocolo;
    }

    public void setDominio_y_protocolo(String dominio_y_protocolo) {
        this.dominio_y_protocolo = dominio_y_protocolo;
    }

    public ComprobarEnlaces(String url, String dominio_y_protocolo) {
        this.url = url;
        this.dominio_y_protocolo = dominio_y_protocolo;
    }

     int internalLinks = 0, externalLinks = 0, relativeLinks = 0,enlacesRotos=0,enlacesCorrectos=0;
     
     String rojo = "\u001B[31m"; //color rojo del mensaje en consola
    String verde = "\u001B[32m"; //color rojo del mensaje en consola
    
    public void comprobar(){
        
         //devuelve true si la url está correcta, si es un 404 da error
        
        // Cantidad de enlaces internos y externos
           try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
        .get();
            Elements links = doc.select("a[href]");
           
            for (Element link : links) {
                String href = link.attr("href");
                
                // Filtrar enlaces no HTTP/HTTPS
    if (href.startsWith("tel:") || href.startsWith("mailto:")) {
        System.out.println("ENLACE NO VÁLIDO: " + href);
        continue; // Saltar estos enlaces
    }
                
                
                
                
                if (href.startsWith(dominio_y_protocolo) && !href.startsWith("#")) {
                    internalLinks++;
                    System.out.println("INTERNO:");
                    System.out.println(href);
                    System.out.println("Anchor text: "+link.text());
                    compruebaRoto(href);
                    
                    
                } else if (href.startsWith("http") && !href.startsWith("#") && !href.startsWith(dominio_y_protocolo)) {
                    externalLinks++;
                    
                    System.out.println("EXTERNO");
                    System.out.println(href);
                     System.out.println("Anchor text: "+link.text());
                    
                    compruebaRoto(href);
                    
                    
                } else if (!href.startsWith("http") && !href.startsWith("#") && !href.startsWith(dominio_y_protocolo)) {
                    System.out.println("RELATIVO");
                    System.out.println(href);
                  //  System.out.println("prueba print dominio: "+dominio_y_protocolo);
                  if (!href.startsWith("/")) {
                         href = "/" + href;
                     }
                    System.out.println("ruta completa: "+dominio_y_protocolo+href);
                     System.out.println("Anchor text: "+link.text());
                    relativeLinks++;
                    internalLinks++;
                    
                   /* ComprobarEstado estadoURL = new ComprobarEstado(dominio_y_protocolo+href);
         
                     System.out.println(estadoURL.comprobar());
                     
                     if(!estadoURL.comprobar()){
                         enlacesRotos++;
                     }else{
                         enlacesCorrectos++;
                     }*/
                    
                   compruebaRoto(dominio_y_protocolo+href);
                    
                }
                
               
                
                
            }
            System.out.println("\nEnlaces internos: " + internalLinks);
            System.out.println("Enlaces externos: " + externalLinks);
            System.out.println("Enlaces internos relativos: " + relativeLinks);
               System.out.println("Enlaces apuntan a url rota: "+enlacesRotos);
               System.out.println("Enlaces apuntan url correcta: "+enlacesCorrectos);
        } catch (IOException iOException) {
            
               System.out.println("algun enlace raro");
        }
           
           
           
           
        
    
    }
    
    
    public void compruebaRoto(String href){
        ComprobarEstado estadoURL = new ComprobarEstado(href);
         
                     System.out.println(estadoURL.comprobar());
                     estadoURL.devolverCodigoEstado();
                     
                     if(!estadoURL.comprobar()){
                         enlacesRotos++;
                         System.out.println(rojo+"INCORRECTO: "+estadoURL.devolverCodigoEstado());
                     }else{
                         System.out.println(verde+"CORRECTO: "+estadoURL.devolverCodigoEstado());
                         enlacesCorrectos++;
                     }
    
    
    }
    
    
}
