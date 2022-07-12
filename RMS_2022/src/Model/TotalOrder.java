package Model;

// Gesamtumsatzanzeige für den Adminbereich
public class TotalOrder {
    private String KellnerName;
    private String Umsatz;

    public TotalOrder(String kellnerName, String umsatz) {
        KellnerName = kellnerName;
        Umsatz = umsatz;
    }

    public String getKellnerName() {
        return KellnerName;
    }

    public String getUmsatz() {
        return Umsatz;
    }

}


