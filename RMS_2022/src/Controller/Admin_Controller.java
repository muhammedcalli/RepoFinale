package Controller;


import Model.Context;
import Model.TotalOrder;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rms.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Model.Context.*;

public class Admin_Controller implements Initializable {

    @FXML
    private TextField BedienernameTextfield;
    @FXML
    private Label messageLabel;
    @FXML
    private Label deleteLabel;
    @FXML
    private Label totalLabel;
    @FXML
    public TableColumn<User, String> nameTblCol;
    @FXML
    private TableView<TotalOrder> umsatzTableView;
    @FXML
    private TableColumn<TotalOrder, String> kellnerTblCol;
    @FXML
    private TableColumn<User, String> activeUserTblCol;
    @FXML
    private TableColumn<User, String> BedienerNummerTblCol;
    @FXML
    private TableColumn<TotalOrder, String> umsatzTblCol;
    @FXML
    private TableColumn<TotalOrder, String> idTblCol;
    @FXML
    private TableView<User> kellnerTabelle;
    @FXML
    private Button logoutButton;



    @FXML
    void Logout(ActionEvent event) throws IOException {
        Stage Tstage = (Stage) logoutButton.getScene().getWindow();
        Tstage.close();
        Stage Login = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 323, 494);
        Login.setTitle("Login");
        Login.setScene(scene);
        Login.initModality(Modality.WINDOW_MODAL);
        Login.show();
    }

    @FXML
    void RegisterBediener(ActionEvent event) throws SQLException {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        String newName = BedienernameTextfield.getText( );

        String insertFields = "INSERT INTO Mitarbeiter(Benutzername, aktiveUser) VALUES ('";
        String insertValues = newName + "','" + "','" + 1 + "' ,'" + "')";
        String insertToRegister= insertFields + insertValues;

        //if (!checkIfUserExists() && !checkIfBedienernummerExists()) {
        if (!checkIfUserExists()){
            if( Context.user == true) {
                insertToRegister = "INSERT INTO Mitarbeiter(Benutzername, aktiveUser) VALUES('" + newName + "'  ,'" + 1 + "')";
            }
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertToRegister);
                messageLabel.setText("New Account is successfully created");
                Context.updateUsers();
                Context.updateUserlist();
                kellnerTabelle.setItems(userTabledata);
                connectDB.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Database_Controller.closeConnection();
    }
    public boolean checkIfUserExists() {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        String name = BedienernameTextfield.getText();
        String verifyLogin = "SELECT count(1) from Mitarbeiter WHERE Benutzername = '" + name + "' ";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {

                if (queryResult.getInt(1) == 1) {
                    messageLabel.setText("Username existiert bereits");

                    return true;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        try {
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Context.user = true;
        return false;

    }
    /*public boolean checkIfBedienernummerExists() {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        String nummer = BedienernummerTextfield.getText();
        String verifyLogin = "SELECT count(1) from Mitarbeiter WHERE Bedienernummer = '" + nummer + "' ";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {

                if (queryResult.getInt(1) == 1) {
                    messageLabel.setText("Bedienernummer existiert bereits");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        Context.user = true;
        return false;
    }

     */

    @FXML
    void deleteBediener(ActionEvent event) throws SQLException {
        String query = " ";
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        System.out.println( selectUser.getId());
        System.out.println(selectUser.getId());

        //query = "DELETE FROM bwlana3jrca5fzranlwf.Mitarbeiter WHERE id = " + selectUser.getId();
        query = "UPDATE `Mitarbeiter` SET `aktiveUser` = '0' WHERE `Mitarbeiter`.`ID` = " + selectUser.getId();

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
            deleteLabel.setText("User: " + selectUser.getBenutzername()+" wurde deaktiviert");
            Context.updateUsers();
            Context.updateUserlist();
            //  kellnerTabelle.setItems(userTabledata);


        } catch (Exception e) {
            e.printStackTrace();
        }
        connectDB.close();
    }
    @FXML
    void reviveUser(ActionEvent event) throws SQLException{
        String query = " ";
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        System.out.println( selectUser.getId());
        System.out.println(selectUser.getId());
        query = "UPDATE `Mitarbeiter` SET `aktiveUser` = '1' WHERE `Mitarbeiter`.`ID` = " + selectUser.getId();

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
            deleteLabel.setText("User: " + selectUser.getBenutzername()+" wurde aktiviert");
            Context.updateUsers();
            Context.updateUserlist();



        } catch (Exception e) {
            e.printStackTrace();
        }
        connectDB.close();
    }
    public int getInt() {
        int selectedIndex = kellnerTabelle.getSelectionModel().getSelectedIndex();
        selectUser = findUserWithId(kellnerTabelle.getItems().get(selectedIndex).getId());
        System.out.println(selectUser.getBenutzername());
        System.out.println(selectUser.getId());
        System.out.println(fakeuser.getBenutzername());

        return selectedIndex;
    }
    User findUserWithId(int id) {
        for (User app : userList) {
            if(app.getId() == id) {
                return app;
            }
        }
        return fakeuser;
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(checkIfUserExists());
        System.out.println("User = " + Context.user);
        nameTblCol.setCellValueFactory(new PropertyValueFactory<>("benutzername"));
        kellnerTblCol.setCellValueFactory(new PropertyValueFactory<>("KellnerName"));
        BedienerNummerTblCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        activeUserTblCol.setCellValueFactory(new PropertyValueFactory<>("aktiveUser"));
        // idTblCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        umsatzTblCol.setCellValueFactory(new PropertyValueFactory<>("Umsatz"));
        try {
            Context.updateAllOrdersList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Context.updateTotalOrderList();
        umsatzTableView.setItems(totalOrderTabledata);
        try {
            Context.updateUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Context.updateUserlist();
        kellnerTabelle.setItems(userTabledata);

    }
    @FXML
    void getBedienername(MouseEvent event) {}
    @FXML
    void getBedienernummer(MouseEvent event) {}


    public double getSum() {
        double sum = 0.0;
        return sum;
    }

}