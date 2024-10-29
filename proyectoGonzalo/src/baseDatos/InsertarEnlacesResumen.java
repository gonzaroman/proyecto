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
public class InsertarEnlacesResumen {
    // LO llamamos DIRECTAMENTE EN comprobarEnlaces.java, en el paquete proyecto.
    //lo vamos guardando en la base de datos segun recorre los enlaces
    public static void insertaEnlacesResumen(String id_analisis, String total_enlaces_internos, String total_enlaces_externos, String total_enlaces_rotos, String estado) {
        String sql = "INSERT INTO ResumenEnlaces(id_analisis, total_enlaces_internos, total_enlaces_externos, total_enlaces_rotos, estado) VALUES(?,?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_analisis);
            pstmt.setString(2, total_enlaces_internos);
            pstmt.setString(3, total_enlaces_externos);          
            pstmt.setString(4, total_enlaces_rotos);
            pstmt.setString(5, estado);
           

            pstmt.executeUpdate();
            System.out.println("Datos insertados en ResumenEnlaces.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}
