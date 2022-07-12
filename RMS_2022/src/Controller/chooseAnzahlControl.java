package Controller;
import Model.PassData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class chooseAnzahlControl {

    @FXML
    private Button logButton;

    @FXML
    private TextField textfield;
    @FXML
    private Button goBackButton;


    // Eingabe löschen
    @FXML
    void Delete(ActionEvent event) {
        textfield.setText("");
    }

    // Eingabe im textfield anzeigen
    @FXML
    void Get_Numbers(ActionEvent event) {
        String s = ((Button) event.getSource()).getText();
        textfield.setText(textfield.getText() + s);

    }
    // Fenster schließen
    @FXML
    void goBack(ActionEvent event)  {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }

    // Logt die Anzahl
    @FXML
    void LogAnzahl(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        if(PassData.getselectedBox() == 1) {
            PassData.setAnzhel(Integer.parseInt(textfield.getText()));
        }
        else if(PassData.getselectedBox() == 2) {
            PassData.setcustomer(Integer.parseInt(textfield.getText()));
        }
        else if(PassData.getselectedBox() == 3) {
            PassData.setmerge(Integer.parseInt(textfield.getText()));
        }
        else if(PassData.getselectedBox() == 4) {
            PassData.setChangeTable(Integer.parseInt(textfield.getText()));
        }
        else if(PassData.getselectedBox() == 5) {
            PassData.setCook(Integer.parseInt(textfield.getText()));
        }
        Stage stage = (Stage) logButton.getScene().getWindow();
        stage.close();
    }

}
