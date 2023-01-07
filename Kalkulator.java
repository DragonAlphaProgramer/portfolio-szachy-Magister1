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
interface Kalkulator {
    
    int zliczacz(SI_MIN_MAX_Alfa_Beta.figury[][] ustawienie, boolean tura_rywala, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, boolean wykonanaRochB, boolean wykonanaRochC,
            int glebia, int kol);
    
    
}
