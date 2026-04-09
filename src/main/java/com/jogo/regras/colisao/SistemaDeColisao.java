package com.jogo.regras.colisao;

import com.jogo.model.Entidade;
import com.jogo.model.inimigos.Americano;
import com.jogo.model.jogadores.Jogador;
import com.jogo.model.objetos.Asteroide;
import com.jogo.model.projetil.Projetil;
import java.util.List;

public class SistemaDeColisao {
    public static void allColisoes(Jogador player, List<Americano> inimigos, List<Asteroide> asteroide){
        colisao_Armada_Player(inimigos, player);
        colisao_Player_Armada(inimigos, player);
        colisao_entre_Armada(inimigos);
        colisao_asteroide_Armada(inimigos, asteroide);
    }
    
    private static void colisoes(Entidade a, Entidade b) {
        if (a.getHitBox().colide(b.getHitBox())) {
            a.atualizacaoPos_colisao(b.dano);
            b.atualizacaoPos_colisao(a.dano);
        }
    }
    
    //Verefica se o player atingiu um inimigo da armada;
    protected static void colisao_Player_Armada(List<Americano> inimigos, Jogador player) {
        for (Projetil disparo : player.disparos) {
            for (Americano a : inimigos) {
                SistemaDeColisao.colisoes(disparo, a);
            }
        }
    }
    
    //Verefica se o player foi atingido por um inimigo da armada;
    protected static void colisao_Armada_Player(List<Americano> inimigos, Jogador player) {
        for (Americano americanos : inimigos) {
            for (Projetil proj : americanos.disparos) {
                SistemaDeColisao.colisoes(proj, americanos);
            }
        }
    }
    
    //Verefica se o player foi atingido por um inimigo da armada;
    protected static void colisao_entre_Armada(List<Americano> inimigos) {
        
    }
    
    protected static void colisao_Jogador_asteroide(List<Asteroide> asteroide, Jogador jogador) {
        
    }
    
    protected static void colisao_asteroide_Armada(List<Americano> inimigos, List<Asteroide> asteroide) {
        
    }
    
    protected static void colisao_entre_asteroides(List<Asteroide> asteroide) {
        
    }
    
    //adicione as colisões que surgiram;
    
}