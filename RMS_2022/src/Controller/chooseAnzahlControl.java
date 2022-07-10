package Controller;
import Model.PassData;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class chooseAnzahlControl {

    @FXML
    private Button b1;

    @FXML
    private Button logButton;

    @FXML
    private TextField textfield;


    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void Get_Numbers(ActionEvent event) {
        String s = ((Button) event.getSource()).getText();
        textfield.setText(textfield.getText() + s);

    }

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

    }

}
