/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para insertar datos en la tabla "Estado" de la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class InsertarEstadoUrl {

    /**
     * Método para insertar el estado HTTP y su mensaje en la tabla "Estado".
     * 
     * @param id_analisis   ID del análisis al que pertenece (clave foránea).
     * @param estado_http   Código de estado HTTP de la URL (por ejemplo, 200, 404).
     * @param mensaje_estado Mensaje descriptivo del estado (por ejemplo, "OK", "Not Found").
     * @param estado        Estado del registro (Correcto o Error).
     */
    public static void insertaEstadoUrl(String id_analisis, String estado_http, String mensaje_estado, String estado) {
        // Consulta SQL para insertar datos en la tabla "Estado"
        String sql = "INSERT INTO estado(id_analisis, estado_http, mensaje_estado, estado) VALUES(?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect(); // Establecemos la conexión con la base de datos
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Preparamos la consulta SQL

            // Asignamos los valores recibidos a los parámetros de la consulta
            pstmt.setString(1, id_analisis);     // ID del análisis asociado
            pstmt.setString(2, estado_http);    // Código de estado HTTP
            pstmt.setString(3, mensaje_estado); // Mensaje descriptivo del estado
            pstmt.setString(4, estado);         // Estado del registro (Correcto o Error)

            // Ejecutamos la consulta para insertar los datos
            pstmt.executeUpdate();
            System.out.println("Datos insertados en estado."); // Confirmación de inserción

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error que ocurra durante la inserción
            System.out.println(e.getMessage());
        }
    }
}
