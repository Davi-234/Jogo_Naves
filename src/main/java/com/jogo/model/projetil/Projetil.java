package com.jogo.model.projetil;

import com.jogo.model.Entity;
import com.jogo.regras.colisao.HitBox;
import com.jogo.regras.estados.Estado_Direcao;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Projetil extends Entity {

    public boolean acertou = false;

    private static final int OFFSET_CANHAO_X = 5;
    private static final int OFFSET_CANHAO_Y = 2;

    public Projetil(int x, int y, float ajuste_referente_a_escala_X, float ajuste_referente_a_escala_Y, Estado_Direcao directionShooter) {
        super(0, 0, (2 * 2), (5 * 2), 2, 5);
        damege = 10;
        this.calculoPos(x, y, ajuste_referente_a_escala_X, ajuste_referente_a_escala_Y);
        sprite = new ImageIcon(getClass().getResource("/objetos/projetel.png")).getImage();
        speed = 10;

        this.escala_width = width / 2;
        this.escala_height = height / 5;

        this.direction = directionShooter;
        
        reformular_Posicoes();
    }
    
    @Override
    public HitBox getHitBox() {
        return new HitBox(posX, posY, width, height);
    }

    @Override
    public void desenharSprite(Graphics2D g) {
        g.drawImage(sprite, posX, posY, width, height, null);
    }

    public void mover() {

        //if (direcao == null) {
        //  direcao = direcao_atirador;
        //}
        
        if (direction == Estado_Direcao.NORTE) {
            moverCima();
        }
        if (direction == Estado_Direcao.SUL) {
            moverBaixo();
        }
        if (direction == Estado_Direcao.LESTE) {
            moverDireita();
        }
        if (direction == Estado_Direcao.OESTE) {
            moverEsquerda();
        }
        if (direction == Estado_Direcao.SUDESTE) {

        }
        if (direction == Estado_Direcao.SUDOESTE) {

        }
        if (direction == Estado_Direcao.NORDESTE) {

        }
        if (direction == Estado_Direcao.NOROESTE) {

        }
    }

    private void moverCima() {
        posY -= speed;
    }

    private void moverBaixo() {
        posY += speed;
    }

    private void moverEsquerda() {
        posX -= speed;
    }

    private void moverDireita() {
        posX += speed;
    }

    private void calculoPos(int x, int y, float ajuste_referente_a_escala_X, float ajuste_referente_a_escala_Y) {
        posX = Math.round((x) + (OFFSET_CANHAO_X * ajuste_referente_a_escala_X));
        posY = Math.round(((y) + (OFFSET_CANHAO_Y * ajuste_referente_a_escala_Y)));
    }

    @Override
    public void atualizacaoPos_colisao(int dano) {
        acertou = true;
    }
}
