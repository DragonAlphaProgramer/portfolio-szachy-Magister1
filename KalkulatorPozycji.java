/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.util.Collection;

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

    private final int[] BONUS_CZARNY_PION = {
        0, 0, 0, 0, 0, 0, 0, 0,
        5, 10, 10, -20, -20, 10, 10, 5,
        5, -5, -10, 0, 0, -10, -5, 5,
        0, 0, 0, 20, 20, 0, 0, 0,
        5, 5, 10, 55, 55, 10, 5, 5,
        25, 25, 29, 29, 29, 29, 25, 25,
        75, 75, 75, 75, 75, 75, 75, 75,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    private final int[] BONUS_BIALY_PION = {
        0, 0, 0, 0, 0, 0, 0, 0,
        75, 75, 75, 75, 75, 75, 75, 75,
        25, 25, 29, 29, 29, 29, 25, 25,
        5, 5, 10, 55, 55, 10, 5, 5,
        0, 0, 0, 20, 20, 0, 0, 0,
        5, -5, -10, 0, 0, -10, -5, 5,
        5, 10, 10, -20, -20, 10, 10, 5,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    private final int[] BONUS_CZARNY_SKOCZEK = {
        -50, -40, -30, -30, -30, -30, -40, -50,
        -40, -20, 0, 5, 5, 0, -20, -40,
        -30, 5, 10, 15, 15, 10, 5, -30,
        -30, 0, 15, 20, 20, 15, 0, -30,
        -30, 5, 15, 20, 20, 15, 5, -30,
        -30, 0, 10, 15, 15, 10, 0, -30,
        -40, -20, 0, 0, 0, 0, -20, -40,
        -50, -40, -30, -30, -30, -30, -40, -50,};

    private final int[] BONUS_BIALY_SKOCZEK = {
        -50, -40, -30, -30, -30, -30, -40, -50,
        -40, -20, 0, 0, 0, 0, -20, -40,
        -30, 0, 10, 15, 15, 10, 0, -30,
        -30, 5, 15, 20, 20, 15, 5, -30,
        -30, 0, 15, 20, 20, 15, 0, -30,
        -30, 5, 10, 15, 15, 10, 5, -30,
        -40, -20, 0, 5, 5, 0, -20, -40,
        -50, -40, -30, -30, -30, -30, -40, -50};

    private final int[] BONUS_CZARNY_GONIEC = {
        -20, -10, -10, -10, -10, -10, -10, -20,
        -10, 5, 0, 0, 0, 0, 5, -10,
        -10, 10, 10, 10, 10, 10, 10, -10,
        -10, 0, 10, 10, 10, 10, 0, -10,
        -10, 5, 5, 10, 10, 5, 5, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -20, -10, -10, -10, -10, -10, -10, -20,};

    private final int[] BONUS_BIALY_GONIEC = {
        -20, -10, -10, -10, -10, -10, -10, -20,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 5, 5, 10, 10, 5, 5, -10,
        -10, 0, 10, 10, 10, 10, 0, -10,
        -10, 10, 10, 10, 10, 10, 10, -10,
        -10, 5, 0, 0, 0, 0, 5, -10,
        -20, -10, -10, -10, -10, -10, -10, -20};

    private final int[] BONUS_CZARNA_WIEZA = {
        0, 0, 0, 5, 5, 0, 0, 0,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        5, 20, 20, 20, 20, 20, 20, 5,
        0, 0, 0, 0, 0, 0, 0, 0,};

    private final int[] BONUS_BIALA_WIEZA = {
        0, 0, 0, 0, 0, 0, 0, 0,
        5, 20, 20, 20, 20, 20, 20, 5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        -5, 0, 0, 0, 0, 0, 0, -5,
        0, 0, 0, 5, 5, 0, 0, 0};

    private final int[] BONUS_CZARNY_HETMAN = {
        -20, -10, -10, -5, -5, -10, -10, -20,
        -10, 0, 5, 0, 0, 0, 0, -10,
        -10, 5, 5, 5, 5, 5, 0, -10,
        0, 0, 5, 5, 5, 5, 0, -5,
        0, 0, 5, 5, 5, 5, 0, -5,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -20, -10, -10, -5, -5, -10, -10, -20
    };

    private final int[] BONUS_BIALY_HETMAN = {
        -20, -10, -10, -5, -5, -10, -10, -20,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -5, 0, 5, 5, 5, 5, 0, -5,
        0, 0, 5, 5, 5, 5, 0, -5,
        -10, 5, 5, 5, 5, 5, 0, -10,
        -10, 0, 5, 0, 0, 0, 0, -10,
        -20, -10, -10, -5, -5, -10, -10, -20
    };

    private final int[] BONUS_CZARNY_KROL = {
        20, 30, 10, 0, 0, 10, 30, 20,
        20, 20, 0, 0, 0, 0, 20, 20,
        -10, -20, -20, -20, -20, -20, -20, -10,
        -20, -30, -30, -40, -40, -30, -30, -20,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30
    };

    private final int[] BONUS_BIALY_KROL = {
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -20, -30, -30, -40, -40, -30, -30, -20,
        -10, -20, -20, -20, -20, -20, -20, -10,
        20, 20, 0, 0, 0, 0, 20, 20,
        20, 30, 10, 0, 0, 10, 30, 20
    };

    public KalkulatorPozycji() {
        /*for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
        KalkulatorPozycji.ustawienie[x][y] = ustawienie[x][y];
            }}
        KalkulatorPozycji.tura_rywala = tura_rywala;
        KalkulatorPozycji.przelotcan = przelotcan;
        KalkulatorPozycji.bleft = bleft;
        KalkulatorPozycji.bright = bright;
        KalkulatorPozycji.wleft = wleft;
        KalkulatorPozycji.wright = wright;
        KalkulatorPozycji.roszadaB = roszadaB;
        KalkulatorPozycji.roszadaC = roszadaC;
        KalkulatorPozycji.wykonanaRochB = wykonanaRochB;
        KalkulatorPozycji.wykonanaRochC = wykonanaRochC;
        KalkulatorPozycji.glebia = glebia;
        KalkulatorPozycji.kol = kol;*/

    }

    @Override
    public int zliczacz(char[][] ustawienie, boolean tura_rywala, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, boolean wykonanaRochB, boolean wykonanaRochC,
            int glebia, int kol) {
      /*  System.out.println("");
        System.out.println(("White Mobility : " + mobilnosc(ustawienie, true, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol)
                + "\n")
                + "White kingThreats : " + szachmat(true, ustawienie, glebia, przelotcan, kol) + "\n"
                + "White attacks : " + ruchy_zbijajace(ustawienie, true, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol) + "\n"
                + "White castle : " + dokonano_roszady(true, wykonanaRochB, wykonanaRochC) + "\n"
                + "White pawnStructure : " + pionkiS(true, ustawienie) + "\n"
                + "---------------------\n"
                + "Black Mobility : " + mobilnosc(ustawienie, false, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol) + "\n"
                + "Black kingThreats : " + szachmat(false, ustawienie, glebia, przelotcan, kol) + "\n"
                + "Black attacks : " + ruchy_zbijajace(ustawienie, false, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol) + "\n"
                + "Black castle : " + dokonano_roszady(false, wykonanaRochB, wykonanaRochC) + "\n"
                + "Black pawnStructure : " + pionkiS(false, ustawienie) + "\n"
                + "PieceEval Total : " + wartosc_bierek(ustawienie) + "\n" + "\n"
                + "kalkulator:" + (punktacja(ustawienie, true, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC,
                        wykonanaRochB, wykonanaRochC, glebia, kol)
                - punktacja(ustawienie, false, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC,
                        wykonanaRochB, wykonanaRochC, glebia, kol)
                + wartosc_bierek(ustawienie)));*/
        return punktacja(ustawienie, true, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC,
                wykonanaRochB, wykonanaRochC, glebia, kol)
                - punktacja(ustawienie, false, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC,
                        wykonanaRochB, wykonanaRochC, glebia, kol)
                + wartosc_bierek(ustawienie);
    }

    private int punktacja(char[][] ustawienie, boolean strona, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, boolean wykonanaRochB, boolean wykonanaRochC,
            int glebia, int kol) {
        return szachmat(strona, ustawienie, glebia, przelotcan, kol)
                + dokonano_roszady(strona, wykonanaRochB, wykonanaRochC)
                + mobilnosc(ustawienie, strona, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol)
                + pionkiS(strona, ustawienie)
                + ruchy_zbijajace(ustawienie, strona, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol);
    }

    private int wartosc_bierek(char[][] ustawienie) {
        int wartosc = 0;
        int gonceB = 0;
        int gonceC = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
               // System.out.println(y+(8*(7-x))+"|"+ustawienie[x][y]);
                switch (ustawienie[x][y]) {
                    case 'p':
                        wartosc = wartosc - (100 + BONUS_CZARNY_PION[y+(8*(7-x))]);
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_PION[x+(7*(7-y))] + "]");
                        break;
                    case 'n':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_SKOCZEK[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (300 + BONUS_CZARNY_SKOCZEK[y+(8*(7-x))]);
                        break;
                    case 'b':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_GONIEC[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (330 + BONUS_CZARNY_GONIEC[y+(8*(7-x))]);
                        gonceC = gonceC + 1;
                        break;
                    case 'r':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNA_WIEZA[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (500 + BONUS_CZARNA_WIEZA[y+(8*(7-x))]);
                        break;
                    case 'q':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_HETMAN[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (900 + BONUS_CZARNY_HETMAN[y+(8*(7-x))]);
                        break;
                    case 'k':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_KROL[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (10000 + BONUS_CZARNY_KROL[y+(8*(7-x))]);
                        break;
                    case 'P':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_PION[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (100 + BONUS_BIALY_PION[y+(8*(7-x))]);
                        break;
                    case 'N':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_SKOCZEK[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (300 + BONUS_BIALY_SKOCZEK[y+(8*(7-x))]);
                        break;
                    case 'B':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_GONIEC[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (330 + BONUS_BIALY_GONIEC[y+(8*(7-x))]);
                        gonceB = gonceB + 1;
                        break;
                    case 'R':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALA_WIEZA[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (500 + BONUS_BIALA_WIEZA[y+(8*(7-x))]);
                        break;
                    case 'Q':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_HETMAN[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (900 + BONUS_BIALY_HETMAN[y+(8*(7-x))]);
                        break;
                    case 'K':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_KROL[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (10000 + BONUS_BIALY_KROL[y+(8*(7-x))]);
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

    private int szachmat(boolean strona, char[][] ustawienie, int glebia, boolean przelotcan, int kol) {
        int[] krol = new int[2];
        if (RuchZagrozenie_kontrola.szach((ustawienie), !strona)) {
            char[][] backup = new char[8][8];
            char[][] backup1 = new char[8][8];
            char[][] backup2 = new char[8][8];
            char[][] backup3 = new char[8][8];
            for (byte i = 0; i < 8; i++) {
                for (byte j = 0; j < 8; j++) {
                    backup[i][j] = (ustawienie)[i][j];
                    backup1[i][j] = (ustawienie)[i][j];
                    backup2[i][j] = (ustawienie)[i][j];
                    backup3[i][j] = (ustawienie)[i][j];
                    if ((ustawienie[i][j] == 'k' && strona)
                            || (ustawienie[i][j] == 'K' && !strona)) {
                        krol[0] = i;
                        krol[1] = j;
                    }
                }
            }
            int SZACH = 45;
            int MAT = 1000000;
            return !SzachMatPatKontrola.uciekaj(backup1, !strona, krol)
                    && !SzachMatPatKontrola.zastaw(backup2, !strona, Wspomagacz.znajdzklopot(backup, !strona), krol, przelotcan)
                    && !SzachMatPatKontrola.znajdzodsiecz(backup3, !strona, Wspomagacz.znajdzklopot(backup, !strona), kol, przelotcan)
                    ? glebia != 0 ? MAT * glebia : MAT : SZACH;

        }
        ////System.out.printlnln("Wchodzi2");
        return 0;
    }

    private int dokonano_roszady(boolean strona, boolean wykonanaRochB, boolean wykonanaRochC) {
        int ROSZADA = 25;
        if (strona) {
            if (wykonanaRochB) {
                //////System.out.println(ROSZADA+"+");
                return ROSZADA;
            }
        } else {
            if (wykonanaRochC) {
                //////System.out.println(ROSZADA+"+");
                return ROSZADA;
            }
        }
        ////System.out.printlnln("Wchodzi3");
        return 0;
    }

    private int pionkiS(boolean strona, char[][] ustawienie) {
////System.out.printlnln("Wchodzi5");
        return pozycja_PION.punktacja(strona, (ustawienie));
    }

   /* private int wiezeS(boolean strona, char[][] ustawienie) {
        pozycja_WIEZA w = new pozycja_WIEZA();
        ////System.out.printlnln("Wchodzi6");
        return w.punktacja(strona, (ustawienie)) - w.punktacja(!strona, (ustawienie));
    }*/

    private int mobilnosc(char[][] ustawienie, boolean strona, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, int kol) {
        ////System.out.printlnln("Wchodzi4");

        ////System.out.printlnln(stosunek(strona)*RUCHY_BONUS);
        int RUCHY_BONUS = 5;
        return RUCHY_BONUS * stosunek(ustawienie, strona, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol);
    }

    private int stosunek(char[][] ustawienie, boolean b, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright, boolean roszadaB, boolean roszadaC, int kol) {
        int swoje = (Generator.generuj_posuniecia(ustawienie, b, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol, true).size());
        int wrogie = Generator.generuj_posuniecia(ustawienie, !b, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol, true).size();
        //System.out.println((int) ((swoje*10.0f) / wrogie*1.0f));
        if (RuchZagrozenie_kontrola.szach((ustawienie), !b)) {
            swoje++;
        }
        if (RuchZagrozenie_kontrola.szach((ustawienie), b)) {
            wrogie++;
        }
        return (int) ((swoje * 10.0f) / wrogie);

    }

   /* private int save_king(char[][] ustawienie, boolean strona, boolean przelotcan,
            boolean RochB, boolean RochC, boolean wl, boolean wr, boolean bl, boolean br, byte kol) {
        analizator_odleglosci save = Bezpieczenstwo_krol.get().zliczpozycje(ustawienie, strona, przelotcan,
                RochB, RochC, wl, wr, bl, br, kol);
        return save.getWartosc();
    }*/

    private int ruchy_zbijajace(char[][] ustawienie, boolean strona, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright, boolean roszadaB, boolean roszadaC, int kol) {
        ////System.out.printlnln("Wchodzi7");
        Collection<Ruch> lista = Generator.generuj_posuniecia((ustawienie), strona, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol, false);
        int licznik_ataku = 0;

        if (!lista.isEmpty()) {
            for (Ruch ruch : lista) {
                if (ruch.korzystnosc_bicia != ' ') {
                    int wartosc_atakujacego = wartosc(ruch.kolejnosc);
                    int wartosc_atakowanego = wartosc(ruch.korzystnosc_bicia);
                    if (wartosc_atakujacego <= wartosc_atakowanego) {
                        licznik_ataku++;
                    }
                }
            }
            if (RuchZagrozenie_kontrola.szach((ustawienie), !strona)) {
                licznik_ataku++;
            }
        }

        //////System.out.printlnSystem.out.print(licznik_ataku*BICIA_BONUS);
        int BICIA_BONUS = 1;
        return (licznik_ataku * BICIA_BONUS);
    }

    private int wartosc(char f) {
        switch (f) {
            case 'b':
            case 'B':
                return 350;
            case 'q':
            case 'Q':
                return 900;
            case 'k':
            case 'K':
                return 10000;
            case 'P':
            case 'p':
                return 100;
            case 'n':
            case 'N':
                return 300;
            case 'r':
            case 'R':
                return 500;
            default:
                return 0;
        }

    }

    /*  private char pozyskaj_figure(Ruch.figura ruch) {
        switch (ruch) {
            case Pion:
                if (tura_rywala == true) {
                    return 'P';
                } else {
                    return 'p';
                }
            case Skoczek:
                if (tura_rywala == true) {
                    return 'N';
                } else {
                    return 'n';
                }
            case Goniec:
                if (tura_rywala == true) {
                    return 'B';
                } else {
                    return 'b';
                }
            case Wieza:
                if (tura_rywala == true) {
                    return 'R';
                } else {
                    return 'r';
                }
            case Hetman:
                if (tura_rywala == true) {
                    return 'Q';
                } else {
                    return 'q';
                }
            case Krol:
                if (tura_rywala == true) {
                    return 'K';
                } else {
                    return 'k';
                }
        }
        return ' ';
    }*/
}