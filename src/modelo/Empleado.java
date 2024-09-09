package modelo;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keila
 */
public class Empleado {
    Conexion cn;
    
    private String codigo;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String fechaNacimiento;
    private int idPuesto;

    // Constructor por defecto
    public Empleado() {}

    // Constructor con todos los atributos
    public Empleado(String codigo, String nombres, String apellidos, String direccion, String telefono, String fechaNacimiento, int idPuesto) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.idPuesto = idPuesto;
    }

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public int getIdPuesto() {
        return idPuesto;
    }
    
    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }
    
   //  @Override
   public void agregar(){
     try{
         PreparedStatement parametro;
         cn = new Conexion();
         cn.abrir_conexion();
         String query;
            query = "insert into empleados(codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento, id_puesto) "+
                 "values(?,?,?,?,?,?,?);";
         parametro  = (PreparedStatement) cn.conexionBD.prepareStatement(query);
         parametro.setString(1, getCodigo());
         parametro.setString(2, getNombres());
         parametro.setString(3, getApellidos());
         parametro.setString(4, getDireccion());
         parametro.setString(5, getTelefono());
         parametro.setString(6, getFechaNacimiento());
         parametro.setString(7, String.valueOf(getIdPuesto()));
         
         int executar= parametro.executeUpdate();
         cn.cerrar_conexion();
        JOptionPane.showMessageDialog(null,Integer.toString(executar) + " Registro Ingresado",
             "Mensaje",JOptionPane.INFORMATION_MESSAGE);
     }catch(HeadlessException | SQLException ex){
         System.out.println("Error"+ex.getMessage());
     }
   }
   
   public DefaultTableModel leer(){
  DefaultTableModel tabla = new DefaultTableModel();
  try{
   cn = new Conexion();
   cn.abrir_conexion();
    String query;
    query = "Select id_empleado as id, codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento, puesto from empleados emp inner join puesos p on emp.id_puesto = p.id_puesto;";
     ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
      
      String encabezado[] = {"id","Codigo","Nombres","Apellidos","Direccion","Telefono","Nacimiento", "Puesto"};
      tabla.setColumnIdentifiers(encabezado);
      
      String datos[]=new String[7];
      
   while(consulta.next()){
      datos[0] = consulta.getString("id");
      datos[1] = consulta.getString("codigo");
      datos[2] = consulta.getString("nombres");
      datos[3] = consulta.getString("apellidos");
      datos[4] = consulta.getString("direccion");
      datos[5] = consulta.getString("telefono");
      datos[6] = consulta.getString("fecha_nacimiento");
      datos[7] = consulta.getString("puesto");
      tabla.addRow(datos);
      }
   cn.cerrar_conexion();
    
      
  }catch(SQLException ex){
      cn.cerrar_conexion();
      System.out.println("Error: " + ex.getMessage() );
  
  }
  return tabla;
  }
   
   public void actualizar(){
         try{
         PreparedStatement parametro;
         cn = new Conexion();
         cn.abrir_conexion();
         String query;
        query = "update empleados nombres= ?,apellidos= ?,direccion= ?,telefono= ?,fecha_nacimiento= ?,id_puesto= ? "+
                 "where codigo = ?";
         parametro  = (PreparedStatement) cn.conexionBD.prepareStatement(query);
         parametro.setString(1, getNombres());
         parametro.setString(2, getApellidos());
         parametro.setString(3, getDireccion());
         parametro.setString(4, getTelefono());
         parametro.setString(5, getFechaNacimiento());
         parametro.setInt(6, getIdPuesto());
         parametro.setString(7, getCodigo());
         
         int executar= parametro.executeUpdate();
         cn.cerrar_conexion();
        JOptionPane.showMessageDialog(null,Integer.toString(executar) + " Registro Actualizado",
             "Mensaje",JOptionPane.INFORMATION_MESSAGE);
     }catch(HeadlessException | SQLException ex){
         System.out.println("Error"+ex.getMessage());
     }
   
   }

   public void eliminar(){
   
    try{
         PreparedStatement parametro;
         cn = new Conexion();
         cn.abrir_conexion();
         String query;
        query = "delete from empleados where codigo = ?";
                 
         parametro  = (PreparedStatement) cn.conexionBD.prepareStatement(query);
         parametro.setString(1, getCodigo());
         
         int executar= parametro.executeUpdate();
         cn.cerrar_conexion();
        JOptionPane.showMessageDialog(null,Integer.toString(executar) + " Registro Eliminado",
             "Mensaje",JOptionPane.INFORMATION_MESSAGE);
     }catch(HeadlessException | SQLException ex){
         System.out.println("Error"+ex.getMessage());
     }
   
   }
}
