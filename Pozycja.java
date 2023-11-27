/*
 * Click nbfs://nbhost///SystemFile//System/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost///SystemFile//System/Templates/Classes/Class.java to edit this template
 */
package szachy;



/**
 *
 * @author PatrykK
 */
public class Pozycja {

    boolean Dbleft, Dbright, Dwleft, Dwright, DKingrochB, DKingrochC, DWhiteMove, przelot_can;
    boolean didrochB,didrochC;
    char[][] pozycja = new char[8][8];
    int przelot;

    public Pozycja(boolean Dbleft, boolean Dbright, boolean Dwleft, boolean Dwright,
            boolean DKingrochB, boolean DKingrochC, boolean DWhiteMove,
            boolean przelot_can, int przelot, char[][] poz) {
        this.Dbleft = Dbleft;
        this.Dbright = Dbright;
        this.Dwleft = Dwleft;
        this.Dwright = Dwright;
        this.DKingrochB = DKingrochB;
        this.DKingrochC = DKingrochC;
        this.DWhiteMove = DWhiteMove;
        this.przelot_can = przelot_can;
        this.przelot = przelot;
        for (int x = 0; x < 8; x++) {
            System.arraycopy(poz[x], 0, this.pozycja[x], 0, 8);
        }
    }

    Pozycja(Pozycja pozycja1) {
        this.Dbleft = pozycja1.Dbleft;
        this.Dbright = pozycja1.Dbright;
        this.Dwleft = pozycja1.Dwleft;
        this.Dwright = pozycja1.Dwright;
        this.DKingrochB = pozycja1.DKingrochB;
        this.DKingrochC = pozycja1.DKingrochC;
        this.DWhiteMove = pozycja1.DWhiteMove;
        this.przelot_can = pozycja1.przelot_can;
        this.przelot = pozycja1.przelot;
        for (int x = 0; x < 8; x++) {
            System.arraycopy(pozycja1.pozycja[x], 0, this.pozycja[x], 0, 8);
        }
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pozycja other = (Pozycja) obj;
        if (this.Dbleft != other.Dbleft) {
            return false;
        }
        if (this.Dbright != other.Dbright) {
            return false;
        }
        if (this.Dwleft != other.Dwleft) {
            return false;
        }
        if (this.Dwright != other.Dwright) {
            return false;
        }
        if (this.DKingrochB != other.DKingrochB) {
            return false;
        }
        if (this.DKingrochC != other.DKingrochC) {
            return false;
        }
        if (this.DWhiteMove != other.DWhiteMove) {
            return false;
        }
        if (this.przelot_can != other.przelot_can) {
            return false;
        }
        if (this.przelot != other.przelot) {
            return false;
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (this.pozycja[x][y] != other.pozycja[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

}
