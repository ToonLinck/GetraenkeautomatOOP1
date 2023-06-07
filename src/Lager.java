import Ware.Ware;

import java.util.ArrayList;
import java.util.List;

public class Lager {

    List<WarenBehaelter> warenListe = new ArrayList<WarenBehaelter>();
    public Lager() {}

    public List<WarenBehaelter> getWarenListe() {
        return warenListe;
    }

    public boolean FuegeWareHinzu(Ware nWare) {
        boolean returnBoolean = false;
        WarenBehaelter nWarenBehaelter = new WarenBehaelter(nWare);
        if (!warenListe.contains(nWarenBehaelter)) {
            warenListe.add(nWarenBehaelter);
        }

        return returnBoolean;
    }

    public boolean LoescheWare(Ware ware) {

        return warenListe.remove(SearchWarenBehaelter(ware));

    }

    public boolean FuelleAuf(Ware ware) {
        boolean returnBoolean = false;
        WarenBehaelter warenBehaelter = SearchWarenBehaelter(ware);
        if (warenBehaelter != null) {
                warenBehaelter.SetVorrat(warenBehaelter.GetKapazitaet());
        }
        return returnBoolean;
    }

    public void FuelleAlleAuf() {
        for (WarenBehaelter behaelter: warenListe) {
            FuelleAuf(behaelter.GetWare());
        }
    }
    private WarenBehaelter SearchWarenBehaelter (Ware ware) {
        WarenBehaelter returnWarenBehaelter = null;

        for (WarenBehaelter behaelter: warenListe) {
            if (behaelter.GetWare() == ware) {
                returnWarenBehaelter = behaelter;
            }
        }

        return returnWarenBehaelter;
    }

    class WarenBehaelter {

        private Ware ware;
        private int kapazitaet = Constants.WarenbehaelterGroesse;
        private int vorrat = 0;

        public WarenBehaelter (Ware pWare) {
            ware = pWare;
        }

        public void SetVorrat(int vorrat) {
            vorrat = vorrat;
        }

        public int GetVorrat() {
            return vorrat;
        }

        public int GetKapazitaet() {
            return kapazitaet;
        }

        public boolean IstVorratVoll() {
            return vorrat + ware.GetGroesse() > kapazitaet;
        }

        public boolean IstVorratLeer() {
            return vorrat < ware.GetGroesse();
        }

        public boolean EntferneEins() {
            boolean returnBoolean = false;

            if (!IstVorratLeer()) {
                vorrat -= ware.GetGroesse();
                returnBoolean = true;
            }

            return returnBoolean;
        }

        public Ware GetWare() {
            return ware;
        }

    }

}
