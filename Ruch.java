/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;



/**
 *
 * @author Patryk klasa przechowująca parametry ruchów
 */
public class Ruch implements Comparable<Ruch> {

    Ruch(boolean anty, String lista, char bity, char[][] ust2) {
        korzystnosc_bicia = bity;
        kolejnosc = lista.charAt(0);
        atak = (bity != ' ')&&!anty;
        roszada = lista.startsWith("O-O",1);
        dlugaroszada = lista.startsWith("O-O-O",1);
        przelot = lista.charAt(6) == ('E');
        promocja = lista.charAt(6) == '='
                && (lista.charAt(0) == 'p' || lista.charAt(0) == 'P');
        if (lista.charAt(6) == '=') {
            promowana = lista.charAt(7);
            switch (lista.charAt(7)) {
                case 'n':
                case 'N':
                    wartosc_promocji = 1;
                    break;
                case 'b':
                case 'B':
                    wartosc_promocji = 2;
                    break;
                case 'r':
                case 'R':
                    wartosc_promocji = 3;
                    break;
                case 'q':
                case 'Q':
                    wartosc_promocji = 4;
                    break;
                default:
                    break;
            }
        }
        if (lista.startsWith("O-O",1)) {
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
        if (!lista.startsWith("O-O",1)) {
            switch (lista.charAt(0)) {
                case 'p':
                    czybialy = false;
                    break;
                case 'P':
                    czybialy = true;
                    break;
                case 'n':
                    czybialy = false;
                    break;
                case 'N':
                    czybialy = true;
                    break;
                case 'b':
                    czybialy = false;
                    break;
                case 'B':
                    czybialy = true;
                    break;
                case 'r':
                    czybialy = false;
                    break;
                case 'R':
                    czybialy = true;
                    break;
                case 'q':
                    czybialy = false;
                    break;
                case 'Q':
                    czybialy = true;
                    break;
                case 'k':
                    czybialy = false;
                    break;
                case 'K':
                    czybialy = true;
                    break;

            }
            czybialy=(kolejnosc=='Q'||kolejnosc=='K'
                    ||kolejnosc=='R'||kolejnosc=='P'
                    ||kolejnosc=='N'||kolejnosc=='B');
        } else {
            czybialy = lista.charAt(0) == 'K';
            kolejnosc = czybialy ? 'K' : 'k';
        }
        wspolczynnik_ruchu = wartosc(kolejnosc);
        wspolczynnik_bitki = wartosc(bity);
        for(int i = 0;i<8;i++){
            for(int j = 0;j<8;j++){
                chessboard_before[i][j]=ust2[i][j];
                
        }
        }
        chessboard_after = zmiana(lista);
        if(!anty){
        czy_szach = lista.charAt(8)=='+';
        }else{
        czy_szach = false;    
        }
    }

   
   
    private char[][] zmiana(String lista) {
        char[][] wynik = new char[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(this.chessboard_before[i], 0, wynik[i], 0, 8);
        }
        if (this.roszada) {

            if (!this.dlugaroszada) {
                if (this.czybialy) {
                    wynik[0][4] = ' ';
                    wynik[0][7] = ' ';
                    wynik[0][5] = 'R';
                    wynik[0][6] = 'K';

                } else {
                    wynik[7][4] = ' ';
                    wynik[7][7] = ' ';
                    wynik[7][5] = 'r';
                    wynik[7][6] = 'k';

                }
            } else {
                if (this.czybialy) {
                    wynik[0][4] = ' ';
                    wynik[0][0] = ' ';
                    wynik[0][3] = 'R';
                    wynik[0][2] = 'K';

                } else {
                    wynik[7][4] = ' ';
                    wynik[7][0] = ' ';
                    wynik[7][3] = 'r';
                    wynik[7][2] = 'k';

                }
            }

        } else {
            wynik[lista.charAt(2) - '1'][lista.charAt(1) - 'A'] = ' ';
            if (this.przelot && (kolejnosc == 'P'||kolejnosc == 'p')) {
                wynik[!this.czybialy ? 3 : 4][lista.charAt(4) - 'A'] = ' ';

            } else if (this.promocja) {
                wynik[ (lista.charAt(5) - '1')][ (lista.charAt(4) - 'A')] = (lista.charAt(7));
            } else {
                wynik[ (lista.charAt(5) - '1')][ (lista.charAt(4) - 'A')] = (lista.charAt(0));
            }
        }
        return wynik;
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


    char[][] chessboard_before = new char[8][8];
    char[][] chessboard_after = new char[8][8];
    Boolean czy_szach, roszada, czybialy, przelot, dlugaroszada, promocja, atak;
    kolumna start1, koniec1;
    rzad start2, koniec2;
    char kolejnosc;
    char promowana = ' ';
    Integer wspolczynnik_ruchu, wspolczynnik_bitki;
    int wartosc_promocji = 0;
    char korzystnosc_bicia;

    private int wartosc(char f) {
        switch (f) {
            case 'b':
            case 'B':
                return 330;
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
            
            wynik=wynik.concat(kolejnosc+"");
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
            wynik = wynik.concat(korzystnosc_bicia == ' ' ? "-" : "x");
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
                wynik = wynik.concat("="+promowana);
            } else {
                wynik = wynik.concat("--");
            }
        }
        return czy_szach ? wynik + "+" : wynik + " ";

    }
}
