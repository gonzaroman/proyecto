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
public class InsertarTexto {
    
    public static void insertaTexto(String id_analisis, String palabra, String frecuencia, String densidad) {
        String sql = "INSERT INTO Texto(id_analisis, palabra, frecuencia, densidad) VALUES(?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_analisis);
            pstmt.setString(2, palabra);
            pstmt.setString(3, frecuencia);          
            pstmt.setString(4, densidad);
          
           

            pstmt.executeUpdate();
            System.out.println("Datos insertados en la tabla Texto.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
