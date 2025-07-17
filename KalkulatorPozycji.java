/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.util.Collection;
import java.util.List;
import static szachy.figury.*;

/**
 *
 * @author Patryk
 */
public class KalkulatorPozycji
        implements Kalkulator {

    public final static KalkulatorPozycji INSTANCE = new KalkulatorPozycji();

    public static KalkulatorPozycji get() {
        return INSTANCE;
    }

    private final static int[] BONUS_CZARNY_PION = {
        0, 0, 0, 0, 0, 0, 0, 0,
        75, 75, 75, 75, 75, 75, 75, 75,
        25, 25, 29, 29, 29, 29, 25, 25,
        5, 5, 10, 55, 55, 10, 5, 5,
        0, 0, 0, 20, 20, 0, 0, 0,
        5, -5, -10, 0, 0, -10, -5, 5,
        5, 10, 10, -20, -20, 10, 10, 5,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    private final static int[] BONUS_BIALY_PION = {
        0, 0, 0, 0, 0, 0, 0, 0,
        5, 10, 10, -20, -20, 10, 10, 5,
        5, -5, -10, 0, 0, -10, -5, 5,
        0, 0, 0, 20, 20, 0, 0, 0,
        5, 5, 10, 55, 55, 10, 5, 5,
        25, 25, 29, 29, 29, 29, 25, 25,
        75, 75, 75, 75, 75, 75, 75, 75,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    private final static int[] BONUS_CZARNY_SKOCZEK = {
        -50, -40, -30, -30, -30, -30, -40, -50,
        -40, -20, 0, 0, 0, 0, -20, -40,
        -30, 0, 10, 15, 15, 10, 0, -30,
        -30, 5, 15, 20, 20, 15, 5, -30,
        -30, 0, 15, 20, 20, 15, 0, -30,
        -30, 5, 10, 15, 15, 10, 5, -30,
        -40, -20, 0, 5, 5, 0, -20, -40,
        -50, -40, -30, -30, -30, -30, -40, -50
    };

    private final static int[] BONUS_BIALY_SKOCZEK = {
        -50, -40, -30, -30, -30, -30, -40, -50,
        -40, -20, 0, 5, 5, 0, -20, -40,
        -30, 5, 10, 15, 15, 10, 5, -30,
        -30, 0, 15, 20, 20, 15, 0, -30,
        -30, 5, 15, 20, 20, 15, 5, -30,
        -30, 0, 10, 15, 15, 10, 0, -30,
        -40, -20, 0, 0, 0, 0, -20, -40,
        -50, -40, -30, -30, -30, -30, -40, -50,};

    private final static int[] BONUS_CZARNY_GONIEC = {
        -20, -10, -10, -10, -10, -10, -10, -20,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 5, 5, 10, 10, 5, 5, -10,
        -10, 0, 10, 10, 10, 10, 0, -10,
        -10, 10, 10, 10, 10, 10, 10, -10,
        -10, 5, 0, 0, 0, 0, 5, -10,
        -20, -10, -10, -10, -10, -10, -10, -20
    };

    private final static int[] BONUS_BIALY_GONIEC = {
        -20, -10, -10, -10, -10, -10, -10, -20,
        -10, 5, 0, 0, 0, 0, 5, -10,
        -10, 10, 10, 10, 10, 10, 10, -10,
        -10, 0, 10, 10, 10, 10, 0, -10,
        -10, 5, 5, 10, 10, 5, 5, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -20, -10, -10, -10, -10, -10, -10, -20,};

    private final static int[] BONUS_CZARNA_WIEZA = {
        0, 0, 0, 0, 0, 0, 0, 0,
        5, 20, 20, 20, 20, 20, 20, 5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        0, 0, 0, 5, 5, 0, 0, 0
    };

    private final static int[] BONUS_BIALA_WIEZA = {
        0, 0, 0, 5, 5, 0, 0, 0,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        5, 20, 20, 20, 20, 20, 20, 5,
        0, 0, 0, 0, 0, 0, 0, 0,};

    private final static int[] BONUS_CZARNY_HETMAN = {
        -20, -10, -10, -5, -5, -10, -10, -20,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -5, 0, 5, 5, 5, 5, 0, -5,
        0, 0, 5, 5, 5, 5, 0, -5,
        -10, 5, 5, 5, 5, 5, 0, -10,
        -10, 0, 5, 0, 0, 0, 0, -10,
        -20, -10, -10, -5, -5, -10, -10, -20
    };

    private final static int[] BONUS_BIALY_HETMAN = {
        -20, -10, -10, -5, -5, -10, -10, -20,
        -10, 0, 5, 0, 0, 0, 0, -10,
        -10, 5, 5, 5, 5, 5, 0, -10,
        0, 0, 5, 5, 5, 5, 0, -5,
        0, 0, 5, 5, 5, 5, 0, -5,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -20, -10, -10, -5, -5, -10, -10, -20
    };

    private final static int[] BONUS_CZARNY_KROL = {
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -20, -30, -30, -40, -40, -30, -30, -20,
        -10, -20, -20, -20, -20, -20, -20, -10,
        20, 20, 0, 0, 0, 0, 20, 20,
        20, 30, 10, 0, 0, 10, 30, 20
    };

    private final static int[] BONUS_BIALY_KROL = {
        20, 30, 10, 0, 0, 10, 30, 20,
        20, 20, 0, 0, 0, 0, 20, 20,
        -10, -20, -20, -20, -20, -20, -20, -10,
        -20, -30, -30, -40, -40, -30, -30, -20,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30
    };

    public KalkulatorPozycji() {

    }

    @Override
    public int zliczacz(char[][] ustawienie, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, boolean wykonanaRochB, boolean wykonanaRochC,
            int kol, int glebia, Collection<figury[]> biale_ruchy, Collection<figury[]> czarne_ruchy) {
        return szachmat(true, ustawienie, przelotcan, kol, glebia)
                + dokonano_roszady(true, wykonanaRochB, wykonanaRochC)
                + pionkiS(true, ustawienie)
                + ruchy_zbijajace(biale_ruchy)
                - szachmat(false, ustawienie, przelotcan, kol, glebia)
                - dokonano_roszady(false, wykonanaRochB, wykonanaRochC)
                - pionkiS(false, ustawienie)
                - ruchy_zbijajace(czarne_ruchy)
                + wartosc_bierek(ustawienie)
                + mobilnosc(biale_ruchy, czarne_ruchy);
    }

    private int wartosc_bierek(char[][] ustawienie) {
        int wartosc = 0;
        int gonceB = 0;
        int gonceC = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                // System.out.println(x*8+y+"|"+ustawienie[x][y]);
                switch (ustawienie[x][y]) {
                    case 'p':
                        wartosc = wartosc - (100 + BONUS_CZARNY_PION[x * 8 + y]);
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_PION[x+(7*(7-y))] + "]");
                        break;
                    case 'n':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_SKOCZEK[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (300 + BONUS_CZARNY_SKOCZEK[x * 8 + y]);
                        break;
                    case 'b':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_GONIEC[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (330 + BONUS_CZARNY_GONIEC[x * 8 + y]);
                        gonceC = gonceC + 1;
                        break;
                    case 'r':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNA_WIEZA[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (500 + BONUS_CZARNA_WIEZA[x * 8 + y]);
                        break;
                    case 'q':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_HETMAN[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (900 + BONUS_CZARNY_HETMAN[x * 8 + y]);
                        break;
                    case 'k':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_KROL[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (10000 + BONUS_CZARNY_KROL[x * 8 + y]);
                        break;
                    case 'P':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_PION[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (100 + BONUS_BIALY_PION[x * 8 + y]);
                        break;
                    case 'N':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_SKOCZEK[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (300 + BONUS_BIALY_SKOCZEK[x * 8 + y]);
                        break;
                    case 'B':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_GONIEC[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (330 + BONUS_BIALY_GONIEC[x * 8 + y]);
                        gonceB = gonceB + 1;
                        break;
                    case 'R':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALA_WIEZA[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (500 + BONUS_BIALA_WIEZA[x * 8 + y]);
                        break;
                    case 'Q':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_HETMAN[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (900 + BONUS_BIALY_HETMAN[x * 8 + y]);
                        break;
                    case 'K':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_KROL[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (10000 + BONUS_BIALY_KROL[x * 8 + y]);
                        break;
                    default:
                        //////System.out.println("[" + 0 + "]");
                        break;
                }
            }//System.out.println();
        }
        //System.out.println("-----------------");
        return wartosc + (gonceB >= 2 ? 25 : 0) - (gonceC >= 2 ? 25 : 0);
    }

    private int szachmat(boolean strona, char[][] pozycja, boolean przelot_can, int przelot, int glebia) {
        int[] krol = new int[2];
        if (RuchZagrozenie_kontrola.szach((pozycja), !strona)) {
            for (byte i = 0; i < 8; i++) {
                for (byte j = 0; j < 8; j++) {
                    if ((pozycja[i][j] == 'k' && strona)
                            || (pozycja[i][j] == 'K' && !strona)) {
                        krol[0] = i;
                        krol[1] = j;
                    }
                }
            }
            return !SzachMatPatKontrola.uciekaj(pozycja, !strona, krol)
                    && !SzachMatPatKontrola.zastaw(pozycja, !strona, Wspomagacz.znajdzklopot(pozycja, !strona), krol, przelot_can)
                    && !SzachMatPatKontrola.znajdzodsiecz(pozycja, !strona, Wspomagacz.znajdzklopot(pozycja, !strona), przelot, przelot_can)
                    ? glebia != 0 ? 1000000 * glebia : 1000000 : 45;

        }
        ////System.out.printlnln("Wchodzi2");
        return 0;
    }

    private int dokonano_roszady(boolean strona, boolean wykonanaB, boolean wykonanaC) {
        
        if (strona) {
            if (wykonanaB) {
                //////System.out.println(ROSZADA+"+");
                return 25;
            }
        } else {
            if (wykonanaC) {
                //////System.out.println(ROSZADA+"+");
                return 25;
            }
        }
        ////System.out.printlnln("Wchodzi3");
        return 0;
    }

    private int pionkiS(boolean strona, char[][] pozycja) {
////System.out.printlnln("Wchodzi5");
        return pozycja_PION.punktacja(strona, (pozycja));
    }

    /* private int wiezeS(boolean strona, char[][] ustawienie) {
        pozycja_WIEZA w = new pozycja_WIEZA();
        ////System.out.printlnln("Wchodzi6");
        return w.punktacja(strona, (ustawienie)) - w.punktacja(!strona, (ustawienie));
    }*/
    private int mobilnosc(Collection<figury[]> swoje, Collection<figury[]> wrogie) {
        return ((5 * (int) ((swoje.size() * 10.0f) / wrogie.size()))
                - (5 * (int) ((wrogie.size() * 10.0f) / swoje.size())));
    }

    /* private int save_king(char[][] ustawienie, boolean strona, boolean przelotcan,
            boolean RochB, boolean RochC, boolean wl, boolean wr, boolean bl, boolean br, byte kol) {
        analizator_odleglosci save = Bezpieczenstwo_krol.get().zliczpozycje(ustawienie, strona, przelotcan,
                RochB, RochC, wl, wr, bl, br, kol);
        return save.getWartosc();
    }*/
    private int ruchy_zbijajace(Collection<figury[]> lista) {
        ////System.out.printlnln("Wchodzi7");
        int licznik_ataku = 0;

        if (!lista.isEmpty()) {
            for (figury[] ruch : lista) {
                if (ruch[1] != figury.pustka) {
                    if (wartosc(ruch[0]) <= wartosc(ruch[1])) {
                        licznik_ataku++;
                    }
                }

            }
        }

        //////System.out.printlnSystem.out.print(licznik_ataku*BICIA_BONUS);
        int BICIA_BONUS = 1;
        return (licznik_ataku * BICIA_BONUS);
    }

    private int wartosc(figury f) {
        switch (f) {
            case BGoniec:
            case CGoniec:
            case Goniec:
                return 350;
            case Hetman:
            case BHetman:
            case CHetman:
                return 900;
            case Krol:
            case BKrol:
            case CKrol:
                return 10000;
            case Pion:
            case BPion:
            case CPion:
                return 100;
            case Skoczek:
            case BSkoczek:
            case CSkoczek:
                return 300;
            case Wieza:
            case BWieza:
            case CWieza:
                return 500;
            default:
                return 0;
        }

    }

}
