package locadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String URL = "jdbc:mysql://localhost:3306/locadora?useSSL=false&serverTimezone=Brazil/East";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection conectaBanco() throws SQLException
    {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());	
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
