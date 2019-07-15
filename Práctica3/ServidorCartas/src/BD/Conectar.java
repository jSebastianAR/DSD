
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectar {
    private static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver"; //Se hace referencia al driver .jar importado en bibliotecas, para hacer la coneccion
    private static final String user = "root";//esta variable contiene el nombre de usuario para ingresar a mysql
    private static final String password = "root";//esta variable contiene el password de usuario para ingresar a mysql
    private static final String url = "jdbc:mysql://localhost:3306/juegocartas";//contiene el host al que se va a conectar y el número de puerto, además del nombre de la bd
    
    public Conectar() { //método para realizar la conexión
        conn = null;
    }
    //este método nos retorna la conexion
    public Connection getConnection()
    {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password); //se hace la conexion
            
        }catch (ClassNotFoundException | SQLException e)//si hay algún error
            {
                System.out.println("error al conectar" + e);
            }
    
        return conn;
    }
    
    //nos desconecta de la bd
    public void desconectar() throws SQLException
    {
        conn.close();
        conn = null;
        if(conn == null)
        {
        //    System.out.println("Conexion terminada..");
        }
    }
}

