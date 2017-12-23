/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.awt.HeadlessException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Administrador
 */
public class Venta extends javax.swing.JDialog {

    /**
     * Creates new form Venta
     */
    Connection cn = null;
    ConexionTienda cc = new ConexionTienda();
    int codCab;
    DefaultTableModel modeloTabla;
    TableColumnModel modeloColumna;

    public Venta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        establecerFecha();
        establecerUsuario();
        habilitarEdicionCamposCliente(true);
        modeloTablaCarrito();
        establecerTamañoColumnas();
    }

    public void establecerFecha() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Calendar fecha = Calendar.getInstance();
        jLabel_Fecha.setText(formateador.format(fecha.getTime()));
    }

    public void establecerUsuario() {
        String sql = "select * from usuarios where cod_usu='" + FramePrincipal.cedUsuario + "'";
//        String sql = "select * from usuarios where cod_usu='1101715876'";
        cn = cc.conectar();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                jTextField_CedEmp.setText(rs.getString("COD_USU"));
                jTextField_NomEmp.setText(rs.getString("NOM_USU") + " " + rs.getString("APE_USU"));
            }
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al obtener datos de la base:\n" + ex);
            try {
                cn.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error de conexión");
            }
        }
    }

    public void habilitarEdicionCamposCliente(boolean habilitado) {
        jTextField_NomCli.setEditable(habilitado);
        jTextField_CedCli.setEditable(habilitado);
        jTextField_CedCli.requestFocus();
        jTextField_ApeCli.setEditable(habilitado);
        jTextField_TelCli.setEditable(habilitado);
        jTextField_DirCli.setEditable(habilitado);
    }

    public void txtLimpiar() {
        jTextField_CedCli.setText("");
        jTextField_NomCli.setText("");
        jTextField_ApeCli.setText("");
        jTextField_DirCli.setText("");
        jTextField_TelCli.setText("");
        habilitarEdicionCamposCliente(true);
        jButton_Cargar.setEnabled(true);
        jButton_Seleccionar.setEnabled(false);
    }

    public void cargarCliente() {
        if (jTextField_CedCli.getText().isEmpty() || !Metodos.verificadorCédula(jTextField_CedCli.getText())) {
            JOptionPane.showMessageDialog(null, "Cédula Errónea");
            jTextField_CedCli.requestFocus(); // Para posicionar el raton
        } else {
            String sql = "select * from clientes where ced_cli='" + jTextField_CedCli.getText() + "'";
            boolean clienteExiste = false;
//        String sql = "select * from clientes where ced_cli='172438393'";
            cn = cc.conectar();
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    jTextField_NomCli.setText(rs.getString("NOM_CLI"));
                    jTextField_ApeCli.setText(rs.getString("APE_CLI"));
                    jTextField_TelCli.setText(rs.getString("TEL_CLI"));
                    jTextField_DirCli.setText(rs.getString("DIR_CLI"));
                    jTextField_CedCli.setEditable(false);
                    jButton_Cargar.setEnabled(false);
                    clienteExiste = true;
                    habilitarEdicionCamposCliente(false);
                    jButton_Seleccionar.setEnabled(true);
                }
                cn.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al obtener datos de la base:\n" + ex);
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    JOptionPane.showMessageDialog(null, "Error de conexión");
                }
            }
            if (!clienteExiste) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Desea ingresar cliente?", "Cliente no existe", JOptionPane.YES_OPTION);
                if (opcion == 0) {
                    habilitarEdicionCamposCliente(true);
                    jTextField_NomCli.requestFocus();
                    jButton_Guardar.setEnabled(true);
                }
            } else {
            }
        }
    }

    public void modeloTablaCarrito() {
        String[] titulos = {"CÓDIGO", "NOMBRE", "TALLA", "CANTIDAD", "V/U", "V/T"};
        jTable_CarritoCompra.getTableHeader().setReorderingAllowed(false);
        jTable_CarritoCompra.getTableHeader().setResizingAllowed(false);
        modeloTabla = new DefaultTableModel(null, titulos) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable_CarritoCompra.setModel(modeloTabla);
        
    }

    public void establecerTamañoColumnas() {
        modeloColumna = jTable_CarritoCompra.getColumnModel();
        modeloColumna.getColumn(0).setPreferredWidth(60);
        modeloColumna.getColumn(1).setPreferredWidth(190);
        modeloColumna.getColumn(2).setPreferredWidth(80);
        modeloColumna.getColumn(3).setPreferredWidth(90);
        modeloColumna.getColumn(4).setPreferredWidth(60);
        modeloColumna.getColumn(5).setPreferredWidth(60);
        jTable_CarritoCompra.setColumnModel(modeloColumna);

    }

    public void crearCabeceraFactura() {
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        Calendar fecha = Calendar.getInstance();
        String FEC_VEN, CED_CLI_VEN, COD_USU_VEN;
        FEC_VEN = formateador.format(fecha.getTime());
        if (jTextField_CedCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se han ingresado datos del cliente");
            jTextField_CedCli.requestFocus(); // Para posicionar el raton
        } else {
            CED_CLI_VEN = jTextField_CedCli.getText();
            COD_USU_VEN = jTextField_CedEmp.getText();
            String sql = "insert into ventas_cab(FEC_VEN,CED_CLI_VEN,COD_USU_VEN) values(?,?,?)";
            cn = cc.conectar();
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, FEC_VEN);
                psd.setString(2, CED_CLI_VEN);
                psd.setString(3, COD_USU_VEN);

                int affectedRows = psd.executeUpdate();

                try (ResultSet generatedKeys = psd.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        affectedRows = generatedKeys.getInt(1);
                        codCab = affectedRows;
                        System.out.println(codCab);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido obtener id");
                    }
                }
                cn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al insertar datos en la base:\n" + ex);
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    JOptionPane.showMessageDialog(null, "Error de conexión");
                }
            }
        }
    }

    public void guardarCliente() {
        if (jTextField_CedCli.getText().isEmpty() || !Metodos.verificadorCédula(jTextField_CedCli.getText())) {
            JOptionPane.showMessageDialog(null, "Cédula Errónea");
            jTextField_CedCli.requestFocus(); // Para posicionar el raton
        } else if (jTextField_ApeCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el apellido");
            jTextField_ApeCli.requestFocus();
        } else if (jTextField_NomCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
            jTextField_NomCli.requestFocus();
        } else if (jTextField_DirCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la dirección");
            jTextField_DirCli.requestFocus();
        } else {
            cn = cc.conectar();
            String CED_CLI, APE_CLI, NOM_CLI, DIR_CLI, TEL_CLI, EMAIL_CLI;
            CED_CLI = jTextField_CedCli.getText().trim();
            APE_CLI = jTextField_ApeCli.getText().trim().toUpperCase();
            NOM_CLI = jTextField_NomCli.getText().trim().toUpperCase();
            DIR_CLI = jTextField_DirCli.getText().trim().toUpperCase();
            TEL_CLI = jTextField_TelCli.getText().trim();
            EMAIL_CLI = "ingreseemail@ejemplo.com";
            String sql = "";
            sql = "insert into clientes(CED_CLI, APE_CLI, NOM_CLI, DIR_CLI, TEL_CLI, EMAIL_CLI)"
                    + "values(?,?,?,?,?,?)";
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, CED_CLI); //(Numero de campo/ nombre)
                psd.setString(2, APE_CLI);
                psd.setString(3, NOM_CLI);
                psd.setString(4, DIR_CLI);
                psd.setString(5, TEL_CLI);
                psd.setString(6, EMAIL_CLI);
                int n = psd.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se insertó la información correctamente");
                    cargarCliente();
                    jButton_Cargar.setEnabled(false);
                    jButton_Guardar.setEnabled(false);
                    habilitarEdicionCamposCliente(false);
                    cn.close();
                } else {
                    cn.close();
                }

            } catch (SQLException ex) { //permite manejar la excepcion de la base de datos
                JOptionPane.showMessageDialog(null, "Datos duplicados");
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    JOptionPane.showMessageDialog(null, "Error de conexión");
                }
            } catch (Exception ex) {
            }
        }
    }

    public void agregarArticulo() {
        String codigo;
        int cantidad = 0;
        double valorTProd = 0;
        InventarioVendedor sel = new InventarioVendedor(null, true);
        sel.setVisible(true);
        boolean existe = false;
        if (!sel.isShowing() && sel.vale) {
            codigo = sel.enviarCodigo();
            cantidad = sel.obtenerCantidad();
            for (int i = 0; i < jTable_CarritoCompra.getRowCount(); i++) {
                if (codigo.equals(jTable_CarritoCompra.getValueAt(i, 0).toString())) {
                    cantidad += Integer.valueOf(jTable_CarritoCompra.getValueAt(i, 3).toString());
                    valorTProd = cantidad * Double.valueOf(jTable_CarritoCompra.getValueAt(i, 4).toString());
                    jTable_CarritoCompra.setValueAt(cantidad, i, 3);
                    jTable_CarritoCompra.setValueAt(valorTProd, i, 5);
                    existe = true;
                }
            }
            if (!existe) {
                String[] registros = new String[6];
                cn = cc.conectar();
                String sql = "SELECT p.id_pro, p.tip_pro, t.des_tal, p.pre_ven "
                        + "FROM productos p, tallas t "
                        + "WHERE p.id_pro = '" + codigo + "' "
                        + "AND p.id_tal_per= t.id_tal";
                try {
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        registros[0] = rs.getString(1);
                        registros[1] = rs.getString(2);
                        registros[2] = rs.getString(3);
                        registros[3] = String.valueOf(cantidad);
                        registros[4] = rs.getString(4);
                        registros[5] = String.valueOf(Integer.valueOf(registros[3]) * Double.valueOf(registros[4]));
                    }
                    modeloTabla.addRow(registros);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al obtener datos de la base:\n" + ex);
                    try {
                        cn.close();
                    } catch (SQLException ex1) {
                        JOptionPane.showMessageDialog(null, "Error de conexión");
                    }
                }
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

        jPanel5 = new javax.swing.JPanel();
        jLabel_Fecha = new javax.swing.JLabel();
        jPanel_Fondo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_CedCli = new javax.swing.JTextField();
        jTextField_NomCli = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField_ApeCli = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_TelCli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_DirCli = new javax.swing.JTextField();
        jButton_Cargar = new javax.swing.JButton();
        jButton_Guardar = new javax.swing.JButton();
        jToggleButton_ConsumidorFinal = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton_Eliminar = new javax.swing.JButton();
        jButton_Seleccionar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_CarritoCompra = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jButtonFacturar = new javax.swing.JButton();
        jButton_Cancelar = new javax.swing.JButton();
        jTextField_Total = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField_CedEmp = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField_NomEmp = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel5.setAutoscrolls(true);

        jLabel_Fecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_Fecha.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel_Fondo.setBorder(javax.swing.BorderFactory.createTitledBorder("VENTA DE ROPA"));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DEL CLIENTE"));

        jLabel1.setText("Cédula:");

        jTextField_CedCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_CedCliKeyTyped(evt);
            }
        });

        jTextField_NomCli.setEditable(false);
        jTextField_NomCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_NomCliKeyTyped(evt);
            }
        });

        jLabel2.setText("Nombre:");

        jTextField_ApeCli.setEditable(false);
        jTextField_ApeCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_ApeCliKeyTyped(evt);
            }
        });

        jLabel3.setText("Apellido:");

        jLabel4.setText("Teléfono:");

        jTextField_TelCli.setEditable(false);
        jTextField_TelCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_TelCliKeyTyped(evt);
            }
        });

        jLabel5.setText("Dirección:");

        jTextField_DirCli.setEditable(false);
        jTextField_DirCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_DirCliKeyTyped(evt);
            }
        });

        jButton_Cargar.setText("Cargar");
        jButton_Cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CargarActionPerformed(evt);
            }
        });

        jButton_Guardar.setText("Guardar");
        jButton_Guardar.setEnabled(false);
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        jToggleButton_ConsumidorFinal.setText("Consumidor Final");
        jToggleButton_ConsumidorFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_ConsumidorFinalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_CedCli, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_Cargar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField_TelCli, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_ApeCli)
                                    .addComponent(jTextField_NomCli, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton_Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(115, 115, 115))
                                    .addComponent(jTextField_DirCli)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jToggleButton_ConsumidorFinal)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jToggleButton_ConsumidorFinal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_CedCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_TelCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Cargar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_NomCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField_DirCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField_ApeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Guardar))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("ARTÍCULO"));

        jLabel6.setText("Seleccione:");

        jButton_Eliminar.setText("Eliminar");
        jButton_Eliminar.setEnabled(false);
        jButton_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EliminarActionPerformed(evt);
            }
        });

        jButton_Seleccionar.setText("...");
        jButton_Seleccionar.setEnabled(false);
        jButton_Seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SeleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jButton_Seleccionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_Eliminar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jButton_Seleccionar)
                    .addComponent(jButton_Eliminar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("CARRITO DE COMPRA"));

        jTable_CarritoCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Descripción", "Cantidad", "V/U", "V/T"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_CarritoCompra);

        jLabel10.setText("Total de la venta:");

        jButtonFacturar.setText("Facturar");
        jButtonFacturar.setEnabled(false);
        jButtonFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFacturarActionPerformed(evt);
            }
        });

        jButton_Cancelar.setText("Cancelar");
        jButton_Cancelar.setEnabled(false);
        jButton_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelarActionPerformed(evt);
            }
        });

        jTextField_Total.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButtonFacturar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Cancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jButtonFacturar)
                    .addComponent(jButton_Cancelar)
                    .addComponent(jTextField_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS VENDEDOR"));

        jLabel8.setText("Cedula:");

        jTextField_CedEmp.setEditable(false);

        jLabel11.setText("Nombres:");

        jTextField_NomEmp.setEditable(false);
        jTextField_NomEmp.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jTextField_CedEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_NomEmp)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jTextField_CedEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_NomEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_FondoLayout = new javax.swing.GroupLayout(jPanel_Fondo);
        jPanel_Fondo.setLayout(jPanel_FondoLayout);
        jPanel_FondoLayout.setHorizontalGroup(
            jPanel_FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_FondoLayout.setVerticalGroup(
            jPanel_FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_FondoLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel13.setText("Fecha:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel_Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel_Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_CedCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CedCliKeyTyped
        // TODO add your handling code here:
//        deshabilitarCancelar();
        Metodos.validarTelefono(evt, jTextField_CedCli);
    }//GEN-LAST:event_jTextField_CedCliKeyTyped

    private void jTextField_NomCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NomCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarLetras(evt, jTextField_NomCli);
    }//GEN-LAST:event_jTextField_NomCliKeyTyped

    private void jTextField_ApeCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_ApeCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarLetras(evt, jTextField_ApeCli);
    }//GEN-LAST:event_jTextField_ApeCliKeyTyped

    private void jTextField_TelCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_TelCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarTelefono(evt, jTextField_TelCli);
    }//GEN-LAST:event_jTextField_TelCliKeyTyped

    private void jTextField_DirCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_DirCliKeyTyped
        // TODO add your handling code here:
        if (jTextField_DirCli.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_DirCliKeyTyped

    private void jButton_CargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CargarActionPerformed
        cargarCliente();

    }//GEN-LAST:event_jButton_CargarActionPerformed

    private void jButton_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarActionPerformed
//        eliminarDeCarritoDeCompra();
    }//GEN-LAST:event_jButton_EliminarActionPerformed

    private void jButtonFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFacturarActionPerformed
//        int preg = JOptionPane.showConfirmDialog(this, "Realizar Venta?...", "Facturando...", JOptionPane.YES_NO_OPTION);
//        if (preg == 0) {
//            Facturar();
//        }
    }//GEN-LAST:event_jButtonFacturarActionPerformed

    private void jButton_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelarActionPerformed
//        confirmarCancelarVenta();
    }//GEN-LAST:event_jButton_CancelarActionPerformed

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        // TODO add your handling code here:
        guardarCliente();
    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void jToggleButton_ConsumidorFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_ConsumidorFinalActionPerformed
        // TODO add your handling code here:
        if (jToggleButton_ConsumidorFinal.isSelected()) {
            jTextField_CedCli.setText("1111111116");
            cargarCliente();
        } else {
            txtLimpiar();
        }
    }//GEN-LAST:event_jToggleButton_ConsumidorFinalActionPerformed

    private void jButton_SeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SeleccionarActionPerformed
        // TODO add your handling code here:
        agregarArticulo();
    }//GEN-LAST:event_jButton_SeleccionarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Venta dialog = new Venta(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFacturar;
    private javax.swing.JButton jButton_Cancelar;
    private javax.swing.JButton jButton_Cargar;
    private javax.swing.JButton jButton_Eliminar;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JButton jButton_Seleccionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_Fecha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel_Fondo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_CarritoCompra;
    private javax.swing.JTextField jTextField_ApeCli;
    private javax.swing.JTextField jTextField_CedCli;
    private javax.swing.JTextField jTextField_CedEmp;
    private javax.swing.JTextField jTextField_DirCli;
    private javax.swing.JTextField jTextField_NomCli;
    private javax.swing.JTextField jTextField_NomEmp;
    private javax.swing.JTextField jTextField_TelCli;
    private javax.swing.JTextField jTextField_Total;
    private javax.swing.JToggleButton jToggleButton_ConsumidorFinal;
    // End of variables declaration//GEN-END:variables
}
