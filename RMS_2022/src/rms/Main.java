package rms;


import Controller.Database_Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Stage Login = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Login.fxml"));
        //   Stage Login = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 257, 543);
        //Stage Login = (Stage) Login_Button.getScene().getWindow();
        Login.setTitle("Login");
        Login.setScene(scene);
        Login.setResizable(false);
        Login.initModality(Modality.WINDOW_MODAL);
        Login.show();


    }





    public static void main(String[] args) {

        launch(args);
      Database_Controller connection = new Database_Controller();
      //Connection connectDB =connection.getConnection();
        System.out.println("Hello");
    }
}

