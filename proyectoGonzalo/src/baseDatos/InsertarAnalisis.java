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
 *
 * @author gonzaloromanmarquez
 */
public class InsertarAnalisis {
    public static int insertaAnalisis(String url_principal, String dominio, String protocolo, String dominio_y_protocolo, String estado_general) {
        String sql = "INSERT INTO analisis(url_principal, dominio, protocolo, dominio_y_protocolo, estado) VALUES(?,?,?,?,?)";
        
                int idAnalisis = -1; // Valor inicial para el id_analisis

        
        try (Connection conn = ConexionBaseDatos.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, url_principal);
            pstmt.setString(2, dominio);
            pstmt.setString(3, protocolo);
            pstmt.setString(4, dominio_y_protocolo);
            pstmt.setString(5, estado_general);

            pstmt.executeUpdate();
            System.out.println("Datos insertados en Análisis.");
            
            
            //este codigo nos devuelve el idAnalisis que luego utilizamos en la demas tablas como clave foranea
            // Obtener el ID generado
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idAnalisis = rs.getInt(1); // Captura el id_analisis generado
            }
            
            System.out.println("Datos insertados en Analisis con id: " + idAnalisis);
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
             return idAnalisis; // Devuelve el ID generado para usarlo en otras inserciones

        
    }
    
}
