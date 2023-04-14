package szachy;

/**
 *
 * @author PatrykK
 */
public class RuchA {

    enum figura {
        Pion, Skoczek, Goniec, Wieza, Hetman, Amazonka, Krol, Pustka
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
    Boolean szach, czy_szach, roszada, czybialy, przelot, dlugaroszada, promocja, atak;
    kolumna start1, koniec1;
    rzad start2, koniec2;
    figura kolejnosc, promowana = null;
    Integer wspolczynnik_ruchu, wspolczynnik_bitki;
    int wartosc_promocji = 0;
    figura korzystnosc_bicia;

    RuchA(String lista, SzachowaArena.figury bity, SzachowaArena.figury[][] szachownica) {

        switch (bity) {
            case BPion:
            case CPion:
                korzystnosc_bicia = RuchA.figura.Pion;
                atak = true;
                break;
            case BSkoczek:
            case CSkoczek:
                korzystnosc_bicia = RuchA.figura.Skoczek;
                atak = true;
                break;
            case BGoniec:
            case CGoniec:
                korzystnosc_bicia = RuchA.figura.Goniec;
                atak = true;
                break;
            case BWieza:
            case CWieza:
                korzystnosc_bicia = RuchA.figura.Wieza;
                atak = true;
                break;
            case BHetman:
            case CHetman:
                korzystnosc_bicia = RuchA.figura.Hetman;
                atak = true;
                break;
            default:
                korzystnosc_bicia = RuchA.figura.Pustka;
                atak = false;
        }
        czy_szach = lista.endsWith("+");
        roszada = lista.substring(1, 4).equals("O-O");
        dlugaroszada = lista.substring(1, 6).equals("O-O-O");
        przelot = lista.charAt(6) == ('E');
        promocja = lista.charAt(6) == '='
                && (lista.charAt(0) == 'p' || lista.charAt(0) == 'P');
        if (lista.charAt(6) == '=') {
            switch (lista.charAt(7)) {
                case 'n':
                    promowana = RuchA.figura.Skoczek;
                    wartosc_promocji = 1;
                    break;
                case 'N':
                    promowana = RuchA.figura.Skoczek;
                    wartosc_promocji = 1;
                    break;
                case 'b':
                    promowana = RuchA.figura.Goniec;
                    wartosc_promocji = 2;
                    break;
                case 'B':
                    promowana = RuchA.figura.Goniec;
                    wartosc_promocji = 2;
                    break;
                case 'r':
                    promowana = RuchA.figura.Wieza;
                    wartosc_promocji = 3;
                    break;
                case 'R':
                    promowana = RuchA.figura.Wieza;
                    wartosc_promocji = 3;
                    break;
                case 'q':
                    promowana = RuchA.figura.Hetman;
                    wartosc_promocji = 4;
                    break;
                case 'Q':
                    promowana = RuchA.figura.Hetman;
                    wartosc_promocji = 4;
                    break;
                default:
                    promowana = RuchA.figura.Pustka;
                    break;
            }
        }
        if (lista.substring(1, 4).equals("O-O")) {
            start1 = RuchA.kolumna.KR;
            start2 = RuchA.rzad.RR;
            koniec1 = RuchA.kolumna.KR;
            koniec2 = RuchA.rzad.RR;
        } else {
            switch (lista.charAt(1)) {
                case 'A':
                    start1 = RuchA.kolumna.k1;
                    break;
                case 'B':
                    start1 = RuchA.kolumna.k2;
                    break;
                case 'C':
                    start1 = RuchA.kolumna.k3;
                    break;
                case 'D':
                    start1 = RuchA.kolumna.k4;
                    break;
                case 'E':
                    start1 = RuchA.kolumna.k5;
                    break;
                case 'F':
                    start1 = RuchA.kolumna.k6;
                    break;
                case 'G':
                    start1 = RuchA.kolumna.k7;
                    break;
                case 'H':
                    start1 = RuchA.kolumna.k8;
                    break;
            }
            switch (lista.charAt(4)) {
                case 'A':
                    koniec1 = RuchA.kolumna.k1;
                    break;
                case 'B':
                    koniec1 = RuchA.kolumna.k2;
                    break;
                case 'C':
                    koniec1 = RuchA.kolumna.k3;
                    break;
                case 'D':
                    koniec1 = RuchA.kolumna.k4;
                    break;
                case 'E':
                    koniec1 = RuchA.kolumna.k5;
                    break;
                case 'F':
                    koniec1 = RuchA.kolumna.k6;
                    break;
                case 'G':
                    koniec1 = RuchA.kolumna.k7;
                    break;
                case 'H':
                    koniec1 = RuchA.kolumna.k8;
                    break;
            }
            switch (lista.charAt(2)) {
                case '1':
                    start2 = RuchA.rzad.r1;
                    break;
                case '2':
                    start2 = RuchA.rzad.r2;
                    break;
                case '3':
                    start2 = RuchA.rzad.r3;
                    break;
                case '4':
                    start2 = RuchA.rzad.r4;
                    break;
                case '5':
                    start2 = RuchA.rzad.r5;
                    break;
                case '6':
                    start2 = RuchA.rzad.r6;
                    break;
                case '7':
                    start2 = RuchA.rzad.r7;
                    break;
                case '8':
                    start2 = RuchA.rzad.r8;
                    break;
            }
            switch (lista.charAt(5)) {
                case '1':
                    koniec2 = RuchA.rzad.r1;
                    break;
                case '2':
                    koniec2 = RuchA.rzad.r2;
                    break;
                case '3':
                    koniec2 = RuchA.rzad.r3;
                    break;
                case '4':
                    koniec2 = RuchA.rzad.r4;
                    break;
                case '5':
                    koniec2 = RuchA.rzad.r5;
                    break;
                case '6':
                    koniec2 = RuchA.rzad.r6;
                    break;
                case '7':
                    koniec2 = RuchA.rzad.r7;
                    break;
                case '8':
                    koniec2 = RuchA.rzad.r8;
                    break;
            }
        }
        if (!lista.substring(1, 4).equals("O-O")) {
            switch (lista.charAt(0)) {
                case 'p':
                    czybialy = false;
                    kolejnosc = RuchA.figura.Pion;
                    break;
                case 'P':
                    czybialy = true;
                    kolejnosc = RuchA.figura.Pion;
                    break;
                case 'n':
                    czybialy = false;
                    kolejnosc = RuchA.figura.Skoczek;
                    break;
                case 'N':
                    czybialy = true;
                    kolejnosc = RuchA.figura.Skoczek;
                    break;
                case 'b':
                    czybialy = false;
                    kolejnosc = RuchA.figura.Goniec;
                    break;
                case 'B':
                    czybialy = true;
                    kolejnosc = RuchA.figura.Goniec;
                    break;
                case 'r':
                    czybialy = false;
                    kolejnosc = RuchA.figura.Wieza;
                    break;
                case 'R':
                    czybialy = true;
                    kolejnosc = RuchA.figura.Wieza;
                    break;
                case 'q':
                    czybialy = false;
                    kolejnosc = RuchA.figura.Hetman;
                    break;
                case 'Q':
                    czybialy = true;
                    kolejnosc = RuchA.figura.Hetman;
                    break;
                case 'k':
                    czybialy = false;
                    kolejnosc = RuchA.figura.Krol;
                    break;
                case 'K':
                    czybialy = true;
                    kolejnosc = RuchA.figura.Krol;
                    break;
                case 'a':
                    czybialy = false;
                    kolejnosc = RuchA.figura.Amazonka;
                    break;
                case 'A':
                    czybialy = true;
                    kolejnosc = RuchA.figura.Amazonka;
                    break;

            }
        } else {
            kolejnosc = RuchA.figura.Krol;
            czybialy = lista.charAt(0) == 'K';
        }

    }

    @Override
    public String toString() {
        String wynik = "";
        if (roszada == true) {
            wynik = dlugaroszada == true ? "O-O-O" : "O-O";
            return czy_szach ? wynik + "+" : wynik;
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
                case Amazonka:
                    wynik = wynik.concat(czybialy ? "A" : "a");
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
            wynik = wynik.concat(korzystnosc_bicia == RuchA.figura.Pustka ? "-" : "x");
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
                    case Amazonka:
                        wynik = wynik.concat(czybialy ? "A" : "a");
                        break;
                }
            } else {
                wynik = wynik.concat("--");
            }
            return czy_szach ? wynik + "+" : wynik;
        }

    }
}
