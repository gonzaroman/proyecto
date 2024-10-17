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

    
    
    public void comprobar(){
        
        // Cantidad de enlaces internos y externos
           try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            int internalLinks = 0, externalLinks = 0;
            for (Element link : links) {
                String href = link.attr("href");
                
                if (href.startsWith(dominio_y_protocolo) && !href.startsWith("#")) {
                    internalLinks++;
                    System.out.println("interno:");
                    System.out.println(href);
                } else if (href.startsWith("http") && !href.startsWith("#") && !href.startsWith(dominio_y_protocolo)) {
                    externalLinks++;
                    
                    System.out.println("externo");
                    System.out.println(href);
                } else if (!href.startsWith("http") && !href.startsWith("#") && !href.startsWith(dominio_y_protocolo)) {
                    System.out.println("otros");
                    System.out.println(href);
                    internalLinks++;
                }
                
            }
            System.out.println("\nEnlaces internos: " + internalLinks);
            System.out.println("Enlaces externos: " + externalLinks);
        } catch (IOException iOException) {
        }
        
    
    }
    
    
}
