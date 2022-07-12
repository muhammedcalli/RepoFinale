package Controller;

import Main.Main;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class
Login_Controller {
    @FXML
    private TextField textfield;
    @FXML
    private Button login_button;
    @FXML
    private javafx.scene.control.Label messageLabel;


    // Logt den User ein wenn die Bedienernummer im System registriert ist
    @FXML
    void Login_Button(ActionEvent event) throws Exception {
        System.out.println(this.textfield.getText());
        Connection connectDB = Database_Controller.getConnection();
        String verifyLogin = "SELECT * FROM bmiervx5yaquiotrakw1.Mitarbeiter WHERE ID = '" + this.textfield.getText() + "' ";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);


            while (queryResult.next()) {
                User currentUser = new User(queryResult.getInt("id"), queryResult.getString("Benutzername"), queryResult.getBoolean("aktiveUser"));
                if (currentUser.isAktiveUser() == true) {
                    this.logIn(currentUser);
                }
                messageLabel.setText("Eingabe falsch oder Anmeldung Blockiert!");
            }
            connectDB.close();
        } catch (Exception var8) {
            var8.printStackTrace();
            var8.getCause();
        }

    }

    // mit Nummer 21 öffnet sich der Adminbereich mit allen anderen registrierten Nummern öffnet sich der Tischplan
    public void logIn(User currentUser) throws IOException {
        System.out.println(currentUser.getId());

        if (currentUser.getId() == 21) {
            Stage stage = (Stage) login_button.getScene().getWindow();
            stage.close();

            Stage adminStage = new Stage();
            User.currentUser = currentUser;

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/AdminPanel.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            adminStage.setTitle("Adminbereich");
            adminStage.setScene(scene);
            adminStage.setResizable(false);
            adminStage.show();
        } else

            try {
                Stage stage = (Stage) login_button.getScene().getWindow();
                stage.close();

                Stage Tstage = new Stage();
                User.currentUser = currentUser;

                System.out.println(currentUser.getBenutzername());
                System.out.println("Test3");
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Tische.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Tstage.setTitle("Restaurant-Management-System");
                Tstage.setScene(scene);
                Tstage.setResizable(false);
                Tstage.show();

            } catch (Exception var3) {
                var3.printStackTrace();
            }

    }

    // Erhält die eingegebenen Zahlen
    @FXML
    void Get_Numbers(ActionEvent numbers) throws Exception {

        String ID = ((Button) numbers.getSource()).getText();
        textfield.setText(textfield.getText() + ID);

    }

    // Löscht die Eingabe
    @FXML
    void Delete(ActionEvent event) throws Exception {
        textfield.setText("");
    }

    // Öffnet das Fenster für die Küchenübersicht
    @FXML
    void onCookButton(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) login_button.getScene().getWindow();
        stage1.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/cookmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Bestellungen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

}
