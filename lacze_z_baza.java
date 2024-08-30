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
import java.util.Random;

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
            System.out.println("Połączyłem się z bazą "+baza);
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
            String szukajSQL = "SELECT ID FROM "+tabela+" WHERE ID=(  SELECT MAX(ID) FROM "+tabela+");";
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
            String dodajSQL = "INSERT INTO "+tabela+" (ID, BIALE, CZARNE) "
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
            System.out.println("Połączyłem się z bazą "+debiuty);
        } catch (ClassNotFoundException | SQLException e) {
           System.err.println("Błąd w połączeniu z bazą: \n" + e.getMessage());
            return null;
        }
        return polaczenie;
    }

    static String uzyskaj_ruch(String ruch) {
        Connection polaczenie;
         Statement stat;
           ArrayList<String> lista = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:debiuty.db");
            stat= polaczenie.createStatement();
            String szukajSQL = "SELECT Ruch FROM Debiuty WHERE Poprzednicy = '"+ruch+"';";
            ResultSet rezultacik = stat.executeQuery(szukajSQL);
                while (rezultacik.next()) {
                    lista.add(rezultacik.getString(1));
                }
        
        if(lista.isEmpty()){
        return "";
        }else{
            Random los = new Random();
            return lista.get(los.nextInt(lista.size()));
        }
    }catch(Exception x){
        return "";
    }
    }
        
}
