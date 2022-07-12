package Model;

import Controller.Admin_Controller;

public class User extends Admin_Controller {
    private int id;
    private String benutzername;
    public static User currentUser;
    private  boolean aktiveUser;

    public User(int id, String benutzername, boolean aktiverUser) {
        this.id = id;
        this.benutzername = benutzername;
        this.aktiveUser = aktiverUser;
    }

    public int getId() {
        return id;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public boolean isAktiveUser() {
        return aktiveUser;
    }

}



