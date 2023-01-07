/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szachy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Patryk
 */
public class Pingowanie implements Runnable{
    private String ip;
    private String ip_start;
    private String interfejs;
    private int port=6070;
    
    Pingowanie(String ipstart,String ip){
        ip_start = ipstart;
       this.ip = ip; 
    }

    
    @Override
    public void run() {
        String nazwa;
        InetAddress inet = null;
         try {
            inet = InetAddress.getByName(ip);  //indirizzo ip di un host dato il nome, sottoforma di oggetto di classe inetAddress, mi permette di effettuare reverselookup

        } catch (UnknownHostException ex) {
            Logger.getLogger(Pingowanie.class.getName()).log(Level.SEVERE, null, ex);
        }
     if (ping(ip)) {
                    nazwa = inet.getHostAddress();
               System.out.println(nazwa);

                SzachowaArena.dodaj_do_listy(nazwa);
            }
    }
 public boolean ping(String host) {
        try {

            String komenda = "";
            String s;
            if (System.getProperty("os.name").startsWith("Windows")) { //se il sistema è windows

                komenda = "ping -n 4 " + host;
            } else { //se il sistema è linux
                komenda = "ping -c 4 " + host;
            }
            Process myProcess = Runtime.getRuntime().exec(komenda); //esegue un comando passato come stringa (in questo caso ping -n 4 <indirizzoip>)
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(myProcess.getInputStream()));  //stream dove è possibile leggere l'output del comando
            while ((s = stdInput.readLine()) != null) { //fix per windows (caso "risposta da <indirizzoiprouter>: Host di destinazione non raggiungibile")
                if (s.toLowerCase().contains("host di destinazione non raggiungibile") || s.toLowerCase().contains("destination host unreachable")) {
                    return false;  //ping fallito (host non raggiungibile)
                }
                if (s.toLowerCase().contains("ttl")) {
                    return true;  //time to leave communicato, vuoldire che l'host ha risposto
                }
            }
            myProcess.waitFor();  //aspetto che termini
            if (myProcess.exitValue() == 0) {  //se risponde normalmente (Risposta da : )
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {  //errori
            e.printStackTrace();
            return false;
        }

    }
    
    
}
