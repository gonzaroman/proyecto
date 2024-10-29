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
public class InsertarEncabezados {
    // LO llamamos DIRECTAMENTE EN comprobarEncabezados.java, en el paquete proyecto.
    //lo vamos guardando en la base de datos segun recorre los Hs
    public static void insertaEncabezados(String id_analisis, String nivel, String contenido, String estado) {
        String sql = "INSERT INTO Encabezados(id_analisis, nivel, contenido, estado) VALUES(?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_analisis);
            pstmt.setString(2, nivel);
            pstmt.setString(3, contenido);
            pstmt.setString(4, estado);
           

            pstmt.executeUpdate();
            System.out.println("Datos insertados en Encabezados.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
