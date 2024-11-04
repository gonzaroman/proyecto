package interfaz;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import baseDatos.ConexionBaseDatos;
import java.net.MalformedURLException;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import proyecto.Ejecutar;
import java.sql.Timestamp;
import java.sql.PreparedStatement;

//TEMAS PARA LOOK AND FEEL

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Gonzalo Román Márquez
 */
public class inicio extends javax.swing.JFrame {

    /**
     * Creates new form inicio
     */
    public inicio() {
        initComponents();
        
        // Agregar MouseListener para capturar clic en jTableDominios
        jTableDominios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = jTableDominios.getSelectedRow(); // Obtener la fila seleccionada

                if (selectedRow != -1) { // Asegurarse de que hay una fila seleccionada
                    // Obtener el valor de la columna donde está el dominio (por ejemplo, columna 0)
                    String dominio = jTableDominios.getValueAt(selectedRow, 0).toString();

                    // Llamar al método para mostrar los análisis del dominio seleccionado en otra tabla
                    selectUrlsAnalizadasTablaSeleccionada(jTableUrlsAnalizadasSeleccionada, dominio);
                }
            }
        });

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                
                 // Aplica el tema y fuerza la actualización de la interfaz gráfica
                javax.swing.SwingUtilities.updateComponentTreeUI(inicio.this);
                inicio.this.pack(); // Opcional: reajustar el tamaño de la ventana


                ConexionBaseDatos.createNewTable();

                selectUrlsAnalizadasTabla(jTableUrlsAnalizadas);
                selectDominioTabla(jTableDominios);
                
                //  selectUrlsAnalizadasTablaSeleccionada(jTableUrlsAnalizadasSeleccionada,"www.snsmarketing.es");
                
                
                establecerAnchocolumnas();
            }
        });
        
          
    }
    
    private void establecerAnchocolumnas() {
        
        //establecer ancho de las columnas
        jTableUrlsAnalizadas.getColumnModel().getColumn(0).setPreferredWidth(400);
        jTableUrlsAnalizadas.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTableUrlsAnalizadas.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTableUrlsAnalizadas.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTableUrlsAnalizadas.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTableUrlsAnalizadas.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTableUrlsAnalizadas.getColumnModel().getColumn(6).setPreferredWidth(100);
        
        
       jTableDominios.getColumnModel().getColumn(0).setPreferredWidth(300);
       jTableDominios.getColumnModel().getColumn(1).setPreferredWidth(200);
       
       //alineacion de las columnas
       DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
       rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER); // Alineación a la derecha
       
       jTableUrlsAnalizadas.getColumnModel().getColumn(1).setCellRenderer(rightRenderer); // Columna 0 alineada a la derecha
       jTableUrlsAnalizadas.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
       jTableUrlsAnalizadas.getColumnModel().getColumn(3).setCellRenderer(rightRenderer); // Columna 0 alineada a la derecha
       jTableUrlsAnalizadas.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
       jTableUrlsAnalizadas.getColumnModel().getColumn(5).setCellRenderer(rightRenderer); // Columna 0 alineada a la derecha
       jTableUrlsAnalizadas.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
      
       jTableDominios.getColumnModel().getColumn(1).setCellRenderer(rightRenderer); // Columna 0 alineada a la derecha
        
}
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTextFieldURL = new javax.swing.JTextField();
        jButtonAnalizar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableUrlsAnalizadas = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableDominios = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableUrlsAnalizadasSeleccionada = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setForeground(new java.awt.Color(255, 255, 204));

        jButtonAnalizar.setText("analizar");
        jButtonAnalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnalizarActionPerformed(evt);
            }
        });

        jTableUrlsAnalizadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTableUrlsAnalizadas);

        jTableDominios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTableDominios);

        jTableUrlsAnalizadasSeleccionada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTableUrlsAnalizadasSeleccionada);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(29, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextFieldURL, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jButtonAnalizar)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAnalizar))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        jTabbedPane1.addTab("GENERAL", jPanel1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1274, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 567, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel3);

        jButton1.setText("OSCURO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("CLARO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnalizarActionPerformed
        // TODO add your handling code here:
        /* String url = jTextFieldURL.getText();
        try {
            Ejecutar.hacer(url); //esto llama a la clase que ejecuta todos los procesos para recorrer la url y guardarlo en la base de datos
        } catch (MalformedURLException ex) {
            Logger.getLogger(inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        selectDominio(jListDominios);*/

        String url = jTextFieldURL.getText();

        // Crear un SwingWorker para ejecutar el análisis en segundo plano
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    Ejecutar.hacer(url); // Ejecuta el proceso en segundo plano
                } catch (MalformedURLException ex) {
                    Logger.getLogger(inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

            @Override
            protected void done() {
                // Ejecutado en el hilo de la GUI al completar doInBackground
                // Actualizar la lista una vez terminado

                selectDominioTabla(jTableDominios);
                selectUrlsAnalizadasTabla(jTableUrlsAnalizadas);
                
              
                
            }
        };

        // Ejecuta el SwingWorker
        worker.execute();

    }//GEN-LAST:event_jButtonAnalizarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
         try {
            // Cambiar al tema oscuro de macOS
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacDarkLaf());

            // Actualizar la interfaz para aplicar el nuevo Look and Feel
            javax.swing.SwingUtilities.updateComponentTreeUI(this);

            // Opcional: redimensionar ventana si es necesario
            this.pack();

        } catch (Exception e) {
            e.printStackTrace();
        }
  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         
       
    //        FlatMacLightLaf.install();   // Tema claro para macOS
            
             try {
            // Cambiar al tema CLARO de macOS
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());

            // Actualizar la interfaz para aplicar el nuevo Look and Feel
            javax.swing.SwingUtilities.updateComponentTreeUI(this);

            // Opcional: redimensionar ventana si es necesario
            this.pack();

        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }//GEN-LAST:event_jButton2ActionPerformed


    
    
    public static void selectDominioTabla(JTable jTableDominios) {

        String sql = "SELECT dominio, COUNT(*) AS cantidad_analisis, MAX(fecha_analisis) AS ultima_fecha "
                + "FROM Analisis "
                + "GROUP BY dominio "
                + "ORDER BY ultima_fecha DESC";

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"DOMINIO", "CANTIDAD ANALISIS"}, 0);

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String dominio = rs.getString("dominio");
                int cantidadAnalisis = rs.getInt("cantidad_analisis");

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{dominio, cantidadAnalisis});
            }

            // Asignar el modelo a la tabla
            jTableDominios.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void selectUrlsAnalizadasTabla(JTable jTableUrlsAnalizadas) {
        String sql = "SELECT id_analisis, url_principal, fecha_analisis FROM Analisis ORDER BY fecha_analisis DESC";
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"URL", "Fecha de Análisis", "Title", "Description", "Encabezados", "Imágenes", "Enlaces"}, 0);

        SimpleDateFormat cambiaFormatoFecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String id_analisis = rs.getString("id_analisis");
                String urlAnalizada = rs.getString("url_principal");

                // Obtener la fecha y formatearla
                Timestamp fechaAnalisisTimestamp = rs.getTimestamp("fecha_analisis");
                String fechaAnalisis = cambiaFormatoFecha.format(fechaAnalisisTimestamp);
                // Obtener la fecha y formatearla

                // Obtener el conteo de errores de encabezados, imágenes y enlaces
                int erroresEncabezados = obtenerConteoErrores(conn, "encabezados", id_analisis);
                int erroresImagenes = obtenerConteoErrores(conn, "imagenes", id_analisis);
                int erroresEnlaces = obtenerConteoErrores(conn, "enlaces", id_analisis);
                int erroresTitle = obtenerConteoErrores(conn, "MetaTitle", id_analisis);
                int erroresDescription = obtenerConteoErrores(conn, "MetaDescription", id_analisis);

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{urlAnalizada, fechaAnalisis, erroresTitle, erroresDescription, erroresEncabezados, erroresImagenes, erroresEnlaces});
            }

            // Asignar el modelo a la tabla
            jTableUrlsAnalizadas.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void selectUrlsAnalizadasTablaSeleccionada(JTable jTableUrlsAnalizadasSeleccionada, String dominio) {
        String sql = "SELECT id_analisis,dominio, url_principal, fecha_analisis FROM Analisis where dominio = '"+dominio+"' ORDER BY fecha_analisis DESC ";
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"URL", "Fecha de Análisis", "Title", "Description", "Encabezados", "Imágenes", "Enlaces"}, 0);

        SimpleDateFormat cambiaFormatoFecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String id_analisis = rs.getString("id_analisis");
                String urlAnalizada = rs.getString("url_principal");

                // Obtener la fecha y formatearla
                Timestamp fechaAnalisisTimestamp = rs.getTimestamp("fecha_analisis");
                String fechaAnalisis = cambiaFormatoFecha.format(fechaAnalisisTimestamp);
                // Obtener la fecha y formatearla

                // Obtener el conteo de errores de encabezados, imágenes y enlaces
                int erroresEncabezados = obtenerConteoErrores(conn, "encabezados", id_analisis);
                int erroresImagenes = obtenerConteoErrores(conn, "imagenes", id_analisis);
                int erroresEnlaces = obtenerConteoErrores(conn, "enlaces", id_analisis);
                int erroresTitle = obtenerConteoErrores(conn, "MetaTitle", id_analisis);
                int erroresDescription = obtenerConteoErrores(conn, "MetaDescription", id_analisis);

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{urlAnalizada, fechaAnalisis, erroresTitle, erroresDescription, erroresEncabezados, erroresImagenes, erroresEnlaces});
            }

            // Asignar el modelo a la tabla
            jTableUrlsAnalizadasSeleccionada.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    

    private static int obtenerConteoErrores(Connection conn, String tabla, String id_analisis) {
        String sqlErrores = "SELECT COUNT(*) AS conteo FROM " + tabla + " WHERE id_analisis = ? AND estado = 'Error'";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlErrores)) {
            pstmt.setString(1, id_analisis);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("conteo");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener errores de " + tabla + " para id_analisis: " + id_analisis);
            System.out.println(e.getMessage());
        }
        return 0; // Devuelve 0 si ocurre un error
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
      */
        try {
         //   javax.swing.UIManager.getInstalledLookAndFeels();
       //   javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
           // javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            // Selecciona uno de los temas comentados y descoméntalo para usarlo
           // FlatLightLaf.install();         // Tema claro predeterminado de FlatLaf
            // FlatDarkLaf.install();       // Tema oscuro predeterminado
            // FlatIntelliJLaf.install();   // Similar a IntelliJ IDEA Light
           //  FlatDarculaLaf.install();    // Similar a IntelliJ IDEA Darcula
            FlatMacLightLaf.install();   // Tema claro para macOS
            // FlatMacDarkLaf.install();    // Tema oscuro para macOS
            
          

        
            
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inicio().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonAnalizar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableDominios;
    private javax.swing.JTable jTableUrlsAnalizadas;
    private javax.swing.JTable jTableUrlsAnalizadasSeleccionada;
    private javax.swing.JTextField jTextFieldURL;
    // End of variables declaration//GEN-END:variables
}
