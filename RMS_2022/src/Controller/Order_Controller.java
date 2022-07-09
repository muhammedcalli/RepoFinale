package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Order_Controller implements Initializable {
    // TODO in Context Klasse packen


    @FXML
    private javafx.scene.control.Button Dishes;
    @FXML
    private Button Drinks;
    @FXML
    private Button Snacks;
    @FXML private Button Back;
    @FXML
    void getInt(MouseEvent event) {
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
      //  this.Dishes.getScene().getWindow().getOnCloseRequest();
   }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //TODO DB Connection wieder schließen

}
