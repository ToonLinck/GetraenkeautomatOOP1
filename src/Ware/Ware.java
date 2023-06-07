package Ware;

public class Ware {

    private String name;
    private double preis;
    private int groesse;
    public Ware (String pName, double pPreis, int pGroesse) {
        name = pName;
        preis = pPreis;
        groesse = pGroesse;
    }

    public String GetName() {
        return name;
    }

    public double GetPreis() {
        return preis;
    }

    public int GetGroesse() {
        return groesse;
    }


}
