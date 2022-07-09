package Model;

import Controller.Database_Controller;
import javafx.scene.control.Tab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class TableData {
    static int selectedTable; // for passing selected table to other screen
    static int[] tableStatus = new int[10]; // for keeping record of table status
    static String[][] tableFood = new String[10][15]; // row for NoofTable , column for food
    static int[] OrderNo = new int[10];


    public static void incrementOrder(int tableNo){
        OrderNo[tableNo]++;

    }
    public static void setLatestOrder(int tableNo){

    }
    public static int getOrderNo(int tableNo) throws SQLException {
        int highVal = 0;
        Database_Controller connection = new Database_Controller();
        Connection connectDB = connection.getConnection();
        PreparedStatement ps = connectDB.prepareStatement("SELECT DISTINCT MAX(regNr) FROM Orders where regNr < ? and regNr > ?");
        ps.setInt(1,(tableNo + 1 )  * 100);
        ps.setInt(2,tableNo * 100);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            highVal = rs.getInt(1);
        }
        int diff = 0;
        if (highVal > 0)
         diff = highVal - (tableNo * 100);
        else
            diff = 0;
        System.out.println(OrderNo[tableNo]  + (tableNo * 100) + diff);
        connectDB.close();
        return   (tableNo * 100) + diff;
    }

    public static int getSelectedTable () {
        return selectedTable;
    }

    public static void setSelectedTable (int selectedTable) {
        TableData.selectedTable = selectedTable;
    }

    public static String getTableStatus (int tableNo) { // we will pass tableNo and then depending aoin the Status value (int) it will return a string of color name
        if(tableStatus[tableNo] == 1){
            return "Green";
        }
        else if (tableStatus[tableNo] == 2){
            return "Yellow";
        }
        else if (tableStatus[tableNo] == 3){
            return "Red";
        }
        return "Green";
    }

    public static void setTableStatus (int tableNo, int status) {
        TableData.tableStatus[tableNo] = status;
    }

    public static void addTableFood(int tableNo, String foodNanzahl){

        for (int i = 0 ; i < tableFood[tableNo].length;i++){
            if(tableFood[tableNo][i] == null){
                    tableFood[tableNo][i] = foodNanzahl;
                    break;

            }
            else {
                if(tableFood[tableNo][i].isEmpty()){
                    tableFood[tableNo][i] = foodNanzahl;
                    break;
                }
                else {
                    continue;
                }


            }


        }

    }

    public  static ArrayList<String> getTableFood (int tableNo){
        ArrayList<String> temp = new ArrayList<>();

        for (int i = 0 ; i < tableFood[tableNo].length ; i++){
            if(tableFood[tableNo][i] != null){
                if(!tableFood[tableNo][i].isEmpty()){
                    temp.add(tableFood[tableNo][i]);
                }

            }

        }
        return temp;
    }

    public static void deleteTableFood(int tableNo,String foodNanzahl){
        boolean flag = false; // for keeping check if food was delted or not
        for (int i = 0 ; i < tableFood[tableNo].length ; i++){
            if(tableFood[tableNo][i] != null){
               if(!tableFood[tableNo][i].isEmpty()){
                   if(tableFood[tableNo][i].equals(foodNanzahl)){
                       tableFood[tableNo][i] = "";
                       flag = true;
                       break;
                   }
               }

            }

        }
        if(flag){
            System.out.println("Food Found and Deleted ");
        }
        else{
            System.out.println("food Not Found");
        }

    }

    public static void mergeTable(int TableNo1 , int TableNo2){
        ArrayList<String> al = getTableFood(TableNo1); // getting all the food of selected table
        if(al.size() != 0){
            for(int i = 0 ; i < al.size() ; i++){ // adding the food to the table that is to be merged
                addTableFood(TableNo2,al.get(i));
            }
            deleteAllTable(TableNo1); // Deleteing the order of the table that is moved to tableno1

        }

    }

    public static boolean transferTable(int TableNo1 , int TableNo2){ // To transfer selected table to desired Table
        ArrayList<String> al = getTableFood(TableNo1); // getting food of seleceted Table
        ArrayList<String> a2 = getTableFood(TableNo2);
        boolean flag = true;
      if(al.size() != 0){
          if(a2.size() == 0){
              for(int i = 0 ; i < al.size() ; i++){ // adding the food to the table that is to be Tramsferred tp
                  addTableFood(TableNo2,al.get(i));
              }
              deleteAllTable(TableNo1); // Deleteing the order of the table that is moved to tableno1
          }
          else{
              System.out.println("Table NO : " + TableNo2 + "Occupied");
              flag = false;
          }
      }
return flag;
    }

    public static void deleteAllTable(int TableNo){
        Arrays.fill(tableFood[TableNo],"");
    }
}
