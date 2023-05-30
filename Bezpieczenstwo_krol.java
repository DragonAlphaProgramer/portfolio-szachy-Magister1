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
 * Klasa ta wyllicza bonus za bezpieczeństwo króla
 */
public class Bezpieczenstwo_krol {

    private static final Bezpieczenstwo_krol INSTANCE = new Bezpieczenstwo_krol();
    
    public static Bezpieczenstwo_krol get() {
        return INSTANCE;
    }
/**
 * przelicza pozycję i daje bonus za bezpieczeństwo króla
 * @param ustawienie bieżąca pozycja
 * @param b strona dla której jest analizowana pozycja
 * @param przelotcan dostęp do bicia w przelocie
 * @param RochB dostęp dla roszady białych
 * @param RochC dostęp dla roszady czarnych
 * @param wl dostęp do roszady długiej dla białych
 * @param wr dostęp do roszady krótkiej dla białych
 * @param bl dostęp do roszady długiej dla czarnych
 * @param br dostęp do roszady krótkiej dla czarnych
 * @param kol kolumna dostępu bicia w przelocie
 * @return zwraca wynik w postaci klasy analizator_odleglosci
 */
    analizator_odleglosci zliczpozycje(char[][] ustawienie, boolean b, boolean przelotcan,
            boolean RochB, boolean RochC, boolean wl, boolean wr, boolean bl, boolean br, byte kol) {
        int[] krol = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((ustawienie[i][j] == 'k' && !b) || (ustawienie[i][j] == 'K' && b)) {
                    krol[0] = j;
                    krol[1] = i;
                }
            }
        }
        Collection<Ruch> ruchy_rywala = Generator.generuj_posuniecia(konwert(ustawienie), !b, przelotcan, bl, br, wl, wr, RochB, RochC, kol,false,' ',new int[2],true,false);
        char symbol = ' ';
        int dystans = Integer.MAX_VALUE;
        for (Ruch move : ruchy_rywala) {
            if (!"O-O".equals(move.toString().substring(0, 3))) {
                String przeznaczenie = move.toString().substring(4);
                int biezacy_wynik = kalkuluj_dystans(przeznaczenie, krol);
                if (biezacy_wynik < dystans) {
                    dystans = biezacy_wynik;
                    symbol = move.toString().charAt(0);
                }
            } else {
                String przeznaczenie;
                if (b) {
                    if (move.toString().startsWith("O-O-O")) {
                        przeznaczenie = "C1";
                    } else {
                        przeznaczenie = "G1";
                    }
                } else {
                    if (move.toString().startsWith("O-O-O")) {
                        przeznaczenie = "C8";
                    } else {
                        przeznaczenie = "G8";
                    }
                }
                int biezacy_wynik = kalkuluj_dystans(przeznaczenie, krol);
                if (biezacy_wynik < dystans) {
                    dystans = biezacy_wynik;
                    symbol = move.toString().charAt(0);
                }

            }
        }
        return new analizator_odleglosci(symbol, dystans);
    }

    private int kalkuluj_dystans(String przeznaczenie, int[] krol) {
        int[] kord_rywala = new int[2];
        switch (przeznaczenie.charAt(0)) {
            case 'A':
                break;
            case 'B':
                kord_rywala[0] = 1;
                break;
            case 'C':
                kord_rywala[0] = 2;
                break;
            case 'D':
                kord_rywala[0] = 3;
                break;
            case 'E':
                kord_rywala[0] = 4;
                break;
            case 'F':
                kord_rywala[0] = 5;
                break;
            case 'G':
                kord_rywala[0] = 6;
                break;
            case 'H':
                kord_rywala[0] = 7;
                break;
        }
        switch (przeznaczenie.charAt(1)) {
            case '1':
                break;
            case '2':
                kord_rywala[1] = 1;
                break;
            case '3':
                kord_rywala[1] = 2;
                break;
            case '4':
                kord_rywala[1] = 3;
                break;
            case '5':
                kord_rywala[1] = 4;
                break;
            case '6':
                kord_rywala[1] = 5;
                break;
            case '7':
                kord_rywala[1] = 6;
                break;
            case '8':
                kord_rywala[1] = 7;
                break;
        }
        int roznicaX = Math.abs(krol[0] - kord_rywala[0]);
        int roznicaY = Math.abs(krol[1] - kord_rywala[1]);
        return Math.max(roznicaY, roznicaX);
    }

    private SI_MIN_MAX_Alfa_Beta.figury[][] konwert(char[][] ustawienie) {
      SI_MIN_MAX_Alfa_Beta.figury[][] pozycja = new SI_MIN_MAX_Alfa_Beta.figury[8][8];
             for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            switch(ustawienie[i][j]){
                case ' ':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.pustka;break;
                case 'P':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.BPion;break;
                case 'p':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.CPion; break;
                case 'N':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.BSkoczek;break;
                case 'n':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.CSkoczek; break;
                case 'B':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.BGoniec;break;
                case 'b':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.CGoniec; break;
                case 'R':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.BWieza;break;
                case 'r':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.CWieza; break;
                case 'Q':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.BHetman;break;
                case 'q':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.CHetman; break;
                case 'K':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.BKrol;break;
                case 'k':pozycja[i][j]=SI_MIN_MAX_Alfa_Beta.figury.CKrol; break;
            }
        }
        }return  pozycja;    
    }

    public class analizator_odleglosci {

        char zagrozenie;
        int dystans;

        public analizator_odleglosci(char zagrozenie, int dystans) {
            this.zagrozenie = zagrozenie;
            this.dystans = dystans;
        }

        public char getZagrozenie() {
            return zagrozenie;
        }

        public int getDystans() {
            return dystans;
        }

        public int getWartosc() {
            int figuraW = 0;
            switch (zagrozenie) {
                case 'p':
                case 'P':
                    figuraW = 1000;
                    break;
                case 'n':
                case 'N':
                case 'b':
                case 'B':
                    figuraW = 3000;
                    break;
                case 'R':
                case 'r':
                    figuraW = 5000;
                    break;
                case 'q':
                case 'Q':
                    figuraW = 9000;
                    break;
            }
            return figuraW / 100 * dystans;
        }

    }
}
