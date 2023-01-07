/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

/**
 *
 * @author Patryk
 */
class Ruch_pokoj {

    public static boolean ruch(int[] lokalS, int[] lokalK, char symbol,
            char[][][] ustawienie, boolean czybiale, boolean przelotcan, int kol, char tulasy) {
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
                        || (((lokalS[0] - lokalK[0]) == -1) && ((lokalS[1] - lokalK[1]) == -2)))
                        && ((tulasy == ' '
                        && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                        || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                        || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                        || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                        && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' '));
                break;
            case 'B':
            case 'b':
                if (lokalK[1] - lokalS[1] == 1 && (lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1)
                        || lokalK[1] - lokalS[1] == -1 && ((lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1))) {

                    wynik = (tulasy == ' '
                            && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                            || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                            && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ');

                } else {
                    if ((lokalS[0] - lokalK[0]) == (lokalS[1] - lokalK[1])) {
                        if (lokalS[1] > lokalK[1]) {
                            outerLoop:
                            for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] - i - 2);
                                beta = (byte) (lokalS[1] - i - 2);
                                if ((lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])
                                        && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' ')) {
                                    wynik = ((i == lokalS[1] - lokalK[1] - 2)
                                            && ((tulasy == ' '
                                            && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                            || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                            && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                            || ((i < lokalS[1] - lokalK[1] - 2) && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' '));
                                    if (wynik == false) {
                                        break;
                                    }
                                } else {

                                    wynik = false;

                                    System.out.println(wynik);
                                    break;
                                }
                            }
                        } else {
                            outerLoop:
                            for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                alfa = (byte) (lokalS[0] + i);
                                beta = (byte) (lokalS[1] + i);

                                if ((lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])
                                        && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' ')) {
                                    wynik = ((i == lokalK[1] - lokalS[1] - 2)
                                            && ((tulasy == ' '
                                            && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                            || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                            && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                            || ((i < lokalK[1] - lokalS[1] - 2) && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' '));
                                    if (wynik == false) {
                                        break;
                                    }
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
                                if ((lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * -1)
                                        && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' ')) {
                                    wynik = ((i == lokalK[1] - lokalS[1] - 2)
                                            && ((tulasy == ' '
                                            && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                            || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                            && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                            || ((i < lokalK[1] - lokalS[1] - 2) && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' '));
                                    if (wynik == false) {
                                        break;
                                    }
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
                                if ((lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * -1)
                                        && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' ')) {
                                    wynik = ((i == lokalS[1] - lokalK[1] - 2)
                                            && ((tulasy == ' '
                                            && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                            || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                            && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                            || ((i < lokalS[1] - lokalK[1] - 2) && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' '));
                                    if (wynik == false) {
                                        break;
                                    }
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
                if (((alfa == 1 || alfa == 0) && (beta - gama == 0) && (((ustawienie[alfa + 1][beta][0] == ' ' && ustawienie[alfa + 1][beta][1] == ' ') && dela - alfa == 1) || ((ustawienie[alfa + 1][beta][0] == ' ' && ustawienie[alfa + 1][beta][1] == ' ') && (dela - alfa == 2) && (ustawienie[alfa + 2][beta][1] == ' ' && ustawienie[alfa + 2][beta][0] == ' '))))
                        || (dela - alfa == 1 && beta - gama == 0 && ((ustawienie[alfa + 1][beta][0] == ' ' && ustawienie[alfa + 1][beta][1] == ' ')))) {
                    wynik = true;
                } else {
                    if (dela - alfa == 1) {
                        if (beta - 1 >= 0 && gama - beta == -1) {
                            z1 = czybiale == true ? ustawienie[alfa + 1][beta - 1][1] : ustawienie[alfa + 1][beta - 1][0];
                            if (przelotcan == true && alfa - dela == -1 && alfa == 4 && beta == kol) {
                                wynik = ustawienie[alfa + 1][beta - 1][0] == ' ' && ustawienie[alfa + 1][beta - 1][1] == ' ' && ustawienie[alfa][beta - 1][1] == 'p';
                            } else {

                                wynik = (z1 == 'q' || z1 == 'r' || z1 == 'b' || z1 == 'n' || z1 == 'p' || z1 == 'k') && tulasy == ' ';
                            }
                        } else {
                            if (beta + 1 <= 7 && gama - beta == 1) {
                                z1 = czybiale == true ? ustawienie[alfa + 1][beta + 1][1] : ustawienie[alfa + 1][beta + 1][0];
                                if (przelotcan == true && alfa - dela == -1 && alfa == 4 && beta + 2 == kol) {
                                    wynik = ustawienie[alfa + 1][beta + 1][1] == ' ' && ustawienie[alfa + 1][beta + 1][0] == ' ' && ustawienie[alfa][beta + 1][1] == 'p';
                                    System.out.println(wynik);
                                } else {

                                    wynik = (z1 == 'q' || z1 == 'r' || z1 == 'b' || z1 == 'n' || z1 == 'p' || z1 == 'k') && tulasy == ' ';
                                }
                            } else {
                                wynik = false;
                                System.out.println(wynik);
                            }
                        }
                    } else {
                        wynik = false;
                        System.out.println(wynik);
                    }
                }
                if (wynik == true && przelotcan == true && (symbol == 'P' && lokalS[1] == 5)) {

                    wynik = true;

                }
                if (wynik == true && przelotcan == false) {

                    wynik = true;

                }
                break;
            case 'p':
                alfa = (byte) (lokalS[1] - 1);
                beta = (byte) (lokalS[0] - 1);
                gama = (byte) (lokalK[0] - 1);
                dela = (byte) (lokalK[1] - 1);
                z1 = czybiale == true ? ustawienie[alfa - 1][beta][1] : ustawienie[alfa - 1][beta][0];
                if (alfa - 2 > -1) {
                    z2 = czybiale == true ? ustawienie[alfa - 2][beta][1] : ustawienie[alfa - 2][beta][0];
                } else {
                    z2 = ' ';
                }
                if (((alfa == 6 || alfa == 7) && (beta - gama == 0) && ((z1 == ' ' && dela - alfa == -1) || (z1 == z2 && dela - alfa == -2
                        && (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p'))))
                        || (dela - alfa == -1 && beta - gama == 0 && (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p'))) {
                    wynik = true;
                } else {
                    if (dela - alfa == -1) {
                        if (beta - 1 >= 0 && gama - beta == -1) {
                            z1 = czybiale == false ? ustawienie[alfa - 1][beta - 1][0] : ustawienie[alfa - 1][beta - 1][1];
                            if (przelotcan == true && alfa - dela == 1 && alfa == 3 && beta == kol) {
                                wynik = (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p') && ustawienie[alfa][beta - 1][0] == 'P';
                            } else {
                                wynik = (z1 == 'Q' || z1 == 'R' || z1 == 'B' || z1 == 'N' || z1 == 'P' || z1 == 'K') && tulasy == ' ';
                            }
                        } else {
                            if (beta + 1 <= 7 && gama - beta == 1) {
                                z1 = czybiale == true ? ustawienie[alfa - 1][beta + 1][1] : ustawienie[alfa - 1][beta + 1][0];
                                if (przelotcan == true && alfa - dela == 1 && alfa == 3 && beta + 2 == kol) {
                                    wynik = (z1 != 'K' && z1 != 'k' && z1 != 'Q' && z1 != 'q' && z1 != 'R' && z1 != 'r' && z1 != 'B' && z1 != 'b' && z1 != 'N' && z1 != 'n' && z1 != 'P' && z1 != 'p') && ustawienie[alfa][beta + 1][0] == 'P';
                                    System.out.println(wynik);
                                } else {
                                    wynik = (z1 == 'Q' || z1 == 'R' || z1 == 'B' || z1 == 'N' || z1 == 'P' || z1 == 'K') && tulasy == ' ';
                                }
                            } else {
                                wynik = false;
                                System.out.println(wynik);
                            }
                        }
                    } else {
                        wynik = false;
                        System.out.println(wynik);
                    }
                }
                if (wynik == true && przelotcan == true && (symbol == 'p' && lokalS[1] == 4)) {

                    wynik = true;

                }
                if (wynik == true && przelotcan == false) {
                    wynik = true;

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

                    wynik = (tulasy == ' '
                            && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                            || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                            && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ');

                } else {
                    if (alfa == gama) {
                        if (beta > dela) {
                            for (byte i = 1; i < beta - dela; i++) {
                                if ((ustawienie[beta - i][alfa][0] == ' ' && ustawienie[beta - i][alfa][0] == ' ')) {
                                    wynik = ((i == beta - dela - 1)
                                            && ((tulasy == ' '
                                            && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                            || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                            && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                            || ((i < beta - dela - 1) && (ustawienie[beta - i][alfa][0] == ' ' && ustawienie[beta - i][alfa][1] == ' '));

                                } else {
                                    wynik = false;
                                    System.out.println(wynik);
                                    break;
                                }
                            }
                        } else {
                            for (byte i = 1; i < dela - beta; i++) {
                                if ((ustawienie[beta + i][alfa][0] == ' ' && ustawienie[beta + i][alfa][0] == ' ')) {
                                    wynik = ((i == dela - beta - 1)
                                            && ((tulasy == ' '
                                            && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                            || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                            || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                            && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                            || ((i < dela - beta - 1) && (ustawienie[beta + i][alfa][0] == ' ' && ustawienie[beta + i][alfa][1] == ' '));
                                    System.out.println(wynik);
                                } else {
                                    wynik = false;
                                    System.out.println(wynik);
                                    break;
                                }
                            }
                        }
                    } else {
                        if (beta == dela) {
                            if (alfa > gama) {
                                for (byte i = 1; i < alfa - gama; i++) {
                                    if (ustawienie[beta][alfa - i][0] == ' ' && ustawienie[beta][alfa - i][1] == ' ') {
                                        wynik = ((i == alfa - gama - 1)
                                                && ((tulasy == ' '
                                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                || ((i < alfa - gama - 1) && (ustawienie[beta][alfa - i][0] == ' ' && ustawienie[beta][alfa - i][1] == ' '));
                                        System.out.println(wynik);
                                    } else {
                                        wynik = false;
                                        System.out.println(wynik);
                                        break;
                                    }
                                }
                            } else {
                                for (byte i = 1; i < gama - alfa; i++) {
                                    if (ustawienie[beta][alfa + i][0] == ' ' && ustawienie[beta][alfa + i][1] == ' ') {
                                        wynik = ((i == gama - alfa - 1)
                                                && ((tulasy == ' '
                                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                || ((i < gama - alfa - 1) && (ustawienie[beta][alfa + i][0] == ' ' && ustawienie[beta][alfa + i][1] == ' '));

                                        System.out.println(wynik);
                                    } else {
                                        wynik = false;
                                        System.out.println(wynik);
                                        break;
                                    }
                                }
                            }
                        } else {
                            wynik = false;
                            System.out.println(wynik);
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

                        wynik = (tulasy == ' '
                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ');

                    } else {
                        if (alfa == gama) {
                            if (beta > dela) {
                                for (byte i = 1; i < beta - dela; i++) {
                                    if ((ustawienie[beta - i][alfa][0] == ' ' && ustawienie[beta - i][alfa][0] == ' ')) {
                                        wynik = ((i == beta - dela - 1)
                                                && ((tulasy == ' '
                                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                || ((i < beta - dela - 1) && (ustawienie[beta - i][alfa][0] == ' ' && ustawienie[beta - i][alfa][1] == ' '));

                                    } else {
                                        wynik = false;
                                        System.out.println(wynik);
                                        break;
                                    }
                                }
                            } else {
                                for (byte i = 1; i < dela - beta; i++) {
                                    if ((ustawienie[beta + i][alfa][0] == ' ' && ustawienie[beta + i][alfa][0] == ' ')) {
                                        wynik = ((i == dela - beta - 1)
                                                && ((tulasy == ' '
                                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                || ((i < dela - beta - 1) && (ustawienie[beta + i][alfa][0] == ' ' && ustawienie[beta + i][alfa][1] == ' '));
                                        System.out.println(wynik);
                                    } else {
                                        wynik = false;
                                        System.out.println(wynik);
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (beta == dela) {
                                if (alfa > gama) {
                                    for (byte i = 1; i < alfa - gama; i++) {
                                        if (ustawienie[beta][alfa - i][0] == ' ' && ustawienie[beta][alfa - i][1] == ' ') {
                                            wynik = ((i == alfa - gama - 1)
                                                    && ((tulasy == ' '
                                                    && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                    || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                    || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                    || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                    && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                    || ((i < alfa - gama - 1) && (ustawienie[beta][alfa - i][0] == ' ' && ustawienie[beta][alfa - i][1] == ' '));
                                            System.out.println(wynik);
                                        } else {
                                            wynik = false;
                                            System.out.println(wynik);
                                            break;
                                        }
                                    }
                                } else {
                                    for (byte i = 1; i < gama - alfa; i++) {
                                        if (ustawienie[beta][alfa + i][0] == ' ' && ustawienie[beta][alfa + i][1] == ' ') {
                                            wynik = ((i == gama - alfa - 1)
                                                    && ((tulasy == ' '
                                                    && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                    || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                    || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                    || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                    && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                    || ((i < gama - alfa - 1) && (ustawienie[beta][alfa + i][0] == ' ' && ustawienie[beta][alfa + i][1] == ' '));

                                            System.out.println(wynik);
                                        } else {
                                            wynik = false;
                                            System.out.println(wynik);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                wynik = false;
                                System.out.println(wynik);
                            }

                        }
                    }
                } else {
                    if (lokalK[1] - lokalS[1] == 1 && (lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1)
                            || lokalK[1] - lokalS[1] == -1 && ((lokalK[0] - lokalS[0] == 1 || lokalK[0] - lokalS[0] == -1))) {

                        wynik = (tulasy == ' '
                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ');

                    } else {
                        if ((lokalS[0] - lokalK[0]) == (lokalS[1] - lokalK[1])) {
                            if (lokalS[1] > lokalK[1]) {
                                outerLoop:
                                for (byte i = 0; i <= (lokalS[1] - lokalK[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] - i - 2);
                                    beta = (byte) (lokalS[1] - i - 2);
                                    if ((lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])
                                            && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' ')) {
                                        wynik = ((i == lokalS[1] - lokalK[1] - 2)
                                                && ((tulasy == ' '
                                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                || ((i < lokalS[1] - lokalK[1] - 2) && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' '));
                                        if (wynik == false) {
                                            break;
                                        }
                                    } else {

                                        wynik = false;

                                        System.out.println(wynik);
                                        break;
                                    }
                                }
                            } else {
                                outerLoop:
                                for (byte i = 0; i <= (lokalK[1] - lokalS[1] - 2); i++) {
                                    alfa = (byte) (lokalS[0] + i);
                                    beta = (byte) (lokalS[1] + i);

                                    if ((lokalS[1] - lokalK[1] == lokalS[0] - lokalK[0])
                                            && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' ')) {
                                        wynik = ((i == lokalK[1] - lokalS[1] - 2)
                                                && ((tulasy == ' '
                                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                || ((i < lokalK[1] - lokalS[1] - 2) && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' '));
                                        if (wynik == false) {
                                            break;
                                        }
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
                                    if ((lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * -1)
                                            && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' ')) {
                                        wynik = ((i == lokalK[1] - lokalS[1] - 2)
                                                && ((tulasy == ' '
                                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                || ((i < lokalK[1] - lokalS[1] - 2) && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' '));
                                        if (wynik == false) {
                                            break;
                                        }
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
                                    if ((lokalS[1] - lokalK[1] == (lokalS[0] - lokalK[0]) * -1)
                                            && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' ')) {
                                        wynik = ((i == lokalS[1] - lokalK[1] - 2)
                                                && ((tulasy == ' '
                                                && ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')
                                                || (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] != ' ' && ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] != ' ')))
                                                || ((ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 1 : 0] == ' '
                                                && (ustawienie[lokalK[1] - 1][lokalK[0] - 1][czybiale == true ? 0 : 1] == ' ')) && tulasy != ' ')))
                                                || ((i < lokalS[1] - lokalK[1] - 2) && (ustawienie[beta][alfa][0] == ' ' && ustawienie[beta][alfa][1] == ' '));
                                        if (wynik == false) {
                                            break;
                                        }
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
                wynik = (((lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == -1)));
                break;

            case 'K':
                wynik = (((lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == -1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == -1)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 0)
                        || (lokalS[0] - lokalK[0] == 1 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == 1)
                        || (lokalS[0] - lokalK[0] == 0 && lokalS[1] - lokalK[1] == -1)));
                break;

        }
        return wynik;
    }

    public static boolean szach(char[][][] pozycja, boolean czybiale) {
        byte krolX = 0, krolY = 0, alfa, beta;
        boolean zagrozenie = false;
        char[][][] pomocnicze = new char[16][16][2];
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                pomocnicze[i + 4][j + 4][1] = pozycja[i][j][1];
                pomocnicze[i + 4][j + 4][0] = pozycja[i][j][0];
                if ((czybiale && pozycja[i][j][0] == 'K') || (!czybiale && pozycja[i][j][1] == 'k')) {
                    krolX = (byte) (i + 4);
                    krolY = (byte) (j + 4);
                }
            }
        }

        if ((czybiale && ((pomocnicze[krolX + 2][krolY - 1][1] == 'n')
                || (pomocnicze[krolX + 2][krolY + 1][1] == 'n')
                || (pomocnicze[krolX - 2][krolY - 1][1] == 'n')
                || (pomocnicze[krolX - 2][krolY + 1][1] == 'n')
                || (pomocnicze[krolX + 1][krolY + 2][1] == 'n')
                || (pomocnicze[krolX + 1][krolY - 2][1] == 'n')
                || (pomocnicze[krolX - 1][krolY + 2][1] == 'n')
                || (pomocnicze[krolX - 1][krolY - 2][1] == 'n')))
                || (!czybiale && ((pomocnicze[krolX + 2][krolY - 1][0] == 'N')
                || (pomocnicze[krolX + 2][krolY + 1][0] == 'N')
                || (pomocnicze[krolX - 2][krolY - 1][0] == 'N')
                || (pomocnicze[krolX - 2][krolY + 1][0] == 'N')
                || (pomocnicze[krolX + 1][krolY + 2][0] == 'N')
                || (pomocnicze[krolX + 1][krolY - 2][0] == 'N')
                || (pomocnicze[krolX - 1][krolY + 2][0] == 'N')
                || (pomocnicze[krolX - 1][krolY - 2][0] == 'N')))) {
            zagrozenie = true;
            return zagrozenie;
        }
        if ((czybiale && ((pomocnicze[krolX + 1][krolY + 1][1] == 'p') || (pomocnicze[krolX + 1][krolY - 1][1] == 'p')))
                || (!czybiale && ((pomocnicze[krolX - 1][krolY + 1][0] == 'P') || (pomocnicze[krolX - 1][krolY - 1][0] == 'P')))) {
            zagrozenie = true;
            return zagrozenie;
        }
        if ((czybiale && ((pomocnicze[krolX + 1][krolY + 1][1] == 'k')
                || (pomocnicze[krolX + 1][krolY][1] == 'k')
                || (pomocnicze[krolX + 1][krolY - 1][1] == 'k')
                || (pomocnicze[krolX][krolY - 1][1] == 'k')
                || (pomocnicze[krolX - 1][krolY - 1][1] == 'k')
                || (pomocnicze[krolX - 1][krolY][1] == 'k')
                || (pomocnicze[krolX - 1][krolY + 1][1] == 'k')
                || (pomocnicze[krolX][krolY + 1][1] == 'k')))
                || (!czybiale && ((pomocnicze[krolX + 1][krolY + 1][0] == 'K')
                || (pomocnicze[krolX + 1][krolY][0] == 'K')
                || (pomocnicze[krolX + 1][krolY - 1][0] == 'K')
                || (pomocnicze[krolX][krolY - 1][0] == 'K')
                || (pomocnicze[krolX - 1][krolY - 1][0] == 'K')
                || (pomocnicze[krolX - 1][krolY][0] == 'K')
                || (pomocnicze[krolX - 1][krolY + 1][0] == 'K')
                || (pomocnicze[krolX][krolY + 1][0] == 'K')))) {
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
                if ((czybiale && pomocnicze[i][beta + licz][1] == ' ') || (!czybiale && pomocnicze[i][beta + licz][0] == ' ')) {
                    zagrozenie = false;
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz][1] != 'q' && pomocnicze[i][beta + licz][1] != 'b'))
                            || (!czybiale && (pomocnicze[i][beta + licz][0] != 'Q' && pomocnicze[i][beta + licz][0] != 'B'))) {
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
                if ((!czybiale && pomocnicze[i][beta + licz][0] == ' ') || (czybiale && pomocnicze[i][beta + licz][1] == ' ')) {
                    zagrozenie = false;
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz][1] != 'q' && pomocnicze[i][beta + licz][1] != 'b'))
                            || (!czybiale && (pomocnicze[i][beta + licz][0] != 'Q' && pomocnicze[i][beta + licz][0] != 'B'))) {
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
                if ((!czybiale && pomocnicze[i][beta + licz][0] == ' ') || (czybiale && pomocnicze[i][beta + licz][1] == ' ')) {
                    zagrozenie = false;
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz][1] != 'q' && pomocnicze[i][beta + licz][1] != 'b'))
                            || (!czybiale && (pomocnicze[i][beta + licz][0] != 'Q' && pomocnicze[i][beta + licz][0] != 'B'))) {
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
                if ((!czybiale && pomocnicze[i][beta + licz][0] == ' ') || (czybiale && pomocnicze[i][beta + licz][1] == ' ')) {
                    zagrozenie = false;
                } else {
                    if ((czybiale && (pomocnicze[i][beta + licz][1] != 'q' && pomocnicze[i][beta + licz][1] != 'b'))
                            || (!czybiale && (pomocnicze[i][beta + licz][0] != 'Q' && pomocnicze[i][beta + licz][0] != 'B'))) {
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
            if ((czybiale && pomocnicze[i][beta][1] == ' ') || (!czybiale && pomocnicze[i][beta][0] == ' ')) {
                zagrozenie = false;
            } else {
                if ((czybiale && (pomocnicze[i][beta][1] != 'q' && pomocnicze[i][beta][1] != 'r'))
                        || (!czybiale && (pomocnicze[i][beta][0] != 'Q' && pomocnicze[i][beta][0] != 'R'))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        for (byte i = (byte) (alfa - 1); i >= 4; i--) {
            if ((czybiale && pomocnicze[i][beta][1] == ' ') || (!czybiale && pomocnicze[i][beta][0] == ' ')) {
                zagrozenie = false;
            } else {
                if ((czybiale && (pomocnicze[i][beta][1] != 'q' && pomocnicze[i][beta][1] != 'r'))
                        || (!czybiale && (pomocnicze[i][beta][0] != 'Q' && pomocnicze[i][beta][0] != 'R'))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        for (byte i = (byte) (beta + 1); i <= 11; i++) {
            if ((!czybiale && pomocnicze[alfa][i][0] == ' ') || (czybiale && pomocnicze[alfa][i][1] == ' ')) {
                zagrozenie = false;
            } else {
                if ((czybiale && (pomocnicze[alfa][i][1] != 'q' && pomocnicze[alfa][i][1] != 'r'))
                        || (!czybiale && (pomocnicze[alfa][i][0] != 'Q' && pomocnicze[alfa][i][0] != 'R'))) {
                    zagrozenie = false;
                } else {
                    zagrozenie = true;
                    return zagrozenie;
                }
                break;
            }
        }
        for (byte i = (byte) (beta - 1); i >= 4; i--) {
            if ((!czybiale && pomocnicze[alfa][i][0] == ' ') || (czybiale && pomocnicze[alfa][i][1] == ' ')) {
                zagrozenie = false;
            } else {
                if ((czybiale && (pomocnicze[alfa][i][1] != 'q' && pomocnicze[alfa][i][1] != 'r'))
                        || (!czybiale && (pomocnicze[alfa][i][0] != 'Q' && pomocnicze[alfa][i][0] != 'R'))) {
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
}
