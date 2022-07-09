package Model;

public class Order {
    String name;
    Double Pries;
    int anzahl;
    String customer;
    int type;
    int id;


    public Order (String name, Double pries, int anzahl,String customer,int type,int id) {
        this.name = name;
        Pries = pries;
        this.anzahl = anzahl;
        this.customer = customer;
        this.type = type;
        this.id = id;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getType () {
        return type;
    }

    public void setType (int type) {
        this.type = type;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Double getPries () {
        return Pries;
    }

    public void setPries (Double pries) {
        Pries = pries;
    }

    public int getAnzahl () {
        return anzahl;
    }

    public void setAnzahl (int anzahl) {
        this.anzahl = anzahl;
    }

    public String getCustomer () {
        return customer;
    }

    public void setCustomer (String customer) {
        this.customer = customer;
    }
}
