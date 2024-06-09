package org.example.luzverde;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/***
 * @Author Milena Petrova
 * @Version 1.0
 * La clase que me sirve para conectar con mi servidor y crear la base de datos
 */
public class DataBaseConnection {
    private static Connection con=null;
    private DataBaseConnection(){}

    /**
     * El constructor vasio ,por la buena practica
     */

    static
    {
        String url ="jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "123456";
        try{
            con= DriverManager.getConnection(url,user,password);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Este metodo en realidad no he llegado de usarlo porque mientras trabajaba no he querido serar la conection
     * @param con
     * @return
     */
    private static boolean isClosed(Connection con) {
        try {
            return con == null || con.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
}




