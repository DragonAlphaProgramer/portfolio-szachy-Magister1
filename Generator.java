/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Patryk Klasa odpowiedzialna za generowanie listy posunięć
 */
class Generator {

    /**
     * sprawdza, czy po ruchu są obecni królowie po obu stronach
     *
     * @param backup sprawdzana pozycja
     * @return jeśli królowie są obecni, zwróci true
     */
    private static boolean obecnosc(final char[][] backup) {
        boolean KB = false, KC = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (backup[i][j] == 'K') {
                    KB = !KB;
                }
                if (backup[i][j] == 'k') {
                    KC = !KC;
                }
            }
        }
        return KC && KB;
    }

    /**
     * Metoda generuje listę dozwolonych posunięć
     *
     * @param ust pozycja na planszy
     * @param tura_rywala strona, która wykonuje ruch
     * @param przelotcan dostęp do bicia w przelocie
     * @param blackleft dostęp czarnych do długiej roszady
     * @param blackright dostęp czarnych do krótkiej roszady
     * @param whiteleft dostęp białych do długiej roszady
     * @param whiteright dostęp białych do krótkiej roszady
     * @param kingrochB dostęp czarnych do roszady
     * @param kingrochC dostęp białych do roszady
     * @param sposób sortowania
     * @param kolumna kolumna z dostępnym biciem w przelocie
     * @return bieżąca lista posunięć w danej pozycji
     */
    static ArrayList<Ruch> generuj_posuniecia(char[][] ust, boolean tura_rywala, boolean przelotcan,
            boolean blackleft, boolean blackright, boolean whiteleft, boolean whiteright,
            boolean kingrochB, boolean kingrochC, int kolumna, boolean all) {

        ArrayList<Ruch> lista_dopuszcalnych_Ruchow = new ArrayList<>();
        char[][] backup = new char[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                backup[x][y] = ust[x][y];
            }
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (backup[x][y] == ' ') {
                    continue;
                }
                boolean szach;
                boolean wynik;
                char znak = backup[x][y];

                int param_ruch = 1;
                int przod;
                int tyl;
                boolean w1, w2, w3, w4, d1, d2, d3, d4;
                // //System.out.print("["+x+" "+y+"]"+znak);
                switch (znak) {
                    case 'P':
                        if (tura_rywala) {
                            if (x == 4 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x + 1][kolumna - 1] = 'P';
                                backup[x][kolumna - 1] = ' ';
                                backup[x][y] = ' ';

                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                backup[x + 1][kolumna - 1] = ' ';
                                backup[x][kolumna - 1] = 'p';
                                backup[x][y] = 'P';
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x + 1));

                                    if (!szach) {
                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "EP "), 'p', backup));
                                    } else {
                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), 'p', backup));
                                    }

                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x + 1 < 8) {
                                    if (b != 0 && ((backup[x + 1][y + b] == 'n')
                                            || (backup[x + 1][y + b] == 'p')
                                            || (backup[x + 1][y + b] == 'b')
                                            || (backup[x + 1][y + b] == 'r')
                                            || (backup[x + 1][y + b] == 'q'))) {
                                        char przechowalnie;
                                        przechowalnie = backup[x + 1][y + b];
                                        if (x != 6) {
                                            backup[x + 1][y + b] = znak;
                                            backup[x][y] = ' ';

                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                            backup[x][y] = znak;
                                            backup[x + 1][y + b] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P"
                                                            + "" + x1 + x2 + "x" + y1 + y2 + "--+"), przechowalnie, backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P"
                                                            + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, backup));
                                                }

                                            }
                                        } else {
                                            char[] symbole = {'Q', 'R', 'B', 'N'};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x + 1][y + b] = symbole[s];
                                                backup[x][y] = ' ';

                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                                backup[x][y] = znak;
                                                backup[x + 1][y + b] = przechowalnie;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (""+symbole[s]) + "+"), przechowalnie, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (""+symbole[s]) + " "), przechowalnie, backup));
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x + 1 < 8) {
                                if (backup[x + 1][y] == ' ' || (backup[x + 1][y] != 'p' && backup[x + 1][y] != 'P'
                                        && backup[x + 1][y] != 'N' && backup[x + 1][y] != 'n' && backup[x + 1][y] != 'B'
                                        && backup[x + 1][y] != 'b' && backup[x + 1][y] != 'R' && backup[x + 1][y] != 'r'
                                        && backup[x + 1][y] != 'Q' && backup[x + 1][y] != 'q' && backup[x + 1][y] != 'K'
                                        && backup[x + 1][y] != 'k')) {
                                    char przechowalnie;
                                    przechowalnie = backup[x + 1][y];
                                    if (x != 6) {
                                        backup[x + 1][y] = znak;
                                        backup[x][y] = ' ';
                                        wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                        szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                        backup[x][y] = znak;
                                        backup[x + 1][y] = przechowalnie;
                                        if (wynik || all) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));
                                            }

                                        }
                                    } else {
                                        char[] symbole = {'Q', 'R', 'B', 'N'};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x + 1][y] = symbole[s];
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x][y] = znak;
                                            backup[x + 1][y] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P"
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + (""+symbole[s]) + "+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P"
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + (""+symbole[s]) + " "), ' ', backup));
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                            if (x + 2 < 8) {
                                if ((backup[x + 2][y] == ' ' || (backup[x + 2][y] != 'p' && backup[x + 2][y] != 'P'
                                        && backup[x + 2][y] != 'N' && backup[x + 2][y] != 'n' && backup[x + 2][y] != 'B'
                                        && backup[x + 2][y] != 'b' && backup[x + 2][y] != 'R' && backup[x + 2][y] != 'r'
                                        && backup[x + 2][y] != 'Q' && backup[x + 2][y] != 'q' && backup[x + 2][y] != 'K'
                                        && backup[x + 2][y] != 'k'))
                                        && (backup[x + 1][y] == ' ' || (backup[x + 1][y] != 'p' && backup[x + 1][y] != 'P'
                                        && backup[x + 1][y] != 'N' && backup[x + 1][y] != 'n' && backup[x + 1][y] != 'B'
                                        && backup[x + 1][y] != 'b' && backup[x + 1][y] != 'R' && backup[x + 1][y] != 'r'
                                        && backup[x + 1][y] != 'Q' && backup[x + 1][y] != 'q' && backup[x + 1][y] != 'K'
                                        && backup[x + 1][y] != 'k')) && x == 1) {
                                    char przechowalnie = backup[x + 2][y];
                                    backup[x + 2][y] = znak;
                                    backup[x][y] = ' ';
                                    wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                    szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                    backup[x][y] = znak;
                                    backup[x + 2][y] = przechowalnie;
                                    if (wynik || all) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 2));
                                        if (szach) {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', backup));
                                        } else {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));
                                        }

                                    }
                                }
                            }
                        }
                        break;
                    case 'q':
                    case 'Q':

                        if ((tura_rywala && znak == 'Q') || (!tura_rywala && znak == 'q')) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            przod = 1;
                            tyl = -1;
                            param_ruch = 1;
                            while (d1 || d2 || d3 || d4
                                    || w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == ' ') {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x + param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x + param_ruch][y + param_ruch] == 'p' || backup[x + param_ruch][y + param_ruch] == 'n'
                                                    || backup[x + param_ruch][y + param_ruch] == 'b' || backup[x + param_ruch][y + param_ruch] == 'r' || backup[x + param_ruch][y + param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x + param_ruch][y + param_ruch] == 'P' || backup[x + param_ruch][y + param_ruch] == 'N'
                                                    || backup[x + param_ruch][y + param_ruch] == 'B' || backup[x + param_ruch][y + param_ruch] == 'R' || backup[x + param_ruch][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == ' ') {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x - param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x - param_ruch][y - param_ruch] == 'p' || backup[x - param_ruch][y - param_ruch] == 'n'
                                                    || backup[x - param_ruch][y - param_ruch] == 'b' || backup[x - param_ruch][y - param_ruch] == 'r' || backup[x - param_ruch][y - param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x - param_ruch][y - param_ruch] == 'P' || backup[x - param_ruch][y - param_ruch] == 'N'
                                                    || backup[x - param_ruch][y - param_ruch] == 'B' || backup[x - param_ruch][y - param_ruch] == 'R' || backup[x - param_ruch][y - param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == ' ') {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x + param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x + param_ruch][y - param_ruch] == 'p' || backup[x + param_ruch][y - param_ruch] == 'n'
                                                    || backup[x + param_ruch][y - param_ruch] == 'b' || backup[x + param_ruch][y - param_ruch] == 'r' || backup[x + param_ruch][y - param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x + param_ruch][y - param_ruch] == 'P' || backup[x + param_ruch][y - param_ruch] == 'N'
                                                    || backup[x + param_ruch][y - param_ruch] == 'B' || backup[x + param_ruch][y - param_ruch] == 'R' || backup[x + param_ruch][y - param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == ' ') {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x - param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x - param_ruch][y + param_ruch] == 'p' || backup[x - param_ruch][y + param_ruch] == 'n'
                                                    || backup[x - param_ruch][y + param_ruch] == 'b' || backup[x - param_ruch][y + param_ruch] == 'r' || backup[x - param_ruch][y + param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x - param_ruch][y + param_ruch] == 'P' || backup[x - param_ruch][y + param_ruch] == 'N'
                                                    || backup[x - param_ruch][y + param_ruch] == 'B' || backup[x - param_ruch][y + param_ruch] == 'R' || backup[x - param_ruch][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == ' ') {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x + param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x + param_ruch][y] == 'p' || backup[x + param_ruch][y] == 'n'
                                                    || backup[x + param_ruch][y] == 'b' || backup[x + param_ruch][y] == 'r' || backup[x + param_ruch][y] == 'q'))
                                                    || (znak == 'q' && (backup[x + param_ruch][y] == 'P' || backup[x + param_ruch][y] == 'N'
                                                    || backup[x + param_ruch][y] == 'B' || backup[x + param_ruch][y] == 'R' || backup[x + param_ruch][y] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == ' ') {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x - param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x - param_ruch][y] == 'p' || backup[x - param_ruch][y] == 'n'
                                                    || backup[x - param_ruch][y] == 'b' || backup[x - param_ruch][y] == 'r' || backup[x - param_ruch][y] == 'q'))
                                                    || (znak == 'q' && (backup[x - param_ruch][y] == 'P' || backup[x - param_ruch][y] == 'N'
                                                    || backup[x - param_ruch][y] == 'B' || backup[x - param_ruch][y] == 'R' || backup[x - param_ruch][y] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == ' ') {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x][y + param_ruch] == 'p' || backup[x][y + param_ruch] == 'n'
                                                    || backup[x][y + param_ruch] == 'b' || backup[x][y + param_ruch] == 'r' || backup[x][y + param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x][y + param_ruch] == 'P' || backup[x][y + param_ruch] == 'N'
                                                    || backup[x][y + param_ruch] == 'B' || backup[x][y + param_ruch] == 'R' || backup[x][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == ' ') {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                            backup[x][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x][y - param_ruch] == 'p' || backup[x][y - param_ruch] == 'n'
                                                    || backup[x][y - param_ruch] == 'b' || backup[x][y - param_ruch] == 'r' || backup[x][y - param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x][y - param_ruch] == 'P' || backup[x][y - param_ruch] == 'N'
                                                    || backup[x][y - param_ruch] == 'B' || backup[x][y - param_ruch] == 'R' || backup[x][y - param_ruch] == 'Q'))) {

                                                char przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                tyl = tyl - 1;
                                param_ruch = param_ruch + 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case 'R':
                    case 'r':
                        if ((tura_rywala != false && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            param_ruch = 1;
                            przod = 1;
                            tyl = -1;
                            while (w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == ' ') {
                                            backup[x + param_ruch][y] = znak;
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x + param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'R' && (backup[x + param_ruch][y] == 'p' || backup[x + param_ruch][y] == 'n'
                                                    || backup[x + param_ruch][y] == 'b' || backup[x + param_ruch][y] == 'r' || backup[x + param_ruch][y] == 'q'))
                                                    || (znak == 'r' && (backup[x + param_ruch][y] == 'P' || backup[x + param_ruch][y] == 'N'
                                                    || backup[x + param_ruch][y] == 'B' || backup[x + param_ruch][y] == 'R' || backup[x + param_ruch][y] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == ' ') {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x - param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'R' && (backup[x - param_ruch][y] == 'p' || backup[x - param_ruch][y] == 'n'
                                                    || backup[x - param_ruch][y] == 'b' || backup[x - param_ruch][y] == 'r' || backup[x - param_ruch][y] == 'q'))
                                                    || (znak == 'r' && (backup[x - param_ruch][y] == 'P' || backup[x - param_ruch][y] == 'N'
                                                    || backup[x - param_ruch][y] == 'B' || backup[x - param_ruch][y] == 'R' || backup[x - param_ruch][y] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == ' ') {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                            backup[x][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'R' && (backup[x][y + param_ruch] == 'p' || backup[x][y + param_ruch] == 'n'
                                                    || backup[x][y + param_ruch] == 'b' || backup[x][y + param_ruch] == 'r' || backup[x][y + param_ruch] == 'q'))
                                                    || (znak == 'r' && (backup[x][y + param_ruch] == 'P' || backup[x][y + param_ruch] == 'N'
                                                    || backup[x][y + param_ruch] == 'B' || backup[x][y + param_ruch] == 'R' || backup[x][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == ' ') {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'R' && (backup[x][y - param_ruch] == 'p' || backup[x][y - param_ruch] == 'n'
                                                    || backup[x][y - param_ruch] == 'b' || backup[x][y - param_ruch] == 'r' || backup[x][y - param_ruch] == 'q'))
                                                    || (znak == 'r' && (backup[x][y - param_ruch] == 'P' || backup[x][y - param_ruch] == 'N'
                                                    || backup[x][y - param_ruch] == 'B' || backup[x][y - param_ruch] == 'R' || backup[x][y - param_ruch] == 'Q'))) {

                                                char przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                                tyl = tyl - 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case 'B':
                    case 'b':
                        if ((tura_rywala != false && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            while (d1 || d2 || d3 || d4) {
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == ' ') {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x + param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'B' && (backup[x + param_ruch][y + param_ruch] == 'p' || backup[x + param_ruch][y + param_ruch] == 'n'
                                                    || backup[x + param_ruch][y + param_ruch] == 'b' || backup[x + param_ruch][y + param_ruch] == 'r' || backup[x + param_ruch][y + param_ruch] == 'q'))
                                                    || (znak == 'b' && (backup[x + param_ruch][y + param_ruch] == 'P' || backup[x + param_ruch][y + param_ruch] == 'N'
                                                    || backup[x + param_ruch][y + param_ruch] == 'B' || backup[x + param_ruch][y + param_ruch] == 'R' || backup[x + param_ruch][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == ' ') {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                            backup[x - param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'B' && (backup[x - param_ruch][y - param_ruch] == 'p' || backup[x - param_ruch][y - param_ruch] == 'n'
                                                    || backup[x - param_ruch][y - param_ruch] == 'b' || backup[x - param_ruch][y - param_ruch] == 'r' || backup[x - param_ruch][y - param_ruch] == 'q'))
                                                    || (znak == 'b' && (backup[x - param_ruch][y - param_ruch] == 'P' || backup[x - param_ruch][y - param_ruch] == 'N'
                                                    || backup[x - param_ruch][y - param_ruch] == 'B' || backup[x - param_ruch][y - param_ruch] == 'R' || backup[x - param_ruch][y - param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == ' ') {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[x + param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'B' && (backup[x + param_ruch][y - param_ruch] == 'p' || backup[x + param_ruch][y - param_ruch] == 'n'
                                                    || backup[x + param_ruch][y - param_ruch] == 'b' || backup[x + param_ruch][y - param_ruch] == 'r' || backup[x + param_ruch][y - param_ruch] == 'q'))
                                                    || (znak == 'b' && (backup[x + param_ruch][y - param_ruch] == 'P' || backup[x + param_ruch][y - param_ruch] == 'N'
                                                    || backup[x + param_ruch][y - param_ruch] == 'B' || backup[x + param_ruch][y - param_ruch] == 'R' || backup[x + param_ruch][y - param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == ' ') {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                            backup[x - param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                }

                                            }
                                        } else {
                                            if ((znak == 'B' && (backup[x - param_ruch][y + param_ruch] == 'p' || backup[x - param_ruch][y + param_ruch] == 'n'
                                                    || backup[x - param_ruch][y + param_ruch] == 'b' || backup[x - param_ruch][y + param_ruch] == 'r' || backup[x - param_ruch][y + param_ruch] == 'q'))
                                                    || (znak == 'b' && (backup[x - param_ruch][y + param_ruch] == 'P' || backup[x - param_ruch][y + param_ruch] == 'N'
                                                    || backup[x - param_ruch][y + param_ruch] == 'B' || backup[x - param_ruch][y + param_ruch] == 'R' || backup[x - param_ruch][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                    }

                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                            }
                        }
                        break;
                    case 'p':
                        if (tura_rywala == false) {
                            if (x == 3 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x - 1][kolumna - 1] = 'p';
                                backup[x][kolumna - 1] = ' ';
                                backup[x][y] = ' ';
                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                backup[x - 1][kolumna - 1] = ' ';
                                backup[x][kolumna - 1] = 'P';
                                backup[x][y] = 'p';
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x - 1));
                                    if (szach == false) {
                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "EP "), 'P', backup));
                                    } else {
                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), 'P', backup));
                                    }

                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x - 1 > -1) {
                                    if (b != 0 && ((backup[x - 1][y + b] == 'N')
                                            || (backup[x - 1][y + b] == 'P')
                                            || (backup[x - 1][y + b] == 'B')
                                            || (backup[x - 1][y + b] == 'R')
                                            || (backup[x - 1][y + b] == 'Q'))) {
                                        char przechowalnie;
                                        przechowalnie = backup[x - 1][y + b];
                                        if (x != 1) {
                                            backup[x - 1][y + b] = znak;
                                            backup[x][y] = ' ';

                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                            backup[x][y] = znak;
                                            backup[x - 1][y + b] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p"
                                                            + "" + x1 + x2 + "x" + y1 + y2 + "--+"), przechowalnie, backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p"
                                                            + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, backup));
                                                }

                                            }
                                        } else {
                                            char[] symbole = {'q', 'r', 'b', 'n'};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x - 1][y + b] = symbole[s];
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                                backup[x][y] = znak;
                                                backup[x - 1][y + b] = przechowalnie;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (""+symbole[s]) + "+"), przechowalnie, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (""+symbole[s]) + " "), przechowalnie, backup));
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x - 1 > -1) {
                                if (backup[x - 1][y] == ' ' || (backup[x - 1][y] != 'p' && backup[x - 1][y] != 'P'
                                        && backup[x - 1][y] != 'N' && backup[x - 1][y] != 'n' && backup[x - 1][y] != 'B'
                                        && backup[x - 1][y] != 'b' && backup[x - 1][y] != 'R' && backup[x - 1][y] != 'r'
                                        && backup[x - 1][y] != 'Q' && backup[x - 1][y] != 'q' && backup[x - 1][y] != 'K'
                                        && backup[x - 1][y] != 'k')) {
                                    char przechowalnie;
                                    przechowalnie = backup[x - 1][y];
                                    if (x != 1) {
                                        backup[x - 1][y] = znak;
                                        backup[x][y] = ' ';
                                        wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                        szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                        backup[x][y] = znak;
                                        backup[x - 1][y] = przechowalnie;
                                        if (wynik || all) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));
                                            }

                                        }
                                    } else {
                                        char[] symbole = {'q', 'r', 'b', 'n'};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x - 1][y] = symbole[s];
                                            backup[x][y] = ' ';
                                            wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                            backup[x][y] = znak;
                                            backup[x - 1][y] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p"
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + (""+symbole[s]) + "+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p"
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + (""+symbole[s]) + " "), ' ', backup));
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                            if (x - 2 > -1) {
                                if ((backup[x - 2][y] == ' ' || (backup[x - 2][y] != 'p' && backup[x - 2][y] != 'P'
                                        && backup[x - 2][y] != 'N' && backup[x - 2][y] != 'n' && backup[x - 2][y] != 'B'
                                        && backup[x - 2][y] != 'b' && backup[x - 2][y] != 'R' && backup[x - 2][y] != 'r'
                                        && backup[x - 2][y] != 'Q' && backup[x - 2][y] != 'q' && backup[x - 2][y] != 'K'
                                        && backup[x - 2][y] != 'k'))
                                        && (backup[x - 1][y] == ' ' || (backup[x - 1][y] != 'p' && backup[x - 1][y] != 'P'
                                        && backup[x - 1][y] != 'N' && backup[x - 1][y] != 'n' && backup[x - 1][y] != 'B'
                                        && backup[x - 1][y] != 'b' && backup[x - 1][y] != 'R' && backup[x - 1][y] != 'r'
                                        && backup[x - 1][y] != 'Q' && backup[x - 1][y] != 'q' && backup[x - 1][y] != 'K'
                                        && backup[x - 1][y] != 'k')) && x == 6) {
                                    char przechowalnie = backup[x - 2][y];
                                    backup[x - 2][y] = znak;
                                    backup[x][y] = ' ';

                                    wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                    szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                    backup[x][y] = znak;
                                    backup[x - 2][y] = przechowalnie;
                                    if (wynik || all) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 2));
                                        if (szach) {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', backup));
                                        } else {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));
                                        }

                                    }
                                }
                            }
                        }
                        break;
                    case 'n':
                    case 'N':
                        if ((tura_rywala != false && znak == 'N') || (tura_rywala == false && znak == 'n')) {
                            for (int i = -2; i < 3; i++) {
                                for (int j = -2; j < 3; j++) {
                                    if (x + i > -1 && x + i < 8 && y + j > -1 && y + j < 8) {
                                        if (Math.abs(j) - Math.abs(i) != 0 && i != 0 && j != 0) {
                                            if ((znak == 'n'
                                                    && ((backup[x + i][y + j] == ' '
                                                    || backup[x + i][y + j] == 'P'
                                                    || backup[x + i][y + j] == 'N'
                                                    || backup[x + i][y + j] == 'B'
                                                    || backup[x + i][y + j] == 'R'
                                                    || backup[x + i][y + j] == 'Q')
                                                    || (backup[x + i][y + j] != 'p'
                                                    && backup[x + i][y + j] != 'n'
                                                    && backup[x + i][y + j] != 'b'
                                                    && backup[x + i][y + j] != 'r'
                                                    && backup[x + i][y + j] != 'q'
                                                    && backup[x + i][y + j] != 'k'
                                                    && backup[x + i][y + j] != 'K')))
                                                    || (znak == 'N'
                                                    && ((backup[x + i][y + j] == ' '
                                                    || backup[x + i][y + j] == 'p'
                                                    || backup[x + i][y + j] == 'n'
                                                    || backup[x + i][y + j] == 'b'
                                                    || backup[x + i][y + j] == 'r'
                                                    || backup[x + i][y + j] == 'q')
                                                    || (backup[x + i][y + j] != 'P'
                                                    && backup[x + i][y + j] != 'N'
                                                    && backup[x + i][y + j] != 'B'
                                                    && backup[x + i][y + j] != 'R'
                                                    && backup[x + i][y + j] != 'Q'
                                                    && backup[x + i][y + j] != 'K'
                                                    && backup[x + i][y + j] != 'k')))) {
                                                char przechowalnia = backup[x + i][y + j];
                                                backup[x + i][y + j] = znak;
                                                backup[x][y] = ' ';

                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                                backup[x][y] = znak;
                                                backup[x + i][y + j] = przechowalnia;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przechowalnia == ' ') {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "N" : "n") + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "N" : "n") + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                        }

                                                    } else {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "N" : "n") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "N" : "n") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'K':
                        if (tura_rywala != false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == ' '
                                                    || (backup[x + i][y + j] == 'p' || backup[x + i][y + j] == 'n' || backup[x + i][y + j] == 'b' || backup[x + i][y + j] == 'r' || backup[x + i][y + j] == 'q')) {
                                                char przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = 'K';
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = 'K';
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == ' ') {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                        }

                                                    } else {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przech, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech, backup));
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!RuchZagrozenie_kontrola.szach((backup), true)) {
                                if (kingrochB) {
                                    if (whiteright && (backup[0][5] == ' ')
                                            && (backup[0][6] == ' ')
                                            && (backup[0][4] == 'K')
                                            && (backup[0][7] == 'R')) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r < 3; r++) {
                                            backup[0][4] = ' ';
                                            backup[0][4 + r] = 'K';
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((backup), true);
                                            backup[0][4] = 'K';
                                            backup[0][4 + r] = ' ';
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[0][4] = ' ';
                                            backup[0][7] = ' ';
                                            backup[0][5] = 'R';
                                            backup[0][6] = 'K';
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[0][4] = 'K';
                                            backup[0][7] = 'R';
                                            backup[0][5] = ' ';
                                            backup[0][6] = ' ';
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O    +"),
                                                        ' ', backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O     "),
                                                        ' ', backup));
                                            }
                                        }
                                    }
                                    if (whiteleft && (backup[0][3] == ' ')
                                            && (backup[0][2] == ' ')
                                            && (backup[0][1] == ' ')
                                            && (backup[0][4] == 'K')
                                            && (backup[0][0] == 'R')) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r <= 2; r++) {
                                            backup[0][4] = ' ';
                                            backup[0][4 - r] = 'K';
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((backup), true);
                                            backup[0][4] = 'K';
                                            backup[0][4 - r] = ' ';
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[0][4] = ' ';
                                            backup[0][0] = ' ';
                                            backup[0][3] = 'R';
                                            backup[0][2] = 'K';
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[0][4] = 'K';
                                            backup[0][0] = 'R';
                                            backup[0][3] = ' ';
                                            backup[0][2] = ' ';
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O-O  +"),
                                                        ' ', backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O-O   "),
                                                        ' ', backup));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'k':
                        if (tura_rywala == false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == ' '
                                                    || (backup[x + i][y + j] == 'P' || backup[x + i][y + j] == 'N' || backup[x + i][y + j] == 'B' || backup[x + i][y + j] == 'R' || backup[x + i][y + j] == 'Q')) {
                                                char przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = 'k';
                                                backup[x][y] = ' ';
                                                wynik = (all || RuchZagrozenie_kontrola.szach((backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = 'k';
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == ' ') {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                        }

                                                    } else {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przech, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech, backup));
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!RuchZagrozenie_kontrola.szach((backup), false)) {
                                if (kingrochC) {
                                    if (blackright && (backup[7][5] == ' ')
                                            && (backup[7][6] == ' ')
                                            && (backup[7][4] == 'k')
                                            && (backup[7][7] == 'r')) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r < 3; r++) {
                                            backup[7][4] = ' ';
                                            backup[7][4 + r] = 'k';
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((backup), false);
                                            backup[7][4] = 'k';
                                            backup[7][4 + r] = ' ';
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[7][4] = ' ';
                                            backup[7][7] = ' ';
                                            backup[7][5] = 'r';
                                            backup[7][6] = 'k';
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[7][4] = 'k';
                                            backup[7][7] = 'r';
                                            backup[7][5] = ' ';
                                            backup[7][6] = ' ';
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O    +"),
                                                        ' ', backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O     "),
                                                        ' ', backup));
                                            }

                                        }
                                    }
                                    if (blackleft && (backup[7][3] == ' ')
                                            && (backup[7][2] == ' ')
                                            && (backup[7][1] == ' ')
                                            && (backup[7][4] == 'k')
                                            && (backup[7][0] == 'r')) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r <= 2; r++) {
                                            backup[7][4] = ' ';
                                            backup[7][4 - r] = 'k';
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((backup), false);
                                            backup[7][4] = 'k';
                                            backup[7][4 - r] = ' ';
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[7][4] = ' ';
                                            backup[7][0] = ' ';
                                            backup[7][3] = 'r';
                                            backup[7][2] = 'k';
                                            szach = RuchZagrozenie_kontrola.szach((backup), !tura_rywala);
                                            backup[7][4] = 'k';
                                            backup[7][0] = 'r';
                                            backup[7][3] = ' ';
                                            backup[7][2] = ' ';
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O-O  +"),
                                                        ' ', backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O-O   "),
                                                        ' ', backup));
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;
                }

            }//System.out.println("");

        }

        try {
            Collections.sort(lista_dopuszcalnych_Ruchow);
        } catch (Exception e) {
            System.out.println(e);
        }
        return (lista_dopuszcalnych_Ruchow);

    }

    static Collection<Ruch> generuj_posuniecia(char[][] ust, boolean tura_rywala, boolean przelotcan,
            boolean blackleft, boolean blackright, boolean whiteleft, boolean whiteright,
            boolean kingrochB, boolean kingrochC, int kolumna, boolean konkret, char znak_start, int[] start, boolean all,
            boolean mgla
    ) {
        List<Ruch> lista_dopuszcalnych_Ruchow = new ArrayList<>();
        char[][] backup = new char[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                backup[x][y] = ust[x][y];
            }
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (backup[x][y] == ' ') {
                    continue;
                }
                boolean szach;
                boolean wynik;
                char znak = backup[x][y];

                int param_ruch = 1;
                int przod;
                int tyl;
                boolean w1, w2, w3, w4, d1, d2, d3, d4;
                // //System.out.print("["+x+" "+y+"]"+znak);
                switch (znak) {
                    case 'P':
                        if (tura_rywala != false) {
                            if (x == 4 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x + 1][kolumna - 1] = 'P';
                                backup[x][kolumna - 1] = ' ';
                                backup[x][y] = ' ';

                                if ((obecnosc(backup))) {
                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                } else {
                                    wynik = false;
                                    szach = false;
                                }

                                backup[x + 1][kolumna - 1] = ' ';
                                backup[x][kolumna - 1] = 'p';
                                backup[x][y] = 'P';
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x + 1));
                                    if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                        if (szach == false) {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP "), 'p', backup));
                                        } else {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), 'p', backup));
                                        }
                                    }
                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x + 1 < 8) {
                                    if (b != 0 && ((backup[x + 1][y + b] == 'n')
                                            || (backup[x + 1][y + b] == 'p')
                                            || (backup[x + 1][y + b] == 'b')
                                            || (backup[x + 1][y + b] == 'r')
                                            || (backup[x + 1][y + b] == 'q')
                                            || (backup[x + 1][y + b] == 'k' && mgla))) {
                                        char przechowalnie;
                                        przechowalnie = backup[x + 1][y + b];
                                        if (x != 6) {
                                            backup[x + 1][y + b] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x + 1][y + b] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "--+"), przechowalnie, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            char[] symbole = {'Q', 'R', 'B', 'N'};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x + 1][y + b] = symbole[s];
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x + 1][y + b] = przechowalnie;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                    if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + "+"), przechowalnie, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + " "), przechowalnie, backup));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x + 1 < 8) {
                                if (backup[x + 1][y] == ' ' || (backup[x + 1][y] != 'p' && backup[x + 1][y] != 'P'
                                        && backup[x + 1][y] != 'N' && backup[x + 1][y] != 'n' && backup[x + 1][y] != 'B'
                                        && backup[x + 1][y] != 'b' && backup[x + 1][y] != 'R' && backup[x + 1][y] != 'r'
                                        && backup[x + 1][y] != 'Q' && backup[x + 1][y] != 'q' && backup[x + 1][y] != 'K'
                                        && backup[x + 1][y] != 'k')) {
                                    char przechowalnie;
                                    przechowalnie = backup[x + 1][y];
                                    if (x != 6) {
                                        backup[x + 1][y] = znak;
                                        backup[x][y] = ' ';
                                        if ((obecnosc(backup))) {
                                            wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        } else {
                                            wynik = false;
                                            szach = false;
                                        }

                                        backup[x][y] = znak;
                                        backup[x + 1][y] = przechowalnie;
                                        if (wynik || all) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak)
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak)
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));
                                                }
                                            }
                                        }
                                    } else {
                                        char[] symbole = {'Q', 'R', 'B', 'N'};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x + 1][y] = symbole[s];
                                            backup[x][y] = ' ';
                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x + 1][y] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                                if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + "+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + " "), ' ', backup));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (x + 2 < 8) {
                                if ((backup[x + 2][y] == ' ' || (backup[x + 2][y] != 'p' && backup[x + 2][y] != 'P'
                                        && backup[x + 2][y] != 'N' && backup[x + 2][y] != 'n' && backup[x + 2][y] != 'B'
                                        && backup[x + 2][y] != 'b' && backup[x + 2][y] != 'R' && backup[x + 2][y] != 'r'
                                        && backup[x + 2][y] != 'Q' && backup[x + 2][y] != 'q' && backup[x + 2][y] != 'K'
                                        && backup[x + 2][y] != 'k'))
                                        && (backup[x + 1][y] == ' ' || (backup[x + 1][y] != 'p' && backup[x + 1][y] != 'P'
                                        && backup[x + 1][y] != 'N' && backup[x + 1][y] != 'n' && backup[x + 1][y] != 'B'
                                        && backup[x + 1][y] != 'b' && backup[x + 1][y] != 'R' && backup[x + 1][y] != 'r'
                                        && backup[x + 1][y] != 'Q' && backup[x + 1][y] != 'q' && backup[x + 1][y] != 'K'
                                        && backup[x + 1][y] != 'k')) && x == 1) {
                                    char przechowalnie = backup[x + 2][y];
                                    backup[x + 2][y] = znak;
                                    backup[x][y] = ' ';

                                    if ((obecnosc(backup))) {
                                        wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                        szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                    } else {
                                        wynik = false;
                                        szach = false;
                                    }

                                    backup[x][y] = znak;
                                    backup[x + 2][y] = przechowalnie;
                                    if (wynik || all) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 2));
                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'q':
                    case 'Q':

                        if ((tura_rywala != false && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            przod = 1;
                            tyl = -1;
                            param_ruch = 1;
                            while (d1 || d2 || d3 || d4
                                    || w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == ' ') {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x + param_ruch][y + param_ruch] == 'p' || backup[x + param_ruch][y + param_ruch] == 'n'
                                                    || backup[x + param_ruch][y + param_ruch] == 'b' || backup[x + param_ruch][y + param_ruch] == 'r' || backup[x + param_ruch][y + param_ruch] == 'q'
                                                    || (mgla && backup[x + param_ruch][y + param_ruch] == 'k')))
                                                    || (znak == 'q' && (backup[x + param_ruch][y + param_ruch] == 'P' || backup[x + param_ruch][y + param_ruch] == 'N'
                                                    || backup[x + param_ruch][y + param_ruch] == 'B' || backup[x + param_ruch][y + param_ruch] == 'R' || backup[x + param_ruch][y + param_ruch] == 'Q'
                                                    || (mgla && backup[x + param_ruch][y + param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == ' ') {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x - param_ruch][y - param_ruch] == 'p' || backup[x - param_ruch][y - param_ruch] == 'n'
                                                    || backup[x - param_ruch][y - param_ruch] == 'b' || backup[x - param_ruch][y - param_ruch] == 'r' || backup[x - param_ruch][y - param_ruch] == 'q'
                                                    || (mgla && backup[x - param_ruch][y - param_ruch] == 'k')))
                                                    || (znak == 'q' && (backup[x - param_ruch][y - param_ruch] == 'P' || backup[x - param_ruch][y - param_ruch] == 'N'
                                                    || backup[x - param_ruch][y - param_ruch] == 'B' || backup[x - param_ruch][y - param_ruch] == 'R' || backup[x - param_ruch][y - param_ruch] == 'Q'
                                                    || (mgla && backup[x - param_ruch][y - param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == ' ') {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x + param_ruch][y - param_ruch] == 'p' || backup[x + param_ruch][y - param_ruch] == 'n'
                                                    || backup[x + param_ruch][y - param_ruch] == 'b' || backup[x + param_ruch][y - param_ruch] == 'r' || backup[x + param_ruch][y - param_ruch] == 'q'
                                                    || (mgla && backup[x + param_ruch][y - param_ruch] == 'k')))
                                                    || (znak == 'q' && (backup[x + param_ruch][y - param_ruch] == 'P' || backup[x + param_ruch][y - param_ruch] == 'N'
                                                    || backup[x + param_ruch][y - param_ruch] == 'B' || backup[x + param_ruch][y - param_ruch] == 'R' || backup[x + param_ruch][y - param_ruch] == 'Q'
                                                    || (mgla && backup[x + param_ruch][y - param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == ' ') {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x - param_ruch][y + param_ruch] == 'p' || backup[x - param_ruch][y + param_ruch] == 'n'
                                                    || backup[x - param_ruch][y + param_ruch] == 'b' || backup[x - param_ruch][y + param_ruch] == 'r' || backup[x - param_ruch][y + param_ruch] == 'q'
                                                    || (mgla && backup[x - param_ruch][y + param_ruch] == 'k')))
                                                    || (znak == 'q' && (backup[x - param_ruch][y + param_ruch] == 'P' || backup[x - param_ruch][y + param_ruch] == 'N'
                                                    || backup[x - param_ruch][y + param_ruch] == 'B' || backup[x - param_ruch][y + param_ruch] == 'R' || backup[x - param_ruch][y + param_ruch] == 'Q'
                                                    || (mgla && backup[x - param_ruch][y + param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == ' ') {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x + param_ruch][y] == 'p' || backup[x + param_ruch][y] == 'n'
                                                    || backup[x + param_ruch][y] == 'b' || backup[x + param_ruch][y] == 'r' || backup[x + param_ruch][y] == 'q'
                                                    || (mgla && backup[x + param_ruch][y] == 'k')))
                                                    || (znak == 'q' && (backup[x + param_ruch][y] == 'P' || backup[x + param_ruch][y] == 'N'
                                                    || backup[x + param_ruch][y] == 'B' || backup[x + param_ruch][y] == 'R' || backup[x + param_ruch][y] == 'Q'
                                                    || (mgla && backup[x + param_ruch][y] == 'K')))) {
                                                char przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == ' ') {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x - param_ruch][y] == 'p' || backup[x - param_ruch][y] == 'n'
                                                    || backup[x - param_ruch][y] == 'b' || backup[x - param_ruch][y] == 'r' || backup[x - param_ruch][y] == 'q'
                                                    || (mgla && backup[x - param_ruch][y] == 'k')))
                                                    || (znak == 'q' && (backup[x - param_ruch][y] == 'P' || backup[x - param_ruch][y] == 'N'
                                                    || backup[x - param_ruch][y] == 'B' || backup[x - param_ruch][y] == 'R' || backup[x - param_ruch][y] == 'Q'
                                                    || (mgla && backup[x - param_ruch][y] == 'k')))) {
                                                char przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == ' ') {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x][y + param_ruch] == 'p' || backup[x][y + param_ruch] == 'n'
                                                    || backup[x][y + param_ruch] == 'b' || backup[x][y + param_ruch] == 'r' || backup[x][y + param_ruch] == 'q'
                                                    || (mgla && backup[x][y + param_ruch] == 'k')))
                                                    || (znak == 'q' && (backup[x][y + param_ruch] == 'P' || backup[x][y + param_ruch] == 'N'
                                                    || backup[x][y + param_ruch] == 'B' || backup[x][y + param_ruch] == 'R' || backup[x][y + param_ruch] == 'Q'
                                                    || (mgla && backup[x][y + param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == ' ') {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'Q' && (backup[x][y - param_ruch] == 'p' || backup[x][y - param_ruch] == 'n'
                                                    || backup[x][y - param_ruch] == 'b' || backup[x][y - param_ruch] == 'r' || backup[x][y - param_ruch] == 'q'
                                                    || (mgla && backup[x][y - param_ruch] == 'k')))
                                                    || (znak == 'q' && (backup[x][y - param_ruch] == 'P' || backup[x][y - param_ruch] == 'N'
                                                    || backup[x][y - param_ruch] == 'B' || backup[x][y - param_ruch] == 'R' || backup[x][y - param_ruch] == 'Q'
                                                    || (mgla && backup[x][y - param_ruch] == 'K')))) {

                                                char przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                tyl = tyl - 1;
                                param_ruch = param_ruch + 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case 'R':
                    case 'r':
                        if ((tura_rywala != false && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            param_ruch = 1;
                            przod = 1;
                            tyl = -1;
                            while (w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == ' ') {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'R' && (backup[x + param_ruch][y] == 'p' || backup[x + param_ruch][y] == 'n'
                                                    || backup[x + param_ruch][y] == 'b' || backup[x + param_ruch][y] == 'r' || backup[x + param_ruch][y] == 'q'
                                                    || (mgla && backup[x + param_ruch][y] == 'k')))
                                                    || (znak == 'r' && (backup[x + param_ruch][y] == 'P' || backup[x + param_ruch][y] == 'N'
                                                    || backup[x + param_ruch][y] == 'B' || backup[x + param_ruch][y] == 'R' || backup[x + param_ruch][y] == 'Q'
                                                    || (mgla && backup[x + param_ruch][y] == 'K')))) {
                                                char przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == ' ') {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'R' && (backup[x - param_ruch][y] == 'p' || backup[x - param_ruch][y] == 'n'
                                                    || backup[x - param_ruch][y] == 'b' || backup[x - param_ruch][y] == 'r' || backup[x - param_ruch][y] == 'q'
                                                    || (mgla && backup[x - param_ruch][y] == 'k')))
                                                    || (znak == 'r' && (backup[x - param_ruch][y] == 'P' || backup[x - param_ruch][y] == 'N'
                                                    || backup[x - param_ruch][y] == 'B' || backup[x - param_ruch][y] == 'R' || backup[x - param_ruch][y] == 'Q'
                                                    || (mgla && backup[x - param_ruch][y] == 'k')))) {
                                                char przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == ' ') {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'R' && (backup[x][y + param_ruch] == 'p' || backup[x][y + param_ruch] == 'n'
                                                    || backup[x][y + param_ruch] == 'b' || backup[x][y + param_ruch] == 'r' || backup[x][y + param_ruch] == 'q'
                                                    || (mgla && backup[x][y + param_ruch] == 'k')))
                                                    || (znak == 'r' && (backup[x][y + param_ruch] == 'P' || backup[x][y + param_ruch] == 'N'
                                                    || backup[x][y + param_ruch] == 'B' || backup[x][y + param_ruch] == 'R' || backup[x][y + param_ruch] == 'Q'
                                                    || (mgla && backup[x][y + param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == ' ') {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'R' && (backup[x][y - param_ruch] == 'p' || backup[x][y - param_ruch] == 'n'
                                                    || backup[x][y - param_ruch] == 'b' || backup[x][y - param_ruch] == 'r' || backup[x][y - param_ruch] == 'q'
                                                    || (mgla && backup[x][y - param_ruch] == 'k')))
                                                    || (znak == 'r' && (backup[x][y - param_ruch] == 'P' || backup[x][y - param_ruch] == 'N'
                                                    || backup[x][y - param_ruch] == 'B' || backup[x][y - param_ruch] == 'R' || backup[x][y - param_ruch] == 'Q'
                                                    || (mgla && backup[x][y - param_ruch] == 'K')))) {

                                                char przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                                tyl = tyl - 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case 'B':
                    case 'b':
                        if ((tura_rywala != false && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            while (d1 || d2 || d3 || d4) {
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == ' ') {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'B' && (backup[x + param_ruch][y + param_ruch] == 'p' || backup[x + param_ruch][y + param_ruch] == 'n'
                                                    || backup[x + param_ruch][y + param_ruch] == 'b' || backup[x + param_ruch][y + param_ruch] == 'r' || backup[x + param_ruch][y + param_ruch] == 'q'
                                                    || (mgla && backup[x + param_ruch][y + param_ruch] == 'k')))
                                                    || (znak == 'b' && (backup[x + param_ruch][y + param_ruch] == 'P' || backup[x + param_ruch][y + param_ruch] == 'N'
                                                    || backup[x + param_ruch][y + param_ruch] == 'B' || backup[x + param_ruch][y + param_ruch] == 'R' || backup[x + param_ruch][y + param_ruch] == 'Q'
                                                    || (mgla && backup[x + param_ruch][y + param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == ' ') {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'B' && (backup[x - param_ruch][y - param_ruch] == 'p' || backup[x - param_ruch][y - param_ruch] == 'n'
                                                    || backup[x - param_ruch][y - param_ruch] == 'b' || backup[x - param_ruch][y - param_ruch] == 'r' || backup[x - param_ruch][y - param_ruch] == 'q'
                                                    || (mgla && backup[x - param_ruch][y - param_ruch] == 'k')))
                                                    || (znak == 'b' && (backup[x - param_ruch][y - param_ruch] == 'P' || backup[x - param_ruch][y - param_ruch] == 'N'
                                                    || backup[x - param_ruch][y - param_ruch] == 'B' || backup[x - param_ruch][y - param_ruch] == 'R' || backup[x - param_ruch][y - param_ruch] == 'Q'
                                                    || (mgla && backup[x - param_ruch][y - param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == ' ') {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';
                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'B' && (backup[x + param_ruch][y - param_ruch] == 'p' || backup[x + param_ruch][y - param_ruch] == 'n'
                                                    || backup[x + param_ruch][y - param_ruch] == 'b' || backup[x + param_ruch][y - param_ruch] == 'r' || backup[x + param_ruch][y - param_ruch] == 'q'
                                                    || (mgla && backup[x + param_ruch][y - param_ruch] == 'k')))
                                                    || (znak == 'b' && (backup[x + param_ruch][y - param_ruch] == 'P' || backup[x + param_ruch][y - param_ruch] == 'N'
                                                    || backup[x + param_ruch][y - param_ruch] == 'B' || backup[x + param_ruch][y - param_ruch] == 'R' || backup[x + param_ruch][y - param_ruch] == 'Q'
                                                    || (mgla && backup[x + param_ruch][y - param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == ' ') {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == 'B' && (backup[x - param_ruch][y + param_ruch] == 'p' || backup[x - param_ruch][y + param_ruch] == 'n'
                                                    || backup[x - param_ruch][y + param_ruch] == 'b' || backup[x - param_ruch][y + param_ruch] == 'r' || backup[x - param_ruch][y + param_ruch] == 'q'
                                                    || (mgla && backup[x - param_ruch][y + param_ruch] == 'k')))
                                                    || (znak == 'b' && (backup[x - param_ruch][y + param_ruch] == 'P' || backup[x - param_ruch][y + param_ruch] == 'N'
                                                    || backup[x - param_ruch][y + param_ruch] == 'B' || backup[x - param_ruch][y + param_ruch] == 'R' || backup[x - param_ruch][y + param_ruch] == 'Q'
                                                    || (mgla && backup[x - param_ruch][y + param_ruch] == 'K')))) {
                                                char przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                            }
                        }
                        break;
                    case 'p':
                        if (tura_rywala == false) {
                            if (x == 3 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x - 1][kolumna - 1] = 'p';
                                backup[x][kolumna - 1] = ' ';
                                backup[x][y] = ' ';

                                if ((obecnosc(backup))) {
                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                } else {
                                    wynik = false;
                                    szach = false;
                                }

                                backup[x - 1][kolumna - 1] = ' ';
                                backup[x][kolumna - 1] = 'P';
                                backup[x][y] = 'p';
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x - 1));
                                    if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                        if (szach == false) {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP "), 'P', backup));
                                        } else {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), 'P', backup));
                                        }
                                    }
                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x - 1 > -1) {
                                    if (b != 0 && ((backup[x - 1][y + b] == 'N')
                                            || (backup[x - 1][y + b] == 'P')
                                            || (backup[x - 1][y + b] == 'B')
                                            || (backup[x - 1][y + b] == 'R')
                                            || (backup[x - 1][y + b] == 'Q')
                                            || (backup[x + 1][y + b] == 'K' && mgla))) {
                                        char przechowalnie;
                                        przechowalnie = backup[x - 1][y + b];
                                        if (x != 1) {
                                            backup[x - 1][y + b] = znak;
                                            backup[x][y] = ' ';

                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x - 1][y + b] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "--+"), przechowalnie, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            char[] symbole = {'q', 'r', 'b', 'n'};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x - 1][y + b] = symbole[s];
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x - 1][y + b] = przechowalnie;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                    if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + "+"), przechowalnie, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + " "), przechowalnie, backup));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x - 1 > -1) {
                                if (backup[x - 1][y] == ' ' || (backup[x - 1][y] != 'p' && backup[x - 1][y] != 'P'
                                        && backup[x - 1][y] != 'N' && backup[x - 1][y] != 'n' && backup[x - 1][y] != 'B'
                                        && backup[x - 1][y] != 'b' && backup[x - 1][y] != 'R' && backup[x - 1][y] != 'r'
                                        && backup[x - 1][y] != 'Q' && backup[x - 1][y] != 'q' && backup[x - 1][y] != 'K'
                                        && backup[x - 1][y] != 'k')) {
                                    char przechowalnie;
                                    przechowalnie = backup[x - 1][y];
                                    if (x != 1) {
                                        backup[x - 1][y] = znak;
                                        backup[x][y] = ' ';
                                        if ((obecnosc(backup))) {
                                            wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        } else {
                                            wynik = false;
                                            szach = false;
                                        }

                                        backup[x][y] = znak;
                                        backup[x - 1][y] = przechowalnie;
                                        if (wynik || all) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak)
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak)
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));
                                                }
                                            }
                                        }
                                    } else {
                                        char[] symbole = {'q', 'r', 'b', 'n'};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x - 1][y] = symbole[s];
                                            backup[x][y] = ' ';
                                            if ((obecnosc(backup))) {
                                                wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x - 1][y] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                                if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + "+"), ' ', backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + " "), ' ', backup));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (x - 2 > -1) {
                                if ((backup[x - 2][y] == ' ' || (backup[x - 2][y] != 'p' && backup[x - 2][y] != 'P'
                                        && backup[x - 2][y] != 'N' && backup[x - 2][y] != 'n' && backup[x - 2][y] != 'B'
                                        && backup[x - 2][y] != 'b' && backup[x - 2][y] != 'R' && backup[x - 2][y] != 'r'
                                        && backup[x - 2][y] != 'Q' && backup[x - 2][y] != 'q' && backup[x - 2][y] != 'K'
                                        && backup[x - 2][y] != 'k'))
                                        && (backup[x - 1][y] == ' ' || (backup[x - 1][y] != 'p' && backup[x - 1][y] != 'P'
                                        && backup[x - 1][y] != 'N' && backup[x - 1][y] != 'n' && backup[x - 1][y] != 'B'
                                        && backup[x - 1][y] != 'b' && backup[x - 1][y] != 'R' && backup[x - 1][y] != 'r'
                                        && backup[x - 1][y] != 'Q' && backup[x - 1][y] != 'q' && backup[x - 1][y] != 'K'
                                        && backup[x - 1][y] != 'k')) && x == 6) {
                                    char przechowalnie = backup[x - 2][y];
                                    backup[x - 2][y] = znak;
                                    backup[x][y] = ' ';

                                    if ((obecnosc(backup))) {
                                        wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                        szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                    } else {
                                        wynik = false;
                                        szach = false;
                                    }

                                    backup[x][y] = znak;
                                    backup[x - 2][y] = przechowalnie;
                                    if (wynik || all) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 2));
                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'n':
                    case 'N':
                        if ((tura_rywala != false && znak == 'N') || (tura_rywala == false && znak == 'n')) {
                            for (int i = -2; i < 3; i++) {
                                for (int j = -2; j < 3; j++) {
                                    if (x + i > -1 && x + i < 8 && y + j > -1 && y + j < 8) {
                                        if (Math.abs(j) - Math.abs(i) != 0 && i != 0 && j != 0) {
                                            if ((znak == 'n'
                                                    && ((backup[x + i][y + j] == ' '
                                                    || backup[x + i][y + j] == 'P'
                                                    || backup[x + i][y + j] == 'N'
                                                    || backup[x + i][y + j] == 'B'
                                                    || backup[x + i][y + j] == 'R'
                                                    || backup[x + i][y + j] == 'Q'
                                                    || (backup[x + i][y + j] == 'K' && mgla))
                                                    || (backup[x + i][y + j] != 'p'
                                                    && backup[x + i][y + j] != 'n'
                                                    && backup[x + i][y + j] != 'b'
                                                    && backup[x + i][y + j] != 'r'
                                                    && backup[x + i][y + j] != 'q'
                                                    && backup[x + i][y + j] != 'k'
                                                    && backup[x + i][y + j] != 'K')))
                                                    || (znak == 'N'
                                                    && ((backup[x + i][y + j] == ' '
                                                    || backup[x + i][y + j] == 'p'
                                                    || backup[x + i][y + j] == 'n'
                                                    || backup[x + i][y + j] == 'b'
                                                    || backup[x + i][y + j] == 'r'
                                                    || backup[x + i][y + j] == 'q'
                                                    || (backup[x + i][y + j] == 'k' && mgla))
                                                    || (backup[x + i][y + j] != 'P'
                                                    && backup[x + i][y + j] != 'N'
                                                    && backup[x + i][y + j] != 'B'
                                                    && backup[x + i][y + j] != 'R'
                                                    && backup[x + i][y + j] != 'Q'
                                                    && backup[x + i][y + j] != 'K'
                                                    && backup[x + i][y + j] != 'k')))) {
                                                char przechowalnia = backup[x + i][y + j];
                                                backup[x + i][y + j] = znak;
                                                backup[x][y] = ' ';

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);

                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x + i][y + j] = przechowalnia;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przechowalnia == ' ') {
                                                        if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                            }
                                                        }
                                                    } else {
                                                        if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'K':
                        if (tura_rywala != false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == ' '
                                                    || (backup[x + i][y + j] == 'p' || backup[x + i][y + j] == 'n'
                                                    || backup[x + i][y + j] == 'b' || backup[x + i][y + j] == 'r'
                                                    || backup[x + i][y + j] == 'q' || (mgla && backup[x + i][y + j] == 'k'))) {
                                                char przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = 'K';
                                                backup[x][y] = ' ';
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);

                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = 'K';
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == ' ') {
                                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                            }
                                                        }
                                                    } else {
                                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przech, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech, backup));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!RuchZagrozenie_kontrola.szach(konwert(backup), true) || mgla) {
                                if (kingrochB) {
                                    if (whiteright && (backup[0][5] == ' ')
                                            && (backup[0][6] == ' ')
                                            && (backup[0][4] == 'K')
                                            && (backup[0][7] == 'R')) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r < 3; r++) {
                                            backup[0][4] = ' ';
                                            backup[0][4 + r] = 'K';
                                            wyniki[r - 1] = !mgla ? !RuchZagrozenie_kontrola.szach(konwert(backup), true) : true;
                                            backup[0][4] = 'K';
                                            backup[0][4 + r] = ' ';
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[0][4] = ' ';
                                            backup[0][7] = ' ';
                                            backup[0][5] = 'R';
                                            backup[0][6] = 'K';
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            backup[0][4] = 'K';
                                            backup[0][7] = 'R';
                                            backup[0][5] = ' ';
                                            backup[0][6] = ' ';
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O    +"),
                                                            ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O     "),
                                                            ' ', backup));
                                                }
                                            }
                                        }
                                    }
                                    if (whiteleft && (backup[0][3] == ' ')
                                            && (backup[0][2] == ' ')
                                            && (backup[0][1] == ' ')
                                            && (backup[0][4] == 'K')
                                            && (backup[0][0] == 'R')) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r <= 2; r++) {
                                            backup[0][4] = ' ';
                                            backup[0][4 - r] = 'K';
                                            wyniki[r - 1] = !mgla ? !RuchZagrozenie_kontrola.szach(konwert(backup), true) : true;
                                            backup[0][4] = 'K';
                                            backup[0][4 - r] = ' ';
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[0][4] = ' ';
                                            backup[0][0] = ' ';
                                            backup[0][3] = 'R';
                                            backup[0][2] = 'K';
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            backup[0][4] = 'K';
                                            backup[0][0] = 'R';
                                            backup[0][3] = ' ';
                                            backup[0][2] = ' ';
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O-O  +"),
                                                            ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O-O   "),
                                                            ' ', backup));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 'k':
                        if (tura_rywala == false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == ' '
                                                    || (backup[x + i][y + j] == 'P' || backup[x + i][y + j] == 'N'
                                                    || backup[x + i][y + j] == 'B' || backup[x + i][y + j] == 'R'
                                                    || backup[x + i][y + j] == 'Q' || (mgla && backup[x + i][y + j] == 'K'))) {
                                                char przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = 'k';
                                                backup[x][y] = ' ';
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || !mgla ? (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false) : true);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = 'k';
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == ' ') {
                                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));
                                                            }
                                                        }
                                                    } else {
                                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przech, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech, backup));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!RuchZagrozenie_kontrola.szach(konwert(backup), false) || mgla) {
                                if (kingrochC) {
                                    if (blackright && (backup[7][5] == ' ')
                                            && (backup[7][6] == ' ')
                                            && (backup[7][4] == 'k')
                                            && (backup[7][7] == 'r')) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r < 3; r++) {
                                            backup[7][4] = ' ';
                                            backup[7][4 + r] = 'k';
                                            wyniki[r - 1] = !mgla ? !RuchZagrozenie_kontrola.szach(konwert(backup), false) : true;
                                            backup[7][4] = 'k';
                                            backup[7][4 + r] = ' ';
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[7][4] = ' ';
                                            backup[7][7] = ' ';
                                            backup[7][5] = 'r';
                                            backup[7][6] = 'k';
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            backup[7][4] = 'k';
                                            backup[7][7] = 'r';
                                            backup[7][5] = ' ';
                                            backup[7][6] = ' ';
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O    +"),
                                                            ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O     "),
                                                            ' ', backup));
                                                }
                                            }
                                        }
                                    }
                                    if (blackleft && (backup[7][3] == ' ')
                                            && (backup[7][2] == ' ')
                                            && (backup[7][1] == ' ')
                                            && (backup[7][4] == 'k')
                                            && (backup[7][0] == 'r')) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r <= 2; r++) {
                                            backup[7][4] = ' ';
                                            backup[7][4 - r] = 'k';
                                            wyniki[r - 1] = !mgla ? !RuchZagrozenie_kontrola.szach(konwert(backup), false) : true;
                                            backup[7][4] = 'k';
                                            backup[7][4 - r] = ' ';
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[7][4] = ' ';
                                            backup[7][0] = ' ';
                                            backup[7][3] = 'r';
                                            backup[7][2] = 'k';
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            backup[7][4] = 'k';
                                            backup[7][0] = 'r';
                                            backup[7][3] = ' ';
                                            backup[7][2] = ' ';
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O-O  +"),
                                                            ' ', backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O-O   "),
                                                            ' ', backup));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }

            }//System.out.println("");

        }

        try {
            Collections.sort(lista_dopuszcalnych_Ruchow);
        } catch (Exception e) {
            System.out.println(e);
        }
        //System.out.println("czas wykonania:"+(System.currentTimeMillis()-czas_start));
        return Collections.unmodifiableCollection(lista_dopuszcalnych_Ruchow);
    }

    private static char[][] konwert(char[][] backup) {
        char[][] pozycja = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (backup[i][j]) {
                    case ' ':
                        pozycja[i][j] = ' ';
                        break;
                    case 'P':
                        pozycja[i][j] = 'P';
                        break;
                    case 'p':
                        pozycja[i][j] = 'p';
                        break;
                    case 'N':
                        pozycja[i][j] = 'N';
                        break;
                    case 'n':
                        pozycja[i][j] = 'n';
                        break;
                    case 'B':
                        pozycja[i][j] = 'B';
                        break;
                    case 'b':
                        pozycja[i][j] = 'b';
                        break;
                    case 'R':
                        pozycja[i][j] = 'R';
                        break;
                    case 'r':
                        pozycja[i][j] = 'r';
                        break;
                    case 'Q':
                        pozycja[i][j] = 'Q';
                        break;
                    case 'q':
                        pozycja[i][j] = 'q';
                        break;
                    case 'K':
                        pozycja[i][j] = 'K';
                        break;
                    case 'k':
                        pozycja[i][j] = 'k';
                        break;
                }
            }
        }
        return pozycja;
    }

    static Collection<RuchA> generuj_posunieciaA(SzachowaArena.figury[][] ust, boolean tura_rywala, boolean przelotcan,
            boolean blackleft, boolean blackright, boolean whiteleft, boolean whiteright,
            boolean kingrochB, boolean kingrochC, int kolumna, boolean konkret, char znak_start, int[] start, boolean all) {
        List<RuchA> lista_dopuszcalnych_Ruchow = new ArrayList<>();
        SzachowaArena.figury[][] backup = new SzachowaArena.figury[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                backup[x][y] = ust[x][y];
            }
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boolean szach;
                boolean wynik;
                szach = false;
                SzachowaArena.figury znak = backup[x][y];

                int param_ruch = 1;
                int przod;
                int tyl;
                boolean w1, w2, w3, w4, d1, d2, d3, d4;
                // //System.out.print("["+x+" "+y+"]"+znak);
                switch (znak) {
                    case BPion:
                        if (tura_rywala != false) {
                            if (x == 4 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x + 1][kolumna - 1] = SzachowaArena.figury.BPion;
                                backup[x][kolumna - 1] = SzachowaArena.figury.pustka;
                                backup[x][y] = SzachowaArena.figury.pustka;

                                if ((obecnosc(backup))) {
                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                } else {
                                    wynik = false;
                                    szach = false;
                                }

                                backup[x + 1][kolumna - 1] = SzachowaArena.figury.pustka;
                                backup[x][kolumna - 1] = SzachowaArena.figury.CPion;
                                backup[x][y] = SzachowaArena.figury.BPion;
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x + 1));
                                    if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                        if (szach == false) {
                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP "), SzachowaArena.figury.CPion, backup));
                                        } else {
                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), SzachowaArena.figury.CPion, backup));
                                        }
                                    }
                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x + 1 < 8) {
                                    if (b != 0 && ((backup[x + 1][y + b] == SzachowaArena.figury.CSkoczek)
                                            || (backup[x + 1][y + b] == SzachowaArena.figury.CPion)
                                            || (backup[x + 1][y + b] == SzachowaArena.figury.CGoniec)
                                            || (backup[x + 1][y + b] == SzachowaArena.figury.CWieza)
                                            || (backup[x + 1][y + b] == SzachowaArena.figury.CHetman)
                                            || (backup[x + 1][y + b] == SzachowaArena.figury.CAmazonka))) {
                                        SzachowaArena.figury przechowalnie;
                                        przechowalnie = backup[x + 1][y + b];
                                        if (x != 6) {
                                            backup[x + 1][y + b] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x + 1][y + b] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "--+"), przechowalnie, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            SzachowaArena.figury[] symbole = {SzachowaArena.figury.BHetman, SzachowaArena.figury.BWieza, SzachowaArena.figury.BGoniec, SzachowaArena.figury.BSkoczek};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x + 1][y + b] = symbole[s];
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x + 1][y + b] = przechowalnie;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                    if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + "+"), przechowalnie, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + " "), przechowalnie, backup));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x + 1 < 8) {
                                if (backup[x + 1][y] == SzachowaArena.figury.pustka || (backup[x + 1][y] != SzachowaArena.figury.CPion && backup[x + 1][y] != SzachowaArena.figury.BPion
                                        && backup[x + 1][y] != SzachowaArena.figury.BSkoczek && backup[x + 1][y] != SzachowaArena.figury.CSkoczek && backup[x + 1][y] != SzachowaArena.figury.BGoniec
                                        && backup[x + 1][y] != SzachowaArena.figury.CGoniec && backup[x + 1][y] != SzachowaArena.figury.BWieza && backup[x + 1][y] != SzachowaArena.figury.CWieza
                                        && backup[x + 1][y] != SzachowaArena.figury.BHetman && backup[x + 1][y] != SzachowaArena.figury.CHetman && backup[x + 1][y] != SzachowaArena.figury.BKrol
                                        && backup[x + 1][y] != SzachowaArena.figury.CKrol && backup[x + 1][y] != SzachowaArena.figury.CAmazonka && backup[x + 1][y] != SzachowaArena.figury.BAmazonka)) {
                                    SzachowaArena.figury przechowalnie;
                                    przechowalnie = backup[x + 1][y];
                                    if (x != 6) {
                                        backup[x + 1][y] = znak;
                                        backup[x][y] = SzachowaArena.figury.pustka;
                                        if ((obecnosc(backup))) {
                                            wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        } else {
                                            wynik = false;
                                            szach = false;
                                        }

                                        backup[x][y] = znak;
                                        backup[x + 1][y] = przechowalnie;
                                        if (wynik || all) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                }
                                            }
                                        }
                                    } else {
                                        SzachowaArena.figury[] symbole = {SzachowaArena.figury.CAmazonka, SzachowaArena.figury.CWieza, SzachowaArena.figury.CGoniec, SzachowaArena.figury.CSkoczek};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x + 1][y] = symbole[s];
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x + 1][y] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                                if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + "+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + " "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (x + 2 < 8) {
                                if ((backup[x + 2][y] == SzachowaArena.figury.pustka || (backup[x + 2][y] != SzachowaArena.figury.CPion && backup[x + 2][y] != SzachowaArena.figury.BPion
                                        && backup[x + 2][y] != SzachowaArena.figury.BSkoczek && backup[x + 2][y] != SzachowaArena.figury.CSkoczek && backup[x + 2][y] != SzachowaArena.figury.BGoniec
                                        && backup[x + 2][y] != SzachowaArena.figury.CGoniec && backup[x + 2][y] != SzachowaArena.figury.BWieza && backup[x + 2][y] != SzachowaArena.figury.CWieza
                                        && backup[x + 2][y] != SzachowaArena.figury.BHetman && backup[x + 2][y] != SzachowaArena.figury.CHetman && backup[x + 2][y] != SzachowaArena.figury.BKrol
                                        && backup[x + 2][y] != SzachowaArena.figury.CKrol && backup[x + 2][y] != SzachowaArena.figury.CAmazonka && backup[x + 2][y] != SzachowaArena.figury.BAmazonka))
                                        && (backup[x + 1][y] == SzachowaArena.figury.pustka || (backup[x + 1][y] != SzachowaArena.figury.CPion && backup[x + 1][y] != SzachowaArena.figury.BPion
                                        && backup[x + 1][y] != SzachowaArena.figury.BSkoczek && backup[x + 1][y] != SzachowaArena.figury.CSkoczek && backup[x + 1][y] != SzachowaArena.figury.BGoniec
                                        && backup[x + 1][y] != SzachowaArena.figury.CGoniec && backup[x + 1][y] != SzachowaArena.figury.BWieza && backup[x + 1][y] != SzachowaArena.figury.CWieza
                                        && backup[x + 1][y] != SzachowaArena.figury.BHetman && backup[x + 1][y] != SzachowaArena.figury.CHetman && backup[x + 1][y] != SzachowaArena.figury.BKrol
                                        && backup[x + 1][y] != SzachowaArena.figury.CKrol && backup[x + 1][y] != SzachowaArena.figury.CAmazonka && backup[x + 1][y] != SzachowaArena.figury.BAmazonka)) && x == 1) {
                                    SzachowaArena.figury przechowalnie = backup[x + 2][y];
                                    backup[x + 2][y] = znak;
                                    backup[x][y] = SzachowaArena.figury.pustka;

                                    if ((obecnosc(backup))) {
                                        wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                        szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                    } else {
                                        wynik = false;
                                        szach = false;
                                    }

                                    backup[x][y] = znak;
                                    backup[x + 2][y] = przechowalnie;
                                    if (wynik || all) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 2));
                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case BAmazonka:
                    case CAmazonka:
                        if ((tura_rywala != false && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            przod = 1;
                            tyl = -1;
                            param_ruch = 1;
                            while (d1 || d2 || d3 || d4
                                    || w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                System.out.println("Amazonka: " + param_ruch + " " + (RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false));
                                                System.out.println("Amazonka: " + param_ruch + " " + szach);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y + param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BAmazonka && (backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CPion || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CGoniec || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CWieza || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CAmazonka))
                                                    || (znak == SzachowaArena.figury.CAmazonka && (backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BPion || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BGoniec || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BWieza || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BAmazonka))) {
                                                SzachowaArena.figury przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y - param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BAmazonka && (backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CPion || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CGoniec || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CWieza || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CAmazonka))
                                                    || (znak == SzachowaArena.figury.CAmazonka && (backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BPion || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BGoniec || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BWieza || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BAmazonka))) {
                                                SzachowaArena.figury przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y - param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BAmazonka && (backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CPion || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CGoniec || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CWieza || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CAmazonka))
                                                    || (znak == SzachowaArena.figury.CAmazonka && (backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BPion || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BGoniec || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BWieza || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BAmazonka))) {
                                                SzachowaArena.figury przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y + param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BAmazonka && (backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CPion || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CGoniec || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CWieza || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CAmazonka))
                                                    || (znak == SzachowaArena.figury.CAmazonka && (backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BPion || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BGoniec || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BWieza || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BAmazonka))) {
                                                SzachowaArena.figury przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == SzachowaArena.figury.pustka) {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BAmazonka && (backup[x + param_ruch][y] == SzachowaArena.figury.CPion || backup[x + param_ruch][y] == SzachowaArena.figury.CSkoczek
                                                    || backup[x + param_ruch][y] == SzachowaArena.figury.CGoniec || backup[x + param_ruch][y] == SzachowaArena.figury.CWieza || backup[x + param_ruch][y] == SzachowaArena.figury.CAmazonka))
                                                    || (znak == SzachowaArena.figury.CAmazonka && (backup[x + param_ruch][y] == SzachowaArena.figury.BPion || backup[x + param_ruch][y] == SzachowaArena.figury.BSkoczek
                                                    || backup[x + param_ruch][y] == SzachowaArena.figury.BGoniec || backup[x + param_ruch][y] == SzachowaArena.figury.BWieza || backup[x + param_ruch][y] == SzachowaArena.figury.BAmazonka))) {
                                                SzachowaArena.figury przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == SzachowaArena.figury.pustka) {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BAmazonka && (backup[x - param_ruch][y] == SzachowaArena.figury.CPion || backup[x - param_ruch][y] == SzachowaArena.figury.CSkoczek
                                                    || backup[x - param_ruch][y] == SzachowaArena.figury.CGoniec || backup[x - param_ruch][y] == SzachowaArena.figury.CWieza || backup[x - param_ruch][y] == SzachowaArena.figury.CAmazonka))
                                                    || (znak == SzachowaArena.figury.CAmazonka && (backup[x - param_ruch][y] == SzachowaArena.figury.BPion || backup[x - param_ruch][y] == SzachowaArena.figury.BSkoczek
                                                    || backup[x - param_ruch][y] == SzachowaArena.figury.BGoniec || backup[x - param_ruch][y] == SzachowaArena.figury.BWieza || backup[x - param_ruch][y] == SzachowaArena.figury.BAmazonka))) {
                                                SzachowaArena.figury przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y + param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BAmazonka && (backup[x][y + param_ruch] == SzachowaArena.figury.CPion || backup[x][y + param_ruch] == SzachowaArena.figury.CSkoczek
                                                    || backup[x][y + param_ruch] == SzachowaArena.figury.CGoniec || backup[x][y + param_ruch] == SzachowaArena.figury.CWieza || backup[x][y + param_ruch] == SzachowaArena.figury.CAmazonka))
                                                    || (znak == SzachowaArena.figury.CAmazonka && (backup[x][y + param_ruch] == SzachowaArena.figury.BPion || backup[x][y + param_ruch] == SzachowaArena.figury.BSkoczek
                                                    || backup[x][y + param_ruch] == SzachowaArena.figury.BGoniec || backup[x][y + param_ruch] == SzachowaArena.figury.BWieza || backup[x][y + param_ruch] == SzachowaArena.figury.BAmazonka))) {
                                                SzachowaArena.figury przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y - param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BAmazonka && (backup[x][y - param_ruch] == SzachowaArena.figury.CPion || backup[x][y - param_ruch] == SzachowaArena.figury.CSkoczek
                                                    || backup[x][y - param_ruch] == SzachowaArena.figury.CGoniec || backup[x][y - param_ruch] == SzachowaArena.figury.CWieza || backup[x][y - param_ruch] == SzachowaArena.figury.CAmazonka))
                                                    || (znak == SzachowaArena.figury.CAmazonka && (backup[x][y - param_ruch] == SzachowaArena.figury.BPion || backup[x][y - param_ruch] == SzachowaArena.figury.BSkoczek
                                                    || backup[x][y - param_ruch] == SzachowaArena.figury.BGoniec || backup[x][y - param_ruch] == SzachowaArena.figury.BWieza || backup[x][y - param_ruch] == SzachowaArena.figury.BAmazonka))) {

                                                SzachowaArena.figury przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                tyl = tyl - 1;
                                param_ruch = param_ruch + 1;
                                przod = przod + 1;
                            }
                        }
                        if ((tura_rywala != false && znak == SzachowaArena.figury.BAmazonka) || (tura_rywala == false && znak == SzachowaArena.figury.CAmazonka)) {
                            for (int i = -2; i < 3; i++) {
                                for (int j = -2; j < 3; j++) {
                                    if (x + i > -1 && x + i < 8 && y + j > -1 && y + j < 8) {
                                        if (Math.abs(j) - Math.abs(i) != 0 && i != 0 && j != 0) {
                                            if ((znak == SzachowaArena.figury.CAmazonka
                                                    && ((backup[x + i][y + j] == SzachowaArena.figury.pustka
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BPion
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BSkoczek
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BGoniec
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BWieza
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BAmazonka
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BHetman)
                                                    || (backup[x + i][y + j] != SzachowaArena.figury.CPion
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CSkoczek
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CGoniec
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CWieza
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CHetman
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CAmazonka
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CKrol
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BKrol)))
                                                    || (znak == SzachowaArena.figury.BAmazonka
                                                    && ((backup[x + i][y + j] == SzachowaArena.figury.pustka
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CPion
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CSkoczek
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CGoniec
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CWieza
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CAmazonka
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CHetman)
                                                    || (backup[x + i][y + j] != SzachowaArena.figury.BPion
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BSkoczek
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BGoniec
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BWieza
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BHetman
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BAmazonka
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BKrol
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CKrol)))) {
                                                SzachowaArena.figury przechowalnia = backup[x + i][y + j];
                                                backup[x + i][y + j] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);

                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x + i][y + j] = przechowalnia;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przechowalnia == SzachowaArena.figury.pustka) {
                                                        if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                            }
                                                        }
                                                    } else {
                                                        if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case CHetman:
                    case BHetman:

                        if ((tura_rywala != false && znak == SzachowaArena.figury.BHetman) || (tura_rywala == false && znak == SzachowaArena.figury.CHetman)) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            przod = 1;
                            tyl = -1;
                            param_ruch = 1;
                            while (d1 || d2 || d3 || d4
                                    || w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == SzachowaArena.figury.BHetman) || (tura_rywala == false && znak == SzachowaArena.figury.CHetman)) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y + param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BHetman && (backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CPion || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CSkoczek || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CGoniec || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CWieza || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CHetman && (backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BPion || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BSkoczek || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BGoniec || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BWieza || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BHetman) || (tura_rywala == false && znak == SzachowaArena.figury.CHetman)) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y - param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BHetman && (backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CPion || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CSkoczek || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CGoniec || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CWieza || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CHetman && (backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BPion || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BSkoczek || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BGoniec || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BWieza || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BHetman) || (tura_rywala == false && znak == SzachowaArena.figury.CHetman)) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y - param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BHetman && (backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CPion || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CSkoczek || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CGoniec || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CWieza || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CHetman && (backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BPion || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BSkoczek || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BGoniec || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BWieza || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BHetman) || (tura_rywala == false && znak == SzachowaArena.figury.CHetman)) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y + param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BHetman && (backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CPion || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CSkoczek || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CGoniec || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CWieza || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CHetman && (backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BPion || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BSkoczek || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BGoniec || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BWieza || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BHetman) || (tura_rywala == false && znak == SzachowaArena.figury.CHetman)) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == SzachowaArena.figury.pustka) {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BHetman && (backup[x + param_ruch][y] == SzachowaArena.figury.CPion || backup[x + param_ruch][y] == SzachowaArena.figury.CSkoczek || backup[x + param_ruch][y] == SzachowaArena.figury.CAmazonka
                                                    || backup[x + param_ruch][y] == SzachowaArena.figury.CGoniec || backup[x + param_ruch][y] == SzachowaArena.figury.CWieza || backup[x + param_ruch][y] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CHetman && (backup[x + param_ruch][y] == SzachowaArena.figury.BPion || backup[x + param_ruch][y] == SzachowaArena.figury.BSkoczek
                                                    || backup[x + param_ruch][y] == SzachowaArena.figury.BGoniec || backup[x + param_ruch][y] == SzachowaArena.figury.BWieza || backup[x + param_ruch][y] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BHetman) || (tura_rywala == false && znak == SzachowaArena.figury.CHetman)) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == SzachowaArena.figury.pustka) {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BHetman && (backup[x - param_ruch][y] == SzachowaArena.figury.CPion || backup[x - param_ruch][y] == SzachowaArena.figury.CSkoczek || backup[x - param_ruch][y] == SzachowaArena.figury.CAmazonka
                                                    || backup[x - param_ruch][y] == SzachowaArena.figury.CGoniec || backup[x - param_ruch][y] == SzachowaArena.figury.CWieza || backup[x - param_ruch][y] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CHetman && (backup[x - param_ruch][y] == SzachowaArena.figury.BPion || backup[x - param_ruch][y] == SzachowaArena.figury.BSkoczek
                                                    || backup[x - param_ruch][y] == SzachowaArena.figury.BGoniec || backup[x - param_ruch][y] == SzachowaArena.figury.BWieza || backup[x - param_ruch][y] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BHetman) || (tura_rywala == false && znak == SzachowaArena.figury.CHetman)) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y + param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BHetman && (backup[x][y + param_ruch] == SzachowaArena.figury.CPion || backup[x][y + param_ruch] == SzachowaArena.figury.CSkoczek || backup[x][y + param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x][y + param_ruch] == SzachowaArena.figury.CGoniec || backup[x][y + param_ruch] == SzachowaArena.figury.CWieza || backup[x][y + param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CHetman && (backup[x][y + param_ruch] == SzachowaArena.figury.BPion || backup[x][y + param_ruch] == SzachowaArena.figury.BSkoczek
                                                    || backup[x][y + param_ruch] == SzachowaArena.figury.BGoniec || backup[x][y + param_ruch] == SzachowaArena.figury.BWieza || backup[x][y + param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BHetman) || (tura_rywala == false && znak == SzachowaArena.figury.CHetman)) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y - param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BHetman && (backup[x][y - param_ruch] == SzachowaArena.figury.CPion || backup[x][y - param_ruch] == SzachowaArena.figury.CSkoczek || backup[x][y - param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x][y - param_ruch] == SzachowaArena.figury.CGoniec || backup[x][y - param_ruch] == SzachowaArena.figury.CWieza || backup[x][y - param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CHetman && (backup[x][y - param_ruch] == SzachowaArena.figury.BPion || backup[x][y - param_ruch] == SzachowaArena.figury.BSkoczek
                                                    || backup[x][y - param_ruch] == SzachowaArena.figury.BGoniec || backup[x][y - param_ruch] == SzachowaArena.figury.BWieza || backup[x][y - param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                tyl = tyl - 1;
                                param_ruch = param_ruch + 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case BWieza:
                    case CWieza:
                        if ((tura_rywala != false && znak == SzachowaArena.figury.BWieza) || (tura_rywala == false && znak == SzachowaArena.figury.CWieza)) {
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            param_ruch = 1;
                            przod = 1;
                            tyl = -1;
                            while (w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == SzachowaArena.figury.BWieza) || (tura_rywala == false && znak == SzachowaArena.figury.CWieza)) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == SzachowaArena.figury.pustka) {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BWieza && (backup[x + param_ruch][y] == SzachowaArena.figury.CPion || backup[x + param_ruch][y] == SzachowaArena.figury.CSkoczek || backup[x + param_ruch][y] == SzachowaArena.figury.CAmazonka
                                                    || backup[x + param_ruch][y] == SzachowaArena.figury.CGoniec || backup[x + param_ruch][y] == SzachowaArena.figury.CWieza || backup[x + param_ruch][y] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CWieza && (backup[x + param_ruch][y] == SzachowaArena.figury.BPion || backup[x + param_ruch][y] == SzachowaArena.figury.BSkoczek || backup[x + param_ruch][y] == SzachowaArena.figury.BAmazonka
                                                    || backup[x + param_ruch][y] == SzachowaArena.figury.BGoniec || backup[x + param_ruch][y] == SzachowaArena.figury.BWieza || backup[x + param_ruch][y] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }

                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BWieza) || (tura_rywala == false && znak == SzachowaArena.figury.CWieza)) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == SzachowaArena.figury.pustka) {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && start[0] == x && start[1] == y)) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BWieza && (backup[x - param_ruch][y] == SzachowaArena.figury.CPion || backup[x - param_ruch][y] == SzachowaArena.figury.CSkoczek || backup[x - param_ruch][y] == SzachowaArena.figury.CAmazonka
                                                    || backup[x - param_ruch][y] == SzachowaArena.figury.CGoniec || backup[x - param_ruch][y] == SzachowaArena.figury.CWieza || backup[x - param_ruch][y] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CWieza && (backup[x - param_ruch][y] == SzachowaArena.figury.BPion || backup[x - param_ruch][y] == SzachowaArena.figury.BSkoczek || backup[x - param_ruch][y] == SzachowaArena.figury.BAmazonka
                                                    || backup[x - param_ruch][y] == SzachowaArena.figury.BGoniec || backup[x - param_ruch][y] == SzachowaArena.figury.BWieza || backup[x - param_ruch][y] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && start[0] == x && start[1] == y)) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BWieza) || (tura_rywala == false && znak == SzachowaArena.figury.CWieza)) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y + param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && start[0] == x && start[1] == y)) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BWieza && (backup[x][y + param_ruch] == SzachowaArena.figury.CPion || backup[x][y + param_ruch] == SzachowaArena.figury.CSkoczek || backup[x][y + param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x][y + param_ruch] == SzachowaArena.figury.CGoniec || backup[x][y + param_ruch] == SzachowaArena.figury.CWieza || backup[x][y + param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CWieza && (backup[x][y + param_ruch] == SzachowaArena.figury.BPion || backup[x][y + param_ruch] == SzachowaArena.figury.BSkoczek || backup[x][y + param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x][y + param_ruch] == SzachowaArena.figury.BGoniec || backup[x][y + param_ruch] == SzachowaArena.figury.BWieza || backup[x][y + param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && start[0] == x && start[1] == y)) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BWieza) || (tura_rywala == false && znak == SzachowaArena.figury.CWieza)) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y - param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (!konkret || (konkret && start[0] == x && start[1] == y)) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BWieza && (backup[x][y - param_ruch] == SzachowaArena.figury.CPion || backup[x][y - param_ruch] == SzachowaArena.figury.CSkoczek || backup[x][y - param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x][y - param_ruch] == SzachowaArena.figury.CGoniec || backup[x][y - param_ruch] == SzachowaArena.figury.CWieza || backup[x][y - param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CWieza && (backup[x][y - param_ruch] == SzachowaArena.figury.BPion || backup[x][y - param_ruch] == SzachowaArena.figury.BSkoczek || backup[x][y - param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x][y - param_ruch] == SzachowaArena.figury.BGoniec || backup[x][y - param_ruch] == SzachowaArena.figury.BWieza || backup[x][y - param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (!konkret || (konkret && start[0] == x && start[1] == y)) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                                tyl = tyl - 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case BGoniec:
                    case CGoniec:
                        if ((tura_rywala != false && znak == SzachowaArena.figury.BGoniec) || (tura_rywala == false && znak == SzachowaArena.figury.CGoniec)) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            while (d1 || d2 || d3 || d4) {
                                if ((tura_rywala && znak == SzachowaArena.figury.BGoniec) || (tura_rywala == false && znak == SzachowaArena.figury.CGoniec)) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y + param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BGoniec && (backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CPion || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CSkoczek || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CGoniec || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CWieza || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CGoniec && (backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BPion || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BSkoczek || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BGoniec || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BWieza || backup[x + param_ruch][y + param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BGoniec) || (tura_rywala == false && znak == SzachowaArena.figury.CGoniec)) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y - param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BGoniec && (backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CPion || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CSkoczek || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CGoniec || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CWieza || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CGoniec && (backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BPion || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BSkoczek || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BGoniec || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BWieza || backup[x - param_ruch][y - param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BGoniec) || (tura_rywala == false && znak == SzachowaArena.figury.CGoniec)) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x + param_ruch][y - param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BGoniec && (backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CPion || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CSkoczek || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CGoniec || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CWieza || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CGoniec && (backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BPion || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BSkoczek || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BGoniec || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BWieza || backup[x + param_ruch][y - param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SzachowaArena.figury.BGoniec) || (tura_rywala == false && znak == SzachowaArena.figury.CGoniec)) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.pustka) {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x - param_ruch][y + param_ruch] = SzachowaArena.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            if ((znak == SzachowaArena.figury.BGoniec && (backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CPion || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CSkoczek || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CAmazonka
                                                    || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CGoniec || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CWieza || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.CHetman))
                                                    || (znak == SzachowaArena.figury.CGoniec && (backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BPion || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BSkoczek || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BAmazonka
                                                    || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BGoniec || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BWieza || backup[x - param_ruch][y + param_ruch] == SzachowaArena.figury.BHetman))) {
                                                SzachowaArena.figury przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                        }
                                                    }
                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                            }
                        }
                        break;
                    case CPion:
                        if (tura_rywala == false) {
                            if (x == 3 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x - 1][kolumna - 1] = SzachowaArena.figury.CPion;
                                backup[x][kolumna - 1] = SzachowaArena.figury.pustka;
                                backup[x][y] = SzachowaArena.figury.pustka;

                                if ((obecnosc(backup))) {
                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                } else {
                                    wynik = false;
                                    szach = false;
                                }

                                backup[x - 1][kolumna - 1] = SzachowaArena.figury.pustka;
                                backup[x][kolumna - 1] = SzachowaArena.figury.BPion;
                                backup[x][y] = SzachowaArena.figury.CPion;
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x - 1));
                                    if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                        if (szach == false) {
                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP "), SzachowaArena.figury.BPion, backup));
                                        } else {
                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), SzachowaArena.figury.BPion, backup));
                                        }
                                    }
                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x - 1 > -1) {
                                    if (b != 0 && ((backup[x - 1][y + b] == SzachowaArena.figury.BSkoczek)
                                            || (backup[x - 1][y + b] == SzachowaArena.figury.BPion)
                                            || (backup[x - 1][y + b] == SzachowaArena.figury.BGoniec)
                                            || (backup[x - 1][y + b] == SzachowaArena.figury.BWieza)
                                            || (backup[x - 1][y + b] == SzachowaArena.figury.BHetman)
                                            || (backup[x - 1][y + b] == SzachowaArena.figury.BAmazonka))) {
                                        SzachowaArena.figury przechowalnie;
                                        przechowalnie = backup[x - 1][y + b];
                                        if (x != 1) {
                                            backup[x - 1][y + b] = znak;
                                            backup[x][y] = SzachowaArena.figury.pustka;

                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x - 1][y + b] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "--+"), przechowalnie, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, backup));
                                                    }
                                                }
                                            }
                                        } else {
                                            SzachowaArena.figury[] symbole = {SzachowaArena.figury.CAmazonka, SzachowaArena.figury.CWieza, SzachowaArena.figury.CGoniec, SzachowaArena.figury.CSkoczek};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x - 1][y + b] = symbole[s];
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x - 1][y + b] = przechowalnie;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                    if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + "+"), przechowalnie, backup));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + " "), przechowalnie, backup));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x - 1 > -1) {
                                if (backup[x - 1][y] == SzachowaArena.figury.pustka || (backup[x - 1][y] != SzachowaArena.figury.CPion && backup[x - 1][y] != SzachowaArena.figury.BPion
                                        && backup[x - 1][y] != SzachowaArena.figury.BSkoczek && backup[x - 1][y] != SzachowaArena.figury.CSkoczek && backup[x - 1][y] != SzachowaArena.figury.BGoniec
                                        && backup[x - 1][y] != SzachowaArena.figury.CGoniec && backup[x - 1][y] != SzachowaArena.figury.BWieza && backup[x - 1][y] != SzachowaArena.figury.CWieza
                                        && backup[x - 1][y] != SzachowaArena.figury.BHetman && backup[x - 1][y] != SzachowaArena.figury.CHetman && backup[x - 1][y] != SzachowaArena.figury.BKrol
                                        && backup[x - 1][y] != SzachowaArena.figury.CKrol && backup[x - 1][y] != SzachowaArena.figury.CAmazonka && backup[x - 1][y] != SzachowaArena.figury.BAmazonka)) {
                                    SzachowaArena.figury przechowalnie;
                                    przechowalnie = backup[x - 1][y];
                                    if (x != 1) {
                                        backup[x - 1][y] = znak;
                                        backup[x][y] = SzachowaArena.figury.pustka;
                                        if ((obecnosc(backup))) {
                                            wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                        } else {
                                            wynik = false;
                                            szach = false;
                                        }

                                        backup[x][y] = znak;
                                        backup[x - 1][y] = przechowalnie;
                                        if (wynik || all) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                }
                                            }
                                        }
                                    } else {
                                        SzachowaArena.figury[] symbole = {SzachowaArena.figury.CHetman, SzachowaArena.figury.CWieza, SzachowaArena.figury.CGoniec, SzachowaArena.figury.CSkoczek};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x - 1][y] = symbole[s];
                                            backup[x][y] = SzachowaArena.figury.pustka;
                                            if ((obecnosc(backup))) {
                                                wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            } else {
                                                wynik = false;
                                                szach = false;
                                            }

                                            backup[x][y] = znak;
                                            backup[x - 1][y] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                                if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + "+"), SzachowaArena.figury.pustka, backup));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + " "), SzachowaArena.figury.pustka, backup));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (x - 2 > -1) {
                                if ((backup[x - 2][y] == SzachowaArena.figury.pustka || (backup[x - 2][y] != SzachowaArena.figury.CPion && backup[x - 2][y] != SzachowaArena.figury.BPion
                                        && backup[x - 2][y] != SzachowaArena.figury.BSkoczek && backup[x - 2][y] != SzachowaArena.figury.CSkoczek && backup[x - 2][y] != SzachowaArena.figury.BGoniec
                                        && backup[x - 2][y] != SzachowaArena.figury.CGoniec && backup[x - 2][y] != SzachowaArena.figury.BWieza && backup[x - 2][y] != SzachowaArena.figury.CWieza
                                        && backup[x - 2][y] != SzachowaArena.figury.BHetman && backup[x - 2][y] != SzachowaArena.figury.CHetman && backup[x - 2][y] != SzachowaArena.figury.BKrol
                                        && backup[x - 2][y] != SzachowaArena.figury.CKrol && backup[x - 2][y] != SzachowaArena.figury.CAmazonka && backup[x - 2][y] != SzachowaArena.figury.BAmazonka))
                                        && (backup[x - 1][y] == SzachowaArena.figury.pustka || (backup[x - 1][y] != SzachowaArena.figury.CPion && backup[x - 1][y] != SzachowaArena.figury.BPion
                                        && backup[x - 1][y] != SzachowaArena.figury.BSkoczek && backup[x - 1][y] != SzachowaArena.figury.CSkoczek && backup[x - 1][y] != SzachowaArena.figury.BGoniec
                                        && backup[x - 1][y] != SzachowaArena.figury.CGoniec && backup[x - 1][y] != SzachowaArena.figury.BWieza && backup[x - 1][y] != SzachowaArena.figury.CWieza
                                        && backup[x - 1][y] != SzachowaArena.figury.BHetman && backup[x - 1][y] != SzachowaArena.figury.CHetman && backup[x - 1][y] != SzachowaArena.figury.BKrol
                                        && backup[x - 1][y] != SzachowaArena.figury.CKrol && backup[x - 1][y] != SzachowaArena.figury.CAmazonka && backup[x - 1][y] != SzachowaArena.figury.BAmazonka)) && x == 6) {
                                    SzachowaArena.figury przechowalnie = backup[x - 2][y];
                                    backup[x - 2][y] = znak;
                                    backup[x][y] = SzachowaArena.figury.pustka;

                                    if ((obecnosc(backup))) {
                                        wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                        szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                    } else {
                                        wynik = false;
                                        szach = false;
                                    }

                                    backup[x][y] = znak;
                                    backup[x - 2][y] = przechowalnie;
                                    if (wynik || all) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 2));
                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case CSkoczek:
                    case BSkoczek:
                        if ((tura_rywala != false && znak == SzachowaArena.figury.BSkoczek) || (tura_rywala == false && znak == SzachowaArena.figury.CSkoczek)) {
                            for (int i = -2; i < 3; i++) {
                                for (int j = -2; j < 3; j++) {
                                    if (x + i > -1 && x + i < 8 && y + j > -1 && y + j < 8) {
                                        if (Math.abs(j) - Math.abs(i) != 0 && i != 0 && j != 0) {
                                            if ((znak == SzachowaArena.figury.CSkoczek
                                                    && ((backup[x + i][y + j] == SzachowaArena.figury.pustka
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BPion
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BSkoczek
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BGoniec
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BWieza
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BHetman
                                                    || backup[x + i][y + j] == SzachowaArena.figury.BAmazonka)
                                                    || (backup[x + i][y + j] != SzachowaArena.figury.CPion
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CSkoczek
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CGoniec
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CWieza
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CHetman
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CKrol
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BKrol
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CAmazonka)))
                                                    || (znak == SzachowaArena.figury.BSkoczek
                                                    && ((backup[x + i][y + j] == SzachowaArena.figury.pustka
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CPion
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CSkoczek
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CGoniec
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CWieza
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CHetman
                                                    || backup[x + i][y + j] == SzachowaArena.figury.CAmazonka)
                                                    || (backup[x + i][y + j] != SzachowaArena.figury.BPion
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BSkoczek
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BGoniec
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BWieza
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BHetman
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BAmazonka
                                                    && backup[x + i][y + j] != SzachowaArena.figury.BKrol
                                                    && backup[x + i][y + j] != SzachowaArena.figury.CKrol)))) {
                                                SzachowaArena.figury przechowalnia = backup[x + i][y + j];
                                                backup[x + i][y + j] = znak;
                                                backup[x][y] = SzachowaArena.figury.pustka;

                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);

                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x][y] = znak;
                                                backup[x + i][y + j] = przechowalnia;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przechowalnia == SzachowaArena.figury.pustka) {
                                                        if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                            }
                                                        }
                                                    } else {
                                                        if (!konkret || (konkret && ((x == start[0] && y == start[1])))) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case BKrol:
                        if (tura_rywala != false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == SzachowaArena.figury.pustka
                                                    || (backup[x + i][y + j] == SzachowaArena.figury.CPion || backup[x + i][y + j] == SzachowaArena.figury.CSkoczek || backup[x + i][y + j] == SzachowaArena.figury.CGoniec || backup[x + i][y + j] == SzachowaArena.figury.CWieza || backup[x + i][y + j] == SzachowaArena.figury.CHetman)) {
                                                SzachowaArena.figury przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = SzachowaArena.figury.BKrol;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);

                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = SzachowaArena.figury.BKrol;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == SzachowaArena.figury.pustka) {
                                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                            }
                                                        }
                                                    } else {
                                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przech, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech, backup));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!RuchZagrozenie_kontrola.szach(konwert(backup), true)) {
                                if (kingrochB) {
                                    if (whiteright && (backup[0][5] == SzachowaArena.figury.pustka)
                                            && (backup[0][6] == SzachowaArena.figury.pustka)
                                            && (backup[0][4] == SzachowaArena.figury.BKrol)
                                            && (backup[0][7] == SzachowaArena.figury.BWieza)) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r < 3; r++) {
                                            backup[0][4] = SzachowaArena.figury.pustka;
                                            backup[0][4 + r] = SzachowaArena.figury.BKrol;
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach(konwert(backup), true);
                                            backup[0][4] = SzachowaArena.figury.BKrol;
                                            backup[0][4 + r] = SzachowaArena.figury.pustka;
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[0][4] = SzachowaArena.figury.pustka;
                                            backup[0][7] = SzachowaArena.figury.pustka;
                                            backup[0][5] = SzachowaArena.figury.BWieza;
                                            backup[0][6] = SzachowaArena.figury.BKrol;
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            backup[0][4] = SzachowaArena.figury.BKrol;
                                            backup[0][7] = SzachowaArena.figury.BWieza;
                                            backup[0][5] = SzachowaArena.figury.pustka;
                                            backup[0][6] = SzachowaArena.figury.pustka;
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(("KO-O+   "),
                                                            SzachowaArena.figury.pustka, backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(("KO-O    "),
                                                            SzachowaArena.figury.pustka, backup));
                                                }
                                            }
                                        }
                                    }
                                    if (whiteleft && (backup[0][3] == SzachowaArena.figury.pustka)
                                            && (backup[0][2] == SzachowaArena.figury.pustka)
                                            && (backup[0][1] == SzachowaArena.figury.pustka)
                                            && (backup[0][4] == SzachowaArena.figury.BKrol)
                                            && (backup[0][0] == SzachowaArena.figury.BWieza)) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r <= 2; r++) {
                                            backup[0][4] = SzachowaArena.figury.pustka;
                                            backup[0][4 - r] = SzachowaArena.figury.BKrol;
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach(konwert(backup), true);
                                            backup[0][4] = SzachowaArena.figury.BKrol;
                                            backup[0][4 - r] = SzachowaArena.figury.pustka;
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[0][4] = SzachowaArena.figury.pustka;
                                            backup[0][0] = SzachowaArena.figury.pustka;
                                            backup[0][3] = SzachowaArena.figury.BWieza;
                                            backup[0][2] = SzachowaArena.figury.BKrol;
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            backup[0][4] = SzachowaArena.figury.BKrol;
                                            backup[0][0] = SzachowaArena.figury.BWieza;
                                            backup[0][3] = SzachowaArena.figury.pustka;
                                            backup[0][2] = SzachowaArena.figury.pustka;
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(("KO-O-O+  "),
                                                            SzachowaArena.figury.pustka, backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(("KO-O-O   "),
                                                            SzachowaArena.figury.pustka, backup));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case CKrol:
                        if (tura_rywala == false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == SzachowaArena.figury.pustka
                                                    || (backup[x + i][y + j] == SzachowaArena.figury.BPion || backup[x + i][y + j] == SzachowaArena.figury.BSkoczek || backup[x + i][y + j] == SzachowaArena.figury.BGoniec || backup[x + i][y + j] == SzachowaArena.figury.BWieza || backup[x + i][y + j] == SzachowaArena.figury.BHetman)) {
                                                SzachowaArena.figury przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = SzachowaArena.figury.CKrol;
                                                backup[x][y] = SzachowaArena.figury.pustka;
                                                if ((obecnosc(backup))) {
                                                    wynik = (all || RuchZagrozenie_kontrola.szach(konwert(backup), tura_rywala) == false);
                                                    szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                                } else {
                                                    wynik = false;
                                                    szach = false;
                                                }

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = SzachowaArena.figury.CKrol;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == SzachowaArena.figury.pustka) {
                                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SzachowaArena.figury.pustka, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SzachowaArena.figury.pustka, backup));
                                                            }
                                                        }
                                                    } else {
                                                        if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                            if (szach) {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przech, backup));
                                                            } else {
                                                                lista_dopuszcalnych_Ruchow.add(new RuchA(((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech, backup));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!RuchZagrozenie_kontrola.szach(konwert(backup), false)) {
                                if (kingrochC) {
                                    if (blackright && (backup[7][5] == SzachowaArena.figury.pustka)
                                            && (backup[7][6] == SzachowaArena.figury.pustka)
                                            && (backup[7][4] == SzachowaArena.figury.CKrol)
                                            && (backup[7][7] == SzachowaArena.figury.CWieza)) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r < 3; r++) {
                                            backup[7][4] = SzachowaArena.figury.pustka;
                                            backup[7][4 + r] = SzachowaArena.figury.CKrol;
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach(konwert(backup), false);
                                            backup[7][4] = SzachowaArena.figury.CKrol;
                                            backup[7][4 + r] = SzachowaArena.figury.pustka;
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[7][4] = SzachowaArena.figury.pustka;
                                            backup[7][7] = SzachowaArena.figury.pustka;
                                            backup[7][5] = SzachowaArena.figury.CWieza;
                                            backup[7][6] = SzachowaArena.figury.CKrol;
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            backup[7][4] = SzachowaArena.figury.CKrol;
                                            backup[7][7] = SzachowaArena.figury.CWieza;
                                            backup[7][5] = SzachowaArena.figury.pustka;
                                            backup[7][6] = SzachowaArena.figury.pustka;
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(("kO-O+     "),
                                                            SzachowaArena.figury.pustka, backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(("kO-O      "),
                                                            SzachowaArena.figury.pustka, backup));
                                                }
                                            }
                                        }
                                    }
                                    if (blackleft && (backup[7][3] == SzachowaArena.figury.pustka)
                                            && (backup[7][2] == SzachowaArena.figury.pustka)
                                            && (backup[7][1] == SzachowaArena.figury.pustka)
                                            && (backup[7][4] == SzachowaArena.figury.CKrol)
                                            && (backup[7][0] == SzachowaArena.figury.CWieza)) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r <= 2; r++) {
                                            backup[7][4] = SzachowaArena.figury.pustka;
                                            backup[7][4 - r] = SzachowaArena.figury.CKrol;
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach(konwert(backup), false);
                                            backup[7][4] = SzachowaArena.figury.CKrol;
                                            backup[7][4 - r] = SzachowaArena.figury.pustka;
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[7][4] = SzachowaArena.figury.pustka;
                                            backup[7][0] = SzachowaArena.figury.pustka;
                                            backup[7][3] = SzachowaArena.figury.CWieza;
                                            backup[7][2] = SzachowaArena.figury.CKrol;
                                            szach = RuchZagrozenie_kontrola.szach(konwert(backup), !tura_rywala);
                                            backup[7][4] = SzachowaArena.figury.CKrol;
                                            backup[7][0] = SzachowaArena.figury.CWieza;
                                            backup[7][3] = SzachowaArena.figury.pustka;
                                            backup[7][2] = SzachowaArena.figury.pustka;
                                            if (!konkret || (konkret && x == start[0] && y == start[1])) {
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(("kO-O-O+  "),
                                                            SzachowaArena.figury.pustka, backup));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new RuchA(("kO-O-O   "),
                                                            SzachowaArena.figury.pustka, backup));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }

            }//System.out.println("");

        }

        return Collections.unmodifiableCollection(lista_dopuszcalnych_Ruchow);
    }

    private static boolean obecnosc(final SzachowaArena.figury[][] backup) {
        boolean KB = false, KC = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (backup[i][j] == SzachowaArena.figury.BKrol) {
                    KB = KB == false;
                }
                if (backup[i][j] == SzachowaArena.figury.CKrol) {
                    KC = KC == false;
                }
            }
        }
        return KC && KB;
    }

    static Collection<Ruch> generuj_posuniecia_antyszach(char[][] ust, boolean tura_rywala, boolean przelotcan, int kolumna) {
        List<Ruch> lista_dopuszcalnych_Ruchow = new ArrayList<>();
        char[][] backup = new char[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                backup[x][y] = ust[x][y];
            }
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                char znak = backup[x][y];

                int param_ruch = 1;
                int przod;
                int tyl;
                boolean w1, w2, w3, w4, d1, d2, d3, d4;
                // //System.out.print("["+x+" "+y+"]"+znak);
                switch (znak) {
                    case 'P':
                        if (tura_rywala != false) {
                            if (x == 4 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x + 1][kolumna - 1] = 'P';
                                backup[x][kolumna - 1] = ' ';
                                backup[x][y] = ' ';

                                backup[x + 1][kolumna - 1] = ' ';
                                backup[x][kolumna - 1] = 'p';
                                backup[x][y] = 'P';

                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x + 1));

                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP "), 'p', backup));

                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x + 1 < 8) {
                                    if (b != 0 && ((backup[x + 1][y + b] == 'n')
                                            || (backup[x + 1][y + b] == 'p')
                                            || (backup[x + 1][y + b] == 'b')
                                            || (backup[x + 1][y + b] == 'r')
                                            || (backup[x + 1][y + b] == 'q'))) {
                                        char przechowalnie;
                                        przechowalnie = backup[x + 1][y + b];
                                        if (x != 6) {
                                            backup[x + 1][y + b] = znak;
                                            backup[x][y] = ' ';

                                            backup[x][y] = znak;
                                            backup[x + 1][y + b] = przechowalnie;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, backup));

                                        } else {
                                            char[] symbole = {'Q', 'R', 'B', 'N'};
                                            for (int s = 0; s < 4; s++) {

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + " "), przechowalnie, backup));

                                            }
                                        }
                                    }
                                }
                            }

                            if (x + 1 < 8) {
                                if (backup[x + 1][y] == ' ' || (backup[x + 1][y] != 'p' && backup[x + 1][y] != 'P'
                                        && backup[x + 1][y] != 'N' && backup[x + 1][y] != 'n' && backup[x + 1][y] != 'B'
                                        && backup[x + 1][y] != 'b' && backup[x + 1][y] != 'R' && backup[x + 1][y] != 'r'
                                        && backup[x + 1][y] != 'Q' && backup[x + 1][y] != 'q' && backup[x + 1][y] != 'K'
                                        && backup[x + 1][y] != 'k')) {
                                    char przechowalnie;
                                    przechowalnie = backup[x + 1][y];
                                    if (x != 6) {

                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));

                                        lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak)
                                                + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));

                                    } else {
                                        char[] symbole = {'Q', 'R', 'B', 'N'};
                                        for (int s = 0; s < 4; s++) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + " "), ' ', backup));

                                        }
                                    }
                                }
                            }
                            if (x + 2 < 8) {
                                if ((backup[x + 2][y] == ' ' || (backup[x + 2][y] != 'p' && backup[x + 2][y] != 'P'
                                        && backup[x + 2][y] != 'N' && backup[x + 2][y] != 'n' && backup[x + 2][y] != 'B'
                                        && backup[x + 2][y] != 'b' && backup[x + 2][y] != 'R' && backup[x + 2][y] != 'r'
                                        && backup[x + 2][y] != 'Q' && backup[x + 2][y] != 'q' && backup[x + 2][y] != 'K'
                                        && backup[x + 2][y] != 'k'))
                                        && (backup[x + 1][y] == ' ' || (backup[x + 1][y] != 'p' && backup[x + 1][y] != 'P'
                                        && backup[x + 1][y] != 'N' && backup[x + 1][y] != 'n' && backup[x + 1][y] != 'B'
                                        && backup[x + 1][y] != 'b' && backup[x + 1][y] != 'R' && backup[x + 1][y] != 'r'
                                        && backup[x + 1][y] != 'Q' && backup[x + 1][y] != 'q' && backup[x + 1][y] != 'K'
                                        && backup[x + 1][y] != 'k')) && x == 1) {
                                    char przechowalnie = backup[x + 2][y];

                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 2));

                                    lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));

                                }
                            }
                        }
                        break;
                    case 'q':
                    case 'Q':

                        if ((tura_rywala != false && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            przod = 1;
                            tyl = -1;
                            param_ruch = 1;
                            while (d1 || d2 || d3 || d4
                                    || w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == ' ') {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x + param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'Q' && (backup[x + param_ruch][y + param_ruch] == 'k' || backup[x + param_ruch][y + param_ruch] == 'p' || backup[x + param_ruch][y + param_ruch] == 'n'
                                                    || backup[x + param_ruch][y + param_ruch] == 'b' || backup[x + param_ruch][y + param_ruch] == 'r' || backup[x + param_ruch][y + param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x + param_ruch][y + param_ruch] == 'K' || backup[x + param_ruch][y + param_ruch] == 'P' || backup[x + param_ruch][y + param_ruch] == 'N'
                                                    || backup[x + param_ruch][y + param_ruch] == 'B' || backup[x + param_ruch][y + param_ruch] == 'R' || backup[x + param_ruch][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == ' ') {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x - param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'Q' && (backup[x - param_ruch][y - param_ruch] == 'k' || backup[x - param_ruch][y - param_ruch] == 'p' || backup[x - param_ruch][y - param_ruch] == 'n'
                                                    || backup[x - param_ruch][y - param_ruch] == 'b' || backup[x - param_ruch][y - param_ruch] == 'r' || backup[x - param_ruch][y - param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x - param_ruch][y - param_ruch] == 'K' || backup[x - param_ruch][y - param_ruch] == 'P' || backup[x - param_ruch][y - param_ruch] == 'N'
                                                    || backup[x - param_ruch][y - param_ruch] == 'B' || backup[x - param_ruch][y - param_ruch] == 'R' || backup[x - param_ruch][y - param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == ' ') {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x + param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'Q' && (backup[x + param_ruch][y - param_ruch] == 'k' || backup[x + param_ruch][y - param_ruch] == 'p' || backup[x + param_ruch][y - param_ruch] == 'n'
                                                    || backup[x + param_ruch][y - param_ruch] == 'b' || backup[x + param_ruch][y - param_ruch] == 'r' || backup[x + param_ruch][y - param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x + param_ruch][y - param_ruch] == 'K' || backup[x + param_ruch][y - param_ruch] == 'P' || backup[x + param_ruch][y - param_ruch] == 'N'
                                                    || backup[x + param_ruch][y - param_ruch] == 'B' || backup[x + param_ruch][y - param_ruch] == 'R' || backup[x + param_ruch][y - param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == ' ') {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x - param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'Q' && (backup[x - param_ruch][y + param_ruch] == 'k' || backup[x - param_ruch][y + param_ruch] == 'p' || backup[x - param_ruch][y + param_ruch] == 'n'
                                                    || backup[x - param_ruch][y + param_ruch] == 'b' || backup[x - param_ruch][y + param_ruch] == 'r' || backup[x - param_ruch][y + param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x - param_ruch][y + param_ruch] == 'K' || backup[x - param_ruch][y + param_ruch] == 'P' || backup[x - param_ruch][y + param_ruch] == 'N'
                                                    || backup[x - param_ruch][y + param_ruch] == 'B' || backup[x - param_ruch][y + param_ruch] == 'R' || backup[x - param_ruch][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == ' ') {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = ' ';

                                            backup[x + param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'Q' && (backup[x + param_ruch][y] == 'k' || backup[x + param_ruch][y] == 'p' || backup[x + param_ruch][y] == 'n'
                                                    || backup[x + param_ruch][y] == 'b' || backup[x + param_ruch][y] == 'r' || backup[x + param_ruch][y] == 'q'))
                                                    || (znak == 'q' && (backup[x + param_ruch][y] == 'K' || backup[x + param_ruch][y] == 'P' || backup[x + param_ruch][y] == 'N'
                                                    || backup[x + param_ruch][y] == 'B' || backup[x + param_ruch][y] == 'R' || backup[x + param_ruch][y] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = ' ';

                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == ' ') {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = ' ';

                                            backup[x - param_ruch][y] = ' ';
                                            backup[x][y] = znak;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'Q' && (backup[x - param_ruch][y] == 'k' || backup[x - param_ruch][y] == 'p' || backup[x - param_ruch][y] == 'n'
                                                    || backup[x - param_ruch][y] == 'b' || backup[x - param_ruch][y] == 'r' || backup[x - param_ruch][y] == 'q'))
                                                    || (znak == 'q' && (backup[x - param_ruch][y] == 'K' || backup[x - param_ruch][y] == 'P' || backup[x - param_ruch][y] == 'N'
                                                    || backup[x - param_ruch][y] == 'B' || backup[x - param_ruch][y] == 'R' || backup[x - param_ruch][y] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = ' ';

                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == ' ') {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'Q' && (backup[x][y + param_ruch] == 'k' || backup[x][y + param_ruch] == 'p' || backup[x][y + param_ruch] == 'n'
                                                    || backup[x][y + param_ruch] == 'b' || backup[x][y + param_ruch] == 'r' || backup[x][y + param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x][y + param_ruch] == 'K' || backup[x][y + param_ruch] == 'P' || backup[x][y + param_ruch] == 'N'
                                                    || backup[x][y + param_ruch] == 'B' || backup[x][y + param_ruch] == 'R' || backup[x][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'Q') || (tura_rywala == false && znak == 'q')) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == ' ') {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x][y - param_ruch] = ' ';
                                            backup[x][y] = znak;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'Q' && (backup[x][y - param_ruch] == 'k' || backup[x][y - param_ruch] == 'p' || backup[x][y - param_ruch] == 'n'
                                                    || backup[x][y - param_ruch] == 'b' || backup[x][y - param_ruch] == 'r' || backup[x][y - param_ruch] == 'q'))
                                                    || (znak == 'q' && (backup[x][y - param_ruch] == 'K' || backup[x][y - param_ruch] == 'P' || backup[x][y - param_ruch] == 'N'
                                                    || backup[x][y - param_ruch] == 'B' || backup[x][y - param_ruch] == 'R' || backup[x][y - param_ruch] == 'Q'))) {

                                                char przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                tyl = tyl - 1;
                                param_ruch = param_ruch + 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case 'r':
                    case 'R':
                        if ((tura_rywala != false && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            param_ruch = 1;
                            przod = 1;
                            tyl = -1;
                            while (w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == ' ') {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = ' ';

                                            backup[x + param_ruch][y] = ' ';
                                            backup[x][y] = znak;
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'R' && (backup[x + param_ruch][y] == 'k' || backup[x + param_ruch][y] == 'p' || backup[x + param_ruch][y] == 'n'
                                                    || backup[x + param_ruch][y] == 'b' || backup[x + param_ruch][y] == 'r' || backup[x + param_ruch][y] == 'q'))
                                                    || (znak == 'r' && (backup[x + param_ruch][y] == 'K' || backup[x + param_ruch][y] == 'P' || backup[x + param_ruch][y] == 'N'
                                                    || backup[x + param_ruch][y] == 'B' || backup[x + param_ruch][y] == 'R' || backup[x + param_ruch][y] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = ' ';

                                                backup[x + param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == ' ') {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = ' ';

                                            backup[x - param_ruch][y] = ' ';
                                            backup[x][y] = znak;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'R' && (backup[x - param_ruch][y] == 'k' || backup[x - param_ruch][y] == 'p' || backup[x - param_ruch][y] == 'n'
                                                    || backup[x - param_ruch][y] == 'b' || backup[x - param_ruch][y] == 'r' || backup[x - param_ruch][y] == 'q'))
                                                    || (znak == 'r' && (backup[x - param_ruch][y] == 'K' || backup[x - param_ruch][y] == 'P' || backup[x - param_ruch][y] == 'N'
                                                    || backup[x - param_ruch][y] == 'B' || backup[x - param_ruch][y] == 'R' || backup[x - param_ruch][y] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = ' ';

                                                backup[x - param_ruch][y] = przechowalnia;
                                                backup[x][y] = znak;
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == ' ') {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'R' && (backup[x][y + param_ruch] == 'k' || backup[x][y + param_ruch] == 'p' || backup[x][y + param_ruch] == 'n'
                                                    || backup[x][y + param_ruch] == 'b' || backup[x][y + param_ruch] == 'r' || backup[x][y + param_ruch] == 'q'))
                                                    || (znak == 'r' && (backup[x][y + param_ruch] == 'K' || backup[x][y + param_ruch] == 'P' || backup[x][y + param_ruch] == 'N'
                                                    || backup[x][y + param_ruch] == 'B' || backup[x][y + param_ruch] == 'R' || backup[x][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'R') || (tura_rywala == false && znak == 'r')) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == ' ') {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x][y - param_ruch] = ' ';
                                            backup[x][y] = znak;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'R' && (backup[x][y - param_ruch] == 'k' || backup[x][y - param_ruch] == 'p' || backup[x][y - param_ruch] == 'n'
                                                    || backup[x][y - param_ruch] == 'b' || backup[x][y - param_ruch] == 'r' || backup[x][y - param_ruch] == 'q'))
                                                    || (znak == 'r' && (backup[x][y - param_ruch] == 'K' || backup[x][y - param_ruch] == 'P' || backup[x][y - param_ruch] == 'N'
                                                    || backup[x][y - param_ruch] == 'B' || backup[x][y - param_ruch] == 'R' || backup[x][y - param_ruch] == 'Q'))) {

                                                char przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                                tyl = tyl - 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case 'b':
                    case 'B':
                        if ((tura_rywala != false && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            while (d1 || d2 || d3 || d4) {
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == ' ') {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x + param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'B' && (backup[x + param_ruch][y + param_ruch] == 'k' || backup[x + param_ruch][y + param_ruch] == 'p' || backup[x + param_ruch][y + param_ruch] == 'n'
                                                    || backup[x + param_ruch][y + param_ruch] == 'b' || backup[x + param_ruch][y + param_ruch] == 'r' || backup[x + param_ruch][y + param_ruch] == 'q'))
                                                    || (znak == 'b' && (backup[x + param_ruch][y + param_ruch] == 'K' || backup[x + param_ruch][y + param_ruch] == 'P' || backup[x + param_ruch][y + param_ruch] == 'N'
                                                    || backup[x + param_ruch][y + param_ruch] == 'B' || backup[x + param_ruch][y + param_ruch] == 'R' || backup[x + param_ruch][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == ' ') {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x - param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'B' && (backup[x - param_ruch][y - param_ruch] == 'k' || backup[x - param_ruch][y - param_ruch] == 'p' || backup[x - param_ruch][y - param_ruch] == 'n'
                                                    || backup[x - param_ruch][y - param_ruch] == 'b' || backup[x - param_ruch][y - param_ruch] == 'r' || backup[x - param_ruch][y - param_ruch] == 'q'))
                                                    || (znak == 'b' && (backup[x - param_ruch][y - param_ruch] == 'K' || backup[x - param_ruch][y - param_ruch] == 'P' || backup[x - param_ruch][y - param_ruch] == 'N'
                                                    || backup[x - param_ruch][y - param_ruch] == 'B' || backup[x - param_ruch][y - param_ruch] == 'R' || backup[x - param_ruch][y - param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == ' ') {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x + param_ruch][y - param_ruch] = ' ';
                                            backup[x][y] = znak;
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'B' && (backup[x + param_ruch][y - param_ruch] == 'k' || backup[x + param_ruch][y - param_ruch] == 'p' || backup[x + param_ruch][y - param_ruch] == 'n'
                                                    || backup[x + param_ruch][y - param_ruch] == 'b' || backup[x + param_ruch][y - param_ruch] == 'r' || backup[x + param_ruch][y - param_ruch] == 'q'))
                                                    || (znak == 'b' && (backup[x + param_ruch][y - param_ruch] == 'K' || backup[x + param_ruch][y - param_ruch] == 'P' || backup[x + param_ruch][y - param_ruch] == 'N'
                                                    || backup[x + param_ruch][y - param_ruch] == 'B' || backup[x + param_ruch][y - param_ruch] == 'R' || backup[x + param_ruch][y - param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == 'B') || (tura_rywala == false && znak == 'b')) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == ' ') {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = ' ';

                                            backup[x - param_ruch][y + param_ruch] = ' ';
                                            backup[x][y] = znak;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                        } else {
                                            if ((znak == 'B' && (backup[x - param_ruch][y + param_ruch] == 'k' || backup[x - param_ruch][y + param_ruch] == 'p' || backup[x - param_ruch][y + param_ruch] == 'n'
                                                    || backup[x - param_ruch][y + param_ruch] == 'b' || backup[x - param_ruch][y + param_ruch] == 'r' || backup[x - param_ruch][y + param_ruch] == 'q'))
                                                    || (znak == 'b' && (backup[x - param_ruch][y + param_ruch] == 'K' || backup[x - param_ruch][y + param_ruch] == 'P' || backup[x - param_ruch][y + param_ruch] == 'N'
                                                    || backup[x - param_ruch][y + param_ruch] == 'B' || backup[x - param_ruch][y + param_ruch] == 'R' || backup[x - param_ruch][y + param_ruch] == 'Q'))) {
                                                char przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = ' ';

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                            }
                        }
                        break;
                    case 'p':
                        if (tura_rywala == false) {
                            if (x == 3 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x - 1][kolumna - 1] = 'p';
                                backup[x][kolumna - 1] = ' ';
                                backup[x][y] = ' ';

                                backup[x - 1][kolumna - 1] = ' ';
                                backup[x][kolumna - 1] = 'P';
                                backup[x][y] = 'p';

                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x - 1));

                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "EP "), 'P', backup));

                            }

                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x - 1 > -1) {
                                    if (b != 0 && ((backup[x - 1][y + b] == 'N')
                                            || (backup[x - 1][y + b] == 'P')
                                            || (backup[x - 1][y + b] == 'B')
                                            || (backup[x - 1][y + b] == 'R')
                                            || (backup[x - 1][y + b] == 'Q'
                                            || (backup[x - 1][y + b] == 'K')))) {
                                        char przechowalnie;
                                        przechowalnie = backup[x - 1][y + b];
                                        if (x != 1) {

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak)
                                                    + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, backup));

                                        } else {
                                            char[] symbole = {'q', 'r', 'b', 'n'};
                                            for (int s = 0; s < 4; s++) {

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));

                                                lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + " "), przechowalnie, backup));

                                            }
                                        }
                                    }
                                }
                            }

                            if (x - 1 > -1) {
                                if (backup[x - 1][y] == ' ' || (backup[x - 1][y] != 'p' && backup[x - 1][y] != 'P'
                                        && backup[x - 1][y] != 'N' && backup[x - 1][y] != 'n' && backup[x - 1][y] != 'B'
                                        && backup[x - 1][y] != 'b' && backup[x - 1][y] != 'R' && backup[x - 1][y] != 'r'
                                        && backup[x - 1][y] != 'Q' && backup[x - 1][y] != 'q' && backup[x - 1][y] != 'K'
                                        && backup[x - 1][y] != 'k')) {
                                    char przechowalnie;
                                    przechowalnie = backup[x - 1][y];
                                    if (x != 1) {
                                        backup[x - 1][y] = znak;
                                        backup[x][y] = ' ';

                                        backup[x][y] = znak;
                                        backup[x - 1][y] = przechowalnie;

                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));

                                        lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', backup));

                                    } else {
                                        char[] symbole = {'q', 'r', 'b', 'n'};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x - 1][y] = symbole[s];
                                            backup[x][y] = ' ';

                                            backup[x][y] = znak;
                                            backup[x - 1][y] = przechowalnie;

                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));

                                            lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak)
                                                    + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + " "), ' ', backup));

                                        }
                                    }
                                }
                            }
                            if (x - 2 > -1) {
                                if ((backup[x - 2][y] == ' ' || (backup[x - 2][y] != 'p' && backup[x - 2][y] != 'P'
                                        && backup[x - 2][y] != 'N' && backup[x - 2][y] != 'n' && backup[x - 2][y] != 'B'
                                        && backup[x - 2][y] != 'b' && backup[x - 2][y] != 'R' && backup[x - 2][y] != 'r'
                                        && backup[x - 2][y] != 'Q' && backup[x - 2][y] != 'q' && backup[x - 2][y] != 'K'
                                        && backup[x - 2][y] != 'k'))
                                        && (backup[x - 1][y] == ' ' || (backup[x - 1][y] != 'p' && backup[x - 1][y] != 'P'
                                        && backup[x - 1][y] != 'N' && backup[x - 1][y] != 'n' && backup[x - 1][y] != 'B'
                                        && backup[x - 1][y] != 'b' && backup[x - 1][y] != 'R' && backup[x - 1][y] != 'r'
                                        && backup[x - 1][y] != 'Q' && backup[x - 1][y] != 'q' && backup[x - 1][y] != 'K'
                                        && backup[x - 1][y] != 'k')) && x == 6) {
                                    char przechowalnie = backup[x - 2][y];
                                    backup[x - 2][y] = znak;
                                    backup[x][y] = ' ';

                                    backup[x][y] = znak;
                                    backup[x - 2][y] = przechowalnie;
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 2));

                                    lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', backup));

                                }
                            }
                        }
                        break;
                    case 'N':
                    case 'n':
                        if ((tura_rywala != false && znak == 'N') || (tura_rywala == false && znak == 'n')) {
                            for (int i = -2; i < 3; i++) {
                                for (int j = -2; j < 3; j++) {
                                    if (x + i > -1 && x + i < 8 && y + j > -1 && y + j < 8) {
                                        if (Math.abs(j) - Math.abs(i) != 0 && i != 0 && j != 0) {
                                            if ((znak == 'n'
                                                    && ((backup[x + i][y + j] == ' '
                                                    || backup[x + i][y + j] == 'P'
                                                    || backup[x + i][y + j] == 'N'
                                                    || backup[x + i][y + j] == 'B'
                                                    || backup[x + i][y + j] == 'R'
                                                    || backup[x + i][y + j] == 'Q'
                                                    || backup[x + i][y + j] == 'K')
                                                    || (backup[x + i][y + j] != 'p'
                                                    && backup[x + i][y + j] != 'n'
                                                    && backup[x + i][y + j] != 'b'
                                                    && backup[x + i][y + j] != 'r'
                                                    && backup[x + i][y + j] != 'q'
                                                    && backup[x + i][y + j] != 'k')))
                                                    || (znak == 'N'
                                                    && ((backup[x + i][y + j] == ' '
                                                    || backup[x + i][y + j] == 'p'
                                                    || backup[x + i][y + j] == 'n'
                                                    || backup[x + i][y + j] == 'b'
                                                    || backup[x + i][y + j] == 'r'
                                                    || backup[x + i][y + j] == 'q'
                                                    || backup[x + i][y + j] == 'k')
                                                    || (backup[x + i][y + j] != 'P'
                                                    && backup[x + i][y + j] != 'N'
                                                    && backup[x + i][y + j] != 'B'
                                                    && backup[x + i][y + j] != 'R'
                                                    && backup[x + i][y + j] != 'Q'
                                                    && backup[x + i][y + j] != 'K')))) {
                                                char przechowalnia = backup[x + i][y + j];
                                                backup[x + i][y + j] = znak;
                                                backup[x][y] = ' ';

                                                backup[x][y] = znak;
                                                backup[x + i][y + j] = przechowalnia;
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                if (przechowalnia == ' ') {

                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                                } else {

                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup));

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        break;
                    case 'K':
                        if (tura_rywala != false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == ' '
                                                    || (backup[x + i][y + j] == 'p' || backup[x + i][y + j] == 'n'
                                                    || backup[x + i][y + j] == 'b' || backup[x + i][y + j] == 'r'
                                                    || backup[x + i][y + j] == 'q' || backup[x + i][y + j] == 'k')) {
                                                char przech = backup[x + i][y + j];

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                if (przech == ' ') {

                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                                } else {

                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech, backup));

                                                }

                                            }
                                        }
                                    }
                                }
                            }

                        }
                        break;
                    case 'k':
                        if (tura_rywala == false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == ' '
                                                    || (backup[x + i][y + j] == 'P' || backup[x + i][y + j] == 'N'
                                                    || backup[x + i][y + j] == 'B' || backup[x + i][y + j] == 'R'
                                                    || backup[x + i][y + j] == 'Q' || backup[x + i][y + j] == 'K')) {
                                                char przech = backup[x + i][y + j];

                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                if (przech == ' ') {

                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', backup));

                                                } else {

                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(true, ((znak) + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech, backup));

                                                }

                                            }
                                        }
                                    }
                                }
                            }

                        }
                        break;
                }

            }//System.out.println("");

        }
        return lista_dopuszcalnych_Ruchow;
    }

    static ArrayList<Ruch> generuj_posuniecia(SI_MIN_MAX_Alfa_Beta.figury[][] ust, boolean tura_rywala, boolean przelotcan,
            boolean blackleft, boolean blackright, boolean whiteleft, boolean whiteright,
            boolean kingrochB, boolean kingrochC, int kolumna, boolean all, boolean mgla) {

        ArrayList<Ruch> lista_dopuszcalnych_Ruchow = new ArrayList<>();
        SI_MIN_MAX_Alfa_Beta.figury[][] backup = new SI_MIN_MAX_Alfa_Beta.figury[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                backup[x][y] = ust[x][y];
            }
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (backup[x][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                    continue;
                }
                boolean szach = false;
                boolean wynik;
                SI_MIN_MAX_Alfa_Beta.figury znak = backup[x][y];

                int param_ruch = 1;
                int przod;
                int tyl;
                boolean w1, w2, w3, w4, d1, d2, d3, d4;
                // //System.out.print("["+x+" "+y+"]"+znak);
                switch (znak) {
                    case BPion:
                        if (tura_rywala) {
                            if (x == 4 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x + 1][kolumna - 1] = SI_MIN_MAX_Alfa_Beta.figury.BPion;
                                backup[x][kolumna - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                backup[x + 1][kolumna - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                backup[x][kolumna - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.BPion;
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x + 1));

                                    if (!szach) {
                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "EP "), ' ', (konwert(backup))));
                                    } else {
                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), ' ', (konwert(backup))));
                                    }

                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x + 1 < 8) {
                                    if (b != 0 && ((backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)
                                            || (backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            || (backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec)
                                            || (backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.CWieza)
                                            || (backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.CHetman)
                                            || (mgla && backup[x + 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.CKrol))) {
                                        SI_MIN_MAX_Alfa_Beta.figury przechowalnie;
                                        przechowalnie = backup[x + 1][y + b];
                                        if (x != 6) {
                                            backup[x + 1][y + b] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x][y] = znak;
                                            backup[x + 1][y + b] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P"
                                                            + "" + x1 + x2 + "x" + y1 + y2 + "--+"), pozyskaj_symbol(przechowalnie), (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P"
                                                            + "" + x1 + x2 + "x" + y1 + y2 + "-- "), pozyskaj_symbol(przechowalnie), (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            SI_MIN_MAX_Alfa_Beta.figury[] symbole = {SI_MIN_MAX_Alfa_Beta.figury.BHetman, SI_MIN_MAX_Alfa_Beta.figury.BWieza, SI_MIN_MAX_Alfa_Beta.figury.BGoniec, SI_MIN_MAX_Alfa_Beta.figury.BSkoczek};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x + 1][y + b] = symbole[s];
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x][y] = znak;
                                                backup[x + 1][y + b] = przechowalnie;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x + 1));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + "+"), pozyskaj_symbol(przechowalnie), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + " "), pozyskaj_symbol(przechowalnie), (konwert(backup))));
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x + 1 < 8) {
                                if (backup[x + 1][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka || (backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.pustka && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BPion
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BSkoczek && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CSkoczek && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BWieza && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BKrol
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CKrol)) {
                                    SI_MIN_MAX_Alfa_Beta.figury przechowalnie;
                                    przechowalnie = backup[x + 1][y];
                                    if (x != 6) {
                                        backup[x + 1][y] = znak;
                                        backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                        wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                        backup[x][y] = znak;
                                        backup[x + 1][y] = przechowalnie;
                                        if (wynik || all) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', (konwert(backup))));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', (konwert(backup))));
                                            }

                                        }
                                    } else {
                                        SI_MIN_MAX_Alfa_Beta.figury[] symbole = {SI_MIN_MAX_Alfa_Beta.figury.BHetman, SI_MIN_MAX_Alfa_Beta.figury.BWieza, SI_MIN_MAX_Alfa_Beta.figury.BGoniec, SI_MIN_MAX_Alfa_Beta.figury.BSkoczek};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x + 1][y] = symbole[s];
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x][y] = znak;
                                            backup[x + 1][y] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 1));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P"
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + "+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P"
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + " "), ' ', (konwert(backup))));
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                            if (x + 2 < 8) {
                                if ((backup[x + 2][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka || (backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.pustka && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BPion
                                        && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BSkoczek && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CSkoczek && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                        && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BWieza && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                        && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BKrol
                                        && backup[x + 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CKrol))
                                        && (backup[x + 1][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka || (backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.pustka && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BPion
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BSkoczek && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CSkoczek && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BWieza && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BKrol
                                        && backup[x + 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CKrol)) && x == 1) {
                                    SI_MIN_MAX_Alfa_Beta.figury przechowalnie = backup[x + 2][y];
                                    backup[x + 2][y] = znak;
                                    backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                    wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                    backup[x][y] = znak;
                                    backup[x + 2][y] = przechowalnie;
                                    if (wynik || all) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + 2));
                                        if (szach) {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', (konwert(backup))));
                                        } else {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', (konwert(backup))));
                                        }

                                    }
                                }
                            }
                        }
                        break;
                    case CHetman:
                    case BHetman:

                        if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman) || (!tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman)) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            przod = 1;
                            tyl = -1;
                            param_ruch = 1;
                            while (d1 || d2 || d3 || d4
                                    || w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman)) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x + param_ruch][y + param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman && (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    && (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x + param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman)) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x - param_ruch][y - param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman && (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    && (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x - param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman)) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x + param_ruch][y - param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman && (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    && (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x + param_ruch][y - param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman)) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x - param_ruch][y + param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman && (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    && (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x - param_ruch][y + param_ruch] = przechowalnia;
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman)) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x + param_ruch][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    && (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CKrol & mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    && (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BKrol & mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x + param_ruch][y] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman)) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x - param_ruch][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    && (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CKrol & mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    && (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BKrol & mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x - param_ruch][y] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman)) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x][y + param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    && (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol & mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    && (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol & mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x][y + param_ruch] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BHetman) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CHetman)) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x][y - param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    && (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol & mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    && (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol & mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x][y - param_ruch] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                tyl = tyl - 1;
                                param_ruch = param_ruch + 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case BWieza:
                    case CWieza:
                        if ((tura_rywala != false && znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza)) {
                            w1 = true;
                            w2 = true;
                            w3 = true;
                            w4 = true;
                            param_ruch = 1;
                            przod = 1;
                            tyl = -1;
                            while (w1 || w2 || w3 || w4) {
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza)) {
                                    if (x + param_ruch < 8 && w1) {

                                        if (w1 && backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x + param_ruch][y] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x + param_ruch][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    && (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CKrol & mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    && (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x + param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BKrol & mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x + param_ruch][y];
                                                backup[x + param_ruch][y] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x + param_ruch][y] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                w1 = false;
                                            } else {
                                                w1 = false;
                                            }
                                        }
                                    } else {
                                        w1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza)) {
                                    if (x - param_ruch > -1 && w2) {

                                        if (w2 && backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x - param_ruch][y] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x - param_ruch][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    && (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.CKrol & mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    && (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x - param_ruch][y] == SI_MIN_MAX_Alfa_Beta.figury.BKrol & mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x - param_ruch][y];
                                                backup[x - param_ruch][y] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x - param_ruch][y] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                w2 = false;
                                            } else {
                                                w2 = false;
                                            }
                                        }
                                    } else {
                                        w2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza)) {
                                    if (y + param_ruch < 8 && w3) {

                                        if (w3 && backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x][y + param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    && (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol & mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    && (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol & mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x][y + param_ruch];
                                                backup[x][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x][y + param_ruch] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                w3 = false;
                                            } else {
                                                w3 = false;
                                            }
                                        }
                                    } else {
                                        w3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza)) {
                                    if (y - param_ruch > -1 && w4) {

                                        if (w4 && backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x][y - param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    && (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol & mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    && (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol & mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x][y - param_ruch];
                                                backup[x][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x][y - param_ruch] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "R" : "r") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                w4 = false;
                                            } else {
                                                w4 = false;
                                            }
                                        }
                                    } else {
                                        w4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                                tyl = tyl - 1;
                                przod = przod + 1;
                            }
                        }
                        break;
                    case BGoniec:
                    case CGoniec:
                        if ((tura_rywala != false && znak == SI_MIN_MAX_Alfa_Beta.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CGoniec)) {
                            d1 = true;
                            d2 = true;
                            d3 = true;
                            d4 = true;
                            while (d1 || d2 || d3 || d4) {
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CGoniec)) {
                                    if (x + param_ruch < 8 && y + param_ruch < 8 && d1) {

                                        if (d1 && backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x + param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x + param_ruch][y + param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BGoniec && (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    && (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x + param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x + param_ruch][y + param_ruch];
                                                backup[x + param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x + param_ruch][y + param_ruch] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                d1 = false;
                                            } else {
                                                d1 = false;
                                            }
                                        }
                                    } else {
                                        d1 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CGoniec)) {
                                    if (x - param_ruch > -1 && y - param_ruch > -1 && d2) {
                                        //////System.out.println((x+param_ruch)+" "+(y+param_ruch));

                                        if (d2 && backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x - param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x - param_ruch][y - param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BGoniec && (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    && (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x - param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x - param_ruch][y - param_ruch];
                                                backup[x - param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x - param_ruch][y - param_ruch] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                d2 = false;
                                            } else {
                                                d2 = false;
                                            }
                                        }
                                    } else {
                                        d2 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CGoniec)) {
                                    if (x + param_ruch < 8 && y - param_ruch > -1 && d3) {

                                        if (d3 && backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x + param_ruch][y - param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x + param_ruch][y - param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BGoniec && (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    && (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x + param_ruch][y - param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x + param_ruch][y - param_ruch];
                                                backup[x + param_ruch][y - param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x + param_ruch][y - param_ruch] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y - param_ruch)), y2 = (char) ('1' + (x + param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                d3 = false;
                                            } else {
                                                d3 = false;
                                            }
                                        }
                                    } else {
                                        d3 = false;
                                    }
                                }
                                if ((tura_rywala && znak == SI_MIN_MAX_Alfa_Beta.figury.BGoniec) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CGoniec)) {
                                    if (x - param_ruch > -1 && y + param_ruch < 8 && d4) {

                                        if (d4 && backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                            backup[x - param_ruch][y + param_ruch] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x - param_ruch][y + param_ruch] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[x][y] = znak;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b")
                                                            + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.BGoniec && (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.pustka || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CWieza || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    && (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x - param_ruch][y + param_ruch] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x - param_ruch][y + param_ruch];
                                                backup[x - param_ruch][y + param_ruch] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x - param_ruch][y + param_ruch] = (przechowalnia);
                                                backup[x][y] = znak;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + param_ruch)), y2 = (char) ('1' + (x - param_ruch));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "B" : "b") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                    }

                                                }
                                                d4 = false;
                                            } else {
                                                d4 = false;
                                            }
                                        }
                                    } else {
                                        d4 = false;
                                    }
                                }
                                param_ruch = param_ruch + 1;
                            }
                        }
                        break;
                    case CPion:
                        if (tura_rywala == false) {
                            if (x == 3 && przelotcan && kolumna != 0 && (kolumna - 1 == y - 1 || kolumna - 1 == y + 1)) {

                                backup[x - 1][kolumna - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                backup[x][kolumna - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                backup[x - 1][kolumna - 1] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                backup[x][kolumna - 1] = SI_MIN_MAX_Alfa_Beta.figury.BPion;
                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (kolumna - 1)), y2 = (char) ('1' + (x - 1));
                                    if (szach == false) {
                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "EP "), 'P', (konwert(backup))));
                                    } else {
                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), 'P', (konwert(backup))));
                                    }

                                }
                            }
                            for (int b = -1; b < 2; b++) {

                                if (y + b > -1 && y + b < 8 && x - 1 > -1) {
                                    if (b != 0 && ((backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek)
                                            || (backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.BPion)
                                            || (backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec)
                                            || (backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.BWieza)
                                            || (backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.BHetman)
                                            || (mgla && backup[x - 1][y + b] == SI_MIN_MAX_Alfa_Beta.figury.BKrol))) {
                                        SI_MIN_MAX_Alfa_Beta.figury przechowalnie;
                                        przechowalnie = backup[x - 1][y + b];
                                        if (x != 1) {
                                            backup[x - 1][y + b] = znak;
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x][y] = znak;
                                            backup[x - 1][y + b] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p"
                                                            + "" + x1 + x2 + "x" + y1 + y2 + "--+"), pozyskaj_symbol(przechowalnie), (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p"
                                                            + "" + x1 + x2 + "x" + y1 + y2 + "-- "), pozyskaj_symbol(przechowalnie), (konwert(backup))));
                                                }

                                            }
                                        } else {
                                            SI_MIN_MAX_Alfa_Beta.figury[] symbole = {SI_MIN_MAX_Alfa_Beta.figury.CHetman, SI_MIN_MAX_Alfa_Beta.figury.CWieza, SI_MIN_MAX_Alfa_Beta.figury.CGoniec, SI_MIN_MAX_Alfa_Beta.figury.CSkoczek};
                                            for (int s = 0; s < 4; s++) {
                                                backup[x - 1][y + b] = symbole[s];
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x][y] = znak;
                                                backup[x - 1][y + b] = przechowalnie;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + b)), y2 = (char) ('1' + (x - 1));
                                                    if (szach) {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + "+"), pozyskaj_symbol(przechowalnie), (konwert(backup))));
                                                    } else {
                                                        lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + " "), pozyskaj_symbol(przechowalnie), (konwert(backup))));
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (x - 1 > -1) {
                                if (backup[x - 1][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka || (backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.pustka && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BPion
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BSkoczek && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CSkoczek && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BWieza && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BKrol
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CKrol)) {
                                    SI_MIN_MAX_Alfa_Beta.figury przechowalnie;
                                    przechowalnie = backup[x - 1][y];
                                    if (x != 1) {
                                        backup[x - 1][y] = znak;
                                        backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                        wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                        backup[x][y] = znak;
                                        backup[x - 1][y] = przechowalnie;
                                        if (wynik || all) {
                                            char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', (konwert(backup))));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', (konwert(backup))));
                                            }

                                        }
                                    } else {
                                        SI_MIN_MAX_Alfa_Beta.figury[] symbole = {SI_MIN_MAX_Alfa_Beta.figury.CHetman, SI_MIN_MAX_Alfa_Beta.figury.CWieza, SI_MIN_MAX_Alfa_Beta.figury.CGoniec, SI_MIN_MAX_Alfa_Beta.figury.CSkoczek};
                                        for (int s = 0; s < 4; s++) {
                                            backup[x - 1][y] = symbole[s];
                                            backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                            backup[x][y] = znak;
                                            backup[x - 1][y] = przechowalnie;
                                            if (wynik || all) {
                                                char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 1));
                                                if (szach) {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p"
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + "+"), ' ', (konwert(backup))));
                                                } else {
                                                    lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p"
                                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + pozyskaj_symbol(symbole[s]) + " "), ' ', (konwert(backup))));
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                            if (x - 2 > -1) {
                                if ((backup[x - 2][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka || (backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.pustka && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BPion
                                        && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BSkoczek && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CSkoczek && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                        && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BWieza && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                        && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.BKrol
                                        && backup[x - 2][y] != SI_MIN_MAX_Alfa_Beta.figury.CKrol))
                                        && (backup[x - 1][y] == SI_MIN_MAX_Alfa_Beta.figury.pustka || (backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.pustka && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BPion
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BSkoczek && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CSkoczek && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BWieza && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.BKrol
                                        && backup[x - 1][y] != SI_MIN_MAX_Alfa_Beta.figury.CKrol)) && x == 6) {
                                    SI_MIN_MAX_Alfa_Beta.figury przechowalnie = backup[x - 2][y];
                                    backup[x - 2][y] = znak;
                                    backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                    wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                    backup[x][y] = znak;
                                    backup[x - 2][y] = przechowalnie;
                                    if (wynik || all) {
                                        char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y)), y2 = (char) ('1' + (x - 2));
                                        if (szach) {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), ' ', (konwert(backup))));
                                        } else {
                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("p" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), ' ', (konwert(backup))));
                                        }

                                    }
                                }
                            }
                        }
                        break;
                    case CSkoczek:
                    case BSkoczek:
                        if ((tura_rywala != false && znak == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek) || (tura_rywala == false && znak == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)) {
                            for (int i = -2; i < 3; i++) {
                                for (int j = -2; j < 3; j++) {
                                    if (x + i > -1 && x + i < 8 && y + j > -1 && y + j < 8) {
                                        if (Math.abs(j) - Math.abs(i) != 0 && i != 0 && j != 0) {
                                            if ((znak == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    && ((backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla))
                                                    || (backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.CKrol)))
                                                    || (znak == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    && ((backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla)
                                                    || (backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    && backup[x + i][y + j] != SI_MIN_MAX_Alfa_Beta.figury.BKrol)))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przechowalnia = backup[x + i][y + j];
                                                backup[x + i][y + j] = znak;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x][y] = znak;
                                                backup[x + i][y + j] = (przechowalnia);
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if ((przechowalnia) == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "N" : "n") + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "N" : "n") + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                        }

                                                    } else {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "N" : "n") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ((tura_rywala ? "N" : "n") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przechowalnia), (konwert(backup))));
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case BKrol:
                        if (tura_rywala != false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CGoniec
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CWieza
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CHetman
                                                    || (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.CKrol && mgla))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                        }

                                                    } else {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przech), (konwert(backup))));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przech), (konwert(backup))));
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (RuchZagrozenie_kontrola.szach((backup), true)) {
                                if (kingrochB) {
                                    if (whiteright && (backup[0][5] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[0][6] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[0][4] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)
                                            && (backup[0][7] == SI_MIN_MAX_Alfa_Beta.figury.BWieza)) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r < 3; r++) {
                                            backup[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[0][4 + r] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((backup), true);
                                            backup[0][4] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                                            backup[0][4 + r] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[0][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[0][5] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                                            backup[0][6] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                                            backup[0][4] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                                            backup[0][7] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                                            backup[0][5] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[0][6] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O    +"),
                                                        ' ', (konwert(backup))));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O     "),
                                                        ' ', (konwert(backup))));
                                            }

                                        }
                                    }
                                    if (whiteleft && (backup[0][3] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[0][2] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[0][1] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[0][4] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)
                                            && (backup[0][0] == SI_MIN_MAX_Alfa_Beta.figury.BWieza)) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r <= 2; r++) {
                                            backup[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[0][4 - r] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((backup), true);
                                            backup[0][4] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                                            backup[0][4 - r] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[0][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[0][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[0][3] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                                            backup[0][2] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;

                                            backup[0][4] = SI_MIN_MAX_Alfa_Beta.figury.BKrol;
                                            backup[0][0] = SI_MIN_MAX_Alfa_Beta.figury.BWieza;
                                            backup[0][3] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[0][2] = SI_MIN_MAX_Alfa_Beta.figury.pustka;

                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O-O  +"),
                                                        ' ', (konwert(backup))));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("KO-O-O   "),
                                                        ' ', (konwert(backup))));
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case CKrol:
                        if (tura_rywala == false) {
                            for (int i = -1; i <= 1; i++) {
                                for (int j = -1; j <= 1; j++) {
                                    if (i != 0 || j != 0) {
                                        if ((x + i > -1 && x + i < 8) && (y + j > -1 && y + j < 8)) {
                                            if (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.pustka
                                                    || (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BPion
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BGoniec
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BWieza
                                                    || backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BHetman
                                                    || (backup[x + i][y + j] == SI_MIN_MAX_Alfa_Beta.figury.BKrol && mgla))) {
                                                SI_MIN_MAX_Alfa_Beta.figury przech = backup[x + i][y + j];
                                                backup[x + i][y + j] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                                wynik = mgla || (all || !RuchZagrozenie_kontrola.szach((backup), tura_rywala));

                                                backup[x + i][y + j] = przech;
                                                backup[x][y] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                                                if (wynik || all) {
                                                    char x1 = (char) ('A' + (y)), x2 = (char) ('1' + (x)), y1 = (char) ('A' + (y + j)), y2 = (char) ('1' + (x + i));
                                                    if (przech == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), ' ', (konwert(backup))));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), ' ', (konwert(backup))));
                                                        }

                                                    } else {
                                                        if (szach) {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), pozyskaj_symbol(przech), (konwert(backup))));
                                                        } else {
                                                            lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), pozyskaj_symbol(przech), (konwert(backup))));
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!RuchZagrozenie_kontrola.szach((backup), false)) {
                                if (kingrochC) {
                                    if (blackright && (backup[7][5] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[7][6] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[7][4] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)
                                            && (backup[7][7] == SI_MIN_MAX_Alfa_Beta.figury.CWieza)) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r < 3; r++) {
                                            backup[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[7][4 + r] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((backup), false);
                                            backup[7][4] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                                            backup[7][4 + r] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[7][7] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[7][5] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                                            backup[7][6] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                                            backup[7][4] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                                            backup[7][7] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                                            backup[7][5] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[7][6] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O    +"),
                                                        ' ', (konwert(backup))));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O     "),
                                                        ' ', (konwert(backup))));
                                            }

                                        }
                                    }
                                    if (blackleft && (backup[7][3] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[7][2] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[7][1] == SI_MIN_MAX_Alfa_Beta.figury.pustka)
                                            && (backup[7][4] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)
                                            && (backup[7][0] == SI_MIN_MAX_Alfa_Beta.figury.CWieza)) {
                                        boolean[] wyniki = new boolean[2];
                                        for (int r = 1; r <= 2; r++) {
                                            backup[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[7][4 - r] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((backup), false);
                                            backup[7][4] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                                            backup[7][4 - r] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                        }
                                        if (wyniki[0] && wyniki[1]) {
                                            backup[7][4] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[7][0] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[7][3] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                                            backup[7][2] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;

                                            backup[7][4] = SI_MIN_MAX_Alfa_Beta.figury.CKrol;
                                            backup[7][0] = SI_MIN_MAX_Alfa_Beta.figury.CWieza;
                                            backup[7][3] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            backup[7][2] = SI_MIN_MAX_Alfa_Beta.figury.pustka;
                                            if (szach) {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O-O  +"),
                                                        ' ', (konwert(backup))));
                                            } else {
                                                lista_dopuszcalnych_Ruchow.add(new Ruch(false, ("kO-O-O   "),
                                                        ' ', (konwert(backup))));
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        break;
                }

            }//System.out.println("");

        }

        try {
            Collections.sort(lista_dopuszcalnych_Ruchow);
        } catch (Exception e) {
            System.out.println(e);
        }
        return (lista_dopuszcalnych_Ruchow);

    }

    public static char[][] konwert(SzachowaArena.figury[][] decha) {
        char[][] wynik = new char[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                switch (decha[x][y]) {
                    case BAmazonka:
                        wynik[x][y] = 'A';
                        break;
                    case CAmazonka:
                        wynik[x][y] = 'a';
                        break;
                    case BKrol:
                        wynik[x][y] = 'K';
                        break;
                    case CKrol:
                        wynik[x][y] = 'k';
                        break;
                    case BHetman:
                        wynik[x][y] = 'Q';
                        break;
                    case CHetman:
                        wynik[x][y] = 'q';
                        break;
                    case BWieza:
                        wynik[x][y] = 'R';
                        break;
                    case CWieza:
                        wynik[x][y] = 'r';
                        break;
                    case BGoniec:
                        wynik[x][y] = 'B';
                        break;
                    case CGoniec:
                        wynik[x][y] = 'b';
                        break;
                    case BSkoczek:
                        wynik[x][y] = 'N';
                        break;
                    case CSkoczek:
                        wynik[x][y] = 'n';
                        break;
                    case BPion:
                        wynik[x][y] = 'P';
                        break;
                    case CPion:
                        wynik[x][y] = 'p';
                        break;
                }
            }
        }
        return wynik;
    }
    public static char[][] konwert(SI_MIN_MAX_Alfa_Beta.figury[][] decha) {
        char[][] wynik = new char[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                switch (decha[x][y]) {
                    case BKrol:
                        wynik[x][y] = 'K';
                        break;
                    case CKrol:
                        wynik[x][y] = 'k';
                        break;
                    case BHetman:
                        wynik[x][y] = 'Q';
                        break;
                    case CHetman:
                        wynik[x][y] = 'q';
                        break;
                    case BWieza:
                        wynik[x][y] = 'R';
                        break;
                    case CWieza:
                        wynik[x][y] = 'r';
                        break;
                    case BGoniec:
                        wynik[x][y] = 'B';
                        break;
                    case CGoniec:
                        wynik[x][y] = 'b';
                        break;
                    case BSkoczek:
                        wynik[x][y] = 'N';
                        break;
                    case CSkoczek:
                        wynik[x][y] = 'n';
                        break;
                    case BPion:
                        wynik[x][y] = 'P';
                        break;
                    case CPion:
                        wynik[x][y] = 'p';
                        break;
                }
            }
        }
        return wynik;
    }

    private static char pozyskaj_symbol(SI_MIN_MAX_Alfa_Beta.figury przechowalnie) {
        switch (przechowalnie) {
                    case BKrol:
                        return 'K';
                        
                    case CKrol:
                        return 'k';
                        
                    case BHetman:
                        return 'Q';
                        
                    case CHetman:
                        return 'q';
                        
                    case BWieza:
                        return 'R';
                        
                    case CWieza:
                        return 'r';
                        
                    case BGoniec:
                        return 'B';
                        
                    case CGoniec:
                        return 'b';
                        
                    case BSkoczek:
                        return 'N';
                        
                    case CSkoczek:
                        return 'n';
                        
                    case BPion:
                        return 'P';
                        
                    case CPion:
                        return 'p';
                    default:
                        return ' ';
                }
    }
}

