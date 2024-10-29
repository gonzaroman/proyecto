/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        } catch (IOException iOException) {
        }
   
    
    }
    
    
    public void calcularDensidad(){
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36").get();
        //.get();
            // Extraer todo el texto visible de la página
            String texto_de_la_url = doc.text();
            
            //dividimos el texto en palabras
            String[] palabras = texto_de_la_url.toLowerCase().split("[\\P{L}]+");
            
             Map<String, Integer> ocurrenciasPalabras = new HashMap<>();
             
             // Contar las ocurrencias de cada palabra
        for (String palabra : palabras) {
            ocurrenciasPalabras.put(palabra, ocurrenciasPalabras.getOrDefault(palabra, 0) + 1);
        }
        
         // Calcular el total de palabras
        int totalPalabras = palabras.length;
        
        //convertimos el map en una lista para poder ordenala 
        List<Map.Entry<String, Integer>> listaOcurrencias = new ArrayList<>(ocurrenciasPalabras.entrySet());

        
        // Ordenar la lista por el porcentaje de densidad en orden descendente
            listaOcurrencias.sort((entry1, entry2) -> {
                double porcentaje1 = ((double) entry1.getValue() / totalPalabras) * 100;
                double porcentaje2 = ((double) entry2.getValue() / totalPalabras) * 100;
                return Double.compare(porcentaje2, porcentaje1); // Ordenar de mayor a menor
            });
         
        // Mostrar el resultado
            System.out.println("Total de palabras: " + totalPalabras);
            System.out.println("Densidad de palabras en el texto (ordenado de mayor a menor):");
            
            int palabrasAmostrar = 0;
             for (Map.Entry<String, Integer> entry : listaOcurrencias) {
                String palabra = entry.getKey();
                int ocurrencias = entry.getValue();
                float porcentaje = ((float) ocurrencias / totalPalabras) * 100;
                porcentaje = Math.round(porcentaje * 100.0f) / 100.0f; //quitamos todos los decimales y dejamos solo dos

                //System.out.printf("%s: %.2f%%\n", palabra, porcentaje);
                palabrasAmostrar++;
                 System.out.println(palabrasAmostrar+" "+palabra+" "+porcentaje+"%  --> "+ocurrencias+" veces");
                if(palabrasAmostrar >=50) break;
                
            }

            
           
            
        } catch (IOException iOException) {
        }
    
    }
    
}
