package Controller;
import Model.TableData;
import rms.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.User.currentUser;

public class Controller extends Login_Controller implements Initializable {
    TableData td = new TableData();

    @FXML
    private javafx.scene.control.Button Button;
    @FXML
    private TextField AktuellerKellner;
    @FXML
    private Label kellnerLabel;
    String label = "Test";
    @FXML
    private Button Desk_2;

    @FXML
    private Button Desk_3;

    @FXML
    private javafx.scene.control.Button Desk_4;

    @FXML
    private Button Desk_5;

    @FXML
    private Button Desk_6;

    @FXML
    private Button Desk_7;

    @FXML
    private Button Desk_8;

    @FXML
    private Button Desk_9;



    @FXML
    private javafx.scene.control.Button Login_Button;

    @FXML
    void Order_Button(ActionEvent event) throws Exception{
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Profile.fxml"));
//        Stage Order = new Stage();
//        Scene scene = new Scene(fxmlLoader.load(), 550, 500);
//        Order.setTitle("Bestellungen");
//        Order.setScene(scene);
//        Order.initModality(Modality.WINDOW_MODAL);
//        Order.initOwner(Login_Button.getScene().getWindow());
//        Order.show();
    }


    @FXML
    void signOut(ActionEvent event) throws IOException {
        Stage stage = (Stage) Desk_2.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 257 , 543);
        stage.setTitle("Login");
        stage.setScene(scene);


    }

    // Öffnet Einzeltischansicht wenn doppelt auf einen TischButton geklickt wird
    @FXML
    void open(MouseEvent MouseEvent) throws IOException {
        if(MouseEvent.getButton()== MouseButton.PRIMARY) {
            String buttonNo =((Button)MouseEvent.getSource()).getText();
//                System.out.println(buttonNo);
            TableData.setSelectedTable(Integer.parseInt(buttonNo));
            if(MouseEvent.getClickCount() == 2){

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Einzeltisch.fxml"));
                Stage Order = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 844, 603);
                Order.setTitle("Tisch");
                Order.setScene(scene);
                Order.setResizable(false);
                Order.initModality(Modality.WINDOW_MODAL);
//                Order.initOwner(Desk_2.getScene().getWindow());
                Order.showAndWait();
                setTableStatus(MouseEvent);


            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kellnerLabel.setText(currentUser.getBenutzername());
    }

    public void setTableStatus(MouseEvent me){
        Button b = ((Button)me.getSource());
        String status = TableData.getTableStatus(Integer.parseInt(b.getText()));
        String styleS = "-fx-background-color: " + status + ";";
        b.setStyle(styleS);



    }
}
