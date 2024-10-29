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
public class InsertarEnlaces {
    
    // LO llamamos DIRECTAMENTE EN comprobarEnlaces.java, en el paquete proyecto.
    //lo vamos guardando en la base de datos segun recorre los enlaces
    public static void insertaEnlaces(String id_analisis, String url_enlace, String tipo, String anchor_text, String estado) {
        String sql = "INSERT INTO Enlaces(id_analisis, url_enlace, tipo, anchor_text, estado) VALUES(?,?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_analisis);
            pstmt.setString(2, url_enlace);
            pstmt.setString(3, tipo);          
            pstmt.setString(4, anchor_text);
            pstmt.setString(5, estado);
           

            pstmt.executeUpdate();
            System.out.println("Datos insertados en Enlaces.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
