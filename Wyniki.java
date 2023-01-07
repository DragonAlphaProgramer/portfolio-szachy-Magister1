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
public class Wyniki {

   private  String ruch;
   private int najlepszy, pula_pozycji, pula_sprawdzona,licznik;
   private  boolean obecny,zakaz,przerwa;

    public boolean isPrzerwa() {
        return przerwa;
    }

    public Wyniki(String ruch, int najlepszy, int pula_pozycji, int pula_sprawdzona,
            int licznik,boolean czy_cos_bylo,boolean zakaz,boolean przerwa) {
        this.ruch = ruch;
        this.najlepszy = najlepszy;
        this.pula_pozycji = pula_pozycji;
        this.pula_sprawdzona = pula_sprawdzona;
        this.obecny=czy_cos_bylo;
        this.licznik=licznik;
        this.zakaz = zakaz;
    }

    public boolean isZakaz() {
        return zakaz;
    }

    public String getOponet() {
        return ruch;
    }

    public int getNajlepszy() {
        return najlepszy;
    }

    public int getPula_pozycji() {
        return pula_pozycji;
    }

    public int getPula_sprawdzona() {
        return pula_sprawdzona;
    }

    public int getLicznik() {
        return licznik;
    }

    public boolean isObecny() {
        return obecny;
    }

    

}
