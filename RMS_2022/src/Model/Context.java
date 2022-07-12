package Model;

import Controller.Database_Controller;
import Model.Drinks;
import Model.Speisen;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class Context {

    public static boolean user = false;
    public static User fakeuser = new User(0, "fake", true);
    public static ArrayList<TotalOrder> totalOrderList = new ArrayList<>();
    public static ObservableList<TotalOrder> totalOrderTabledata= FXCollections.observableArrayList();
    public static User selectUser = fakeuser;
    public static ArrayList<User> userList = new ArrayList<>();
    public static ObservableList<User> userTabledata = FXCollections.observableArrayList();


    // Update vom Gesamtumsatz
    public static void updateTotalOrderList() {
        totalOrderTabledata.clear();
        for (TotalOrder app_2 : totalOrderList ) {
            TotalOrder app_2Tbldata = new TotalOrder(app_2.getKellnerName(), app_2.getUmsatz());
            totalOrderTabledata.add(app_2Tbldata);
        }
    }

    // Update der Userlist
    public static void updateUserlist(){
        userTabledata.clear();
        for (User app : userList){
            User appTbldata = new User(app.getId(), app.getBenutzername(), app.isAktiveUser());
            userTabledata.add(appTbldata);
        }
    }

    // updated alle Bestellungen und zeigt Sie in der Gesamtübersicht an, wird in der Initialize von Admin_Controller aufgerufen um die Tabelle zu füllen
    public static void updateAllOrdersList() throws SQLException {
        totalOrderList.clear();
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();

        String request =  "SELECT Mitarbeiter.ID, sum(Produkt.Preis) as Gesamtumsatz From Orders, Produkt, Mitarbeiter WHERE Produkt.ID = produktID AND Mitarbeiter.ID = mitarbeiterID GROUP BY mitarbeiterID";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(request);


            while (queryResult.next()) {
                TotalOrder app_2 = new TotalOrder(queryResult.getString("Id"),queryResult.getString("Gesamtumsatz"));
                totalOrderList.add(app_2);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Update der User aus der Datenbank
    public static void updateUsers() throws SQLException {
        userList.clear();
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();

        String request = "SELECT * FROM Mitarbeiter";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(request);
            while (queryResult.next()) {
                User app = new User(queryResult.getInt("ID"), queryResult.getString("Benutzername"), queryResult.getBoolean("aktiveUser"));
                userList.add(app);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}