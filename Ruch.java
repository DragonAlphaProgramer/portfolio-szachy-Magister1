/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.util.Collection;

/**
 *
 * @author Patryk klasa przechowująca parametry ruchów
 */
public class Ruch implements Comparable<Ruch> {

    private Integer zwroc_pole(Ruch.rzad r, Ruch.kolumna k) {
        int x = 0;
        int y = 0;
        switch (r) {
            case RR:
            case r1:
                x = 1;
                break;
            case r2:
                x = 2;
                break;
            case r3:
                x = 3;
                break;
            case r4:
                x = 4;
                break;
            case r5:
                x = 5;
                break;
            case r6:
                x = 6;
                break;
            case r7:
                x = 7;
                break;
            case r8:
                x = 8;
        }

        switch (k) {
            case KR:
            case k5:
                x = 5;
                break;
            case k1:
                x = 1;
                break;
            case k2:
                x = 2;
                break;
            case k3:
                x = 3;
                break;
            case k4:
                x = 4;
                break;
            case k6:
                x = 6;
                break;
            case k7:
                x = 7;
                break;
            case k8:
                x = 8;
        }

        return x * 8 + y;
    }

    private SI_MIN_MAX_Alfa_Beta.figury[][] zmiana(SI_MIN_MAX_Alfa_Beta.figury[][] szachownica, String lista) {
        SI_MIN_MAX_Alfa_Beta.figury[][] wynik = new SI_MIN_MAX_Alfa_Beta.figury[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                wynik[i][j] = szachownica[i][j];
                wynik[i][j] = szachownica[i][j];
            }
        }
        if (this.roszada) {

            if (!this.dlugaroszada) {
                if (this.czybialy) {
                    wynik[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    wynik[0][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    wynik[0][5] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    wynik[0][6] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                } else {
                    wynik[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    wynik[7][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    wynik[7][5] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    wynik[7][6] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                }
            } else {
                if (this.czybialy) {
                    wynik[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    wynik[0][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    wynik[0][3] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                    wynik[0][2] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                } else {
                    wynik[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    wynik[7][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                    wynik[7][3] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                    wynik[7][2] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                }
            }

        } else {
            wynik[lista.charAt(2) - '1'][lista.charAt(1) - 'A'] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
            if (this.przelot && kolejnosc == figura.Pion) {
                wynik[!this.czybialy ? 3 : 4][lista.charAt(4) - 'A'] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

            } else if (this.promocja) {
                wynik[ (lista.charAt(5) - '1')][ (lista.charAt(4) - 'A')] = pozyskaj_figure(lista.charAt(7));
            } else {
                wynik[ (lista.charAt(5) - '1')][ (lista.charAt(4) - 'A')] = pozyskaj_figure(lista.charAt(0));
            }
        }
        return wynik;
    }

    private SI_MIN_MAX_Alfa_Beta.figury pozyskaj_figure(char znak) {
        switch (znak) {
            case 'p':
                return SI_MIN_MAX_Alfa_Beta.figury.CPion;
            case 'P':
                return SI_MIN_MAX_Alfa_Beta.figury.BPion;
            case 'n':
                return SI_MIN_MAX_Alfa_Beta.figury.CSkoczek;
            case 'N':
                return SI_MIN_MAX_Alfa_Beta.figury.BSkoczek;
            case 'b':
                return SI_MIN_MAX_Alfa_Beta.figury.CGoniec;
            case 'B':
                return SI_MIN_MAX_Alfa_Beta.figury.BGoniec;
            case 'r':
                return SI_MIN_MAX_Alfa_Beta.figury.CWieza;
            case 'R':
                return SI_MIN_MAX_Alfa_Beta.figury.BWieza;
            case 'q':
                return SI_MIN_MAX_Alfa_Beta.figury.CHetman;
            case 'Q':
                return SI_MIN_MAX_Alfa_Beta.figury.BHetman;
            case 'k':
                return SI_MIN_MAX_Alfa_Beta.figury.CKrol;
            case 'K':
                return SI_MIN_MAX_Alfa_Beta.figury.BKrol;

        }
        return SI_MIN_MAX_Alfa_Beta.figury.pustka;
    }

    enum figura {
        Pion, Skoczek, Goniec, Wieza, Hetman, Krol, Pustka
    }

    enum rzad {
        r1, r2, r3, r4, r5, r6, r7, r8, RR
    }

    enum kolumna {
        k1, k2, k3, k4, k5, k6, k7, k8, KR
    }

    enum sortowanie {
        pierwszy_szach, pierwsze_bicie, zaden
    }
    SI_MIN_MAX_Alfa_Beta.figury[][] chessboard_before = new SI_MIN_MAX_Alfa_Beta.figury[8][8];
    SI_MIN_MAX_Alfa_Beta.figury[][] chessboard_after = new SI_MIN_MAX_Alfa_Beta.figury[8][8];
    Boolean szach, czy_szach, roszada, czybialy, przelot, dlugaroszada, promocja, atak;
    kolumna start1, koniec1;
    Collection<Ruch> lista_ruchow;
    rzad start2, koniec2;
    figura kolejnosc, promowana = null;
    final sortowanie sposob;
    Integer wspolczynnik_ruchu, wspolczynnik_bitki;
    int wartosc_promocji = 0;
    figura korzystnosc_bicia;

    private int wartosc(figura f) {
        switch (f) {
            case Goniec:
                return 350;
            case Hetman:
                return 900;
            case Krol:
                return 10000;
            case Pion:
                return 100;
            case Skoczek:
                return 300;
            case Wieza:
                return 500;
            default:
                return 0;
        }

    }

    private int wartosc(SI_MIN_MAX_Alfa_Beta.figury f) {
        switch (f) {
            case BGoniec:
            case CGoniec:
                return 350;
            case BHetman:
            case CHetman:
                return 900;
            case BPion:
            case CPion:
                return 100;
            case CSkoczek:
            case BSkoczek:
                return 300;
            case CWieza:
            case BWieza:
                return 500;
            default:
                return 0;
        }

    }

    Ruch(boolean anty, String lista, int sposob, SI_MIN_MAX_Alfa_Beta.figury bity, SI_MIN_MAX_Alfa_Beta.figury[][] szachownica) {

        switch (bity) {
            case BPion:
            case CPion:
                korzystnosc_bicia = figura.Pion;
                break;
            case BSkoczek:
            case CSkoczek:
                korzystnosc_bicia = figura.Skoczek;
                break;
            case BGoniec:
            case CGoniec:
                korzystnosc_bicia = figura.Goniec;
                break;
            case BWieza:
            case CWieza:
                korzystnosc_bicia = figura.Wieza;
                break;
            case BHetman:
            case CHetman:
                korzystnosc_bicia = figura.Hetman;
                break;
            case BKrol:
            case CKrol:
                korzystnosc_bicia = figura.Krol;
                break;
            default:
                korzystnosc_bicia = figura.Pustka;
        }
        atak = (bity != SI_MIN_MAX_Alfa_Beta.figury.pustka)&&!anty;
        roszada = lista.startsWith("O-O", 1);
        dlugaroszada = lista.startsWith("O-O-O", 1);
        przelot = lista.charAt(6) == ('E');
        promocja = lista.charAt(6) == '='
                && (lista.charAt(0) == 'p' || lista.charAt(0) == 'P');
        if (lista.charAt(6) == '=') {
            switch (lista.charAt(7)) {
                case 'n':
                case 'N':
                    promowana = figura.Skoczek;
                    wartosc_promocji = 1;
                    break;
                case 'b':
                case 'B':
                    promowana = figura.Goniec;
                    wartosc_promocji = 2;
                    break;
                case 'r':
                case 'R':
                    promowana = figura.Wieza;
                    wartosc_promocji = 3;
                    break;
                case 'q':
                case 'Q':
                    promowana = figura.Hetman;
                    wartosc_promocji = 4;
                    break;
                default:
                    promowana = figura.Pustka;
                    break;
            }
        }
        if (lista.startsWith("O-O", 1)) {
            start1 = kolumna.KR;
            start2 = rzad.RR;
            koniec1 = kolumna.KR;
            koniec2 = rzad.RR;
        } else {
            switch (lista.charAt(1)) {
                case 'A':
                    start1 = kolumna.k1;
                    break;
                case 'B':
                    start1 = kolumna.k2;
                    break;
                case 'C':
                    start1 = kolumna.k3;
                    break;
                case 'D':
                    start1 = kolumna.k4;
                    break;
                case 'E':
                    start1 = kolumna.k5;
                    break;
                case 'F':
                    start1 = kolumna.k6;
                    break;
                case 'G':
                    start1 = kolumna.k7;
                    break;
                case 'H':
                    start1 = kolumna.k8;
                    break;
            }
            switch (lista.charAt(4)) {
                case 'A':
                    koniec1 = kolumna.k1;
                    break;
                case 'B':
                    koniec1 = kolumna.k2;
                    break;
                case 'C':
                    koniec1 = kolumna.k3;
                    break;
                case 'D':
                    koniec1 = kolumna.k4;
                    break;
                case 'E':
                    koniec1 = kolumna.k5;
                    break;
                case 'F':
                    koniec1 = kolumna.k6;
                    break;
                case 'G':
                    koniec1 = kolumna.k7;
                    break;
                case 'H':
                    koniec1 = kolumna.k8;
                    break;
            }
            switch (lista.charAt(2)) {
                case '1':
                    start2 = rzad.r1;
                    break;
                case '2':
                    start2 = rzad.r2;
                    break;
                case '3':
                    start2 = rzad.r3;
                    break;
                case '4':
                    start2 = rzad.r4;
                    break;
                case '5':
                    start2 = rzad.r5;
                    break;
                case '6':
                    start2 = rzad.r6;
                    break;
                case '7':
                    start2 = rzad.r7;
                    break;
                case '8':
                    start2 = rzad.r8;
                    break;
            }
            switch (lista.charAt(5)) {
                case '1':
                    koniec2 = rzad.r1;
                    break;
                case '2':
                    koniec2 = rzad.r2;
                    break;
                case '3':
                    koniec2 = rzad.r3;
                    break;
                case '4':
                    koniec2 = rzad.r4;
                    break;
                case '5':
                    koniec2 = rzad.r5;
                    break;
                case '6':
                    koniec2 = rzad.r6;
                    break;
                case '7':
                    koniec2 = rzad.r7;
                    break;
                case '8':
                    koniec2 = rzad.r8;
                    break;
            }
        }
        if (!lista.startsWith("O-O", 1)) {
            switch (lista.charAt(0)) {
                case 'p':
                    czybialy = false;
                    kolejnosc = figura.Pion;
                    break;
                case 'P':
                    czybialy = true;
                    kolejnosc = figura.Pion;
                    break;
                case 'n':
                    czybialy = false;
                    kolejnosc = figura.Skoczek;
                    break;
                case 'N':
                    czybialy = true;
                    kolejnosc = figura.Skoczek;
                    break;
                case 'b':
                    czybialy = false;
                    kolejnosc = figura.Goniec;
                    break;
                case 'B':
                    czybialy = true;
                    kolejnosc = figura.Goniec;
                    break;
                case 'r':
                    czybialy = false;
                    kolejnosc = figura.Wieza;
                    break;
                case 'R':
                    czybialy = true;
                    kolejnosc = figura.Wieza;
                    break;
                case 'q':
                    czybialy = false;
                    kolejnosc = figura.Hetman;
                    break;
                case 'Q':
                    czybialy = true;
                    kolejnosc = figura.Hetman;
                    break;
                case 'k':
                    czybialy = false;
                    kolejnosc = figura.Krol;
                    break;
                case 'K':
                    czybialy = true;
                    kolejnosc = figura.Krol;
                    break;

            }
        } else {
            kolejnosc = figura.Krol;
            czybialy = lista.charAt(0) == 'K';
        }

        assert kolejnosc != null;
        wspolczynnik_ruchu = wartosc(kolejnosc);
        wspolczynnik_bitki = wartosc(bity);
        chessboard_before = szachownica;
        chessboard_after = zmiana(chessboard_before, lista);
        if(!anty){
        czy_szach = lista.charAt(8)=='+';
        }else{
        czy_szach = false;    
        }
        switch (sposob) {
            case 1:
                this.sposob = sortowanie.pierwszy_szach;
                break;
            case 2:
                this.sposob = sortowanie.pierwsze_bicie;
                break;
            default:
                this.sposob = sortowanie.zaden;
        }
    }

    @Override
    public int compareTo(Ruch obiekt) {
        //if (Boolean.compare(this.czy_szach, obiekt.czy_szach) == 0) {
        if (Boolean.compare(this.atak, obiekt.atak) == 0) {
            if (Boolean.compare(this.roszada, obiekt.roszada) == 0) {
                return Integer.compare(obiekt.wspolczynnik_ruchu, this.wspolczynnik_ruchu);
            } else {
                return Boolean.compare(this.roszada, obiekt.roszada) * -1;
            }
        } else {
            return Boolean.compare(this.atak, obiekt.atak) * -1;
        }
        /* } else {
            return Boolean.compare(this.czy_szach, obiekt.czy_szach) * -1;
        }*/
    }

    @Override
    public String toString() {
        String wynik = "";
        if (roszada) {
            wynik = dlugaroszada ? "O-O-O" : "O-O";
        } else {
            switch (kolejnosc) {
                case Pion:
                    wynik = wynik.concat(czybialy ? "P" : "p");
                    break;
                case Skoczek:
                    wynik = wynik.concat(czybialy ? "N" : "n");
                    break;
                case Goniec:
                    wynik = wynik.concat(czybialy ? "B" : "b");
                    break;
                case Wieza:
                    wynik = wynik.concat(czybialy ? "R" : "r");
                    break;
                case Hetman:
                    wynik = wynik.concat(czybialy ? "Q" : "q");
                    break;
                case Krol:
                    wynik = wynik.concat(czybialy ? "K" : "k");
                    break;
            }
            switch (start1) {
                case k1:
                    wynik = wynik.concat("A");
                    break;
                case k2:
                    wynik = wynik.concat("B");
                    break;
                case k3:
                    wynik = wynik.concat("C");
                    break;
                case k4:
                    wynik = wynik.concat("D");
                    break;
                case k5:
                    wynik = wynik.concat("E");
                    break;
                case k6:
                    wynik = wynik.concat("F");
                    break;
                case k7:
                    wynik = wynik.concat("G");
                    break;
                case k8:
                    wynik = wynik.concat("H");
                    break;
            }
            switch (start2) {
                case r1:
                    wynik = wynik.concat("1");
                    break;
                case r2:
                    wynik = wynik.concat("2");
                    break;
                case r3:
                    wynik = wynik.concat("3");
                    break;
                case r4:
                    wynik = wynik.concat("4");
                    break;
                case r5:
                    wynik = wynik.concat("5");
                    break;
                case r6:
                    wynik = wynik.concat("6");
                    break;
                case r7:
                    wynik = wynik.concat("7");
                    break;
                case r8:
                    wynik = wynik.concat("8");
                    break;
            }
            wynik = wynik.concat(korzystnosc_bicia == figura.Pustka ? "-" : "x");
            switch (koniec1) {
                case k1:
                    wynik = wynik.concat("A");
                    break;
                case k2:
                    wynik = wynik.concat("B");
                    break;
                case k3:
                    wynik = wynik.concat("C");
                    break;
                case k4:
                    wynik = wynik.concat("D");
                    break;
                case k5:
                    wynik = wynik.concat("E");
                    break;
                case k6:
                    wynik = wynik.concat("F");
                    break;
                case k7:
                    wynik = wynik.concat("G");
                    break;
                case k8:
                    wynik = wynik.concat("H");
                    break;
            }
            switch (koniec2) {
                case r1:
                    wynik = wynik.concat("1");
                    break;
                case r2:
                    wynik = wynik.concat("2");
                    break;
                case r3:
                    wynik = wynik.concat("3");
                    break;
                case r4:
                    wynik = wynik.concat("4");
                    break;
                case r5:
                    wynik = wynik.concat("5");
                    break;
                case r6:
                    wynik = wynik.concat("6");
                    break;
                case r7:
                    wynik = wynik.concat("7");
                    break;
                case r8:
                    wynik = wynik.concat("8");
                    break;
            }
            if (przelot) {
                wynik = wynik.concat("EP");
            } else if (promocja) {
                wynik = wynik.concat("=");
                switch (promowana) {
                    case Skoczek:
                        wynik = wynik.concat(czybialy ? "N" : "n");
                        break;
                    case Goniec:
                        wynik = wynik.concat(czybialy ? "B" : "b");
                        break;
                    case Wieza:
                        wynik = wynik.concat(czybialy ? "R" : "r");
                        break;
                    case Hetman:
                        wynik = wynik.concat(czybialy ? "Q" : "q");
                        break;
                }
            } else {
                wynik = wynik.concat("--");
            }
        }
        return czy_szach ? wynik + "+" : wynik;

    }
}
