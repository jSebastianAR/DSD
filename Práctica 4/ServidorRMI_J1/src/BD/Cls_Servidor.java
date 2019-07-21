 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jSeba
 */
public class Cls_Servidor {
    private Conectar Conexion;
    private final String SQL_INSERT_partida = "INSERT INTO partida (fecha,hora_inicio) values(?,?)";//cuando empieza la partida
    private String SQL_UPDATE_partida;//cuando empieza la partida
    private final String SQL_INSERT_peticion = "INSERT INTO peticion (ID_CARTA,ip_jugador,valor_carta,hora_peticion,no_servidor) values(?,?,?,?,?)";//cuando empieza la partida
    private PreparedStatement PS;
    private DefaultTableModel DT;
    private ResultSet RS;
    
    public Cls_Servidor()
    {
        PS = null;
        Conexion = new Conectar();
    }
    
    public String insertPartida(String fecha,String hora_inicio) throws SQLException
    {
        int res = 0;
        String query = "INSERT INTO partida (fecha,hora_inicio) values('"+fecha+"','"+hora_inicio+"')";
        try
        {
            PS = Conexion.getConnection().prepareStatement(SQL_INSERT_partida);
            PS.setString(1, fecha);
            PS.setString(2, hora_inicio);
            res = PS.executeUpdate();//devuelve el número de lineas afectadas por el insert
            
            if(res > 0)
            {
                //JOptionPane.showMessageDialog(null,"Registro guardado...");
                System.out.println("Se guardo el registro insert Partida");
            }
        }catch(SQLException e)
        {
            System.err.println("Error al guardar los datos en la bd(insertPartida): "+ e.getMessage());
        }finally//siempre pasa por esta parte haya errores o no
        {
            PS = null; 
            Conexion.desconectar();//Se cierra la conexion
        }
        
        return query;
    }
    
    public String updatePartida(String hora_fin,String hora_inicio, String fecha) throws SQLException //update para agregar la fecha final
    {
        //SQL_INSERT_partida = "UPDATE partida SET hora_fin="+hora_fin+" WHERE hora_inicio="+hora_inicio+" AND"+" fecha="+fecha;
        SQL_UPDATE_partida = "UPDATE partida SET hora_fin='"+hora_fin+"' WHERE hora_inicio='"+hora_inicio+"' AND"+" fecha='"+fecha+"'";
        int res = 0;
        try
        {
            PS = Conexion.getConnection().prepareStatement(SQL_UPDATE_partida);
            res = PS.executeUpdate();//devuelve el número de lineas afectadas por el insert
            
            if(res > 0)
            {
                //JOptionPane.showMessageDialog(null,"Registro guardado...");
                System.out.println("Se guardo la hora_fin en update Partida");
            }
        }catch(SQLException e)
        {
            System.err.println("Error al guardar los datos en la bd(updatePartida): "+ e.getMessage());
        }finally//siempre pasa por esta parte haya errores o no
        {
            PS = null; 
            Conexion.desconectar();//Se cierra la conexion
        }
        
        return SQL_UPDATE_partida;
    }
    
 
    
    public String insertPeticion(String idCarta,String ipJugador,String valorCarta,String horaPeticion,int no_servidor) throws SQLException
    {
        int res = 0;
        String query = "INSERT INTO peticion (ID_CARTA,ip_jugador,valor_carta,hora_peticion,no_servidor) values('"+idCarta+"','"+ipJugador+"','"+valorCarta+"','"+horaPeticion+"',"+no_servidor+")";
        try
        {    
            System.out.println("idCarta: "+idCarta+" ipJugador: "+ipJugador+" valorCarta: "+valorCarta+" horaPeticion: "+horaPeticion);
            PS = Conexion.getConnection().prepareStatement(SQL_INSERT_peticion);
            PS.setString(1,idCarta);
            PS.setString(2,ipJugador);
            PS.setString(3,valorCarta);
            PS.setString(4,horaPeticion);
            PS.setInt(5, no_servidor);
            res = PS.executeUpdate();//devuelve el número de lineas afectadas por el insert
            
            if(res>0)
            {
                //System.out.println("Se guardaron los valores:"+RS.getString("ID_CARTA")+" "+RS.getString("ip_jugador")+" "+RS.getString("valor_carta")+" en insert peticion");
                System.err.println("Exito al guardar los datos en la bd(insertPeticion)");
            }
        }catch(SQLException e)
        {
            System.err.println("Error al guardar los datos en la bd(insertPeticion): "+ e.getMessage());
        }finally//siempre pasa por esta parte haya errores o no
        {
            PS = null; 
            Conexion.desconectar();//Se cierra la conexion
            
        }
        return query;
    }

    public int getIDcarta(String carta) throws SQLException
    {
        int valor = 0;
        try
        {
            
            PS = Conexion.getConnection().prepareStatement("SELECT ID FROM carta WHERE imagen='"+carta+"'");
            RS = PS.executeQuery();
            Object[] fila = new Object[100];
            while(RS.next())//recorre todos los datos obtenidos
            {
                fila[0] = RS.getInt(1);//obtiene el nombre del producto
                valor = (int) fila[0];
                System.out.println("ID en getIDcarta: "+fila[0]);
                DT.addRow(fila);//agrega todo el arreglo con los datos de un registro
            }
            
        }catch(SQLException e)
        {
            System.out.println("Error al insertar os datos :" + e.getMessage());
        }finally
        {
            PS= null;
            RS = null;
            Conexion.desconectar();
        }
        return valor;
    }
    
    public void consultaRemota(String consulta) throws SQLException
    {
        try{
            PS = Conexion.getConnection().prepareStatement(consulta);
            int res = PS.executeUpdate();
            if(res>0)
            {
                
                System.out.println("Consulta Remota efectuada en Servidor 1");
            }
            
        }catch(SQLException e)
        {
            System.out.println("Fallo haciendo la consulta remota en server 1: "+e);
        }
        
    }
}
