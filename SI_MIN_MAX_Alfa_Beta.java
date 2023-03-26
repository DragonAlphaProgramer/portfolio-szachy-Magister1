package szachy;

import java.util.ArrayList;
import java.util.Collection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Patryk
 */
public class SI_MIN_MAX_Alfa_Beta implements Strategia {

    ArrayList<Ruch> kombinacja = new ArrayList<>();
    int pozycje = 0;
    int all_position = 0;
    int licznik, maxglebia;
    figury[][] pozycja = new figury[8][8];
    figury[][] pozycja_wyjsciowa = new figury[8][8];
    boolean tura_rywala, przelotcan, bleft, bright, wleft, wright,
            kingrochB, kingrochC, didRochB, didRochC;
    int kolumna;
    boolean przerwa;
    boolean zakaz;
    private final boolean wyjsciowa_tura;
    private final Kalkulator wynikowa;

    synchronized private boolean kontrola_pat(char[][] ustawienie, boolean strona, boolean przelotcan) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[] poza_krolewska = new int[2];
                poza_krolewska[0] = j;
                poza_krolewska[1] = i;
                if (ustawienie[i][j] == 'K' || ustawienie[i][j] == 'k') {

                    //System.out.println("wchodzi król "+(ustawienie[i][j]=='K'?"biały":"czarny"));
                    if ((strona && ustawienie[i][j] == 'K')
                            || (!strona && ustawienie[i][j] == 'k')) {
                        //   System.out.println("wchodzi "+ustawienie[i][j]+" "+strona);
                        poza_krolewska[0] = i;
                        poza_krolewska[1] = j;
                        //  System.out.println(ustawienie[poza_krolewska[0]][poza_krolewska[1]]);
                        if (SzachMatPatKontrola.uciekaj(ustawienie, strona, poza_krolewska)) {

                            return false;
                        }
                    }

                } else {

                    if (SzachMatPatKontrola.znajdz_ruch(ustawienie, strona, ustawienie[i][j], poza_krolewska, przelotcan)) {

                        return false;
                    }

                }
            }
        }
        return true;
    }

    public enum figury {
        BKrol, BHetman, BWieza, BGoniec, BSkoczek, BPion,
        CKrol, CHetman, CWieza, CGoniec, CSkoczek, CPion, pustka
    }

    public synchronized static char[][] konwert(figury[][] pozycja) {
        char[][] wynik = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (pozycja[i][j]) {
                    case BKrol:
                        wynik[i][j] = 'K';
                        break;
                    case BHetman:
                        wynik[i][j] = 'Q';
                        break;
                    case BWieza:
                        wynik[i][j] = 'R';
                        break;
                    case BGoniec:
                        wynik[i][j] = 'B';
                        break;
                    case BSkoczek:
                        wynik[i][j] = 'N';
                        break;
                    case BPion:
                        wynik[i][j] = 'P';
                        break;
                    case CKrol:
                        wynik[i][j] = 'k';
                        break;
                    case CHetman:
                        wynik[i][j] = 'q';
                        break;
                    case CWieza:
                        wynik[i][j] = 'r';
                        break;
                    case CGoniec:
                        wynik[i][j] = 'b';
                        break;
                    case CSkoczek:
                        wynik[i][j] = 'n';
                        break;
                    case CPion:
                        wynik[i][j] = 'p';
                        break;
                    case pustka:
                        wynik[i][j] = ' ';
                        break;
                }
            }
        }
        return wynik;
    }

    public SI_MIN_MAX_Alfa_Beta(char[][] ustawienie, boolean tura_rywala, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean kingrochB, boolean kingrochC, boolean dokonano_RB, boolean dokonano_RC,
            int kol, boolean odwrot, int licznik, int glebina) {
        this.licznik = licznik;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (ustawienie[i][j]) {
                    case ' ':
                        pozycja[i][j] = figury.pustka;
                        break;
                    case 'P':
                        pozycja[i][j] = figury.BPion;
                        break;
                    case 'p':
                        pozycja[i][j] = figury.CPion;
                        break;
                    case 'N':
                        pozycja[i][j] = figury.BSkoczek;
                        break;
                    case 'n':
                        pozycja[i][j] = figury.CSkoczek;
                        break;
                    case 'B':
                        pozycja[i][j] = figury.BGoniec;
                        break;
                    case 'b':
                        pozycja[i][j] = figury.CGoniec;
                        break;
                    case 'R':
                        pozycja[i][j] = figury.BWieza;
                        break;
                    case 'r':
                        pozycja[i][j] = figury.CWieza;
                        break;
                    case 'Q':
                        pozycja[i][j] = figury.BHetman;
                        break;
                    case 'q':
                        pozycja[i][j] = figury.CHetman;
                        break;
                    case 'K':
                        pozycja[i][j] = figury.BKrol;
                        break;
                    case 'k':
                        pozycja[i][j] = figury.CKrol;
                        break;
                }
                pozycja_wyjsciowa[i][j] = pozycja[i][j];
            }
        }
        this.tura_rywala = tura_rywala;
        this.wyjsciowa_tura = tura_rywala;
        this.przelotcan = przelotcan;
        this.bleft = bleft;
        this.bright = bright;
        this.wleft = wleft;
        this.wright = wright;
        this.didRochB = dokonano_RB;
        this.didRochC = dokonano_RC;
        this.kolumna = kol;
        this.kingrochB = kingrochB;
        this.kingrochC = kingrochC;
        this.maxglebia = glebina;
        wynikowa = KalkulatorPozycji.get();
    }

    /**
     *
     * @param glebia
     * @param move
     * @param najwieksza
     * @param najmniejsza
     * @return
     */
    public Ruch_wartosc wykonaj(int glebia, final Ruch move, int najwieksza, int najmniejsza) {
        int biezaca_ogolna;
        all_position = all_position + 1;
        byte Nkolumna;
        if (move.kolejnosc == Ruch.figura.Pion && (Math.abs(pozyskajkordkolumna(move.koniec2) - pozyskajkordkolumna(move.start2)) == 2)) {
            Nkolumna = (byte) (pozyskajkordrzad(move.start1));
            this.przelotcan = true;
        } else {
            Nkolumna = 0;
            this.przelotcan = false;
        }
        try {

            if (!RuchZagrozenie_kontrola.szach(konwert(move.chessboard_after), this.tura_rywala)
                    && obecnosc(this.pozycja)) {
                aktualizacja_parametrow(move);
                this.tura_rywala = !this.tura_rywala;
                kombinacja.add(move);
                biezaca_ogolna = (wyjsciowa_tura)
                        ? minimum((glebia - 1), Nkolumna, najwieksza, najmniejsza, move.chessboard_after)
                        : maximum((glebia - 1), Nkolumna, najwieksza, najmniejsza, move.chessboard_after);
                this.tura_rywala = !this.tura_rywala;
                if (wyjsciowa_tura) {
                    setPrzerwa(kontrola_mat(konwert(move.chessboard_after), false, Nkolumna, przelotcan));
                } else {
                    setPrzerwa(kontrola_mat(konwert(move.chessboard_after), true, Nkolumna, przelotcan));
                }

                przywroc_parametry(move);
                setZakaz(false);
                return new Ruch_wartosc(move, biezaca_ogolna);
            } else {
                setZakaz(true);
                System.out.println("err2");
                przywroc_parametry(move);
                return new Ruch_wartosc(move, (!wyjsciowa_tura ? Integer.MAX_VALUE : Integer.MIN_VALUE));
            }
        } catch (Exception e) {
            System.out.println("ERROR POSITION");
            for (int i = 7; i > -1; i--) {
                for (int j = 0; j < 8; j++) {

                    System.out.print("[" + konwert(this.pozycja)[i][j] + "]");
                }
                System.out.println();
            }
            System.out.println(e);
            return new Ruch_wartosc(move, (!wyjsciowa_tura ? Integer.MAX_VALUE : Integer.MIN_VALUE));
        }

    }

    synchronized public boolean isZakaz() {
        return zakaz;
    }

    synchronized public void setZakaz(boolean zakaz) {
        this.zakaz = zakaz;
    }

    synchronized private int maximum(final int glebia, byte kolumna, int biggest, int samllest, figury[][] chessboard) {
        int[] temp1 = {0, 0};
        Collection<Ruch> lista = Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, 2, kolumna, false, ' ', temp1, false);
        if (glebia == 0 || koniec(konwert(chessboard), this.tura_rywala, this.przelotcan, kolumna)
                || lista.isEmpty()) {
            pozycje = pozycje + 1;
            /* for (Ruch r : kombinacja) {
                System.out.print(r.toString() + ",");
            }
            System.out.println("");*/

            return this.wynikowa.zliczacz(chessboard, tura_rywala, przelotcan,
                    bleft, bright, wleft, wright, kingrochB, kingrochC, didRochB, didRochC, glebia, kolumna);
        }
        int tempB = biggest;
        all_position = all_position + lista.size();
        for (final Ruch move : lista) {
            if (!RuchZagrozenie_kontrola.szach(konwert(move.chessboard_after), this.tura_rywala)
                    && obecnosc(move.chessboard_after)) {
                //    System.out.println(move.toString()+ "|"+move.wspolczynnik_bitki);
                byte Nkolumna;
                if (move.kolejnosc == Ruch.figura.Pion && (Math.abs(pozyskajkordkolumna(move.koniec2) - pozyskajkordkolumna(move.start2)) == 2)) {
                    Nkolumna = (byte) (pozyskajkordrzad(move.start1));
                    this.przelotcan = true;
                } else {
                    Nkolumna = 0;
                    this.przelotcan = false;
                }
                aktualizacja_parametrow(move);
                this.tura_rywala = !this.tura_rywala;
                kombinacja.add(move);
                tempB = Math.max(tempB, minimum(((glebia - 1)), Nkolumna,
                        tempB, samllest, move.chessboard_after));
                przywroc_parametry(move);
                this.tura_rywala = !this.tura_rywala;
                kombinacja.remove(kombinacja.size() - 1);
                if (samllest <= tempB) {
                    break;
                }
            }
        }
        return tempB;
    }

    synchronized private int minimum(final int glebia, byte kolumna, int biggest, int smallest, figury[][] chessboard) {
        int[] temp1 = new int[2];
        Collection<Ruch> lista = Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, 2, kolumna, false, ' ', temp1, false);
        if (glebia == 0 || koniec(konwert(chessboard), this.tura_rywala, this.przelotcan, kolumna)
                || lista.isEmpty()) {
            pozycje = pozycje + 1;
            /* for (Ruch r : kombinacja) {
                System.out.print(r.toString() + ",");
            }
            System.out.println("");*/

            return this.wynikowa.zliczacz(chessboard, tura_rywala, przelotcan,
                    bleft, bright, wleft, wright, kingrochB, kingrochC, didRochB, didRochC, glebia, kolumna);
        }
        int tempM = smallest;
        all_position = all_position + lista.size();
        for (final Ruch move : lista) {
            if (!RuchZagrozenie_kontrola.szach(konwert(move.chessboard_after), this.tura_rywala)
                    && obecnosc(move.chessboard_after)) {
                byte Nkolumna;
                // System.out.println(move.toString()+ "|"+move.wspolczynnik_bitki); 
                if (move.kolejnosc == Ruch.figura.Pion && (Math.abs(pozyskajkordkolumna(move.koniec2) - pozyskajkordkolumna(move.start2)) == 2)) {
                    Nkolumna = (byte) (pozyskajkordrzad(move.start1));
                    this.przelotcan = true;
                } else {
                    Nkolumna = 0;
                    this.przelotcan = false;
                }
                aktualizacja_parametrow(move);
                this.tura_rywala = !this.tura_rywala;
                kombinacja.add(move);
                tempM = Math.min(tempM, maximum(((glebia - 1)),
                        Nkolumna, biggest, tempM, move.chessboard_after));
                przywroc_parametry(move);
                kombinacja.remove(kombinacja.size() - 1);
                this.tura_rywala = !this.tura_rywala;
                if (tempM <= biggest) {
                    break;
                }
            }
        }
        return tempM;
    }

    synchronized private boolean obecnosc(figury[][] ustawienie13) {
        byte KB = 0, KC = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (ustawienie13[x][y] == figury.BKrol) {
                    KB++;
                }
                if (ustawienie13[x][y] == figury.CKrol) {
                    KC++;
                }
            }
        }
        return KB == 1 && KC == 1;
    }

    synchronized private boolean koniec(char[][] ustawienie, boolean strona, boolean przelotcan, int kol) {
        int pionB = 0, pionC = 0, lekkieB = 0, lekkieC = 0, ciezkieB = 0, ciezkieC = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (ustawienie[i][j]) {
                    case 'P':
                        pionB++;
                        break;
                    case 'p':
                        pionC++;
                        break;
                    case 'N':
                    case 'B':
                        lekkieB++;
                        break;
                    case 'n':
                    case 'b':
                        lekkieC++;
                        break;
                    case 'R':
                    case 'Q':
                        ciezkieB++;
                        break;
                    case 'r':
                    case 'q':
                        ciezkieC++;
                        break;
                }

            }
        }
        if (pionB < 1 && pionC < 1 && lekkieB < 2 && lekkieC < 2 && ciezkieB < 1 && ciezkieC < 1) {
            return true;
        } else {

            if (RuchZagrozenie_kontrola.szach(ustawienie, strona)) {
                return kontrola_mat(ustawienie, strona, (byte) kol, przelotcan);
            } else {

                return kontrola_pat(ustawienie, strona, przelotcan);
            }
        }
    }

    synchronized private boolean kontrola_mat(char[][] ustawienie, boolean strona, byte kol, boolean przelotcan) {
        int[] krol = new int[2];
        if (RuchZagrozenie_kontrola.szach((ustawienie), strona)) {
            char[][] backup = new char[8][8];
            char[][] backup1 = new char[8][8];
            char[][] backup2 = new char[8][8];
            char[][] backup3 = new char[8][8];
            for (byte i = 0; i < 8; i++) {
                for (byte j = 0; j < 8; j++) {
                    backup[i][j] = ustawienie[i][j];
                    backup1[i][j] = ustawienie[i][j];
                    backup2[i][j] = ustawienie[i][j];
                    backup3[i][j] = ustawienie[i][j];
                    if ((ustawienie[i][j] == 'K' && strona)
                            || (ustawienie[i][j] == 'k' && !strona)) {
                        krol[0] = i;
                        krol[1] = j;
                    }
                }
            }

            return !SzachMatPatKontrola.uciekaj(backup1, strona, krol)
                    && !SzachMatPatKontrola.zastaw(backup2, strona, Wspomagacz.znajdzklopot(backup, strona), krol, przelotcan)
                    && !SzachMatPatKontrola.znajdzodsiecz(backup3, strona, Wspomagacz.znajdzklopot(backup, strona), kol, przelotcan);

        } else {
            return false;
        }

    }

    synchronized private void aktualizacja_parametrow(final Ruch move) {
        if (move.roszada && !move.dlugaroszada) {
            if (this.tura_rywala) {
                wright = false;
            } else {
                bright = false;
            }
        } else if (move.roszada) {
            wleft = false;
        }
        if (move.roszada) {
            if (this.tura_rywala) {
                this.didRochB = true;
            } else {
                this.didRochC = true;
            }
            kingrochB = false;
        } else {
            switch (move.kolejnosc) {
                case Wieza:
                    if (!tura_rywala) {
                        if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                            bleft = false;
                        } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                            bright = false;
                        }
                    } else {
                        if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                            wleft = false;
                        } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                            wright = false;
                        }
                    }
                    break;
                case Krol:
                    if (tura_rywala) {
                        if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                            kingrochB = false;
                        }
                    } else {
                        if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                            kingrochC = false;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    synchronized private void przywroc_parametry(final Ruch move) {
        if (move.roszada && !move.dlugaroszada) {
            if (this.tura_rywala) {
                wright = true;
            } else {
                bright = true;
            }
        } else if (move.roszada) {
            wleft = true;
        }
        if (move.roszada) {
            if (this.tura_rywala) {
                this.didRochB = false;
            } else {
                this.didRochC = false;
            }
            kingrochB = true;
        } else {
            switch (move.kolejnosc) {
                case Wieza:
                    if (!tura_rywala) {
                        if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                            bleft = true;
                        } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                            bright = true;
                        }
                    } else {
                        if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                            wleft = true;
                        } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                            wright = true;
                        }
                    }
                    break;
                case Krol:
                    if (tura_rywala) {
                        if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                            kingrochB = true;
                        }
                    } else {
                        if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                            kingrochC = true;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    synchronized private int pozyskajkordkolumna(Ruch.rzad kord) {
        switch (kord) {
            case r1:
                return 1;
            case r2:
                return 2;
            case r3:
                return 3;
            case r4:
                return 4;
            case r5:
                return 5;
            case r6:
                return 6;
            case r7:
                return 7;
            case r8:
                return 8;
        }
        return 0;
    }

    synchronized private int pozyskajkordrzad(Ruch.kolumna kord) {
        switch (kord) {
            case k1:
                return 1;
            case k2:
                return 2;
            case k3:
                return 3;
            case k4:
                return 4;
            case k5:
                return 5;
            case k6:
                return 6;
            case k7:
                return 7;
            case k8:
                return 8;
        }
        return 0;

    }

    synchronized public void setPrzerwa(boolean przerwa) {
        this.przerwa = przerwa;
    }

    synchronized public int getPozycje() {
        return pozycje;
    }

    synchronized public int getAll_position() {
        return all_position;
    }

    synchronized public boolean isPrzerwa() {
        return przerwa;
    }

}
