/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author USER-11
 */
/**
 *
 * @author JoséDaniel
 */
public class Coneccion {

    private Connection conexion;

//    public static void main(String[] args) {
//        Coneccion obconeccion = new Coneccion();
//        obconeccion.Conectar();
//    }

    public Connection getConexion() {
        return conexion;
    }

    public void getClose() {
        try {
            conexion.close();
        } catch (SQLException ex) {

        }
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Connection establecerDB() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String user = "";
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String password = "";
            conexion = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver no encontrado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Fallo al recibir base de datos");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No hay resultado");
        } finally {
            return conexion;
        }
    }

    public Coneccion Conectar() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:XE";
            conexion = DriverManager.getConnection(BaseDeDatos, "", "");
            if (conexion != null) {
                System.out.println("Conexion exitosa a registro");
            } else {
                System.out.println("Conexion fallida");
            }
        } catch (Exception e) {
            System.out.println("error" + e);
        }

        return this;
    }

}
