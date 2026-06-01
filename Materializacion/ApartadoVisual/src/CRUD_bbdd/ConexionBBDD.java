package CRUD_bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBBDD {

    private static final String url = "jdbc:mysql://localhost:3306/desafio_grupo6";
    private static final String user = "root";
    private static final String password = "0000";

    public static Connection conexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Logger.getLogger(ConexionBBDD.class.getName()).log(Level.SEVERE, null, e);
        }
        return con;
    }

}
