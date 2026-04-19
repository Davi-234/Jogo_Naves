package com.jogo.model.objetos;

import com.jogo.model.Entity;
import com.jogo.regras.estados.Estado_Direcao;

public class Asteroide extends Entity{
    
    public Asteroide(int x, int y, int largura, int altura, Estado_Direcao direcao) {
        super(x, y, largura, altura, 38,29);
        lifePoints = 50;
        speed = 2;
        setSprite("/objetos/meteoro.png");
        damege = 5;
        this.direction = direcao;
    }

    @Override
    public void atualizacaoPos_colisao(int dano) {
        lifePoints -= dano;   
    }
    
    public void uptade(){
        if (direction == Estado_Direcao.NORTE) {
            posY--;
        }
        if (direction == Estado_Direcao.SUL) {
            posY++;
        }
        if (direction == Estado_Direcao.OESTE) {
            posX++;
        }
        if (direction == Estado_Direcao.LESTE) {
            posX--;
        }
    }
    
}
