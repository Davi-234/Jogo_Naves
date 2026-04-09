package com.jogo.model.projetil;

import com.jogo.model.Entidade;
import com.jogo.regras.colisao.HitBox;
import com.jogo.regras.estados.Estado_Direcao;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Projetil extends Entidade {

    public boolean acertou = false;

    private static final int OFFSET_CANHAO_X = 5;
    private static final int OFFSET_CANHAO_Y = 2;

    public Projetil(int x, int y, float ajuste_referente_a_escala_X, float ajuste_referente_a_escala_Y, Estado_Direcao direcao) {
        super(0, 0, (2 * 2), (5 * 2));
        dano = 10;
        this.calculoPos(x, y, ajuste_referente_a_escala_X, ajuste_referente_a_escala_Y);
        sprite = new ImageIcon(getClass().getResource("/objetos/projetel.png")).getImage();
        velocidade = 10;

        this.ajuste_referente_a_escala_X = largura / 2;
        this.ajuste_referente_a_escala_Y = altura / 5;

        this.direcao = direcao;
        
        if (direcao == Estado_Direcao.NORTE || direcao == Estado_Direcao.SUL) {
            altura = (int) this.ajuste_referente_a_escala_Y * 5;
            largura = (int) this.ajuste_referente_a_escala_X * 2;
        } 
        else if (direcao == Estado_Direcao.LESTE || direcao == Estado_Direcao.OESTE) {
            largura = (int) this.ajuste_referente_a_escala_Y * 5;
            altura = (int) this.ajuste_referente_a_escala_X * 2;
        }
    }

    public HitBox getHitBox() {
        return new HitBox(posX, posY, largura, altura);
    }

    public void desenharSprite(Graphics2D g) {
        g.drawImage(sprite, posX, posY, largura, altura, null);
    }

    public void mover() {

        //if (direcao == null) {
        //  direcao = direcao_atirador;
        //}
        
        if (direcao == Estado_Direcao.NORTE) {
            moverCima();
        }
        if (direcao == Estado_Direcao.SUL) {
            moverBaixo();
        }
        if (direcao == Estado_Direcao.LESTE) {
            moverDireita();
        }
        if (direcao == Estado_Direcao.OESTE) {
            moverEsquerda();
        }
        if (direcao == Estado_Direcao.SUDESTE) {

        }
        if (direcao == Estado_Direcao.SUDOESTE) {

        }
        if (direcao == Estado_Direcao.NORDESTE) {

        }
        if (direcao == Estado_Direcao.NOROESTE) {

        }
    }

    private void moverCima() {
        posY -= velocidade;
    }

    private void moverBaixo() {
        posY += velocidade;
    }

    private void moverEsquerda() {
        posX -= velocidade;
    }

    private void moverDireita() {
        posX += velocidade;
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
