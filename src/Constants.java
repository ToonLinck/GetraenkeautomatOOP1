import Ware.*;
import Geld.*;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public class WindowComponents{
        public static final String WindowName = "Getraenkeautomat";
        public static final String UeberschriftText = "Getraenkeautomat";
        public static final String Geldeinwurf = "Geldeinwurf";
    }

    public class ErrorMSGs{

        public static final String ErrorGenericError = "Es ist Etwas schief gelaufen";
        public static final String ErrorOnEmptyAusgabe = "Die Ausgabeklappe ist bereits Leer";
        public static final String ErrorOnFullAusgabe  = "Entleere vorher die Ausgabeklappe";
        public static final String ErrorOnBalanceTooLow = "Budget reicht nicht aus";
        public static final String ErrorOnLagerEmpty = "Dieser Artikel ist nicht mehr vorrätig";

    }


    public static final int WarenbehaelterGroesse = 100;


    public class Waren {
        public static List<Getraenk> getraenkListe = new ArrayList<Getraenk>() {{
            add(new Getraenk("Wasser", 1.20, 10));
            add(new Getraenk("Cola", 1.90,10));
            add(new Getraenk("Fanta", 1.90, 10));
            add(new Getraenk("Sports Drink", 2.10, 10));
        }};

        public static List<Snack> snackListe  = new ArrayList<Snack>() {{
            add(new Snack("Chips", 2.50, 10));
            add(new Snack("Snickers", 1.80, 5));
            add(new Snack("Kinder Schokolade", 1.20, 5));
            add(new Snack("Kaugummi", 0.50, 2));
        }};
    }

    public class Gelder {
        public static List<Geld> geldMuenzListe = new ArrayList<Geld>() {{
            add(new Muenze(0.01));
            add(new Muenze(0.02));
            add(new Muenze(0.05));
            add(new Muenze(0.10));
            add(new Muenze(0.20));
            add(new Muenze(0.50));
            add(new Muenze(1));
            add(new Muenze(2));
        }};
        public static List<Geld> geldScheinListe = new ArrayList<Geld>() {{
            add(new Schein(5));
            add(new Schein(10));
            add(new Schein(20));
            add(new Schein(50));
        }};

    }
}
