package Controller;

import Model.PassData;
import Model.TableData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rms.Main;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CookMenuConroller implements Initializable {
    TableData td = new TableData();

    @FXML
    private TextArea cookOrderT;

    @FXML
    private Button goback;

    String foodtocook[] = new String[30];
    int foodcount = 1;



    @FXML
    void onGoBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) goback.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 257, 543);
        stage.setTitle("Login");
        stage.setScene(scene);
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        cookOrderT.appendText(String.format("%3s%25s\n","OrderNo","Bestellungen"));
//        for(int i = 1 ; i < 10 ; i++){
//           ArrayList<String> s = TableData.getTableFood(i);
//
//           if(s.size() != 0 ){
//               for (int j = 0; j < s.size(); j++) {
//                   foodtocook[foodcount] = s.get(j);
//                   String foodName = s.get(j).split("-")[0];
//                   Double Pries = Double.parseDouble(s.get(j).split("-")[1]);
//                   int anzahl = Integer.parseInt(s.get(j).split("-")[2]);
////                 String custNo = s.get(i).split("-")[3];
//                   cookOrderT.appendText(String.format("%5d%24s%30s\n",foodcount,anzahl,foodName));
//                   foodcount++;
//               }
//           }
//        }
        try{
            Database_Controller connection = new Database_Controller();
            Connection connectDB = connection.getConnection();
//         PreparedStatement psCount = connectDB.prepareStatement("SELECT COUNT (Produkt.Name) from Produkt INNER JOIN Orders where Orders.produktID = Produkt.ID");
//         ResultSet rsCount = psCount.executeQuery();
//         rsCount.next();
//         int count = rsCount.getInt(1);
            for (int i = 1 ; i <= 9 ; i++){
                PreparedStatement s = connectDB.prepareStatement("SELECT Produkt.Name from Produkt INNER JOIN Orders where Orders.produktID = Produkt.ID and regNr = ?");
                s.setInt(1,TableData.getOrderNo(i));
                ResultSet rs = s.executeQuery();
                while(rs.next()){
                    foodtocook[foodcount] = rs.getString(1);
                    String foodName = rs.getString(1);
                    cookOrderT.appendText(String.format("%5d%30s\n",foodcount++,foodName));
                }
            }
            connectDB.close();

        }
        catch (SQLException E){

        }
    }
    @FXML
    void fifthfield(MouseEvent event) throws IOException {
        PassData.setselectedBox(5); // for customer
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
        Stage Anzahl = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 250, 500);
        Anzahl.setTitle("Anzahl");
        Anzahl.setScene(scene);
        Anzahl.initModality(Modality.WINDOW_MODAL);
        Anzahl.initOwner(deleteTextF.getScene().getWindow());
        Anzahl.showAndWait();
        deleteTextF.setText(String.valueOf(PassData.getCook()));
    }

    @FXML
    private TextField deleteTextF;


    @FXML
    void deleteItem(ActionEvent event) {
        int ind = Integer.valueOf(deleteTextF.getText());
        if(!foodtocook[ind].isEmpty())
            foodtocook[ind] = "";
        System.out.println("Food on Index:" + ind + "Deleted");
        updateView();
    }

    void updateView(){
        String foodName ="";
        cookOrderT.setText(String.format("%3s%25s\n","OrderNo","Bestellungen"));
        for (int i = 1 ; i < foodtocook.length ; i++){
            if( !(foodtocook[i] == null)&&  !foodtocook[i].isEmpty() ){
                foodName = foodtocook[i];
                cookOrderT.appendText(String.format("%5d%30s\n",i,foodName));

            }
        }

    }
}