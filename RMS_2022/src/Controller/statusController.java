package Controller;

import Model.TableData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class statusController {

    @FXML private Button goBackButton;
    TableData td = new TableData();

    // schließt das Fenster
    @FXML
    void goBack (ActionEvent event) {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }
    // Setzt die Farbe des ausgewählten Tisches auf grün
    @FXML
    void onGreen(ActionEvent event) {
        int tableno = TableData.getSelectedTable();
        TableData.setTableStatus(tableno,1);
    }

    // Setzt die Farbe des ausgewählten Tisches auf gelb
    @FXML
    void onYello(ActionEvent event) {
        int tableno = TableData.getSelectedTable();
        TableData.setTableStatus(tableno,2);

    }
    // Setzt die Farbe des ausgewählten Tisches auf rot
    @FXML
    void onRed(ActionEvent event) {
        int tableno = TableData.getSelectedTable();
        TableData.setTableStatus(tableno,3);

    }



}
