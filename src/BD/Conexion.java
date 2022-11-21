/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;
import Controlador.Controlador;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Conexion {
    public static Connection getConnection(){
            Connection connection = null;
            String usuario = "sa";
            String contrasena = "0000";
            String db = "sqlite3";
            String ip = "localhost";
            String puerto = "1433";
            try {
                String cadena = "jdbc:sqlserver://localhost:"+puerto+";"+"databaseName="+db;
                connection = DriverManager.getConnection(cadena, usuario, contrasena);
                JOptionPane.showMessageDialog(null, "Conexion exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.toString());
            
        }
            return connection;
    }
    
}
