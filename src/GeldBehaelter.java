public class GeldBehaelter {

    private double balance = 0;

    public GeldBehaelter(){}

    public double GetBalance() {
        return balance;
    }

    public boolean FuegeGeldHinzu (double summe) {
        boolean returnBoolean = false;
        if (summe > 0) {
            balance += summe;
            returnBoolean = true;
        }

        return returnBoolean;
    }

    public boolean ZieheGeldAb(double summe) {
        boolean returnBoolean = false;
        if (balance >= summe && summe > 0) {
            balance -= summe;
            returnBoolean = true;
        }

        return  returnBoolean;
    }
}
