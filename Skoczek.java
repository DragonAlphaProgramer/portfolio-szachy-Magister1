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
public class Skoczek extends FiguraK {

    Skoczek(char c, boolean b, int i, int j) {
       symbol = c;
        czybialy = b;
        poczatekY = i;
        poczatekX = j;
    }

    ArrayList<Ruch> daj_mozliwosci(FiguraK[][] backup, char[][] backup2, boolean all) {
        boolean wynik,szach;
        ArrayList<Ruch> ruchy = new ArrayList<>();
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                if (poczatekY + i > -1 && poczatekY + i < 8 && poczatekX + j > -1 && poczatekX + j < 8) {
                    if (Math.abs(j) - Math.abs(i) != 0 && i != 0 && j != 0) {
                        if ((!czybialy
                                && ((backup[poczatekY + i][poczatekX + j].symbol == ' '
                                || backup[poczatekY + i][poczatekX + j].symbol == 'P'
                                || backup[poczatekY + i][poczatekX + j].symbol == 'N'
                                || backup[poczatekY + i][poczatekX + j].symbol == 'B'
                                || backup[poczatekY + i][poczatekX + j].symbol == 'R'
                                || backup[poczatekY + i][poczatekX + j].symbol == 'Q')
                                || (backup[poczatekY + i][poczatekX + j].symbol != 'p'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'n'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'b'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'r'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'q'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'k'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'K')))
                                || (czybialy
                                && ((backup[poczatekY + i][poczatekX + j].symbol == ' '
                                || backup[poczatekY + i][poczatekX + j].symbol == 'p'
                                || backup[poczatekY + i][poczatekX + j].symbol == 'n'
                                || backup[poczatekY + i][poczatekX + j].symbol == 'b'
                                || backup[poczatekY + i][poczatekX + j].symbol == 'r'
                                || backup[poczatekY + i][poczatekX + j].symbol == 'q')
                                || (backup[poczatekY + i][poczatekX + j].symbol != 'P'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'N'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'B'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'R'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'Q'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'k'
                                && backup[poczatekY + i][poczatekX + j].symbol != 'K')))) {
                            char przechowalnia = backup2[poczatekY + i][poczatekX + j];
                            backup2[poczatekY + i][poczatekX + j]= czybialy ? 'N' : 'n';
                            backup2[poczatekY][poczatekX] = ' ';

                            
                                wynik = (all || RuchZagrozenie_kontrola.szach((backup2), czybialy) == false);
                                szach = RuchZagrozenie_kontrola.szach((backup2), !czybialy);

                           

                            backup2[poczatekY][poczatekX] = czybialy ? 'N' : 'n';
                            backup[poczatekY + i][poczatekX + j].symbol = przechowalnia;
                            if (wynik || all) {
                                char x1 = (char) ('A' + (poczatekX)), x2 = (char) ('1' + (poczatekY)), y1 = (char) ('A' + (poczatekX + j)), y2 = (char) ('1' + (poczatekY + i));
                                if (przechowalnia == ' ') {
                                    if (szach) {
                                        ruchy.add(new Ruch(false, ((czybialy ? "N" : "n") + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "--+"), SI_MIN_MAX_Alfa_Beta.figury.pustka, backup2));
                                    } else {
                                        ruchy.add(new Ruch(false, ((czybialy ? "N" : "n") + "" + x1 + "" + x2 + "-" + y1 + "" + y2 + "-- "), SI_MIN_MAX_Alfa_Beta.figury.pustka, backup2));
                                    }

                                } else {
                                    if (szach) {
                                        ruchy.add(new Ruch(false, ((czybialy ? "N" : "n") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "--+"), przechowalnia, backup2));
                                    } else {
                                        ruchy.add(new Ruch(false, ((czybialy ? "N" : "n") + "" + x1 + "" + x2 + "x" + y1 + "" + y2 + "-- "), przechowalnia, backup2));
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        return ruchy;
    }
}
