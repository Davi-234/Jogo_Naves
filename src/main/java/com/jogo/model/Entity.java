package com.jogo.model;

import com.jogo.regras.colisao.HitBox;
import com.jogo.regras.estados.Estado_Direcao;
import com.jogo.regras.estados.Estado_comportamento;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Entity {
    
    public int posX;
    public int posY;

    public int width;
    public int height;

    public int speed;

    public Image sprite;

    public float escala_width;
    public float escala_height;

    public int lifePoints;
    public int damege;
    
    private int width_sprite;
    private int height_sprite;
    
    public Estado_Direcao direction;
    public Estado_comportamento state_behavier;

    public Entity(int x, int y, int largura, int altura, int x_sprite, int y_sprite) {
        this.posX = x;
        this.posY = y;
        this.width = largura;
        this.height = altura;
        width_sprite = x_sprite;
        height_sprite = y_sprite;
    }

    public HitBox getHitBox() {
        return new HitBox(posX, posY, width, height);
    }

    public void desenharSprite(Graphics2D g) {
        g.drawImage(sprite, posX, posY, width, height, null);
    }

    public void setSprite(String url) {
        sprite = new ImageIcon(getClass().getResource(url)).getImage();
    }

    public abstract void atualizacaoPos_colisao(int damege);

    public void reformular_Posicoes() {
        if (direction == Estado_Direcao.NORTE || direction == Estado_Direcao.SUL) {
            height = Math.round(escala_height *  height_sprite); //erro de tamanho dos sprites
            width = Math.round(escala_width * width_sprite);
        } else {
            height = Math.round(escala_width * width_sprite);
            width = Math.round(escala_height * height_sprite);
        }
    }
}
