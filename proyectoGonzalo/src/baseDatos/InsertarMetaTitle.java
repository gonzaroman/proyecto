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
public class InsertarMetaTitle {
    public static void insertaMetaTitleUrl(String id_analisis, String titulo_pagina, String estado_titulo) {
        String sql = "INSERT INTO MetaTitle(id_analisis, titulo_pagina, estado_titulo) VALUES(?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_analisis);
            pstmt.setString(2, titulo_pagina);
            pstmt.setString(3, estado_titulo);
           
           

            pstmt.executeUpdate();
            System.out.println("Datos insertados en MetaTitle.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
