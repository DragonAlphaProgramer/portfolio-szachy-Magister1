/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package szachy;

import java.util.ArrayList;

/**
 *
 * @author PatrykK
 */
public interface Strategia {
    Ruch_wartosc wykonaj(int glebia, ArrayList<Ruch> move, int najwieksza, int najmniejsza);
}
