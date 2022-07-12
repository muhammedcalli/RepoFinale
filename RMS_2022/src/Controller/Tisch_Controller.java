package Controller;

import Model.*;
import javafx.scene.text.Text;
import Main.Main;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Model.User.currentUser;


public class Tisch_Controller implements Initializable {
    String anzahlcount = "0";
    PassData pd = new PassData(); //touch
    TableData td = new TableData();
    @FXML
    private Button goBackButton;
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

       // schließt das Fenster
    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }

    // Öffnet das Fenster um den Status vom Tisch zu ändern
    @FXML
    void edit(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/Status.fxml"));
        Stage Status = new Stage();
        Scene hallo = new Scene(fxmlLoader.load(), 550, 500);
        Status.setTitle("Bestellungen");
        Status.setScene(hallo);
        Status.setResizable(false);
        Status.initModality(Modality.WINDOW_MODAL);
        Status.initOwner(editButton.getScene().getWindow());
        Status.show();
    }

    // Fügt Bestellungen dem Tisch hinzu und speichert Sie in der Datenbank
    @FXML
    void addItemsToTable(ActionEvent event) throws SQLException {
        Speisen sp = DishTable.getSelectionModel().getSelectedItem();
        Drinks d = TabelDate.getSelectionModel().getSelectedItem();
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();

        if (!(sp == null)) {
            for (int i = 0; i < Integer.parseInt(anzahl.getText()); i++) {
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
        if (!(d == null)) {
            for (int i = 0; i < Integer.parseInt(anzahl.getText()); i++) {
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

    // Öffnet das Fenster um die Anzahl für die Bestellung auszuwählen
    @FXML
    void chooseAnzahl(MouseEvent event) throws IOException {
        PassData.setselectedBox(1); // for anzahl
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
        Stage Anzahl = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 250, 457);
        Anzahl.setTitle("Anzahl");
        Anzahl.setScene(scene);
        Anzahl.setResizable(false);
        Anzahl.initModality(Modality.WINDOW_MODAL);
        Anzahl.initOwner(anzahl.getScene().getWindow());
        Anzahl.showAndWait();
        anzahl.setText(String.valueOf(PassData.getAnzhel()));


    }

    // Öffnet das Fenster um einer Bestellung einen Kunden zuzuweisen für den Rechnugs split
    @FXML
    void secondfield(MouseEvent event) throws IOException {
        PassData.setselectedBox(2); // for customer
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
        Stage Anzahl = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 250, 457);
        Anzahl.setTitle("Costumer");
        Anzahl.setScene(scene);
        Anzahl.initModality(Modality.WINDOW_MODAL);
        Anzahl.initOwner(custNoTextF.getScene().getWindow());
        Anzahl.showAndWait();
        custNoTextF.setText(String.valueOf(PassData.getcustomer()));
    }

    // Öffnet ein Fenster um die neue Tischnummer einzugeben wohin die Bestellunen verschoben werden sollen
    @FXML
    void thirdfield(MouseEvent event) throws IOException {
        PassData.setselectedBox(3); // for customer
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/ChooseAnzahl.fxml"));
        Stage Anzahl = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 250, 457);
        Anzahl.setTitle("Anzahl");
        Anzahl.setScene(scene);
        Anzahl.initModality(Modality.WINDOW_MODAL);
        Anzahl.initOwner(mergeT.getScene().getWindow());
        Anzahl.showAndWait();
        mergeT.setText(String.valueOf(PassData.getmerge()));
    }

    // löscht eine Bestellung
    @FXML
    void deleteOrder(ActionEvent event) throws SQLException {
        Order order = allOrdersTableList.getSelectionModel().getSelectedItem();
        String foodS = order.getName() + "-" + order.getPries() + "-" + order.getAnzahl() + "-" + order.getCustomer() + "-" +order.getType();
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps = connectDB.prepareStatement("DELETE FROM `Orders` WHERE produktID = ? and regNr = ? and custID = ?");
        ps.setInt(1, order.getId());
        ps.setInt(2, TableData.getOrderNo(TableData.getSelectedTable()));
        ps.setInt(3, Integer.parseInt(order.getCustomer()));
        System.out.println(order.getId() + " - " + TableData.getOrderNo(TableData.getSelectedTable()) + " - " + order.getCustomer());
        ps.executeUpdate();
        allOrdersTableList.getItems().clear();
        allOrdersTableList.refresh();

        // Rrefershing the view by getting tablefood and populating the tablev

        int regNr = TableData.getOrderNo(TableData.getSelectedTable());
        boolean isNotNull = false;
        PreparedStatement popPS = connectDB.prepareStatement("SELECT Produkt.Name,Produkt.Preis,custID, Produkt.Type,Produkt.ID FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ?");
        ;
        popPS.setInt(1, regNr);
        ResultSet rs = popPS.executeQuery();
        while (rs.next()) {
            String foodName = rs.getString(1);
            Double Pries = rs.getDouble(2);
            int anzahl = 1;
            String cno = rs.getString(3);
            int type = rs.getInt(4);
            int pID = rs.getInt(5);
            orderList.add(new Order(foodName, Pries, anzahl, cno, type, pID));
            isNotNull = true;
        }
        if (isNotNull) {
            allOrdersTableList.setItems(orderList);
            allOrdersTableList.refresh();
        }
        connectDB.close();
    }

    public void getDishInt() {
        int selectedDishindex = DishTable.getSelectionModel().getSelectedIndex();
        System.out.println(selectedDishindex);
    }

    public int getInt() {
        int selectedIndex = TabelDate.getSelectionModel().getSelectedIndex();
        return selectedIndex;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anzahl.setText("1");
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
                speisenList.add(new Speisen(speisenID, speisenName, speisenPreis));

                nameDishTableCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
                preisDishTableCol.setCellValueFactory(new PropertyValueFactory<>("Preis"));
                DishTable.setItems(speisenList);
            }
            anzahl.setText(String.valueOf(PassData.getAnzhel()));
            anzahlOrderCol.setCellValueFactory(new PropertyValueFactory<>("Anzahl"));
            nameOrderCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            PriesOrderCol.setCellValueFactory(new PropertyValueFactory<>("Pries"));
            custOrderCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
            int regNr = TableData.getOrderNo(TableData.getSelectedTable());
            boolean isNotNull = false;
            PreparedStatement popPS = connectDB.prepareStatement("SELECT Produkt.Name,Produkt.Preis,custID, Produkt.Type ,Produkt.ID  FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ?");
            ;
            popPS.setString(1, String.valueOf(regNr));
            ResultSet rs = popPS.executeQuery();
            while (rs.next()) {
                String foodName = rs.getString(1);
                Double Pries = rs.getDouble(2);
                int anzahl = 1;
                String cno = rs.getString(3);
                int type = rs.getInt(4);
                int pId = rs.getInt(5);
                orderList.add(new Order(foodName, Pries, anzahl, cno, type, pId));
                isNotNull = true;
            }
            if (isNotNull) {
                allOrdersTableList.setItems(orderList);
                allOrdersTableList.refresh();
            }
            connectDB.close();
        } catch (SQLException e) {

            Logger.getLogger(Tisch_Controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void mergeButton(ActionEvent event) throws SQLException {
        int mergeNum = Integer.parseInt(mergeT.getText());

        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps = connectDB.prepareStatement("UPDATE `Orders` SET regNr = ? WHERE regNr = ?");
        System.out.println(mergeNum);

        ps.setString(1, String.valueOf(TableData.getOrderNo(mergeNum)));
        ps.setString(2, String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
        ps.executeUpdate();


        connectDB.close();

    }

    @FXML
    void splitButton(ActionEvent event) throws SQLException {
        String splitBill = new String();
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps = connectDB.prepareStatement("SELECT DISTINCT COUNT(custID ) FROM Orders WHERE regNr = ?");
        ps.setString(1, String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        Double[] splitPrice = new Double[count + 1];
        //Getting split amount
        for (int i = 1; i < count + 1; i++) {
            PreparedStatement psSplit = connectDB.prepareStatement("SELECT SUM(Produkt.Preis) FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ? and custID = ? ");
            psSplit.setString(1, String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
            psSplit.setInt(2, i);
            ResultSet rsSplit = psSplit.executeQuery();
            rsSplit.next();
            splitPrice[i] = rsSplit.getDouble(1);
            splitBill += String.format(" C%s: %.1f", i, splitPrice[i]);
        }
        totalBillText.setText(splitBill);
        // changing order numbers
        for (int i = 1; i < count + 1; i++) {
            PreparedStatement psSplit = connectDB.prepareStatement("UPDATE `Orders` SET `regNr` = ? WHERE regNr = ? and custID = ?; ");
            psSplit.setString(2, String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
            if (i > 1)
                TableData.incrementOrder(TableData.getSelectedTable());
            psSplit.setString(1, String.valueOf(TableData.getOrderNo(TableData.getSelectedTable()) + (i - 1)));
            psSplit.setInt(3, i);
            psSplit.executeUpdate();

        }
        PreparedStatement tempps = connectDB.prepareStatement("INSERT INTO `Orders` (`id`, `produktID`, `tisch`, `custID`, `mitarbeiterID`, `date`, `regNr`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, ?)");
        tempps.setInt(1, TableData.getOrderNo(TableData.getSelectedTable()) + 1);
        tempps.execute();
        Statement delNullS = connectDB.createStatement();
        delNullS.execute("delete from Orders where custID is NULL and regNr NOT in \n" +
                "((select Max(regNr) from (select * from Orders) as eins where regNr between 1000 and 1999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as zwei where regNr between 2000 and 2999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as drei where regNr between 3000 and 3999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as vier where regNr between 4000 and 4999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as funf where regNr between 5000 and 5999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as sechs where regNr between 6000 and 6999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as sieben where regNr between 7000 and 7999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as acht where regNr between 8000 and 8999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as neun where regNr between 9000 and 9999 having Max(regNr) is Not null))");
        connectDB.close();
    }
    @FXML
    void totalButton(ActionEvent event) throws SQLException {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        ArrayList<String> s = TableData.getTableFood(TableData.getSelectedTable());
        Double sum = 0.0;
        PreparedStatement mealPS = connectDB.prepareStatement("SELECT SUM(Produkt.Preis) FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ?");
        mealPS.setString(1, String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
        ResultSet rs = mealPS.executeQuery();
        rs.next();
        sum = rs.getDouble(1);
        totalBillText.setText(String.valueOf(sum));
        PreparedStatement tempps = connectDB.prepareStatement("INSERT INTO `Orders` (`id`, `produktID`, `tisch`, `custID`, `mitarbeiterID`, `date`, `regNr`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, ?)");
        tempps.setInt(1, TableData.getOrderNo(TableData.getSelectedTable()) + 1);
        tempps.execute();
        Statement delNullS = connectDB.createStatement();
        delNullS.execute("delete from Orders where custID is NULL and regNr NOT in \n" +
                "((select Max(regNr) from (select * from Orders) as eins where regNr between 1000 and 1999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as zwei where regNr between 2000 and 2999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as drei where regNr between 3000 and 3999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as vier where regNr between 4000 and 4999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as funf where regNr between 5000 and 5999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as sechs where regNr between 6000 and 6999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as sieben where regNr between 7000 and 7999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as acht where regNr between 8000 and 8999 having Max(regNr) is Not null) union all\n" +
                "(select Max(regNr) from (select * from Orders) as neun where regNr between 9000 and 9999 having Max(regNr) is Not null)) ");

        connectDB.close();
    }

    @FXML
    void onGenerateInvoice(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException, SQLException {
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement custCountS = connectDB.prepareStatement("SELECT DISTINCT COUNT(custID) from Orders where regNr = ?");
        custCountS.setInt(1, TableData.getOrderNo(TableData.getSelectedTable()));
        ResultSet custCountRS = custCountS.executeQuery();
        custCountRS.next();
        int noOfCust = custCountRS.getInt(1);
        System.out.println("Number of Customer = " + noOfCust);
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
            writer.println("Your Bill No is :" + (TableData.getOrderNo(TableData.getSelectedTable()) + (j - 1)));
        }
        writer.close();
    }

    public String converttoDouble(String val) {
        String temp = String.format("%.1f", Double.parseDouble(val));
        try {
            temp = temp.replace(',', '.');
        } catch (Exception e) {
            return temp;
        }
        return temp;
    }
    @FXML
    void onChangeCust(ActionEvent event) throws SQLException {
        Order or = allOrdersTableList.getSelectionModel().getSelectedItem();
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps = connectDB.prepareStatement("UPDATE `Orders` SET `custID` = ? WHERE  custID = ? and regNr = ? and produktID = ?");
        ps.setInt(1, Integer.parseInt(custNoTextF.getText()));
        ps.setInt(2, Integer.parseInt(or.getCustomer()));
        ps.setString(3, String.valueOf(TableData.getOrderNo(TableData.getSelectedTable())));
        ps.setInt(4, or.getId());
        ps.executeUpdate();
        allOrdersTableList.getItems().clear();
        allOrdersTableList.refresh();
        int regNr = TableData.getOrderNo(TableData.getSelectedTable());
        boolean isNotNull = false;
        PreparedStatement popPS = connectDB.prepareStatement("SELECT Produkt.Name,Produkt.Preis,custID, Produkt.Type ,Produkt.ID  FROM `Orders` INNER JOIN Produkt WHERE Orders.produktID = Produkt.ID and regNr = ?");
        ;
        popPS.setString(1, String.valueOf(regNr));
        ResultSet rs = popPS.executeQuery();
        while (rs.next()) {
            String foodName = rs.getString(1);
            Double Pries = rs.getDouble(2);
            int anzahl = 1;
            String cno = rs.getString(3);
            int type = rs.getInt(4);
            int pId = rs.getInt(5);
            orderList.add(new Order(foodName, Pries, anzahl, cno, type, pId));
            isNotNull = true;
        }
        if (isNotNull) {
            allOrdersTableList.setItems(orderList);
            allOrdersTableList.refresh();
        }
        connectDB.close();

    }

}