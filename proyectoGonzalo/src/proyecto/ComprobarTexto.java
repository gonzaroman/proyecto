package proyecto;

import baseDatos.InsertarTexto; // Importar la clase para insertar datos en la tabla de texto
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Clase para analizar y calcular la densidad de palabras en el texto visible de una página web.
 */
public class ComprobarTexto {
    // Atributos principales
    String url; // URL de la página web a analizar
    String idAnalisis; // ID del análisis para registrar resultados en la base de datos

    // Métodos getters y setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Constructor para inicializar la clase con la URL y el ID del análisis
    public ComprobarTexto(String url, String idAnalisis) {
        this.url = url;
        this.idAnalisis = idAnalisis;
    }

    /**
     * Método para extraer y mostrar todo el texto visible de una página web.
     */
    public void comprobar() {
        System.out.println("------------------TEXTO DE LA URL------------------------");

        try {
            // Descargar y analizar el HTML de la página web
            Document doc = Jsoup.connect(url).get();

            // Extraer todo el texto visible de la página
            String texto_de_la_url = doc.text();

            // Mostrar el texto extraído en la consola
            System.out.println(texto_de_la_url);
        } catch (IOException iOException) {
            // Manejar errores durante la conexión
            System.out.println("Error al extraer el texto de la URL: " + iOException.getMessage());
        }
    }

    /**
     * Método para calcular la densidad de palabras en el texto visible de una página web.
     * Registra las palabras con mayor densidad en la base de datos.
     */
    public void calcularDensidad() {
        try {
            // Descargar y analizar el HTML de la página web
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .get();

            // Extraer todo el texto visible de la página
            String texto_de_la_url = doc.text();

            // Dividir el texto en palabras, ignorando caracteres no alfabéticos
            String[] palabras = texto_de_la_url.toLowerCase().split("[\\P{L}]+");

            // Crear un mapa para contar las ocurrencias de cada palabra
            Map<String, Integer> ocurrenciasPalabras = new HashMap<>();

            // Contar las ocurrencias de cada palabra
            for (String palabra : palabras) {
                ocurrenciasPalabras.put(palabra, ocurrenciasPalabras.getOrDefault(palabra, 0) + 1);
            }

            // Calcular el total de palabras en el texto
            int totalPalabras = palabras.length;

            // Convertir el mapa en una lista para poder ordenarla
            List<Map.Entry<String, Integer>> listaOcurrencias = new ArrayList<>(ocurrenciasPalabras.entrySet());

            // Ordenar la lista por el porcentaje de densidad en orden descendente
            listaOcurrencias.sort((entry1, entry2) -> {
                double porcentaje1 = ((double) entry1.getValue() / totalPalabras) * 100;
                double porcentaje2 = ((double) entry2.getValue() / totalPalabras) * 100;
                return Double.compare(porcentaje2, porcentaje1); // Ordenar de mayor a menor
            });

            // Mostrar el total de palabras y la densidad de palabras en consola
            System.out.println("Total de palabras: " + totalPalabras);
            System.out.println("Densidad de palabras en el texto (ordenado de mayor a menor):");

            // Limitar el número de palabras a mostrar
            int palabrasAmostrar = 0;

            for (Map.Entry<String, Integer> entry : listaOcurrencias) {
                String palabra = entry.getKey(); // Palabra actual
                int apariciones = entry.getValue(); // Número de veces que aparece
                float porcentaje = ((float) apariciones / totalPalabras) * 100;

                // Redondear el porcentaje a dos decimales
                porcentaje = Math.round(porcentaje * 100.0f) / 100.0f;

                // Mostrar la palabra, porcentaje y número de apariciones
                palabrasAmostrar++;
                System.out.println(palabrasAmostrar + " " + palabra + " " + porcentaje + "%  --> " + apariciones + " veces");

                // Insertar los datos en la base de datos
                InsertarTexto.insertaTexto(idAnalisis, palabra, apariciones + "", porcentaje + "");

                // Mostrar hasta 50 palabras como máximo
                if (palabrasAmostrar >= 50) break;
            }
        } catch (IOException iOException) {
            // Manejar errores durante la conexión o análisis
            System.out.println("Error al calcular la densidad de palabras: " + iOException.getMessage());
        }
    }
}
