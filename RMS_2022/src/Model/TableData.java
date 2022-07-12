package Model;

import Controller.Database_Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableData {
    static int selectedTable;
    static int[] tableStatus = new int[10];
    static String[][] tableFood = new String[10][15];
    static int[] OrderNo = new int[10];


    // Inkrementiert die Order Nummer mit der Tischnummer
    public static void incrementOrder(int tableNo) {
        OrderNo[tableNo]++;

    }

    public static int getOrderNo(int tableNo) throws SQLException {
        int highVal = 0;
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps = connectDB.prepareStatement("SELECT DISTINCT MAX(regNr) FROM Orders where regNr < ? and regNr > ?");
        ps.setInt(1, (tableNo + 1) * 1000);
        ps.setInt(2, tableNo * 1000);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            highVal = rs.getInt(1);
        }
        int diff = 0;
        if (highVal > 0)
            diff = highVal - (tableNo * 1000);
        else
            diff = 0;
        System.out.println(OrderNo[tableNo] + (tableNo * 1000) + diff);
        connectDB.close();
        return (tableNo * 1000) + diff;
    }

    public static int getSelectedTable() {
        return selectedTable;
    }

    public static void setSelectedTable(int selectedTable) {
        TableData.selectedTable = selectedTable;
    }

    public static String getTableStatus(int tableNo) { // we will pass tableNo and then depending aoin the Status value (int) it will return a string of color name
        if (tableStatus[tableNo] == 1) {
            return "Green";
        } else if (tableStatus[tableNo] == 2) {
            return "Yellow";
        } else if (tableStatus[tableNo] == 3) {
            return "Red";
        }
        return "Green";
    }

    public static void setTableStatus(int tableNo, int status) {
        TableData.tableStatus[tableNo] = status;
    }

    public static void addTableFood(int tableNo, String foodNanzahl) {

        for (int i = 0; i < tableFood[tableNo].length; i++) {
            if (tableFood[tableNo][i] == null) {
                tableFood[tableNo][i] = foodNanzahl;
                break;

            } else {
                if (tableFood[tableNo][i].isEmpty()) {
                    tableFood[tableNo][i] = foodNanzahl;
                    break;
                } else {
                    continue;
                }
            }
        }
    }

    public static ArrayList<String> getTableFood(int tableNo) {
        ArrayList<String> temp = new ArrayList<>();

        for (int i = 0; i < tableFood[tableNo].length; i++) {
            if (tableFood[tableNo][i] != null) {
                if (!tableFood[tableNo][i].isEmpty()) {
                    temp.add(tableFood[tableNo][i]);
                }

            }

        }
        return temp;
    }

}
