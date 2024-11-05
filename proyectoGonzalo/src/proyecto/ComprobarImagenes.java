/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import baseDatos.InsertarEncabezados;
import baseDatos.InsertarImagenes;
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
    String idAnalisis;
    String ruta_imagen,alt_texto,estado_alt;
    
    
    
     String rojo = "\u001B[31m"; //color rojo del mensaje en consola
    String verde = "\u001B[32m"; //color rojo del mensaje en consola

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

    public ComprobarImagenes(String url, String idAnalisis) {
        this.url = url;
        this.idAnalisis = idAnalisis;
    }
    
    
    public void comprobar(){
        //  Imágenes con el atributo "alt" o "title"
        // Descargar y analizar el HTML de la página web
            try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36").get();
        //.get();
            Elements imgElements = doc.select("img");
            System.out.println("Imágenes (alt y title):");
            for (Element img : imgElements) {
                
                ruta_imagen = img.absUrl("src");
                alt_texto = img.attr("alt");
               // String titleText = img.attr("title");
               
                // Filtrar imágenes en formato Base64
            if (ruta_imagen.startsWith("data:image/") || ruta_imagen.isEmpty()) {
                System.out.println("Imagen en Base64 ignorada: " + ruta_imagen);
                continue; // Saltar esta iteración si es una imagen en Base64
            }
               
                System.out.println("ruta: " + ruta_imagen);
                System.out.println("Imagen: alt=\"" + alt_texto + "\"");
                
                if (alt_texto.length()==0){
                    System.out.println(rojo+"Error: Alt vacio");
                    estado_alt = "Error";
                }else{
                    System.out.println(verde+"Correcto");
                    estado_alt = "Correcto";
                }
                
                //insertaImagenes(String id_analisis, String ruta_imagen, String alt_texto, String estado_alt)
                InsertarImagenes.insertaImagenes(idAnalisis, ruta_imagen, alt_texto, estado_alt);
            }
        } catch (IOException iOException) {
        }
    
    }
    
}
