/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.util.Collection;

/**
 *
 * @author Patryk
 */
interface Kalkulator {

    int zliczacz(char[][] ustawienie, boolean przelotcan,
            boolean bleft, boolean bright, boolean wleft, boolean wright,
            boolean roszadaB, boolean roszadaC, boolean wykonanaRochB, boolean wykonanaRochC,
            int kol, int glebia, Collection<figury[]> biale_ruchy, Collection<figury[]> czarne_ruchy);

}
