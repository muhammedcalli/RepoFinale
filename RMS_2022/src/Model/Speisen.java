package Model;

import Controller.Speisen_Controller;
import Controller.Tisch_Controller;

public class Speisen extends Tisch_Controller {

    private int id;
    private String name;
    private String preis;

    public Speisen(int id, String name, String preis) {
        this.id = id;
        this.name = name;
        this.preis = preis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
