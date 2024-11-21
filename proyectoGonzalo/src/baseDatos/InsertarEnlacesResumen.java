/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para insertar datos en la tabla "ResumenEnlaces" de la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class InsertarEnlacesResumen {

    // Este método es llamado directamente desde comprobarEnlaces.java en el paquete "proyecto".
    // Los datos se insertan en la tabla de resumen de enlaces a medida que se procesan los enlaces.

    public static void insertaEnlacesResumen(String id_analisis, String total_enlaces_internos, String total_enlaces_externos, String total_enlaces_rotos, String estado) {
        // Consulta SQL para insertar datos en la tabla "ResumenEnlaces"
        String sql = "INSERT INTO ResumenEnlaces(id_analisis, total_enlaces_internos, total_enlaces_externos, total_enlaces_rotos, estado) VALUES(?,?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect(); // Establecemos la conexión con la base de datos
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Preparamos la consulta SQL

            // Asignamos los valores recibidos a los parámetros de la consulta
            pstmt.setString(1, id_analisis);              // ID del análisis asociado (clave foránea)
            pstmt.setString(2, total_enlaces_internos);   // Total de enlaces internos encontrados
            pstmt.setString(3, total_enlaces_externos);   // Total de enlaces externos encontrados
            pstmt.setString(4, total_enlaces_rotos);      // Total de enlaces rotos encontrados
            pstmt.setString(5, estado);                  // Estado del resumen (Correcto o Error)

            // Ejecutamos la consulta para insertar los datos
            pstmt.executeUpdate();
            System.out.println("Datos insertados en ResumenEnlaces."); // Confirmación de inserción

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error que ocurra durante la inserción
            System.out.println(e.getMessage());
        }
    }
}
