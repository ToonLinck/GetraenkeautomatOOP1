import javax.swing.*;

public class GUIContainer {

    public GUIContainer() {}

    private JFrame window;
    private JTextField warenAusgabe;
    private JTextField rueckGeldAusgabe;
    private JTextField kundeBalanceAnzeige;

    public JTextField getWarenAusgabe() {
        return warenAusgabe;
    }

    public void setWarenAusgabe(JTextField warenAusgabe) {
        this.warenAusgabe = warenAusgabe;
    }
    public JTextField getKundeBalanceAnzeige() {
        return kundeBalanceAnzeige;
    }

    public void setKundeBalanceAnzeige(JTextField kundeBalanceAnzeige) {
        this.kundeBalanceAnzeige = kundeBalanceAnzeige;
    }

    public JFrame getWindow() {
        return window;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

    public JTextField getRueckGeldAusgabe() {
        return rueckGeldAusgabe;
    }

    public void setRueckGeldAusgabe(JTextField rueckGeldAusgabe) {
        this.rueckGeldAusgabe = rueckGeldAusgabe;
    }



}
