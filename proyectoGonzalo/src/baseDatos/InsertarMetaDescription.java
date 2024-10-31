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
public class InsertarMetaDescription {
    public static void insertaMetaDescription(String id_analisis, String meta_descripcion, String estado_descripcion) {
        String sql = "INSERT INTO MetaDescription(id_analisis, meta_descripcion, estado) VALUES(?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_analisis);
            pstmt.setString(2, meta_descripcion);
            pstmt.setString(3, estado_descripcion);
           
           

            pstmt.executeUpdate();
            System.out.println("Datos insertados en MetaDescription.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
