/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class Clientes extends javax.swing.JDialog {

    public Clientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        establecerModeloTabla();
        //cargarDatosClientes();
        botonesInicio();
        txtBloqueo(false);
    }
    DefaultTableModel modeloTabla = new DefaultTableModel() {
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    Coneccion cn = new Coneccion();
    PreparedStatement pst = null;
    Statement st = null;
    ResultSet rs = null;
    
    public void txtLimpiar() {
        jTextField_CedCli.setText("");
        jTextField_ApeCli.setText("");
        jTextField_NomCli.setText("");
        jTextField_DirCli.setText("");
        jTextField_TelCli.setText("");
        jTextField_Buscar.setText("");
        txtBloqueo(false);
        
    }
    
    public void txtBloqueo(boolean tutia) {
        jTextField_CedCli.requestFocus();
        jTextField_CedCli.setEnabled(tutia);
        jTextField_NomCli.setEnabled(tutia);
        jTextField_ApeCli.setEnabled(tutia);
        jTextField_DirCli.setEnabled(tutia);
        jTextField_TelCli.setEnabled(tutia);
        //jTextField_Buscar.setEnabled(tutia);
        
    }
    
    public void botonesNuevo() {
        jButton_Actualizar.setEnabled(false);
        jButton_Cancelar.setEnabled(true);
        jButton_Guardar.setEnabled(true);
        jButton_Nuevo.setEnabled(false);
    }
    
    public void botonesInicio() {
        jButton_Actualizar.setEnabled(false);
        jButton_Cancelar.setEnabled(false);
        jButton_Guardar.setEnabled(false);
        jButton_Nuevo.setEnabled(true);
    }
    
    

    /**
     * Creates new form Clientes
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_DatosClientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_CedCli = new javax.swing.JTextField();
        jTextField_NomCli = new javax.swing.JTextField();
        jTextField_ApeCli = new javax.swing.JTextField();
        jTextField_DirCli = new javax.swing.JTextField();
        jTextField_TelCli = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Buscar = new javax.swing.JTextField();
        jButton_Actualizar = new javax.swing.JButton();
        jButton_Nuevo = new javax.swing.JButton();
        jButton_Guardar = new javax.swing.JButton();
        jButton_Cancelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes"));

        jTable_DatosClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cédula", "Nombre", "Apellido", "Dirección", "Teléfono", "Ciudad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_DatosClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_DatosClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_DatosClientes);

        jLabel1.setText("Cédula:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Apellido:");

        jLabel4.setText("Dirección:");

        jLabel5.setText("Teléfono:");

        jTextField_CedCli.setEnabled(false);

        jTextField_NomCli.setEnabled(false);
        jTextField_NomCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_NomCliKeyTyped(evt);
            }
        });

        jTextField_ApeCli.setEnabled(false);
        jTextField_ApeCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_ApeCliKeyTyped(evt);
            }
        });

        jTextField_DirCli.setEnabled(false);
        jTextField_DirCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_DirCliKeyTyped(evt);
            }
        });

        jTextField_TelCli.setEnabled(false);
        jTextField_TelCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_TelCliKeyTyped(evt);
            }
        });

        jLabel6.setText("Buscar:");

        jTextField_Buscar.setEnabled(false);
        jTextField_Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_BuscarKeyReleased(evt);
            }
        });

        jButton_Actualizar.setText("Modificar");

        jButton_Nuevo.setText("Nuevo");
        jButton_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NuevoActionPerformed(evt);
            }
        });

        jButton_Guardar.setText("Guardar");
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        jButton_Cancelar.setText("Cancelar");
        jButton_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelarActionPerformed(evt);
            }
        });

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_CedCli, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_NomCli, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(jTextField_ApeCli)
                            .addComponent(jTextField_DirCli)
                            .addComponent(jTextField_Buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(jTextField_TelCli))
                        .addGap(212, 212, 212)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton_Actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_CedCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField_NomCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_ApeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_DirCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField_TelCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_Nuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_Guardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_Actualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_Cancelar)
                        .addGap(8, 8, 8)
                        .addComponent(jButton1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void establecerModeloTabla() {
        jTable_DatosClientes.setModel(modeloTabla);
        modeloTabla.addColumn("Cédula");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Teléfono");
    }
    
    public void actualizarClientes() {
        limpiarTabla();
        cargarDatosClientes();
    }
    
    private void limpiarTabla() {
        for (int i = jTable_DatosClientes.getRowCount() - 1; i >= 0; i--) {
            modeloTabla.removeRow(i);
        }
    }
    
    public void cargarDatosClientes() {
        Vector<Cliente> clientes = DatosClientes();
        Object cli[] = new Object[5];
        boolean existe = false;
        for (int i = 0; i < clientes.size(); i++) {
            cli[0] = clientes.get(i).getCedula();
            cli[1] = clientes.get(i).getNombre();
            cli[2] = clientes.get(i).getApellido();
            cli[3] = clientes.get(i).getDireccion();
            cli[4] = clientes.get(i).getTelefono();
            for (int j = 0; j < jTable_DatosClientes.getRowCount(); j++) {
                if (cli[0].toString().equals(jTable_DatosClientes.getValueAt(j, 0))) {
                    existe = true;
                    break;
                } else {
                    existe = false;
                }
            }
            
            if (!existe) {
                modeloTabla.addRow(cli);
            }
            
        }
    }
    
    public Vector<Cliente> DatosClientes() {
        Vector<Cliente> clientes = new Vector<Cliente>();
        Cliente cl;
        try {
            
            cn.Conectar();
            st = cn.getConexion().createStatement();
            String sql = "select * from clientes";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                cl = new Cliente();
                cl.setCedula(rs.getString(1));
                cl.setNombre(rs.getString(2));
                cl.setApellido(rs.getString(3));
                cl.setDireccion(rs.getString(4));
                cl.setTelefono(rs.getString(5));
                clientes.add(cl);
            }
            st.close();
            rs.close();
            cn.getConexion().close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error : " + ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.NullPointerException ex1) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error : " + ex1.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return clientes;
    }
    
    private void actualizarUnDato() throws HeadlessException {
        // TODO add your handling code here:

        if (jTextField_ApeCli.getText().equals("")
                || jTextField_NomCli.getText().equals("")
                || jTextField_DirCli.getText().equals("")
                || jTextField_TelCli.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campos sin llenar", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            Object[] datos = {jTextField_CedCli.getText(), jTextField_NomCli.getText(), jTextField_ApeCli.getText(),
                jTextField_DirCli.getText(), jTextField_TelCli.getText()};
            try {
                cn.Conectar();
                String sql = "UPDATE CLIENTES SET "
                        + "NOM_CLI = '" + datos[1].toString() + "' , "
                        + "APE_CLI = '" + datos[2].toString() + "' , "
                        + "DIR_CLI = '" + datos[3].toString() + "' , "
                        + "TEL_CLI = '" + datos[4].toString() + "' "
                        + "WHERE CED_CLI = '" + datos[0].toString() + "'";
                pst = cn.getConexion().prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Datos actualizados correctamente!...");
                limpiarCampos();
                cargarDatosClientes();
                deshabilitarComponentes();
                actualizarClientes();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar " + ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void habilitarComponentes() {
        jTextField_ApeCli.setEnabled(true);
        jTextField_NomCli.setEnabled(true);
        jTextField_DirCli.setEnabled(true);
        jTextField_TelCli.setEnabled(true);
    }
    
    public void deshabilitarComponentes() {
        jTextField_ApeCli.setEnabled(false);
        jTextField_NomCli.setEnabled(false);
        jTextField_DirCli.setEnabled(false);
        jTextField_TelCli.setEnabled(false);
    }
    
    public void limpiarCampos() {
        jTextField_CedCli.setText("");
        jTextField_ApeCli.setText("");
        jTextField_NomCli.setText("");
        jTextField_DirCli.setText("");
        jTextField_TelCli.setText("");
    }
    
    private void mostrarValores() {
        // TODO add your handling code here:
        if (jTable_DatosClientes.getSelectedRow() != -1) {
            habilitarComponentes();
            int fila = jTable_DatosClientes.getSelectedRow();
            jTextField_CedCli.setText(jTable_DatosClientes.getValueAt(fila, 0).toString());
            jTextField_NomCli.setText(jTable_DatosClientes.getValueAt(fila, 1).toString());
            jTextField_ApeCli.setText(jTable_DatosClientes.getValueAt(fila, 2).toString());
            jTextField_DirCli.setText(jTable_DatosClientes.getValueAt(fila, 3).toString());
            jTextField_TelCli.setText(jTable_DatosClientes.getValueAt(fila, 4).toString());
        } else {
            deshabilitarComponentes();
        }
    }
    
    public void buscarDato() {
        limpiarTabla();
        try {
            cn.Conectar();
            String consulta = "SELECT * FROM CLIENTES WHERE APE_CLI LIKE " + "'" + jTextField_Buscar.getText() + "_%'";
            st = cn.getConexion().prepareStatement(consulta);
            rs = st.executeQuery(consulta);
            String[] fila = new String[5];
            while (rs.next()) {
                fila[0] = rs.getString("CED_CLI");
                fila[1] = rs.getString("NOM_CLI");
                fila[2] = rs.getString("APE_CLI");
                fila[3] = rs.getString("DIR_CLI");
                fila[4] = rs.getString("TEL_CLI");
                modeloTabla.addRow(fila);
            }
            
            rs.close();
            st.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error : " + ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void jTable_DatosClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_DatosClientesMouseClicked
        mostrarValores();
    }//GEN-LAST:event_jTable_DatosClientesMouseClicked
    
    private void jTextField_NomCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NomCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarLetras(evt, jTextField_NomCli);
    }//GEN-LAST:event_jTextField_NomCliKeyTyped
    
    private void jTextField_ApeCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_ApeCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarLetras(evt, jTextField_ApeCli);
    }//GEN-LAST:event_jTextField_ApeCliKeyTyped
    
    private void jTextField_DirCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_DirCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarLetras(evt, jTextField_DirCli);
    }//GEN-LAST:event_jTextField_DirCliKeyTyped
    
    private void jTextField_TelCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_TelCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarTelefono(evt, jTextField_TelCli);
    }//GEN-LAST:event_jTextField_TelCliKeyTyped
    
    private void jTextField_BuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_BuscarKeyReleased
        // TODO add your handling code here:
        buscarDato();
    }//GEN-LAST:event_jTextField_BuscarKeyReleased
    
    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void jButton_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NuevoActionPerformed
        // TODO add your handling code here:
        botonesNuevo();
        txtBloqueo(true);
    }//GEN-LAST:event_jButton_NuevoActionPerformed

    private void jButton_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelarActionPerformed
        // TODO add your handling code here:
        txtLimpiar();
        txtBloqueo(false);
        botonesInicio();
    }//GEN-LAST:event_jButton_CancelarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                Clientes dialog = new Clientes(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Actualizar;
    private javax.swing.JButton jButton_Cancelar;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JButton jButton_Nuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_DatosClientes;
    private javax.swing.JTextField jTextField_ApeCli;
    private javax.swing.JTextField jTextField_Buscar;
    private javax.swing.JTextField jTextField_CedCli;
    private javax.swing.JTextField jTextField_DirCli;
    private javax.swing.JTextField jTextField_NomCli;
    private javax.swing.JTextField jTextField_TelCli;
    // End of variables declaration//GEN-END:variables
}
