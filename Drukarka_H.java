/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package szachy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;

/**
 *
 * @author PatrykK
 */
public class Drukarka_H implements Printable {

    Image obraz;

    private Image konwert_na_grafike(ArrayList<String> historia) {
        BufferedImage bufferedImage = new BufferedImage(580, 850, BufferedImage.TYPE_INT_RGB);
        Graphics2D obraz1 = bufferedImage.createGraphics();
        obraz1.setColor(Color.white);
        obraz1.fillRect(0, 0, 580, 850);
        obraz1.setColor(Color.black);
        int x = 0;
        obraz1.setFont(new Font("Liberation Mono", Font.PLAIN, 10));
        int temp = 1;
        for (int i = 0; i < historia.size(); i++) {

             if (i % 2 == 0) {
                obraz1.drawString(((temp) + "." + historia.get(i)), x * 135, (i == 0) ? 10 : (i / 2) * 10 - (750 * x)+10);
            } else {
                obraz1.drawString(historia.get(i), 80 + x * 135, (i != 1) ? ((i - 1) / 2) * 10 - (750 * x) +10: 10);
                temp++;
            }
            if (((i + 1) % 150 == 0)) {
                x = x + 1;
            }
        }
        return bufferedImage;
    }

    private Image konwert_na_grafike(ArrayList<String> historia,int przedzial) {
        BufferedImage bufferedImage = new BufferedImage(580, 850, BufferedImage.TYPE_INT_RGB);
        Graphics2D obraz1 = bufferedImage.createGraphics();
        obraz1.setColor(Color.white);
        obraz1.fillRect(0, 0, 580, 850);
        obraz1.setColor(Color.black);
        int x = 0;
        obraz1.setFont(new Font("Liberation Mono", Font.PLAIN, 10));
        int temp = 1;
        for (int i = 0; i < historia.size(); i++) {
             if (i % 2 == 0) {
                obraz1.drawString(((temp) + "." + historia.get(i)), x * 135, (i == 0) ? 10 : (i / 2) * 10 - (750 * x)+10);
            } else {
                obraz1.drawString(historia.get(i), 80 + x * 135, (i != 1) ? ((i - 1) / 2) * 10 - (750 * x) +10: 10);
                temp++;
            }
            if (((i + 1) % 150 == 0)) {
                x = x + 1;
            }
        }
        obraz1.drawString(((przedzial-1)*300+1)+" - "+((przedzial)*300),300 ,790);
        return bufferedImage;
    }

    
    
     public Drukarka_H(ArrayList<String> historia,int prezial) {
        obraz = konwert_na_grafike(historia,prezial);
    }
    public Drukarka_H(ArrayList<String> historia) {
        obraz = konwert_na_grafike(historia);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {


        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        /* Now we perform our rendering */
        graphics.drawImage(getObraz(), 0, 0, null);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    private Image getObraz() {
        return this.obraz;
    }

}
