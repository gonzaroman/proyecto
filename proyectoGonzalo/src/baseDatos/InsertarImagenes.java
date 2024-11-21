/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para insertar datos en la tabla "Imagenes" de la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class InsertarImagenes {

    /**
     * Método para insertar información sobre imágenes en la tabla "Imagenes".
     * 
     * @param id_analisis   ID del análisis al que pertenece la imagen (clave foránea).
     * @param ruta_imagen   Ruta o URL de la imagen.
     * @param alt_texto     Texto alternativo (atributo "alt") de la imagen.
     * @param estado_alt    Estado del texto alternativo (Correcto o Error).
     */
    public static void insertaImagenes(String id_analisis, String ruta_imagen, String alt_texto, String estado_alt) {
        // Consulta SQL para insertar datos en la tabla "Imagenes"
        String sql = "INSERT INTO Imagenes(id_analisis, ruta_imagen, alt_texto, estado) VALUES(?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect(); // Establecemos la conexión con la base de datos
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Preparamos la consulta SQL

            // Asignamos los valores recibidos a los parámetros de la consulta
            pstmt.setString(1, id_analisis);  // ID del análisis asociado
            pstmt.setString(2, ruta_imagen); // Ruta o URL de la imagen
            pstmt.setString(3, alt_texto);   // Texto alternativo de la imagen
            pstmt.setString(4, estado_alt); // Estado del atributo "alt" (Correcto o Error)

            // Ejecutamos la consulta para insertar los datos
            pstmt.executeUpdate();
            System.out.println("Datos insertados en Imagenes."); // Confirmación de inserción

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error que ocurra durante la inserción
            System.out.println(e.getMessage());
        }
    }
}
