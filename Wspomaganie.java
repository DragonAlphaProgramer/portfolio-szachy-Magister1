/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package szachy;

/**
 *
 * @author PatrykK
 */
public interface Wspomaganie {
    
    
     int[] znajdzmozliwosc(char[][] ustawienie, boolean czybiale, int[] poszukiwanie,
            boolean przelotcan);
     int[] znajdzmozliwosc(char[][] ustawienie, char[][] nakladki, boolean czybiale, int[] poszukiwanie,
            boolean przelotcan);
     
     int[] znajdzklopot(char[][] ust, char[][] nakladka, boolean czybiale);
     
     int[] znajdzklopot(char[][] ust, boolean czybiale);
}
