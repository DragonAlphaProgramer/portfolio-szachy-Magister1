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
public class Hetman extends FiguraK {

    Hetman(char c, boolean b, int i, int j) {
        symbol = c;
        czybialy = b;
        poczatekY = i;
        poczatekX = j;
    }

    ArrayList<Ruch> daj_mozliwosci(FiguraK[][] ust, char[][] ust2, boolean all) {

        ArrayList<Ruch> ruchy = new ArrayList<>();
        boolean wynik, szach;
        boolean d1 = true;
        boolean d2 = true;
        boolean d3 = true;
        boolean d4 = true;
        boolean w1 = true;
        boolean w2 = true;
        boolean w3 = true;
        boolean w4 = true;
        int przod = 1;
        int tyl = -1;
        int param_ruch = 1;
        while (d1 || d2 || d3 || d4
                || w1 || w2 || w3 || w4) {
            if (poczatekY + param_ruch < 8 && poczatekX + param_ruch < 8 && d1) {

                if (d1 && ust[poczatekY + param_ruch][poczatekX + param_ruch] == null) {
                    ust2[poczatekY + param_ruch][poczatekX + param_ruch] = czybialy ? 'Q' : 'q';
                    ust2[poczatekY][poczatekX] = ' ';

                    wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                    szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                    ust2[poczatekY + param_ruch][poczatekX + param_ruch] = ' ';
                    ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + param_ruch)), y2 = (char) ('1' + (poczatekY + param_ruch));
                        if (szach) {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), null, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), null, ust2));
                        }

                    }
                } else {
                    if (czybialy && ((ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'p' || ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'n'
                            || ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'b' || ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'r' || ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'q'))
                            || (!czybialy && ((ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'P' || ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'N'
                            || ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'B' || ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'R' || ust[poczatekY + param_ruch][poczatekX + param_ruch].symbol == 'Q')))) {
                        char przechowalnia = ust2[poczatekY + param_ruch][poczatekX + param_ruch];
                        ust2[poczatekY + param_ruch][poczatekX + param_ruch] = czybialy ? 'Q' : 'q';
                        ust2[poczatekY][poczatekX] = ' ';

                        wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                        szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                        ust2[poczatekY + param_ruch][poczatekX + param_ruch] = przechowalnia;
                        ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + param_ruch)), y2 = (char) ('1' + (poczatekY + param_ruch));
                            if (szach) {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, ust2));
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

            if (poczatekY - param_ruch > -1 && poczatekX - param_ruch > -1 && d2) {

                if (d2 && ust[poczatekY - param_ruch][poczatekX - param_ruch] == null) {
                    ust2[poczatekY - param_ruch][poczatekX - param_ruch] = czybialy ? 'Q' : 'q';
                    ust2[poczatekY][poczatekX] = ' ';

                    wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                    szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                    ust2[poczatekY - param_ruch][poczatekX - param_ruch] = ' ';
                    ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + param_ruch)), y2 = (char) ('1' + (poczatekY + param_ruch));
                        if (szach) {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), null, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), null, ust2));
                        }

                    }
                } else {
                    if (czybialy && ((ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'p' || ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'n'
                            || ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'b' || ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'r' || ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'q'))
                            || (!czybialy && ((ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'P' || ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'N'
                            || ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'B' || ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'R' || ust[poczatekY - param_ruch][poczatekX - param_ruch].symbol == 'Q')))) {
                        char przechowalnia = ust2[poczatekY - param_ruch][poczatekX - param_ruch];
                        ust2[poczatekY - param_ruch][poczatekX - param_ruch] = czybialy ? 'Q' : 'q';
                        ust2[poczatekY][poczatekX] = ' ';

                        wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                        szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                        ust2[poczatekY - param_ruch][poczatekX - param_ruch] = przechowalnia;
                        ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX - param_ruch)), y2 = (char) ('1' + (poczatekY - param_ruch));
                            if (szach) {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, ust2));
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

            if (poczatekY + param_ruch < 8 && poczatekX - param_ruch > -1 && d3) {

                if (d3 && ust[poczatekY + param_ruch][poczatekX - param_ruch] == null) {
                    ust2[poczatekY + param_ruch][poczatekX - param_ruch] = czybialy ? 'Q' : 'q';
                    ust2[poczatekY][poczatekX] = ' ';

                    wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                    szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                    ust2[poczatekY + param_ruch][poczatekX - param_ruch] = ' ';
                    ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + param_ruch)), y2 = (char) ('1' + (poczatekY - param_ruch));
                        if (szach) {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), null, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), null, ust2));
                        }

                    }
                } else {
                    if (czybialy && ((ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'p' || ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'n'
                            || ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'b' || ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'r' || ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'q'))
                            || (!czybialy && ((ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'P' || ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'N'
                            || ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'B' || ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'R' || ust[poczatekY + param_ruch][poczatekX - param_ruch].symbol == 'Q')))) {
                        char przechowalnia = ust2[poczatekY + param_ruch][poczatekX - param_ruch];
                        ust2[poczatekY + param_ruch][poczatekX - param_ruch] = czybialy ? 'Q' : 'q';
                        ust2[poczatekY][poczatekX] = ' ';

                        wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                        szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                        ust2[poczatekY + param_ruch][poczatekX - param_ruch] = przechowalnia;
                        ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX - param_ruch)), y2 = (char) ('1' + (poczatekY - param_ruch));
                            if (szach) {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, ust2));
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
            if (poczatekY - param_ruch > -1 && poczatekX + param_ruch < 8 && d4) {

                if (d4 && ust[poczatekY - param_ruch][poczatekX + param_ruch] == null) {
                    ust2[poczatekY - param_ruch][poczatekX + param_ruch] = czybialy ? 'Q' : 'q';
                    ust2[poczatekY][poczatekX] = ' ';

                    wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                    szach = RuchZagrozenie_kontrola.szach((ust2), !czybialy);

                    ust2[poczatekY - param_ruch][poczatekX + param_ruch] = ' ';
                    ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + param_ruch)), y2 = (char) ('1' + (poczatekY + param_ruch));
                        if (szach) {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), null, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), null, ust2));
                        }

                    }
                } else {
                    if (czybialy && ((ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'p' || ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'n'
                            || ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'b' || ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'r' || ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'q'))
                            || (!czybialy && ((ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'P' || ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'N'
                            || ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'B' || ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'R' || ust[poczatekY - param_ruch][poczatekX + param_ruch].symbol == 'Q')))) {
                        char przechowalnia = ust2[poczatekY - param_ruch][poczatekX + param_ruch];
                        ust2[poczatekY - param_ruch][poczatekX + param_ruch] = czybialy ? 'Q' : 'q';
                        ust2[poczatekY][poczatekX] = ' ';

                        wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                        szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                        ust2[poczatekY - param_ruch][poczatekX + param_ruch] = przechowalnia;
                        ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + param_ruch)), y2 = (char) ('1' + (poczatekY + param_ruch));
                            if (szach) {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, ust2));
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
            if (poczatekY + param_ruch < 8 && w1) {

                if (w1 && ust[poczatekY + param_ruch][poczatekX] == null) {
                    ust2[poczatekY + param_ruch][poczatekX] = czybialy ? 'Q' : 'q';
                    ust2[poczatekY][poczatekX] = ' ';
                    wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                    szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                    ust2[poczatekY + param_ruch][poczatekX] = ' ';
                    ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY + param_ruch));
                        if (szach) {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), null, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), null, ust2));
                        }

                    }
                } else {
                    if (czybialy && ((ust[poczatekY + param_ruch][poczatekX].symbol == 'p' || ust[poczatekY + param_ruch][poczatekX].symbol == 'n'
                            || ust[poczatekY + param_ruch][poczatekX].symbol == 'b' || ust[poczatekY + param_ruch][poczatekX].symbol == 'r' || ust[poczatekY + param_ruch][poczatekX].symbol == 'q'))
                            || (!czybialy && ((ust[poczatekY + param_ruch][poczatekX].symbol == 'P' || ust[poczatekY + param_ruch][poczatekX].symbol == 'N'
                            || ust[poczatekY + param_ruch][poczatekX].symbol == 'B' || ust[poczatekY + param_ruch][poczatekX].symbol == 'R' || ust[poczatekY + param_ruch][poczatekX].symbol == 'Q')))) {
                        char przechowalnia = ust2[poczatekY + param_ruch][poczatekX];
                        ust2[poczatekY + param_ruch][poczatekX] = czybialy ? 'Q' : 'q';
                        ust2[poczatekY][poczatekX] = ' ';

                        wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                        szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                        ust2[poczatekY + param_ruch][poczatekX] = przechowalnia;
                        ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY + param_ruch));
                            if (szach) {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, ust2));
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

            if (poczatekY - param_ruch < 8 && w2) {

                if (w2 && ust[poczatekY - param_ruch][poczatekX] == null) {
                    ust2[poczatekY - param_ruch][poczatekX] = czybialy ? 'Q' : 'q';
                    ust2[poczatekY][poczatekX] = ' ';
                    wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                    szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                    ust2[poczatekY - param_ruch][poczatekX] = ' ';
                    ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY - param_ruch));
                        if (szach) {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), null, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), null, ust2));
                        }

                    }
                } else {
                    if (czybialy && ((ust[poczatekY - param_ruch][poczatekX].symbol == 'p' || ust[poczatekY - param_ruch][poczatekX].symbol == 'n'
                            || ust[poczatekY - param_ruch][poczatekX].symbol == 'b' || ust[poczatekY - param_ruch][poczatekX].symbol == 'r' || ust[poczatekY - param_ruch][poczatekX].symbol == 'q'))
                            || (!czybialy && ((ust[poczatekY - param_ruch][poczatekX].symbol == 'P' || ust[poczatekY - param_ruch][poczatekX].symbol == 'N'
                            || ust[poczatekY - param_ruch][poczatekX].symbol == 'B' || ust[poczatekY - param_ruch][poczatekX].symbol == 'R' || ust[poczatekY - param_ruch][poczatekX].symbol == 'Q')))) {
                        char przechowalnia = ust2[poczatekY - param_ruch][poczatekX];
                        ust2[poczatekY - param_ruch][poczatekX] = czybialy ? 'Q' : 'q';
                        ust2[poczatekY][poczatekX] = ' ';

                        wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                        szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                        ust2[poczatekY - param_ruch][poczatekX] = przechowalnia;
                        ust2[poczatekY][poczatekX] = czybialy ? 'Q' : 'q';
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX)), y2 = (char) ('1' + (poczatekY - param_ruch));
                            if (szach) {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, ust2));
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
            if (poczatekX + param_ruch < 8 && w2) {

                if (w3 && ust[poczatekY][poczatekX + param_ruch] == null) {
                    ust2[poczatekY][poczatekX + param_ruch] = czybialy ? 'Q' : 'q';
                    ust2[poczatekY][poczatekX + param_ruch] = ' ';
                    wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                    szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                    ust2[poczatekY][poczatekX + param_ruch] = ' ';
                    ust2[poczatekY][poczatekX + param_ruch] = czybialy ? 'Q' : 'q';
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX + param_ruch)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + param_ruch)), y2 = (char) ('1' + (poczatekY));
                        if (szach) {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), null, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), null, ust2));
                        }

                    }
                } else {
                    if (czybialy && ((ust[poczatekY][poczatekX + param_ruch].symbol == 'p' || ust[poczatekY][poczatekX + param_ruch].symbol == 'n'
                            || ust[poczatekY][poczatekX + param_ruch].symbol == 'b' || ust[poczatekY][poczatekX + param_ruch].symbol == 'r' || ust[poczatekY][poczatekX + param_ruch].symbol == 'q'))
                            || (!czybialy && ((ust[poczatekY][poczatekX + param_ruch].symbol == 'P' || ust[poczatekY][poczatekX + param_ruch].symbol == 'N'
                            || ust[poczatekY][poczatekX + param_ruch].symbol == 'B' || ust[poczatekY][poczatekX + param_ruch].symbol == 'R' || ust[poczatekY][poczatekX + param_ruch].symbol == 'Q')))) {
                        char przechowalnia = ust2[poczatekY][poczatekX + param_ruch];
                        ust2[poczatekY][poczatekX + param_ruch] = czybialy ? 'Q' : 'q';
                        ust2[poczatekY][poczatekX + param_ruch] = ' ';

                        wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                        szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                        ust2[poczatekY][poczatekX + param_ruch] = przechowalnia;
                        ust2[poczatekY][poczatekX + param_ruch] = czybialy ? 'Q' : 'q';
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX + param_ruch)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + param_ruch)), y2 = (char) ('1' + (poczatekY));
                            if (szach) {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, ust2));
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
            if (poczatekX - param_ruch < 8 && w2) {

                if (w4 && ust[poczatekY][poczatekX - param_ruch] == null) {
                    ust2[poczatekY][poczatekX - param_ruch] = czybialy ? 'Q' : 'q';
                    ust2[poczatekY][poczatekX - param_ruch] = ' ';
                    wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                    szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                    ust2[poczatekY][poczatekX - param_ruch] = ' ';
                    ust2[poczatekY][poczatekX - param_ruch] = czybialy ? 'Q' : 'q';
                    if (wynik || all) {
                        char x1 = (char) ('A' + (poczatekX - param_ruch)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX - param_ruch)), y2 = (char) ('1' + (poczatekY));
                        if (szach) {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), null, ust2));
                        } else {
                            ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q")
                                    + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), null, ust2));
                        }

                    }
                } else {
                    if (czybialy && ((ust[poczatekY][poczatekX - param_ruch].symbol == 'p' || ust[poczatekY][poczatekX - param_ruch].symbol == 'n'
                            || ust[poczatekY][poczatekX - param_ruch].symbol == 'b' || ust[poczatekY][poczatekX - param_ruch].symbol == 'r' || ust[poczatekY][poczatekX - param_ruch].symbol == 'q'))
                            || (!czybialy && ((ust[poczatekY][poczatekX - param_ruch].symbol == 'P' || ust[poczatekY][poczatekX - param_ruch].symbol == 'N'
                            || ust[poczatekY][poczatekX - param_ruch].symbol == 'B' || ust[poczatekY][poczatekX - param_ruch].symbol == 'R' || ust[poczatekY][poczatekX - param_ruch].symbol == 'Q')))) {
                        char przechowalnia = ust2[poczatekY][poczatekX - param_ruch];
                        ust2[poczatekY][poczatekX - param_ruch] = czybialy ? 'Q' : 'q';
                        ust2[poczatekY][poczatekX - param_ruch] = ' ';

                        wynik = (all || RuchZagrozenie_kontrola.szach((ust2), czybialy) == false);
                        szach = RuchZagrozenie_kontrola.szach((ust2), czybialy);

                        ust2[poczatekY][poczatekX - param_ruch] = przechowalnia;
                        ust2[poczatekY][poczatekX - param_ruch] = czybialy ? 'Q' : 'q';
                        if (wynik || all) {
                            char x1 = (char) ('A' + (poczatekX - param_ruch)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX - param_ruch)), y2 = (char) ('1' + (poczatekY));
                            if (szach) {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, ust2));
                            } else {
                                ruchy.add(new Ruch(false, ((czybialy ? "Q" : "q") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, ust2));
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

            tyl = tyl - 1;
            param_ruch = param_ruch + 1;
            przod = przod + 1;
        }
        return ruchy;
    }

}
