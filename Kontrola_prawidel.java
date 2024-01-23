/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package szachy;

/**
 *
 * @author PatrykK
 */
public interface Kontrola_prawidel {

    

   

    boolean rozszerzenie(char symbol, char rozszerzony);

    boolean antyruch(int[] lokalS, int[] lokalK, char symbol,
            char[][] ustawienie, boolean czybiale, boolean przelotcan, int kol);

    boolean szach(figury[][] pozycja, boolean czybiale);

    public boolean szach(char[][] pozycja, boolean czybiale);
    
    public boolean ruch(int[] lokalS, int[] lokalK, char symbol,
            char[][] ustawienie, boolean czybiale, boolean przelotcan, int kol,boolean mgla);
    
    public boolean szach(char[][] pozycja, char[][] nakladki, boolean czybiale);
    
    public boolean ruch(int[] lokalS, int[] lokalK, char symbol, char c, char[][] ustawienie, boolean czybiale, boolean przelotcan, int kol, char[][] nakladki);
}
