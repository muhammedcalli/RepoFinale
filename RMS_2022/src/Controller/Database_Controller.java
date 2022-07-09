package Controller;

import javafx.scene.control.TextField;

import java.sql.*;

public class Database_Controller {
    public static Connection Databaselink;


   public static Connection getConnection() {
        String databaseName = "bmiervx5yaquiotrakw1";
        String databaseUser = "umsopp7qhukqhxt4";
        String databasePassword = "mWYgmQk6vNUO50rsGLQy";
        String url = "jdbc:mysql://umsopp7qhukqhxt4:mWYgmQk6vNUO50rsGLQy@bmiervx5yaquiotrakw1-mysql.services.clever-cloud.com:3306/" +  databaseName;
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Databaselink = DriverManager.getConnection(url, databaseUser, databasePassword);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        System.out.println("Connected");
        return Databaselink;

    }
    public static void closeConnection() throws SQLException {
       try {
           if(Databaselink != null && !Databaselink.isClosed()){
               Databaselink.close();
               System.out.println("Connection Closed");
           }
       }
       catch (Exception e) {
           throw e;
       }
    }
}
