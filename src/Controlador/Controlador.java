/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Modelo.Administrator;
import BD.Conexion;
import Modelo.*;
import Vista.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author User
 */
public class Controlador implements ActionListener{
    
    Usuario usuario = new Usuario();
    Contrato contrato = new Contrato();
    Login login = new Login();
    MenuPrincipal menuPrincipal = new MenuPrincipal();
    AnadirUsuario anadirUsuario = new AnadirUsuario();
    DefaultTableModel modelo = new DefaultTableModel();
    
    public Controlador(Login login) {
        this.login =login;
    }
    
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    java.util.Date date = cal.getTime();
    public String fechaHora = dateFormat.format(date);
    
    public static boolean agregarUsuario(String id, String contrasena, String is_superuser, String email, String is_staff, String is_active,String date_joined, String rol  ) {
        try{
            Connection conectar = Conexion.getConnection();
            String query="INSERT INTO dbo.feriavirtualapp_user(id,password,is_superuser,email,is_staff,is_active,date_joined,rol,imagen)VALUE(?,?,?,?,?,?,?)";
            PreparedStatement insertar = conectar.prepareStatement(query);
            insertar.setString(1,id);
            insertar.setString(2,contrasena);
            insertar.setString(3,is_superuser);
            insertar.setString(4,email);
            insertar.setString(5,is_staff);
            insertar.setString(6,is_active);
            insertar.setString(7,date_joined);
            insertar.setString(8,rol);
           
            insertar.execute();
            insertar.close();
            conectar.close();
            JOptionPane.showMessageDialog(null, "Usuario agregado correctamente.");
            return true;
        
        }
        catch(Exception b){
            JOptionPane.showMessageDialog(null, "Usuario no fue agregado");
            System.out.println(b);
            return false;
        
        }
       
  
    }
    public String convertirSHA256(String password) {
	MessageDigest md = null;
	try {
		md = MessageDigest.getInstance("SHA-256");
	} 
	catch (NoSuchAlgorithmException e) {		
		e.printStackTrace();
		return null;
	}
	    
	byte[] hash = md.digest(password.getBytes());
	StringBuffer sb = new StringBuffer();
	    
	for(byte b : hash) {        
		sb.append(String.format("%02x", b));
	}
	    
	return sb.toString();
    }
    
    public String iniciarSesion (String username, String password){
        Administrator administrator = new Administrator();
        administrator.usrAdmin = username;
        administrator.pass = password;
                
        if(username.equals("admin")&& password.equals("ff960cb55673958c594d0daaab1e368651c75c02f9687192a1811e7b180336a7")){
            Login login = new Login();
            login.setVisible(false);
            
            JOptionPane.showMessageDialog(null,"Bienvenido usuario");
            MenuPrincipal menuprincipal = new MenuPrincipal();
            menuprincipal.setVisible(true);
        }
        else if(username.equals("admin")){
            
            JOptionPane.showMessageDialog(null,"Contraseña incorrecta");    
        }
        else {
            JOptionPane.showMessageDialog(null,"Usuario y contraseña incorrectos!");
        }            
         
        
        return username;
    }
   
    /*
    public String iniciarSesion (String username, String password){
        String sql = "SELECT username, password, is_active from dbo.feriavirtualapp_user where username='"+username+
        "'AND password='"+password+"';";          
        try{   
            Conexion conn = new Conexion();
            Connection con = conn.getConnection();
            Statement st = con.createStatement();
            String isActive ="";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                isActive=rs.getString("0");
            }        
            if(isActive.equals("1")){
                Login login = new Login();
                login.setVisible(false);
                System.out.println(rs);
                JOptionPane.showMessageDialog(null,"Bienvenido usuario");
                MenuPrincipal menuprincipal = new MenuPrincipal();
                menuprincipal.setVisible(true);
            }
            else if(isActive.equals("0")){
                System.out.println(rs);
                JOptionPane.showMessageDialog(null,"Usuario bloqueado por incumplimiento de terminos & condiciones");    
            }
            else {
                JOptionPane.showMessageDialog(null,"Usuario y contraseña incorrectos!");
            }            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);   
        }
        return username;
    }
    */
    
    /*
     public static void eliminarUsuario(){
        
        DefaultTableModel modelo = new DefaultTableModel();
        PreparedStatement ps = null;
        
        int selFila ;
        
        try {
            
            selFila =jTable.getSelectedRow();
            if (selFila ==-1){
                JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario", "Validación", JOptionPane.WARNING_MESSAGE);
            }
            else{
                Conexion objCon = new Conexion();
                Connection conn =objCon.getConnection();
                int Fila = jTable.getSelectedRow();
                String codigo =jTable.getValueAt(Fila, 0).toString();
                ps = conn.prepareStatement("DELETE FROM dbo.feriavirtualapp_user WHERE username=?");
                ps.setString(1,codigo);
                ps.execute();
                JOptionPane.showMessageDialog(null, "Película eliminada correctamente");
                modelo.removeRow(Fila);
            }  
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila", "Validación", JOptionPane.WARNING_MESSAGE);

        }
     }
     
     public static void buscarUsuario(){
        String campo = jBuscar.getText();
        String where = "";
        if(!"".equals(campo)){
            where = "WHERE codigo ='"+ campo +"'";  
        }
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            jTable.setModel(modelo);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConnection();
            
            String sql ="SELECT username FROM dbo.feriavirtualapp_user "
                    + where;
            System.out.println(sql);
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            ResultSetMetaData rsMd =rs. getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            modelo.addColumn("Código");
            modelo.addColumn("Titulo");
            modelo.addColumn("Formato");
            modelo.addColumn("Duración");
            modelo.addColumn("Categoria");
            modelo.addColumn("Director");
            modelo.addColumn("Estreno");
            while(rs.next()){
                Object[]filas =new Object[cantidadColumnas];
                for (int i = 0; i<cantidadColumnas ; i++){
                    filas[i]=rs.getObject(i+1);
                }
                modelo.addRow(filas);
            }  
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    
    public static boolean agregarContrato(String id, String contrasena, String is_superuser, String email, String is_staff, String is_active,String date_joined, String rol  ) {
        try{
            Connection conectar = Conexion.getConnection();
            String query="INSERT INTO dbo.feriavirtualapp_user(id,password,is_superuser,email,is_staff,is_active,date_joined,rol,imagen)VALUE(?,?,?,?,?,?,?)";
            PreparedStatement insertar = conectar.prepareStatement(query);
            insertar.setString(1,id);
            insertar.setString(2,contrasena);
            insertar.setString(3,is_superuser);
            insertar.setString(4,email);
            insertar.setString(5,is_staff);
            insertar.setString(6,is_active);
            insertar.setString(7,date_joined);
            insertar.setString(8,rol);
           
            insertar.execute();
            insertar.close();
            conectar.close();
            JOptionPane.showMessageDialog(null, "Usuario agregado correctamente.");
            return true;
        
        }
        catch(Exception b){
            JOptionPane.showMessageDialog(null, "Usuario no fue agregado");
            System.out.println(b);
            return false;
        
        }
    
*/

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

   
    
}
