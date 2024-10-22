/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection.Response;


import java.net.URL;
import org.jsoup.Connection;


/**
 *
 * @author gonzaloromanmarquez
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) throws MalformedURLException {
         String url ="";
         String dominio="";
        // TODO code application logic here
         //url = "https://neoattack.com/";
         //url = "https://www.snsmarketing.es/";
         // url = "https://www.snsmarketing.es/blog/tendencias-para-2025-chatbots-e-inteligencia-artificial-en-el-marketing-digital/";
         //url = "https://iesaugustobriga.educarex.es/";
         //url = "https://neoattack.com/agencia-de-fgjfggmarketing-de-contenidos/"; //esta es para probar errores 404
        //  url ="http://www.template-joomspirit.com"; // redireccion 301
        
        String rojo = "\u001B[31m"; //color rojo del mensaje en consola
    String verde = "\u001B[32m"; //color rojo del mensaje en consola
        
         
        
        //PIDE URL POR TECLADO
        PedirUrl teclado = new PedirUrl();
        url = teclado.escribir();
          
          
         
         URL url1 = new URL(url);
         dominio = url1.getHost();
         System.out.println("dominio: "+dominio);
         System.out.println(url1.getProtocol());
         String protocolo = url1.getProtocol();
         
         String dominio_y_protocolo = protocolo+"://"+dominio; 
         System.out.println("dominio y protocolo: "+dominio_y_protocolo);
         
         
         ComprobarEstado estadoURL = new ComprobarEstado(url);
         
         estadoURL.comprobar(); //devuelve true si la url está correcta, si es un 404 da error
         estadoURL.devolverCodigoEstado(); //devuelve el codigo del estado, 200, 404
         estadoURL.devolverMensaje();
         //System.out.println(estadoURL.devolverCodigoEstado());
         //System.out.println(estadoURL.devolverMensaje());
         
         if(estadoURL.comprobar()){
             System.out.println(verde+"Conexión correcta: 200 ok");
         
         
         
         ComprobarEstructura EstructuraURL = new ComprobarEstructura(url);
         EstructuraURL.comprobar();
         
         ComprobarImagenes imagenesURL = new ComprobarImagenes(url);
         imagenesURL.comprobar();
        
         ComprobarEnlaces enlaces = new ComprobarEnlaces(url,dominio_y_protocolo);
         enlaces.comprobar();
           
         ComprobarTexto texto = new ComprobarTexto(url);
        texto.comprobar();
        texto.calcularDensidad();
          
           
           
            //System.out.println(doc.html());
            
            
            
         }else{
             System.out.println(rojo+"Error: url no encontrada: 404");
             //Este error debe guardarse en base de datos
         }
            
            
        
        
    }
    
}
