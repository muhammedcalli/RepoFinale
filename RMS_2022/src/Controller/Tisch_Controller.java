package Controller;

import Model.*;
import javafx.scene.text.Text;
import rms.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Model.User.currentUser;


public class Tisch_Controller implements Initializable {
    String anzahlcount = "0";
    PassData pd = new PassData();
    TableData td = new TableData();
    @FXML
    private Button editButton;
    @FXML
    private TableView<Drinks> TabelDate;
    @FXML
    private TableView<Speisen> DishTable;

    @FXML
    private TableView<Order> allOrdersTableList;


    @FXML
    private TextField anzahl;
    @FXML
    private Button goBackButton;


    @FXML
    private TableColumn<Drinks, String> nameTableCol;
    @FXML
    private TableColumn<Drinks, String> preisTableCol;

    @FXML
    private TableColumn<Speisen, String> nameDishTableCol;
    @FXML
    private TableColumn<Speisen, String> preisDishTableCol;
    @FXML
    private TableColumn<Order, String> PriesOrderCol;
    @FXML
    private TableColumn<Order, String> anzahlOrderCol;
    @FXML
    private TableColumn<Order, String> nameOrderCol;


    @FXML
    private TableColumn<Order, String> custOrderCol;
    @FXML
    private TextField mergeT;
    @FXML
    private Text totalBillText;

    @FXML
    private TextField changeTableT;
    @FXML
    private TextField custNoTextF;


    @FXML
    private Button logButton;
    @FXML
    private TextField textfield;
    ObservableList<Drinks> drinkList = FXCollections.observableArrayList();
    ObservableList<Speisen> speisenList = FXCollections.observableArrayList();
    ObservableList<Order> orderList = FXCollections.observableArrayList();
    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void edit(ActionEvent event)throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Status.fxml"));
        Stage servus = new Stage();
        Scene hallo = new Scene(fxmlLoader.load(), 550, 500);
        servus.setTitle("Bestellungen");
        servus.setScene(hallo);
        servus.initModality(Modality.WINDOW_MODAL);
        servus.initOwner(editButton.getScene().getWindow());
        servus.show();



    }
    @FXML
    void addItemsToTable(ActionEvent event) throws SQLException {
        Speisen sp = DishTable.getSelectionModel().getSelectedItem();
        Drinks d = TabelDate.getSelectionModel().getSelectedItem();
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();

       if(!(sp == null)) {
           for( int i = 0 ; i < Integer.parseInt(anzahl.getText()); i++ ) {
               System.out.println(sp.getName() + sp.getPreis());
               orderList.add(new Order(sp.getName(), Double.parseDouble(sp.getPreis()), 1, "1", 1, sp.getId()));
               String foodS = sp.getName() + "-" + converttoDouble(sp.getPreis()) + "-" + 1 + "-" + 1 + "-" + 1;
               TableData.addTableFood(TableData.getSelectedTable(), foodS);
               PreparedStatement ps = connectDB.prepareStatement("INSERT INTO `Orders` (`id`, `produktID`, `tisch`,`custID`, `mitarbeiterID`, `date`, `regNr`) VALUES (NULL,?, ?, ?, ?, ?,?)");
               ps.setInt(1, sp.getId());
               ps.setInt(2, TableData.getSelectedTable());
               ps.setInt(3, Integer.parseInt(custNoTextF.getText()));
               ps.setInt(4, currentUser.getId());
               ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
               ps.setInt(6, TableData.getOrderNo(TableData.getSelectedTable()));
               ps.execute();
           }


       }
       if(!(d == null)) {
           for( int i = 0 ; i < Integer.parseInt(anzahl.getText()); i++ ) {
               System.out.println(d.getName() + d.getPreis());
               orderList.add(new Order(d.getName(), Double.parseDouble(d.getPreis()), 1, "1", 0, d.getID()));
               String drinkS = d.getName() + "-" + converttoDouble(d.getPreis()) + "-" + 1 + "-" + 1 + "-" + 0;
               TableData.addTableFood(TableData.getSelectedTable(), drinkS);
               PreparedStatement ps = connectDB.prepareStatement("INSERT INTO `Orders` (`id`, `produktID`, `tisch`,`custID`, `mitarbeiterID`, `date`, `regNr`) VALUES (NULL,?, ?, ?, ?, ?,?)");
               ps.setInt(1, d.getID());
               ps.setInt(2, TableData.getSelectedTable());
               ps.setInt(3, Integer.parseInt(custNoTextF.getText()));
               ps.setInt(4, currentUser.getId());
               ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
               ps.setInt(6, TableData.getOrderNo(TableData.getSelectedTable()));
               ps.execute();
           }
           System.out.println(d.getID());
       }
       allOrdersTableList.setItems(orderList);
       allOrdersTableList.refresh();

        DishTable.getSelectionModel().clearSelection();
        TabelDate.getSelectionModel().clearSelection();

    }
    @FXML
    void chooseAnzahl(MouseEvent event) throws IOException {
        PassData.setselectedBox(1); // for anzahl
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
        Stage Anzahl = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 250, 500);
        Anzahl.setTitle("Anzahl");
        Anzahl.setScene(scene);
        Anzahl.initModality(Modality.WINDOW_MODAL);
        Anzahl.initOwner(anzahl.getScene().getWindow());
        Anzahl.showAndWait();
        anzahl.setText(String.valueOf(PassData.getAnzhel()));


    }
    @FXML
    void secondfield(MouseEvent event) throws IOException {
        PassData.setselectedBox(2); // for customer
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
        Stage Anzahl = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 250, 500);
        Anzahl.setTitle("Costumer");
        Anzahl.setScene(scene);
        Anzahl.initModality(Modality.WINDOW_MODAL);
        Anzahl.initOwner(custNoTextF.getScene().getWindow());
        Anzahl.showAndWait();
        custNoTextF.setText(String.valueOf(PassData.getcustomer()));
    }
    @FXML
    void thirdfield(MouseEvent event) throws IOException {
        PassData.setselectedBox(3); // for customer
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
        Stage Anzahl = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 250, 500);
        Anzahl.setTitle("Anzahl");
        Anzahl.setScene(scene);
        Anzahl.initModality(Modality.WINDOW_MODAL);
        Anzahl.initOwner(mergeT.getScene().getWindow());
        Anzahl.showAndWait();
        mergeT.setText(String.valueOf(PassData.getmerge()));
    }
    @FXML
    void fourthfield(MouseEvent event) throws IOException {
        PassData.setselectedBox(4); // for customer
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
        Stage Anzahl = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 250, 500);
        Anzahl.setTitle("Anzahl");
        Anzahl.setScene(scene);
        Anzahl.initModality(Modality.WINDOW_MODAL);
        Anzahl.initOwner(changeTableT.getScene().getWindow());
        Anzahl.showAndWait();
        changeTableT.setText(String.valueOf(PassData.getChangeTable()));
    }




//     PassData.setselectedBox(2); // for customer
//    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
//    Stage Anzahl = new Stage();
//    Scene scene = new Scene(fxmlLoader.load(), 250, 500);
//        Anzahl.setTitle("Anzahl");
//        Anzahl.setScene(scene);
//        Anzahl.initModality(Modality.WINDOW_MODAL);
//        Anzahl.initOwner(anzahl.getScene().getWindow());
//        Anzahl.showAndWait();
//        customerNoT.setText(String.valueOf(PassData.getcustomer()));

    //     PassData.setselectedBox(3); // for customer
//    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
//    Stage Anzahl = new Stage();
//    Scene scene = new Scene(fxmlLoader.load(), 250, 500);
//        Anzahl.setTitle("Anzahl");
//        Anzahl.setScene(scene);
//        Anzahl.initModality(Modality.WINDOW_MODAL);
//        Anzahl.initOwner(anzahl.getScene().getWindow());
//        Anzahl.showAndWait();
//        customerNoT.setText(String.valueOf(PassData.getmerge()));




    @FXML
    void deleteOrder(ActionEvent event) throws SQLException {
        Order order = allOrdersTableList.getSelectionModel().getSelectedItem();
        String foodS = order.getName() + "-" + order.getPries() + "-" + order.getAnzahl() + "-" + order.getCustomer() + "-" +order.getType();
//        System.out.println(foodS);
//        TableData.deleteTableFood(TableData.getSelectedTable(), foodS);
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps = connectDB.prepareStatement("DELETE FROM `Orders` WHERE produktID = ? and regNr = ? and custID = ?");
        ps.setInt(1,order.getId());
        ps.setInt(2,TableData.getOrderNo(TableData.getSelectedTable()));
        ps.setInt(3,Integer.parseInt(order.getCustomer()));
        System.out.println(order.getId() + " - " + TableData.getOrderNo(TableData.getSelectedTable()) + " - " +order.getCustomer() );
        ps.executeUpdate();
        allOrdersTableList.getItems().clear();
        allOrdersTableList.refresh();
        // Rrefershing the view by getting tablefood and populating the tablev
        int regNr = TableData.getOrderNo(TableData.getSelectedTable());
        boolean isNotNull = false;
        PreparedStatement popPS = connectDB.prepareStatement("SELECT Produkt.Name,Produkt.Preis,custID, Produkt.Type,Produkt.ID FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ?");;
        popPS.setInt(1,regNr);
        ResultSet rs = popPS.executeQuery();
        while (rs.next()){
            String foodName = rs.getString(1);
            Double Pries = rs.getDouble(2);
            int anzahl = 1;
            String cno= rs.getString(3);
            int type = rs.getInt(4);
            int pID = rs.getInt(5);
            orderList.add(new Order(foodName,Pries,anzahl,cno,type,pID));
            isNotNull =true;
        }
        if(isNotNull) {
            allOrdersTableList.setItems(orderList);
            allOrdersTableList.refresh();
        }


        /*
        ArrayList<String> s = TableData.getTableFood(TableData.getSelectedTable());
        System.out.println(orderList.size());

        if(s.size() != 0) {
            for (int i = 0; i < s.size(); i++) {
                String foodName = s.get(i).split("-")[0];
                Double Pries = Double.parseDouble(s.get(i).split("-")[1]);
                int anzahl = Integer.parseInt(s.get(i).split("-")[2]);
                String cno= s.get(i).split("-")[3];
                int type = Integer.valueOf(s.get(i).split("-")[4]);
                orderList.add(new Order(foodName, Pries, anzahl,cno,type,order.getId()));
            }
        }
        allOrdersTableList.setItems(orderList);
        allOrdersTableList.refresh();
        */

    connectDB.close();
    }


    @FXML
    void LogAnzahl(ActionEvent event) {
        Stage stage = (Stage) logButton.getScene().getWindow();
        stage.close();
    }
    public void getDishInt(){
        int selectedDishindex = DishTable.getSelectionModel().getSelectedIndex();
        System.out.println(selectedDishindex);
    }

    //TODO überarbeiten return wert ist vlt falsch
   public int getInt() {
        int selectedIndex = TabelDate.getSelectionModel().getSelectedIndex();
        return selectedIndex;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custNoTextF.setText("1");
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();


        String sql = "SELECT * FROM `Produkt` WHERE Type = 0";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet QueryOutput = statement.executeQuery(sql);
            while (QueryOutput.next()) {

                int drinkID = QueryOutput.getInt("id");
                String drinkName = QueryOutput.getString("name");
                String drinkPreis = QueryOutput.getString("preis");
                drinkList.add(new Drinks(drinkID, drinkName, drinkPreis));


                nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                preisTableCol.setCellValueFactory(new PropertyValueFactory<>("preis"));
                TabelDate.setItems(drinkList);

            }

        } catch (SQLException e) {

            Logger.getLogger(Tisch_Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        String sql_2 = "SELECT * FROM `Produkt` WHERE Type = 1";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet QueryOutput = statement.executeQuery(sql_2);
            while (QueryOutput.next()) {

                int speisenID = QueryOutput.getInt("id");
                String speisenName = QueryOutput.getString("Name");
                String speisenPreis = QueryOutput.getString("Preis");
                speisenList.add(new Speisen(speisenID,speisenName, speisenPreis));

                nameDishTableCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
                preisDishTableCol.setCellValueFactory(new PropertyValueFactory<>("Preis"));
                DishTable.setItems(speisenList);
            }
            anzahl.setText(String.valueOf(PassData.getAnzhel()));
            anzahlOrderCol.setCellValueFactory(new PropertyValueFactory<>("Anzahl"));
            nameOrderCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            PriesOrderCol.setCellValueFactory(new PropertyValueFactory<>("Pries"));
            custOrderCol.setCellValueFactory(new PropertyValueFactory<>("customer"));

//            System.out.println(TableData.getSelectedTable());
//            ArrayList<String> s = TableData.getTableFood(TableData.getSelectedTable());
//            System.out.println(orderList.size());
            int regNr = TableData.getOrderNo(TableData.getSelectedTable());
            boolean isNotNull = false;
            PreparedStatement popPS = connectDB.prepareStatement("SELECT Produkt.Name,Produkt.Preis,custID, Produkt.Type ,Produkt.ID  FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ?");;
            popPS.setString(1, String.valueOf(regNr));
            ResultSet rs = popPS.executeQuery();
            while (rs.next()){
            String foodName = rs.getString(1);
            Double Pries = rs.getDouble(2);
            int anzahl = 1;
            String cno= rs.getString(3);
            int type = rs.getInt(4);
            int pId = rs.getInt(5);
            orderList.add(new Order(foodName,Pries,anzahl,cno,type,pId));
            isNotNull =true;
            }
            if(isNotNull) {
                allOrdersTableList.setItems(orderList);
                allOrdersTableList.refresh();
            }
            /*
//            if(s.size() != 0){
////                for (int i = 0 ; i < s.size() ; i++){
////                    String foodName = s.get(i).split("-")[0];
////                    Double Pries = Double.parseDouble(s.get(i).split("-")[1]);
////                    int anzahl = Integer.parseInt(s.get(i).split("-")[2]);
////                    String cno= s.get(i).split("-")[3];
////                    int type = Integer.parseInt(s.get(i).split("-")[4]);
////                    orderList.add(new Order(foodName,Pries,anzahl,cno,type,));
////                }
//
//
//
//                allOrdersTableList.setItems(orderList);
//                allOrdersTableList.refresh();
//
//            }
*/
            connectDB.close();



        } catch (SQLException e) {

            Logger.getLogger(Tisch_Controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    void mergeButton(ActionEvent event) throws SQLException {
//        TableData.mergeTable(TableData.getSelectedTable(),Integer.parseInt(mergeT.getText()));
//        allOrdersTableList.getItems().clear();
        // for Database
        int mergeNum = Integer.parseInt(mergeT.getText());
        Order order = allOrdersTableList.getSelectionModel().getSelectedItem();
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps= connectDB.prepareStatement("UPDATE `Orders` SET regNr = ? WHERE regNr = ?");
        System.out.println(mergeNum);
//        ps.setInt(1,mergeNum);
        ps.setString(1,String.valueOf(TableData.getOrderNo(mergeNum)));
        ps.setString(2,String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
        ps.executeUpdate();

//        ps.execute();
        connectDB.close();
//        allOrdersTableList.getItems().clear();
    }

    @FXML
    void splitButton(ActionEvent event) throws SQLException {
//        ArrayList<String> s = TableData.getTableFood(TableData.getSelectedTable());
//        ArrayList<String> customerCount = new ArrayList<>(); // Keep track of Customer from the order of one tabel
//        for (int i = 0 ; i < s.size() ; i ++){// getting number of Customers
//            boolean ifNotExist = true;
//            if(customerCount.size() == 0){
//                customerCount.add(s.get(i).split("-")[3]);// getting customer no by splitting from String
//                continue;
//            }
//            else{
//                for(int j = 0 ; j < customerCount.size() ; j++){
//                    if(customerCount.get(j).equals(s.get(i).split("-")[3])){ // checking if the customerexit in customer count or not
//                        ifNotExist = false;
//                        continue;
//                    }
//                }
//            }
//            if(ifNotExist){
//                customerCount.add(s.get(i).split("-")[3]);
//            }
//        }
        String splitBill = new String();
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps = connectDB.prepareStatement("SELECT DISTINCT COUNT(custID ) FROM Orders WHERE regNr = ?");
        ps.setString(1,String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        Double []splitPrice = new Double[count+1];
        //Getting split amount
        for (int i = 1 ; i < count + 1; i++){
            PreparedStatement psSplit = connectDB.prepareStatement("SELECT SUM(Produkt.Preis) FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ? and custID = ? ");
            psSplit.setString(1,String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
            psSplit.setInt(2,i);
            ResultSet rsSplit = psSplit.executeQuery();
            rsSplit.next();
            splitPrice[i] = rsSplit.getDouble(1);
            splitBill += String.format(" C%s: %.1f",i,splitPrice[i]);
        }
        totalBillText.setText(splitBill);
        // changing order numbers
        for (int i = 1 ; i < count + 1; i++){
            PreparedStatement psSplit = connectDB.prepareStatement("UPDATE `Orders` SET `regNr` = ? WHERE regNr = ? and custID = ?; ");
            psSplit.setString(2,String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
            if (i > 1)
            TableData.incrementOrder(TableData.getSelectedTable());
            psSplit.setString(1,String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())+ (i - 1)) );
            psSplit.setInt(3,i);
            psSplit.executeUpdate();

        }
        //Statement delNullS = connectDB.createStatement();
        //delNullS.execute("DELETE from Orders where custID is null ");
        PreparedStatement tempps = connectDB.prepareStatement("INSERT INTO `Orders` (`id`, `produktID`, `tisch`, `custID`, `mitarbeiterID`, `date`, `regNr`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, ?)");
        tempps.setInt(1,TableData.getOrderNo(TableData.getSelectedTable()) + 1);
        tempps.execute();


//        // Now Splitting the bill
//        Double[] sum = new Double[customerCount.size()];
//        String splitBill = "";
//        Arrays.fill(sum,0.0);
//        if(s.size() != 0){
//            for(int k = 0 ; k <customerCount.size(); k++){
//                for (int i = 0 ; i < s.size() ; i++){
//                    if(customerCount.get(k).equals(s.get(i).split("-")[3])){
//                        String foodName = s.get(i).split("-")[0];
//                        Double Pries = Double.parseDouble(s.get(i).split("-")[1]);
//                        int anzahl = Integer.parseInt(s.   get(i).split("-")[2]);
//                       System.out.println("Food " + foodName + " Total " + (Pries * anzahl));
//                       sum[k] += (Pries * anzahl);
//                   }
//                }
//                splitBill += String.format(" C%s: %.1f",customerCount.get(k),sum[k]);
//            }
//
//            System.out.println("Total of each food is : " + splitBill);
//            totalBillText.setText(splitBill);
//        }
//        else
//            System.out.println("No Order");
//
    }

    @FXML
    void totalButton(ActionEvent event) throws SQLException {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        ArrayList<String> s = TableData.getTableFood(TableData.getSelectedTable());
        Double sum = 0.0;
        PreparedStatement mealPS = connectDB.prepareStatement("SELECT SUM(Produkt.Preis) FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ?");
        mealPS.setString(1,String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
        ResultSet rs = mealPS.executeQuery();
        rs.next();
        sum = rs.getDouble(1);
        /*if(s.size() != 0){
//            for (int i = 0 ; i < s.size() ; i++){
//                String foodName = s.get(i).split("-")[0];
//                Double Pries = Double.parseDouble(s.get(i).split("-")[1]);
//                int anzahl = Integer.parseInt(s.get(i).split("-")[2]);
//                mealPS.setInt(1,TableData.getSelectedTable());
//                mealPS.setString(2,foodName);
//                mealPS.execute();
//
//                System.out.println("Food " + foodName + " Total " + (Pries * anzahl));
//                sum += (Pries * anzahl);
//            }
//            System.out.println("Total of all food is : " + sum);
//            totalBillText.setText(String.valueOf(sum));

//            PreparedStatement ps = connectDB.prepareStatement("INSERT INTO `orderhistory` (`Id`, `WaiterID`, `waiterName`, `orderPrice`) VALUES (NULL,?, ?, ?);");
//            ps.setString(1,String.valueOf(currentUser.getId()));
//            ps.setString(2,currentUser.getBenutzername());
//            ps.setString(3,String.valueOf(sum));
//            ps.execute();
//            PreparedStatement waiterRevS = connectDB.prepareStatement("Select Revenue from SalesRevenue WHERE `WaiterID` = ? ");
//            waiterRevS.setInt(1,currentUser.getId());
//            System.out.println("Waiter Name "+ currentUser.getId());
//            ResultSet wRS = waiterRevS.executeQuery();
//            wRS.next();
//            Double WaiterOldrev = wRS.getDouble(1);
//            PreparedStatement waiterUpdatedRev = connectDB.prepareStatement("UPDATE `SalesRevenue` SET `Revenue` = ? WHERE `SalesRevenue`.`WaiterID` = ?");
//            waiterUpdatedRev.setDouble(1,(WaiterOldrev + sum));
//
//            waiterUpdatedRev.setInt(2,currentUser.getId());
//            waiterUpdatedRev.executeUpdate();


        }
//        else
//            System.out.println("No Order");
        */
        totalBillText.setText(String.valueOf(sum));
//        TableData.incrementOrder(TableData.getSelectedTable());
        //Statement delNullS = connectDB.createStatement();
        //delNullS.execute("DELETE from Orders where custID is null ");
        PreparedStatement tempps = connectDB.prepareStatement("INSERT INTO `Orders` (`id`, `produktID`, `tisch`, `custID`, `mitarbeiterID`, `date`, `regNr`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, ?)");
        tempps.setInt(1,TableData.getOrderNo(TableData.getSelectedTable()) + 1 );
        tempps.execute();
        connectDB.close();
    }
    @FXML
    void onGenerateInvoice(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException, SQLException {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement custCountS = connectDB.prepareStatement("SELECT DISTINCT COUNT(custID) from Orders where regNr = ?");
        custCountS.setInt(1,TableData.getOrderNo(TableData.getSelectedTable()));
        ResultSet custCountRS = custCountS.executeQuery();
        custCountRS.next();
        int noOfCust = custCountRS.getInt(1);
        System.out.println("Number of Customer = " + noOfCust);
//        ArrayList<String> s = TableData.getTableFood(TableData.getSelectedTable());
//        ArrayList<String> customerCount = new ArrayList<>(); // Keep track of Customer from the order of one tabel
//        for (int i = 0 ; i < s.size() ; i ++){// getting number of Customers
//            boolean ifNotExist = true;
//            if(customerCount.size() == 0){
//                customerCount.add(s.get(i).split("-")[3]);// getting customer no by splitting from String
//                continue;
//            }
//            else{
//                for(int j = 0 ; j < customerCount.size() ; j++){
//                    if(customerCount.get(j).equals(s.get(i).split("-")[3])){ // checking if the customerexit in customer count or not
//                        ifNotExist = false;
//                        continue;
//                    }
//                }
//            }
//            if(ifNotExist){
//                customerCount.add(s.get(i).split("-")[3]);
//            }
//        }
        PrintWriter writer = new PrintWriter("Receipt.txt", "UTF-8");
        writer.printf("%15s", "My Restaurant");
        writer.println("-----------------------------------");
        for (int j = 1; j <= noOfCust; j++) {
            Double sum = 0.0;
            PreparedStatement ps = connectDB.prepareStatement("SELECT Produkt.Name,Produkt.Preis from Produkt INNER JOIN Orders where Orders.produktID = Produkt.ID and Orders.regNr = ? and custID = ?");
            ps.setInt(1, TableData.getOrderNo(TableData.getSelectedTable()));
            ps.setInt(2, j);
            ResultSet rs = ps.executeQuery();
            writer.println("--------Customer#" + (j) + "-----------");
            writer.printf("%10s%10s%n", "Food Name", "Preis");
            while (rs.next()) {
                String foodName = rs.getString(1);
                Double Pries = rs.getDouble(2);
                sum += Pries;
                writer.printf("%10s%10s%n", foodName, Pries);

            }
            writer.println("-----------------------------------");
            writer.println("Your total bill amount is $" + sum);
            writer.println("Your Bill No is :" + (TableData.getOrderNo(TableData.getSelectedTable() ) + (j - 1 )));

            // Now Splitting the bill
//        Double[] sum = new Double[customerCount.size()];
//        String splitBill = "";
//        Arrays.fill(sum,0.0);
//        if(s.size() != 0){
//            for(int k = 0 ; k <customerCount.size(); k++){
//                writer.printf("%15s","My Restaurant");
//                writer.println("-----------------------------------");
//                writer.println("--------Customer#"+ (k + 1)+ "-----------");
//                writer.printf("%10s%10s%10s%n","Food Name","Preis","Anzahl");
//                for (int i = 0 ; i < s.size() ; i++){
//                    if(customerCount.get(k).equals(s.get(i).split("-")[3])){
//                        String foodName = s.get(i).split("-")[0];
//                        Double Pries = Double.parseDouble(s.get(i).split("-")[1]);
//                        int anzahl = Integer.parseInt(s.   get(i).split("-")[2]);
////                        System.out.println("Food " + foodName + " Total " + (Pries * anzahl));
//                        writer.printf("%10s%10s%10s%n",foodName,Pries,anzahl);
//                        sum[k] += (Pries * anzahl);
//                    }
//                }
////                splitBill += String.format("C%s: %.1f",customerCount.get(k),sum[k]);
//                writer.println("-----------------------------------");
//                writer.println("Your total bill amount is $" + sum[k]);
////                writer.println("Your total bill amount is $" + totalBillText.getText() + ".");
//
//                writer.println(">----------------------------------->");
//            }
//
//            System.out.println("Total of each food is : " + splitBill);
//            totalBillText.setText(splitBill);
//        }

        }
        writer.close();
    }

    @FXML
    void onchangeTable(ActionEvent event) {
        boolean flag  = TableData.transferTable(TableData.getSelectedTable(),Integer.parseInt(changeTableT.getText()));
        if(flag){
            allOrdersTableList.getItems().clear();
        }
        else {
            System.out.println("Table Occupied");
        }


    }

    public String  converttoDouble(String  val ){
       String temp =  String.format("%.1f",Double.parseDouble(val));
       try {
            temp = temp.replace(',', '.');
        }
       catch (Exception e){
           return temp;
       }
        return temp;
    }

    public void printInvoice(){

    }

    @FXML
    void onChangeCust(ActionEvent event) throws SQLException {
        Order or = allOrdersTableList.getSelectionModel().getSelectedItem();
//        deleteOrder(event);
//        orderList.add(new Order(or.getName(),or.getPries(),Integer.parseInt(anzahl.getText()),custNoTextF.getText(),or.getType()));
//        String foodS = or.getName() + "-" + or.getPries() + "-" + anzahl.getText()+ "-" + custNoTextF.getText();
//        TableData.addTableFood(TableData.getSelectedTable(),foodS);
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps = connectDB.prepareStatement("UPDATE `Orders` SET `custID` = ? WHERE  custID = ? and regNr = ? and produktID = ?");
        ps.setInt(1, Integer.parseInt(custNoTextF.getText()));
        ps.setInt(2,Integer.parseInt(or.getCustomer()));
        ps.setString(3,String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
        ps.setInt(4,or.getId());
        ps.executeUpdate();
        allOrdersTableList.getItems().clear();
        allOrdersTableList.refresh();
        int regNr = TableData.getOrderNo(TableData.getSelectedTable());
        boolean isNotNull = false;
        PreparedStatement popPS = connectDB.prepareStatement("SELECT Produkt.Name,Produkt.Preis,custID, Produkt.Type ,Produkt.ID  FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ?");;
        popPS.setString(1, String.valueOf(regNr));
        ResultSet rs = popPS.executeQuery();
        while (rs.next()){
            String foodName = rs.getString(1);
            Double Pries = rs.getDouble(2);
            int anzahl = 1;
            String cno= rs.getString(3);
            int type = rs.getInt(4);
            int pId = rs.getInt(5);
            orderList.add(new Order(foodName,Pries,anzahl,cno,type,pId));
            isNotNull =true;
        }
        if(isNotNull) {
            allOrdersTableList.setItems(orderList);
            allOrdersTableList.refresh();
        }
        connectDB.close();

    }

}