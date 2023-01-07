/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

/**
 *
 * @author Patryk sprawdza czy jest możliwy ratunek dla króla
 */
public class SzachMatPatKontrola {

    public static boolean znajdzodsiecz(char[][] ustawienie, boolean czybiale, int[] klopot,
            int kol, boolean przelotcan) {
        int pomoc;
        char przechowalnia = ustawienie[klopot[1]][klopot[0]];
        int[] odsiecz = new int[2];
        int X, Y, alfa, beta;
        char[][] poza_odsieczy;
        char bijacy;
        boolean dozwolony;
        poza_odsieczy = ustawienie;
        char[][] pomocnicze = new char[16][16];
        X = klopot[1] + 4;
        Y = klopot[0] + 4;
        for (int i = 0; i < 8; i++) {
            System.arraycopy(poza_odsieczy[i], 0, pomocnicze[i + 4], 4, 8);
        }

        if ((!czybiale && ((pomocnicze[X + 2][Y - 1] == 'n')
                || (pomocnicze[X + 2][Y + 1] == 'n')
                || (pomocnicze[X - 2][Y - 1] == 'n')
                || (pomocnicze[X - 2][Y + 1] == 'n')
                || (pomocnicze[X + 1][Y + 2] == 'n')
                || (pomocnicze[X + 1][Y - 2] == 'n')
                || (pomocnicze[X - 1][Y + 2] == 'n')
                || (pomocnicze[X - 1][Y - 2] == 'n')))
                || (czybiale && ((pomocnicze[X + 2][Y - 1] == 'N')
                || (pomocnicze[X + 2][Y + 1] == 'N')
                || (pomocnicze[X - 2][Y - 1] == 'N')
                || (pomocnicze[X - 2][Y + 1] == 'N')
                || (pomocnicze[X + 1][Y + 2] == 'N')
                || (pomocnicze[X + 1][Y - 2] == 'N')
                || (pomocnicze[X - 1][Y + 2] == 'N')
                || (pomocnicze[X - 1][Y - 2] == 'N')))) {
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                        if ((czybiale && pomocnicze[X + i][Y + j] == 'N') || (!czybiale && pomocnicze[X + i][Y + j] == 'n')) {
                            odsiecz[0] = X + i - 4;
                            odsiecz[1] = Y + j - 4;
                            if (czybiale) {
                                bijacy = 'N';
                            } else {
                                bijacy = 'n';
                            }
                            przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                            poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                            poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';

                            dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
                            poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                            poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                            if (dozwolony) {
                                return true;
                            }
                        }
                    }
                }
            }

        }

        int[] przelocik = new int[2];
        if ((!czybiale && ((pomocnicze[X + 1][Y + 1] == 'p') || (pomocnicze[X + 1][Y - 1] == 'p')))
                || (czybiale && ((pomocnicze[X - 1][Y + 1] == 'P') || (pomocnicze[X - 1][Y - 1] == 'P')))) {
            if (czybiale) {
                bijacy = 'P';
                if (pomocnicze[X - 1][Y + 1] == 'P') {
                    odsiecz[0] = X - 1 - 4;
                    odsiecz[1] = Y + 1 - 4;
                } else {
                    odsiecz[0] = X - 1 - 4;
                    odsiecz[1] = Y - 1 - 4;
                }
            } else {
                bijacy = 'p';
                if (pomocnicze[X + 1][Y + 1] == 'p') {
                    odsiecz[0] = X + 1 - 4;
                    odsiecz[1] = Y + 1 - 4;
                } else {
                    odsiecz[0] = X + 1 - 4;
                    odsiecz[1] = Y - 1 - 4;
                }
            }
            klopot[0] = klopot[0] + 1;
            klopot[1] = klopot[1] + 1;
            przechowalnia = poza_odsieczy[klopot[1] - 1][klopot[0] - 1];
            poza_odsieczy[klopot[1] - 1][klopot[0] - 1] = bijacy;
            poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
            dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
            poza_odsieczy[klopot[1] - 1][klopot[0] - 1] = przechowalnia;
            poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
            klopot[0] = klopot[0] - 1;
            klopot[1] = klopot[1] - 1;
            if (dozwolony) {
                return true;
            }
        }

        for (int p = -1; p < 2; p++) {
            if (p != 0 && (klopot[0] + p > -1 && klopot[0] + p < 8)) {
                if ((!czybiale && przelotcan
                        && (((poza_odsieczy[2][klopot[0]] == ' ' && (poza_odsieczy[3][klopot[0] + p] == 'p')))))
                        || (czybiale && przelotcan
                        && (((poza_odsieczy[5][klopot[0]] == ' ' && (poza_odsieczy[4][klopot[0] + p] == 'P')))))) {
                    System.out.println("przelot");
                    if (czybiale) {
                        bijacy = 'P';
                        przelocik[0] = 5;
                        przelocik[1] = klopot[0];
                        if (poza_odsieczy[4][klopot[0] + p] == 'P') {
                            odsiecz[0] = klopot[0] + p;
                            odsiecz[1] = 4;
                        }
                    } else {
                        bijacy = 'p';
                        przelocik[0] = 2;
                        przelocik[1] = klopot[0];
                        if (poza_odsieczy[3][klopot[0] + p] == 'p') {
                            odsiecz[0] = klopot[0] + p;
                            odsiecz[1] = 3;
                        }
                    }
                    //System.out.println("wchodzi");
                    poza_odsieczy[przelocik[1]][przelocik[0]] = bijacy;
                    pomoc = odsiecz[0];
                    odsiecz[0] = odsiecz[1];
                    odsiecz[1] = pomoc;
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    poza_odsieczy[klopot[1]][klopot[0]] = ' ';
                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
                    System.out.println(dozwolony);
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    poza_odsieczy[przelocik[1]][przelocik[0]] = ' ';
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                    if (dozwolony) {
                        return true;
                    }
                }
            }
        }
        alfa = klopot[1] + 4;
        beta = klopot[0] + 4;
        //System.out.println("sprawdzane pole bicia: " + alfa + "," + beta+" "+pomocnicze[alfa][beta]);
        int licz = 0;
        for (int i = alfa + 1; i <= 15; i++) {
            licz = licz + 1;
            if (beta + licz >= 15) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] != ' ')) {
                    if ((!czybiale && (pomocnicze[i][beta + licz] == 'q' || pomocnicze[i][beta + licz] == 'b'))
                            || (czybiale && (pomocnicze[i][beta + licz] == 'Q' || pomocnicze[i][beta + licz] == 'B'))) {
                        if (czybiale) {
                            if (pomocnicze[i][beta + licz] == 'Q') {
                                bijacy = 'Q';
                            } else {
                                bijacy = 'B';
                            }
                        } else {
                            if (pomocnicze[i][beta + licz] == 'q') {
                                bijacy = 'q';
                            } else {
                                bijacy = 'b';
                            }
                        }
                        odsiecz[1] = beta + licz - 4;
                        odsiecz[0] = i - 4;

                        poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                        // System.out.println("1dfunkcjabij:" + przechowalnia);
                        //System.out.println("kordy odsiecz x:" + (odsiecz[1]) + " y:" + (odsiecz[0]));
                        //System.out.println("bij cel:" + poza_odsieczy[klopot[0]][klopot[1]]);
                        //System.out.println("kordy cel x:" + (klopot[1]) + " y:" + (klopot[0]));

                        dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
                        //System.out.println(dozwolony);
                        poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                        if (dozwolony) {
                            return true;
                        }
                    }
                    break;
                }
            }
        }

        licz = 0;
        for (int i = alfa + 1; i <= 15; i++) {
            licz = licz - 1;
            if (beta + licz <= 0) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] != ' ')) {
                    if ((!czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B'))) {
                    } else {
                        if (czybiale) {
                            if (pomocnicze[i][beta + licz] == 'Q') {
                                bijacy = 'Q';
                            } else {
                                bijacy = 'B';
                            }
                        } else {
                            if (pomocnicze[i][beta + licz] == 'q') {
                                bijacy = 'q';
                            } else {
                                bijacy = 'b';
                            }
                        }
                        odsiecz[1] = beta + licz - 4;
                        odsiecz[0] = i - 4;

                        poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                        // System.out.println("2dfunkcjabij:" + przechowalnia);
                        //System.out.println("kordy odsiecz x:" + (odsiecz[1]) + " y:" + (odsiecz[0]));
                        //System.out.println("bij cel:" + poza_odsieczy[klopot[0]][klopot[1]]);
                        //System.out.println("kordy cel x:" + (klopot[1]) + " y:" + (klopot[0]));

                        dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
                        poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                        if (dozwolony) {
                            return true;
                        }
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (int i = alfa - 1; i >= 2; i--) {
            licz = licz + 1;
            if (beta + licz >= 15) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] != ' ')) {
                    if ((!czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B'))) {
                    } else {
                        if (czybiale) {
                            if (pomocnicze[i][beta + licz] == 'Q') {
                                bijacy = 'Q';
                            } else {
                                bijacy = 'B';
                            }
                        } else {
                            if (pomocnicze[i][beta + licz] == 'q') {
                                bijacy = 'q';
                            } else {
                                bijacy = 'b';
                            }
                        }
                        odsiecz[1] = beta + licz - 4;
                        odsiecz[0] = i - 4;

                        poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                        //System.out.println("3dfunkcjabij:" + przechowalnia);
                        //System.out.println("kordy odsiecz x:" + (odsiecz[1]) + " y:" + (odsiecz[0]));
                        //System.out.println("bij cel:" + poza_odsieczy[klopot[0]][klopot[1]]);
                        //System.out.println("kordy cel x:" + (klopot[1]) + " y:" + (klopot[0]));

                        dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);

                        poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                        if (dozwolony) {
                            return true;
                        }
                    }
                    break;
                }
            }
        }

        licz = 0;

        for (int i = alfa - 1; i >= 2; i--) {
            licz = licz - 1;
            if (beta + licz <= 0) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] != ' ')) {
                    if ((!czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B'))) {
                    } else {
                        if (czybiale) {
                            if (pomocnicze[i][beta + licz] == 'Q') {
                                bijacy = 'Q';
                            } else {
                                bijacy = 'B';
                            }
                        } else {
                            if (pomocnicze[i][beta + licz] == 'q') {
                                bijacy = 'q';
                            } else {
                                bijacy = 'b';
                            }
                        }
                        odsiecz[1] = beta + licz - 4;
                        odsiecz[0] = i - 4;

                        poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                        // System.out.println("4dfunkcjabij:" + przechowalnia);
                        //System.out.println("kordy odsiecz x:" + (odsiecz[1]) + " y:" + (odsiecz[0]));
                        //System.out.println("bij cel:" + poza_odsieczy[klopot[0]][klopot[1]]);
                        //System.out.println("kordy cel x:" + (klopot[1]) + " y:" + (klopot[0]));

                        dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
                        poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                        if (dozwolony) {

                            return true;
                        }
                    }
                    break;
                }
            }
        }
        for (int i = alfa + 1; i <= 15; i++) {
            if (pomocnicze[i][beta] != ' ') {
                if ((!czybiale && (pomocnicze[i][beta] != 'q' && pomocnicze[i][beta] != 'r'))
                        || (czybiale && (pomocnicze[i][beta] != 'Q' && pomocnicze[i][beta] != 'R'))) {

                } else {
                    if (czybiale) {
                        if (pomocnicze[i][beta] == 'Q') {
                            bijacy = 'Q';
                        } else {
                            bijacy = 'R';
                        }
                    } else {
                        if (pomocnicze[i][beta] == 'q') {
                            bijacy = 'q';
                        } else {
                            bijacy = 'r';
                        }
                    }
                    odsiecz[1] = Y - 4;
                    odsiecz[0] = i - 4;
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    //System.out.println("1dfunkcjabij:" + przechowalnia);
                    //System.out.println("kordy odsiecz x:" + (odsiecz[1]) + " y:" + (odsiecz[0]));
                    //System.out.println("bij cel:" + poza_odsieczy[klopot[0]][klopot[1]]);
                    //System.out.println("kordy cel x:" + (klopot[1]) + " y:" + (klopot[0]));

                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                    if (dozwolony) {

                        return true;
                    }
                }
                break;
            }
        }

        for (int i = alfa - 1; i >= 2; i--) {
            if (pomocnicze[i][beta] != ' ') {
                if ((!czybiale && (pomocnicze[i][beta] != 'q' && pomocnicze[i][beta] != 'r'))
                        || (czybiale && (pomocnicze[i][beta] != 'Q' && pomocnicze[i][beta] != 'R'))) {

                } else {
                    if (czybiale) {
                        if (pomocnicze[i][beta] == 'Q') {
                            bijacy = 'Q';
                        } else {
                            bijacy = 'R';
                        }
                    } else {
                        if (pomocnicze[i][beta] == 'q') {
                            bijacy = 'q';
                        } else {
                            bijacy = 'r';
                        }
                    }
                    odsiecz[1] = Y - 4;
                    odsiecz[0] = i - 4;
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    //System.out.println("1dfunkcjabij:" + przechowalnia);
                    //System.out.println("kordy odsiecz x:" + (odsiecz[1]) + " y:" + (odsiecz[0]));
                    //System.out.println("bij cel:" + poza_odsieczy[klopot[0]][klopot[1]]);
                    //System.out.println("kordy cel x:" + (klopot[1]) + " y:" + (klopot[0]));

                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                    if (dozwolony) {

                        return true;
                    }
                }
                break;
            }
        }

        for (int i = Y + 1; i <= 15; i++) {
            if (pomocnicze[alfa][i] == ' ') {
            } else {
                if ((!czybiale && (pomocnicze[alfa][i] != 'q' && pomocnicze[alfa][i] != 'r'))
                        || (czybiale && (pomocnicze[alfa][i] != 'Q' && pomocnicze[alfa][i] != 'R'))) {

                } else {
                    if (czybiale) {
                        if (pomocnicze[alfa][i] == 'Q') {
                            bijacy = 'Q';
                        } else {
                            bijacy = 'R';
                        }
                    } else {
                        if (pomocnicze[alfa][i] == 'q') {
                            bijacy = 'q';
                        } else {
                            bijacy = 'r';
                        }
                    }
                    odsiecz[1] = i - 4;
                    odsiecz[0] = alfa - 4;
                    //System.out.println(klopot[0]+"|"+klopot[1]);
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    //System.out.println("1dfunkcjabij:" + przechowalnia);
                    //System.out.println("kordy odsiecz x:" + (odsiecz[1]) + " y:" + (odsiecz[0]));
                    //System.out.println("bij cel:" + poza_odsieczy[klopot[0]][klopot[1]]);
                    //System.out.println("kordy cel x:" + (klopot[1]) + " y:" + (klopot[0]));

                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                    if (dozwolony) {

                        return true;
                    }
                }
                break;
            }
        }

        for (int i = beta - 1; i >= 2; i--) {
            if (pomocnicze[alfa][i] == ' ') {
            } else {
                if ((!czybiale && (pomocnicze[alfa][i] != 'q' && pomocnicze[alfa][i] != 'r'))
                        || (czybiale && (pomocnicze[alfa][i] != 'Q' && pomocnicze[alfa][i] != 'R'))) {

                } else {
                    if (czybiale) {
                        if (pomocnicze[alfa][i] == 'Q') {
                            bijacy = 'Q';
                        } else {
                            bijacy = 'R';
                        }
                    } else {
                        if (pomocnicze[alfa][i] == 'q') {
                            bijacy = 'q';
                        } else {
                            bijacy = 'r';
                        }
                    }
                    odsiecz[1] = i - 4;
                    odsiecz[0] = alfa - 4;
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    //System.out.println("1dfunkcjabij:" + przechowalnia);
                    //System.out.println("kordy odsiecz x:" + (odsiecz[1]) + " y:" + (odsiecz[0]));
                    //System.out.println("bij cel:" + poza_odsieczy[klopot[0]][klopot[1]]);
                    //System.out.println("kordy cel x:" + (klopot[1]) + " y:" + (klopot[0]));
                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale);
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                    if (dozwolony) {

                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    public static boolean zastaw(char[][] ustawienie, boolean ruchB, int[] atak, int[] poza_krolewska,
            boolean przelotcan) {
        //System.out.println("X:" + atak[0] + "Y:" + atak[1]);
        //System.out.println(" krolX:" + poza_krolewska[0] + "Y:" + poza_krolewska[1]);
        //System.out.println(ustawienie[atak[0]][atak[1]]);
        int X, Y, krolX, krolY;
        char[][] pozycja;
        pozycja = ustawienie;
        Y = atak[0] + 4;
        X = atak[1] + 4;

        int[] zastawa;
        char[][] pomocna = new char[16][16];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(pozycja[i], 0, pomocna[i + 4], 4, 8);
        }
        krolX = poza_krolewska[0] + 4;
        krolY = poza_krolewska[1] + 4;
        if ((Math.abs(krolX - X) == 1 && Math.abs(krolY - Y) == 2)
                || (Math.abs(krolX - X) == 2 && Math.abs(krolY - Y) == 1)) {
            return false;
        } else {
            //System.out.println("roznica" + (krolX - X) + " " + (krolY - Y));
            if (((Math.abs(krolX - X) != 1) && (Math.abs(krolY - Y) != 1))
                    || ((Math.abs(krolX - X) == 0 || Math.abs(krolX - X) > 1) && (Math.abs(krolY - Y) != 1))
                    || ((Math.abs(krolY - Y) == 0 || Math.abs(krolY - Y) > 1) && (Math.abs(krolX - X) != 1))) {
                //System.out.println("|||");
                if (krolX - X == 0) {
                    if (krolY - Y > 0) {
                        for (int i = 1; i < (krolY - Y); i++) {
                            atak[0] = krolX - 4;
                            atak[1] = krolY - i - 4;
                            zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                            //System.out.println("sprawdzone: " + klopot[0] + "," + klopot[1]);      

                            if (zastawa[0] != -1 && zastawa[1] != -1) {
                                return true;
                            }
                        }
                    } else {
                        for (int i = 1; i < (Math.abs(krolY - Y)); i++) {

                            atak[0] = krolX - 4;
                            atak[1] = krolY + i - 4;
                            zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                            //System.out.println("sprawdzone: " + klopot[0] + "," + klopot[1]);                               
                            if (zastawa[0] != -1 && zastawa[1] != -1) {
                                return true;
                            }

                        }
                    }
                } else {
                    //x!=0
                    if (krolX - X > 0) {
                        //x>0
                        if (krolY - Y == 0) {
                            //y==0
                            for (int i = 1; i < (krolX - X); i++) {
                                atak[0] = krolX - i - 4;
                                atak[1] = krolY - 4;
                                zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                //System.out.println("sprawdzone: " + klopot[0] + "," + klopot[1]);      

                                if (zastawa[0] != -1 && zastawa[1] != -1) {
                                    return true;
                                }
                            }
                        } else {
                            //y!=0
                            if (krolY - Y > 0) {
                                //System.out.println("y>0");
                                for (int i = 1; i < (krolX - X); i++) {
                                    atak[0] = krolX - i - 4;
                                    atak[1] = krolY - i - 4;
                                    zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                    //System.out.println("sprawdzone: " + klopot[0] + "," + klopot[1]);      

                                    if (zastawa[0] != -1 && zastawa[1] != -1) {
                                        return true;
                                    }
                                }
                            } else {
                                // System.out.println("y<0");
                                for (int i = 1; i < (krolX - X); i++) {
                                    atak[0] = krolX - i - 4;
                                    atak[1] = krolY + i - 4;
                                    zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                    //System.out.println("sprawdzone: " + klopot[0] + "," + klopot[1]);      

                                    if (zastawa[0] != -1 && zastawa[1] != -1) {
                                        return true;
                                    }
                                }
                            }
                        }
                    } else {
                        //"x<0
                        if (krolY - Y == 0) {
                            //y==0
                            //System.out.println("-");
                            for (int i = 1; i < (Math.abs(krolX - X)); i++) {
                                atak[0] = krolX + i - 4;
                                atak[1] = krolY - 4;
                                zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                //System.out.println("sprawdzone: " + klopot[0] + "," + klopot[1]);      

                                if (zastawa[0] != -1 && zastawa[1] != -1) {
                                    //System.out.println(zastawa[0] + "|" + zastawa[1]);
                                    return true;
                                }
                            }
                        } else { //y!=0
                            if (krolY - Y > 0) {
                                //System.out.println("y>0");
                                for (int i = 1; i < (Math.abs(krolX - X)); i++) {
                                    atak[0] = krolX + i - 4;
                                    atak[1] = krolY - i - 4;
                                    zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                    //System.out.println("sprawdzone: " + klopot[0] + "," + klopot[1]);      

                                    if (zastawa[0] != -1 && zastawa[1] != -1) {
                                        return true;
                                    }
                                }
                            } else {
                                //System.out.println("y<0");
                                for (int i = 1; i < (Math.abs(krolX - X)); i++) {
                                    atak[0] = krolX + i - 4;
                                    atak[1] = krolY + i - 4;
                                    zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                    //System.out.println("sprawdzone: " + klopot[0] + "," + klopot[1]);      

                                    if (zastawa[0] != -1 && zastawa[1] != -1) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean znajdz_ruch(char[][] kontrolamat, boolean czybiale, char symbol, int[] pole_baza,
            boolean przelotcan) {
        boolean wynik = false;
        char[][] pomocnicze;
        char[][] pomocna = new char[16][16];
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                System.arraycopy(kontrolamat[i], 0, pomocna[i + 4], 4, 8);
            }
        }
        byte[] pole_czas = new byte[2];
        byte[] baza_konca = new byte[2];
        pole_czas[1] = (byte) (pole_baza[0] + 4);
        pole_czas[0] = (byte) (pole_baza[1] + 4);
        pomocnicze = pomocna;
        int[] pole_start = new int[2];
        pole_start[0] = pole_baza[0];
        pole_start[1] = pole_baza[1];
        if (czybiale) {
            switch (symbol) {
                case 'P': {
                    if (przelotcan) {
                        if (pomocnicze[pole_czas[0]][pole_czas[1] - 1] == 'p' && pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == ' ' && pole_czas[0] - 4 == 4) {
                            kontrolamat[pole_czas[0] + 1 - 4][pole_czas[1] - 1 - 4] = 'P';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = ' ';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 1 - 4] = ' ';
                            baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);

                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_czas[0] + 1 - 4][pole_czas[1] - 1 - 4] = ' ';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = 'P';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 1 - 4] = 'p';
                        }
                        if (pomocnicze[pole_czas[0]][pole_czas[1] + 1] == 'p' && pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == ' ' && pole_czas[0] - 4 == 4) {
                            kontrolamat[pole_czas[0] + 1 - 4][pole_czas[1] + 1 - 4] = 'P';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = ' ';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] + 1 - 4] = ' ';
                            baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);

                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_czas[0] + 1 - 4][pole_czas[1] + 1 - 4] = ' ';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = 'P';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] + 1 - 4] = 'p';
                        }
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'n')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'p')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'b')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'r')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'q')) {
                        baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'n')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'p')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'b')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'r')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'q')) {
                        baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if (pomocnicze[pole_czas[0] + 1][pole_czas[1]] == ' ') {
                        baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                        /*  for (byte i = 0; i < 8; i++) {
                            for (byte j = 0; j < 8; j++) {
                        System.out.print(kontrolamat[i][j]);
                            }System.out.println("");}*/
                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        if (wynik) {
                            return wynik;
                        }
                    }
                }
                break;
                case 'Q': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if (i != 0 || j != 0) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'R': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if (((i == 0 && j != 0) || (j == 0 && i != 0)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'B': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if ((Math.abs(i) == Math.abs(j)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'N': {
                    for (byte i = -2; i <= 2; i++) {
                        for (byte j = -2; j <= 2; j++) {
                            if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        if (!czybiale) {
            switch (symbol) {
                case 'p': {
                    if (przelotcan) {
                        if (pomocnicze[pole_czas[0]][pole_czas[1] - 1] == 'p' && pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == ' ' && pole_czas[0] - 4 == 3) {
                            kontrolamat[pole_czas[0] - 1 - 4][pole_czas[1] - 1 - 4] = 'p';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = ' ';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 1 - 4] = ' ';
                            baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);

                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_czas[0] - 1 - 4][pole_czas[1] - 1 - 4] = ' ';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = 'p';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 1 - 4] = 'P';
                        }
                        if (pomocnicze[pole_czas[0]][pole_czas[1] + 1] == 'p' && pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == ' ' && pole_czas[0] - 4 == 3) {
                            kontrolamat[pole_czas[0] - 1 - 4][pole_czas[1] + 1 - 4] = 'p';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = ' ';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] + 1 - 4] = ' ';
                            baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);

                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_czas[0] - 1 - 4][pole_czas[1] + 1 - 4] = ' ';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = 'p';
                            kontrolamat[pole_czas[0] - 4][pole_czas[1] + 1 - 4] = 'P';
                        }
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'N')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'P')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'B')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'R')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'Q')) {
                        baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'N')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'P')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'B')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'R')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'Q')) {
                        baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';

                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if (pomocnicze[pole_czas[0] - 1][pole_czas[1]] == ' ') {
                        baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';

                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        if (wynik) {
                            return wynik;
                        }
                    }
                }
                break;
                case 'q': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if (i != 0 || j != 0) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'r': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if (((i == 0 && j != 0) || (j == 0 && i != 0)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'b': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if ((Math.abs(i) == Math.abs(j)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'n': {
                    for (byte i = -2; i <= 2; i++) {
                        for (byte j = -2; j <= 2; j++) {
                            if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        return wynik;

    }

    public static boolean uciekaj(char[][] ustawienie, boolean czybiale, int[] poza_krolewska) {
        boolean ucieczka=false;
        char przech;
        int krolX = poza_krolewska[0], krolY = poza_krolewska[1];
        for (byte i = -1; i <= 1; i++) {
            for (byte j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    ucieczka = false;
                } else {
                    if ((krolX + i > -1 && krolX + i < 8) && (krolY + j > -1 && krolY + j < 8)) {
                        if (ustawienie[krolX + i][krolY + j] == ' '
                                || (!czybiale && (ustawienie[krolX + i][krolY + j] == 'P' || ustawienie[krolX + i][krolY + j] == 'N' || ustawienie[krolX + i][krolY + j] == 'B' || ustawienie[krolX + i][krolY + j] == 'R' || ustawienie[krolX + i][krolY + j] == 'Q'))
                                || (czybiale && (ustawienie[krolX + i][krolY + j] == 'p' || ustawienie[krolX + i][krolY + j] == 'n' || ustawienie[krolX + i][krolY + j] == 'b' || ustawienie[krolX + i][krolY + j] == 'r' || ustawienie[krolX + i][krolY + j] == 'q'))) {
                            przech = ustawienie[krolX + i][krolY + j];
                            if (czybiale) {
                                ustawienie[krolX + i][krolY + j] = 'K';
                            } else {
                                ustawienie[krolX + i][krolY + j] = 'k';
                            }
                            ustawienie[krolX][krolY] = ' ';
                            ucieczka = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale);
                            ustawienie[krolX + i][krolY + j] = przech;
                            if (czybiale) {
                                ustawienie[krolX][krolY] = 'K';
                            } else {
                                ustawienie[krolX][krolY] = 'k';
                            }
                            if (ucieczka) {
                                return true;
                            }
                        } else {
                            ucieczka = false;
                        }
                    }
                }
            }
        }
        return ucieczka;
    }

    static boolean uciekaj(char[][] ustawienie, char[][] nakladki, boolean czybiale, int[] poza_krolewska) {
        boolean ucieczka = true;
        char przech;
        int krolX = poza_krolewska[0], krolY = poza_krolewska[1];
        for (byte i = -1; i <= 1; i++) {
            for (byte j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    ucieczka = false;
                } else {
                    if ((krolX + i > -1 && krolX + i < 8) && (krolY + j > -1 && krolY + j < 8)) {
                        if (ustawienie[krolX + i][krolY + j] == ' '
                                || (!czybiale && (ustawienie[krolX + i][krolY + j] == 'P' || ustawienie[krolX + i][krolY + j] == 'N' || ustawienie[krolX + i][krolY + j] == 'B' || ustawienie[krolX + i][krolY + j] == 'R' || ustawienie[krolX + i][krolY + j] == 'Q'))
                                || (czybiale && (ustawienie[krolX + i][krolY + j] == 'p' || ustawienie[krolX + i][krolY + j] == 'n' || ustawienie[krolX + i][krolY + j] == 'b' || ustawienie[krolX + i][krolY + j] == 'r' || ustawienie[krolX + i][krolY + j] == 'q'))) {
                            przech = ustawienie[krolX + i][krolY + j];
                            if (czybiale) {
                                ustawienie[krolX + i][krolY + j] = 'K';
                            } else {
                                ustawienie[krolX + i][krolY + j] = 'k';
                            }
                            ustawienie[krolX][krolY] = ' ';
                            ucieczka = !RuchZagrozenie_kontrola.szach(ustawienie, czybiale)
                                    && !RuchZagrozenie_kontrola.szach(ustawienie, nakladki, czybiale);
                            ustawienie[krolX + i][krolY + j] = przech;
                            if (czybiale) {
                                ustawienie[krolX][krolY] = 'K';
                            } else {
                                ustawienie[krolX][krolY] = 'k';
                            }
                            if (ucieczka) {
                                return true;
                            }
                        } else {
                            ucieczka = false;
                        }
                    }
                }
            }
        }
        if (nakladki[krolX][krolY] != ' ') {
            return znajdz_bezpieczny_ruch(ustawienie, nakladki, ucieczka, nakladki[krolX][krolY], poza_krolewska, ucieczka);
        }
        return ucieczka;
    }

    public static boolean znajdzodsiecz(char[][] ustawienie, char[][] nakladka, boolean czybiale, int[] klopot,
            int kol, boolean przelotcan) {
        int pomoc;
        char przechowalnia;
        int[] odsiecz = new int[2];
        int X, Y, alfa, beta;
        char[][] poza_odsieczy;
        char bijacy;
        boolean dozwolony;
        poza_odsieczy = ustawienie;
        char[][] pomocnicze = new char[16][16];
        X = klopot[1] + 4;
        Y = klopot[0] + 4;
        for (int i = 0; i < 8; i++) {
            System.arraycopy(nakladka[i], 0, pomocnicze[i + 4], 4, 8);
        }

        if ((!czybiale && ((pomocnicze[X + 2][Y - 1] == 'n')
                || (pomocnicze[X + 2][Y + 1] == 'n')
                || (pomocnicze[X - 2][Y - 1] == 'n')
                || (pomocnicze[X - 2][Y + 1] == 'n')
                || (pomocnicze[X + 1][Y + 2] == 'n')
                || (pomocnicze[X + 1][Y - 2] == 'n')
                || (pomocnicze[X - 1][Y + 2] == 'n')
                || (pomocnicze[X - 1][Y - 2] == 'n')))
                || (czybiale && ((pomocnicze[X + 2][Y - 1] == 'N')
                || (pomocnicze[X + 2][Y + 1] == 'N')
                || (pomocnicze[X - 2][Y - 1] == 'N')
                || (pomocnicze[X - 2][Y + 1] == 'N')
                || (pomocnicze[X + 1][Y + 2] == 'N')
                || (pomocnicze[X + 1][Y - 2] == 'N')
                || (pomocnicze[X - 1][Y + 2] == 'N')
                || (pomocnicze[X - 1][Y - 2] == 'N')))) {
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                        if ((czybiale && pomocnicze[X + i][Y + j] == 'N') || (!czybiale && pomocnicze[X + i][Y + j] == 'n')) {
                            odsiecz[0] = X + i - 4;
                            odsiecz[1] = Y + j - 4;
                            if (czybiale) {
                                bijacy = 'N';
                            } else {
                                bijacy = 'n';
                            }
                            przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                            poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
                            poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                            nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                            dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                                    && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                            poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                            poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
                            nakladka[odsiecz[0]][odsiecz[1]] = bijacy;
                            if (dozwolony) {
                                return true;
                            }
                        }
                    }
                }
            }

        }

        int[] przelocik = new int[2];
        if ((!czybiale && ((pomocnicze[X + 1][Y + 1] == 'p') || (pomocnicze[X + 1][Y - 1] == 'p')))
                || (czybiale && ((pomocnicze[X - 1][Y + 1] == 'P') || (pomocnicze[X - 1][Y - 1] == 'P')))) {
            if (czybiale) {
                bijacy = 'P';
                if (pomocnicze[X - 1][Y + 1] == 'P') {
                    odsiecz[0] = X - 1 - 4;
                    odsiecz[1] = Y + 1 - 4;
                } else {
                    odsiecz[0] = X - 1 - 4;
                    odsiecz[1] = Y - 1 - 4;
                }
            } else {
                bijacy = 'p';
                if (pomocnicze[X + 1][Y + 1] == 'p') {
                    odsiecz[0] = X + 1 - 4;
                    odsiecz[1] = Y + 1 - 4;
                } else {
                    odsiecz[0] = X + 1 - 4;
                    odsiecz[1] = Y - 1 - 4;
                }
            }
            przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
            poza_odsieczy[klopot[1]][klopot[0]] = bijacy;
            poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
            nakladka[odsiecz[0]][odsiecz[1]] = ' ';
            dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                    && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
            poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
            poza_odsieczy[odsiecz[0]][odsiecz[1]] = bijacy;
            nakladka[odsiecz[0]][odsiecz[1]] = bijacy;
            if (dozwolony) {
                return true;
            }
        }
        for (int p = -1; p < 2; p++) {
            if (p != 0 && (klopot[0] + p > -1 && klopot[0] + p < 8)) {
                if ((!czybiale && przelotcan
                        && (((poza_odsieczy[2][klopot[0]] == ' ' && (poza_odsieczy[klopot[1]][klopot[0] + p] == 'p')))))
                        || (czybiale && przelotcan
                        && (((poza_odsieczy[5][klopot[0]] == ' ' && (poza_odsieczy[klopot[1]][klopot[0] + p] == 'P')))))) {
                    System.out.println("przelot");
                    if (czybiale) {
                        bijacy = 'P';
                        przelocik[0] = klopot[1];
                        przelocik[1] = klopot[0];
                        if (poza_odsieczy[4][klopot[0] + p] == 'P') {
                            odsiecz[0] = klopot[0] + p;
                            odsiecz[1] = klopot[1];
                        }
                    } else {
                        bijacy = 'p';
                        przelocik[0] = klopot[1];
                        przelocik[1] = klopot[0];
                        if (poza_odsieczy[3][klopot[0] + p] == 'p') {
                            odsiecz[0] = klopot[0] + p;
                            odsiecz[1] = klopot[1];
                        }
                    }

                    poza_odsieczy[przelocik[1]][przelocik[0]] = bijacy;
                    pomoc = odsiecz[0];
                    odsiecz[0] = odsiecz[1];
                    odsiecz[1] = pomoc;
                    char moved = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    poza_odsieczy[klopot[1]][klopot[0]] = ' ';
                    nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                            && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    poza_odsieczy[przelocik[1]][przelocik[0]] = ' ';
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = moved;
                    nakladka[odsiecz[0]][odsiecz[1]] = bijacy;
                    if (dozwolony) {
                        return true;
                    }
                }
            }
        }
        alfa = klopot[1] + 4;
        beta = klopot[0] + 4;
        //System.out.println("sprawdzane pole bicia: " + alfa + "," + beta+" "+pomocnicze[alfa][beta]);
        int licz = 0;
        for (int i = alfa + 1; i < 12; i++) {
            licz = licz + 1;
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else if (poza_odsieczy[i - 4][beta + licz - 4] != ' ') {
                    if ((!czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B'))) {

                    } else {
                        if (czybiale) {
                            if (pomocnicze[i][beta + licz] == 'Q') {
                                bijacy = 'Q';
                            } else {
                                bijacy = 'B';
                            }
                        } else {
                            if (pomocnicze[i][beta + licz] == 'q') {
                                bijacy = 'q';
                            } else {
                                bijacy = 'b';
                            }
                        }
                        odsiecz[1] = beta + licz - 4;
                        odsiecz[0] = i - 4;
                        przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                        char moved = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                        poza_odsieczy[klopot[1]][klopot[0]] = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                        nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                        dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                                && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                        nakladka[klopot[1]][klopot[0]] = bijacy;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = moved;
                        poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                        if (dozwolony) {
                            return true;
                        }
                    }
                    break;
                }
            }
        }

        licz = 0;
        for (int i = alfa + 1; i < 12; i++) {
            licz = licz - 1;
            if (beta + licz <= 3) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else if (poza_odsieczy[i - 4][beta + licz - 4] != ' ') {
                    if ((!czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B'))) {

                    } else {
                        if (czybiale) {
                            if (pomocnicze[i][beta + licz] == 'Q') {
                                bijacy = 'Q';
                            } else {
                                bijacy = 'B';
                            }
                        } else {
                            if (pomocnicze[i][beta + licz] == 'q') {
                                bijacy = 'q';
                            } else {
                                bijacy = 'b';
                            }
                        }
                        odsiecz[1] = beta + licz - 4;
                        odsiecz[0] = i - 4;
                        przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                        char moved = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                        poza_odsieczy[klopot[1]][klopot[0]] = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                        nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                        dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                                && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                        nakladka[klopot[1]][klopot[0]] = bijacy;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = moved;
                        poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                        if (dozwolony) {
                            return true;
                        }
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (int i = alfa - 1; i > 3; i--) {
            licz = licz + 1;
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else if (poza_odsieczy[i - 4][beta + licz - 4] != ' ') {
                    if ((!czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B'))) {

                    } else {
                        if (czybiale) {
                            if (pomocnicze[i][beta + licz] == 'Q') {
                                bijacy = 'Q';
                            } else {
                                bijacy = 'B';
                            }
                        } else {
                            if (pomocnicze[i][beta + licz] == 'q') {
                                bijacy = 'q';
                            } else {
                                bijacy = 'b';
                            }
                        }
                        odsiecz[1] = beta + licz - 4;
                        odsiecz[0] = i - 4;
                        przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                        char moved = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                        poza_odsieczy[klopot[1]][klopot[0]] = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                        nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                        dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                                && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                        nakladka[klopot[1]][klopot[0]] = bijacy;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = moved;
                        poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                        if (dozwolony) {
                            return true;
                        }
                    }
                    break;
                }
            }
        }

        licz = 0;

        for (int i = alfa - 1; i > 2; i--) {
            licz = licz - 1;
            if (beta + licz <= 12) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else if (poza_odsieczy[i - 4][beta + licz - 4] != ' ') {
                    if ((!czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b'))
                            || (czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B'))) {

                    } else {
                        if (czybiale) {
                            if (pomocnicze[i][beta + licz] == 'Q') {
                                bijacy = 'Q';
                            } else {
                                bijacy = 'B';
                            }
                        } else {
                            if (pomocnicze[i][beta + licz] == 'q') {
                                bijacy = 'q';
                            } else {
                                bijacy = 'b';
                            }
                        }
                        odsiecz[1] = beta + licz - 4;
                        odsiecz[0] = i - 4;
                        przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                        char moved = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                        poza_odsieczy[klopot[1]][klopot[0]] = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                        nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                        dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                                && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                        nakladka[klopot[1]][klopot[0]] = bijacy;
                        poza_odsieczy[odsiecz[0]][odsiecz[1]] = moved;
                        poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                        if (dozwolony) {

                            return true;
                        }
                    }
                    break;
                }
            }
        }
        for (int i = alfa + 1; i < 12; i++) {
            if (pomocnicze[i][beta] == ' ') {
            } else if (poza_odsieczy[i - 4][beta - 4] != ' ') {
                if ((!czybiale && (pomocnicze[i][beta] != 'q' && pomocnicze[i][beta] != 'r'))
                        || (czybiale && (pomocnicze[i][beta] != 'Q' && pomocnicze[i][beta] != 'R'))) {

                } else {
                    if (czybiale) {
                        if (pomocnicze[i][beta] == 'Q') {
                            bijacy = 'Q';
                        } else {
                            bijacy = 'R';
                        }
                    } else {
                        if (pomocnicze[i][beta] == 'q') {
                            bijacy = 'q';
                        } else {
                            bijacy = 'r';
                        }
                    }
                    odsiecz[1] = Y - 4;
                    odsiecz[0] = i - 4;
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    char moved = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                    poza_odsieczy[klopot[1]][klopot[0]] = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                            && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                    nakladka[klopot[1]][klopot[0]] = bijacy;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = moved;
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    if (dozwolony) {

                        return true;
                    }
                }
                break;
            }
        }

        for (int i = alfa - 1; i > 3; i--) {
            if (pomocnicze[i][beta] == ' ') {
            } else if (poza_odsieczy[i - 4][beta - 4] != ' ') {
                if ((!czybiale && (pomocnicze[i][beta] != 'q' && pomocnicze[i][beta] != 'r'))
                        || (czybiale && (pomocnicze[i][beta] != 'Q' && pomocnicze[i][beta] != 'R'))) {

                } else {
                    if (czybiale) {
                        if (pomocnicze[i][beta] == 'Q') {
                            bijacy = 'Q';
                        } else {
                            bijacy = 'R';
                        }
                    } else {
                        if (pomocnicze[i][beta] == 'q') {
                            bijacy = 'q';
                        } else {
                            bijacy = 'r';
                        }
                    }
                    odsiecz[1] = Y - 4;
                    odsiecz[0] = i - 4;
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    char moved = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                    poza_odsieczy[klopot[1]][klopot[0]] = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                            && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                    nakladka[klopot[1]][klopot[0]] = bijacy;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = moved;
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    if (dozwolony) {
                        return true;
                    }
                }
                break;
            }
        }

        for (int i = Y + 1; i < 12; i++) {
            if (pomocnicze[alfa][i] == ' ') {
            } else if (poza_odsieczy[alfa - 4][i - 4] != ' ') {
                if ((!czybiale && (pomocnicze[alfa][i] != 'q' && pomocnicze[alfa][i] != 'r'))
                        || (czybiale && (pomocnicze[alfa][i] != 'Q' && pomocnicze[alfa][i] != 'R'))) {

                } else {
                    if (czybiale) {
                        if (pomocnicze[alfa][i] == 'Q') {
                            bijacy = 'Q';
                        } else {
                            bijacy = 'R';
                        }
                    } else {
                        if (pomocnicze[alfa][i] == 'q') {
                            bijacy = 'q';
                        } else {
                            bijacy = 'r';
                        }
                    }
                    odsiecz[1] = i - 4;
                    odsiecz[0] = alfa - 4;
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    char moved = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                    poza_odsieczy[klopot[1]][klopot[0]] = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                            && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                    nakladka[klopot[1]][klopot[0]] = bijacy;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = moved;
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    if (dozwolony) {
                        return true;
                    }
                }
                break;
            }
        }

        for (int i = beta - 1; i > 3; i--) {
            if (pomocnicze[alfa][i] == ' ') {
            } else if (poza_odsieczy[alfa - 4][i - 4] != ' ') {
                if ((!czybiale && (pomocnicze[alfa][i] != 'q' && pomocnicze[alfa][i] != 'r'))
                        || (czybiale && (pomocnicze[alfa][i] != 'Q' && pomocnicze[alfa][i] != 'R'))) {

                } else {
                    if (czybiale) {
                        if (pomocnicze[alfa][i] == 'Q') {
                            bijacy = 'Q';
                        } else {
                            bijacy = 'R';
                        }
                    } else {
                        if (pomocnicze[alfa][i] == 'q') {
                            bijacy = 'q';
                        } else {
                            bijacy = 'r';
                        }
                    }
                    odsiecz[1] = i - 4;
                    odsiecz[0] = alfa - 4;
                    przechowalnia = poza_odsieczy[klopot[1]][klopot[0]];
                    char moved = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                    poza_odsieczy[klopot[1]][klopot[0]] = poza_odsieczy[odsiecz[0]][odsiecz[1]];
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = ' ';
                    nakladka[odsiecz[0]][odsiecz[1]] = ' ';
                    dozwolony = !RuchZagrozenie_kontrola.szach(poza_odsieczy, czybiale)
                            && !RuchZagrozenie_kontrola.szach(poza_odsieczy, nakladka, czybiale);
                    nakladka[klopot[1]][klopot[0]] = bijacy;
                    poza_odsieczy[odsiecz[0]][odsiecz[1]] = moved;
                    poza_odsieczy[klopot[1]][klopot[0]] = przechowalnia;
                    if (dozwolony) {
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    public static boolean zastaw(char[][] ustawienie, char[][] nakladka, boolean ruchB, int[] atak, int[] poza_krolewska,
            boolean przelotcan) {
        {
            boolean wynik = false;
            //System.out.println("X:" + atak[0] + "Y:" + atak[1]);
            //System.out.println(" krolX:" + poza_krolewska[0] + "Y:" + poza_krolewska[1]);
            //System.out.println(ustawienie[atak[0]][atak[1]]);
            int X, Y, krolX, krolY;
            char[][] pozycja;
            pozycja = ustawienie;
            Y = atak[0] + 4;
            X = atak[1] + 4;

            int[] zastawa;
            char[][] pomocna = new char[16][16];
            for (int i = 0; i < 8; i++) {
                System.arraycopy(nakladka[i], 0, pomocna[i + 4], 4, 8);
            }
            krolX = poza_krolewska[0] + 4;
            krolY = poza_krolewska[1] + 4;
            if ((Math.abs(krolX - X) == 1 && Math.abs(krolY - Y) == 2)
                    || (Math.abs(krolX - X) == 2 && Math.abs(krolY - Y) == 1)) {
                return false;
            } else {
                if (((Math.abs(krolX - X) != 1) && (Math.abs(krolY - Y) != 1))
                        || ((Math.abs(krolX - X) == 0 || Math.abs(krolX - X) > 1) && (Math.abs(krolY - Y) != 1))
                        || ((Math.abs(krolY - Y) == 0 || Math.abs(krolY - Y) > 1) && (Math.abs(krolX - X) != 1))) {
                    //System.out.println("|||");
                    if (krolX - X == 0) {
                        if (krolY - Y > 0) {
                            for (int i = 1; i < (krolY - Y); i++) {
                                atak[0] = krolX - 4;
                                atak[1] = krolY - i - 4;
                                zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                if (zastawa[0] == -1 || zastawa[1] == -1) {
                                    Wspomagacz.znajdzmozliwosc(pozycja, nakladka, ruchB, atak, przelotcan);
                                }
                                if (zastawa[0] != -1 && zastawa[1] != -1) {
                                    return true;
                                }
                            }
                        } else {
                            for (int i = 1; i < (Math.abs(krolY - Y)); i++) {

                                atak[0] = krolX - 4;
                                atak[1] = krolY + i - 4;
                                zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                if (zastawa[0] == -1 || zastawa[1] == -1) {
                                    Wspomagacz.znajdzmozliwosc(pozycja, nakladka, ruchB, atak, przelotcan);
                                }
                                if (zastawa[0] != -1 && zastawa[1] != -1) {
                                    return true;
                                }

                            }
                        }
                    } else {
                        //x!=0
                        if (krolX - X > 0) {
                            //x>0
                            if (krolY - Y == 0) {
                                //y==0
                                for (int i = 1; i < (krolX - X); i++) {
                                    atak[0] = krolX - i - 4;
                                    atak[1] = krolY - 4;
                                    zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                    if (zastawa[0] == -1 || zastawa[1] == -1) {
                                        Wspomagacz.znajdzmozliwosc(pozycja, nakladka, ruchB, atak, przelotcan);
                                    }
                                    if (zastawa[0] != -1 && zastawa[1] != -1) {
                                        return true;
                                    }
                                }
                            } else {
                                //y!=0
                                if (krolY - Y > 0) {
                                    //System.out.println("y>0");
                                    for (int i = 1; i < (krolX - X); i++) {
                                        atak[0] = krolX - i - 4;
                                        atak[1] = krolY - i - 4;
                                        zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                        if (zastawa[0] == -1 || zastawa[1] == -1) {
                                            Wspomagacz.znajdzmozliwosc(pozycja, nakladka, ruchB, atak, przelotcan);
                                        }
                                        if (zastawa[0] != -1 && zastawa[1] != -1) {
                                            return true;
                                        }
                                    }
                                } else {
                                    // System.out.println("y<0");
                                    for (int i = 1; i < (krolX - X); i++) {
                                        atak[0] = krolX - i - 4;
                                        atak[1] = krolY + i - 4;
                                        zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                        if (zastawa[0] == -1 || zastawa[1] == -1) {
                                            Wspomagacz.znajdzmozliwosc(pozycja, nakladka, ruchB, atak, przelotcan);
                                        }
                                        if (zastawa[0] != -1 && zastawa[1] != -1) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else {
                            //"x<0
                            if (krolY - Y == 0) {
                                //y==0
                                //System.out.println("-");
                                for (int i = 1; i < (Math.abs(krolX - X)); i++) {
                                    atak[0] = krolX + i - 4;
                                    atak[1] = krolY - 4;
                                    zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                    if (zastawa[0] == -1 || zastawa[1] == -1) {
                                        Wspomagacz.znajdzmozliwosc(pozycja, nakladka, ruchB, atak, przelotcan);
                                    }
                                    if (zastawa[0] != -1 && zastawa[1] != -1) {
                                        //System.out.println(zastawa[0] + "|" + zastawa[1]);
                                        return true;
                                    }
                                }
                            } else { //y!=0
                                if (krolY - Y > 0) {
                                    //System.out.println("y>0");
                                    for (int i = 1; i < (Math.abs(krolX - X)); i++) {
                                        atak[0] = krolX + i - 4;
                                        atak[1] = krolY - i - 4;
                                        zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                        if (zastawa[0] == -1 || zastawa[1] == -1) {
                                            Wspomagacz.znajdzmozliwosc(pozycja, nakladka, ruchB, atak, przelotcan);
                                        }
                                        if (zastawa[0] != -1 && zastawa[1] != -1) {
                                            return true;
                                        }
                                    }
                                } else {
                                    //System.out.println("y<0");
                                    for (int i = 1; i < (Math.abs(krolX - X)); i++) {
                                        atak[0] = krolX + i - 4;
                                        atak[1] = krolY + i - 4;
                                        zastawa = Wspomagacz.znajdzmozliwosc(pozycja, ruchB, atak, przelotcan);
                                        if (zastawa[0] == -1 || zastawa[1] == -1) {
                                            Wspomagacz.znajdzmozliwosc(pozycja, nakladka, ruchB, atak, przelotcan);
                                        }
                                        if (zastawa[0] != -1 && zastawa[1] != -1) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    return false;
                }
            }
            return false;
        }
    }

    static boolean znajdz_ruch(char[][] kontrolamat, char[][] nakladki, boolean czybiale, char symbol, int[] pole_baza, boolean przelotcan) {
        {
            boolean wynik = false;
            char[][] pomocnicze;
            char[][] pomocna = new char[16][16];
            for (byte i = 0; i < 8; i++) {
                for (byte j = 0; j < 8; j++) {
                    System.arraycopy(kontrolamat[i], 0, pomocna[i + 4], 4, 8);
                }
            }
            byte[] pole_czas = new byte[2];
            byte[] baza_konca = new byte[2];
            pole_czas[1] = (byte) (pole_baza[0] + 4);
            pole_czas[0] = (byte) (pole_baza[1] + 4);
            pomocnicze = pomocna;
            int[] pole_start = new int[2];
            pole_start[0] = pole_baza[0];
            pole_start[1] = pole_baza[1];
            if (czybiale) {
                switch (symbol) {
                    case 'P': {
                        if (przelotcan) {
                            if (pomocnicze[pole_czas[0]][pole_czas[1] - 1] == 'p' && pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == ' ') {
                                kontrolamat[pole_czas[0] + 1 - 4][pole_czas[1] - 1 - 4] = 'P';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = ' ';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 1 - 4] = ' ';
                                baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                                baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);

                                wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                kontrolamat[pole_czas[0] + 1 - 4][pole_czas[1] - 1 - 4] = ' ';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = 'P';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 1 - 4] = 'p';
                            }
                            if (pomocnicze[pole_czas[0]][pole_czas[1] + 1] == 'p' && pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == ' ') {
                                kontrolamat[pole_czas[0] + 1 - 4][pole_czas[1] + 1 - 4] = 'P';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = ' ';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] + 1 - 4] = ' ';
                                baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                                baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);

                                wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                kontrolamat[pole_czas[0] + 1 - 4][pole_czas[1] + 1 - 4] = ' ';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = 'P';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] + 1 - 4] = 'p';
                            }
                            if (wynik) {
                                return wynik;
                            }
                        }
                        if ((pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'n')
                                || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'p')
                                || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'b')
                                || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'r')
                                || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'q')) {
                            baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);
                            char przechowalnie;
                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                            if (wynik) {
                                return wynik;
                            }
                        }
                        if ((pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'n')
                                || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'p')
                                || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'b')
                                || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'r')
                                || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'q')) {
                            baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);
                            char przechowalnie;
                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                            if (wynik) {
                                return wynik;
                            }
                        }
                        if (pomocnicze[pole_czas[0] + 1][pole_czas[1]] == ' ') {
                            baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] - 4);
                            char przechowalnie;
                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                            /*  for (byte i = 0; i < 8; i++) {
                            for (byte j = 0; j < 8; j++) {
                        System.out.print(kontrolamat[i][j]);
                            }System.out.println("");}*/
                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                            if (wynik) {
                                return wynik;
                            }
                        }
                    }
                    case 'Q': {
                        for (byte i = -1; i <= 1; i++) {
                            for (byte j = -1; j <= 1; j++) {
                                if (i != 0 || j != 0) {
                                    if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                        if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                                || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                            baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                            char przechowalnie;
                                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                            if (wynik) {
                                                return wynik;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 'R': {
                        for (byte i = -1; i <= 1; i++) {
                            for (byte j = -1; j <= 1; j++) {
                                if (((i == 0 && j != 0) || (j == 0 && i != 0)) && (i != 0 || j != 0)) {
                                    if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                        if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                                || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                            baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                            char przechowalnie;
                                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                            if (wynik) {
                                                return wynik;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 'B': {
                        for (byte i = -1; i <= 1; i++) {
                            for (byte j = -1; j <= 1; j++) {
                                if ((Math.abs(i) == Math.abs(j)) && (i != 0 || j != 0)) {
                                    if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                        if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                                || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                            baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                            char przechowalnie;
                                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                            if (wynik) {
                                                return wynik;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 'N': {
                        for (byte i = -2; i <= 2; i++) {
                            for (byte j = -2; j <= 2; j++) {
                                if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                                    if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                        if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                                || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                            baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                            char przechowalnie;
                                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                            if (wynik) {
                                                return wynik;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!czybiale) {
                switch (symbol) {
                    case 'p': {
                        if (przelotcan) {
                            if (pomocnicze[pole_czas[0]][pole_czas[1] - 1] == 'p' && pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == ' ' && pole_czas[0] - 4 == 3) {
                                kontrolamat[pole_czas[0] - 1 - 4][pole_czas[1] - 1 - 4] = 'p';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = ' ';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 1 - 4] = ' ';
                                baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                                baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);

                                wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                kontrolamat[pole_czas[0] - 1 - 4][pole_czas[1] - 1 - 4] = ' ';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = 'p';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 1 - 4] = 'P';
                            }
                            if (pomocnicze[pole_czas[0]][pole_czas[1] + 1] == 'p' && pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == ' ' && pole_czas[0] - 4 == 3) {
                                kontrolamat[pole_czas[0] - 1 - 4][pole_czas[1] + 1 - 4] = 'p';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = ' ';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] + 1 - 4] = ' ';
                                baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                                baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);

                                wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                kontrolamat[pole_czas[0] - 1 - 4][pole_czas[1] + 1 - 4] = ' ';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] - 4] = 'p';
                                kontrolamat[pole_czas[0] - 4][pole_czas[1] + 1 - 4] = 'P';
                            }
                            if (wynik) {
                                return wynik;
                            }
                        }
                        if ((pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'N')
                                || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'P')
                                || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'B')
                                || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'R')
                                || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'Q')) {
                            baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);
                            char przechowalnie;
                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                            if (wynik) {
                                return wynik;
                            }
                        }
                        if ((pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'N')
                                || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'P')
                                || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'B')
                                || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'R')
                                || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'Q')) {
                            baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);
                            char przechowalnie;
                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';

                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                            if (wynik) {
                                return wynik;
                            }
                        }
                        if (pomocnicze[pole_czas[0] - 1][pole_czas[1]] == ' ') {
                            baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                            baza_konca[0] = (byte) (pole_czas[1] - 4);
                            char przechowalnie;
                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';

                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                            if (wynik) {
                                return wynik;
                            }
                        }
                    }
                    case 'q': {
                        for (byte i = -1; i <= 1; i++) {
                            for (byte j = -1; j <= 1; j++) {
                                if (i != 0 || j != 0) {
                                    if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                        if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                                || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                            baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                            char przechowalnie;
                                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                            if (wynik) {
                                                return wynik;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 'r': {
                        for (byte i = -1; i <= 1; i++) {
                            for (byte j = -1; j <= 1; j++) {
                                if (((i == 0 && j != 0) || (j == 0 && i != 0)) && (i != 0 || j != 0)) {
                                    if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                        if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                                || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                            baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                            char przechowalnie;
                                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                            if (wynik) {
                                                return wynik;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 'b': {
                        for (byte i = -1; i <= 1; i++) {
                            for (byte j = -1; j <= 1; j++) {
                                if ((Math.abs(i) == Math.abs(j)) && (i != 0 || j != 0)) {
                                    if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                        if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                                || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                            baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                            char przechowalnie;
                                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;

                                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                            if (wynik) {
                                                return wynik;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 'n': {
                        for (byte i = -2; i <= 2; i++) {
                            for (byte j = -2; j <= 2; j++) {
                                if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                                    if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                        if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                                && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                                || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                                || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                            baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                            kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                            char przechowalnie;
                                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = symbol;
                                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale);
                                            kontrolamat[pole_start[1]][pole_start[0]] = symbol;
                                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                            if (wynik) {
                                                return wynik;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return wynik;

        }
    }

    private static boolean znajdz_bezpieczny_ruch(char[][] kontrolamat, char[][] nakladki, boolean czybiale, char symbol, int[] pole_baza, boolean przelotcan) {
        boolean wynik = false;
        char[][] pomocnicze;
        char[][] pomocna = new char[16][16];
        char[][] pomocnaN = new char[16][16];
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                System.arraycopy(kontrolamat[i], 0, pomocna[i + 4], 4, 8);
                System.arraycopy(nakladki[i], 0, pomocnaN[i + 4], 4, 8);
            }
        }
        byte[] pole_czas = new byte[2];
        byte[] baza_konca = new byte[2];
        pole_czas[1] = (byte) (pole_baza[0] + 4);
        pole_czas[0] = (byte) (pole_baza[1] + 4);
        pomocnicze = pomocna;
        int[] pole_start = new int[2];
        pole_start[0] = pole_baza[0];
        pole_start[1] = pole_baza[1];
        if (czybiale) {
            switch (symbol) {
                case 'P': {
                    if ((pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'n')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'p')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'b')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'r')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'q')) {
                        baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'K';
                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = 'K';
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        nakladki[pole_start[1]][pole_start[0]] = 'P';
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'n')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'p')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'b')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'r')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'q')) {
                        baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'K';
                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = 'K';
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        nakladki[pole_start[1]][pole_start[0]] = 'P';
                        if (wynik) {
                            return wynik;
                        }
                    }
                    for (int i = 1; i < 3; i++) {
                        if ((pomocnicze[pole_czas[0] + i][pole_czas[1]] == ' ') && (i + pole_czas[0] < 8)) {
                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                            baza_konca[0] = (byte) (pole_czas[1] - 4);
                            char przechowalnie;
                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                            kontrolamat[baza_konca[1]][baza_konca[0]] = 'K';
                            nakladki[pole_start[1]][pole_start[0]] = ' ';
                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                    || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                            kontrolamat[pole_start[1]][pole_start[0]] = 'K';
                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                            nakladki[pole_start[1]][pole_start[0]] = 'P';
                            if (wynik) {
                                return wynik;
                            }
                        }
                    }
                }
                case 'Q': {
                    for (byte i = -7; i <= 7; i++) {
                        for (byte j = -7; j <= 7; j++) {
                            int i1 = Math.abs(j) - Math.abs(i);
                            if ((i != 0 || j != 0) && ((i1 == 0)
                                    || (Math.abs(i1) == Math.abs(j) || Math.abs(i1) == Math.abs(i)))) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'K';
                                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = 'K';
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        nakladki[pole_start[1]][pole_start[0]] = 'Q';
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                case 'R': {
                    for (byte i = -7; i <= 7; i++) {
                        for (byte j = -7; j <= 7; j++) {
                            if (((i == 0 && j != 0) || (j == 0 && i != 0)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'K';
                                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = 'K';
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        nakladki[pole_start[1]][pole_start[0]] = 'R';
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                case 'B': {
                    for (byte i = -7; i <= 7; i++) {
                        for (byte j = -7; j <= 7; j++) {
                            if ((Math.abs(i) == Math.abs(j)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'K';
                                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = 'K';
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        nakladki[pole_start[1]][pole_start[0]] = 'B';
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                case 'N': {
                    for (byte i = -2; i <= 2; i++) {
                        for (byte j = -2; j <= 2; j++) {
                            if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'K';
                                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = 'K';
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        nakladki[pole_start[1]][pole_start[0]] = 'N';
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!czybiale) {
            switch (symbol) {
                case 'p': {
                    if ((pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'n')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'p')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'b')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'r')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'q')) {
                        baza_konca[1] = (byte) (pole_czas[0] - 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] + 1 - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'k';
                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = 'k';
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        nakladki[pole_start[1]][pole_start[0]] = 'p';
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'n')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'p')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'b')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'r')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'q')) {
                        baza_konca[1] = (byte) (pole_czas[0] + 1 - 4);
                        baza_konca[0] = (byte) (pole_czas[1] - 1 - 4);
                        char przechowalnie;
                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'k';
                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                        kontrolamat[pole_start[1]][pole_start[0]] = 'k';
                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                        nakladki[pole_start[1]][pole_start[0]] = 'p';
                        if (wynik) {
                            return wynik;
                        }
                    }
                    for (int i = 1; i < 3; i++) {
                        if ((pomocnicze[pole_czas[0] - i][pole_czas[1]] == ' ') && (i + pole_czas[0] < 8)) {
                            baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                            baza_konca[0] = (byte) (pole_czas[1] - 4);
                            char przechowalnie;
                            przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                            kontrolamat[baza_konca[1]][baza_konca[0]] = 'k';
                            nakladki[pole_start[1]][pole_start[0]] = ' ';
                            wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                    || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                            kontrolamat[pole_start[1]][pole_start[0]] = 'k';
                            kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                            nakladki[pole_start[1]][pole_start[0]] = 'p';
                            if (wynik) {
                                return wynik;
                            }
                        }
                    }
                }
                case 'q': {
                    for (byte i = -7; i <= 7; i++) {
                        for (byte j = -7; j <= 7; j++) {
                            if ((i != 0 || j != 0) && ((Math.abs(j) - Math.abs(i) == 0)
                                    || (Math.abs(Math.abs(j) - Math.abs(i)) == Math.abs(j) || Math.abs(Math.abs(j) - Math.abs(i)) == Math.abs(i)))) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'k';
                                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = 'k';
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        nakladki[pole_start[1]][pole_start[0]] = 'q';
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                case 'r': {
                    for (byte i = -7; i <= 7; i++) {
                        for (byte j = -7; j <= 7; j++) {
                            if (((i == 0 && j != 0) || (j == 0 && i != 0)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'k';
                                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = 'k';
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        nakladki[pole_start[1]][pole_start[0]] = 'r';
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                case 'b': {
                    for (byte i = -7; i <= 7; i++) {
                        for (byte j = -7; j <= 7; j++) {
                            if ((Math.abs(i) == Math.abs(j)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'k';
                                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = 'k';
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        nakladki[pole_start[1]][pole_start[0]] = 'b';
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                case 'n': {
                    for (byte i = -2; i <= 2; i++) {
                        for (byte j = -2; j <= 2; j++) {
                            if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        baza_konca[1] = (byte) (pole_czas[0] + i - 4);
                                        baza_konca[0] = (byte) (pole_czas[1] + j - 4);
                                        kontrolamat[pole_start[1]][pole_start[0]] = ' ';
                                        char przechowalnie;
                                        przechowalnie = kontrolamat[baza_konca[1]][baza_konca[0]];
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = 'k';
                                        nakladki[pole_start[1]][pole_start[0]] = ' ';
                                        wynik = !RuchZagrozenie_kontrola.szach(kontrolamat, czybiale)
                                                || !RuchZagrozenie_kontrola.szach(kontrolamat, nakladki, czybiale);
                                        kontrolamat[pole_start[1]][pole_start[0]] = 'k';
                                        kontrolamat[baza_konca[1]][baza_konca[0]] = przechowalnie;
                                        nakladki[pole_start[1]][pole_start[0]] = 'n';
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return wynik;

    }
      public static boolean znajdz_antyruch(char[][] kontrolamat, boolean czybiale, char symbol, int[] pole_baza,
            boolean przelotcan) {
        boolean wynik = false;
        char[][] pomocnicze;
        char[][] pomocna = new char[16][16];
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                System.arraycopy(kontrolamat[i], 0, pomocna[i + 4], 4, 8);
            }
        }
        byte[] pole_czas = new byte[2];
        byte[] baza_konca = new byte[2];
        pole_czas[1] = (byte) (pole_baza[0] + 4);
        pole_czas[0] = (byte) (pole_baza[1] + 4);
        pomocnicze = pomocna;
        int[] pole_start = new int[2];
        pole_start[0] = pole_baza[0];
        pole_start[1] = pole_baza[1];
        if (czybiale) {
            switch (symbol) {
                case 'P': {
                    if (przelotcan) {
                        if (pomocnicze[pole_czas[0]][pole_czas[1] - 1] == 'p' && pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == ' ' && pole_czas[0] - 4 == 4) {
                           wynik=true;
                        }
                        if (pomocnicze[pole_czas[0]][pole_czas[1] + 1] == 'p' && pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == ' ' && pole_czas[0] - 4 == 4) {
                             wynik=true;
                        }
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'n')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'p')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'b')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'r')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'q')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'k')) {
                        wynik = true;
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'n')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'p')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'b')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'r')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] - 1] == 'q')
                            || (pomocnicze[pole_czas[0] + 1][pole_czas[1] + 1] == 'k')) {
                      wynik=true;
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if (pomocnicze[pole_czas[0] + 1][pole_czas[1]] == ' ') {
                        wynik=true;
                        if (wynik) {
                            return wynik;
                        }
                    }
                }
                break;
                case 'Q': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if (i != 0 || j != 0) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'k')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                       wynik=true;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'R': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if (((i == 0 && j != 0) || (j == 0 && i != 0)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'k')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                       wynik=true;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'B': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if ((Math.abs(i) == Math.abs(j)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'k')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        wynik=true;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'N': {
                    for (byte i = -2; i <= 2; i++) {
                        for (byte j = -2; j <= 2; j++) {
                            if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'N')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'P')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'B')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'R')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'Q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'K'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'n')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'p')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'b')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'r')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'k')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        wynik=true;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        if (!czybiale) {
            switch (symbol) {
                case 'p': {
                    if (przelotcan) {
                        if (pomocnicze[pole_czas[0]][pole_czas[1] - 1] == 'p' && pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == ' ' && pole_czas[0] - 4 == 3) {
                            wynik=true;
                        }
                        if (pomocnicze[pole_czas[0]][pole_czas[1] + 1] == 'p' && pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == ' ' && pole_czas[0] - 4 == 3) {
                            wynik=true;
                        }
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'N')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'P')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'B')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'R')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'Q')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] + 1] == 'K')) {
                        wynik=true;
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if ((pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'N')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'P')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'B')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'R')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'Q')
                            || (pomocnicze[pole_czas[0] - 1][pole_czas[1] - 1] == 'K')) {
                        wynik=true;
                        if (wynik) {
                            return wynik;
                        }
                    }
                    if (pomocnicze[pole_czas[0] - 1][pole_czas[1]] == ' ') {
                        wynik=true;
                        if (wynik) {
                            return wynik;
                        }
                    }
                }
                break;
                case 'q': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if (i != 0 || j != 0) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'K')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                       wynik=true;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'r': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if (((i == 0 && j != 0) || (j == 0 && i != 0)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'K')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                        wynik=true;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'b': {
                    for (byte i = -1; i <= 1; i++) {
                        for (byte j = -1; j <= 1; j++) {
                            if ((Math.abs(i) == Math.abs(j)) && (i != 0 || j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'K')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                         wynik=true;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
                case 'n': {
                    for (byte i = -2; i <= 2; i++) {
                        for (byte j = -2; j <= 2; j++) {
                            if ((i != j && i * -1 != j) && (i != 0 && j != 0)) {
                                if (pole_czas[0] + i - 4 > -1 && pole_czas[0] + i - 4 < 8 && pole_czas[1] + j - 4 > -1 && pole_czas[1] + j - 4 < 8) {
                                    if (((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'n')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'p')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'b')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'r')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'q')
                                            && (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] != 'k'))
                                            || ((pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'N')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'P')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'B')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'R')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'Q')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == 'K')
                                            || (pomocnicze[pole_czas[0] + i][pole_czas[1] + j] == ' '))) {
                                         wynik=true;
                                        if (wynik) {
                                            return wynik;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        return wynik;

    }

}
