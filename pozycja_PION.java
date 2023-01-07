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
public class pozycja_PION {

    public static int punktacja(boolean gracz, char[][] ustawienie) {
        byte[] pion_na_linii = zlicz_piony(gracz, ustawienie);
        return zlicz_zdublowane(pion_na_linii) + zlicz_izolowane(pion_na_linii);
    }

    private static byte[] zlicz_piony(boolean gracz, char[][] pozycja) {
        byte[] pozycje = new byte[8];
        for (byte i = 0; i < 8; i++) {
            for (byte j = 0; j < 8; j++) {
                if ((pozycja[i][j] == 'P' && gracz) || (pozycja[i][j] == 'p' && !gracz)) {
                    pozycje[j]++;
                }
            }
        }
        return pozycje;
    }

    private static int zlicz_zdublowane(byte[] pion_na_linii) {
        int kara = 0;
        for (int i = 0; i < 8; i++) {

            if (pion_na_linii[i] > 1) {
                kara = kara + pion_na_linii[i];
            }
        }
        return kara * (-10);
    }

    private static int zlicz_izolowane(byte[] pion_na_linii) {
        int izolatka = 0;
        if (pion_na_linii[0] > 0 && pion_na_linii[1] == 0) {
            izolatka = izolatka + pion_na_linii[0];
        }
        if (pion_na_linii[7] > 0 && pion_na_linii[6] == 0) {
            izolatka = izolatka + pion_na_linii[7];
        }
        for (byte i = 1; i < 7; i++) {
            if (pion_na_linii[i + 1] == 0 && pion_na_linii[i - 1] == 0 && pion_na_linii[i] > 0) {
                izolatka = izolatka + pion_na_linii[i];
                
            }
        }
        return izolatka * (-10);
    }
}
