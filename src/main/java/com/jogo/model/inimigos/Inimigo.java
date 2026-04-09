package com.jogo.model.inimigos;

import com.jogo.model.Entidade;
import com.jogo.model.jogadores.Jogador;
import com.jogo.model.projetil.Projetil;
import com.jogo.regras.Atirador;
import java.util.ArrayList;
import java.util.List;

public abstract class Inimigo extends Entidade implements Atirador{        
    public List<Projetil> disparos;
    public int valorEmPts;
    
    public Inimigo(int x, int y, int largura, int altura) {
        super(x, y, largura, altura);
        disparos = new ArrayList<>();
    }
    
    public abstract void uptade(Jogador player);
    
}
