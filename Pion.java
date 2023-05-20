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
public class Pion extends FiguraK {

    boolean enpassant;
    int kol;

    Pion(char c, boolean b, int i, int j) {
        symbol = c;
        czybialy = b;
        poczatekY = i;
        poczatekX = j;
    }

    ArrayList<Ruch> daj_mozliwosci(FiguraK[][] ust, char[][] ust2, boolean all, int kol, boolean enpassant) {
        ArrayList<Ruch> ruchy = new ArrayList<>();
        boolean wynik, szach;
        if (czybialy) {
            if (poczatekY == 4 && enpassant && kol != 0 && (kol - 1 == poczatekX - 1 || kol - 1 == poczatekX + 1)) {

                ust2[poczatekY + 1][kol - 1] = 'P';
                ust2[poczatekY][kol - 1] = ' ';
                ust2[poczatekY][poczatekX] = ' ';

                wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                ust2[poczatekY + 1][kol - 1] = ' ';
                ust2[poczatekY][kol - 1] = 'p';
                ust2[poczatekY][poczatekX] = 'P';
                if (wynik || all) {
                    char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (kol - 1)), y2 = (char) ('1' + (poczatekY + 1));

                    if (!szach) {
                        ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "EP "), SI_MIN_MAX_Alfa_Beta.figury.CPion, ust2));
                    } else {
                        ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), SI_MIN_MAX_Alfa_Beta.figury.CPion, ust2));
                    }

                }
            }
            for (int b = -1; b < 2; b++) {

                if (poczatekX + b > -1 && poczatekX + b < 8 && poczatekY + 1 < 8) {
                    if (b != 0 && ((ust[poczatekY + 1][poczatekX + b].symbol == 'n')
                            || (ust[poczatekY + 1][poczatekX + b].symbol == 'p')
                            || (ust[poczatekY + 1][poczatekX + b].symbol == 'b')
                            || (ust[poczatekY + 1][poczatekX + b].symbol == 'r')
                            || (ust[poczatekY + 1][poczatekX + b].symbol == 'q'))) {
                        char przechowalnie;
                        przechowalnie = ust2[poczatekY + 1][poczatekX + b];
                        if (poczatekY != 6) {
                            ust2[poczatekY + 1][poczatekX + b] = 'P';
                            ust2[poczatekY][poczatekX] = ' ';

                            wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                            szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                            ust2[poczatekY][poczatekX] = 'P';
                            ust2[poczatekY + 1][poczatekX + b] = przechowalnie;
                            if (wynik || all) {
                                char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + b)), y2 = (char) ('1' + (poczatekY + 1));
                                if (szach) {
                                    ruchy.add(new Ruch(false, ("P"
                                            + "" + x1 + x2 + "x" + y1 + y2 + "--+"), przechowalnie, ust2));
                                } else {
                                    ruchy.add(new Ruch(false, ("P"
                                            + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, ust2));
                                }

                            }
                        } else {
                            char[] symbole = {'Q', 'R', 'B', 'N'};
                            for (int s = 0; s < 4; s++) {
                                ust2[poczatekY + 1][poczatekX + b] = symbole[s];
                                ust2[poczatekY][poczatekX] = ' ';

                                wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                                szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                                ust2[poczatekY][poczatekX] = 'P';
                                ust2[poczatekY + 1][poczatekX + b] = przechowalnie;
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + b)), y2 = (char) ('1' + (poczatekY + 1));
                                    if (szach) {
                                        ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + "+"), przechowalnie, ust2));
                                    } else {
                                        ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + " "), przechowalnie, ust2));
                                    }

                                }
                            }
                        }
                    }
                }
            }

            if (poczatekY + 1 < 8) {
                if (ust[poczatekY + 1][poczatekX].symbol == ' ' || (ust[poczatekY + 1][poczatekX].symbol != 'p' && ust[poczatekY + 1][poczatekX].symbol != 'P'
                        && ust[poczatekY + 1][poczatekX].symbol != 'N' && ust[poczatekY + 1][poczatekX].symbol != 'n' && ust[poczatekY + 1][poczatekX].symbol != 'B'
                        && ust[poczatekY + 1][poczatekX].symbol != 'b' && ust[poczatekY + 1][poczatekX].symbol != 'R' && ust[poczatekY + 1][poczatekX].symbol != 'r'
                        && ust[poczatekY + 1][poczatekX].symbol != 'Q' && ust[poczatekY + 1][poczatekX].symbol != 'q' && ust[poczatekY + 1][poczatekX].symbol != 'K'
                        && ust[poczatekY + 1][poczatekX].symbol != 'k')) {
                    char przechowalnie;
                    przechowalnie = ust2[poczatekY + 1][poczatekX];
                    if (poczatekY != 6) {
                        ust2[poczatekY + 1][poczatekX] = 'P';
                        ust2[poczatekY][poczatekX] = ' ';

                        wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                        szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                        ust2[poczatekY][poczatekX] = 'P';
                        ust2[poczatekY + 1][poczatekX] = przechowalnie;
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY + 1));
                            if (szach) {
                                ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            }

                        }
                    } else {
                        char[] symbole = {'Q', 'R', 'B', 'N'};
                        for (int s = 0; s < 4; s++) {
                            ust2[poczatekY + 1][poczatekX] = symbole[s];
                            ust2[poczatekY][poczatekX] = ' ';
                            wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                            szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                            ust2[poczatekY][poczatekX] = 'P';
                            ust2[poczatekY + 1][poczatekX] = przechowalnie;
                            if (wynik || all) {
                                char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY + 1));
                                if (szach) {
                                    ruchy.add(new Ruch(false, ("P"
                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + "+"), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                                } else {
                                    ruchy.add(new Ruch(false, ("P"
                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + " "), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                                }

                            }
                        }
                    }
                }
            }
            if (poczatekY + 2 < 8) {
                if ((ust2[poczatekY + 2][poczatekX] == ' ' || (ust2[poczatekY + 2][poczatekX] != 'p' && ust2[poczatekY + 2][poczatekX] != 'P'
                        && ust2[poczatekY + 2][poczatekX] != 'N' && ust2[poczatekY + 2][poczatekX] != 'n' && ust2[poczatekY + 2][poczatekX] != 'B'
                        && ust2[poczatekY + 2][poczatekX] != 'b' && ust2[poczatekY + 2][poczatekX] != 'R' && ust2[poczatekY + 2][poczatekX] != 'r'
                        && ust2[poczatekY + 2][poczatekX] != 'Q' && ust2[poczatekY + 2][poczatekX] != 'r' && ust2[poczatekY + 2][poczatekX] != 'K'
                        && ust2[poczatekY + 2][poczatekX] != 'k'))
                        && (ust2[poczatekY + 1][poczatekX] == ' ' || (ust2[poczatekY + 1][poczatekX] != 'p' && ust2[poczatekY + 1][poczatekX] != 'P'
                        && ust2[poczatekY + 1][poczatekX] != 'N' && ust2[poczatekY + 1][poczatekX] != 'n' && ust2[poczatekY + 1][poczatekX] != 'B'
                        && ust2[poczatekY + 1][poczatekX] != 'b' && ust2[poczatekY + 1][poczatekX] != 'R' && ust2[poczatekY + 1][poczatekX] != 'r'
                        && ust2[poczatekY + 1][poczatekX] != 'Q' && ust2[poczatekY + 1][poczatekX] != 'r' && ust2[poczatekY + 1][poczatekX] != 'K'
                        && ust2[poczatekY + 1][poczatekX] != 'k')) && poczatekY == 1) {
                    char przechowalnie = ust2[poczatekY + 2][poczatekX];
                    ust2[poczatekY + 2][poczatekX] = 'P';
                    ust2[poczatekY][poczatekX] = ' ';

                    wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                    szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                    ust2[poczatekY][poczatekX] = 'P';
                    ust2[poczatekY + 2][poczatekX] = przechowalnie;
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY + 2));
                        if (szach) {
                            ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                        }

                    }
                }
            }
        } else {
            if (poczatekY == 5 && enpassant && kol != 0 && (kol - 1 == poczatekX - 1 || kol - 1 == poczatekX + 1)) {

                ust2[poczatekY - 1][kol - 1] = 'p';
                ust2[poczatekY][kol - 1] = ' ';
                ust2[poczatekY][poczatekX] = ' ';

                wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                ust2[poczatekY - 1][kol - 1] = ' ';
                ust2[poczatekY][kol - 1] = 'P';
                ust2[poczatekY][poczatekX] = 'p';
                if (wynik || all) {
                    char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (kol - 1)), y2 = (char) ('1' + (poczatekY - 1));

                    if (!szach) {
                        ruchy.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "EP "), SI_MIN_MAX_Alfa_Beta.figury.BPion, ust2));
                    } else {
                        ruchy.add(new Ruch(false, ("p" + "" + x1 + x2 + "x" + y1 + y2 + "EP+"), SI_MIN_MAX_Alfa_Beta.figury.BPion, ust2));
                    }

                }
            }
            for (int b = -1; b < 2; b++) {

                if (poczatekX + b > -1 && poczatekX + b < 8 && poczatekY + 1 < 8) {
                    if (b != 0 && ((ust[poczatekY - 1][poczatekX + b].symbol == 'N')
                            || (ust[poczatekY - 1][poczatekX + b].symbol == 'P')
                            || (ust[poczatekY - 1][poczatekX + b].symbol == 'B')
                            || (ust[poczatekY - 1][poczatekX + b].symbol == 'R')
                            || (ust[poczatekY - 1][poczatekX + b].symbol == 'Q'))) {
                        char przechowalnie;
                        przechowalnie = ust2[poczatekY - 1][poczatekX + b];
                        if (poczatekY != 6) {
                            ust2[poczatekY - 1][poczatekX + b] = 'P';
                            ust2[poczatekY][poczatekX] = ' ';

                            wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                            szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                            ust2[poczatekY][poczatekX] = 'P';
                            ust2[poczatekY - 1][poczatekX + b] = przechowalnie;
                            if (wynik || all) {
                                char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + b)), y2 = (char) ('1' + (poczatekY - 1));
                                if (szach) {
                                    ruchy.add(new Ruch(false, ("p"
                                            + "" + x1 + x2 + "x" + y1 + y2 + "--+"), przechowalnie, ust2));
                                } else {
                                    ruchy.add(new Ruch(false, ("p"
                                            + "" + x1 + x2 + "x" + y1 + y2 + "-- "), przechowalnie, ust2));
                                }

                            }
                        } else {
                            char[] symbole = {'q', 'r', 'b', 'n'};
                            for (int s = 0; s < 4; s++) {
                                ust2[poczatekY - 1][poczatekX + b] = symbole[s];
                                ust2[poczatekY][poczatekX] = ' ';

                                wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                                szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                                ust2[poczatekY][poczatekX] = 'P';
                                ust2[poczatekY - 1][poczatekX + b] = przechowalnie;
                                if (wynik || all) {
                                    char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + b)), y2 = (char) ('1' + (poczatekY - 1));
                                    if (szach) {
                                        ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + "+"), przechowalnie, ust2));
                                    } else {
                                        ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "x" + y1 + y2 + "=" + (symbole[s]) + " "), przechowalnie, ust2));
                                    }

                                }
                            }
                        }
                    }
                }
            }

            if (poczatekY + 1 < 8) {
                if (ust[poczatekY - 1][poczatekX].symbol == ' ' || (ust[poczatekY - 1][poczatekX].symbol != 'p' && ust[poczatekY - 1][poczatekX].symbol != 'P'
                        && ust[poczatekY - 1][poczatekX].symbol != 'N' && ust[poczatekY - 1][poczatekX].symbol != 'n' && ust[poczatekY - 1][poczatekX].symbol != 'B'
                        && ust[poczatekY - 1][poczatekX].symbol != 'b' && ust[poczatekY - 1][poczatekX].symbol != 'R' && ust[poczatekY - 1][poczatekX].symbol != 'r'
                        && ust[poczatekY - 1][poczatekX].symbol != 'Q' && ust[poczatekY - 1][poczatekX].symbol != 'q' && ust[poczatekY - 1][poczatekX].symbol != 'K'
                        && ust[poczatekY - 1][poczatekX].symbol != 'k')) {
                    char przechowalnie;
                    przechowalnie = ust2[poczatekY - 1][poczatekX];
                    if (poczatekY != 6) {
                        ust2[poczatekY - 1][poczatekX] = 'P';
                        ust2[poczatekY][poczatekX] = ' ';

                        wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                        szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                        ust2[poczatekY][poczatekX] = 'P';
                        ust2[poczatekY - 1][poczatekX] = przechowalnie;
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY - 1));
                            if (szach) {
                                ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                            }

                        }
                    } else {
                        char[] symbole = {'q', 'r', 'b', 'n'};
                        for (int s = 0; s < 4; s++) {
                            ust2[poczatekY - 1][poczatekX] = symbole[s];
                            ust2[poczatekY][poczatekX] = ' ';
                            wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                            szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                            ust2[poczatekY][poczatekX] = 'P';
                            ust2[poczatekY - 1][poczatekX] = przechowalnie;
                            if (wynik || all) {
                                char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY - 1));
                                if (szach) {
                                    ruchy.add(new Ruch(false, ("P"
                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + "+"), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                                } else {
                                    ruchy.add(new Ruch(false, ("P"
                                            + "" + x1 + x2 + "-" + y1 + y2 + "=" + (symbole[s]) + " "), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                                }

                            }
                        }
                    }
                }
            }
            if (poczatekY + 2 < 8) {
                if ((ust2[poczatekY - 2][poczatekX] == ' ' || (ust2[poczatekY - 2][poczatekX] != 'p' && ust2[poczatekY - 2][poczatekX] != 'P'
                        && ust2[poczatekY - 2][poczatekX] != 'N' && ust2[poczatekY - 2][poczatekX] != 'n' && ust2[poczatekY - 2][poczatekX] != 'B'
                        && ust2[poczatekY - 2][poczatekX] != 'b' && ust2[poczatekY - 2][poczatekX] != 'R' && ust2[poczatekY - 2][poczatekX] != 'r'
                        && ust2[poczatekY - 2][poczatekX] != 'Q' && ust2[poczatekY - 2][poczatekX] != 'r' && ust2[poczatekY - 2][poczatekX] != 'K'
                        && ust2[poczatekY - 2][poczatekX] != 'k'))
                        && (ust2[poczatekY - 1][poczatekX] == ' ' || (ust2[poczatekY - 1][poczatekX] != 'p' && ust2[poczatekY - 1][poczatekX] != 'P'
                        && ust2[poczatekY - 1][poczatekX] != 'N' && ust2[poczatekY - 1][poczatekX] != 'n' && ust2[poczatekY - 1][poczatekX] != 'B'
                        && ust2[poczatekY - 1][poczatekX] != 'b' && ust2[poczatekY - 1][poczatekX] != 'R' && ust2[poczatekY - 1][poczatekX] != 'r'
                        && ust2[poczatekY - 1][poczatekX] != 'Q' && ust2[poczatekY - 1][poczatekX] != 'r' && ust2[poczatekY - 1][poczatekX] != 'K'
                        && ust2[poczatekY - 1][poczatekX] != 'k')) && poczatekY == 6) {
                    char przechowalnie = ust2[poczatekY + 2][poczatekX];
                    ust2[poczatekY + 2][poczatekX] = 'p';
                    ust2[poczatekY][poczatekX] = ' ';

                    wynik = (all || !RuchZagrozenie_kontrola.szach((ust2), czybialy));
                    szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                    ust2[poczatekY][poczatekX] = 'p';
                    ust2[poczatekY + 2][poczatekX] = przechowalnie;
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY - 2));
                        if (szach) {
                            ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "--+"), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ("P" + "" + x1 + x2 + "-" + y1 + y2 + "-- "), SI_MIN_MAX_Alfa_Beta.figury.pustka, ust2));
                        }

                    }
                }
            }
        }
        return ruchy;
    }
}
