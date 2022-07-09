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
    public static User currentUser;
    public static boolean user = false;
    public static Drinks currentDrink;
    public static Speisen currentSpeise;
    public static User fakeuser = new User(0, "fake", true);
    public static Drinks fakedrink = new Drinks(0, "fake", "fake");
    public static Speisen fakespeise = new Speisen(0,"fake","fake");
    public static User selectuser= fakeuser;
    public static TotalOrder total = new TotalOrder(" ", " ");
    public static TotalOrder selectOrder = total;
    public static ArrayList<TotalOrder> totalOrderList = new ArrayList<>();
    public static ObservableList<TotalOrder> totalOrderTabledata= FXCollections.observableArrayList();
    public static User selectUser = fakeuser;
    public static ArrayList<User> userList = new ArrayList<>();
    public static ObservableList<User> userTabledata = FXCollections.observableArrayList();

    public static Drinks selectDrink = fakedrink;
    public static ArrayList<Drinks> drinksList = new ArrayList<>();





    //TODO
    public static void UpdateDrink() {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();

        String request = "SELECT * from Speisekarte WHERE id = " + currentDrink.getID();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(request);


            while (queryResult.next()) {
                currentDrink = new Drinks(queryResult.getInt("id"), queryResult.getString("name"), queryResult.getString("preis"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }
    public static void updateTotalOrderList() {
        totalOrderTabledata.clear();
        for (TotalOrder app_2 : totalOrderList ) {
            TotalOrder app_2Tbldata = new TotalOrder(app_2.getKellnerName(), app_2.getUmsatz());
            totalOrderTabledata.add(app_2Tbldata);
        }
    }
    public static void updateUserlist(){
        userTabledata.clear();
        for (User app : userList){
            User appTbldata = new User(app.getId(), app.getBenutzername(), app.isAktiveUser());
            userTabledata.add(appTbldata);
        }
    }
    public static void updateCurrentUser() {

        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();

        String request = "SELECT * from Mitarbeiter WHERE id = " + currentUser.getId();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(request);


            while (queryResult.next()) {
                currentUser = new User(queryResult.getInt("id"), queryResult.getString("Benutzername"), queryResult.getBoolean("aktiveUser"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }
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