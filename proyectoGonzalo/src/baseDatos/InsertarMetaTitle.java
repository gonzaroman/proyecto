/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para insertar datos en la tabla "MetaTitle" de la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class InsertarMetaTitle {

    /**
     * Método para insertar información sobre el título de la página en la tabla "MetaTitle".
     * 
     * @param id_analisis   ID del análisis al que pertenece el título (clave foránea).
     * @param titulo_pagina Título de la página.
     * @param estado_titulo Estado del título (Correcto o Error).
     */
    public static void insertaMetaTitleUrl(String id_analisis, String titulo_pagina, String estado_titulo) {
        // Consulta SQL para insertar datos en la tabla "MetaTitle"
        String sql = "INSERT INTO MetaTitle(id_analisis, titulo_pagina, estado) VALUES(?,?,?)";

        try (Connection conn = ConexionBaseDatos.connect(); // Establecemos la conexión con la base de datos
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Preparamos la consulta SQL

            // Asignamos los valores recibidos a los parámetros de la consulta
            pstmt.setString(1, id_analisis);    // ID del análisis asociado
            pstmt.setString(2, titulo_pagina); // Título de la página
            pstmt.setString(3, estado_titulo); // Estado del título (Correcto o Error)

            // Ejecutamos la consulta para insertar los datos
            pstmt.executeUpdate();
            System.out.println("Datos insertados en MetaTitle."); // Confirmación de inserción

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error que ocurra durante la inserción
            System.out.println(e.getMessage());
        }
    }
}
