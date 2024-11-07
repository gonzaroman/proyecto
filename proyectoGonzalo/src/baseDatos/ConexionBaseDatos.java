/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 6003262
 */
public class ConexionBaseDatos {
    // Método para conectar a la base de datos
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
        String sqlCrearTablaAnalisis = """
                     
                     CREATE TABLE IF NOT EXISTS Analisis (
                         id_analisis INTEGER PRIMARY KEY AUTOINCREMENT,
                         url_principal TEXT NOT NULL,
                         dominio TEXT,
                         protocolo TEXT,
                         dominio_y_protocolo TEXT,
                         fecha_analisis TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         estado TEXT CHECK (estado IN ('Correcto', 'Error')) DEFAULT 'Correcto'
                     );""";
           
                
        String sqlCrearTablaEstado = """        
                     -- Tabla de Estado, con referencia a Analisis
                     CREATE TABLE IF NOT EXISTS  Estado (
                         id_estado INTEGER PRIMARY KEY AUTOINCREMENT,
                         id_analisis INTEGER,
                         estado_http INTEGER,
                         mensaje_estado TEXT,
                         estado TEXT CHECK (estado IN ('Correcto', 'Error')) DEFAULT 'Correcto',
                         FOREIGN KEY (id_analisis) REFERENCES Analisis(id_analisis)
                     );""";
                
                String sqlCrearTablaMetaTitle = """
                     
                     -- Tablas de Metaetiquetas
                     
                     -- Tabla MetaTitle, con referencia a Analisis
                     CREATE TABLE IF NOT EXISTS MetaTitle (
                         id_meta_title INTEGER PRIMARY KEY AUTOINCREMENT,
                         id_analisis INTEGER,
                         titulo_pagina TEXT,
                         estado TEXT CHECK (estado IN ('Correcto', 'Error')) DEFAULT 'Correcto',
                         FOREIGN KEY (id_analisis) REFERENCES Analisis(id_analisis)
                     );""";
                     
                String sqlCrearTablaMetaDescription= """        
                     -- Tabla MetaDescription, con referencia a Analisis
                     CREATE TABLE IF NOT EXISTS  MetaDescription (
                         id_meta_description INTEGER PRIMARY KEY AUTOINCREMENT,
                         id_analisis INTEGER,
                         meta_descripcion TEXT,
                         estado TEXT CHECK (estado IN ('Correcto', 'Error')) DEFAULT 'Correcto',
                         FOREIGN KEY (id_analisis) REFERENCES Analisis(id_analisis)
                     );""";
                
               String sqlCrearTablaEncabezados= """    
                     -- Tabla de Encabezados, con referencia a Analisis
                     CREATE TABLE IF NOT EXISTS  Encabezados (
                         id_encabezado INTEGER PRIMARY KEY AUTOINCREMENT,
                         id_analisis INTEGER,
                         nivel TEXT,
                         contenido TEXT,
                         estado TEXT CHECK (estado IN ('Correcto', 'Error')) DEFAULT 'Correcto',
                         informacion TEXT,
                         FOREIGN KEY (id_analisis) REFERENCES Analisis(id_analisis)
                     );""";
               
                String sqlCrearTablaImagenes= """      
                     -- Tabla de Imagenes, con referencia a Analisis
                     CREATE TABLE IF NOT EXISTS Imagenes (
                         id_imagen INTEGER PRIMARY KEY AUTOINCREMENT,
                         id_analisis INTEGER,
                         ruta_imagen TEXT,
                         alt_texto TEXT,
                         estado TEXT CHECK (estado IN ('Correcto', 'Error')) DEFAULT 'Correcto',
                         FOREIGN KEY (id_analisis) REFERENCES Analisis(id_analisis)
                     );""";
                
                
                 String sqlCrearTablaEnlaces= """     
                     -- Tabla de Enlaces, con referencia a Analisis
                     CREATE TABLE IF NOT EXISTS Enlaces (
                         id_enlace INTEGER PRIMARY KEY AUTOINCREMENT,
                         id_analisis INTEGER,
                         url_enlace TEXT,
                         tipo TEXT,
                         anchor_text TEXT,
                         estado TEXT CHECK (estado IN ('Correcto', 'Error')) DEFAULT 'Correcto',
                         
                         FOREIGN KEY (id_analisis) REFERENCES Analisis(id_analisis)
                     );""";
                 
                 String sqlCrearTablaResumenEnlaces= """ 
                     
                     -- Tabla de ResumenEnlaces, con referencia a Analisis
                     CREATE TABLE IF NOT EXISTS  ResumenEnlaces (
                         id_resumen INTEGER PRIMARY KEY AUTOINCREMENT,
                         id_analisis INTEGER,
                         total_enlaces_internos INTEGER,
                         total_enlaces_externos INTEGER,
                         total_enlaces_rotos INTEGER,
                         estado TEXT CHECK (estado IN ('Correcto', 'Error')) DEFAULT 'Correcto',
                         FOREIGN KEY (id_analisis) REFERENCES Analisis(id_analisis)
                     );""";
                     
                String sqlCrearTablaTexto= """ 
                     -- Tabla de Texto, con referencia a Analisis
                     CREATE TABLE IF NOT EXISTS  Texto (
                         id_texto INTEGER PRIMARY KEY AUTOINCREMENT,
                         id_analisis INTEGER,
                         palabra TEXT,
                         frecuencia INTEGER,
                         densidad REAL,
                         estado TEXT CHECK (estado IN ('Correcto', 'Error')) DEFAULT 'Correcto',
                         FOREIGN KEY (id_analisis) REFERENCES Analisis(id_analisis)
                     ); """;                  
                     

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // Crear la tabla
            stmt.execute(sqlCrearTablaAnalisis);
            stmt.execute(sqlCrearTablaEstado);
            stmt.execute(sqlCrearTablaMetaTitle);
            stmt.execute(sqlCrearTablaMetaDescription);
            stmt.execute(sqlCrearTablaEncabezados);
            stmt.execute(sqlCrearTablaImagenes);
            stmt.execute(sqlCrearTablaEnlaces);
            stmt.execute(sqlCrearTablaResumenEnlaces);
            stmt.execute(sqlCrearTablaTexto);
            System.out.println("Tablas creadas.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
