package com.jogo.model;

import com.jogo.regras.colisao.HitBox;
import com.jogo.regras.estados.Estado_Direcao;
import com.jogo.regras.estados.Estado_comportamento;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Entidade {
    public int posX;
    public int posY;
    
    public int largura;
    public int altura;
    
    public int velocidade;
    
    public Image sprite;
    
    public float ajuste_referente_a_escala_X;
    public float ajuste_referente_a_escala_Y;
    
    public int vida;
    public int dano;
    
    public Estado_Direcao direcao;
    public Estado_comportamento estado;
    
    public Entidade (int x, int y, int largura, int altura){
        this.posX = x;
        this.posY = y;
        this.largura = largura;
        this.altura = altura;
    }
        
    public HitBox getHitBox() {
        return new HitBox(posX, posY, largura, altura);
    }
        
    public void desenharSprite(Graphics2D g) {
        g.drawImage(sprite, posX, posY, largura, altura, null);
    }
    
    public void setSprite(String caminho){
        sprite = new ImageIcon(getClass().getResource(caminho)).getImage();
    }

    public abstract void atualizacaoPos_colisao(int dano);
    
}
