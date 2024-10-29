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
public class InsertarImagenes {
    // LO llamamos DIRECTAMENTE EN comprobarImagenes.java, en el paquete proyecto.
    //lo vamos guardando en la base de datos segun recorre las imagenes
    public static void insertaImagenes(String id_analisis, String ruta_imagen, String alt_texto, String estado_alt) {
        String sql = "INSERT INTO Imagenes(id_analisis, ruta_imagen, alt_texto, estado_alt) VALUES(?,?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_analisis);
            pstmt.setString(2, ruta_imagen);
            pstmt.setString(3, alt_texto);
            pstmt.setString(4, estado_alt);
           

            pstmt.executeUpdate();
            System.out.println("Datos insertados en Imagenes.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
