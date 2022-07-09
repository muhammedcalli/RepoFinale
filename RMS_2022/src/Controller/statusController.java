package Controller;

import Model.TableData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class statusController {
    TableData td = new TableData();

    @FXML
    void onGreen(ActionEvent event) {
        int tableno = TableData.getSelectedTable();
        TableData.setTableStatus(tableno,1);
    }

    @FXML
    void onYello(ActionEvent event) {
        int tableno = TableData.getSelectedTable();
        TableData.setTableStatus(tableno,2);

    }

    @FXML
    void onRed(ActionEvent event) {
        int tableno = TableData.getSelectedTable();
        TableData.setTableStatus(tableno,3);

    }



}
