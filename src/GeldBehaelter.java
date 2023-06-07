public class GeldBehaelter {

    private int balance;

    public GeldBehaelter(){}

    public int GetBalance() {
        return balance;
    }

    public boolean FuegeGeldHinzu (int summe) {
        boolean returnBoolean = false;
        if (summe > 0) {
            balance += summe;
            returnBoolean = true;
        }

        return returnBoolean;
    }

    public boolean ZieheGeldAb(int summe) {
        boolean returnBoolean = false;
        if (balance >= summe && summe > 0) {
            balance -= summe;
            returnBoolean = true;
        }

        return  returnBoolean;
    }
}
