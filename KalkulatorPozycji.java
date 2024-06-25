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

 private final static int[] BONUS_CZARNY_PION = {
            0,  0,  0,  0,  0,  0,  0,  0,
            75, 75, 75, 75, 75, 75, 75, 75,
            25, 25, 29, 29, 29, 29, 25, 25,
            5,  5, 10, 55, 55, 10,  5,  5,
            0,  0,  0, 20, 20,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,
            5, 10, 10,-20,-20, 10, 10,  5,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private final static int[] BONUS_BIALY_PION = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10,-20,-20, 10, 10,  5,
            5, -5,-10,  0,  0,-10, -5,  5,
            0,  0,  0, 20, 20,  0,  0,  0,
            5,  5, 10, 55, 55, 10,  5,  5,
            25, 25, 29, 29, 29, 29, 25, 25,
            75, 75, 75, 75, 75, 75, 75, 75,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private final static int[] BONUS_CZARNY_SKOCZEK = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50
    };

    private final static int[] BONUS_BIALY_SKOCZEK = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50,
    };

    private final static int[] BONUS_CZARNY_GONIEC = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -20,-10,-10,-10,-10,-10,-10,-20
    };

    private final static int[] BONUS_BIALY_GONIEC = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10,-10,-10,-10,-10,-20,
    };

    private final static int[] BONUS_CZARNA_WIEZA = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 20, 20, 20, 20, 20, 20,  5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            0,  0,  0,  5,  5,  0,  0,  0
    };

    private final static int[] BONUS_BIALA_WIEZA = {
            0,  0,  0,  5,  5,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            5, 20, 20, 20, 20, 20, 20,  5,
            0,  0,  0,  0,  0,  0,  0,  0,
    };

    private final static int[] BONUS_CZARNY_HETMAN = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5,  5,  5,  5,  0,-10,
             -5,  0,  5,  5,  5,  5,  0, -5,
              0,  0,  5,  5,  5,  5,  0, -5,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };

    private final static int[] BONUS_BIALY_HETMAN = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -10,  5,  5,  5,  5,  5,  0,-10,
              0,  0,  5,  5,  5,  5,  0, -5,
              0,  0,  5,  5,  5,  5,  0, -5,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };

    private final static int[] BONUS_CZARNY_KROL = {
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -10,-20,-20,-20,-20,-20,-20,-10,
             20, 20,  0,  0,  0,  0, 20, 20,
             20, 30, 10,  0,  0, 10, 30, 20
    };

    private final static int[] BONUS_BIALY_KROL = {
             20, 30, 10,  0,  0, 10, 30, 20,
             20, 20,  0,  0,  0,  0, 20, 20,
            -10,-20,-20,-20,-20,-20,-20,-10,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30
    };

    public KalkulatorPozycji() {
        

    }

    @Override
    public int zliczacz(char[][] ustawienie, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, boolean wykonanaRochB, boolean wykonanaRochC,
            int kol, int glebia){
        Collection<Ruch> biale_ruchy = Generator.generuj_posuniecia(ustawienie, true, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol, true);
        Collection<Ruch> czarne_ruchy = Generator.generuj_posuniecia(ustawienie, false, przelotcan, bleft, bright, wleft, wright, roszadaB, roszadaC, kol, true);
        return  szachmat(true,ustawienie,przelotcan,kol,glebia)
                + dokonano_roszady(true,wykonanaRochB, wykonanaRochC)
                + pionkiS(true,ustawienie)
                + ruchy_zbijajace(biale_ruchy)
                - szachmat(false,ustawienie,przelotcan,kol,glebia)
                - dokonano_roszady(false,wykonanaRochB, wykonanaRochC)
                - pionkiS(false,ustawienie)
                - ruchy_zbijajace(czarne_ruchy)
                + wartosc_bierek(ustawienie)
                + mobilnosc(biale_ruchy,czarne_ruchy);
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
                        wartosc = wartosc - (100 + BONUS_CZARNY_PION[x*8+y]);
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_PION[x+(7*(7-y))] + "]");
                        break;
                    case 'n':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_SKOCZEK[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (300 + BONUS_CZARNY_SKOCZEK[x*8+y]);
                        break;
                    case 'b':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_GONIEC[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (330 + BONUS_CZARNY_GONIEC[x*8+y]);
                        gonceC = gonceC + 1;
                        break;
                    case 'r':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNA_WIEZA[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (500 + BONUS_CZARNA_WIEZA[x*8+y]);
                        break;
                    case 'q':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_HETMAN[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (900 + BONUS_CZARNY_HETMAN[x*8+y]);
                        break;
                    case 'k':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_CZARNY_KROL[x+(7*(7-y))] + "]");
                        wartosc = wartosc - (10000 + BONUS_CZARNY_KROL[x*8+y]);
                        break;
                    case 'P':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_PION[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (100 + BONUS_BIALY_PION[x*8+y]);
                        break;
                    case 'N':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_SKOCZEK[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (300 + BONUS_BIALY_SKOCZEK[x*8+y]);
                        break;
                    case 'B':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_GONIEC[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (330 + BONUS_BIALY_GONIEC[x*8+y]);
                        gonceB = gonceB + 1;
                        break;
                    case 'R':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALA_WIEZA[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (500 + BONUS_BIALA_WIEZA[x*8+y]);
                        break;
                    case 'Q':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_HETMAN[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (900 + BONUS_BIALY_HETMAN[x*8+y]);
                        break;
                    case 'K':
                        //System.out.print("[" + (x + 1) + "|" + (char) ('A' + y) + " " + (ustawienie)[x+(7*(7-y))] + BONUS_BIALY_KROL[x+(7*(7-y))] + "]");
                        wartosc = wartosc + (10000 + BONUS_BIALY_KROL[x*8+y]);
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

    private int szachmat(boolean strona,char[][] pozycja, boolean  przelot_can,int przelot, int glebia) {
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
            int SZACH = 45;
            int MAT = 1000000;
            return !SzachMatPatKontrola.uciekaj(pozycja, !strona, krol)
                    && !SzachMatPatKontrola.zastaw(pozycja, !strona, Wspomagacz.znajdzklopot(pozycja, !strona), krol, przelot_can)
                    && !SzachMatPatKontrola.znajdzodsiecz(pozycja, !strona, Wspomagacz.znajdzklopot(pozycja, !strona), przelot, przelot_can)
                    ? glebia != 0 ? MAT * glebia : MAT : SZACH;

        }
        ////System.out.printlnln("Wchodzi2");
        return 0;
    }

    private int dokonano_roszady(boolean strona,boolean wykonanaB,boolean wykonanaC) {
        int ROSZADA = 25;
        if (strona) {
            if (wykonanaB) {
                //////System.out.println(ROSZADA+"+");
                return ROSZADA;
            }
        } else {
            if (wykonanaC) {
                //////System.out.println(ROSZADA+"+");
                return ROSZADA;
            }
        }
        ////System.out.printlnln("Wchodzi3");
        return 0;
    }

    private int pionkiS(boolean strona,char[][] pozycja) {
////System.out.printlnln("Wchodzi5");
        return pozycja_PION.punktacja(strona, (pozycja));
    }

   /* private int wiezeS(boolean strona, char[][] ustawienie) {
        pozycja_WIEZA w = new pozycja_WIEZA();
        ////System.out.printlnln("Wchodzi6");
        return w.punktacja(strona, (ustawienie)) - w.punktacja(!strona, (ustawienie));
    }*/

    private int mobilnosc(Collection<Ruch> swoje, Collection<Ruch> wrogie) {
        return  ((5 *(int)((swoje.size() * 10.0f) / wrogie.size()))-
                (5 *(int)((wrogie.size() * 10.0f) / swoje.size())));
    }

   

   /* private int save_king(char[][] ustawienie, boolean strona, boolean przelotcan,
            boolean RochB, boolean RochC, boolean wl, boolean wr, boolean bl, boolean br, byte kol) {
        analizator_odleglosci save = Bezpieczenstwo_krol.get().zliczpozycje(ustawienie, strona, przelotcan,
                RochB, RochC, wl, wr, bl, br, kol);
        return save.getWartosc();
    }*/

    private int ruchy_zbijajace(Collection<Ruch> lista) {
        ////System.out.printlnln("Wchodzi7");
        int licznik_ataku = 0;

        if (!lista.isEmpty()) {
            for (Ruch ruch : lista) {
                if(!RuchZagrozenie_kontrola.szach(ruch.chessboard_after, ruch.czybialy)){
                if (ruch.atak) {
                    int wartosc_atakujacego = wartosc(ruch.kolejnosc);
                    int wartosc_atakowanego = ruch.korzystnosc_bicia;
                    if (wartosc_atakujacego <= wartosc_atakowanego) {
                        licznik_ataku++;
                    }
                }
                }
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