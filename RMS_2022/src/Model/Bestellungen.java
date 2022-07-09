package Model;

public class Bestellungen {
    private int id;
    private int tischnummer;
    private int speiseId;
    private int  drinksId;
    private double totalpreis;

    public Bestellungen(int id, int tischnummer, int speiseId, int drinksId, double totalpreis) {
        this.id = id;
        this.tischnummer = tischnummer;
        this.speiseId = speiseId;
        this.drinksId = drinksId;
        this.totalpreis = totalpreis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTischnummer() {
        return tischnummer;
    }

    public void setTischnummer(int tischnummer) {
        this.tischnummer = tischnummer;
    }

    public int getSpeiseId() {
        return speiseId;
    }

    public void setSpeiseId(int speiseId) {
        this.speiseId = speiseId;
    }

    public int getDrinksId() {
        return drinksId;
    }

    public void setDrinksId(int drinksId) {
        this.drinksId = drinksId;
    }

    public double getTotalpreis() {
        return totalpreis;
    }

    public void setTotalpreis(double totalpreis) {
        this.totalpreis = totalpreis;
    }
}
