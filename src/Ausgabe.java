import java.util.List;

public class Ausgabe<AusgabeType>{

    private AusgabeType ausgabe;

    public Ausgabe() {}

    public AusgabeType GetAusgabe() {
        return ausgabe;
    }

    public boolean GebeAus (AusgabeType nAusgabe) {
        boolean returnBoolean = false;
        if (nAusgabe != null) {
            ausgabe = nAusgabe;
            returnBoolean = true;
        }
        return returnBoolean;
    }

    public boolean IstLeer() {
        return ausgabe == null;
    }

    public boolean LeereAusgabe() {
        boolean returnBoolean = false;
        if (ausgabe != null) {
            ausgabe = null;
            returnBoolean = true;
        }
        return returnBoolean;
    }

}
