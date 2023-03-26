/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package szachy;

/**
 *
 * @author PatrykK
 */
class Ruch_wartosc {
    int wartosc;
    Ruch ruch;

    Ruch_wartosc(Ruch move, int biezaca_ogolna) {
    ruch = move;
    wartosc=biezaca_ogolna;
    System.out.println(move.toString()+ " "+biezaca_ogolna);
    }
}
