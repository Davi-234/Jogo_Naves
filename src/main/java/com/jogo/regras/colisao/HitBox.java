package com.jogo.regras.colisao;

public record HitBox (int x, int y, int largura, int altura) implements Colide{

    @Override
    public boolean colide(HitBox a) {
        return x < (a.x + a.largura) && (x + largura) > a.x &&
               y < (a.y + a.altura) && (y + altura) > a.y; 
    }
    
}
