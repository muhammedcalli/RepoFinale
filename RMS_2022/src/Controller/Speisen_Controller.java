package Controller;
import Model.Speisen;
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

public class Speisen_Controller  {

    @FXML
    private TableView<?> TableView;

    @FXML
    private TableColumn<Speisen_Controller,String> dishIdCol;

    @FXML
    private TableColumn<Speisen_Controller,String> dishNameCol;

    @FXML
    private TableColumn<Speisen_Controller,String> dishPreisCol;

    @FXML
    private TableView<Speisen_Controller> dishTblData;

    ObservableList<Speisen_Controller> speisenList = FXCollections.observableArrayList();

   /* @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();


        String sql = "SELECT id, name, preis FROM Speisen";
        try {
            System.out.println("Test_3");
            Statement statement = connectDB.createStatement();
            ResultSet QueryOutput = statement.executeQuery(sql);
            while (QueryOutput.next()) {

                String speisenID = QueryOutput.getString("ID");
                String speisenName = QueryOutput.getString("Name");
                String speisenPreis = QueryOutput.getString("Preis");
                speisenList.add(new Speisen(speisenID,speisenName,speisenPreis));
            }
            dishIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            dishNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            dishPreisCol.setCellValueFactory(new PropertyValueFactory<>("preis"));
            dishTblData.setItems(speisenList);

        }catch (SQLException e ) {

            Logger.getLogger(Speisen_Controller.class.getName()).log(Level.SEVERE,null,e);
        }

    }/*

    */

    }

