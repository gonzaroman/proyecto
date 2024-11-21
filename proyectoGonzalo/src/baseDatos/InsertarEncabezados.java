/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para insertar datos en la tabla "Encabezados" de la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class InsertarEncabezados {
    // Este método es llamado directamente desde comprobarEncabezados.java en el paquete "proyecto".
    // Los datos se insertan en la base de datos mientras se recorren los encabezados HTML (H1, H2, etc.).

    public static void insertaEncabezados(String id_analisis, String nivel, String contenido, String estado, String informacion) {
        // Consulta SQL para insertar datos en la tabla "Encabezados"
        String sql = "INSERT INTO Encabezados(id_analisis, nivel, contenido, estado, informacion) VALUES(?,?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect(); // Establecemos la conexión con la base de datos
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Preparamos la consulta SQL

            // Asignamos los valores recibidos a los parámetros de la consulta
            pstmt.setString(1, id_analisis);  // ID del análisis asociado (clave foránea)
            pstmt.setString(2, nivel);       // Nivel del encabezado (H1, H2, etc.)
            pstmt.setString(3, contenido);   // Contenido del encabezado
            pstmt.setString(4, estado);      // Estado del encabezado (Correcto o Error)
            pstmt.setString(5, informacion); // Información adicional sobre el encabezado

            // Ejecutamos la consulta para insertar los datos
            pstmt.executeUpdate();
            System.out.println("Datos insertados en Encabezados."); // Confirmación de inserción

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error que ocurra durante la inserción
            System.out.println(e.getMessage());
        }
    }
}
