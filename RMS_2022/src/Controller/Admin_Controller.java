package Controller;


import Model.Context;
import Model.TotalOrder;
import Model.User;
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
import Main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static Model.Context.*;

public class Admin_Controller implements Initializable {

    @FXML
    private TextField BedienernameTextfield;
    @FXML
    private Label messageLabel;
    @FXML
    private Label deleteLabel;
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
    private TableView<User> kellnerTabelle;
    @FXML
    private Button logoutButton;


    //Logout vom Admin Fenster und Rückkehr zum Login Fenster
    @FXML
    void Logout(ActionEvent event) throws IOException {
        Stage Tstage = (Stage) logoutButton.getScene().getWindow();
        Tstage.close();
        Stage Login = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 257, 543);
        Login.setTitle("Login");
        Login.setScene(scene);
        Login.initModality(Modality.WINDOW_MODAL);
        Login.show();
    }

    // Neuer Kellner wird im System angelegt
    @FXML
    void RegisterBediener(ActionEvent event) throws SQLException {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        String newName = BedienernameTextfield.getText();

        String insertFields = "INSERT INTO Mitarbeiter(Benutzername, aktiveUser) VALUES ('";
        String insertValues = newName + "','" + "','" + 1 + "' ,'" + "')";
        String insertToRegister = insertFields + insertValues;

        if (!checkIfUserExists()) {
            if (Context.user == true) {
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

    // Überprüft ob der Bediener schon existiert, wenn er existiert wird der Bediener nicht angelegt
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

    // Kellner können nicht gelöscht aber dafür deaktiviert werden, dadurch können sie sich nicht mehr im System anmelden
    @FXML
    void deleteBediener(ActionEvent event) throws SQLException {
        String query = " ";
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        System.out.println(selectUser.getId());
        System.out.println(selectUser.getId());

        query = "UPDATE `Mitarbeiter` SET `aktiveUser` = '0' WHERE `Mitarbeiter`.`ID` = " + selectUser.getId();

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
            deleteLabel.setText("User: " + selectUser.getBenutzername() + " wurde deaktiviert");
            Context.updateUsers();
            Context.updateUserlist();


        } catch (Exception e) {
            e.printStackTrace();
        }
        connectDB.close();
    }


    // Deaktivierte Kellner können mit dieser Funktion wieder aktiviert werden und die Anmeldesperre wird aufgehoben
    @FXML
    void reviveUser(ActionEvent event) throws SQLException {
        String query = " ";
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        System.out.println(selectUser.getId());
        System.out.println(selectUser.getId());
        query = "UPDATE `Mitarbeiter` SET `aktiveUser` = '1' WHERE `Mitarbeiter`.`ID` = " + selectUser.getId();

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
            deleteLabel.setText("User: " + selectUser.getBenutzername() + " wurde aktiviert");
            Context.updateUsers();
            Context.updateUserlist();


        } catch (Exception e) {
            e.printStackTrace();
        }
        connectDB.close();
    }

    // Wenn in die Tabelle geklickt wird sucht diese Funktion den Tabellenindex und danach den richtigen Wert in der Datenbank mit findUserWithID(int ID)
    public int getInt() {
        int selectedIndex = kellnerTabelle.getSelectionModel().getSelectedIndex();
        selectUser = findUserWithId(kellnerTabelle.getItems().get(selectedIndex).getId());
        System.out.println(selectUser.getBenutzername());
        System.out.println(selectUser.getId());
        System.out.println(fakeuser.getBenutzername());

        return selectedIndex;
    }

    // Sucht den richtigen Wert in der Datenbank, Input Value ist der Tabellenindex aus der Funktion public int getInt()
    User findUserWithId(int id) {
        for (User app : userList) {
            if (app.getId() == id) {
                return app;
            }
        }
        return fakeuser;
    }

    // initialisierung der Tabellendaten
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(checkIfUserExists());
        System.out.println("User = " + Context.user);
        nameTblCol.setCellValueFactory(new PropertyValueFactory<>("benutzername"));
        kellnerTblCol.setCellValueFactory(new PropertyValueFactory<>("KellnerName"));
        BedienerNummerTblCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        activeUserTblCol.setCellValueFactory(new PropertyValueFactory<>("aktiveUser"));
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
    void getBedienername(MouseEvent event) {
    }

}