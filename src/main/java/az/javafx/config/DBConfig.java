package az.javafx.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    public static Connection getConnection() {

        try {
            String url = "jdbc:mysql://localhost:3306/studentmanagmentapp03?" + "useUnicode=true"
                    + "&useJDBCCompliantTimezoneShift=true&" + "useLegacyDatetimeCode=false&" + "serverTimezone=UTC";


            String user = "root";
            String password = "laman.";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
