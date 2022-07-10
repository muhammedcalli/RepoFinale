package Model;

public class PassData {
    static int anzhel;
    static int customer;
    static int merge;
    static int selectedBox;
    static int changeTable;

    static int cook;

    public PassData(){
        anzhel = 1 ;
        customer = 1;
    }

    public static int getChangeTable() {
        return changeTable;
    }

    public static void setChangeTable(int changeTable) {
        PassData.changeTable = changeTable;
    }

    public static int getselectedBox () {
        return selectedBox;
    }

    public static void setselectedBox (int selectedBox) {
        PassData.selectedBox = selectedBox;
    }

    public static int getAnzhel () {
        return anzhel;
    }

    public static void setAnzhel (int anzhel) {
        PassData.anzhel = anzhel;
    }

    public static int getcustomer () {
        return customer;
    }

    public static void setcustomer (int customer) {
        PassData.customer = customer;
    }

    public static int getmerge() {
        return merge;
    }

    public static void setmerge (int merge) {
        PassData.merge =merge;
    }

    public static int getCook() {
        return cook;
    }

    public static void setCook (int cook) { PassData.cook =cook; }


}
