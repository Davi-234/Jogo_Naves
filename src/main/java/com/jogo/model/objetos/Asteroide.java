package com.jogo.model.objetos;

import com.jogo.model.Entidade;
import com.jogo.regras.estados.Estado_Direcao;

public class Asteroide extends Entidade{
    
    public Asteroide(int x, int y, int largura, int altura, Estado_Direcao direcao) {
        super(x, y, largura, altura);
        vida = 50;
        velocidade = 2;
        setSprite("/objetos/meteoro.png");
        dano = 5;
        this.direcao = direcao;
    }

    @Override
    public void atualizacaoPos_colisao(int dano) {
        vida -= dano;   
    }
    
    public void uptade(){
        if (direcao == Estado_Direcao.NORTE) {
            posY--;
        }
        if (direcao == Estado_Direcao.SUL) {
            posY++;
        }
        if (direcao == Estado_Direcao.OESTE) {
            posX++;
        }
        if (direcao == Estado_Direcao.LESTE) {
            posX--;
        }
    }
    
}
