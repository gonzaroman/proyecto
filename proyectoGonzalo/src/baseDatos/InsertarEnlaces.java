/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para insertar datos en la tabla "Enlaces" de la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class InsertarEnlaces {

    // Este método es llamado directamente desde comprobarEnlaces.java en el paquete "proyecto".
    // Los datos de los enlaces se insertan en la base de datos mientras se recorren uno por uno.

    public static void insertaEnlaces(String id_analisis, String url_enlace, String tipo, String anchor_text, String estado) {
        // Consulta SQL para insertar datos en la tabla "Enlaces"
        String sql = "INSERT INTO Enlaces(id_analisis, url_enlace, tipo, anchor_text, estado) VALUES(?,?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect(); // Establecemos la conexión con la base de datos
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Preparamos la consulta SQL

            // Asignamos los valores recibidos a los parámetros de la consulta
            pstmt.setString(1, id_analisis);  // ID del análisis asociado (clave foránea)
            pstmt.setString(2, url_enlace);  // URL del enlace
            pstmt.setString(3, tipo);        // Tipo de enlace (interno o externo)
            pstmt.setString(4, anchor_text); // Texto del enlace (anchor text)
            pstmt.setString(5, estado);      // Estado del enlace (Correcto o Error)

            // Ejecutamos la consulta para insertar los datos
            pstmt.executeUpdate();
            System.out.println("Datos insertados en Enlaces."); // Confirmación de inserción

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error que ocurra durante la inserción
            System.out.println(e.getMessage());
        }
    }
}
