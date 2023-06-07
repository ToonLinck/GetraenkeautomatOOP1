import Geld.*;
import Ware.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Getraenkeautomat {
    /* Deklaration von statischen Variablen */
    static Lager lager = new Lager();
    static GeldBehaelter kasse = new GeldBehaelter();
    static GeldBehaelter geldBoerse = new GeldBehaelter();
    static Ausgabe<Ware> warenAusgabe = new Ausgabe<Ware>();
    static Ausgabe<Muenze> rueckgeldAusgabe = new Ausgabe<Muenze>();

    static JLabel errorMsgLabel;

    public static void main(String[] args) {
        //Waren werden zum Lager Hinzugefuegt
        lager.FuegeWareHinzu(new Getraenk("wasser", 1, 10));
        lager.FuegeWareHinzu(new Snack("Snickers", 2, 5));

        lager.FuelleAlleAuf();// Alle Lager werden aufgefuellt


        JFrame window;
        window = CreateGUI();
        if (window != null) {
            window.setVisible(true);
        }
    }


    public static boolean KaufeWare(Ware ware) {
        boolean returnBoolean = false;

        return returnBoolean;
    }

    private static JFrame CreateGUI () {
        JFrame window = null;
        try {
            window = new JFrame(Constants.WindowComponents.WindowName);

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(1000,800);
            window.setResizable(false);

            JPanel body = new JPanel();
            body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
            body.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

                JPanel header = new JPanel(new BorderLayout());
                header.setMaximumSize(new Dimension(1000, 70));

                    JLabel ueberschrift = new JLabel(Constants.WindowComponents.UeberschriftText);
                    ueberschrift.setFont(new Font("Bold", Font.PLAIN, 18));

                    JButton geldEinwurf = new JButton(Constants.WindowComponents.Geleinwurf);

                header.add(ueberschrift, BorderLayout.WEST);
                header.add(geldEinwurf, BorderLayout.EAST);

                JPanel getraenkeAuswahl = CreateWarenAuswahl(Getraenk.class);
                JPanel snackAuswahl = CreateWarenAuswahl(Snack.class);

                JPanel errorSlot = new JPanel(new BorderLayout());
                errorSlot.setMaximumSize(new Dimension(1000, 70));
                    errorMsgLabel = new JLabel();
                    errorMsgLabel.setForeground(Color.red);
                    errorSlot.add(errorMsgLabel);


                JPanel footer = new JPanel(new BorderLayout());
                footer.setMaximumSize(new Dimension(1000, 70));
                    JTextArea warenAusgabe = new JTextArea();
                    footer.add(warenAusgabe);




            body.add(header);
            body.add(getraenkeAuswahl);
            body.add(snackAuswahl);
            body.add(errorSlot);
            body.add(footer);
            window.getContentPane().add(body);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return window;
    }

    private static JPanel CreateWarenAuswahl (Class c) {

        JPanel returnPanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
        returnPanel.setBorder(BorderFactory.createEmptyBorder(30,30,0,0));
        for (Lager.WarenBehaelter lager: lager.getWarenListe()) {
            Ware selectedWare = lager.GetWare();
            if (selectedWare.getClass() == c) {
                JPanel warenContainer = new JPanel();
                warenContainer.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK, 1),
                        BorderFactory.createEmptyBorder(2,2,2,2)));
                warenContainer.setLayout(new BoxLayout(warenContainer,BoxLayout.Y_AXIS));

                JLabel warenName = new JLabel(selectedWare.GetName());
                warenName.setFont(new Font("Bold", Font.PLAIN, 14));
                JLabel warenPreis= new JLabel(selectedWare.GetPreis() + "€");
                JButton warenKaufButton = new JButton("Kaufen");
                warenKaufButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        KaufeWare(selectedWare);
                    }
                });

                warenContainer.add(warenName);
                warenContainer.add(warenPreis);
                warenContainer.add(warenKaufButton);

                returnPanel.add(warenContainer);
            }
        }
        return returnPanel;
    }
}
