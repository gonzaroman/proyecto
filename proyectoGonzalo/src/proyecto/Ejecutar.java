/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import baseDatos.ConexionBaseDatos;
import baseDatos.InsertarAnalisis;
import baseDatos.InsertarEstadoUrl;
import baseDatos.InsertarMetaDescription;
import baseDatos.InsertarMetaTitle;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author 6003262
 */
public class Ejecutar {
    
   // String url ="";
    
    
    public static void hacer(String url) throws MalformedURLException{
    
      //String url ="";
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
        //conexion base de datos
        
        ConexionBaseDatos.createNewTable();
         
        
        //PIDE URL POR TECLADO
       // PedirUrl teclado = new PedirUrl();
       // url = teclado.escribir();
          
          
         
         URL url1 = new URL(url);
         dominio = url1.getHost();
         System.out.println("dominio: "+dominio);
         System.out.println(url1.getProtocol());
         String protocolo = url1.getProtocol();
         
         String dominio_y_protocolo = protocolo+"://"+dominio; 
         System.out.println("dominio y protocolo: "+dominio_y_protocolo);
         
         
         ComprobarEstado estadoURL = new ComprobarEstado(url);
         
         estadoURL.comprobar(); //devuelve true si la url está correcta, si es un 404 da error
         String codigoEstado = estadoURL.devolverCodigoEstado()+"" ; //devuelve el codigo del estado, 200, 404
        
         estadoURL.devolverMensaje(); //devuelve mensaje "OK"
         
         System.out.println("devuelve codigo estado "+estadoURL.devolverCodigoEstado());
         System.out.println("devuelve mensaje "+estadoURL.devolverMensaje());
         
         
         if(estadoURL.comprobar()){
             System.out.println(verde+"Conexión correcta: 200 ok");
             
             
         //insertaAnalisis(String url_principal, String dominio, String protocolo, String dominio_y_protocolo, String estado_general)
         
         //aqui lo que hacemos en generar el analisis y guardamos en id_analsis el id que se ha generado, para poderlo utilizar como clave foranea en las demas consultas
         //hemos recuperado el codigo en una consulta en insetar analisis y lo devolvemos con un return, ya que es necesario para las demas tablas
         int id_analisis =  InsertarAnalisis.insertaAnalisis(url, dominio, protocolo, dominio_y_protocolo, "Correcto");
         
         //insertaEstadoUrl(String id_analisis, String estado_http, String mensaje_estado, String estado)
        // InsertarEstadoUrl.insertaEstadoUrl(1,estadoURL.devolverCodigoEstado() , estadoURL.devolverMensaje(),estadoURL.devolverMensaje() );
         
        String idAnalisis = id_analisis+"";//convertimos el id del analisis en string para poderlo enviar en la consulta como clave foranea.
        
        
        InsertarEstadoUrl.insertaEstadoUrl(idAnalisis,codigoEstado ,  estadoURL.devolverMensaje(),"Correcto");
         
        //Comprobar Meta Title
        ComprobarTitle title = new ComprobarTitle(url);
        title.comprobar();
      //  title.getTitle();
      //  title.getEstado_title();
        
        //insertar Datos Meta Title en BD
        InsertarMetaTitle.insertaMetaTitleUrl(idAnalisis, title.getTitle(), title.getEstado_title());
        
        
        //Comprobar Meta Description
        ComprobarDescription description = new ComprobarDescription(url);
        description.comprobar();
        
        //Insertar Meta Title en BD
        
        InsertarMetaDescription.insertaMetaDescription(idAnalisis, description.getDescription(), description.getEstadoDescription());
        
        
        //Insertar Estructura de Hs en BD
        //Creamos la comprobacion de encabezados y llamamos a clase InsertarEncabezados desde el metodo comprobar, puesto que los va recorriendo
        ComprobarEncabezados encabezados = new ComprobarEncabezados(url,idAnalisis);
        encabezados.comprobar();
         
       /*  
         ComprobarEstructura EstructuraURL = new ComprobarEstructura(url);
         EstructuraURL.comprobar();
         */
         
         ComprobarImagenes imagenesURL = new ComprobarImagenes(url,idAnalisis);
         imagenesURL.comprobar();
       
        
         ComprobarEnlaces enlaces = new ComprobarEnlaces(url,dominio_y_protocolo,idAnalisis);
         enlaces.comprobar();
         
         
          
         ComprobarTexto texto = new ComprobarTexto(url,idAnalisis);
        texto.comprobar();
        texto.calcularDensidad();
          
          
           
            //System.out.println(doc.html());
            
            
            
         }else{
             System.out.println(rojo+"Error: url no encontrada: 404");
             //Este error debe guardarse en base de datos
         }
    }
    
}
