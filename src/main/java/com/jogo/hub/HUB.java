package com.jogo.hub;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class HUB {
    private Image hearthHUB;
    private final Image pauseHUB = new ImageIcon(getClass().getResource("/hub/telas/pause.png")).getImage();
    
    private int posX_HUB_Vida = 100;
    
    private int posX_Pause = 102;
    private int posY_Pause = 102;

    public HUB(int vida, int tela_x, int tela_y) {        
        if (tela_x > 0) {
            posX_HUB_Vida = tela_x - (47 * 4); // este 4 multiplica a largura do sprite, mas aqui serve para coloca-lo na posição correta.
            posX_Pause = (int) tela_x * (1/2);
            posY_Pause = (int) tela_y * (1/2);
        }

        if (vida > 200 && vida <= 225) {
            hearthHUB = new ImageIcon(getClass().getResource("/hub/coracao/coracaocheio.png")).getImage();
        } else if (vida > 175 && vida <= 200) {
            hearthHUB = new ImageIcon(getClass().getResource("/hub/coracao/coracao3meio.png")).getImage();
        } else if (vida > 150 && vida <= 175) {
            hearthHUB = new ImageIcon(getClass().getResource("/hub/coracao/coracao3.png")).getImage();
        } else if (vida > 125 && vida <= 150) {
            hearthHUB = new ImageIcon(getClass().getResource("/hub/coracao/coracao2meio.png")).getImage();
        } else if (vida > 100 && vida <= 125) {
            hearthHUB = new ImageIcon(getClass().getResource("/hub/coracao/coracao2.png")).getImage();
        } else if (vida > 75 && vida <= 100) {
            hearthHUB = new ImageIcon(getClass().getResource("/hub/coracao/coracao1meio.png")).getImage();
        } else if (vida > 50 && vida <= 75) {
            hearthHUB = new ImageIcon(getClass().getResource("/hub/coracao/coracao1.png")).getImage();
        } else if (vida > 0 && vida <= 50) {
            hearthHUB = new ImageIcon(getClass().getResource("/hub/coracao/coracaomeio.png")).getImage();
        } else if (vida <= 0) {
            hearthHUB = new ImageIcon(getClass().getResource("/hub/coracao/coracao0.png")).getImage();
        }
    }
    
    public void getHUB_Pause(Graphics2D g2, ImageObserver observer) {
        g2.drawImage(pauseHUB, posX_Pause, posY_Pause, 500, 500, observer);
    }
    
    public void getLife_HUB(Graphics2D g2, ImageObserver observer) {
        g2.drawImage(hearthHUB, posX_HUB_Vida, 0, (47 * 4), (12 * 4), observer);
    }
}
