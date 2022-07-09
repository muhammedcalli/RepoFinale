package Controller;
import Model.Drinks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Drink_Controller {

    @FXML
    private TableView<?> TableView;

    @FXML
    private TableColumn<Drink_Controller,String> idTableCol;

    @FXML
    private TableColumn<Drinks, String> nameTableCol;

    @FXML
    private TableColumn<Drinks, String> preisTableCol;

    @FXML
    private TableView<Drink_Controller> tblData;

    ObservableList<Drink_Controller> drinkList = FXCollections.observableArrayList();


  /*  @Override
    public void initialize(URL url, ResourceBundle resource) {

        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();


        String sql = "SELECT ID, Name, Preis FROM Speisekarte";
        try {
            System.out.println("Test_1");
            Statement statement = connectDB.createStatement();
            ResultSet QueryOutput = statement.executeQuery(sql);
            while (QueryOutput.next()) {

                String drinkID = QueryOutput.getString("ID");
                String drinkName = QueryOutput.getString("Name");
                String drinkPreis = QueryOutput.getString("Preis");
                drinkList.add(new Drinks(drinkID,drinkName,drinkPreis));
            }
            idTableCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            nameTableCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            preisTableCol.setCellValueFactory(new PropertyValueFactory<>("Preis"));
            tblData.setItems(drinkList);

        }catch (SQLException e ) {

            Logger.getLogger(Drink_Controller.class.getName()).log(Level.SEVERE,null,e);
        }
    } /*

   */
 }
