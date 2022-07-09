package Model;

import Controller.Tisch_Controller;

public class Drinks extends Tisch_Controller{

    private  int id;
    private String name;
    private String preis;

    public Drinks(int id, String name, String preis) {
        this.id = id;
        this.name = name;
        this.preis = preis;
    }
    public  int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPreis() {
        return preis;
    }
    public void setPreis(String preis) {
        this.preis = preis;
    }

}
