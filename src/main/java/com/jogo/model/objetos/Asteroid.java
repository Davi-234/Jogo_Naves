package com.jogo.model.objetos;

import com.jogo.model.Entity;
import com.jogo.regras.estados.Direction;

public class Asteroid extends Entity{
    
    public Asteroid(int x, int y, int largura, int altura, Direction direcao) {
        super(x, y, largura, altura, 38,29);
        lifePoints = 50;
        speed = 2;
        setSprite("/objetos/meteoro.png");
        damege = 5;
        this.direction = direcao;
    }

    @Override
    public void uptade_collison(int damege) {
        lifePoints -= damege;   
    }
    
    public void uptade(){
        if (direction == Direction.NORTE) {
            posY--;
        }
        if (direction == Direction.SUL) {
            posY++;
        }
        if (direction == Direction.OESTE) {
            posX++;
        }
        if (direction == Direction.LESTE) {
            posX--;
        }
    }
    
}
