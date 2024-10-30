package interfaz;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 6003262
 */
public class Consultas {
    public static Connection connect() {
        Connection conn = null;
        try {
            // Ruta del archivo de la base de datos
            String url = "jdbc:sqlite:base_datos.db";
            
            // Conexión a la base de datos
            conn = DriverManager.getConnection(url);
            
            System.out.println("Conexión establecida.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
     // Método para crear una tabla
    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    age integer\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // Crear la tabla
            stmt.execute(sql);
            System.out.println("Tabla creada.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void insert(String name, int age) {
    String sql = "INSERT INTO students(name, age) VALUES(?,?)";

    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setInt(2, age);
        pstmt.executeUpdate();
        System.out.println("Registro insertado.");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
    
    
    // Método para leer datos de la tabla "students"
    public static void selectAll(){
        String sql = "SELECT url_principal,fecha_analisis FROM Analisis";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
               // System.out.println("ID: " + rs.getInt("id"));
                System.out.print(" URL: " + rs.getString("url_principal"));
                System.out.print(" fecha Analsis: " + rs.getTime("fecha_analisis"));
                 System.out.println(" " + rs.getDate("fecha_analisis"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     // Método para leer datos de la tabla "students"
    public static void dominios(){
        String sql = "SELECT dominio FROM Analisis group by dominio";
        System.out.println("DOMINIOS");
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
               // System.out.println("ID: " + rs.getInt("id"));
                System.out.println(" URL: " + rs.getString("dominio"));
                
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
  
      // Método para leer datos de la tabla "students"
    public static void selectDominio(String dominio){
        String sql = "SELECT url_principal,fecha_analisis FROM Analisis where dominio='"+dominio+"' ";
        System.out.println("Urls del dominio: "+dominio);
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
               // System.out.println("ID: " + rs.getInt("id"));
               
                System.out.print(" URL: " + rs.getString("url_principal"));
                System.out.print(" fecha Analsis: " + rs.getTime("fecha_analisis"));
                 System.out.println(" " + rs.getDate("fecha_analisis"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     public static void selectIdAnalisisDominio(String dominio){
        String sql = "SELECT id_analisis FROM Analisis where dominio='"+dominio+"' ";
        System.out.println("ID Analisis de todas las urls del dominio: "+dominio);
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
               // System.out.println("ID: " + rs.getInt("id"));
               
                System.out.print(" id_analisis: " + rs.getString("id_analisis"));
                
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     
    public static void selectIdAnalisisURL(String fecha) {
        String sql = "SELECT id_analisis FROM Analisis where fecha_analisis='" + fecha + "' ";
        System.out.println("ID Analisis de la url analizada en la fecha: " + fecha);
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
                // System.out.println("ID: " + rs.getInt("id"));

                System.out.print(" id_analisis: " + rs.getString("id_analisis"));

                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void selectEncabezadosID(String id) {
        String sql = "SELECT * FROM Encabezados where id_analisis='" + id + "' ";
        System.out.println("Todos los encabezados del ID Analisis: " + id);
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
                // System.out.println("ID: " + rs.getInt("id"));

                 System.out.print(" NIVEL " + rs.getString("nivel"));
                System.out.print(" contenido: " + rs.getString("contenido"));
                System.out.println(" Estado: "+rs.getString("estado")); 

                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
  
}
