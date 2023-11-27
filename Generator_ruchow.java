/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package szachy;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author PatrykK
 */
public interface Generator_ruchow {
    ArrayList<Ruch> generuj_posuniecia(char[][] ust, boolean tura_rywala, boolean przelotcan,
            boolean blackleft, boolean blackright, boolean whiteleft, boolean whiteright,
            boolean kingrochB, boolean kingrochC, int kolumna, boolean all);
    Collection<Ruch> generuj_posuniecia(char[][] ust, boolean tura_rywala, boolean przelotcan,
            boolean blackleft, boolean blackright, boolean whiteleft, boolean whiteright,
            boolean kingrochB, boolean kingrochC, int kolumna, boolean konkret, char znak_start, int[] start, boolean all,
            boolean mgla);
    Collection<RuchA> generuj_posunieciaA(figuryA[][] ust, boolean tura_rywala, boolean przelotcan,
            boolean blackleft, boolean blackright, boolean whiteleft, boolean whiteright,
            boolean kingrochB, boolean kingrochC, int kolumna, boolean konkret, char znak_start, int[] start, boolean all);
    Collection<Ruch> generuj_posuniecia_antyszach(char[][] ust, boolean tura_rywala, boolean przelotcan, int kolumna);
    ArrayList<Ruch> generuj_posuniecia(figury[][] ust, boolean tura_rywala, boolean przelotcan,
            boolean blackleft, boolean blackright, boolean whiteleft, boolean whiteright,
            boolean kingrochB, boolean kingrochC, int kolumna, boolean all, boolean mgla);
            
}
