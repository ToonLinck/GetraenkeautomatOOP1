import Geld.*;
import Ware.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Getraenkeautomat {
    /* Deklaration von statischen Variablen */
    static Lager lager = new Lager();
    static GeldBehaelter kasse = new GeldBehaelter();
    static GeldBehaelter geldBoerse = new GeldBehaelter();
    static Ausgabe<Ware> warenAusgabe = new Ausgabe<Ware>();
    static Ausgabe<Double> rueckgeldAusgabe = new Ausgabe<Double>();
    static GUIContainer guiContainer = new GUIContainer();

    private static final DecimalFormat df = new DecimalFormat("0.00");

    static JLabel errorMsgLabel;

    public static void main(String[] args) {

        List<Ware> warenListe = new ArrayList<Ware>();
        warenListe.addAll(Constants.Waren.getraenkListe);
        warenListe.addAll(Constants.Waren.snackListe);
        for (Ware ware: warenListe) {
            lager.FuegeWareHinzu(ware);
        }

        lager.FuelleAlleAuf();// Alle Lager werden aufgefuellt

        if (CreateGUI()) {
            guiContainer.getWindow().setVisible(true);
        }

        UpdateValues();
    }

    private static void UpdateValues() {

        if (!warenAusgabe.IstLeer())
            guiContainer.getWarenAusgabe().setText(warenAusgabe.GetAusgabe().GetName());
        else
            guiContainer.getWarenAusgabe().setText("");

        guiContainer.getKundeBalanceAnzeige().setText(df.format(geldBoerse.GetBalance()) + "€");

        if (!rueckgeldAusgabe.IstLeer())
            guiContainer.getRueckGeldAusgabe().setText(df.format(rueckgeldAusgabe.GetAusgabe()) + "€");
        else
            guiContainer.getRueckGeldAusgabe().setText(0.00 + "€");

        errorMsgLabel.setText("");
    }

    private static boolean KaufeWare(Ware ware) {
        boolean returnBoolean = false;

        if (warenAusgabe.IstLeer()) {
            if (!lager.SearchWarenBehaelter(ware).IstVorratLeer()) {
                if (geldBoerse.GetBalance() >= ware.GetPreis()) {

                    lager.SearchWarenBehaelter(ware).EntferneEins();
                    geldBoerse.ZieheGeldAb(ware.GetPreis());
                    warenAusgabe.GebeAus(ware);

                    UpdateValues();
                }
                else
                    SetErrorMessage(Constants.ErrorMSGs.ErrorOnBalanceTooLow);
            }
            else
                SetErrorMessage(Constants.ErrorMSGs.ErrorOnLagerEmpty);
        }
        else
            SetErrorMessage(Constants.ErrorMSGs.ErrorOnFullAusgabe);

        return returnBoolean;
    }



    private static boolean CreateGUI () {
        JFrame window = null;
        try {
            window = new JFrame(Constants.WindowComponents.WindowName);

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(1000,800);
            //window.setResizable(false);
            guiContainer.setWindow(window);

            JPanel body = new JPanel();
            body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
            body.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

                JPanel header = new JPanel(new BorderLayout());
                header.setMaximumSize(new Dimension(1000, 70));

                    JLabel ueberschrift = new JLabel(Constants.WindowComponents.UeberschriftText);
                    ueberschrift.setFont(new Font("Bold", Font.PLAIN, 18));

                    JButton geldEinwurf = new JButton(Constants.WindowComponents.Geldeinwurf);
                    geldEinwurf.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            OpenGeldEinwurfWindow();
                        }
                    });

                header.add(ueberschrift, BorderLayout.WEST);
                header.add(geldEinwurf, BorderLayout.EAST);

                JPanel kundenBalancePanel = new JPanel(new BorderLayout());
                kundenBalancePanel.setMaximumSize(new Dimension(1000, 70));
                JTextField kundenBalanceField = new JTextField();
                kundenBalanceField.setEditable(false);
                kundenBalanceField.setColumns(10);
                kundenBalanceField.setBorder(new CompoundBorder(
                    kundenBalanceField.getBorder(),
                    BorderFactory.createEmptyBorder(0,5,0,5)));
                guiContainer.setKundeBalanceAnzeige(kundenBalanceField);

                kundenBalancePanel.add(kundenBalanceField, BorderLayout.EAST);

                JPanel getraenkeAuswahl = CreateWarenAuswahl(Getraenk.class);
                JPanel snackAuswahl = CreateWarenAuswahl(Snack.class);

                JPanel errorSlot = new JPanel(new BorderLayout());
                errorSlot.setMaximumSize(new Dimension(1000, 70));
                    errorMsgLabel = new JLabel();
                    errorMsgLabel.setForeground(Color.red);
                    errorSlot.add(errorMsgLabel);

                JPanel rueckgeldAusgabePanel = new JPanel(new BorderLayout());
                rueckgeldAusgabePanel.setMaximumSize(new Dimension(1000, 70));
                    JTextField rueckgeldAusgabeField = new JTextField("0.00€");
                    rueckgeldAusgabeField.setEditable(false);
                    rueckgeldAusgabeField.setColumns(10);
                    rueckgeldAusgabeField.setBorder(new CompoundBorder(
                    rueckgeldAusgabeField.getBorder(),
                    BorderFactory.createEmptyBorder(0,5,0,5)));
                    guiContainer.setRueckGeldAusgabe(rueckgeldAusgabeField);

                    rueckgeldAusgabePanel.add(rueckgeldAusgabeField, BorderLayout.EAST);

                JPanel rueckgeldLeerenPanel = new JPanel(new BorderLayout());
                rueckgeldLeerenPanel.setMaximumSize(new Dimension(1000, 70));
                    JButton leereRueckgeldButton = new JButton("Rückgeld Leeren");
                    leereRueckgeldButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (rueckgeldAusgabe.IstLeer()) {
                                SetErrorMessage(Constants.ErrorMSGs.ErrorOnEmptyAusgabe);
                            }
                            else {
                                boolean returnSuccess = rueckgeldAusgabe.LeereAusgabe();

                                if (!returnSuccess) {
                                    SetErrorMessage(Constants.ErrorMSGs.ErrorGenericError);
                                }
                                UpdateValues();
                            }
                        }
                    });

                    JButton getRueckgeldButton  = new JButton("Rückgeld Ausgeben");
                    getRueckgeldButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (rueckgeldAusgabe.IstLeer())
                                rueckgeldAusgabe.GebeAus(geldBoerse.GetBalance());
                            else
                                rueckgeldAusgabe.GebeAus(rueckgeldAusgabe.GetAusgabe() + geldBoerse.GetBalance());

                            geldBoerse.ZieheGeldAb(geldBoerse.GetBalance());
                            UpdateValues();
                        }
                    });

                rueckgeldLeerenPanel.add(getRueckgeldButton, BorderLayout.WEST);
                rueckgeldLeerenPanel.add(leereRueckgeldButton, BorderLayout.EAST);

                JPanel footer = new JPanel(new BorderLayout());
                footer.setMaximumSize(new Dimension(1000, 70));
                    JTextField warenAusgabeListe = new JTextField();
                    warenAusgabeListe.setEditable(false);
                    warenAusgabeListe.setBorder(new CompoundBorder(
                            warenAusgabeListe.getBorder(),
                            BorderFactory.createEmptyBorder(0,5,0,5)));
                    guiContainer.setWarenAusgabe(warenAusgabeListe);

                    JButton warenAusgabeLeerenButton = new JButton("Ausgabe leeren");
                    warenAusgabeLeerenButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            boolean leereAusgabeResult = warenAusgabe.LeereAusgabe();

                            if (!leereAusgabeResult) {
                                SetErrorMessage(Constants.ErrorMSGs.ErrorOnEmptyAusgabe);
                            }
                            else
                                UpdateValues();
                        }
                    });
                    warenAusgabeLeerenButton.setBorder(new CompoundBorder(
                            warenAusgabeLeerenButton.getBorder(),
                            BorderFactory.createEmptyBorder(0,5,0,5)));

                    footer.add(warenAusgabeListe);
                    footer.add(warenAusgabeLeerenButton, BorderLayout.EAST);




            body.add(header);
            body.add(kundenBalancePanel);
            body.add(getraenkeAuswahl);
            body.add(snackAuswahl);
            body.add(rueckgeldAusgabePanel);
            body.add(rueckgeldLeerenPanel);
            body.add(footer);
            body.add(errorSlot);
            window.getContentPane().add(body);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return window != null;

    }

    private static void OpenGeldEinwurfWindow() {
        guiContainer.getWindow().setEnabled(false);

        JFrame geldEinwurfWindow = new JFrame(Constants.WindowComponents.Geldeinwurf);
        geldEinwurfWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                guiContainer.getWindow().setEnabled( true);
            }
        });

        geldEinwurfWindow.setSize(800,640);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));

            JPanel header = new JPanel(new BorderLayout());
            header.setMaximumSize(new Dimension(1000, 70));
                JLabel ueberschrift = new JLabel(Constants.WindowComponents.Geldeinwurf);
                ueberschrift.setFont(new Font("Bold", Font.PLAIN, 18));
            header.add(ueberschrift);



        body.add(header);
        body.add(CreateGeldAuswahl(Constants.Gelder.geldMuenzListe));
        body.add(CreateGeldAuswahl(Constants.Gelder.geldScheinListe));
        geldEinwurfWindow.getContentPane().add(body);

        geldEinwurfWindow.setEnabled(true);
        geldEinwurfWindow.setVisible(true);


    }

    private static JPanel CreateGeldAuswahl (List<Geld> geldListe) {
        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        returnPanel.setBorder(BorderFactory.createEmptyBorder(30,30,0,0));

        for (Geld geld: geldListe) {
            JButton geldButton = new JButton(geld.GetWert() + "€");
            geldButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    geldBoerse.FuegeGeldHinzu(geld.GetWert());
                    UpdateValues();
                }
            });

            returnPanel.add(geldButton);
        }

        return returnPanel;
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

    private static void SetErrorMessage(String errorMSG) {
        errorMsgLabel.setText(errorMSG);
    }

}
