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
import java.awt.Image;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
        aplicarTemaClaro();

        // Asigna un modelo vacío con encabezados personalizados desde el inicio
        DefaultTableModel initialModel = new DefaultTableModel(
                new String[]{"URL", "Fecha de Análisis", "Título", "Descripción", "Encabezados", "Imágenes", "Enlaces"}, 0
        );
        jTableUrlsAnalizadas.setModel(initialModel);
        jTableUrlsAnalizadasSeleccionada.setModel(initialModel);
        establecerAnchocolumnas();
        
         
        
        // Agregar MouseListener para capturar clic en jTableDominios
        jTableDominios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = jTableDominios.getSelectedRow(); // Obtener la fila seleccionada

                if (selectedRow != -1) { // Asegurarse de que hay una fila seleccionada
                    // Obtener el valor de la columna donde está el dominio (por ejemplo, columna 0)
                    String dominio = jTableDominios.getValueAt(selectedRow, 0).toString();

                    // Llamar al método para mostrar los análisis del dominio seleccionado en otra tabla
                    selectUrlsAnalizadasTablaSeleccionada(jTableUrlsAnalizadasSeleccionada, dominio);
                    establecerAnchocolumnas();

                  //  jLabelUrlSeleccionada.setText(dominio);
                    
                    ocultarIdAnalisis();
                }

            }
        });

        // Agregar MouseListener para capturar clic en jTableUrlsAnalizadas
        jTableUrlsAnalizadas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = jTableUrlsAnalizadas.getSelectedRow(); // Obtener la fila seleccionada

                if (selectedRow != -1) { // Asegurarse de que hay una fila seleccionada
                    // Obtener el valor de la columna donde está el dominio (por ejemplo, columna 0)
                    String dominio = jTableUrlsAnalizadas.getValueAt(selectedRow, 1).toString();
                    String idAnalisis = jTableUrlsAnalizadas.getValueAt(selectedRow, 0).toString();

                    // Llamar al método para mostrar los análisis del dominio seleccionado en otra tabla
                    jLabelUrlSeleccionada.setText(dominio);

                    rellenaLabels(idAnalisis);
                }

            }
        });

        // Agregar MouseListener para capturar clic en jTableUrlsAnalizadas
        jTableUrlsAnalizadasSeleccionada.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = jTableUrlsAnalizadasSeleccionada.getSelectedRow(); // Obtener la fila seleccionada

                if (selectedRow != -1) { // Asegurarse de que hay una fila seleccionada
                    // Obtener el valor de la columna donde está el dominio (por ejemplo, columna 0)
                    String urlSeleccionada = jTableUrlsAnalizadasSeleccionada.getValueAt(selectedRow, 1).toString();
                    String idAnalisis = jTableUrlsAnalizadasSeleccionada.getValueAt(selectedRow, 0).toString();

                    // Llamar al método para mostrar los análisis del dominio seleccionado en otra tabla
                    jLabelUrlSeleccionada.setText(urlSeleccionada);

                    /*selectMetaTitleTabla(jTableTitle,idAnalisis);
                     selectMetaDescriptionTabla(jTableDescription,idAnalisis);*/
                    rellenaLabels(idAnalisis);

                }

            }
        });
        
        
       jTableImagenes.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = jTableImagenes.getSelectedRow(); // Obtener la fila seleccionada

        if (selectedRow != -1) { // Asegurarse de que hay una fila seleccionada
            String urlImagen = jTableImagenes.getValueAt(selectedRow, 0).toString();
             // Limpiar cualquier texto o imagen previa en jLabelImagen
            jLabelImagen.setIcon(null);
            jLabelImagen.setText("");
            try {
                // Crear conexión con el User-Agent especificado
                URL url = new URL(urlImagen);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");

                // Leer la imagen desde la conexión
                Image image = ImageIO.read(connection.getInputStream());
                
                if (image != null) {

                // Escalar la imagen para ajustarla al JLabel
                Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                jLabelImagen.setIcon(new ImageIcon(scaledImage));
                }else{
                    System.out.println("La imagen no se puede cargar");
                    jLabelImagen.setText("no se puede cargar");
                }

            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("No se pudo cargar la imagen desde la URL proporcionada.");
            }
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
                 ocultarIdAnalisis(); // Ocultar ID
                 
                // Seleccionar la primera fila después de cargar los datos
                if (jTableUrlsAnalizadas.getRowCount() > 0) {
                    jTableUrlsAnalizadas.setRowSelectionInterval(0, 0);
                    String idAnalisis = jTableUrlsAnalizadas.getValueAt(0, 0).toString();
                    rellenaLabels(idAnalisis); // Cargar datos de la primera fila
                   jLabelUrlSeleccionada.setText(jTableUrlsAnalizadas.getValueAt(0, 1).toString()); // Mostrar URL
                }
                
                 // Seleccionar la primera fila después de cargar los datos
                if (jTableDominios.getRowCount() > 0) {
                    jTableDominios.setRowSelectionInterval(0, 0);
                    String idAnalisis = jTableDominios.getValueAt(0, 0).toString();
                   selectUrlsAnalizadasTablaSeleccionada(jTableUrlsAnalizadasSeleccionada, idAnalisis);
                    establecerAnchocolumnas();

                    ocultarIdAnalisis();
                   
                   
                }
                 
                 
                 
                 
                 
                 
            }
        });

    }
    
    private void ocultarIdAnalisis() {
        // Suponiendo que el ID está en la primera columna (índice 0)
        jTableUrlsAnalizadas.getColumnModel().getColumn(0).setMinWidth(0);
        jTableUrlsAnalizadas.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableUrlsAnalizadas.getColumnModel().getColumn(0).setPreferredWidth(0);

        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(0).setMinWidth(0);
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(0).setPreferredWidth(0);

    }

    private void rellenaLabels(String idAnalisis) {

        selectMetaTitleTabla(jTableTitle, idAnalisis);
        selectMetaDescriptionTabla(jTableDescription, idAnalisis);
        selectMetaTitleTabla(jTableTitle, idAnalisis);
        selectMetaDescriptionTabla(jTableDescription, idAnalisis);
        selectEncabezadosTabla(jTableEncabezados, idAnalisis);
        selectImagenesTabla(jTableImagenes, idAnalisis);
        selectEnlacesTabla(jTableEnlaces, idAnalisis);
        selectTextoTabla(jTableTexto, idAnalisis);
        
        ocultarIdAnalisis();
        
        
    }

    private void establecerAnchocolumnas() {

        //establecer ancho de las columnas
      
        jTableUrlsAnalizadas.getColumnModel().getColumn(1).setPreferredWidth(400);
        jTableUrlsAnalizadas.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTableUrlsAnalizadas.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTableUrlsAnalizadas.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTableUrlsAnalizadas.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTableUrlsAnalizadas.getColumnModel().getColumn(6).setPreferredWidth(100);

       
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(1).setPreferredWidth(700);
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(6).setPreferredWidth(100);

        jTableDominios.getColumnModel().getColumn(0).setPreferredWidth(300);
        jTableDominios.getColumnModel().getColumn(1).setPreferredWidth(200);

        //alineacion de las columnas
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER); // Alineación a la derecha

        
        jTableUrlsAnalizadas.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        jTableUrlsAnalizadas.getColumnModel().getColumn(3).setCellRenderer(rightRenderer); // Columna 0 alineada a la derecha
        jTableUrlsAnalizadas.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        jTableUrlsAnalizadas.getColumnModel().getColumn(5).setCellRenderer(rightRenderer); // Columna 0 alineada a la derecha
        jTableUrlsAnalizadas.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
       

       
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(3).setCellRenderer(rightRenderer); // Columna 0 alineada a la derecha
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(5).setCellRenderer(rightRenderer); // Columna 0 alineada a la derecha
        jTableUrlsAnalizadasSeleccionada.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
       

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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTitle = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDescription = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableEncabezados = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableImagenes = new javax.swing.JTable();
        jLabelImagen = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableEnlaces = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTableTexto = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableResumen = new javax.swing.JTable();
        jButtonTemaClaro = new javax.swing.JButton();
        jButtonTemaOscuro = new javax.swing.JButton();
        jLabelUrlSeleccionada = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setForeground(new java.awt.Color(255, 255, 204));

        jButtonAnalizar.setText("Analizar");
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

        jLabel3.setText("URLs Analizadas del dominio: ");

        jLabel4.setText("Últimas URLs Analizadas:");

        jLabel5.setText("Dominios Analizados:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(29, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel3))
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
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        jTabbedPane1.addTab("GENERAL", jPanel1);

        jTableTitle.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableTitle);

        jLabel1.setText("TITLE");

        jTableDescription.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableDescription);

        jLabel2.setText("META DESCRIPTION");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
                        .addComponent(jScrollPane2)))
                .addContainerGap(367, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(226, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("META ETIQUETAS", jPanel3);

        jTableEncabezados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableEncabezados);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(281, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ENCABEZADOS (Hs)", jPanel2);

        jTableImagenes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTableImagenes);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabelImagen)
                .addContainerGap(177, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabelImagen)))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("IMÁGENES", jPanel4);

        jTableEnlaces.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(jTableEnlaces);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1042, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(226, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ENLACES", jPanel5);

        jTableTexto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(jTableTexto);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(796, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("TEXTO", jPanel6);

        jTableResumen.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(jTableResumen);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(764, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("RESUMEN", jPanel7);

        jButtonTemaClaro.setText("CLARO");
        jButtonTemaClaro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTemaClaroActionPerformed(evt);
            }
        });

        jButtonTemaOscuro.setText("OSCURO");
        jButtonTemaOscuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTemaOscuroActionPerformed(evt);
            }
        });

        jLabelUrlSeleccionada.setText("URL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addComponent(jLabelUrlSeleccionada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonTemaClaro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonTemaOscuro)
                .addGap(48, 48, 48))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonTemaClaro)
                    .addComponent(jButtonTemaOscuro)
                    .addComponent(jLabelUrlSeleccionada))
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

    private void jButtonTemaOscuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTemaOscuroActionPerformed
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

    }//GEN-LAST:event_jButtonTemaOscuroActionPerformed

    public void aplicarTemaClaro() {
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

    }


    private void jButtonTemaClaroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTemaClaroActionPerformed
        // TODO add your handling code here:
        aplicarTemaClaro();


    }//GEN-LAST:event_jButtonTemaClaroActionPerformed

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
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "URL", "Fecha de Análisis", "Title", "Description", "Encabezados", "Imágenes", "Enlaces"}, 0);

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
                tableModel.addRow(new Object[]{id_analisis, urlAnalizada, fechaAnalisis, erroresTitle, erroresDescription, erroresEncabezados, erroresImagenes, erroresEnlaces});
            }

            // Asignar el modelo a la tabla
            jTableUrlsAnalizadas.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectUrlsAnalizadasTablaSeleccionada(JTable jTableUrlsAnalizadasSeleccionada, String dominio) {
        String sql = "SELECT id_analisis,dominio, url_principal, fecha_analisis FROM Analisis where dominio = '" + dominio + "' ORDER BY fecha_analisis DESC ";
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "URL", "Fecha de Análisis", "Title", "Description", "Encabezados", "Imágenes", "Enlaces"}, 0);

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
                tableModel.addRow(new Object[]{id_analisis, urlAnalizada, fechaAnalisis, erroresTitle, erroresDescription, erroresEncabezados, erroresImagenes, erroresEnlaces});
            }

            // Asignar el modelo a la tabla
            jTableUrlsAnalizadasSeleccionada.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectMetaTitleTabla(JTable jTableTitle, String idAnalisis) {

        String sql = "SELECT titulo_pagina, estado From MetaTitle where id_analisis ='" + idAnalisis + "'";
        System.out.println(sql);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"META TITLE", "ESTADO"}, 0);

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String dominio = rs.getString("titulo_pagina");
                String cantidadAnalisis = rs.getString("estado");

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{dominio, cantidadAnalisis});
            }

            // Asignar el modelo a la tabla
            jTableTitle.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void selectMetaDescriptionTabla(JTable jTableDescription, String idAnalisis) {

        String sql = "SELECT meta_descripcion, estado From MetaDescription where id_analisis ='" + idAnalisis + "'";
        System.out.println(sql);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"META DESCRIPTION", "ESTADO"}, 0);

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String dominio = rs.getString("meta_descripcion");
                String cantidadAnalisis = rs.getString("estado");

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{dominio, cantidadAnalisis});
            }

            // Asignar el modelo a la tabla
            jTableDescription.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void selectEncabezadosTabla(JTable jTableEncabezados, String idAnalisis) {

        String sql = "SELECT nivel,contenido, estado From Encabezados where id_analisis ='" + idAnalisis + "'";
        System.out.println(sql);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"NIVEL", "CONTENIDO", "ESTADO"}, 0);

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String nivel = rs.getString("nivel");
                String contenido = rs.getString("contenido");
                String estado = rs.getString("estado");

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{nivel, contenido, estado});
            }

            // Asignar el modelo a la tabla
            jTableEncabezados.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public static void selectImagenesTabla(JTable jTableImagenes, String idAnalisis) {

        String sql = "SELECT ruta_imagen,alt_texto, estado From Imagenes where id_analisis ='" + idAnalisis + "'";
        System.out.println(sql);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"RUTA IMAGEN", "ALT", "ESTADO"}, 0);

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String ruta_imagen = rs.getString("ruta_imagen");
                String alt_texto = rs.getString("alt_texto");
                String estado = rs.getString("estado");

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{ruta_imagen, alt_texto, estado});
            }

            // Asignar el modelo a la tabla
            jTableImagenes.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public static void selectEnlacesTabla(JTable jTableEnlaces, String idAnalisis) {

        String sql = "SELECT url_enlace,tipo,anchor_text, estado From Enlaces where id_analisis ='" + idAnalisis + "'";
        System.out.println(sql);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"URL", "INTERNO/EXTERNO","ANCHOR TEXT", "ESTADO"}, 0);

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String url_enlace = rs.getString("url_enlace");
                String tipo = rs.getString("tipo");
                String achor_text = rs.getString("anchor_text");
                String estado = rs.getString("estado");

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{url_enlace, tipo, achor_text, estado});
            }

            // Asignar el modelo a la tabla
            jTableEnlaces.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    public static void selectTextoTabla(JTable jTableTexto, String idAnalisis) {

        String sql = "SELECT palabra,frecuencia,densidad From Texto where id_analisis ='" + idAnalisis + "'";
        System.out.println(sql);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"PALABRA", "Nº VECES","DENSIDAD(%)"}, 0);

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String palabra = rs.getString("palabra");
                String frecuencia = rs.getString("frecuencia");
                String densidad = rs.getString("densidad");
                

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{palabra, frecuencia, densidad});
            }

            // Asignar el modelo a la tabla
            jTableTexto.setModel(tableModel);

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
    private javax.swing.JButton jButtonAnalizar;
    private javax.swing.JButton jButtonTemaClaro;
    private javax.swing.JButton jButtonTemaOscuro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelImagen;
    private javax.swing.JLabel jLabelUrlSeleccionada;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableDescription;
    private javax.swing.JTable jTableDominios;
    private javax.swing.JTable jTableEncabezados;
    private javax.swing.JTable jTableEnlaces;
    private javax.swing.JTable jTableImagenes;
    private javax.swing.JTable jTableResumen;
    private javax.swing.JTable jTableTexto;
    private javax.swing.JTable jTableTitle;
    private javax.swing.JTable jTableUrlsAnalizadas;
    private javax.swing.JTable jTableUrlsAnalizadasSeleccionada;
    private javax.swing.JTextField jTextFieldURL;
    // End of variables declaration//GEN-END:variables
}
