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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Gonzalo Román Márquez
 */
public class inicio extends javax.swing.JFrame {

    String idAnalisisActual = null;
    DefaultTableModel initialModel;
    private Timer timer; //timer para poder hacer que el mensaje analizando parpadee

    /**
     * Creates new form inicio
     */
    public inicio() {
        initComponents();
        aplicarTemaClaro();
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        jLabelAnalizando.setVisible(false);

        desactivarReordenarColumnas();

        // Asigna un modelo vacío con encabezados personalizados desde el inicio
        initialModel = new DefaultTableModel(
                new String[]{"ID", "URL", "Fecha de Análisis", "Title", "Description", "Encabezados", "Imágenes", "Enlaces"}, 0) {
           
        };

        jTableUrlsAnalizadas.setModel(initialModel);
        jTableUrlsAnalizadasSeleccionada.setModel(initialModel);
        establecerAnchocolumnas();

        desactivarPestañas();
        ocultarIdAnalisis();
        
        jLabelLogo.setText("");
        

        // Configurar el Timer para el efecto de parpadeo
        timer = new Timer(500, e -> {
            // Alterna entre dos colores para el efecto de parpadeo
            if (jLabelAnalizando.getForeground().equals(Color.LIGHT_GRAY)) {
                jLabelAnalizando.setForeground(Color.GRAY);
            } else {
                jLabelAnalizando.setForeground(Color.LIGHT_GRAY);
            }
        });

        // Desactiva el botón de eliminar al inicio
        jButtonEliminarAnalisis.setEnabled(false);

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

                    jLabelDominioSeleccionado.setText(dominio);
                    jLabelUrlAbajo.setText("");
                    ocultarIdAnalisis();
                    establecerAnchocolumnas();
                    jButtonEliminarAnalisis.setEnabled(false);//ocultamos el boton eliminar
                    desactivarPestañas();
                    jLabelUrlSeleccionada.setText("");

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
                    String fecha = jTableUrlsAnalizadas.getValueAt(selectedRow, 2).toString();

                    idAnalisisActual = jTableUrlsAnalizadas.getValueAt(selectedRow, 0).toString();

                    // Llamar al método para mostrar los análisis del dominio seleccionado en otra tabla
                    jLabelUrlSeleccionada.setText(fecha + " " + dominio);
                    jLabelUrlAbajo.setText(fecha + " " + dominio);
                    rellenaLabels(idAnalisis);
                    jButtonEliminarAnalisis.setEnabled(true);//activamos el boton eliminar

                    // Habilitar todas las pestañas al seleccionar una URL
                    for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
                        jTabbedPane1.setEnabledAt(i, true);
                    }
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
                    String fecha = jTableUrlsAnalizadasSeleccionada.getValueAt(selectedRow, 2).toString();
                    idAnalisisActual = jTableUrlsAnalizadasSeleccionada.getValueAt(selectedRow, 0).toString();

                    // Llamar al método para mostrar los análisis del dominio seleccionado en otra tabla
                    jLabelUrlSeleccionada.setText(fecha + " " + urlSeleccionada);
                    jLabelUrlAbajo.setText(fecha + " " + urlSeleccionada);

                   
                    rellenaLabels(idAnalisis);
                    jButtonEliminarAnalisis.setEnabled(true);//desactivamos el boton eliminar

                    // Habilitar todas las pestañas al seleccionar una URL
                    for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
                        jTabbedPane1.setEnabledAt(i, true);
                    }

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
                        } else {
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

                //   establecerAnchocolumnas();
                ocultarIdAnalisis(); // Ocultar ID

                cargaPrimerosDatos();

            }
        });

    }

    private void desactivarReordenarColumnas() {
        jTableUrlsAnalizadas.getTableHeader().setReorderingAllowed(false);
        jTableUrlsAnalizadasSeleccionada.getTableHeader().setReorderingAllowed(false);
        jTableDominios.getTableHeader().setReorderingAllowed(false);
        jTableTitle.getTableHeader().setReorderingAllowed(false);
        jTableDescription.getTableHeader().setReorderingAllowed(false);
        jTableEncabezados.getTableHeader().setReorderingAllowed(false);
        jTableImagenes.getTableHeader().setReorderingAllowed(false);
        jTableEnlaces.getTableHeader().setReorderingAllowed(false);
        jTableTexto.getTableHeader().setReorderingAllowed(false);
        jTableResumen.getTableHeader().setReorderingAllowed(false);
        jTableResumenErrores.getTableHeader().setReorderingAllowed(false);
    }

    private void cargaPrimerosDatos() {
        // Seleccionar la primera fila después de cargar los datos
        if (jTableUrlsAnalizadas.getRowCount() > 0) {
            jTableUrlsAnalizadas.setRowSelectionInterval(0, 0);
            String idAnalisis = jTableUrlsAnalizadas.getValueAt(0, 0).toString();
            rellenaLabels(idAnalisis); // Cargar datos de la primera fila
            //jLabelUrlSeleccionada.setText(jTableUrlsAnalizadas.getValueAt(0, 1).toString()); // Mostrar URL
            jLabelUrlSeleccionada.setText("");
        }

        // Seleccionar la primera fila después de cargar los datos
        if (jTableDominios.getRowCount() > 0) {
            jTableDominios.setRowSelectionInterval(0, 0);
            String idAnalisis = jTableDominios.getValueAt(0, 0).toString();
            selectUrlsAnalizadasTablaSeleccionada(jTableUrlsAnalizadasSeleccionada, idAnalisis);
            establecerAnchocolumnas();
            String dominio = jTableDominios.getValueAt(0, 0).toString();
            jLabelDominioSeleccionado.setText(dominio);
            ocultarIdAnalisis();

        }

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

    private void desactivarPestañas() {
        // Inicializar el JTabbedPane desactivando todas las pestañas
        for (int i = 1; i < jTabbedPane1.getTabCount(); i++) {
            jTabbedPane1.setEnabledAt(i, false);
        }

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
        selectResumenTabla(jTableResumen, idAnalisis);

        selectListadoResumenErroresTabla(jTableResumenErrores, idAnalisis);

        ocultarIdAnalisis();
        establecerAnchocolumnas();

    }

    private void establecerAnchocolumnas() {
        // Configuración de ancho de columnas para cada tabla
        int[] anchosUrlsAnalizadas = {0, 400, 150, 100, 100, 100, 100};
        int[] anchosUrlsAnalizadasSeleccionada = {0, 700, 150, 100, 100, 100, 100};
        int[] anchosDominios = {300, 200};
        int[] anchosTitle = {600, 50};
        int[] anchosDescription = {600, 50};
        int[] anchosEncabezados = {50, 700, 50};
        int[] anchosImagenes = {400, 300, 50};
        int[] anchosEnlaces = {400, 50, 200, 50};
        int[] anchosTexto = {400, 100, 200};
        int[] anchosResumen = {400, 100, 100, 100, 100, 100};
        int[] anchosResumenErrores = {100, 700, 300};

        // Aplicar ajustes de ancho y alineación
        ajustarAnchoColumnas(jTableUrlsAnalizadas, anchosUrlsAnalizadas, true);
        ajustarAnchoColumnas(jTableUrlsAnalizadasSeleccionada, anchosUrlsAnalizadasSeleccionada, true);
        ajustarAnchoColumnas(jTableDominios, anchosDominios, true);

        ajustarAnchoColumnas(jTableTitle, anchosTitle, true);
        ajustarAnchoColumnas(jTableDescription, anchosDescription, true);
        ajustarAnchoColumnas(jTableEncabezados, anchosEncabezados, true);
        ajustarAnchoColumnas(jTableImagenes, anchosImagenes, true);
        ajustarAnchoColumnas(jTableEnlaces, anchosEnlaces, true);
        ajustarAnchoColumnas(jTableTexto, anchosTexto, true);

        ajustarAnchoColumnas(jTableResumen, anchosResumen, true);
        ajustarAnchoColumnas(jTableResumenErrores, anchosResumenErrores, true);

    }

// Método de utilidad para ajustar ancho y alineación de columnas en cualquier JTable
    private void ajustarAnchoColumnas(JTable table, int[] anchos, boolean alinearCentro) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        // Aplicar ancho y alineación en las columnas indicadas
        for (int i = 0; i < anchos.length && i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            if (alinearCentro && i > 1) { // Aplicar alineación en las columnas a partir de la segunda
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
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
        jButtonEliminarAnalisis = new javax.swing.JButton();
        jLabelDominioSeleccionado = new javax.swing.JLabel();
        jLabelUrlAbajo = new javax.swing.JLabel();
        jLabelAnalizando = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
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
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableImagenes = new javax.swing.JTable();
        jLabelImagen = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableEnlaces = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTableTexto = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTableResumen = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTableResumenErrores = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButtonTemaClaro = new javax.swing.JButton();
        jButtonTemaOscuro = new javax.swing.JButton();
        jLabelUrlSeleccionada = new javax.swing.JLabel();
        jLabelLogo = new javax.swing.JLabel();

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

        jButtonEliminarAnalisis.setText("ELIMINAR");
        jButtonEliminarAnalisis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarAnalisisActionPerformed(evt);
            }
        });

        jLabelDominioSeleccionado.setText("Dominio");

        jLabelAnalizando.setText("...ANALIZANDO...");

        jLabel7.setText("Errores Encontrados:");

        jSeparator2.setAlignmentX(0.0F);
        jSeparator2.setAlignmentY(0.0F);
        jSeparator2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(188, 188, 188))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(11, 11, 11)))))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDominioSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelUrlAbajo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonEliminarAnalisis)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelAnalizando)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextFieldURL, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonAnalizar)))
                        .addGap(501, 501, 501))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonAnalizar))
                        .addGap(11, 11, 11)
                        .addComponent(jLabelAnalizando)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelDominioSeleccionado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEliminarAnalisis)
                    .addComponent(jLabelUrlAbajo))
                .addGap(21, 21, 21))
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
                .addContainerGap(345, Short.MAX_VALUE))
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

        jLabel12.setText("Análisis de Estructura de Encabezados");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(252, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
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

        jLabel11.setText("Análisis de Imágenes");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelImagen)
                .addContainerGap(155, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabelImagen))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(95, Short.MAX_VALUE))
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

        jLabel10.setText("Análisis de Enlaces");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1042, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
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

        jLabel9.setText("Densidad de Palabras");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(774, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
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

        jTableResumenErrores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(jTableResumenErrores);

        jLabel6.setText("Listado de Errores");

        jLabel8.setText("Errores Encontrados");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
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

        jLabelUrlSeleccionada.setText("Realiza un Analisis");

        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelLogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(1054, 1054, 1054)
                                .addComponent(jButtonTemaClaro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonTemaOscuro)
                                .addGap(20, 20, 20))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelUrlSeleccionada)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonTemaClaro)
                            .addComponent(jButtonTemaOscuro)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUrlSeleccionada, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelLogo, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Método auxiliar para validar la URL
    private boolean isUrlValida(String urlText) {
        try {
            new URL(urlText); // Si no lanza una excepción, es válida
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }


    private void jButtonAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnalizarActionPerformed
        // TODO add your handling code here:
        jButtonAnalizar.setEnabled(false);
        String urlText = jTextFieldURL.getText();

        // Verificar si la URL es válida
        if (!isUrlValida(urlText) && !urlText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La URL no está bien formada. Por favor, ingrese una URL válida.\n ejemplo: https://www.dominio.com/url/", "Error", JOptionPane.ERROR_MESSAGE);
            jButtonAnalizar.setEnabled(true);
            return;
        }

        if (urlText.isEmpty()) {
            jButtonAnalizar.setEnabled(true);
            return;
        }

        jLabelAnalizando.setVisible(true);
        String url = jTextFieldURL.getText();

        jLabelAnalizando.setText("...Analizando..." + url);

        jTextFieldURL.setText("");

        timer.start(); // Inicia el temporizador

        // Crear un SwingWorker para ejecutar el análisis en segundo plano
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    Boolean resultado = Ejecutar.hacer(url); // Ejecuta el proceso en segundo plano

                    if (!resultado) {
                        //Mensaje de pagina no encontrada o rota
                       
                        JOptionPane.showMessageDialog(
                                inicio.this,
                                "Error: La URL \n" + url + " \n no se encuentra o está rota.",
                                "Error de Análisis",
                                JOptionPane.ERROR_MESSAGE
                        );

                    }

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
                jLabelAnalizando.setVisible(false);
                timer.stop(); // Detiene el temporizador
                jButtonAnalizar.setEnabled(true);
                URL url1;
                try {
                    url1 = new URL(url);
                    String dominio = url1.getHost();
                    selectUrlsAnalizadasTablaSeleccionada(jTableUrlsAnalizadasSeleccionada, dominio);

                } catch (MalformedURLException ex) {
                    Logger.getLogger(inicio.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Seleccionar la primera fila y darle el foco
                if (jTableUrlsAnalizadas.getRowCount() > 0) {
                    jTableUrlsAnalizadas.setRowSelectionInterval(0, 0); // Selecciona la primera fila
                    jTableUrlsAnalizadas.requestFocus(); // Coloca el foco en la tabla

                    // Simula el clic en la primera fila
                    java.awt.event.MouseEvent clickEvent = new java.awt.event.MouseEvent(
                            jTableUrlsAnalizadas,
                            java.awt.event.MouseEvent.MOUSE_CLICKED,
                            System.currentTimeMillis(),
                            0,
                            0,
                            0,
                            1,
                            false
                    );
                    jTableUrlsAnalizadas.dispatchEvent(clickEvent);
                }

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
            
             ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/logo_oscuro.png"));
             jLabelLogo.setIcon(icono);

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
            
             ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/logo.png"));
       jLabelLogo.setIcon(icono);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void jButtonTemaClaroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTemaClaroActionPerformed
        // TODO add your handling code here:
        aplicarTemaClaro();


    }//GEN-LAST:event_jButtonTemaClaroActionPerformed

    public static void eliminarAnalisis(String idAnalisis) {
        String[] tablas = {"MetaTitle", "MetaDescription", "Encabezados", "Imagenes", "Enlaces", "Texto", "Analisis"};

        try (Connection conn = Consultas.connect()) {
            for (String tabla : tablas) {
                String sql = "DELETE FROM " + tabla + " WHERE id_analisis = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, idAnalisis);
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Análisis con ID " + idAnalisis + " eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el análisis: " + e.getMessage());
        }
    }


    private void jButtonEliminarAnalisisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarAnalisisActionPerformed
        // TODO add your handling code here:

        //eliminarAnalisis(idAnalisisActual); // Llamada al método de eliminación
        if (idAnalisisActual != null) { // Verifica que hay un análisis seleccionado
            eliminarAnalisis(idAnalisisActual); // Llamada al método de eliminación
            JOptionPane.showMessageDialog(this, "Análisis eliminado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un análisis para eliminar.");
        }

        selectUrlsAnalizadasTabla(jTableUrlsAnalizadas);
        selectDominioTabla(jTableDominios);

        desactivarPestañas();

        cargaPrimerosDatos();

        if (jTableDominios.getRowCount() == 0) {
            // Si no quedan dominios, vacía la tabla de URLs analizadas del dominio
            DefaultTableModel model = (DefaultTableModel) jTableUrlsAnalizadasSeleccionada.getModel();
            model.setRowCount(0);
            jLabelUrlSeleccionada.setText("No hay análisis disponibles");
        }
        jButtonEliminarAnalisis.setEnabled(false);
        jLabelUrlAbajo.setText("Análisis Eliminado");

    }//GEN-LAST:event_jButtonEliminarAnalisisActionPerformed

    public void selectDominioTabla(JTable jTableDominios) {

        String sql = "SELECT dominio, COUNT(*) AS cantidad_analisis, MAX(fecha_analisis) AS ultima_fecha "
                + "FROM Analisis "
                + "GROUP BY dominio "
                + "ORDER BY ultima_fecha DESC";

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"DOMINIO", "CANTIDAD ANALISIS"}, 0) {
          /*  @Override
            public boolean isCellEditable(int row, int column) {
                // Deshabilita la edición para todas las celdas
                return false;
            }*/
        };

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

    public void selectUrlsAnalizadasTabla(JTable jTableUrlsAnalizadas) {
        String sql = "SELECT id_analisis, url_principal, fecha_analisis FROM Analisis ORDER BY fecha_analisis DESC";
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "URL", "Fecha de Análisis", "Title", "Description", "Encabezados", "Imágenes", "Enlaces"}, 0) {
        
        };

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
            ocultarIdAnalisis(); // Oculta la columna ID después de cargar los datos

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectUrlsAnalizadasTablaSeleccionada(JTable jTableUrlsAnalizadasSeleccionada, String dominio) {
        String sql = "SELECT id_analisis,dominio, url_principal, fecha_analisis FROM Analisis where dominio = '" + dominio + "' ORDER BY fecha_analisis DESC ";
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "URL", "Fecha de Análisis", "Title", "Description", "Encabezados", "Imágenes", "Enlaces"}, 0) {
           
        };

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
            ocultarIdAnalisis();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectMetaTitleTabla(JTable jTableTitle, String idAnalisis) {

        String sql = "SELECT titulo_pagina, estado From MetaTitle where id_analisis ='" + idAnalisis + "'";
        System.out.println(sql);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"META TITLE", "ESTADO"}, 0) {
         /*   @Override
            public boolean isCellEditable(int row, int column) {
                // Deshabilita la edición para todas las celdas
                return false;
            }*/
        };

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

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"META DESCRIPTION", "ESTADO"}, 0) {
         /*   @Override
            public boolean isCellEditable(int row, int column) {
                // Deshabilita la edición para todas las celdas
                return false;
            }*/
        };

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

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"NIVEL", "CONTENIDO", "ESTADO"}, 0) {
         
        };

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

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"RUTA IMAGEN", "ALT", "ESTADO"}, 0) {
           
        };

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

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"URL", "INTERNO/EXTERNO", "ANCHOR TEXT", "ESTADO"}, 0) {
        
        };

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
       // System.out.println(sql);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"PALABRA", "Nº VECES", "DENSIDAD(%)"}, 0) {
          
        };

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

    public static void selectResumenTabla(JTable jTableResumen, String idAnalisis) {

        String sql = "SELECT id_analisis,dominio, url_principal, fecha_analisis FROM Analisis where id_analisis = '" + idAnalisis + "' ORDER BY fecha_analisis DESC ";
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"URL", "Fecha de Análisis", "Title", "Description", "Encabezados", "Imágenes", "Enlaces"}, 0) {
           
        };

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
            jTableResumen.setModel(tableModel);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void selectListadoResumenErroresTabla(JTable jTableResumenErrores, String idAnalisis) {

        
        String sql = "SELECT 'MetaTitle' AS origen, titulo_pagina AS detalle, 'Meta Title Vacio' AS estado "
                + "FROM MetaTitle "
                + "WHERE id_analisis = '" + idAnalisis + "' AND estado = 'Error' "
                + "UNION ALL "
                + "SELECT 'MetaDescription' AS origen, meta_descripcion AS detalle, 'Meta Description Vacía' AS estado "
                + "FROM MetaDescription "
                + "WHERE id_analisis = '" + idAnalisis + "' AND estado = 'Error' "
                + "UNION ALL "
                + "SELECT nivel AS origen, contenido AS detalle, informacion "
                + "FROM Encabezados "
                + "WHERE id_analisis = '" + idAnalisis + "' AND estado = 'Error' "
                + "UNION ALL "
                + "SELECT 'Imagenes' AS origen, ruta_imagen AS detalle, 'no tiene Alt' AS estado "
                + "FROM Imagenes "
                + "WHERE id_analisis = '" + idAnalisis + "' AND estado = 'Error' "
                + "UNION ALL "
                + "SELECT 'Enlaces' AS origen, url_enlace AS detalle, 'Enlace Roto' AS estado  "
                + "FROM Enlaces "
                + "WHERE id_analisis = '" + idAnalisis + "' AND estado = 'Error'";

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ERROR EN", "DETALLE", "ESTADO"}, 0) {
          
        };

        try (Connection conn = Consultas.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            // Iterar sobre los resultados de la consulta y agregar al modelo de tabla
            while (rs.next()) {
                String origen = rs.getString("origen");
                String detalle = rs.getString("detalle");
                String estado = rs.getString("estado");

                // Agregar una nueva fila a la tabla
                tableModel.addRow(new Object[]{origen, detalle, estado});
            }

            // Asignar el modelo a la tabla
            jTableResumenErrores.setModel(tableModel);

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
            
            FlatMacLightLaf.install();   // Tema claro  macOS
           

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
    private javax.swing.JButton jButtonEliminarAnalisis;
    private javax.swing.JButton jButtonTemaClaro;
    private javax.swing.JButton jButtonTemaOscuro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAnalizando;
    private javax.swing.JLabel jLabelDominioSeleccionado;
    private javax.swing.JLabel jLabelImagen;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelUrlAbajo;
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
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableDescription;
    private javax.swing.JTable jTableDominios;
    private javax.swing.JTable jTableEncabezados;
    private javax.swing.JTable jTableEnlaces;
    private javax.swing.JTable jTableImagenes;
    private javax.swing.JTable jTableResumen;
    private javax.swing.JTable jTableResumenErrores;
    private javax.swing.JTable jTableTexto;
    private javax.swing.JTable jTableTitle;
    private javax.swing.JTable jTableUrlsAnalizadas;
    private javax.swing.JTable jTableUrlsAnalizadasSeleccionada;
    private javax.swing.JTextField jTextFieldURL;
    // End of variables declaration//GEN-END:variables
}
