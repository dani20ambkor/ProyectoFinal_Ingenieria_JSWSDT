/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AutosViaje.java
 *
 * Created on 16-oct-2017, 10:42:01
 */
package formularios;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.binary.Base64;
import sun.security.util.Password;

/**
 *
 * @author Invitado_PC16
 */
public class Usuarios extends javax.swing.JInternalFrame {

    /**
     * Creates new form AutosViaje
     */
    DefaultTableModel model;

    public Usuarios() {
        initComponents();
        botonesInicio();
        txtBloqueo(false);
        txtConfiguracion();
        cargarTablaUsuarios("");
        lblConfirmarContraseña.setVisible(false);
        lblContraseñasNoCoinciden.setVisible(false);
        tblClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override //ir cambiando los valores si se selecciona una fila
            public void valueChanged(ListSelectionEvent e) {

                if (tblClientes.getSelectedRow() != -1) {
                    int fila = tblClientes.getSelectedRow();
                    txtCedula.setText(String.valueOf(tblClientes.getValueAt(fila, 0)).trim());
                    txtNombre.setText(String.valueOf(tblClientes.getValueAt(fila, 1)).trim());
                    txtApellido.setText(String.valueOf(tblClientes.getValueAt(fila, 2)).trim());
                    txtCargo.setText(String.valueOf(tblClientes.getValueAt(fila, 3)).trim());
                    pwdContraseña.setText(String.valueOf(tblClientes.getValueAt(fila, 4)).trim());
                    txtBloqueo(true);
                    txtCedula.setEnabled(false);
                    botonesActualizar();
                }
            }
        });

        model.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int columna = e.getColumn();
                    int fila = e.getLastRow();
                    String nombreColumna = null;
                    String valor = tblClientes.getValueAt(fila, columna).toString();
                    if (columna == 1) {
                        nombreColumna = "USU_NOMBRE";
                    } else if (columna == 2) {
                        nombreColumna = "USU_APELLIDO";
                    } else if (columna == 3) {
                        nombreColumna = "USU_PERFIL";
                    } else if (columna == 4) {
                        nombreColumna = "USU_CLAVE";
                        String contraseña = Encriptar(tblClientes.getValueAt(fila, columna).toString());
                        valor = contraseña;
                    }

                    String sql = "update usuarios set " + nombreColumna + "='" + valor + "'where USU_CEDULA='" + tblClientes.getValueAt(fila, 0) + "'";
                    conexionViaje cc = new conexionViaje();
                    Connection cn = cc.conectar();
                    PreparedStatement pst;
                    try {
                        pst = cn.prepareStatement(sql);
                        pst.executeUpdate();;
                    } catch (SQLException ex) {
                    }

                }


            }
        });
    }
    //Controlar con decimales el año
    /*
     * Consultar tipos de statement controlar campos de solo texto o solo
     * numeros
     *
     *
     */

    public void cargarTablaUsuarios(String Dato) {

        String[] titulos = {"CÉDULA", "NOMBRE", "APELLIDO", "CARGO", "CONTRASEÑA"};
        String[] registros = new String[5];
        int estado;
        model = new DefaultTableModel(null, titulos) {

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 4) {
                    return false;
                }
                return true;
            }
        };
        conexionViaje cc = new conexionViaje();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "select * from usuarios where USU_CEDULA like '%" + Dato + "%' order by USU_APELLIDO";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); //Manejar celda por celda el resultado del statement (consulta)
            while (rs.next()) {
                registros[0] = rs.getString("USU_CEDULA");
                registros[1] = rs.getString("USU_NOMBRE");
                registros[2] = rs.getString("USU_APELLIDO");
                registros[3] = rs.getString("USU_PERFIL");
                registros[4] = Desencriptar(rs.getString("USU_CLAVE"));

                model.addRow(registros);

            }
            tblClientes.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (NullPointerException ex1) {
        } catch (Exception ex2) {
        }

    }

    public void txtConfiguracion() {
        txtCedula.tipoDatoYLongitud(10, false);
        txtNombre.tipoDatoYLongitud(25, true);
        txtApellido.tipoDatoYLongitud(25, true);
        txtCargo.tipoDatoYLongitud(10, true);


        //txtContraseña.longitudTexto(4);
    }

    public void txtLimpiar() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        pwdContraseña.setText("");
        pwdContraseñaConf.setText("");
        txtCargo.setText("");
        txtBloqueo(false);

    }

    public void txtBloqueo(boolean tutia) {
        txtCedula.requestFocus();
        txtCedula.setEnabled(tutia);
        txtNombre.setEnabled(tutia);
        txtApellido.setEnabled(tutia);
        txtCargo.setEnabled(tutia);
        pwdContraseñaConf.setEnabled(tutia);
        pwdContraseña.setEnabled(tutia);
        txtApellido.setEnabled(tutia);

    }

    public void botonesNuevo() {
        jButton_Actualizar_Auto.setEnabled(false);
        jButton_Borrar_Auto.setEnabled(false);
        jButton_Cancelar_Auto.setEnabled(true);
        jButton_Guardar_Auto.setEnabled(true);
        jButton_Nuevo_Auto.setEnabled(false);
        jButton_Salir_Auto.setEnabled(true);
    }

    public void botonesInicio() {
        jButton_Actualizar_Auto.setEnabled(false);
        jButton_Borrar_Auto.setEnabled(false);
        jButton_Cancelar_Auto.setEnabled(false);
        jButton_Guardar_Auto.setEnabled(false);
        jButton_Nuevo_Auto.setEnabled(true);
        jButton_Salir_Auto.setEnabled(true);
    }

    public void botonesActualizar() {
        jButton_Actualizar_Auto.setEnabled(true);
        jButton_Borrar_Auto.setEnabled(true);
        jButton_Cancelar_Auto.setEnabled(true);
        jButton_Guardar_Auto.setEnabled(false);
        jButton_Nuevo_Auto.setEnabled(false);
        jButton_Salir_Auto.setEnabled(true);
    }

    public static String Encriptar(String texto) {

        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public static String Desencriptar(String textoEncriptado) throws Exception {

        String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public void guardar() {
        if (txtCedula.getText().isEmpty() && Metodos.verificadorCédula(txtCedula.getText())) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una Cédula (correcta)");
            //Posicionar el foco en el texto
            txtCedula.requestFocus();
        } else if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Nombre");
            txtNombre.requestFocus();
        } else if (txtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Apellido");
            txtApellido.requestFocus();
        } else if (txtCargo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el Cargo");
            txtCargo.requestFocus();
        } else if (pwdContraseña.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la contraseña");
            pwdContraseña.requestFocus();
        } else if (pwdContraseñaConf.getText().isEmpty()) {
            lblConfirmarContraseña.setVisible(true);
            pwdContraseñaConf.requestFocus();
        } else {
            if (contraseñasCoinciden()) {
                conexionViaje cc = new conexionViaje();
                Connection cn = cc.conectar();
                String USU_CEDULA, USU_NOMBRE, USU_APELLIDO, USU_CARGO, USU_CLAVE;
                USU_CEDULA = txtCedula.getText();
                USU_NOMBRE = txtNombre.getText();
                USU_APELLIDO = txtApellido.getText();
                USU_CARGO = txtCargo.getText();
                USU_CLAVE = Encriptar(String.valueOf(pwdContraseña.getPassword()));
                String sql = "";
                sql = "INSERT INTO USUARIOS (USU_CEDULA, USU_NOMBRE, USU_APELLIDO, USU_PERFIL, USU_CLAVE) VALUES (?,?,?,?,?)";
                try {
                    PreparedStatement psd = cn.prepareStatement(sql);
                    psd.setString(1, USU_CEDULA);
                    psd.setString(2, USU_NOMBRE);
                    psd.setString(3, USU_APELLIDO);
                    psd.setString(4, USU_CARGO);
                    psd.setString(5, USU_CLAVE);
                    int n = psd.executeUpdate();
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                        cargarTablaUsuarios("");
                        txtLimpiar();
                        botonesInicio();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un problema con el ingreso " + ex);
                } catch (NullPointerException exp) {
                    //JOptionPane.showMessageDialog(null, "Error: no existe conección a la base"+exp);
                }
            }

        }
    }
    
    public String obtenerContraseña(JPasswordField pwd){
        String contraseña1="";
        char[] cont1 = pwd.getPassword();
        for (int i = 0; i < cont1.length; i++) {
            contraseña1 += cont1[i];
        }
        return contraseña1;
    }

    
    public boolean contraseñasCoinciden() {
        lblConfirmarContraseña.setVisible(false);
        lblContraseñasNoCoinciden.setVisible(false);
        String contraseña1= obtenerContraseña(pwdContraseña);
        String contraseña2 = obtenerContraseña(pwdContraseñaConf);
        if (!contraseña1.equals(contraseña2)) {
            lblContraseñasNoCoinciden.setVisible(true);
            pwdContraseñaConf.requestFocus();
            return false;
        }
        return true;
    }

    public void actualizar() {
        if (contraseñasCoinciden()) {
            conexionViaje cc = new conexionViaje();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "update usuarios set USU_NOMBRE='" + txtNombre.getText() + "' "
                    + ",USU_APELLIDO=' " + txtApellido.getText() + "' "
                    + ",USU_PERFIL=' " + txtCargo.getText() + "' "
                    + ",USU_CLAVE=' " + Encriptar(obtenerContraseña(pwdContraseña)) + "' "
                    + "where USU_CEDULA='" + txtCedula.getText() + "'";
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se actualizó el registro correctamente");
                    cargarTablaUsuarios("");
                    txtLimpiar();
                    botonesInicio();
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public void borrar() {
//        conexionViaje cc = new conexionViaje();
//        Connection cn = cc.conectar();
//        String sql = "";
//        sql = "delete from auto where AUT_PLACA='" + txtPlaca.getText() + "'";
//        sql = "update auto set AUT_ESTADO='" + 0 + "' where AUT_PLACA='" + ucTextLetras12.getText() + "'";;
//        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea borrar?", "Borrar Dato", JOptionPane.YES_NO_OPTION);
//        if (confirm == 0) {
//            try {
//                PreparedStatement psd = cn.prepareStatement(sql);
//                int n = psd.executeUpdate();
//                if (n > 0) {
//                    JOptionPane.showMessageDialog(null, "Se borró el registro correctamente");
//                    cargarTablaAutos("");
//                    txtLimpiar();
//                    botonesInicio();
//                }
//
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(null, ex);
//            }
//        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCargo = new uctextletras.UcTextLetras();
        jLabel7 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        lblConfirmarContraseña = new javax.swing.JLabel();
        txtCedula = new uctextletras.UcTextLetras();
        txtNombre = new uctextletras.UcTextLetras();
        pwdContraseña = new javax.swing.JPasswordField();
        pwdContraseñaConf = new javax.swing.JPasswordField();
        txtApellido = new uctextletras.UcTextLetras();
        lblContraseñasNoCoinciden = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton_Nuevo_Auto = new javax.swing.JButton();
        jButton_Guardar_Auto = new javax.swing.JButton();
        jButton_Actualizar_Auto = new javax.swing.JButton();
        jButton_Cancelar_Auto = new javax.swing.JButton();
        jButton_Borrar_Auto = new javax.swing.JButton();
        jButton_Salir_Auto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);
        setTitle("USUARIOS");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Cédula:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Apellido:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Cargo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Contraseña:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Contraseña:");

        jLabel7.setText("Buscar:");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        lblConfirmarContraseña.setForeground(new java.awt.Color(255, 0, 0));
        lblConfirmarContraseña.setText("Confirme la contraseña por favor.");

        lblContraseñasNoCoinciden.setForeground(new java.awt.Color(255, 0, 0));
        lblContraseñasNoCoinciden.setText("Las contraseñas no coinciden.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pwdContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(56, 56, 56)
                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblConfirmarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(pwdContraseñaConf)))
                            .addComponent(lblContraseñasNoCoinciden, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(pwdContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblConfirmarContraseña)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(pwdContraseñaConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblContraseñasNoCoinciden)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jButton_Nuevo_Auto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jButton_Nuevo_Auto.setText("Nuevo");
        jButton_Nuevo_Auto.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton_Nuevo_Auto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Nuevo_AutoActionPerformed(evt);
            }
        });

        jButton_Guardar_Auto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton_Guardar_Auto.setText("Guardar");
        jButton_Guardar_Auto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Guardar_AutoActionPerformed(evt);
            }
        });

        jButton_Actualizar_Auto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        jButton_Actualizar_Auto.setText("Actualizar");
        jButton_Actualizar_Auto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Actualizar_AutoActionPerformed(evt);
            }
        });

        jButton_Cancelar_Auto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jButton_Cancelar_Auto.setText("Cacelar");
        jButton_Cancelar_Auto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Cancelar_AutoActionPerformed(evt);
            }
        });

        jButton_Borrar_Auto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/borrar.png"))); // NOI18N
        jButton_Borrar_Auto.setText("Borrar");
        jButton_Borrar_Auto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Borrar_AutoActionPerformed(evt);
            }
        });

        jButton_Salir_Auto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        jButton_Salir_Auto.setText("Volver");
        jButton_Salir_Auto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Salir_AutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton_Borrar_Auto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Cancelar_Auto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Nuevo_Auto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Guardar_Auto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Actualizar_Auto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Salir_Auto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_Nuevo_Auto)
                .addGap(18, 18, 18)
                .addComponent(jButton_Guardar_Auto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Actualizar_Auto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Cancelar_Auto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Borrar_Auto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_Salir_Auto)
                .addContainerGap())
        );

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_Guardar_AutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Guardar_AutoActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_jButton_Guardar_AutoActionPerformed

    private void jButton_Nuevo_AutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Nuevo_AutoActionPerformed
        // TODO add your handling code here:
        txtBloqueo(true);
        botonesNuevo();
    }//GEN-LAST:event_jButton_Nuevo_AutoActionPerformed

    private void jButton_Cancelar_AutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Cancelar_AutoActionPerformed
        // TODO add your handling code here:
        txtLimpiar();
        botonesInicio();
    }//GEN-LAST:event_jButton_Cancelar_AutoActionPerformed

    private void jButton_Salir_AutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Salir_AutoActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton_Salir_AutoActionPerformed

    private void jButton_Actualizar_AutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Actualizar_AutoActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_jButton_Actualizar_AutoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        cargarTablaUsuarios(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void jButton_Borrar_AutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Borrar_AutoActionPerformed
        // TODO add your handling code here:
        borrar();
    }//GEN-LAST:event_jButton_Borrar_AutoActionPerformed

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
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Usuarios().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Actualizar_Auto;
    private javax.swing.JButton jButton_Borrar_Auto;
    private javax.swing.JButton jButton_Cancelar_Auto;
    private javax.swing.JButton jButton_Guardar_Auto;
    private javax.swing.JButton jButton_Nuevo_Auto;
    private javax.swing.JButton jButton_Salir_Auto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblConfirmarContraseña;
    private javax.swing.JLabel lblContraseñasNoCoinciden;
    private javax.swing.JPasswordField pwdContraseña;
    private javax.swing.JPasswordField pwdContraseñaConf;
    private javax.swing.JTable tblClientes;
    private uctextletras.UcTextLetras txtApellido;
    private javax.swing.JTextField txtBuscar;
    private uctextletras.UcTextLetras txtCargo;
    private uctextletras.UcTextLetras txtCedula;
    private uctextletras.UcTextLetras txtNombre;
    // End of variables declaration//GEN-END:variables
}
