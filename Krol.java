/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package szachy;

import java.util.ArrayList;

/**
 *
 * @author PatrykK
 */
public class Krol extends FiguraK {

    boolean roszada, lewo, prawo;

    Krol(char c, boolean b, int i, int j, boolean kingroch, boolean dluga, boolean krotka) {
        symbol = c;
        czybialy = b;
        poczatekY = i;
        poczatekX = j;
        roszada = kingroch;
        lewo = dluga;
        prawo = krotka;
    }

    ArrayList<Ruch> daj_mozliwosci(FiguraK[][] ust, char[][] ust2, boolean wszystko) {
        ArrayList<Ruch> ruchy = new ArrayList<>();
        boolean wynik, szach;
        if (czybialy) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        if ((poczatekY + i > -1 && poczatekY + i < 8) && (poczatekX + j > -1 && poczatekX + j < 8)) {
                            if (ust[poczatekY + i][poczatekX + j] == null
                                    || (ust[poczatekY + i][poczatekX + j].symbol == 'p' || ust[poczatekY + i][poczatekX + j].symbol == 'n' || ust[poczatekY + i][poczatekX + j].symbol == 'b' || ust[poczatekY + i][poczatekX + j].symbol == 'r' || ust[poczatekY + i][poczatekX + j].symbol == 'q')) {
                                char przech2 = ust2[poczatekY + i][poczatekX + j];
                                ust2[poczatekY + i][poczatekX + j] = 'K';
                                ust2[poczatekY][poczatekX] = ' ';

                                wynik = (wszystko || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                                szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                                ust2[poczatekY + i][poczatekX + j] = przech2;
                                ust2[poczatekY][poczatekX] = 'K';
                                if (wynik || wszystko) {
                                    char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + j)), y2 = (char) ('1' + (poczatekY + i));
                                    if (przech2 == ' ') {
                                        if (szach) {
                                            ruchy.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                                        } else {
                                            ruchy.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                                        }

                                    } else {
                                        if (szach) {
                                            ruchy.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przech2, ust2));
                                        } else {
                                            ruchy.add(new Ruch(false, ("K" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech2, ust2));
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!RuchZagrozenie_kontrola.szach((ust2), false)) {
                if (roszada) {
                    if (prawo && (ust[0][5] == null)
                            && (ust[0][6] == null)
                            && (ust[0][4].symbol == 'K')
                            && (ust[0][0].symbol == 'R')) {
                        boolean[] wyniki = new boolean[2];
                        for (int r = 1; r < 3; r++) {
                            ust2[0][4] = ' ';
                            ust2[0][4 + r] = 'K';
                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((ust2), false);
                            ust2[0][4] = 'K';
                            ust2[0][4 + r] = ' ';
                        }
                        if (wyniki[0] && wyniki[1]) {
                            ust2[0][4] = ' ';
                            ust2[0][5] = 'R';
                            ust2[0][6] = 'K';
                            szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);
                            ust2[0][4] = 'K';
                            ust2[0][0] = 'R';
                            ust2[0][5] = ' ';
                            ust2[0][6] = ' ';
                            if (szach) {
                                ruchy.add(new Ruch(false, ("KO-O    +"),
                                        SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ("KO-O     "),
                                        SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            }

                        }
                    }
                    if (lewo && (ust[0][3].symbol == ' ')
                            && (ust[0][2].symbol == ' ')
                            && (ust[0][1].symbol == ' ')
                            && (ust[0][4].symbol == 'K')
                            && (ust[0][0].symbol == 'R')) {
                        boolean[] wyniki = new boolean[2];
                        for (int r = 1; r <= 2; r++) {
                            ust2[0][4] = ' ';
                            ust2[0][4 - r] = 'K';
                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((ust2), false);
                            ust2[0][4] = 'K';
                            ust2[0][4 - r] = ' ';
                        }
                        if (wyniki[0] && wyniki[1]) {
                            ust2[0][4] = ' ';
                            ust2[0][0] = ' ';
                            ust2[0][3] = 'R';
                            ust2[0][2] = 'K';
                            szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);
                            ust2[0][4] = 'K';
                            ust2[0][0] = 'R';
                            ust2[0][3] = ' ';
                            ust2[0][2] = ' ';
                            if (szach) {
                                ruchy.add(new Ruch(false, ("KO-O-O  +"),
                                        SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ("KO-O-O   "),
                                        SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            }

                        }
                    }
                }
            }
        } else {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        if ((poczatekY + i > -1 && poczatekY + i < 8) && (poczatekX + j > -1 && poczatekX + j < 8)) {
                            if (ust[poczatekY + i][poczatekX + j] == null
                                    || (ust[poczatekY + i][poczatekX + j].symbol == 'P' || ust[poczatekY + i][poczatekX + j].symbol == 'N' || ust[poczatekY + i][poczatekX + j].symbol == 'B' || ust[poczatekY + i][poczatekX + j].symbol == 'R' || ust[poczatekY + i][poczatekX + j].symbol == 'Q')) {
                                char przech2 = ust2[poczatekY + i][poczatekX + j];
                                ust2[poczatekY + i][poczatekX + j] = 'K';
                                ust2[poczatekY][poczatekX] = ' ';

                                wynik = (wszystko || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                                szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                                ust2[poczatekY + i][poczatekX + j] = przech2;
                                ust2[poczatekY][poczatekX] = 'k';
                                if (wynik || wszystko) {
                                    char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + j)), y2 = (char) ('1' + (poczatekY + i));
                                    if (przech2 == ' ') {
                                        if (szach) {
                                            ruchy.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                                        } else {
                                            ruchy.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                                        }

                                    } else {
                                        if (szach) {
                                            ruchy.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przech2, ust2));
                                        } else {
                                            ruchy.add(new Ruch(false, ("k" + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przech2, ust2));
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!RuchZagrozenie_kontrola.szach((ust2), false)) {
                if (roszada) {
                    if (prawo && (ust[7][5] == null)
                            && (ust[7][6] == null)
                            && (ust[7][4].symbol == 'k')
                            && (ust[7][0].symbol == 'r')) {
                        boolean[] wyniki = new boolean[2];
                        for (int r = 1; r < 3; r++) {
                            ust2[7][4] = ' ';
                            ust2[7][4 + r] = 'k';
                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((ust2), false);
                            ust2[7][4] = 'k';
                            ust2[7][4 + r] = ' ';
                        }
                        if (wyniki[0] && wyniki[1]) {
                            ust2[7][4] = ' ';
                            ust2[7][5] = 'r';
                            ust2[7][6] = 'k';
                            szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);
                            ust2[7][4] = 'k';
                            ust2[7][0] = 'r';
                            ust2[7][5] = ' ';
                            ust2[7][6] = ' ';
                            if (szach) {
                                ruchy.add(new Ruch(false, ("kO-O    +"),
                                        SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ("kO-O     "),
                                        SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            }

                        }
                    }
                    if (lewo && (ust[7][3].symbol == ' ')
                            && (ust[7][2].symbol == ' ')
                            && (ust[7][1].symbol == ' ')
                            && (ust[7][4].symbol == 'k')
                            && (ust[7][0].symbol == 'r')) {
                        boolean[] wyniki = new boolean[2];
                        for (int r = 1; r <= 2; r++) {
                            ust2[7][4] = ' ';
                            ust2[7][4 - r] = 'k';
                            wyniki[r - 1] = !RuchZagrozenie_kontrola.szach((ust2), false);
                            ust2[7][4] = 'k';
                            ust2[7][4 - r] = ' ';
                        }
                        if (wyniki[0] && wyniki[1]) {
                            ust2[7][4] = ' ';
                            ust2[7][0] = ' ';
                            ust2[7][3] = 'r';
                            ust2[7][2] = 'k';
                            szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);
                            ust2[7][4] = 'k';
                            ust2[7][0] = 'r';
                            ust2[7][3] = ' ';
                            ust2[7][2] = ' ';
                            if (szach) {
                                ruchy.add(new Ruch(false, ("kO-O-O  +"),
                                        SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ("kO-O-O   "),
                                        SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            }

                        }
                    }
                }
            }
        }
        return ruchy;
    }

}
