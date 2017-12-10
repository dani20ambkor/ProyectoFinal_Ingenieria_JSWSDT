/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import javax.swing.*;

/**
 *
 * @author PC
 */
public class FramePrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FramePrincipal
     */
    public static String cedUsuario;
    public FramePrincipal(String cedUsu) {
        initComponents();
        cedUsuario = cedUsu;
        PonerImagenFondo();
        this.setLocationRelativeTo(null);
    }

    
    private void PonerImagenFondo() {
        
        ((JPanel) getContentPane()).setOpaque(false);        
        ImageIcon uno = new ImageIcon(this.getClass().getResource("/imagenes/menu/fondoMenu.png"));
        JLabel fondo = new JLabel();
        fondo.setIcon(uno);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, uno.getIconWidth(), uno.getIconHeight());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Administrador = new javax.swing.JButton();
        btn_Clientes = new javax.swing.JButton();
        btn_Ventas = new javax.swing.JButton();
        btn_Inventario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        btn_Administrador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Administrador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/administrador.png"))); // NOI18N
        btn_Administrador.setText("Administrador");
        btn_Administrador.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Administrador.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Administrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AdministradorActionPerformed(evt);
            }
        });

        btn_Clientes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/clientes.png"))); // NOI18N
        btn_Clientes.setText("Clientes");
        btn_Clientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Clientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClientesActionPerformed(evt);
            }
        });

        btn_Ventas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/venta.png"))); // NOI18N
        btn_Ventas.setText("Ventas");
        btn_Ventas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Ventas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btn_Inventario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/stock.png"))); // NOI18N
        btn_Inventario.setText("Inventario");
        btn_Inventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Inventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_InventarioActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/LogoR.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_Administrador, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btn_Inventario, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_Inventario, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btn_Ventas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Administrador, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Clientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClientesActionPerformed
        // TODO add your handling code here:
        new Clientes(null,true).setVisible(true);
    }//GEN-LAST:event_btn_ClientesActionPerformed

    private void btn_AdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AdministradorActionPerformed
        // TODO add your handling code here:
        new Usuarios1(null, true,cedUsuario).setVisible(true);
    }//GEN-LAST:event_btn_AdministradorActionPerformed

    private void btn_InventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_InventarioActionPerformed
        // TODO add your handling code here:
        new Inventario(null, true).setVisible(true);
    }//GEN-LAST:event_btn_InventarioActionPerformed

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
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FramePrincipal("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_Administrador;
    public javax.swing.JButton btn_Clientes;
    public javax.swing.JButton btn_Inventario;
    public javax.swing.JButton btn_Ventas;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
