package szachy;

import java.util.ArrayList;
import java.util.Collection;
import static szachy.SzachowaArena.jProgressBar1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Patryk
 */
public class SI_MIN_MAX_Alfa_Beta {

    int pozycje = 0;
    int all_position = 0;
    int licznik;
    boolean tura_rywala, przelotcan, bleft, bright, wleft, wright,
            kingrochB, kingrochC, didRochB, didRochC;
    boolean przerwa;
    boolean zakaz;
    private final boolean wyjsciowa_tura;
    private final Kalkulator wynikowa;

    private boolean kontrola_pat(char[][] ustawienie, boolean strona, boolean przelotcan) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[] poza_krolewska = new int[2];
                poza_krolewska[0] = j;
                poza_krolewska[1] = i;
                if (ustawienie[i][j] == 'K' || ustawienie[i][j] == 'k') {

                    //System.out.println("wchodzi król "+(ustawienie[i][j]=='K'?"biały":"czarny"));
                    if ((strona && ustawienie[i][j] == 'K')
                            || (!strona && ustawienie[i][j] == 'k')) {
                        //   System.out.println("wchodzi "+ustawienie[i][j]+" "+strona);
                        poza_krolewska[0] = i;
                        poza_krolewska[1] = j;
                        //  System.out.println(ustawienie[poza_krolewska[0]][poza_krolewska[1]]);
                        if (SzachMatPatKontrola.uciekaj(ustawienie, strona, poza_krolewska)) {

                            return false;
                        }
                    }

                } else {

                    if (SzachMatPatKontrola.znajdz_ruch(ustawienie, strona, ustawienie[i][j], poza_krolewska, przelotcan)) {

                        return false;
                    }

                }
            }
        }
        return true;
    }

    public SI_MIN_MAX_Alfa_Beta(char[][] ustawienie, boolean tura_rywala, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean kingrochB, boolean kingrochC, boolean dokonano_RB, boolean dokonano_RC,
            int kol, int licznik, int glebina) {
        this.licznik = licznik;
        this.tura_rywala = tura_rywala;
        this.wyjsciowa_tura = tura_rywala;
        this.przelotcan = przelotcan;
        this.bleft = bleft;
        this.bright = bright;
        this.wleft = wleft;
        this.wright = wright;
        this.didRochB = dokonano_RB;
        this.didRochC = dokonano_RC;
        this.kingrochB = kingrochB;
        this.kingrochC = kingrochC;
        wynikowa = KalkulatorPozycji.get();
    }

    /**
     *
     * @param glebia
     * @param ruch
     * @param najwieksza
     * @param najmniejsza
     * @return
     */
    public Ruch_wartosc wykonaj(int glebia, Collection<Ruch> ruch, int najwieksza, int najmniejsza) {
        int biezaca_ogolna = 0;
        Ruch najlepszy = null;
        all_position = all_position + ruch.size();
        int elem = 0;
        for (final Ruch move : ruch) {
            elem++;
            jProgressBar1.setValue(elem);
            jProgressBar1.setString("Rozpatrywane:" + (move.toString()) + "| bieżacy wybór:" + (najlepszy != null ? najlepszy.toString() : ""));
            byte Nkolumna;
            if ((move.kolejnosc == 'P' || move.kolejnosc == 'p') && (Math.abs(pozyskajkordkolumna(move.koniec2) - pozyskajkordkolumna(move.start2)) == 2)) {
                Nkolumna = (byte) (pozyskajkordrzad(move.start1));
                this.przelotcan = true;
            } else {
                Nkolumna = 0;
                this.przelotcan = false;
            }
            try {

                if (move.roszada && !move.dlugaroszada) {
                    if (this.tura_rywala) {
                        wright = false;
                    } else {
                        bright = false;
                    }
                } else if (move.roszada) {
                    wleft = false;
                }
                if (move.roszada) {
                    if (this.tura_rywala) {
                        this.didRochB = true;
                    } else {
                        this.didRochC = true;
                        kingrochC = false;
                    }
                } else {
                    switch (move.kolejnosc) {
                        case 'r':
                        case 'R':
                            if (!tura_rywala) {
                                if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                                    bleft = false;
                                } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                                    bright = false;
                                }
                            } else {
                                if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                                    wleft = false;
                                } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                                    wright = false;
                                }
                            }
                            break;
                        case 'K':
                        case 'k':
                            if (tura_rywala) {
                                if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                                    kingrochB = false;
                                }
                            } else {
                                if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                                    kingrochC = false;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                this.tura_rywala = !this.tura_rywala;
                biezaca_ogolna = (wyjsciowa_tura)
                        ? minimum((glebia - 1), Nkolumna, najwieksza, najmniejsza, move.chessboard_after)
                        : maximum((glebia - 1), Nkolumna, najwieksza, najmniejsza, move.chessboard_after);
                this.tura_rywala = !this.tura_rywala;
                if (wyjsciowa_tura) {
                    setPrzerwa(kontrola_mat((move.chessboard_after), false, Nkolumna, przelotcan));
                } else {
                    setPrzerwa(kontrola_mat((move.chessboard_after), true, Nkolumna, przelotcan));
                }

                if (move.roszada && !move.dlugaroszada) {
                    if (this.tura_rywala) {
                        wright = true;
                    } else {
                        bright = true;
                    }
                } else if (move.roszada) {
                    wleft = true;
                }
                if (move.roszada) {
                    if (this.tura_rywala) {
                        this.didRochB = false;
                    } else {
                        this.didRochC = false;
                    }
                    kingrochB = true;
                } else {
                    switch (move.kolejnosc) {
                        case 'r':
                        case 'R':
                            if (!tura_rywala) {
                                if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                                    bleft = true;
                                } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                                    bright = true;
                                }
                            } else {
                                if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                                    wleft = true;
                                } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                                    wright = true;
                                }
                            }
                            break;
                        case 'k':
                        case 'K':
                            if (tura_rywala) {
                                if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                                    kingrochB = true;
                                }
                            } else {
                                if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                                    kingrochC = true;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                setZakaz(false);
                if (wyjsciowa_tura && biezaca_ogolna > najwieksza) {
                    najwieksza = biezaca_ogolna;
                    najlepszy = move;
                } else if (!wyjsciowa_tura && biezaca_ogolna < najmniejsza) {
                    najmniejsza = biezaca_ogolna;
                    najlepszy = move;
                }

                jProgressBar1.setString("Rozpatrywane:" + move + "| bieżacy wybór:" + najlepszy.toString());
                if (this.przerwa) {
                    System.out.println("przerwa");
                    break;
                }
            } catch (Exception e) {
                System.out.println("ERROR POSITION");
                e.printStackTrace();
                return new Ruch_wartosc(move, (!wyjsciowa_tura ? Integer.MAX_VALUE : Integer.MIN_VALUE));
            }
        }
        return new Ruch_wartosc(najlepszy, biezaca_ogolna);
    }

    public boolean isZakaz() {
        return zakaz;
    }

    public void setZakaz(boolean zakaz) {
        this.zakaz = zakaz;
    }

    private int maximum(final int glebia, int kolumna, int biggest, int samllest, char[][] chessboard) {
        if (glebia == 0 || koniec((chessboard), this.tura_rywala, this.przelotcan, kolumna)) {
            pozycje = pozycje + 1;
            /* for (Ruch r : kombinacja) {
                System.out.print(r.toString() + ",");
            }
            System.out.println("");*/
            Collection<figury[]> biale_ruchy = Generator.generuj_posuniecia_Name(chessboard, true, przelotcan, bleft, bright, wleft, wright, kingrochB, kingrochC, kolumna, true);
            Collection<figury[]> czarne_ruchy = Generator.generuj_posuniecia_Name(chessboard, false, przelotcan, bleft, bright, wleft, wright, kingrochB, kingrochC, kolumna, true);
            return this.wynikowa.zliczacz(
                    chessboard, bleft, bright, wleft, wright, kingrochB, kingrochC,
                    przelotcan, this.didRochB, this.didRochC, kolumna, glebia,biale_ruchy,czarne_ruchy);
        }
        final Collection<Ruch> lista = Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, kolumna, false);

        int tempB = biggest;
        all_position = all_position + lista.size();
        for (final Ruch move : lista) {
            //    System.out.println(move.toString()+ "|"+move.wspolczynnik_bitki);
            byte Nkolumna;
            if ((move.kolejnosc == 'P' || move.kolejnosc == 'p') && (Math.abs(pozyskajkordkolumna(move.koniec2) - pozyskajkordkolumna(move.start2)) == 2)) {
                Nkolumna = (byte) (pozyskajkordrzad(move.start1));
                this.przelotcan = true;
            } else {
                Nkolumna = 0;
                this.przelotcan = false;
            }
            if (move.roszada && !move.dlugaroszada) {
                if (this.tura_rywala) {
                    wright = false;
                } else {
                    bright = false;
                }
            } else if (move.roszada) {
                wleft = false;
            }
            if (move.roszada) {
                if (this.tura_rywala) {
                    this.didRochB = true;
                } else {
                    this.didRochC = true;
                    kingrochC = false;
                }
            } else {
                switch (move.kolejnosc) {
                    case 'r':
                    case 'R':
                        if (!tura_rywala) {
                            if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                                bleft = false;
                            } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                                bright = false;
                            }
                        } else {
                            if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                                wleft = false;
                            } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                                wright = false;
                            }
                        }
                        break;
                    case 'K':
                    case 'k':
                        if (tura_rywala) {
                            if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                                kingrochB = false;
                            }
                        } else {
                            if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                                kingrochC = false;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            this.tura_rywala = !this.tura_rywala;
            tempB = Math.max(tempB, minimum(((glebia - 1)), Nkolumna,
                    tempB, samllest, move.chessboard_after));
            if (move.roszada && !move.dlugaroszada) {
                if (this.tura_rywala) {
                    wright = true;
                } else {
                    bright = true;
                }
            } else if (move.roszada) {
                wleft = true;
            }
            if (move.roszada) {
                if (this.tura_rywala) {
                    this.didRochB = false;
                } else {
                    this.didRochC = false;
                }
                kingrochB = true;
            } else {
                switch (move.kolejnosc) {
                    case 'r':
                    case 'R':
                        if (!tura_rywala) {
                            if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                                bleft = true;
                            } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                                bright = true;
                            }
                        } else {
                            if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                                wleft = true;
                            } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                                wright = true;
                            }
                        }
                        break;
                    case 'k':
                    case 'K':
                        if (tura_rywala) {
                            if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                                kingrochB = true;
                            }
                        } else {
                            if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                                kingrochC = true;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            this.tura_rywala = !this.tura_rywala;
            this.przelotcan = (kolumna != 0);
            if (samllest <= tempB) {
                break;
            }

        }
        return tempB;
    }

    private int minimum(final int glebia, int kolumna, int biggest, int smallest, char[][] chessboard) {
        if (glebia == 0 || koniec((chessboard), this.tura_rywala, this.przelotcan, kolumna)) {
            pozycje = pozycje + 1;
            /* for (Ruch r : kombinacja) {
                System.out.print(r.toString() + ",");
            }
            System.out.println("");*/
            Collection<figury[]> biale_ruchy = Generator.generuj_posuniecia_Name(chessboard, true, przelotcan, bleft, bright, wleft, wright, kingrochB, kingrochC, kolumna, true);
            Collection<figury[]> czarne_ruchy = Generator.generuj_posuniecia_Name(chessboard, false, przelotcan, bleft, bright, wleft, wright, kingrochB, kingrochC, kolumna, true);          
            return this.wynikowa.zliczacz(
                    chessboard, przelotcan, bleft, bright, wleft, wright, kingrochB, kingrochC,
                    this.didRochB, this.didRochC, kolumna, glebia,biale_ruchy,czarne_ruchy);
        }
        final Collection<Ruch> lista = Generator.generuj_posuniecia(chessboard, this.tura_rywala, this.przelotcan,
                this.bleft, this.bright, this.wleft, this.wright, this.kingrochB, this.kingrochC, kolumna, false);

        int tempM = smallest;
        all_position = all_position + lista.size();
        for (final Ruch move : lista) {

            byte Nkolumna;
            // System.out.println(move.toString()+ "|"+move.wspolczynnik_bitki); 
            if ((move.kolejnosc == 'P' || move.kolejnosc == 'p') && (Math.abs(pozyskajkordkolumna(move.koniec2) - pozyskajkordkolumna(move.start2)) == 2)) {
                Nkolumna = (byte) (pozyskajkordrzad(move.start1));
                this.przelotcan = true;
            } else {
                Nkolumna = 0;
                this.przelotcan = false;
            }
            if (move.roszada && !move.dlugaroszada) {
                if (this.tura_rywala) {
                    wright = false;
                } else {
                    bright = false;
                }
            } else if (move.roszada) {
                wleft = false;
            }
            if (move.roszada) {
                if (this.tura_rywala) {
                    this.didRochB = true;
                } else {
                    this.didRochC = true;
                    kingrochC = false;
                }

            } else {
                switch (move.kolejnosc) {
                    case 'r':
                    case 'R':
                        if (!tura_rywala) {
                            if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                                bleft = false;
                            } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                                bright = false;
                            }
                        } else {
                            if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                                wleft = false;
                            } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                                wright = false;
                            }
                        }
                        break;
                    case 'K':
                    case 'k':
                        if (tura_rywala) {
                            if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                                kingrochB = false;
                            }
                        } else {
                            if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                                kingrochC = false;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            this.tura_rywala = !this.tura_rywala;
            tempM = Math.min(tempM, maximum(((glebia - 1)),
                    Nkolumna, biggest, tempM, move.chessboard_after));
            if (move.roszada && !move.dlugaroszada) {
                if (this.tura_rywala) {
                    wright = true;
                } else {
                    bright = true;
                }
            } else if (move.roszada) {
                wleft = true;
            }
            if (move.roszada) {
                if (this.tura_rywala) {
                    this.didRochB = false;
                } else {
                    this.didRochC = false;
                }
                kingrochB = true;
            } else {
                switch (move.kolejnosc) {
                    case 'r':
                    case 'R':
                        if (!tura_rywala) {
                            if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r8) {
                                bleft = true;
                            } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r8) {
                                bright = true;
                            }
                        } else {
                            if (move.start1 == Ruch.kolumna.k1 && move.start2 == Ruch.rzad.r1) {
                                wleft = true;
                            } else if (move.start1 == Ruch.kolumna.k8 && move.start2 == Ruch.rzad.r1) {
                                wright = true;
                            }
                        }
                        break;
                    case 'k':
                    case 'K':
                        if (tura_rywala) {
                            if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r1) {
                                kingrochB = true;
                            }
                        } else {
                            if (move.start1 == Ruch.kolumna.k5 && move.start2 == Ruch.rzad.r8) {
                                kingrochC = true;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            this.tura_rywala = !this.tura_rywala;
            this.przelotcan = (kolumna != 0);
            if (tempM <= biggest) {
                break;
            }
        }
        return tempM;
    }

    private boolean koniec(char[][] ustawienie, boolean strona, boolean przelotcan, int kol) {
        int pionB = 0, pionC = 0, lekkieB = 0, lekkieC = 0, ciezkieB = 0, ciezkieC = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (ustawienie[i][j]) {
                    case 'P':
                        pionB++;
                        break;
                    case 'p':
                        pionC++;
                        break;
                    case 'N':
                    case 'B':
                        lekkieB++;
                        break;
                    case 'n':
                    case 'b':
                        lekkieC++;
                        break;
                    case 'R':
                    case 'Q':
                        ciezkieB++;
                        break;
                    case 'r':
                    case 'q':
                        ciezkieC++;
                        break;
                }

            }
        }
        if (pionB < 1 && pionC < 1 && lekkieB < 2 && lekkieC < 2 && ciezkieB < 1 && ciezkieC < 1) {
            return true;
        } else {

            if (RuchZagrozenie_kontrola.szach(ustawienie, strona)) {
                return kontrola_mat(ustawienie, strona, (byte) kol, przelotcan);
            } else {

                return kontrola_pat(ustawienie, strona, przelotcan);
            }
        }
    }

    private boolean kontrola_mat(char[][] ustawienie, boolean strona, byte kol, boolean przelotcan) {
        int[] krol = new int[2];
        if (RuchZagrozenie_kontrola.szach((ustawienie), strona)) {
            char[][] backup = new char[8][8];
            char[][] backup1 = new char[8][8];
            char[][] backup2 = new char[8][8];
            char[][] backup3 = new char[8][8];
            for (byte i = 0; i < 8; i++) {
                for (byte j = 0; j < 8; j++) {
                    backup[i][j] = ustawienie[i][j];
                    backup1[i][j] = ustawienie[i][j];
                    backup2[i][j] = ustawienie[i][j];
                    backup3[i][j] = ustawienie[i][j];
                    if ((ustawienie[i][j] == 'K' && strona)
                            || (ustawienie[i][j] == 'k' && !strona)) {
                        krol[0] = i;
                        krol[1] = j;
                    }
                }
            }

            return !SzachMatPatKontrola.uciekaj(backup1, strona, krol)
                    && !SzachMatPatKontrola.zastaw(backup2, strona, Wspomagacz.znajdzklopot(backup, strona), krol, przelotcan)
                    && !SzachMatPatKontrola.znajdzodsiecz(backup3, strona, Wspomagacz.znajdzklopot(backup, strona), kol, przelotcan);

        } else {
            return false;
        }

    }

    private int pozyskajkordkolumna(Ruch.rzad kord) {
        switch (kord) {
            case r1:
                return 1;
            case r2:
                return 2;
            case r3:
                return 3;
            case r4:
                return 4;
            case r5:
                return 5;
            case r6:
                return 6;
            case r7:
                return 7;
            case r8:
                return 8;
        }
        return 0;
    }

    private int pozyskajkordrzad(Ruch.kolumna kord) {
        switch (kord) {
            case k1:
                return 1;
            case k2:
                return 2;
            case k3:
                return 3;
            case k4:
                return 4;
            case k5:
                return 5;
            case k6:
                return 6;
            case k7:
                return 7;
            case k8:
                return 8;
        }
        return 0;

    }

    public void setPrzerwa(boolean przerwa) {
        this.przerwa = przerwa;
    }

    public int getPozycje() {
        return pozycje;
    }

    public int getAll_position() {
        return all_position;
    }

    public boolean isPrzerwa() {
        return przerwa;
    }

}
