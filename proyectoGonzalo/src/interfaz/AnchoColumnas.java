/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author 6003262
 */
public class AnchoColumnas {
    
    
    
    public void establecerAnchocolumnas(JTable jTableUrlsAnalizadas, JTable jTableUrlsAnalizadasSeleccionada,
                                        JTable jTableDominios, JTable jTableTitle, JTable jTableDescription,
                                        JTable jTableEncabezados, JTable jTableImagenes, JTable jTableEnlaces,
                                        JTable jTableTexto, JTable jTableResumen, JTable jTableResumenErrores) {

       
        // Ajustar el ancho de columnas en jTableDominios
        jTableDominios.getColumnModel().getColumn(0).setPreferredWidth(300); // Columna dominio más ancha
        jTableDominios.getColumnModel().getColumn(1).setPreferredWidth(300); // Columna cantidad análisis más pequeña

       

        // Ajustar el ancho de columnas en jTableEncabezados
        jTableEncabezados.getColumnModel().getColumn(0).setPreferredWidth(50); // Columna Nivel
        jTableEncabezados.getColumnModel().getColumn(1).setPreferredWidth(500); // Columna Contenido más ancha
        jTableEncabezados.getColumnModel().getColumn(2).setPreferredWidth(70);  // Columna Estado más pequeña

        // Ajustar el ancho de columnas en jTableImagenes
        jTableImagenes.getColumnModel().getColumn(0).setPreferredWidth(400); // Columna Ruta Imagen más ancha
        jTableImagenes.getColumnModel().getColumn(1).setPreferredWidth(200); // Columna Alt más pequeña
        jTableImagenes.getColumnModel().getColumn(2).setPreferredWidth(70);  // Columna Estado

        // Ajustar el ancho de columnas en jTableEnlaces
        jTableEnlaces.getColumnModel().getColumn(0).setPreferredWidth(400); // Columna URL más ancha
        jTableEnlaces.getColumnModel().getColumn(1).setPreferredWidth(150); // Columna Tipo más pequeña
        jTableEnlaces.getColumnModel().getColumn(2).setPreferredWidth(300); // Columna Anchor Text más ancha
        jTableEnlaces.getColumnModel().getColumn(3).setPreferredWidth(70);  // Columna Estado

        // Ajustar el ancho de columnas en jTableTexto
        jTableTexto.getColumnModel().getColumn(0).setPreferredWidth(300); // Columna Palabra más ancha
        jTableTexto.getColumnModel().getColumn(1).setPreferredWidth(100); // Columna Nº Veces
        jTableTexto.getColumnModel().getColumn(2).setPreferredWidth(100); // Columna Densidad(%)

     

        // Ajustar el ancho de columnas en jTableResumenErrores
        jTableResumenErrores.getColumnModel().getColumn(0).setPreferredWidth(100); // Columna Error en
        jTableResumenErrores.getColumnModel().getColumn(1).setPreferredWidth(500); // Columna Detalle más ancha
        jTableResumenErrores.getColumnModel().getColumn(2).setPreferredWidth(100); // Columna Estado

        
    }

  
    
    
   
    
    
}
