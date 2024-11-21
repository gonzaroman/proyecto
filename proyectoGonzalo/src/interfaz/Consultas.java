package interfaz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Clase para gestionar consultas SQL en la base de datos.
 * Autor: Gonzalo Román Márquez
 */
public class Consultas {

    /**
     * Método para establecer una conexión con la base de datos SQLite.
     * 
     * @return Connection objeto de conexión a la base de datos.
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:base_datos.db"; // Ruta de la base de datos
            conn = DriverManager.getConnection(url); // Establecer conexión
            System.out.println("Conexión establecida.");
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Mensaje de error si falla la conexión
        }
        return conn;
    }

    /**
     * Método para obtener todas las URLs principales y las fechas de análisis 
     * de la tabla "Analisis".
     */
    public static void selectAll() {
        String sql = "SELECT url_principal, fecha_analisis FROM Analisis";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.print("URL: " + rs.getString("url_principal"));
                System.out.print(" Fecha Análisis: " + rs.getTime("fecha_analisis"));
                System.out.println(" " + rs.getDate("fecha_analisis"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para obtener una lista de dominios únicos de la tabla "Analisis".
     */
    public static void dominios() {
        String sql = "SELECT dominio FROM Analisis GROUP BY dominio";
        System.out.println("DOMINIOS");

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("Dominio: " + rs.getString("dominio"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para obtener todas las URLs y las fechas de análisis de un dominio específico.
     * 
     * @param dominio Dominio a consultar.
     */
    public static void selectDominio(String dominio) {
        String sql = "SELECT url_principal, fecha_analisis FROM Analisis WHERE dominio='" + dominio + "'";
        System.out.println("URLs del dominio: " + dominio);

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.print("URL: " + rs.getString("url_principal"));
                System.out.print(" Fecha Análisis: " + rs.getTime("fecha_analisis"));
                System.out.println(" " + rs.getDate("fecha_analisis"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para obtener los IDs de análisis de un dominio específico.
     * 
     * @param dominio Dominio a consultar.
     */
    public static void selectIdAnalisisDominio(String dominio) {
        String sql = "SELECT id_analisis FROM Analisis WHERE dominio='" + dominio + "'";
        System.out.println("IDs de análisis para el dominio: " + dominio);

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.print("ID Análisis: " + rs.getString("id_analisis"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para obtener los IDs de análisis realizados en una fecha específica.
     * 
     * @param fecha Fecha de análisis a consultar.
     */
    public static void selectIdAnalisisURL(String fecha) {
        String sql = "SELECT id_analisis FROM Analisis WHERE fecha_analisis='" + fecha + "'";
        System.out.println("ID de análisis para la fecha: " + fecha);

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.print("ID Análisis: " + rs.getString("id_analisis"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para obtener todos los encabezados asociados a un ID de análisis.
     * 
     * @param id ID del análisis a consultar.
     */
    public static void selectEncabezadosID(String id) {
        String sql = "SELECT * FROM Encabezados WHERE id_analisis='" + id + "'";
        System.out.println("Encabezados para el ID de análisis: " + id);

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.print("Nivel: " + rs.getString("nivel"));
                System.out.print(" Contenido: " + rs.getString("contenido"));
                System.out.println(" Estado: " + rs.getString("estado"));
                System.out.println("----------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
