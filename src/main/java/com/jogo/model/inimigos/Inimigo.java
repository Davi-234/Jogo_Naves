package com.jogo.model.inimigos;

import com.jogo.model.Entity;
import com.jogo.model.jogadores.Player;
import com.jogo.model.projetil.Projetil;
import com.jogo.regras.Atirador;
import java.util.ArrayList;
import java.util.List;

public abstract class Inimigo extends Entity implements Atirador{        
    public List<Projetil> disparos;
    public int valorEmPts;
    
    public Inimigo(int x, int y, int largura, int altura, int x_sprite, int y_sprite) {
        super(x, y, largura, altura, x_sprite, y_sprite);
        disparos = new ArrayList<>();
    }
    
    public abstract void uptade(Player player);
    
}
