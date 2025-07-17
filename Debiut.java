/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package szachy;

import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author PatrykK
 */
class Debiut implements Comparable<Debiut>{

    
    private  String nazwa="";
    private  String ruchy;
    ArrayList<DefaultMutableTreeNode> hierarchia = new ArrayList<>();
    
    Debiut(String ruchy, String nazwa) {
       this.ruchy = ruchy;
       this.nazwa = nazwa;
       StringTokenizer st = new StringTokenizer(nazwa, "/");
        while (st.hasMoreTokens()) {
       this.hierarchia.add(new DefaultMutableTreeNode(st.nextToken()));
        }
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getRuchy() {
        return ruchy;
    }

    @Override
    public int compareTo(Debiut o) {
        return (o.getNazwa().equals(this.getNazwa())) ? 0 : 1;
    }
    
}
