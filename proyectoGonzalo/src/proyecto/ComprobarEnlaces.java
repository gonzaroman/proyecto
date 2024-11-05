/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import baseDatos.InsertarEnlaces;
import baseDatos.InsertarEnlacesResumen;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
    String idAnalisis;
    String url_enlace, tipo, anchor_text, estadoResumen;
    String estado = "Correcto";

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

   
    public ComprobarEnlaces(String url, String dominio_y_protocolo, String idAnalisis) {
        this.url = url;
        this.dominio_y_protocolo = dominio_y_protocolo;
        this.idAnalisis = idAnalisis;
    }

    int internalLinks = 0, externalLinks = 0, relativeLinks = 0, enlacesRotos = 0, enlacesCorrectos = 0;

    String rojo = "\u001B[31m"; //color rojo del mensaje en consola
    String verde = "\u001B[32m"; //color rojo del mensaje en consola

    public void comprobar() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(10); // Limitar a 10 hilos simultáneos

        //devuelve true si la url está correcta, si es un 404 da error
        // Cantidad de enlaces internos y externos
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36").get();
            //.get();
            Elements links = doc.select("a[href]");

            for (Element link : links) {
                String href = link.attr("href");

                // Filtrar enlaces no HTTP/HTTPS
                if (href.startsWith("tel:") || href.startsWith("mailto:")) {
                    System.out.println("ENLACE NO VÁLIDO: " + href);
                    continue; // Saltar estos enlaces
                }

                // Crear una tarea para cada enlace
                executor.submit(() -> {
                    try {
                        verificarEnlace(href, link);
                    } catch (IOException e) {
                        System.out.println("Error al verificar enlace: " + href);
                    }
                });

            }//fin del for

            executor.shutdown(); // No aceptar más tareas
            executor.awaitTermination(5, TimeUnit.MINUTES); // Esperar hasta que todas las tareas finalice

            System.out.println("Enlaces internos: " + internalLinks);
            System.out.println("Enlaces externos: " + externalLinks);
            System.out.println("Enlaces internos relativos: " + relativeLinks);
            System.out.println("Enlaces apuntan a url rota: " + enlacesRotos);
            System.out.println("Enlaces apuntan url correcta: " + enlacesCorrectos);
            
            if(enlacesRotos>0){
                estadoResumen = "Error";
            }

            InsertarEnlacesResumen.insertaEnlacesResumen(idAnalisis, internalLinks + "", externalLinks + "", enlacesRotos + "", estadoResumen);

        } catch (IOException iOException) {

            System.out.println("algun enlace raro");
        }

    }

    private void verificarEnlace(String href, Element link) throws IOException {
       
        if (href.isEmpty()) {
            System.out.println("Enlace vacío ignorado.");
            return;
        }
    
         // Ajustar enlace raíz para que sea una URL completa si es "/"
    if ("/".equals(href)) {
        href = dominio_y_protocolo + href; // Construir URL completa para la raíz
        internalLinks++;
            System.out.println("INTERNO:");
            tipo = "INTERNO";
            System.out.println(href);
            url_enlace = href;
            System.out.println("Anchor text: " + link.text());
            anchor_text = link.text();
            estado = compruebaRoto(href);
    }
        
        if (href.startsWith(dominio_y_protocolo) && !href.startsWith("#")) {
            internalLinks++;
            System.out.println("INTERNO:");
            tipo = "INTERNO";
            System.out.println(href);
            url_enlace = href;
            System.out.println("Anchor text: " + link.text());
            anchor_text = link.text();
            estado = compruebaRoto(href);

        } else if (href.startsWith("http") && !href.startsWith("#") && !href.startsWith(dominio_y_protocolo)) {
            externalLinks++;

            System.out.println("EXTERNO");
            tipo = "EXTERNO";
            System.out.println(href);
            url_enlace = href;
            System.out.println("Anchor text: " + link.text());
            anchor_text = link.text();
            estado = compruebaRoto(href);

        } else if (!href.startsWith("http") && !href.startsWith("#") && !href.startsWith(dominio_y_protocolo)) {
            System.out.println("INTERNO/RELATIVO");
            System.out.println(href);
            //  System.out.println("prueba print dominio: "+dominio_y_protocolo);
            if (!href.startsWith("/")) {
                href = "/" + href;
            }
            System.out.println("ruta completa: " + dominio_y_protocolo + href);
            System.out.println("Anchor text: " + link.text());
            relativeLinks++;
            internalLinks++;

            tipo = "INTERNO";
            url_enlace = dominio_y_protocolo + href;
            anchor_text = link.text();

            estado = compruebaRoto(dominio_y_protocolo + href);

        }

        InsertarEnlaces.insertaEnlaces(idAnalisis, url_enlace, tipo, anchor_text, estado);
    }

    public String compruebaRoto(String href) {
        ComprobarEstado estadoURL = new ComprobarEstado(href);

        System.out.println(estadoURL.comprobar());
        // estadoURL.devolverCodigoEstado();

        if (!estadoURL.comprobar()) {
            enlacesRotos++;
            System.out.println(rojo + "INCORRECTO: " + estadoURL.devolverCodigoEstado());
            return "Error";
        } else {
            System.out.println(verde + "CORRECTO: " + estadoURL.devolverCodigoEstado());
            enlacesCorrectos++;
            return "Correcto";
        }

    }

}
