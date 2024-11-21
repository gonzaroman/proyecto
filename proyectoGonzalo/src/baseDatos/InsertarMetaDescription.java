/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para insertar datos en la tabla "MetaDescription" de la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class InsertarMetaDescription {

    /**
     * Método para insertar información sobre la meta descripción en la tabla "MetaDescription".
     * 
     * @param id_analisis       ID del análisis al que pertenece la meta descripción (clave foránea).
     * @param meta_descripcion  Texto de la meta descripción.
     * @param estado_descripcion Estado de la meta descripción (Correcto o Error).
     */
    public static void insertaMetaDescription(String id_analisis, String meta_descripcion, String estado_descripcion) {
        // Consulta SQL para insertar datos en la tabla "MetaDescription"
        String sql = "INSERT INTO MetaDescription(id_analisis, meta_descripcion, estado) VALUES(?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect(); // Establecemos la conexión con la base de datos
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Preparamos la consulta SQL

            // Asignamos los valores recibidos a los parámetros de la consulta
            pstmt.setString(1, id_analisis);       // ID del análisis asociado
            pstmt.setString(2, meta_descripcion); // Texto de la meta descripción
            pstmt.setString(3, estado_descripcion); // Estado de la meta descripción (Correcto o Error)

            // Ejecutamos la consulta para insertar los datos
            pstmt.executeUpdate();
            System.out.println("Datos insertados en MetaDescription."); // Confirmación de inserción

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error que ocurra durante la inserción
            System.out.println(e.getMessage());
        }
    }
}
