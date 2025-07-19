/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author Patryk
 */
public class lacze_z_baza {

    public static Connection polacz(String baza) {
        Connection polaczenie;
        try {
            // Wskazanie jaki rodzaj bazy danych będzie wykorzystany, tu sqlite
            Class.forName("org.sqlite.JDBC");
            // Połączenie, wskazujemy rodzaj bazy i jej nazwę
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            System.out.println("Połączyłem się z bazą " + baza);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Błąd w połączeniu z bazą: \n" + e.getMessage());
            return null;
        }
        return polaczenie;
    }

    public static void stworzTabele(Connection polaczenie, String tabela) {
        // Obiekt odpowiadający za wykonanie instrukcji
        Statement stat;
        try {
            stat = polaczenie.createStatement();
            // polecenie SQL tworzące tablicę
            String tabelaSQL = "CREATE TABLE " + tabela
                    + " (ID     INT PRIMARY KEY     NOT NULL  ,"
                    + " BIALE         STRING    NOT NULL, "
                    + " CZARNE        STRING     NOT NULL )";
            // wywołanie polecenia
            stat.executeUpdate(tabelaSQL);
            // zamykanie wywołania i połączenia
            stat.close();
            polaczenie.close();
        } catch (SQLException e) {
            System.out.println("Nie mogę stworzyć tabeli " + e.getMessage());
        }
    }

    public static void dodajDane(String tabela, String biale, String czarne) {
        Connection polaczenie;
        Statement stat;
        int ic = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + tabela + ".db");
            stat = polaczenie.createStatement();
            String szukajSQL = "SELECT ID FROM " + tabela + " WHERE ID=(  SELECT MAX(ID) FROM " + tabela + ");";
            try (ResultSet rezultacik = stat.executeQuery(szukajSQL)) {
                while (rezultacik.next()) {
                    int idn = ((Number) rezultacik.getObject(1)).intValue();
                    ic = idn + 1;
                }
            }

            int idv = ic;
            System.out.println("id=" + idv);
            if (idv == 0) {
                idv = 1;
            }

            //     System.out.println("Polecenie: \n" + szukajSQL + "\n wykonane.");
            String dodajSQL = "INSERT INTO " + tabela + " (ID, BIALE, CZARNE) "
                    + "VALUES (" + idv + ","
                    + "'" + biale + "',"
                    + "'" + czarne + "');";
            stat.executeUpdate(dodajSQL);
            stat.close();
            polaczenie.close();
            // Komunikat i wydrukowanie końcowej formy polecenia SQL
            //    System.out.println("Polecenie: \n" + dodajSQL + "\n wykonane.");
        } catch (ClassNotFoundException | SQLException e) {
            //      System.out.println("Nie mogę dodać danych " + e.getMessage());
        }

    }

    static Connection polacz_ksiazka(String debiuty) {
        Connection polaczenie;
        try {
            // Wskazanie jaki rodzaj bazy danych będzie wykorzystany, tu sqlite
            Class.forName("org.sqlite.JDBC");
            // Połączenie, wskazujemy rodzaj bazy i jej nazwę
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + debiuty + ".db");
            System.out.println("Połączyłem się z bazą " + debiuty);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Błąd w połączeniu z bazą: \n" + e.getMessage());
            return null;
        }
        return polaczenie;
    }

    static List<Debiut> uzyskaj_debiut() {
        Connection polaczenie;
        Statement stat;
        List<Debiut> lista = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:debiuty.db");
            stat = polaczenie.createStatement();
            String szukajSQL = "SELECT * FROM Debiuty_koncowki ORDER BY Wariant;";
            ResultSet rezultacik = stat.executeQuery(szukajSQL);

            while (rezultacik.next()) {
                lista.add(new Debiut(rezultacik.getString(2), rezultacik.getString(3)));

            }
            Collections.sort(lista);
            return lista;
        } catch (ClassNotFoundException | SQLException x) {
            return null;
        }
    }

    static String uzyskaj_ruch(String ruch, ArrayList<String> bialy, ArrayList<String> czarny) {
        Connection polaczenie;
        Statement stat;
        System.out.println(ruch);
        String mutacja = "";
        ArrayList<String> lista = new ArrayList<>();
        int B = 0, C = 0, Bmax = bialy.size(), Cmax = czarny.size(), B1 = B + 1, C1 = 0;
        boolean permutacje_stop = false, wyjsciowa = true, ostatnia = false, swieza_zmiana = false;
        try {
            System.out.println(Bmax + " " + Cmax);
            do {
                permutacje_stop = ostatnia;
                mutacja = "";
                if (!ruch.equals("START")) {
                    if (!wyjsciowa) {
                        if (Bmax > 1 && !swieza_zmiana) {
                            Collections.swap(bialy, B, B1);
                        }
                        if (Cmax > 1 && C < C1 && C1 > 0 && C1 < Cmax) {
                            Collections.swap(czarny, C, C1);
                        }
                    }
                    for (int i = 0; i < Bmax; i++) {
                        mutacja = mutacja.concat(bialy.get(i) + "," + (czarny.size() >= (i + 1) ? czarny.get(i) + "," : ""));
                    }
                    mutacja = mutacja.substring(0, mutacja.length() - 1);
                }
                System.out.println("MUT:" + mutacja);
                Class.forName("org.sqlite.JDBC");
                polaczenie = DriverManager.getConnection("jdbc:sqlite:debiuty.db");
                stat = polaczenie.createStatement();
                String szukajSQL = "SELECT Ruch FROM Debiuty WHERE Poprzednicy = '" + (ruch.equals("START") ? ruch : mutacja) + "';";
                ResultSet rezultacik = stat.executeQuery(szukajSQL);
                while (rezultacik.next()) {
                    lista.add(rezultacik.getString(1));
                    ostatnia = true;
                    System.out.println("wynik:" + lista.get(lista.size() - 1));
                }
                System.out.println(lista.size());
                if (!ruch.equals("START")) {
                    if (!wyjsciowa) {
                        if (Bmax > 1) {
                            Collections.swap(bialy, B, B1);
                        }
                        if (Cmax > 1 && C < C1) {
                            Collections.swap(czarny, C, C1);
                        }
                        B++;
                        if (B >= B1) {
                            B1++;
                            B = 0;
                        }
                        if (B1 >= Bmax) {
                            B1 = 1;
                            swieza_zmiana = true;
                            if (C1 == 0) {
                                C1 = 1;
                                C = 0;
                            } else {
                                C++;
                                if (C >= C1) {
                                    C1++;
                                    C = 0;
                                }
                            }
                        }
                        System.out.println(B + "|" + B1);
                        System.out.println(C + "|" + C1);
                        if (lista.isEmpty() && !permutacje_stop) {
                            ostatnia = (B1 >= bialy.size() - 1 && C1 >= czarny.size());
                        }
                    }
                }
                wyjsciowa = false;
            } while (permutacje_stop == false && lista.isEmpty());
            System.out.println("pusta:" + lista.isEmpty());
            if (lista.isEmpty()) {
                return "";
            } else {
                Random los = new Random();
                return lista.get(los.nextInt(lista.size()));
            }
        } catch (ClassNotFoundException | SQLException x) {
            return "";
        }
    }

    static String uzyskaj_debiut_konkret(String text) {
        Connection polaczenie;
        Statement stat;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:debiuty.db");
            stat = polaczenie.createStatement();
            text = text.replace("'", "''");
            String szukajSQL = "SELECT * FROM Debiuty_koncowki WHERE Wariant='" + text + "';";
            ResultSet rezultacik = stat.executeQuery(szukajSQL);
            return (rezultacik.getString(2));
        } catch (ClassNotFoundException | SQLException x) {
            return null;
        }
    }

    static String uzyskaj_ruchy(String text) {
        Connection polaczenie;
        Statement stat;
        String wynik = "";
        String pom = "";
        ArrayList<String> lista = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:debiuty.db");
            stat = polaczenie.createStatement();
            text = text.replace("'", "''");
            String szukajSQL = "SELECT * FROM Debiuty_koncowki WHERE Wariant LIKE '" + text + "%' ;";
            ResultSet rezultacik = stat.executeQuery(szukajSQL);
            while (rezultacik.next()) {
                lista.add((rezultacik.getString(2)));
            }
            Collections.sort(lista);
            if (lista.size() > 1) {
                for (String s : lista) {
                    pom = "";
                    if (wynik.equals("")) {
                        wynik = s;
                    } else {
                        StringTokenizer tokeny = new StringTokenizer(wynik, ",");
                        StringTokenizer tokeny2 = new StringTokenizer(s, ",");
                        while (tokeny.hasMoreTokens()) {
                            String temp = tokeny.nextToken();
                            String temp2 = tokeny2.hasMoreTokens() ? tokeny2.nextToken() : "";
                            if (temp.equals(temp2)) {
                                pom = pom.concat(temp + ",");
                            } else {
                                wynik = pom;
                                break;
                            }
                        }
                    }
                }
                return wynik.substring(0,wynik.length()-1);
            } else {
                return lista.get(0);
            }
        } catch (ClassNotFoundException | SQLException x) {
            return null;
        }
    }

}
