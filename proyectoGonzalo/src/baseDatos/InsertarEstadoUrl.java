/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author gonzaloromanmarquez
 */
public class InsertarEstadoUrl {
    public static void insertaEstadoUrl(String id_analisis, String estado_http, String mensaje_estado, String estado) {
        String sql = "INSERT INTO estado(id_analisis, estado_http, mensaje_estado, estado) VALUES(?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_analisis);
            pstmt.setString(2, estado_http);
            pstmt.setString(3, mensaje_estado);
            pstmt.setString(4, estado);
           

            pstmt.executeUpdate();
            System.out.println("Datos insertados en estado.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
