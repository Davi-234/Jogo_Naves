package com.jogo.model.inimigos;

import com.jogo.model.Entity;
import com.jogo.model.jogadores.Player;
import com.jogo.model.projetil.Projectile;
import java.util.ArrayList;
import java.util.List;
import com.jogo.regras.Sniper;

public abstract class Enemy extends Entity implements Sniper{        
    public List<Projectile> disparos;
    public int valorEmPts;
    
    public Enemy(int x, int y, int largura, int altura, int x_sprite, int y_sprite) {
        super(x, y, largura, altura, x_sprite, y_sprite);
        disparos = new ArrayList<>();
    }
    
    public abstract void uptade(Player player);
    
}
