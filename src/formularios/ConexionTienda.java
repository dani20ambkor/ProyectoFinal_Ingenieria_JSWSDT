/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class ConexionTienda {

    Connection connect = null;

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("oracle.jdbc.OracleDriver");
            /*String user = "";
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String password = "";

            connect = DriverManager.getConnection(url, user, password);*/
            connect = DriverManager.getConnection("jdbc:mysql://localhost/tienda","root","");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver no encontrado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Fallo al recibir base de datos");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No hay resultado");
        } finally {
            return connect;
        }
    }
}