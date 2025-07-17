/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package szachy;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.teleal.cling.support.model.StorageMedium;

/**
 *
 * @author PatrykK
 */
public class Debiut_nauka extends javax.swing.JFrame {

    public ArrayList<String> trening = new ArrayList<>();
    public ArrayList<Boolean> bledy = new ArrayList<>();
    List<Debiut> lista = new ArrayList<>();
    private DefaultMutableTreeNode root;
    ArrayList<ArrayList<String>> debiuty_schemat = new ArrayList<>();
    HashMap<String, HashSet<DefaultMutableTreeNode>> nazwy = new HashMap<>();
    HashMap<String, HashSet<DefaultMutableTreeNode>> nazwy2 = new HashMap<>();
    HashMap<String, HashSet<DefaultMutableTreeNode>> nazwy3 = new HashMap<>();
    HashMap<String, HashSet<DefaultMutableTreeNode>> nazwy4 = new HashMap<>();
    HashMap<String, HashSet<DefaultMutableTreeNode>> nazwy5 = new HashMap<>();

    /**
     * Creates new form Debiut_nauka
     */
    public Debiut_nauka() {
        initComponents();
        lista = lacze_z_baza.uzyskaj_debiut();
        drzewo_Debiut.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        root = new DefaultMutableTreeNode("Debiuty");
        for (Debiut d : lista) {
            StringTokenizer st = new StringTokenizer(d.getNazwa(), "/");
            ArrayList<String> sciezka = new ArrayList<>();
            while (st.hasMoreTokens()) {
                sciezka.add(st.nextToken());
            }
            debiuty_schemat.add(sciezka);
        }

        int x = 0;
        for (ArrayList<String> listaS : debiuty_schemat) {
            int y = 0;
            for (String s : listaS) {
                HashSet<DefaultMutableTreeNode> itemsList;
                ArrayList<String> itemsListS;
                switch (y) {
                    case 0:
                        itemsList = nazwy.get("Debiuty");
                        if (itemsList == null) {
                            itemsList = new HashSet<DefaultMutableTreeNode>();
                            itemsList.add(new DefaultMutableTreeNode(s));
                            nazwy.put("Debiuty", itemsList);
                        } else {
                            itemsListS = new ArrayList<>();
                            for (DefaultMutableTreeNode wezly : nazwy.get("Debiuty")) {
                                itemsListS.add(wezly.toString());
                            }

                            if (!itemsListS.contains(s)) {
                                itemsList.add(new DefaultMutableTreeNode(s));
                            }

                        }
                        y = 1;
                        break;
                    case 1:
                        itemsList = nazwy2.get(listaS.get(y - 1));
                        if (itemsList == null) {
                            itemsList = new HashSet<DefaultMutableTreeNode>();
                            itemsList.add(new DefaultMutableTreeNode(s));
                            nazwy2.put(listaS.get(0), itemsList);
                        } else {
                            itemsListS = new ArrayList<>();
                            for (DefaultMutableTreeNode wezly : nazwy2.get(listaS.get(0))) {
                                itemsListS.add(wezly.toString());
                            }
                            if (!itemsListS.contains(s)) {
                                itemsList.add(new DefaultMutableTreeNode(s));
                            }
                        }
                        y = 2;
                        break;
                    case 2:
                        itemsList = nazwy3.get(listaS.get(0) + "/" + listaS.get(1));
                        if (itemsList == null) {
                            itemsList = new HashSet<DefaultMutableTreeNode>();
                            itemsList.add(new DefaultMutableTreeNode(s));
                            nazwy3.put(listaS.get(0) + "/" + listaS.get(1), itemsList);
                        } else {
                            itemsListS = new ArrayList<>();
                            for (DefaultMutableTreeNode wezly : nazwy3.get(listaS.get(0) + "/" + listaS.get(1))) {
                                itemsListS.add(wezly.toString());
                            }
                            if (!itemsListS.contains(s)) {
                                itemsList.add(new DefaultMutableTreeNode(s));
                            }
                        }
                        y = 3;
                        break;
                    case 3:
                        itemsList = nazwy4.get(listaS.get(0) + "/" + listaS.get(1) + "/" + listaS.get(2));
                        if (itemsList == null) {
                            itemsList = new HashSet<DefaultMutableTreeNode>();
                            itemsList.add(new DefaultMutableTreeNode(s));
                            nazwy4.put(listaS.get(0) + "/" + listaS.get(1) + "/" + listaS.get(2), itemsList);
                        } else {
                            itemsListS = new ArrayList<>();
                            for (DefaultMutableTreeNode wezly : nazwy4.get(
                                    listaS.get(0) + "/" + listaS.get(1) + "/" + listaS.get(2))) {
                                itemsListS.add(wezly.toString());
                            }
                            if (!itemsListS.contains(s)) {
                                itemsList.add(new DefaultMutableTreeNode(s));
                            }
                        }
                        y = 4;
                        break;
                    case 4:
                        itemsList = nazwy5.get(listaS.get(0) + "/" + listaS.get(1) + "/" + listaS.get(2)+ "/" + listaS.get(3));
                        if (itemsList == null) {
                            itemsList = new HashSet<DefaultMutableTreeNode>();
                            itemsList.add(new DefaultMutableTreeNode(s));
                            nazwy5.put(listaS.get(0) + "/" + listaS.get(1) + "/" + listaS.get(2) + "/" + listaS.get(3), itemsList);
                        } else {
                            itemsListS = new ArrayList<>();
                            for (DefaultMutableTreeNode wezly : nazwy5.get(listaS.get(0) + "/" + listaS.get(1) + "/" + listaS.get(2) + "/" + listaS.get(3))) {
                                itemsListS.add(wezly.toString());
                            }
                            if (!itemsListS.contains(s)) {
                                itemsList.add(new DefaultMutableTreeNode(s));
                            }
                        }
                        break;
                }
            }
            x++;
        }

        for (HashMap.Entry<String, HashSet<DefaultMutableTreeNode>> n1 : nazwy.entrySet()) {
            HashSet<DefaultMutableTreeNode> wezly = n1.getValue();
            for (DefaultMutableTreeNode wezel : wezly) {
                root.add(wezel);
            }
        }
        for (HashMap.Entry<String, HashSet<DefaultMutableTreeNode>> n1 : nazwy.entrySet()) {
            HashSet<DefaultMutableTreeNode> wezly = n1.getValue();
            for (DefaultMutableTreeNode wezel : wezly) {
                for (HashMap.Entry<String, HashSet<DefaultMutableTreeNode>> n2 : nazwy2.entrySet()) {
                    if (n2.getKey().equals(wezel.toString())) {
                        for (DefaultMutableTreeNode node : n2.getValue()) {
                            wezel.add(node);
                        }
                    }
                }
            }
        }

        for (HashMap.Entry<String, HashSet<DefaultMutableTreeNode>> n2 : nazwy2.entrySet()) {
            HashSet<DefaultMutableTreeNode> wezly = n2.getValue();
            for (DefaultMutableTreeNode wezel : wezly) {
                for (HashMap.Entry<String, HashSet<DefaultMutableTreeNode>> n3 : nazwy3.entrySet()) {
                    for (DefaultMutableTreeNode node : n3.getValue()) {
                        if (n3.getKey().equals(n2.getKey() + "/" + wezel.toString())) {
                            wezel.add(node);
                            
                        }
                    }
                }
            }
        }

        for (HashMap.Entry<String, HashSet<DefaultMutableTreeNode>> n3 : nazwy3.entrySet()) {
            HashSet<DefaultMutableTreeNode> wezly = n3.getValue();
            for (DefaultMutableTreeNode wezel : wezly) {
                for (HashMap.Entry<String, HashSet<DefaultMutableTreeNode>> n4 : nazwy4.entrySet()) {
                    for (DefaultMutableTreeNode node : n4.getValue()) {
                        if (n4.getKey().equals(n3.getKey() + "/" + wezel.toString())) {
                            wezel.add(node);
                        }
                    }
                }
            }
        }
        for (HashMap.Entry<String, HashSet<DefaultMutableTreeNode>> n4 : nazwy4.entrySet()) {
            HashSet<DefaultMutableTreeNode> wezly = n4.getValue();
            for (DefaultMutableTreeNode wezel : wezly) {
                for (HashMap.Entry<String, HashSet<DefaultMutableTreeNode>> n5 : nazwy5.entrySet()) {
                    for (DefaultMutableTreeNode node : n5.getValue()) {
                        if (n5.getKey().equals(n4.getKey() + "/" + wezel.toString())) {
                            wezel.add(node);
                        }
                    }
                }
            }
        }

        System.out.println(nazwy.get("Debiuty").size());
        DefaultTreeModel treeModel = (DefaultTreeModel) drzewo_Debiut.getModel();
        treeModel.setRoot(root);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        drzewo_Debiut = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Szachy Magister - debiuty");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Debiuty");
        drzewo_Debiut.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        drzewo_Debiut.setToolTipText("");
        drzewo_Debiut.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        drzewo_Debiut.setFocusCycleRoot(true);
        drzewo_Debiut.setFocusable(false);
        drzewo_Debiut.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                drzewo_DebiutValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(drzewo_Debiut);

        jLabel1.setText("Debiut");

        jLabel2.setText("Ruchy");

        jButton1.setText("Ćwicz debiut");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(335, 335, 335)
                        .addComponent(jButton1)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        StringTokenizer tokeny = new StringTokenizer(jLabel2.getText(),",");
        trening.removeAll(trening);
         bledy.removeAll(trening);
       while (tokeny.hasMoreTokens()) {
                trening.add(tokeny.nextToken());
                bledy.add(true);
            }   
      
       for (int i = 1; i <= 8; i++) {
            for (char j = 'A'; j <= 'H'; j++) {
                dobierzprzycisk(String.valueOf(j + "" + i)).setEnabled(true);
            }
        }
       this.setVisible(false);
       SzachowaArena.wzor=true;
       SzachowaArena.remis_prop.setEnabled(true);
       SzachowaArena.remis_zgoda.setEnabled(true);
       SzachowaArena.poddanie.setEnabled(true);
       SzachowaArena.jTextArea2.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void drzewo_DebiutValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_drzewo_DebiutValueChanged
          TreePath selectionPath = drzewo_Debiut.getSelectionPath();
          if (selectionPath != null) {
                jLabel1.setText("");
              Object selectedNode = selectionPath.getLastPathComponent();
                for(Object s : selectionPath.getPath()){
                    jLabel1.setText(jLabel1.getText().concat(s+"/"));
                }
                jLabel1.setText(jLabel1.getText().replaceAll("Debiuty/", ""));
                jLabel1.setText(jLabel1.getText().substring(0,jLabel1.getText().length()-1));
               if (((DefaultMutableTreeNode) selectedNode).isLeaf()) {
                
                 jLabel2.setText(lacze_z_baza.uzyskaj_debiut_konkret(jLabel1.getText()));
            } else {
                 jLabel2.setText(lacze_z_baza.uzyskaj_ruchy(jLabel1.getText()));
            }
                jButton1.setEnabled(true);
          }else{
               jButton1.setEnabled(false);
          }
    }//GEN-LAST:event_drzewo_DebiutValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Debiut_nauka.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Debiut_nauka.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Debiut_nauka.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Debiut_nauka.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Debiut_nauka().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree drzewo_Debiut;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public JButton dobierzprzycisk(String nazwabuttona) {
        switch (nazwabuttona) {
            case "A1":
                return  SzachowaArena.A1;
            case "A2":
                return  SzachowaArena.A2;
            case "A3":
                return  SzachowaArena.A3;
            case "A4":
                return  SzachowaArena.A4;
            case "A5":
                return  SzachowaArena.A5;
            case "A6":
                return  SzachowaArena.A6;
            case "A7":
                return  SzachowaArena.A7;
            case "A8":
                return  SzachowaArena.A8;
            case "B1":
                return  SzachowaArena.B1;
            case "B2":
                return  SzachowaArena.B2;
            case "B3":
                return  SzachowaArena.B3;
            case "B4":
                return  SzachowaArena.B4;
            case "B5":
                return  SzachowaArena.B5;
            case "B6":
                return  SzachowaArena.B6;
            case "B7":
                return  SzachowaArena.B7;
            case "B8":
                return  SzachowaArena.B8;
            case "C1":
                return  SzachowaArena.C1;
            case "C2":
                return  SzachowaArena.C2;
            case "C3":
                return  SzachowaArena.C3;
            case "C4":
                return  SzachowaArena.C4;
            case "C5":
                return  SzachowaArena.C5;
            case "C6":
                return  SzachowaArena.C6;
            case "C7":
                return  SzachowaArena.C7;
            case "C8":
                return  SzachowaArena.C8;
            case "D1":
                return  SzachowaArena.D1;
            case "D2":
                return  SzachowaArena.D2;
            case "D3":
                return  SzachowaArena.D3;
            case "D4":
                return  SzachowaArena.D4;
            case "D5":
                return  SzachowaArena.D5;
            case "D6":
                return  SzachowaArena.D6;
            case "D7":
                return  SzachowaArena.D7;
            case "D8":
                return  SzachowaArena.D8;
            case "E1":
                return  SzachowaArena.E1;
            case "E2":
                return  SzachowaArena.E2;
            case "E3":
                return  SzachowaArena.E3;
            case "E4":
                return  SzachowaArena.E4;
            case "E5":
                return  SzachowaArena.E5;
            case "E6":
                return  SzachowaArena.E6;
            case "E7":
                return  SzachowaArena.E7;
            case "E8":
                return  SzachowaArena.E8;
            case "F1":
                return  SzachowaArena.F1;
            case "F2":
                return  SzachowaArena.F2;
            case "F3":
                return  SzachowaArena.F3;
            case "F4":
                return  SzachowaArena.F4;
            case "F5":
                return  SzachowaArena.F5;
            case "F6":
                return  SzachowaArena.F6;
            case "F7":
                return  SzachowaArena.F7;
            case "F8":
                return  SzachowaArena.F8;
            case "G1":
                return  SzachowaArena.G1;
            case "G2":
                return  SzachowaArena.G2;
            case "G3":
                return  SzachowaArena.G3;
            case "G4":
                return  SzachowaArena.G4;
            case "G5":
                return  SzachowaArena.G5;
            case "G6":
                return  SzachowaArena.G6;
            case "G7":
                return  SzachowaArena.G7;
            case "G8":
                return  SzachowaArena.G8;
            case "H1":
                return  SzachowaArena.H1;
            case "H2":
                return  SzachowaArena.H2;
            case "H3":
                return  SzachowaArena.H3;
            case "H4":
                return  SzachowaArena.H4;
            case "H5":
                return  SzachowaArena.H5;
            case "H6":
                return  SzachowaArena.H6;
            case "H7":
                return  SzachowaArena.H7;
            case "H8":
                return  SzachowaArena.H8;
        }
        return null;
    }
}
