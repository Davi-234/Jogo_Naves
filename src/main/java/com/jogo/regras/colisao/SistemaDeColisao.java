package com.jogo.regras.colisao;

import com.jogo.model.Entity;
import com.jogo.model.inimigos.Americano;
import com.jogo.model.jogadores.Player;
import com.jogo.model.objetos.Asteroide;
import com.jogo.model.projetil.Projetil;
import java.util.List;

public class SistemaDeColisao {

    public static void allCollisons(Player player, List<Americano> inimigos, List<Asteroide> asteroide) {
        collison_Armada_Player(inimigos, player);
        collison_Player_Armada(inimigos, player);
        collison_entre_Armada(inimigos);
        collison_asteroide_Armada(inimigos, asteroide);
        collison_Player_Asteroide(asteroide, player);
    }

    private static void collisons(Entity a, Entity b) {
        if (a.getHitBox().isColling(b.getHitBox())) {
            a.atualizacaoPos_colisao(b.damege);
            b.atualizacaoPos_colisao(a.damege);
        }
    }

    //Verefica se o player atingiu um inimigo da armada;
    protected static void collison_Player_Armada(List<Americano> inimigos, Player player) {
        for (Projetil disparo : player.disparos) {
            for (Americano a : inimigos) {
                SistemaDeColisao.collisons(disparo, a);
            }
        }
    }

    //Verefica se o player foi atingido por um inimigo da armada;
    protected static void collison_Armada_Player(List<Americano> inimigos, Player player) {
        for (Americano americanos : inimigos) {
            for (Projetil proj : americanos.disparos) {
                SistemaDeColisao.collisons(proj, player);
            }
        }
    }

    //Verefica se houve colisão entres inimigos da armada;
    //Isso é O(n)²
    protected static void collison_entre_Armada(List<Americano> inimigos) {
        for (int h = 0; h < inimigos.size(); h++) {
            for (int i = 0; i < inimigos.size(); i++) {
                Americano a = inimigos.get(h);
                Americano b = inimigos.get(i);

                if (a.getHitBox().isColling(b.getHitBox())) {
                    if (a.posX > b.posX) {
                        a.posX -= 2;
                        b.posX += 2;
                    } else {
                        a.posX += 2;
                        b.posX -= 2;
                    }

                    if (a.posY > b.posY) {
                        a.posY -= 2;
                        b.posY += 2;
                    } else {
                        a.posY += 2;
                        b.posY -= 2;
                    }
                }
            }
        }
    }

    protected static void collison_Player_Asteroide(List<Asteroide> asteroide, Player jogador) {

    }

    protected static void collison_asteroide_Armada(List<Americano> inimigos, List<Asteroide> asteroide) {

    }

    protected static void collison_entre_asteroides(List<Asteroide> asteroide) {

    }

    //adicione as colisões que surgiram;
}
