/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para insertar datos en la tabla "Texto" de la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class InsertarTexto {

    /**
     * Método para insertar información sobre las palabras y su análisis en la tabla "Texto".
     * 
     * @param id_analisis ID del análisis al que pertenece la palabra (clave foránea).
     * @param palabra     Palabra analizada.
     * @param frecuencia  Frecuencia de aparición de la palabra.
     * @param densidad    Densidad de la palabra en el texto.
     */
    public static void insertaTexto(String id_analisis, String palabra, String frecuencia, String densidad) {
        // Consulta SQL para insertar datos en la tabla "Texto"
        String sql = "INSERT INTO Texto(id_analisis, palabra, frecuencia, densidad) VALUES(?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect(); // Establecemos la conexión con la base de datos
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Preparamos la consulta SQL

            // Asignamos los valores recibidos a los parámetros de la consulta
            pstmt.setString(1, id_analisis);  // ID del análisis asociado
            pstmt.setString(2, palabra);     // Palabra analizada
            pstmt.setString(3, frecuencia);  // Frecuencia de la palabra en el texto
            pstmt.setString(4, densidad);    // Densidad de la palabra en el texto

            // Ejecutamos la consulta para insertar los datos
            pstmt.executeUpdate();
           // System.out.println("Datos insertados en la tabla Texto."); // Confirmación de inserción

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error que ocurra durante la inserción
            System.out.println(e.getMessage());
        }
    }
}
