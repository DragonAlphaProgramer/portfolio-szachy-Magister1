/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.util.ArrayList;

/**
 *
 * @author Patryk
 */
public class pozycja_WIEZA {

    public ArrayList<int[]> podaj_wieze(boolean gracz, char[][] poza) {
        ArrayList<int[]> wynik = new ArrayList<>();
        int[] wieza = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if((gracz&&poza[i][j]=='R')||(!gracz&&poza[i][j]=='r')){
                    wieza[0]=i;
                    wieza[1]=j;
                    wynik.add(wieza);
                }
            }
        }
        return wynik;
    }

    public int punktacja(boolean gracz, char[][] poza) {
        ArrayList<int[]> wieze = podaj_wieze(gracz, poza);
        return zlicz_wartosc_bonusu(poza,wieze);
    }

    private int zlicz_wartosc_bonusu(char[][] poza, ArrayList<int[]> wieze) {
        int bonus = 0;
        int na_linii=0;
        for(int[] lockacja : wieze){
            for(int k=0;k<8;k++){
                if(poza[k][lockacja[1]]!=' '){
                    na_linii=na_linii+1;
                }
            }
            if(na_linii==1){
                bonus=bonus+30;
            }
        }
        return bonus;
        }
}
