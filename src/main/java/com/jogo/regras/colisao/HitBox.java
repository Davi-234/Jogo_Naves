package com.jogo.regras.colisao;

public record HitBox (int x, int y, int largura, int altura) implements Collides{

    @Override
    public boolean isCollided(HitBox a) {
        return x < (a.x + a.largura) && (x + largura) > a.x &&
               y < (a.y + a.altura) && (y + altura) > a.y; 
    }
    
}
