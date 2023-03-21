/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package szachy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author PatrykK
 */
class Drukarka_P implements Printable {

    char[][] pozycja = new char[8][8];
    boolean czybialy;
    Image obraz;
    ArrayList<String> odpis = new ArrayList<>();

    private Image konwert_na_grafike(char[][] ust, boolean ruchB) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(590, 835, BufferedImage.TYPE_INT_RGB);
        Graphics2D obraz = bufferedImage.createGraphics();
        BufferedImage[] figury = {(ImageIO.read(this.getClass().getResource("Wking005.png"))),
            (ImageIO.read(this.getClass().getResource("Wqueen05.png"))),
            (ImageIO.read(this.getClass().getResource("Wrook005.png"))),
            (ImageIO.read(this.getClass().getResource("Wbishop5.png"))),
            (ImageIO.read(this.getClass().getResource("Wknight5.png"))),
            (ImageIO.read(this.getClass().getResource("Wpawn005.png"))),
            (ImageIO.read(this.getClass().getResource("Bking005.png"))),
            (ImageIO.read(this.getClass().getResource("Bqueen05.png"))),
            (ImageIO.read(this.getClass().getResource("Brook005.png"))),
            (ImageIO.read(this.getClass().getResource("Bbishop5.png"))),
            (ImageIO.read(this.getClass().getResource("Bknight5.png"))),
            (ImageIO.read(this.getClass().getResource("Bpawn005.png")))};
        Image[] figury2 = new Image[12];
        for (int i = 0; i < 12; i++) {
            figury2[i] = figury[i].getScaledInstance(42, 42, Image.SCALE_DEFAULT);
        }
        obraz.setColor(Color.white);
        obraz.fillRect(0, 0, 590, 835);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println(ust[i][j]);
                if (i % 2 == 1) {
                    obraz.setColor(j % 2 == 0 ? new Color(244, 164, 96) : new Color(115, 69, 19));
                    obraz.fill(new Rectangle2D.Double(j * 56, (Math.abs(i - 7)) * 56, 56, 56));
                    switch (ust[i][j]) {
                        case 'K':
                            obraz.drawImage(figury2[0], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'k':
                            obraz.drawImage(figury2[6], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'Q':
                            obraz.drawImage(figury2[1], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'q':
                            obraz.drawImage(figury2[7], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'R':
                            obraz.drawImage(figury2[2], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'r':
                            obraz.drawImage(figury2[8], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'B':
                            obraz.drawImage(figury2[3], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'b':
                            obraz.drawImage(figury2[9], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'N':
                            obraz.drawImage(figury2[4], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'n':
                            obraz.drawImage(figury2[10], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'P':
                            obraz.drawImage(figury2[5], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'p':
                            obraz.drawImage(figury2[11], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                    }
                } else {
                    obraz.setColor(j % 2 == 1 ? new Color(244, 164, 96) : new Color(115, 69, 19));
                    obraz.fill(new Rectangle2D.Double(j * 56, (Math.abs(i - 7)) * 56, 56, 56));
                    switch (ust[i][j]) {
                        case 'K':
                            obraz.drawImage(figury2[0], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'k':
                            obraz.drawImage(figury2[6], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'Q':
                            obraz.drawImage(figury2[1], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'q':
                            obraz.drawImage(figury2[7], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'R':
                            obraz.drawImage(figury2[2], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'r':
                            obraz.drawImage(figury2[8], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'B':
                            obraz.drawImage(figury2[3], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'b':
                            obraz.drawImage(figury2[9], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'N':
                            obraz.drawImage(figury2[4], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'n':
                            obraz.drawImage(figury2[10], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'P':
                            obraz.drawImage(figury2[5], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'p':
                            obraz.drawImage(figury2[11], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                    }
                }
            }
        }
        obraz.setColor(ruchB == true ? Color.white : Color.black);
        obraz.fillOval(460, 410, 30, 30);
        obraz.setColor(Color.black);
        obraz.setStroke(new BasicStroke(2));
        obraz.drawOval(460, 410, 30, 30);
        obraz.drawString("8", 0, 46);
        obraz.drawString("7", 0, 102);
        obraz.drawString("6", 0, 158);
        obraz.drawString("5", 0, 214);
        obraz.drawString("4", 0, 270);
        obraz.drawString("3", 0, 326);
        obraz.drawString("2", 0, 382);
        obraz.drawString("1", 0, 438);
        obraz.drawString("A", 0, 448);
        obraz.drawString("B", 56, 448);
        obraz.drawString("C", 112, 448);
        obraz.drawString("D", 168, 448);
        obraz.drawString("E", 224, 448);
        obraz.drawString("F", 280, 448);
        obraz.drawString("G", 336, 448);
        obraz.drawString("H", 392, 448);
        obraz.dispose();
        /*String nazwa_obrazu = JOptionPane.showInputDialog("podaj nazwę obrazu");
        File file = new File(nazwa_obrazu+".png");
        ImageIO.write(bufferedImage, "png", file);*/
        return bufferedImage;
    }

    private Image konwert_na_grafike(char[][] ust, boolean ruchB, ArrayList<String> text) throws IOException {
        int y = 0;
        boolean dluga;
        int rozmiarX = (int) (text.size() / 100);
        dluga = rozmiarX / 100 > 3;
        rozmiarX = rozmiarX > 3 ? 3 : rozmiarX;
        System.out.println(rozmiarX);
        BufferedImage bufferedImage = new BufferedImage(590, 835, BufferedImage.TYPE_INT_RGB);
        Graphics2D obraz = bufferedImage.createGraphics();
        BufferedImage[] figury = {(ImageIO.read(this.getClass().getResource("Wking005.png"))),
            (ImageIO.read(this.getClass().getResource("Wqueen05.png"))),
            (ImageIO.read(this.getClass().getResource("Wrook005.png"))),
            (ImageIO.read(this.getClass().getResource("Wbishop5.png"))),
            (ImageIO.read(this.getClass().getResource("Wknight5.png"))),
            (ImageIO.read(this.getClass().getResource("Wpawn005.png"))),
            (ImageIO.read(this.getClass().getResource("Bking005.png"))),
            (ImageIO.read(this.getClass().getResource("Bqueen05.png"))),
            (ImageIO.read(this.getClass().getResource("Brook005.png"))),
            (ImageIO.read(this.getClass().getResource("Bbishop5.png"))),
            (ImageIO.read(this.getClass().getResource("Bknight5.png"))),
            (ImageIO.read(this.getClass().getResource("Bpawn005.png")))};
        Image[] figury2 = new Image[12];
        for (int i = 0; i < 12; i++) {
            figury2[i] = figury[i].getScaledInstance(42, 42, Image.SCALE_DEFAULT);
        }
        obraz.setColor(Color.white);
        obraz.fillRect(0, 0, 590, 835);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println(ust[i][j]);
                if (i % 2 == 1) {
                    obraz.setColor(j % 2 == 0 ? new Color(244, 164, 96) : new Color(115, 69, 19));
                    obraz.fill(new Rectangle2D.Double(j * 56, (Math.abs(i - 7)) * 56, 56, 56));
                    switch (ust[i][j]) {
                        case 'K':
                            obraz.drawImage(figury2[0], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'k':
                            obraz.drawImage(figury2[6], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'Q':
                            obraz.drawImage(figury2[1], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'q':
                            obraz.drawImage(figury2[7], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'R':
                            obraz.drawImage(figury2[2], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'r':
                            obraz.drawImage(figury2[8], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'B':
                            obraz.drawImage(figury2[3], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'b':
                            obraz.drawImage(figury2[9], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'N':
                            obraz.drawImage(figury2[4], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'n':
                            obraz.drawImage(figury2[10], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'P':
                            obraz.drawImage(figury2[5], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'p':
                            obraz.drawImage(figury2[11], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                    }
                } else {
                    obraz.setColor(j % 2 == 1 ? new Color(244, 164, 96) : new Color(115, 69, 19));
                    obraz.fill(new Rectangle2D.Double(j * 56, (Math.abs(i - 7)) * 56, 56, 56));
                    switch (ust[i][j]) {
                        case 'K':
                            obraz.drawImage(figury2[0], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'k':
                            obraz.drawImage(figury2[6], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'Q':
                            obraz.drawImage(figury2[1], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'q':
                            obraz.drawImage(figury2[7], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'R':
                            obraz.drawImage(figury2[2], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'r':
                            obraz.drawImage(figury2[8], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'B':
                            obraz.drawImage(figury2[3], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'b':
                            obraz.drawImage(figury2[9], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'N':
                            obraz.drawImage(figury2[4], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'n':
                            obraz.drawImage(figury2[7], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'P':
                            obraz.drawImage(figury2[5], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                        case 'p':
                            obraz.drawImage(figury2[11], (j) * 56 + 7, ((Math.abs(i - 7)) * 56 + 7), null);
                            break;
                    }
                }
            }
        }
        obraz.setColor(ruchB == true ? Color.white : Color.black);
        obraz.fillOval(460, 410, 30, 30);
        obraz.setColor(Color.black);
        obraz.setStroke(new BasicStroke(2));
        obraz.drawOval(460, 410, 30, 30);
        int x = 0;
        obraz.setFont(new Font("Courier New", Font.PLAIN, 10));
        int temp = 1;
        for (int i = 0; i < text.size(); i++) {
            if (i % 2 == 0) {
                obraz.drawString((String.valueOf(temp) + "." + text.get(i)), 0 + x * 135, (i == 0) ? 460 : (i / 2) * 10 - (250 * x) + 460);
            } else {
                obraz.drawString(text.get(i), 80 + x * 135, (i != 1) ? ((i - 1) / 2) * 10 - (250 * x) + 460 : 460);
                temp++;
            }
            if (((i + 1) % 50 == 0 && i > 0)) {
                x = x + 1;
            }
            /* switch (i % 8) {
                case 0:
                    obraz.drawString((String.valueOf(temp) + "." + text.get(i)), 0 + 0 * 135, (i <8) ? 460 : (10 * x) + 460);
                    break;
                case 1:
                    obraz.drawString(text.get(i), 80 + 0 * 135, (i>=8) ? (10 * x) + 460 : 460);
                    temp++;
                    break;
                    case 2:
                    obraz.drawString((String.valueOf(temp) + "." + text.get(i)), 0 + 1 * 135, (i <8) ? 460 : (10 * x) + 460);
                    break;
                case 3:
                    obraz.drawString(text.get(i), 80 + 1 * 135, (i>=8) ? (10 * x) + 460 : 460);
                    temp++;
                    break;
                    case 4:
                    obraz.drawString((String.valueOf(temp) + "." + text.get(i)), 0 + 2 * 135, (i <8) ? 460 :  (10 * x) + 460);
                    break;
                case 5:
                    obraz.drawString(text.get(i), 80 + 2 * 135, (i>=8) ? (10 * x) + 460 : 460);
                    temp++;
                    break;
                    case 6:
                    obraz.drawString((String.valueOf(temp) + "." + text.get(i)), 0 + 3 * 135, (i <8) ? 460 : (10 * x) + 460);
                    break;
                case 7:
                    obraz.drawString(text.get(i), 80 + 3 * 135, (i>=8) ? (10 * x) + 460 : 460);
                    temp++;
                    break;
            }
            if (((i + 1) % 8 == 0 && i > 0)) {
                x = x + 1;
            }*/
        }

        obraz.drawString("8", 0, 46);
        obraz.drawString("7", 0, 102);
        obraz.drawString("6", 0, 158);
        obraz.drawString("5", 0, 214);
        obraz.drawString("4", 0, 270);
        obraz.drawString("3", 0, 326);
        obraz.drawString("2", 0, 382);
        obraz.drawString("1", 0, 438);
        obraz.drawString("A", 0, 448);
        obraz.drawString("B", 56, 448);
        obraz.drawString("C", 112, 448);
        obraz.drawString("D", 168, 448);
        obraz.drawString("E", 224, 448);
        obraz.drawString("F", 280, 448);
        obraz.drawString("G", 336, 448);
        obraz.drawString("H", 392, 448);
        obraz.dispose();
        //String nazwa_obrazu = JOptionPane.showInputDialog("podaj nazwę obrazu");
        //File file = new File(nazwa_obrazu+".png");
        //ImageIO.write(bufferedImage, "png", file);
        return bufferedImage;
    }

    public Image getObraz() {
        return obraz;
    }

    

    Drukarka_P(char[][] ust, boolean ruchB, ArrayList<String> ruchy_literowe) throws IOException {
        obraz = konwert_na_grafike(ust, ruchB, ruchy_literowe);
    }

    Drukarka_P(char[][] ust, boolean ruchB) throws IOException {
        obraz = konwert_na_grafike(ust, ruchB);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

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

   

}
