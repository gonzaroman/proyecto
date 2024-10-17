/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author gonzaloromanmarquez
 */
public class PedirUrl {

    public PedirUrl() {
    }
    
    public String escribir(){
        String texto = null;
        try {
            // Solicitar un texto al usuario
            try (Scanner scanner = new Scanner(System.in)) {
                // Solicitar un texto al usuario
                System.out.print("Introduce una url: ");
                // Leer el texto introducido por el usuario
                texto = scanner.nextLine();
                // Mostrar el texto introducido
                System.out.println("Has introducido: " + texto);
                // Cerrar el Scanner
                try {
                    new URL(texto);
                    return texto;
                } catch (MalformedURLException malformedURLException) {
                    System.out.println("url mal escrita, tienes que poner https:// ");
                    escribir();
                }
            }
        } catch (Exception e) {
          
        }
        
        return "";
    
    }
    
    
}
