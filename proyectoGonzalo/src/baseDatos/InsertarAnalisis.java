/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Clase para insertar datos en la tabla "Analisis" de la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class InsertarAnalisis {
    // Método para insertar un nuevo análisis en la base de datos
    public static int insertaAnalisis(String url_principal, String dominio, String protocolo, String dominio_y_protocolo, String estado_general) {
        // Consulta SQL para insertar datos en la tabla "analisis"
        String sql = "INSERT INTO analisis(url_principal, dominio, protocolo, dominio_y_protocolo, estado) VALUES(?,?,?,?,?)";
        
        int idAnalisis = -1; // Inicializamos el ID a -1 para indicar que aún no se ha generado
        
        try (Connection conn = ConexionBaseDatos.connect(); // Establecemos conexión con la base de datos
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) { // Preparamos la consulta con opción para devolver claves generadas

            // Asignamos los valores recibidos a los parámetros de la consulta
            pstmt.setString(1, url_principal);
            pstmt.setString(2, dominio);
            pstmt.setString(3, protocolo);
            pstmt.setString(4, dominio_y_protocolo);
            pstmt.setString(5, estado_general);

            // Ejecutamos la consulta
            pstmt.executeUpdate();
            System.out.println("Datos insertados en Análisis.");
            
            // Código para obtener el ID generado automáticamente
            ResultSet rs = pstmt.getGeneratedKeys(); // Recuperamos las claves generadas
            if (rs.next()) {
                idAnalisis = rs.getInt(1); // Obtenemos el ID del nuevo registro
            }
            
            System.out.println("Datos insertados en Analisis con ID: " + idAnalisis);

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error de SQL
            System.out.println(e.getMessage());
        }
        
        return idAnalisis; // Devolvemos el ID generado para usarlo como clave foránea en otras tablas
    }
}
