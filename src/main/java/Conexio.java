import java.sql.*;
public class Conexio {

public Connection getConexio() {
    Connection con = null;
    try
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // create our mysql database connection
        String myUrl = "jdbc:mysql://192.168.56.1:3306/libreria";
        con = DriverManager.getConnection(myUrl, "toni", "1234");

        System.out.println("CONEXION CORRECTA");

    }
    catch (Exception e)
    {
        System.err.println("EXCEPCION DE CONEXION");
        System.err.println(e.getMessage());
    }
    return con;

}

}
