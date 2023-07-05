/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import szachy.SI_MIN_MAX_Alfa_Beta;
/**
 *
 * @author Patryk Sprawdza ruch pod kątem dopuszczalności
 */
public class RuchZagrozenie_kontrola {

    static boolean rozszerzenie(char symbol, char rozszerzony) {
        switch (symbol) {
            case 'k':
            case 'K':
                return true;
            case 'q':
            case 'Q':
                switch (rozszerzony) {
                    case 'P':
                    case 'p':
                    case 'N':
                    case 'n':
                        return true;
                    default:
                        return false;
                }
            case 'R':
            case 'r':
                switch (rozszerzony) {
                    case 'P':
                    case 'p':
                    case 'N':
                    case 'n':
                    case 'B':
                    case 'b':
                    case 'Q':
                    case 'q':
                        return true;
                    default:
                        return false;
                }
            case 'B':
            case 'b':
                switch (rozszerzony) {
                    case 'P':
                    case 'p':
                    case 'N':
                    case 'n':
                    case 'R':
                    case 'r':
                    case 'Q':
                    case 'q':
                        return true;
                    default:
                        return false;
                }
            case 'N':
            case 'n':
                switch (rozszerzony) {
                    case 'P':
                    case 'p':
                    case 'R':
                    case 'r':
                    case 'B':
                    case 'b':
                    case 'Q':
                    case 'q':
                        return true;
                    default:
                        return false;
                }
            case 'P':
            case 'p':
                switch (rozszerzony) {
                    case 'R':
                    case 'r':
                    case 'N':
                    case 'n':
                    case 'B':
                    case 'b':
                    case 'Q':
                    case 'q':
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    static boolean ruch(int[] lokalS, int[] lokalK, char symbol, char c, char[][] ustawienie, boolean czybiale, boolean przelotcan, int kol, char[][] nakladki) {
        boolean krolZ;
        byte alfa, beta, gama, dela;
        boolean bez, wynik = false;
        char z1, z2;
        switch (c) {
            case 'N':
            case 'n':
                wynik = ((((lokalS[0] - lokalK[0]) == 2) && ((lokalS[1] - lokalK[1]) == 1))
                        || (((lokalS[0] - lokalK[0]) == 2) && ((lokalS[1] - lokalK[1]) == -1))
                        || (((lokalS[0] - lokalK[0]) == -2) && ((lokalS[1] - lokalK[1]) == 1))
                        || (((lokalS[0] - lokalK[0]) == -2) && ((lokalS[1] - lokalK[1]) == -1))
                        || (((lokalS[0] - lokalK[0]) == 1) && ((lokalS[1] - lokalK[1]) == 2))
                        || (((lokalS[0] - lokalK[0]) == -1) && ((lokalS[1] - lokalK[1]) == 2))
                        || (((lokalS[0] - lokalK[0]) == 1) && ((lokalS[1] - lokalK[1]) == -2))
                        || (((lokalS[0] - lokalK[0]) == -1) && ((lokalS[1] - lokalK[1]) == -2)));
                if (wynik == true) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ = szach(ustawienie, czybiale) || szach(ustawienie, nakladki, czybiale) || szach(ustawienie, nakladki, czybiale);
                    wynik = !krolZ;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                }
                break;
            case 'B':
            case 'b':
                if (lokalK[1] - lokalS[1] == 1 && (lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1)
                        || lokalK[1] - lokalS[1] == -1 && ((lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1))) {

                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ = szach(ustawienie, czybiale) || szach(ustawienie, nakladki, czybiale);
                    wynik = krolZ != true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                    }
                } else {
                    if ((lokalS[0] - lokalK[0]) == (lokalS[1] - lokalK[1])) {
                        if (lokalS[1] > lokalK[1]) {
                            outerLoop:
                            for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] - i - 2);
                                beta = (byte) (lokalS[1] - i - 2);
                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        } else {
                            outerLoop:
                            for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] + i);
                                beta = (byte) (lokalS[1] + i);

                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k'))
                                        && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        if (lokalK[1] > lokalS[1]) {
                            outerLoop:
                            for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] - i - 2);
                                beta = (byte) (lokalS[1] + i);
                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (-1 * (lokalS[1] - lokalK[1]) == (lokalS[0] - lokalK[0]))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        } else {
                            outerLoop:
                            for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] + i);
                                beta = (byte) (lokalS[1] - i - 2);
                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * (-1))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        }

                    }
                }
                break;
            case 'P':
                alfa = (byte) (lokalS[1] - 1);
                beta = (byte) (lokalS[0] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                if (((alfa == 1 || alfa == 0) && (beta - gama == 0) && ((ustawienie[alfa + 1][beta] == ' ' && dela - alfa == 1) || (ustawienie[alfa + 1][beta] == ' ' && dela - alfa == 2 && ustawienie[alfa + 2][beta] == ' ')))
                        || (dela - alfa == 1 && beta - gama == 0 && (ustawienie[alfa + 1][beta] == ' '))) {
                    wynik = true;
                    if (symbol == 'K' && ((dela - alfa == 2) && ((nakladki[dela][gama - 1] == 'p' || nakladki[dela][gama + 1] == 'p')
                            || (ustawienie[dela][gama - 1] == 'p' || ustawienie[dela][gama + 1] == 'p')))) {
                        return false;
                    }
                } else {
                    if (dela - alfa == 1) {
                        if (beta - 1 >= 0 && gama - beta == -1) {
                            z1 = ustawienie[alfa + 1][beta - 1];
                            if (przelotcan == true && alfa - dela == -1 && alfa == 4 && beta == kol) {
                                wynik = ustawienie[alfa + 1][beta - 1] == ' ' && ustawienie[alfa][beta - 1] == 'p';
                            } else {
                                wynik = (z1 == 'q' || z1 == 'r' || z1 == 'b' || z1 == 'n' || z1 == 'p');
                            }
                        } else {
                            if (beta + 1 <= 7 && gama - beta == 1) {
                                z1 = ustawienie[alfa + 1][beta + 1];
                                if (przelotcan == true && alfa - dela == -1 && alfa == 4 && beta + 2 == kol) {
                                    wynik = ustawienie[alfa + 1][beta + 1] == ' ' && ustawienie[alfa][beta + 1] == 'p';
                                } else {
                                    wynik = (z1 == 'q' || z1 == 'r' || z1 == 'b' || z1 == 'n' || z1 == 'p');
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    } else {
                        wynik = false;
                    }
                }
                if (wynik == true && przelotcan == true && (symbol == 'P' && lokalS[1] == 5)) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    ustawienie[lokalS[1] - 1][lokalK[0] - 1] = ' ';
                    krolZ = szach(ustawienie, czybiale) || szach(ustawienie, nakladki, czybiale);
                    wynik = krolZ != true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = 'p';
                    }
                }
                if (wynik == true && przelotcan == false) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ = szach(ustawienie, czybiale) || szach(ustawienie, nakladki, czybiale);
                    wynik = krolZ != true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                }
                break;
            case 'p':
                alfa = (byte) (lokalS[1] - 1);
                beta = (byte) (lokalS[0] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                z1 = ustawienie[alfa - 1][beta];
                if (alfa - 2 > -1) {
                    z2 = ustawienie[alfa - 2][beta];
                } else {
                    z2 = ' ';
                }
                if (((alfa == 6) && (beta - gama == 0) && ((z1 == ' ' && dela - alfa == -1) || (z1 == z2 && dela - alfa == -2
                        && (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p'))))
                        || (dela - alfa == -1 && beta - gama == 0 && (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p'))) {
                    wynik = true;
                    if (symbol == 'k' && ((dela - alfa == 2) && ((nakladki[dela][gama - 1] == 'P' || nakladki[dela][gama + 1] == 'P')
                            || (ustawienie[dela][gama - 1] == 'P' || ustawienie[dela][gama + 1] == 'P')))) {
                        return false;
                    }
                } else {
                    if (dela - alfa == -1) {
                        if (beta - 1 >= 0 && gama - beta == -1) {
                            z1 = ustawienie[alfa - 1][beta - 1];
                            if (przelotcan == true && alfa - dela == 1 && alfa == 3 && beta == kol) {
                                wynik = (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p') && ustawienie[alfa][beta - 1] == 'P';
                            } else {
                                wynik = (z1 == 'Q' || z1 == 'R' || z1 == 'B' || z1 == 'N' || z1 == 'P');
                            }
                        } else {
                            if (beta + 1 <= 7 && gama - beta == 1) {
                                z1 = ustawienie[alfa - 1][beta + 1];
                                if (przelotcan == true && alfa - dela == 1 && alfa == 3 && beta + 2 == kol) {
                                    wynik = (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p') && ustawienie[alfa][beta + 1] == 'P';
                                } else {
                                    wynik = (z1 == 'Q' || z1 == 'R' || z1 == 'B' || z1 == 'N' || z1 == 'P');
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    } else {
                        wynik = false;
                    }
                }
                if (wynik == true && przelotcan == true && (symbol == 'p' && lokalS[1] == 4)) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    ustawienie[lokalS[1] - 1][lokalK[0] - 1] = ' ';
                    krolZ = szach(ustawienie, czybiale) || szach(ustawienie, nakladki, czybiale);
                    wynik = krolZ != true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = 'p';
                    }
                }
                if (wynik == true && przelotcan == false) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ = szach(ustawienie, czybiale) || szach(ustawienie, nakladki, czybiale);
                    wynik = krolZ != true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                }
                break;
            case 'R':
            case 'r':
                alfa = (byte) (lokalS[0] - 1);
                beta = (byte) (lokalS[1] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                if ((beta - dela == 0) && (alfa - gama == 1 || alfa - gama == -1)
                        || (alfa - gama == 0) && (beta - dela == 1 || beta - dela == -1)) {

                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ = szach(ustawienie, czybiale) || szach(ustawienie, nakladki, czybiale);
                    wynik = krolZ != true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                } else {
                    if (alfa == gama) {
                        if (beta > dela) {
                            for (byte i = 1; i < beta - dela; i++) {
                                if ((ustawienie[beta - i][alfa] == ' ' || (ustawienie[beta - i][alfa] != 'P' && ustawienie[beta - i][alfa] != 'p'
                                        && ustawienie[beta - i][alfa] != 'N' && ustawienie[beta - i][alfa] != 'n' && ustawienie[beta - i][alfa] != 'b' && ustawienie[beta - i][alfa] != 'B'
                                        && ustawienie[beta - i][alfa] != 'R' && ustawienie[beta - i][alfa] != 'r' && ustawienie[beta - i][alfa] != 'Q' && ustawienie[beta - i][alfa] != 'q'
                                        && ustawienie[beta - i][alfa] != 'K' && ustawienie[beta - i][alfa] != 'k'))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        } else {
                            for (byte i = 1; i < dela - beta; i++) {
                                if ((ustawienie[beta + i][alfa] == ' ' || (ustawienie[beta + i][alfa] != 'P' && ustawienie[beta + i][alfa] != 'p'
                                        && ustawienie[beta + i][alfa] != 'N' && ustawienie[beta + i][alfa] != 'n' && ustawienie[beta + i][alfa] != 'b' && ustawienie[beta + i][alfa] != 'B'
                                        && ustawienie[beta + i][alfa] != 'R' && ustawienie[beta + i][alfa] != 'r' && ustawienie[beta + i][alfa] != 'Q' && ustawienie[beta + i][alfa] != 'q'
                                        && ustawienie[beta + i][alfa] != 'K' && ustawienie[beta + i][alfa] != 'k'))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        if (beta == dela) {
                            if (alfa > gama) {
                                for (byte i = 1; i < alfa - gama; i++) {
                                    if (ustawienie[beta][alfa - i] == ' ' || (ustawienie[beta][alfa - i] != 'P' && ustawienie[beta][alfa - i] != 'p'
                                            && ustawienie[beta][alfa - i] != 'N' && ustawienie[beta][alfa - i] != 'n' && ustawienie[beta][alfa - i] != 'b' && ustawienie[beta][alfa - i] != 'B'
                                            && ustawienie[beta][alfa - i] != 'R' && ustawienie[beta][alfa - i] != 'r' && ustawienie[beta][alfa - i] != 'Q' && ustawienie[beta][alfa - i] != 'q'
                                            && ustawienie[beta][alfa - i] != 'K' && ustawienie[beta][alfa - i] != 'k')) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                for (byte i = 1; i < gama - alfa; i++) {
                                    if (ustawienie[beta][alfa + i] == ' ' || (ustawienie[beta][alfa + i] != 'P' && ustawienie[beta][alfa + i] != 'p'
                                            && ustawienie[beta][alfa + i] != 'N' && ustawienie[beta][alfa + i] != 'n' && ustawienie[beta][alfa + i] != 'b' && ustawienie[beta][alfa + i] != 'B'
                                            && ustawienie[beta][alfa + i] != 'R' && ustawienie[beta][alfa + i] != 'r' && ustawienie[beta][alfa + i] != 'Q' && ustawienie[beta][alfa + i] != 'q'
                                            && ustawienie[beta][alfa + i] != 'K' && ustawienie[beta][alfa + i] != 'k')) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            wynik = false;
                        }
                    }
                }
                break;
            case 'q':
            case 'Q':
                if (lokalS[0] == lokalK[0] || lokalS[1] == lokalK[1]) {
                    alfa = (byte) (lokalS[0] - 1);
                    beta = (byte) (lokalS[1] - 1);
                    gama = (byte) (lokalK[0] - 1);
                    dela = (byte) (lokalK[1] - 1);
                    if ((beta - dela == 0) && (alfa - gama == 1 || alfa - gama == -1)
                            || (alfa - gama == 0) && (beta - dela == 1 || beta - dela == -1)) {

                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                        krolZ = szach(ustawienie, czybiale) || szach(ustawienie, nakladki, czybiale);
                        wynik = krolZ != true;
                        if (wynik == false) {
                            ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                            ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        }
                    } else {
                        if (alfa == gama) {
                            if (beta > dela) {
                                for (byte i = 1; i < beta - dela; i++) {
                                    if ((ustawienie[beta - i][alfa] == ' ' || (ustawienie[beta - i][alfa] != 'P' && ustawienie[beta - i][alfa] != 'p'
                                            && ustawienie[beta - i][alfa] != 'N' && ustawienie[beta - i][alfa] != 'n' && ustawienie[beta - i][alfa] != 'b' && ustawienie[beta - i][alfa] != 'B'
                                            && ustawienie[beta - i][alfa] != 'R' && ustawienie[beta - i][alfa] != 'r' && ustawienie[beta - i][alfa] != 'Q' && ustawienie[beta - i][alfa] != 'q'
                                            && ustawienie[beta - i][alfa] != 'K' && ustawienie[beta - i][alfa] != 'k'))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                for (byte i = 1; i < dela - beta; i++) {
                                    if ((ustawienie[beta + i][alfa] == ' ' || (ustawienie[beta + i][alfa] != 'P' && ustawienie[beta + i][alfa] != 'p'
                                            && ustawienie[beta + i][alfa] != 'N' && ustawienie[beta + i][alfa] != 'n' && ustawienie[beta + i][alfa] != 'b' && ustawienie[beta + i][alfa] != 'B'
                                            && ustawienie[beta + i][alfa] != 'R' && ustawienie[beta + i][alfa] != 'r' && ustawienie[beta + i][alfa] != 'Q' && ustawienie[beta + i][alfa] != 'q'
                                            && ustawienie[beta + i][alfa] != 'K' && ustawienie[beta + i][alfa] != 'k'))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (beta == dela) {
                                if (alfa > gama) {
                                    for (byte i = 1; i < alfa - gama; i++) {
                                        if (ustawienie[beta][alfa - i] == ' ' || (ustawienie[beta][alfa - i] != 'P' && ustawienie[beta][alfa - i] != 'p'
                                                && ustawienie[beta][alfa - i] != 'N' && ustawienie[beta][alfa - i] != 'n' && ustawienie[beta][alfa - i] != 'b' && ustawienie[beta][alfa - i] != 'B'
                                                && ustawienie[beta][alfa - i] != 'R' && ustawienie[beta][alfa - i] != 'r' && ustawienie[beta][alfa - i] != 'Q' && ustawienie[beta][alfa - i] != 'q'
                                                && ustawienie[beta][alfa - i] != 'K' && ustawienie[beta][alfa - i] != 'k')) {
                                            wynik = true;
                                        } else {
                                            wynik = false;
                                            break;
                                        }
                                    }
                                } else {
                                    for (byte i = 1; i < gama - alfa; i++) {
                                        if (ustawienie[beta][alfa + i] == ' ' || (ustawienie[beta][alfa + i] != 'P' && ustawienie[beta][alfa + i] != 'p'
                                                && ustawienie[beta][alfa + i] != 'N' && ustawienie[beta][alfa + i] != 'n' && ustawienie[beta][alfa + i] != 'b' && ustawienie[beta][alfa + i] != 'B'
                                                && ustawienie[beta][alfa + i] != 'R' && ustawienie[beta][alfa + i] != 'r' && ustawienie[beta][alfa + i] != 'Q' && ustawienie[beta][alfa + i] != 'q'
                                                && ustawienie[beta][alfa + i] != 'K' && ustawienie[beta][alfa + i] != 'k')) {
                                            wynik = true;
                                        } else {
                                            wynik = false;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    }
                } else {
                    if (lokalK[1] - lokalS[1] == 1 && (lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1)
                            || lokalK[1] - lokalS[1] == -1 && ((lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1))) {

                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                        krolZ = szach(ustawienie, czybiale) || szach(ustawienie, nakladki, czybiale);
                        wynik = krolZ != true;
                        if (wynik == false) {
                            ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                            ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        }
                    } else {
                        if ((lokalS[0] - lokalK[0]) == (lokalS[1] - lokalK[1])) {
                            if (lokalS[1] > lokalK[1]) {
                                outerLoop:
                                for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] - i - 2);
                                    beta = (byte) (lokalS[1] - i - 2);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                outerLoop:
                                for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] + i);
                                    beta = (byte) (lokalS[1] + i);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (lokalK[1] > lokalS[1]) {
                                outerLoop:
                                for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] - i - 2);
                                    beta = (byte) (lokalS[1] + i);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (-1 * (lokalS[1] - lokalK[1]) == (lokalS[0] - lokalK[0]))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                outerLoop:
                                for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] + i);
                                    beta = (byte) (lokalS[1] - i - 2);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * (-1))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case 'k':
                if ((((lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == -1)))) {

                    symbol = ustawienie[lokalK[1] - 1][lokalK[0] - 1];
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = 'k';
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    bez = szach(ustawienie, czybiale);
                    wynik = bez != true;
                    if (wynik == true) {
                    } else {
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    }
                } else {
                    wynik = false;
                }
                break;
            case 'K':
                if ((((lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == -1)))) {

                    symbol = ustawienie[lokalK[1] - 1][lokalK[0] - 1];
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = 'K';
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    bez = szach(ustawienie, czybiale);
                    wynik = bez != true;
                    if (wynik == true) {
                    } else {
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    }
                } else {
                    wynik = false;
                }
                break;
        }
        return wynik;
    }

    static boolean szach(char[][] pozycja, char[][] nakladki, boolean czybiale) {
        byte krolX = 0, krolY = 0, alfa, beta;
        boolean zagrozenie = false;
        char[][] pomocnicze = new char[16][16];
        char[][] kamizelki = new char[16][16];
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                kamizelki[i + 4][j + 4] = nakladki[i][j];
                pomocnicze[i + 4][j + 4] = pozycja[i][j];
                if ((czybiale && pozycja[i][j] == 'K') || (!czybiale && pozycja[i][j] == 'k')) {
                    krolX = (byte) (i + 4);
                    krolY = (byte) (j + 4);
                }

            }
        }
        if ((czybiale && ((kamizelki[krolX + 2][krolY - 1] == 'n')
                || (kamizelki[krolX + 2][krolY + 1] == 'n')
                || (kamizelki[krolX - 2][krolY - 1] == 'n')
                || (kamizelki[krolX - 2][krolY + 1] == 'n')
                || (kamizelki[krolX + 1][krolY + 2] == 'n')
                || (kamizelki[krolX + 1][krolY - 2] == 'n')
                || (kamizelki[krolX - 1][krolY + 2] == 'n')
                || (kamizelki[krolX - 1][krolY - 2] == 'n')))
                || (!czybiale && ((kamizelki[krolX + 2][krolY - 1] == 'N')
                || (kamizelki[krolX + 2][krolY + 1] == 'N')
                || (kamizelki[krolX - 2][krolY - 1] == 'N')
                || (kamizelki[krolX - 2][krolY + 1] == 'N')
                || (kamizelki[krolX + 1][krolY + 2] == 'N')
                || (kamizelki[krolX + 1][krolY - 2] == 'N')
                || (kamizelki[krolX - 1][krolY + 2] == 'N')
                || (kamizelki[krolX - 1][krolY - 2] == 'N')))) {
            zagrozenie = true;
            return zagrozenie;
        }
        if (((czybiale && ((pomocnicze[krolX + 1][krolY + 1] == 'p' || kamizelki[krolX + 1][krolY + 1] == 'p') || (pomocnicze[krolX + 1][krolY - 1] == 'p' || kamizelki[krolX + 1][krolY - 1] == 'p')))
                || (!czybiale && ((pomocnicze[krolX - 1][krolY + 1] == 'P' || kamizelki[krolX - 1][krolY + 1] == 'P') || (pomocnicze[krolX - 1][krolY - 1] == 'P' || kamizelki[krolX - 1][krolY - 1] == 'P'))))) {
            zagrozenie = true;
            return zagrozenie;
        }
        if ((czybiale && ((pomocnicze[krolX + 1][krolY + 1] == 'k')
                || (pomocnicze[krolX + 1][krolY] == 'k')
                || (pomocnicze[krolX + 1][krolY - 1] == 'k')
                || (pomocnicze[krolX][krolY - 1] == 'k')
                || (pomocnicze[krolX - 1][krolY - 1] == 'k')
                || (pomocnicze[krolX - 1][krolY] == 'k')
                || (pomocnicze[krolX - 1][krolY + 1] == 'k')
                || (pomocnicze[krolX][krolY + 1] == 'k')))
                || (!czybiale && ((pomocnicze[krolX + 1][krolY + 1] == 'K')
                || (pomocnicze[krolX + 1][krolY] == 'K')
                || (pomocnicze[krolX + 1][krolY - 1] == 'K')
                || (pomocnicze[krolX][krolY - 1] == 'K')
                || (pomocnicze[krolX - 1][krolY - 1] == 'K')
                || (pomocnicze[krolX - 1][krolY] == 'K')
                || (pomocnicze[krolX - 1][krolY + 1] == 'K')
                || (pomocnicze[krolX][krolY + 1] == 'K')))) {
            zagrozenie = true;
            System.out.println("error K");
            return zagrozenie;
        }
        alfa = krolX;
        beta = krolY;
        byte licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else {
                    if (((czybiale && (kamizelki[i][beta + licz] == 'q' || kamizelki[i][beta + licz] == 'b'))
                            || (!czybiale && (kamizelki[i][beta + licz] == 'Q' || kamizelki[i][beta + licz] == 'B')))) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else {
                    if (((czybiale && (kamizelki[i][beta + licz] == 'q' || kamizelki[i][beta + licz] == 'b'))
                            || (!czybiale && (kamizelki[i][beta + licz] == 'Q' || kamizelki[i][beta + licz] == 'B')))) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 3) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else {
                    if (((czybiale && (kamizelki[i][beta + licz] == 'q' || kamizelki[i][beta + licz] == 'b'))
                            || (!czybiale && (kamizelki[i][beta + licz] == 'Q' || kamizelki[i][beta + licz] == 'B')))) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 3) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else {
                    if (((czybiale && (kamizelki[i][beta + licz] == 'q' || kamizelki[i][beta + licz] == 'b'))
                            || (!czybiale && (kamizelki[i][beta + licz] == 'Q' || kamizelki[i][beta + licz] == 'B')))) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }
        alfa = krolX;
        beta = krolY;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            if (pomocnicze[i][beta] == ' ') {
            } else {
                if (((czybiale && (kamizelki[i][beta] == 'q' || kamizelki[i][beta] == 'r'))
                        || (!czybiale && (kamizelki[i][beta] == 'Q' || kamizelki[i][beta] == 'R')))) {
                    return true;
                } else {
                    break;
                }
            }
        }
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            if (pomocnicze[i][beta] == ' ') {
            } else {
                if (((czybiale && (kamizelki[i][beta] == 'q' || kamizelki[i][beta] == 'r'))
                        || (!czybiale && (kamizelki[i][beta] == 'Q' || kamizelki[i][beta] == 'R')))) {
                    return true;
                } else {
                    break;
                }
            }
        }
        for (byte i = (byte) (beta + 1); i <= 11; i++) {
            if (pomocnicze[alfa][i] == ' ') {
            } else {
                if (((czybiale && (kamizelki[alfa][i] == 'q' || kamizelki[alfa][i] == 'r'))
                        || (!czybiale && (kamizelki[alfa][i] == 'Q' || kamizelki[alfa][i] == 'R')))) {
                    return true;
                } else {
                    break;
                }
            }
        }
        for (byte i = (byte) (beta - 1); i >= 4; i--) {
            if (pomocnicze[alfa][i] == ' ') {
                if (((czybiale && (kamizelki[alfa][i] == 'q' || kamizelki[alfa][i] == 'r'))
                        || (!czybiale && (kamizelki[alfa][i] == 'Q' || kamizelki[alfa][i] == 'R')))) {
                    return true;
                } else {
                    break;
                }
            }
        }
        return zagrozenie;

    }

    private RuchZagrozenie_kontrola() {
    }

    /**
     * Sprawdza prawidłowość ruchu pod kątem prawidłowości z zasadami
     *
     * @param lokalS współrzędne pola startu
     * @param lokalK współrzędne pola końca
     * @param symbol ruszana figura
     * @param ustawienie bieżąca pozycja
     * @param czybiale kto wykonuje ruch
     * @param przelotcan dostęp do bicia w przelocie
     * @param kol kolumna z biciem w przelocie
     * @return czy ruch może być wykonany
     */
    public static boolean ruch(int[] lokalS, int[] lokalK, char symbol,
            char[][] ustawienie, boolean czybiale, boolean przelotcan, int kol,boolean mgla) {
        boolean krolZ;
        byte alfa, beta, gama, dela;
        boolean bez, wynik = false;
        char z1, z2;
        switch (symbol) {
            case 'A':
            case 'a':
                wynik = ((((lokalS[0] - lokalK[0]) == 2) && ((lokalS[1] - lokalK[1]) == 1))
                        || (((lokalS[0] - lokalK[0]) == 2) && ((lokalS[1] - lokalK[1]) == -1))
                        || (((lokalS[0] - lokalK[0]) == -2) && ((lokalS[1] - lokalK[1]) == 1))
                        || (((lokalS[0] - lokalK[0]) == -2) && ((lokalS[1] - lokalK[1]) == -1))
                        || (((lokalS[0] - lokalK[0]) == 1) && ((lokalS[1] - lokalK[1]) == 2))
                        || (((lokalS[0] - lokalK[0]) == -1) && ((lokalS[1] - lokalK[1]) == 2))
                        || (((lokalS[0] - lokalK[0]) == 1) && ((lokalS[1] - lokalK[1]) == -2))
                        || (((lokalS[0] - lokalK[0]) == -1) && ((lokalS[1] - lokalK[1]) == -2)));
                if (wynik == true) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ = !mgla ?szach(ustawienie, czybiale):false;
                    wynik = !mgla ? krolZ != true : true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                }
                if (wynik == true) {
                    break;
                }
                if (lokalS[0] == lokalK[0] || lokalS[1] == lokalK[1]) {
                    alfa = (byte) (lokalS[0] - 1);
                    beta = (byte) (lokalS[1] - 1);
                    gama = (byte) (lokalK[0] - 1);
                    dela = (byte) (lokalK[1] - 1);
                    if ((beta - dela == 0) && (alfa - gama == 1 || alfa - gama == -1)
                            || (alfa - gama == 0) && (beta - dela == 1 || beta - dela == -1)) {

                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                        krolZ =!mgla ? szach(ustawienie, czybiale):false;
                        wynik = !mgla ? krolZ != true : true;
                        if (wynik == false) {
                            ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                            ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        }
                    } else {
                        if (alfa == gama) {
                            if (beta > dela) {
                                for (byte i = 1; i < beta - dela; i++) {
                                    if ((ustawienie[beta - i][alfa] == ' ' || (ustawienie[beta - i][alfa] != 'P' && ustawienie[beta - i][alfa] != 'p' && ustawienie[beta - i][alfa] != 'A' && ustawienie[beta - i][alfa] != 'a'
                                            && ustawienie[beta - i][alfa] != 'N' && ustawienie[beta - i][alfa] != 'n' && ustawienie[beta - i][alfa] != 'b' && ustawienie[beta - i][alfa] != 'B'
                                            && ustawienie[beta - i][alfa] != 'R' && ustawienie[beta - i][alfa] != 'r' && ustawienie[beta - i][alfa] != 'Q' && ustawienie[beta - i][alfa] != 'q'
                                            && ustawienie[beta - i][alfa] != 'K' && ustawienie[beta - i][alfa] != 'k'))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                for (byte i = 1; i < dela - beta; i++) {
                                    if ((ustawienie[beta + i][alfa] == ' ' || (ustawienie[beta + i][alfa] != 'P' && ustawienie[beta + i][alfa] != 'p' && ustawienie[beta - i][alfa] != 'A' && ustawienie[beta - i][alfa] != 'a'
                                            && ustawienie[beta + i][alfa] != 'N' && ustawienie[beta + i][alfa] != 'n' && ustawienie[beta + i][alfa] != 'b' && ustawienie[beta + i][alfa] != 'B'
                                            && ustawienie[beta + i][alfa] != 'R' && ustawienie[beta + i][alfa] != 'r' && ustawienie[beta + i][alfa] != 'Q' && ustawienie[beta + i][alfa] != 'q'
                                            && ustawienie[beta + i][alfa] != 'K' && ustawienie[beta + i][alfa] != 'k'))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (beta == dela) {
                                if (alfa > gama) {
                                    for (byte i = 1; i < alfa - gama; i++) {
                                        if (ustawienie[beta][alfa - i] == ' ' || (ustawienie[beta][alfa - i] != 'P' && ustawienie[beta][alfa - i] != 'p' && ustawienie[beta - i][alfa] != 'A' && ustawienie[beta - i][alfa] != 'a'
                                                && ustawienie[beta][alfa - i] != 'N' && ustawienie[beta][alfa - i] != 'n' && ustawienie[beta][alfa - i] != 'b' && ustawienie[beta][alfa - i] != 'B'
                                                && ustawienie[beta][alfa - i] != 'R' && ustawienie[beta][alfa - i] != 'r' && ustawienie[beta][alfa - i] != 'Q' && ustawienie[beta][alfa - i] != 'q'
                                                && ustawienie[beta][alfa - i] != 'K' && ustawienie[beta][alfa - i] != 'k')) {
                                            wynik = true;
                                        } else {
                                            wynik = false;
                                            break;
                                        }
                                    }
                                } else {
                                    for (byte i = 1; i < gama - alfa; i++) {
                                        if (ustawienie[beta][alfa + i] == ' ' || (ustawienie[beta][alfa + i] != 'P' && ustawienie[beta][alfa + i] != 'p' && ustawienie[beta - i][alfa] != 'A' && ustawienie[beta - i][alfa] != 'a'
                                                && ustawienie[beta][alfa + i] != 'N' && ustawienie[beta][alfa + i] != 'n' && ustawienie[beta][alfa + i] != 'b' && ustawienie[beta][alfa + i] != 'B'
                                                && ustawienie[beta][alfa + i] != 'R' && ustawienie[beta][alfa + i] != 'r' && ustawienie[beta][alfa + i] != 'Q' && ustawienie[beta][alfa + i] != 'q'
                                                && ustawienie[beta][alfa + i] != 'K' && ustawienie[beta][alfa + i] != 'k')) {
                                            wynik = true;
                                        } else {
                                            wynik = false;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    }
                } else {
                    if (lokalK[1] - lokalS[1] == 1 && (lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1)
                            || lokalK[1] - lokalS[1] == -1 && ((lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1))) {

                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                        krolZ =!mgla ? szach(ustawienie, czybiale):false;
                        wynik = !mgla ? krolZ != true : true;
                        if (wynik == false) {
                            ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                            ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        }
                    } else {
                        if ((lokalS[0] - lokalK[0]) == (lokalS[1] - lokalK[1])) {
                            if (lokalS[1] > lokalK[1]) {
                                outerLoop:
                                for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] - i - 2);
                                    beta = (byte) (lokalS[1] - i - 2);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p' && ustawienie[beta - i][alfa] != 'A' && ustawienie[beta - i][alfa] != 'a'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                outerLoop:
                                for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] + i);
                                    beta = (byte) (lokalS[1] + i);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p' && ustawienie[beta - i][alfa] != 'A' && ustawienie[beta - i][alfa] != 'a'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (lokalK[1] > lokalS[1]) {
                                outerLoop:
                                for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] - i - 2);
                                    beta = (byte) (lokalS[1] + i);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p' && ustawienie[beta - i][alfa] != 'A' && ustawienie[beta - i][alfa] != 'a'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (-1 * (lokalS[1] - lokalK[1]) == (lokalS[0] - lokalK[0]))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                outerLoop:
                                for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] + i);
                                    beta = (byte) (lokalS[1] - i - 2);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p' && ustawienie[beta - i][alfa] != 'A' && ustawienie[beta - i][alfa] != 'a'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * (-1))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case 'N':
            case 'n':
                wynik = ((((lokalS[0] - lokalK[0]) == 2) && ((lokalS[1] - lokalK[1]) == 1))
                        || (((lokalS[0] - lokalK[0]) == 2) && ((lokalS[1] - lokalK[1]) == -1))
                        || (((lokalS[0] - lokalK[0]) == -2) && ((lokalS[1] - lokalK[1]) == 1))
                        || (((lokalS[0] - lokalK[0]) == -2) && ((lokalS[1] - lokalK[1]) == -1))
                        || (((lokalS[0] - lokalK[0]) == 1) && ((lokalS[1] - lokalK[1]) == 2))
                        || (((lokalS[0] - lokalK[0]) == -1) && ((lokalS[1] - lokalK[1]) == 2))
                        || (((lokalS[0] - lokalK[0]) == 1) && ((lokalS[1] - lokalK[1]) == -2))
                        || (((lokalS[0] - lokalK[0]) == -1) && ((lokalS[1] - lokalK[1]) == -2)));
                if (wynik == true) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ =!mgla ? szach(ustawienie, czybiale):false;
                    wynik = !mgla ? krolZ != true : true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                }
                break;
            case 'B':
            case 'b':
                if (lokalK[1] - lokalS[1] == 1 && (lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1)
                        || lokalK[1] - lokalS[1] == -1 && ((lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1))) {

                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ =!mgla ? szach(ustawienie, czybiale):false;
                    wynik = !mgla ? krolZ != true : true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                    }
                } else {
                    if ((lokalS[0] - lokalK[0]) == (lokalS[1] - lokalK[1])) {
                        if (lokalS[1] > lokalK[1]) {
                            outerLoop:
                            for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] - i - 2);
                                beta = (byte) (lokalS[1] - i - 2);
                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        } else {
                            outerLoop:
                            for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] + i);
                                beta = (byte) (lokalS[1] + i);

                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k'))
                                        && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        if (lokalK[1] > lokalS[1]) {
                            outerLoop:
                            for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] - i - 2);
                                beta = (byte) (lokalS[1] + i);
                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (-1 * (lokalS[1] - lokalK[1]) == (lokalS[0] - lokalK[0]))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        } else {
                            outerLoop:
                            for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] + i);
                                beta = (byte) (lokalS[1] - i - 2);
                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * (-1))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        }

                    }
                }
                break;
            case 'P':
                alfa = (byte) (lokalS[1] - 1);
                beta = (byte) (lokalS[0] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                if (((alfa <= 1) && (beta - gama == 0) && ((ustawienie[alfa + 1][beta] == ' ' && dela - alfa == 1) || (ustawienie[alfa + 1][beta] == ' ' && dela - alfa == 2 && ustawienie[alfa + 2][beta] == ' ')))
                        || (dela - alfa == 1 && beta - gama == 0 && (ustawienie[alfa + 1][beta] == ' '))) {
                    wynik = true;
                } else {
                    if (dela - alfa == 1) {
                        if (beta - 1 >= 0 && gama - beta == -1) {
                            z1 = ustawienie[alfa + 1][beta - 1];
                            if (przelotcan == true && alfa - dela == -1 && alfa == 4 && beta == kol) {
                                wynik = ustawienie[alfa + 1][beta - 1] == ' ' && ustawienie[alfa][beta - 1] == 'p';
                            } else {
                                wynik = (z1 == 'q' || z1 == 'r' || z1 == 'b' || z1 == 'n' || z1 == 'p');
                            }
                        } else {
                            if (beta + 1 <= 7 && gama - beta == 1) {
                                z1 = ustawienie[alfa + 1][beta + 1];
                                if (przelotcan == true && alfa - dela == -1 && alfa == 4 && beta + 2 == kol) {
                                    wynik = ustawienie[alfa + 1][beta + 1] == ' ' && ustawienie[alfa][beta + 1] == 'p';
                                } else {
                                    wynik = (z1 == 'q' || z1 == 'r' || z1 == 'b' || z1 == 'n' || z1 == 'p');
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    } else {
                        wynik = false;
                    }
                }
                if (wynik == true && przelotcan == true && (symbol == 'P' && lokalS[1] == 5)) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    ustawienie[lokalS[1] - 1][lokalK[0] - 1] = ' ';
                    krolZ =!mgla ? szach(ustawienie, czybiale):false;
                    wynik = !mgla ? krolZ != true : true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = 'p';
                    }
                }
                if (wynik == true && przelotcan == false) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ =!mgla ? szach(ustawienie, czybiale):false;
                    wynik = !mgla ? krolZ != true : true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                }
                break;
            case 'p':
                alfa = (byte) (lokalS[1] - 1);
                beta = (byte) (lokalS[0] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                z1 = ustawienie[alfa - 1][beta];
                if (alfa - 2 > -1) {
                    z2 = ustawienie[alfa - 2][beta];
                } else {
                    z2 = ' ';
                }
                if (((alfa >= 6) && (beta - gama == 0) && ((z1 == ' ' && dela - alfa == -1) || (z1 == z2 && dela - alfa == -2
                        && (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p'))))
                        || (dela - alfa == -1 && beta - gama == 0 && (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p'))) {
                    wynik = true;
                } else {
                    if (dela - alfa == -1) {
                        if (beta - 1 >= 0 && gama - beta == -1) {
                            z1 = ustawienie[alfa - 1][beta - 1];
                            if (przelotcan == true && alfa - dela == 1 && alfa == 3 && beta == kol) {
                                wynik = (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p') && ustawienie[alfa][beta - 1] == 'P';
                            } else {
                                wynik = (z1 == 'Q' || z1 == 'R' || z1 == 'B' || z1 == 'N' || z1 == 'P');
                            }
                        } else {
                            if (beta + 1 <= 7 && gama - beta == 1) {
                                z1 = ustawienie[alfa - 1][beta + 1];
                                if (przelotcan == true && alfa - dela == 1 && alfa == 3 && beta + 2 == kol) {
                                    wynik = (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p') && ustawienie[alfa][beta + 1] == 'P';
                                } else {
                                    wynik = (z1 == 'Q' || z1 == 'R' || z1 == 'B' || z1 == 'N' || z1 == 'P');
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    } else {
                        wynik = false;
                    }
                }
                if (wynik == true && przelotcan == true && (symbol == 'p' && lokalS[1] == 4)) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    ustawienie[lokalS[1] - 1][lokalK[0] - 1] = ' ';
                    krolZ =!mgla ? szach(ustawienie, czybiale):false;
                    wynik = !mgla ? krolZ != true : true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = 'p';
                    }
                }
                if (wynik == true && przelotcan == false) {
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ =!mgla ? szach(ustawienie, czybiale):false;
                    wynik = !mgla ? krolZ != true : true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                }
                break;
            case 'R':
            case 'r':
                alfa = (byte) (lokalS[0] - 1);
                beta = (byte) (lokalS[1] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                if ((beta - dela == 0) && (alfa - gama == 1 || alfa - gama == -1)
                        || (alfa - gama == 0) && (beta - dela == 1 || beta - dela == -1)) {

                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ =!mgla ? szach(ustawienie, czybiale):false;
                    wynik = !mgla ? krolZ != true : true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                } else {
                    if (alfa == gama) {
                        if (beta > dela) {
                            for (byte i = 1; i < beta - dela; i++) {
                                if ((ustawienie[beta - i][alfa] == ' ' || (ustawienie[beta - i][alfa] != 'P' && ustawienie[beta - i][alfa] != 'p'
                                        && ustawienie[beta - i][alfa] != 'N' && ustawienie[beta - i][alfa] != 'n' && ustawienie[beta - i][alfa] != 'b' && ustawienie[beta - i][alfa] != 'B'
                                        && ustawienie[beta - i][alfa] != 'R' && ustawienie[beta - i][alfa] != 'r' && ustawienie[beta - i][alfa] != 'Q' && ustawienie[beta - i][alfa] != 'q'
                                        && ustawienie[beta - i][alfa] != 'K' && ustawienie[beta - i][alfa] != 'k'))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        } else {
                            for (byte i = 1; i < dela - beta; i++) {
                                if ((ustawienie[beta + i][alfa] == ' ' || (ustawienie[beta + i][alfa] != 'P' && ustawienie[beta + i][alfa] != 'p'
                                        && ustawienie[beta + i][alfa] != 'N' && ustawienie[beta + i][alfa] != 'n' && ustawienie[beta + i][alfa] != 'b' && ustawienie[beta + i][alfa] != 'B'
                                        && ustawienie[beta + i][alfa] != 'R' && ustawienie[beta + i][alfa] != 'r' && ustawienie[beta + i][alfa] != 'Q' && ustawienie[beta + i][alfa] != 'q'
                                        && ustawienie[beta + i][alfa] != 'K' && ustawienie[beta + i][alfa] != 'k'))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        if (beta == dela) {
                            if (alfa > gama) {
                                for (byte i = 1; i < alfa - gama; i++) {
                                    if (ustawienie[beta][alfa - i] == ' ' || (ustawienie[beta][alfa - i] != 'P' && ustawienie[beta][alfa - i] != 'p'
                                            && ustawienie[beta][alfa - i] != 'N' && ustawienie[beta][alfa - i] != 'n' && ustawienie[beta][alfa - i] != 'b' && ustawienie[beta][alfa - i] != 'B'
                                            && ustawienie[beta][alfa - i] != 'R' && ustawienie[beta][alfa - i] != 'r' && ustawienie[beta][alfa - i] != 'Q' && ustawienie[beta][alfa - i] != 'q'
                                            && ustawienie[beta][alfa - i] != 'K' && ustawienie[beta][alfa - i] != 'k')) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                for (byte i = 1; i < gama - alfa; i++) {
                                    if (ustawienie[beta][alfa + i] == ' ' || (ustawienie[beta][alfa + i] != 'P' && ustawienie[beta][alfa + i] != 'p'
                                            && ustawienie[beta][alfa + i] != 'N' && ustawienie[beta][alfa + i] != 'n' && ustawienie[beta][alfa + i] != 'b' && ustawienie[beta][alfa + i] != 'B'
                                            && ustawienie[beta][alfa + i] != 'R' && ustawienie[beta][alfa + i] != 'r' && ustawienie[beta][alfa + i] != 'Q' && ustawienie[beta][alfa + i] != 'q'
                                            && ustawienie[beta][alfa + i] != 'K' && ustawienie[beta][alfa + i] != 'k')) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            wynik = false;
                        }
                    }
                }
                break;
            case 'q':
            case 'Q':
                if (lokalS[0] == lokalK[0] || lokalS[1] == lokalK[1]) {
                    alfa = (byte) (lokalS[0] - 1);
                    beta = (byte) (lokalS[1] - 1);
                    gama = (byte) (lokalK[0] - 1);
                    dela = (byte) (lokalK[1] - 1);
                    if ((beta - dela == 0) && (alfa - gama == 1 || alfa - gama == -1)
                            || (alfa - gama == 0) && (beta - dela == 1 || beta - dela == -1)) {

                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                        krolZ =!mgla ? szach(ustawienie, czybiale):false;
                        wynik = !mgla ? krolZ != true : true;
                        if (wynik == false) {
                            ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                            ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        }
                    } else {
                        if (alfa == gama) {
                            if (beta > dela) {
                                for (byte i = 1; i < beta - dela; i++) {
                                    if ((ustawienie[beta - i][alfa] == ' ' || (ustawienie[beta - i][alfa] != 'P' && ustawienie[beta - i][alfa] != 'p'
                                            && ustawienie[beta - i][alfa] != 'N' && ustawienie[beta - i][alfa] != 'n' && ustawienie[beta - i][alfa] != 'b' && ustawienie[beta - i][alfa] != 'B'
                                            && ustawienie[beta - i][alfa] != 'R' && ustawienie[beta - i][alfa] != 'r' && ustawienie[beta - i][alfa] != 'Q' && ustawienie[beta - i][alfa] != 'q'
                                            && ustawienie[beta - i][alfa] != 'K' && ustawienie[beta - i][alfa] != 'k'))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                for (byte i = 1; i < dela - beta; i++) {
                                    if ((ustawienie[beta + i][alfa] == ' ' || (ustawienie[beta + i][alfa] != 'P' && ustawienie[beta + i][alfa] != 'p'
                                            && ustawienie[beta + i][alfa] != 'N' && ustawienie[beta + i][alfa] != 'n' && ustawienie[beta + i][alfa] != 'b' && ustawienie[beta + i][alfa] != 'B'
                                            && ustawienie[beta + i][alfa] != 'R' && ustawienie[beta + i][alfa] != 'r' && ustawienie[beta + i][alfa] != 'Q' && ustawienie[beta + i][alfa] != 'q'
                                            && ustawienie[beta + i][alfa] != 'K' && ustawienie[beta + i][alfa] != 'k'))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (beta == dela) {
                                if (alfa > gama) {
                                    for (byte i = 1; i < alfa - gama; i++) {
                                        if (ustawienie[beta][alfa - i] == ' ' || (ustawienie[beta][alfa - i] != 'P' && ustawienie[beta][alfa - i] != 'p'
                                                && ustawienie[beta][alfa - i] != 'N' && ustawienie[beta][alfa - i] != 'n' && ustawienie[beta][alfa - i] != 'b' && ustawienie[beta][alfa - i] != 'B'
                                                && ustawienie[beta][alfa - i] != 'R' && ustawienie[beta][alfa - i] != 'r' && ustawienie[beta][alfa - i] != 'Q' && ustawienie[beta][alfa - i] != 'q'
                                                && ustawienie[beta][alfa - i] != 'K' && ustawienie[beta][alfa - i] != 'k')) {
                                            wynik = true;
                                        } else {
                                            wynik = false;
                                            break;
                                        }
                                    }
                                } else {
                                    for (byte i = 1; i < gama - alfa; i++) {
                                        if (ustawienie[beta][alfa + i] == ' ' || (ustawienie[beta][alfa + i] != 'P' && ustawienie[beta][alfa + i] != 'p'
                                                && ustawienie[beta][alfa + i] != 'N' && ustawienie[beta][alfa + i] != 'n' && ustawienie[beta][alfa + i] != 'b' && ustawienie[beta][alfa + i] != 'B'
                                                && ustawienie[beta][alfa + i] != 'R' && ustawienie[beta][alfa + i] != 'r' && ustawienie[beta][alfa + i] != 'Q' && ustawienie[beta][alfa + i] != 'q'
                                                && ustawienie[beta][alfa + i] != 'K' && ustawienie[beta][alfa + i] != 'k')) {
                                            wynik = true;
                                        } else {
                                            wynik = false;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    }
                } else {
                    if (lokalK[1] - lokalS[1] == 1 && (lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1)
                            || lokalK[1] - lokalS[1] == -1 && ((lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1))) {

                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                        krolZ =!mgla ? szach(ustawienie, czybiale):false;
                        wynik = !mgla ? krolZ != true : true;
                        if (wynik == false) {
                            ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                            ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                        }
                    } else {
                        if ((lokalS[0] - lokalK[0]) == (lokalS[1] - lokalK[1])) {
                            if (lokalS[1] > lokalK[1]) {
                                outerLoop:
                                for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] - i - 2);
                                    beta = (byte) (lokalS[1] - i - 2);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                outerLoop:
                                for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] + i);
                                    beta = (byte) (lokalS[1] + i);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (lokalK[1] > lokalS[1]) {
                                outerLoop:
                                for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] - i - 2);
                                    beta = (byte) (lokalS[1] + i);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (-1 * (lokalS[1] - lokalK[1]) == (lokalS[0] - lokalK[0]))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                outerLoop:
                                for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] + i);
                                    beta = (byte) (lokalS[1] - i - 2);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * (-1))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case 'k':
                if ((((lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == -1)))) {

                    symbol = ustawienie[lokalK[1] - 1][lokalK[0] - 1];
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = 'k';
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    bez =!mgla ? szach(ustawienie, czybiale):false;
                    wynik = bez != true;
                    if (wynik == true) {
                    } else {
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    }
                } else {
                    wynik = false;
                }
                break;
            case 'K':
                if ((((lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == -1)))) {

                    symbol = ustawienie[lokalK[1] - 1][lokalK[0] - 1];
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = 'K';
                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    bez = !mgla ? szach(ustawienie, czybiale):false;
                    wynik = bez != true;
                    if (wynik == true) {
                    } else {
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    }
                } else {
                    wynik = false;
                }
                break;
        }
        return wynik;
    }

    /**
     * sprawdza, czy król jest zagrożony
     *
     * @param pozycja sprawdzana pozycja
     * @param czybiale sprawdzana strona
     * @return true jeśli król jest zagrożony
     */
    public static boolean szach(char[][] pozycja, boolean czybiale) {
        byte krolX = 0, krolY = 0, alfa, beta;
        boolean zagrozenie = false;
        char[][] pomocnicze = new char[16][16];
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                pomocnicze[i + 4][j + 4] = pozycja[i][j];
                if ((czybiale && pozycja[i][j] == 'K') || (!czybiale && pozycja[i][j] == 'k')) {
                    krolX = (byte) (i + 4);
                    krolY = (byte) (j + 4);
                }

            }
        }
        //  System.out.println(krolX+" "+krolY);
        if ((czybiale && ((pomocnicze[krolX + 2][krolY - 1] == 'n')
                || (pomocnicze[krolX + 2][krolY + 1] == 'n')
                || (pomocnicze[krolX - 2][krolY - 1] == 'n')
                || (pomocnicze[krolX - 2][krolY + 1] == 'n')
                || (pomocnicze[krolX + 1][krolY + 2] == 'n')
                || (pomocnicze[krolX + 1][krolY - 2] == 'n')
                || (pomocnicze[krolX - 1][krolY + 2] == 'n')
                || (pomocnicze[krolX - 1][krolY - 2] == 'n')
                || (pomocnicze[krolX + 2][krolY - 1] == 'a')
                || (pomocnicze[krolX + 2][krolY + 1] == 'a')
                || (pomocnicze[krolX - 2][krolY - 1] == 'a')
                || (pomocnicze[krolX - 2][krolY + 1] == 'a')
                || (pomocnicze[krolX + 1][krolY + 2] == 'a')
                || (pomocnicze[krolX + 1][krolY - 2] == 'a')
                || (pomocnicze[krolX - 1][krolY + 2] == 'a')
                || (pomocnicze[krolX - 1][krolY - 2] == 'a')))
                || (!czybiale && ((pomocnicze[krolX + 2][krolY - 1] == 'N')
                || (pomocnicze[krolX + 2][krolY + 1] == 'N')
                || (pomocnicze[krolX - 2][krolY - 1] == 'N')
                || (pomocnicze[krolX - 2][krolY + 1] == 'N')
                || (pomocnicze[krolX + 1][krolY + 2] == 'N')
                || (pomocnicze[krolX + 1][krolY - 2] == 'N')
                || (pomocnicze[krolX - 1][krolY + 2] == 'N')
                || (pomocnicze[krolX - 1][krolY - 2] == 'N')
                || (pomocnicze[krolX + 2][krolY - 1] == 'A')
                || (pomocnicze[krolX + 2][krolY + 1] == 'A')
                || (pomocnicze[krolX - 2][krolY - 1] == 'A')
                || (pomocnicze[krolX - 2][krolY + 1] == 'A')
                || (pomocnicze[krolX + 1][krolY + 2] == 'A')
                || (pomocnicze[krolX + 1][krolY - 2] == 'A')
                || (pomocnicze[krolX - 1][krolY + 2] == 'A')
                || (pomocnicze[krolX - 1][krolY - 2] == 'A')))) {
            zagrozenie = true;
            return zagrozenie;
        }
        if ((czybiale && ((pomocnicze[krolX + 1][krolY + 1] == 'p') || (pomocnicze[krolX + 1][krolY - 1] == 'p')))
                || (!czybiale && ((pomocnicze[krolX - 1][krolY + 1] == 'P') || (pomocnicze[krolX - 1][krolY - 1] == 'P')))) {
            zagrozenie = true;
            return zagrozenie;
        }
        if ((czybiale && ((pomocnicze[krolX + 1][krolY + 1] == 'k')
                || (pomocnicze[krolX + 1][krolY] == 'k')
                || (pomocnicze[krolX + 1][krolY - 1] == 'k')
                || (pomocnicze[krolX][krolY - 1] == 'k')
                || (pomocnicze[krolX - 1][krolY - 1] == 'k')
                || (pomocnicze[krolX - 1][krolY] == 'k')
                || (pomocnicze[krolX - 1][krolY + 1] == 'k')
                || (pomocnicze[krolX][krolY + 1] == 'k')))
                || (!czybiale && ((pomocnicze[krolX + 1][krolY + 1] == 'K')
                || (pomocnicze[krolX + 1][krolY] == 'K')
                || (pomocnicze[krolX + 1][krolY - 1] == 'K')
                || (pomocnicze[krolX][krolY - 1] == 'K')
                || (pomocnicze[krolX - 1][krolY - 1] == 'K')
                || (pomocnicze[krolX - 1][krolY] == 'K')
                || (pomocnicze[krolX - 1][krolY + 1] == 'K')
                || (pomocnicze[krolX][krolY + 1] == 'K')))) {
            zagrozenie = true;
            return zagrozenie;
        }
        alfa = krolX;
        beta = krolY;
        byte licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b' && pomocnicze[i][beta + licz] != 'a'))
                            || (!czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B' && pomocnicze[i][beta + licz] != 'A'))) {
                        zagrozenie = false;
                    } else {
                        zagrozenie = true;
                        return zagrozenie;
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b' && pomocnicze[i][beta + licz] != 'a'))
                            || (!czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B' && pomocnicze[i][beta + licz] != 'A'))) {
                        zagrozenie = false;
                    } else {
                        zagrozenie = true;
                        return zagrozenie;
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 3) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b' && pomocnicze[i][beta + licz] != 'a'))
                            || (!czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B' && pomocnicze[i][beta + licz] != 'A'))) {
                        zagrozenie = false;
                    } else {
                        zagrozenie = true;
                        return zagrozenie;
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 3) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == ' ')) {
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz] != 'q' && pomocnicze[i][beta + licz] != 'b' && pomocnicze[i][beta + licz] != 'a'))
                            || (!czybiale && (pomocnicze[i][beta + licz] != 'Q' && pomocnicze[i][beta + licz] != 'B' && pomocnicze[i][beta + licz] != 'A'))) {
                        zagrozenie = false;
                    } else {
                        zagrozenie = true;
                        return zagrozenie;
                    }
                    break;
                }
            }
        }
        alfa = krolX;
        beta = krolY;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            if (pomocnicze[i][beta] == ' ') {
            } else {
                if ((czybiale && (pomocnicze[i][beta] != 'q' && pomocnicze[i][beta] != 'r' && pomocnicze[i][beta] != 'a'))
                        || (!czybiale && (pomocnicze[i][beta] != 'Q' && pomocnicze[i][beta] != 'R' && pomocnicze[i][beta] != 'A'))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            if (pomocnicze[i][beta] == ' ') {
            } else {
                if ((czybiale && (pomocnicze[i][beta] != 'q' && pomocnicze[i][beta] != 'r' && pomocnicze[i][beta] != 'a'))
                        || (!czybiale && (pomocnicze[i][beta] != 'Q' && pomocnicze[i][beta] != 'R' && pomocnicze[i][beta] != 'A'))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        for (byte i = (byte) (beta + 1); i <= 11; i++) {
            if (pomocnicze[alfa][i] == ' ') {
            } else {
                if ((czybiale && (pomocnicze[alfa][i] != 'q' && pomocnicze[alfa][i] != 'r' && pomocnicze[alfa][i] != 'a'))
                        || (!czybiale && (pomocnicze[alfa][i] != 'Q' && pomocnicze[alfa][i] != 'R' && pomocnicze[alfa][i] != 'A'))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        for (byte i = (byte) (beta - 1); i >= 4; i--) {
            if (pomocnicze[alfa][i] == ' ') {
            } else {
                if ((czybiale && (pomocnicze[alfa][i] != 'q' && pomocnicze[alfa][i] != 'r' && pomocnicze[alfa][i] != 'a'))
                        || (!czybiale && (pomocnicze[alfa][i] != 'Q' && pomocnicze[alfa][i] != 'R' && pomocnicze[alfa][i] != 'A'))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        return zagrozenie;
    }

    public static boolean szach(SI_MIN_MAX_Alfa_Beta.figury[][] pozycja, boolean czybiale) {
        byte krolX = 0, krolY = 0, alfa, beta;
        boolean zagrozenie = false;
        SI_MIN_MAX_Alfa_Beta.figury[][] pomocnicze = new SI_MIN_MAX_Alfa_Beta.figury[16][16];
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                pomocnicze[i + 4][j + 4] = pozycja[i][j];
                if ((czybiale && pozycja[i][j] == SI_MIN_MAX_Alfa_Beta.figury.BKrol) || (!czybiale && pozycja[i][j] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)) {
                    krolX = (byte) (i + 4);
                    krolY = (byte) (j + 4);
                }

            }
        }
        //  System.out.println(krolX+" "+krolY);
        if ((czybiale && ((pomocnicze[krolX + 2][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)
                || (pomocnicze[krolX + 2][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)
                || (pomocnicze[krolX - 2][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)
                || (pomocnicze[krolX - 2][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)
                || (pomocnicze[krolX + 1][krolY + 2] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)
                || (pomocnicze[krolX + 1][krolY - 2] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)
                || (pomocnicze[krolX - 1][krolY + 2] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)
                || (pomocnicze[krolX - 1][krolY - 2] == SI_MIN_MAX_Alfa_Beta.figury.CSkoczek)))
                || (!czybiale && ((pomocnicze[krolX + 2][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek)
                || (pomocnicze[krolX + 2][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek)
                || (pomocnicze[krolX - 2][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek)
                || (pomocnicze[krolX - 2][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek)
                || (pomocnicze[krolX + 1][krolY + 2] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek)
                || (pomocnicze[krolX + 1][krolY - 2] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek)
                || (pomocnicze[krolX - 1][krolY + 2] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek)
                || (pomocnicze[krolX - 1][krolY - 2] == SI_MIN_MAX_Alfa_Beta.figury.BSkoczek)))) {
            zagrozenie = true;
            return zagrozenie;
        }
        if ((czybiale && ((pomocnicze[krolX + 1][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.CPion) || (pomocnicze[krolX + 1][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.CPion)))
                || (!czybiale && ((pomocnicze[krolX - 1][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.BPion) || (pomocnicze[krolX - 1][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.BPion)))) {
            zagrozenie = true;
            return zagrozenie;
        }
        if ((czybiale && ((pomocnicze[krolX + 1][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)
                || (pomocnicze[krolX + 1][krolY] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)
                || (pomocnicze[krolX + 1][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)
                || (pomocnicze[krolX][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)
                || (pomocnicze[krolX - 1][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)
                || (pomocnicze[krolX - 1][krolY] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)
                || (pomocnicze[krolX - 1][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)
                || (pomocnicze[krolX][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.CKrol)))
                || (!czybiale && ((pomocnicze[krolX + 1][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)
                || (pomocnicze[krolX + 1][krolY] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)
                || (pomocnicze[krolX + 1][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)
                || (pomocnicze[krolX][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)
                || (pomocnicze[krolX - 1][krolY - 1] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)
                || (pomocnicze[krolX - 1][krolY] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)
                || (pomocnicze[krolX - 1][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)
                || (pomocnicze[krolX][krolY + 1] == SI_MIN_MAX_Alfa_Beta.figury.BKrol)))) {
            zagrozenie = true;
            return zagrozenie;
        }
        alfa = krolX;
        beta = krolY;
        byte licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == SI_MIN_MAX_Alfa_Beta.figury.pustka)) {
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec))
                            || (!czybiale && (pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec))) {
                        zagrozenie = false;
                    } else {
                        zagrozenie = true;
                        return zagrozenie;
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            licz = (byte) (licz + 1);
            if (beta + licz >= 12) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == SI_MIN_MAX_Alfa_Beta.figury.pustka)) {
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec))
                            || (!czybiale && (pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec))) {
                        zagrozenie = false;
                    } else {
                        zagrozenie = true;
                        return zagrozenie;
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 3) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == SI_MIN_MAX_Alfa_Beta.figury.pustka)) {
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec))
                            || (!czybiale && (pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec))) {
                        zagrozenie = false;
                    } else {
                        zagrozenie = true;
                        return zagrozenie;
                    }
                    break;
                }
            }
        }
        licz = 0;
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            licz = (byte) (licz - 1);
            if (beta + licz <= 3) {
                break;
            } else {
                if ((pomocnicze[i][beta + licz] == SI_MIN_MAX_Alfa_Beta.figury.pustka)) {
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.CGoniec))
                            || (!czybiale && (pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && pomocnicze[i][beta + licz] != SI_MIN_MAX_Alfa_Beta.figury.BGoniec))) {
                        zagrozenie = false;
                    } else {
                        zagrozenie = true;
                        return zagrozenie;
                    }
                    break;
                }
            }
        }
        alfa = krolX;
        beta = krolY;
        for (byte i = (byte) (alfa + 1); i <= 11; i++) {
            if (pomocnicze[i][beta] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
            } else {
                if ((czybiale && (pomocnicze[i][beta] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && pomocnicze[i][beta] != SI_MIN_MAX_Alfa_Beta.figury.CWieza))
                        || (!czybiale && (pomocnicze[i][beta] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && pomocnicze[i][beta] != SI_MIN_MAX_Alfa_Beta.figury.BWieza))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            if (pomocnicze[i][beta] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
            } else {
                if ((czybiale && (pomocnicze[i][beta] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && pomocnicze[i][beta] != SI_MIN_MAX_Alfa_Beta.figury.CWieza))
                        || (!czybiale && (pomocnicze[i][beta] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && pomocnicze[i][beta] != SI_MIN_MAX_Alfa_Beta.figury.BWieza))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        for (byte i = (byte) (beta + 1); i <= 11; i++) {
            if (pomocnicze[alfa][i] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
            } else {
                if ((czybiale && (pomocnicze[alfa][i] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && pomocnicze[alfa][i] != SI_MIN_MAX_Alfa_Beta.figury.CWieza))
                        || (!czybiale && (pomocnicze[alfa][i] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && pomocnicze[alfa][i] != SI_MIN_MAX_Alfa_Beta.figury.BWieza))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        for (byte i = (byte) (beta - 1); i >= 4; i--) {
            if (pomocnicze[alfa][i] == SI_MIN_MAX_Alfa_Beta.figury.pustka) {
            } else {
                if ((czybiale && (pomocnicze[alfa][i] != SI_MIN_MAX_Alfa_Beta.figury.CHetman && pomocnicze[alfa][i] != SI_MIN_MAX_Alfa_Beta.figury.CWieza))
                        || (!czybiale && (pomocnicze[alfa][i] != SI_MIN_MAX_Alfa_Beta.figury.BHetman && pomocnicze[alfa][i] != SI_MIN_MAX_Alfa_Beta.figury.BWieza))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        return zagrozenie;
    }
    public static boolean antyruch(int[] lokalS, int[] lokalK, char symbol,
            char[][] ustawienie, boolean czybiale, boolean przelotcan, int kol) {
        boolean krolZ;
        byte alfa, beta, gama, dela;
        boolean bez, wynik = false;
        char z1, z2;
        switch (symbol) {
            case 'N':
            case 'n':
                wynik = ((((lokalS[0] - lokalK[0]) == 2) && ((lokalS[1] - lokalK[1]) == 1))
                        || (((lokalS[0] - lokalK[0]) == 2) && ((lokalS[1] - lokalK[1]) == -1))
                        || (((lokalS[0] - lokalK[0]) == -2) && ((lokalS[1] - lokalK[1]) == 1))
                        || (((lokalS[0] - lokalK[0]) == -2) && ((lokalS[1] - lokalK[1]) == -1))
                        || (((lokalS[0] - lokalK[0]) == 1) && ((lokalS[1] - lokalK[1]) == 2))
                        || (((lokalS[0] - lokalK[0]) == -1) && ((lokalS[1] - lokalK[1]) == 2))
                        || (((lokalS[0] - lokalK[0]) == 1) && ((lokalS[1] - lokalK[1]) == -2))
                        || (((lokalS[0] - lokalK[0]) == -1) && ((lokalS[1] - lokalK[1]) == -2)));
                if (wynik == true) {
                    return true;
                }
                break;
            case 'B':
            case 'b':
                if (lokalK[1] - lokalS[1] == 1 && (lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1)
                        || lokalK[1] - lokalS[1] == -1 && ((lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1))) {
                    return true;
                } else {
                    if ((lokalS[0] - lokalK[0]) == (lokalS[1] - lokalK[1])) {
                        if (lokalS[1] > lokalK[1]) {
                            outerLoop:
                            for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] - i - 2);
                                beta = (byte) (lokalS[1] - i - 2);
                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        } else {
                            outerLoop:
                            for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] + i);
                                beta = (byte) (lokalS[1] + i);

                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k'))
                                        && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        if (lokalK[1] > lokalS[1]) {
                            outerLoop:
                            for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] - i - 2);
                                beta = (byte) (lokalS[1] + i);
                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (-1 * (lokalS[1] - lokalK[1]) == (lokalS[0] - lokalK[0]))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        } else {
                            outerLoop:
                            for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] + i);
                                beta = (byte) (lokalS[1] - i - 2);
                                if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                        && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                        && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                        && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * (-1))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        }

                    }
                }
                break;
            case 'P':
                alfa = (byte) (lokalS[1] - 1);
                beta = (byte) (lokalS[0] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                if (((alfa <= 1) && (beta - gama == 0) && ((ustawienie[alfa + 1][beta] == ' ' && dela - alfa == 1) || (ustawienie[alfa + 1][beta] == ' ' && dela - alfa == 2 && ustawienie[alfa + 2][beta] == ' ')))
                        || (dela - alfa == 1 && beta - gama == 0 && (ustawienie[alfa + 1][beta] == ' '))) {
                    wynik = true;
                } else {
                    if (dela - alfa == 1) {
                        if (beta - 1 >= 0 && gama - beta == -1) {
                            z1 = ustawienie[alfa + 1][beta - 1];
                            if (przelotcan == true && alfa - dela == -1 && alfa == 4 && beta == kol) {
                                wynik = ustawienie[alfa + 1][beta - 1] == ' ' && ustawienie[alfa][beta - 1] == 'p';
                            } else {
                                wynik = (z1 == 'q' || z1 == 'r' || z1 == 'b' || z1 == 'n' || z1 == 'p' || z1 == 'k');
                            }
                        } else {
                            if (beta + 1 <= 7 && gama - beta == 1) {
                                z1 = ustawienie[alfa + 1][beta + 1];
                                if (przelotcan == true && alfa - dela == -1 && alfa == 4 && beta + 2 == kol) {
                                    wynik = ustawienie[alfa + 1][beta + 1] == ' ' && ustawienie[alfa][beta + 1] == 'p';
                                } else {
                                    wynik = (z1 == 'q' || z1 == 'r' || z1 == 'b' || z1 == 'n' || z1 == 'p' || z1 == 'k');
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    } else {
                        wynik = false;
                    }
                }
                if (wynik == true && przelotcan == true && (symbol == 'P' && lokalS[1] == 5)) {
                    wynik=true;
                }
                if (wynik == true && przelotcan == false) {
                    wynik=true;
                }
                break;
            case 'p':
                alfa = (byte) (lokalS[1] - 1);
                beta = (byte) (lokalS[0] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                z1 = ustawienie[alfa - 1][beta];
                if (alfa - 2 > -1) {
                    z2 = ustawienie[alfa - 2][beta];
                } else {
                    z2 = ' ';
                }
                if (((alfa >= 6) && (beta - gama == 0) && ((z1 == ' ' && dela - alfa == -1) || (z1 == z2 && dela - alfa == -2
                        && (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p'))))
                        || (dela - alfa == -1 && beta - gama == 0 && (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p'))) {
                    wynik = true;
                } else {
                    if (dela - alfa == -1) {
                        if (beta - 1 >= 0 && gama - beta == -1) {
                            z1 = ustawienie[alfa - 1][beta - 1];
                            if (przelotcan == true && alfa - dela == 1 && alfa == 3 && beta == kol) {
                                wynik = (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p') && ustawienie[alfa][beta - 1] == 'P';
                            } else {
                                wynik = (z1 == 'Q' || z1 == 'R' || z1 == 'B' || z1 == 'N' || z1 == 'P' || z1 == 'K');
                            }
                        } else {
                            if (beta + 1 <= 7 && gama - beta == 1) {
                                z1 = ustawienie[alfa - 1][beta + 1];
                                if (przelotcan == true && alfa - dela == 1 && alfa == 3 && beta + 2 == kol) {
                                    wynik = (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p') && ustawienie[alfa][beta + 1] == 'P';
                                } else {
                                    wynik = (z1 == 'Q' || z1 == 'R' || z1 == 'B' || z1 == 'N' || z1 == 'P' || z1 == 'K');
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    } else {
                        wynik = false;
                    }
                }
                if (wynik == true && przelotcan == true && (symbol == 'p' && lokalS[1] == 4)) {
                    wynik=true;
                }
                if (wynik == true && przelotcan == false) {
                    wynik=true;
                }
                break;
            case 'R':
            case 'r':
                alfa = (byte) (lokalS[0] - 1);
                beta = (byte) (lokalS[1] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                if ((beta - dela == 0) && (alfa - gama == 1 || alfa - gama == -1)
                        || (alfa - gama == 0) && (beta - dela == 1 || beta - dela == -1)) {

                    ustawienie[lokalS[1] - 1][lokalS[0] - 1] = ' ';
                    ustawienie[lokalK[1] - 1][lokalK[0] - 1] = symbol;
                    krolZ = szach(ustawienie, czybiale);
                    wynik = krolZ != true;
                    if (wynik == false) {
                        ustawienie[lokalS[1] - 1][lokalS[0] - 1] = symbol;
                        ustawienie[lokalK[1] - 1][lokalK[0] - 1] = ' ';
                    }
                } else {
                    if (alfa == gama) {
                        if (beta > dela) {
                            for (byte i = 1; i < beta - dela; i++) {
                                if ((ustawienie[beta - i][alfa] == ' ' || (ustawienie[beta - i][alfa] != 'P' && ustawienie[beta - i][alfa] != 'p'
                                        && ustawienie[beta - i][alfa] != 'N' && ustawienie[beta - i][alfa] != 'n' && ustawienie[beta - i][alfa] != 'b' && ustawienie[beta - i][alfa] != 'B'
                                        && ustawienie[beta - i][alfa] != 'R' && ustawienie[beta - i][alfa] != 'r' && ustawienie[beta - i][alfa] != 'Q' && ustawienie[beta - i][alfa] != 'q'
                                        && ustawienie[beta - i][alfa] != 'K' && ustawienie[beta - i][alfa] != 'k'))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        } else {
                            for (byte i = 1; i < dela - beta; i++) {
                                if ((ustawienie[beta + i][alfa] == ' ' || (ustawienie[beta + i][alfa] != 'P' && ustawienie[beta + i][alfa] != 'p'
                                        && ustawienie[beta + i][alfa] != 'N' && ustawienie[beta + i][alfa] != 'n' && ustawienie[beta + i][alfa] != 'b' && ustawienie[beta + i][alfa] != 'B'
                                        && ustawienie[beta + i][alfa] != 'R' && ustawienie[beta + i][alfa] != 'r' && ustawienie[beta + i][alfa] != 'Q' && ustawienie[beta + i][alfa] != 'q'
                                        && ustawienie[beta + i][alfa] != 'K' && ustawienie[beta + i][alfa] != 'k'))) {
                                    wynik = true;
                                } else {
                                    wynik = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        if (beta == dela) {
                            if (alfa > gama) {
                                for (byte i = 1; i < alfa - gama; i++) {
                                    if (ustawienie[beta][alfa - i] == ' ' || (ustawienie[beta][alfa - i] != 'P' && ustawienie[beta][alfa - i] != 'p'
                                            && ustawienie[beta][alfa - i] != 'N' && ustawienie[beta][alfa - i] != 'n' && ustawienie[beta][alfa - i] != 'b' && ustawienie[beta][alfa - i] != 'B'
                                            && ustawienie[beta][alfa - i] != 'R' && ustawienie[beta][alfa - i] != 'r' && ustawienie[beta][alfa - i] != 'Q' && ustawienie[beta][alfa - i] != 'q'
                                            && ustawienie[beta][alfa - i] != 'K' && ustawienie[beta][alfa - i] != 'k')) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                for (byte i = 1; i < gama - alfa; i++) {
                                    if (ustawienie[beta][alfa + i] == ' ' || (ustawienie[beta][alfa + i] != 'P' && ustawienie[beta][alfa + i] != 'p'
                                            && ustawienie[beta][alfa + i] != 'N' && ustawienie[beta][alfa + i] != 'n' && ustawienie[beta][alfa + i] != 'b' && ustawienie[beta][alfa + i] != 'B'
                                            && ustawienie[beta][alfa + i] != 'R' && ustawienie[beta][alfa + i] != 'r' && ustawienie[beta][alfa + i] != 'Q' && ustawienie[beta][alfa + i] != 'q'
                                            && ustawienie[beta][alfa + i] != 'K' && ustawienie[beta][alfa + i] != 'k')) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            wynik = false;
                        }
                    }
                }
                break;
            case 'q':
            case 'Q':
                if (lokalS[0] == lokalK[0] || lokalS[1] == lokalK[1]) {
                    alfa = (byte) (lokalS[0] - 1);
                    beta = (byte) (lokalS[1] - 1);
                    gama = (byte) (lokalK[0] - 1);
                    dela = (byte) (lokalK[1] - 1);
                    if ((beta - dela == 0) && (alfa - gama == 1 || alfa - gama == -1)
                            || (alfa - gama == 0) && (beta - dela == 1 || beta - dela == -1)) {
                        wynik = true;
                    } else {
                        if (alfa == gama) {
                            if (beta > dela) {
                                for (byte i = 1; i < beta - dela; i++) {
                                    if ((ustawienie[beta - i][alfa] == ' ' || (ustawienie[beta - i][alfa] != 'P' && ustawienie[beta - i][alfa] != 'p'
                                            && ustawienie[beta - i][alfa] != 'N' && ustawienie[beta - i][alfa] != 'n' && ustawienie[beta - i][alfa] != 'b' && ustawienie[beta - i][alfa] != 'B'
                                            && ustawienie[beta - i][alfa] != 'R' && ustawienie[beta - i][alfa] != 'r' && ustawienie[beta - i][alfa] != 'Q' && ustawienie[beta - i][alfa] != 'q'
                                            && ustawienie[beta - i][alfa] != 'K' && ustawienie[beta - i][alfa] != 'k'))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                for (byte i = 1; i < dela - beta; i++) {
                                    if ((ustawienie[beta + i][alfa] == ' ' || (ustawienie[beta + i][alfa] != 'P' && ustawienie[beta + i][alfa] != 'p'
                                            && ustawienie[beta + i][alfa] != 'N' && ustawienie[beta + i][alfa] != 'n' && ustawienie[beta + i][alfa] != 'b' && ustawienie[beta + i][alfa] != 'B'
                                            && ustawienie[beta + i][alfa] != 'R' && ustawienie[beta + i][alfa] != 'r' && ustawienie[beta + i][alfa] != 'Q' && ustawienie[beta + i][alfa] != 'q'
                                            && ustawienie[beta + i][alfa] != 'K' && ustawienie[beta + i][alfa] != 'k'))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (beta == dela) {
                                if (alfa > gama) {
                                    for (byte i = 1; i < alfa - gama; i++) {
                                        if (ustawienie[beta][alfa - i] == ' ' || (ustawienie[beta][alfa - i] != 'P' && ustawienie[beta][alfa - i] != 'p'
                                                && ustawienie[beta][alfa - i] != 'N' && ustawienie[beta][alfa - i] != 'n' && ustawienie[beta][alfa - i] != 'b' && ustawienie[beta][alfa - i] != 'B'
                                                && ustawienie[beta][alfa - i] != 'R' && ustawienie[beta][alfa - i] != 'r' && ustawienie[beta][alfa - i] != 'Q' && ustawienie[beta][alfa - i] != 'q'
                                                && ustawienie[beta][alfa - i] != 'K' && ustawienie[beta][alfa - i] != 'k')) {
                                            wynik = true;
                                        } else {
                                            wynik = false;
                                            break;
                                        }
                                    }
                                } else {
                                    for (byte i = 1; i < gama - alfa; i++) {
                                        if (ustawienie[beta][alfa + i] == ' ' || (ustawienie[beta][alfa + i] != 'P' && ustawienie[beta][alfa + i] != 'p'
                                                && ustawienie[beta][alfa + i] != 'N' && ustawienie[beta][alfa + i] != 'n' && ustawienie[beta][alfa + i] != 'b' && ustawienie[beta][alfa + i] != 'B'
                                                && ustawienie[beta][alfa + i] != 'R' && ustawienie[beta][alfa + i] != 'r' && ustawienie[beta][alfa + i] != 'Q' && ustawienie[beta][alfa + i] != 'q'
                                                && ustawienie[beta][alfa + i] != 'K' && ustawienie[beta][alfa + i] != 'k')) {
                                            wynik = true;
                                        } else {
                                            wynik = false;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                wynik = false;
                            }
                        }
                    }
                } else {
                    if (lokalK[1] - lokalS[1] == 1 && (lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1)
                            || lokalK[1] - lokalS[1] == -1 && ((lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1))) {
                        wynik = true;
                    } else {
                        if ((lokalS[0] - lokalK[0]) == (lokalS[1] - lokalK[1])) {
                            if (lokalS[1] > lokalK[1]) {
                                outerLoop:
                                for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] - i - 2);
                                    beta = (byte) (lokalS[1] - i - 2);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                outerLoop:
                                for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] + i);
                                    beta = (byte) (lokalS[1] + i);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (lokalK[1] > lokalS[1]) {
                                outerLoop:
                                for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] - i - 2);
                                    beta = (byte) (lokalS[1] + i);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (-1 * (lokalS[1] - lokalK[1]) == (lokalS[0] - lokalK[0]))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            } else {
                                outerLoop:
                                for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] + i);
                                    beta = (byte) (lokalS[1] - i - 2);
                                    if ((ustawienie[beta][alfa] == ' ' || (ustawienie[beta][alfa] != 'P' && ustawienie[beta][alfa] != 'p'
                                            && ustawienie[beta][alfa] != 'N' && ustawienie[beta][alfa] != 'n' && ustawienie[beta][alfa] != 'b' && ustawienie[beta][alfa] != 'B'
                                            && ustawienie[beta][alfa] != 'R' && ustawienie[beta][alfa] != 'r' && ustawienie[beta][alfa] != 'Q' && ustawienie[beta][alfa] != 'q'
                                            && ustawienie[beta][alfa] != 'K' && ustawienie[beta][alfa] != 'k')) && (lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * (-1))) {
                                        wynik = true;
                                    } else {
                                        wynik = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case 'k':
                if ((((lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == -1)))) {

                    wynik = true;
                } else {
                    wynik = false;
                }
                break;
            case 'K':
                if ((((lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == -1)))) {

                    wynik = true;
                } else {
                    wynik = false;
                }
                break;
        }
        return wynik;
    }

}
