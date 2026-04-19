package com.jogo.regras.colisao;

import com.jogo.model.Entity;
import com.jogo.model.inimigos.Americam;
import com.jogo.model.jogadores.Player;
import com.jogo.model.objetos.Asteroid;
import com.jogo.model.projetil.Projectile;
import java.util.List;

public class Systeam_Collisons {

    public static void allCollisons(Player player, List<Americam> inimigos, List<Asteroid> asteroide) {
        collison_Armada_Player(inimigos, player);
        collison_Player_Armada(inimigos, player);
        collison_entre_Armada(inimigos);
        collison_asteroide_Armada(inimigos, asteroide);
        collison_Player_Asteroide(asteroide, player);
    }

    private static void collisons(Entity a, Entity b) {
        if (a.getHitBox().isCollided(b.getHitBox())) {
            a.uptade_collison(b.damege);
            b.uptade_collison(a.damege);
        }
    }

    //Verefica se o player atingiu um inimigo da armada;
    protected static void collison_Player_Armada(List<Americam> inimigos, Player player) {
        for (Projectile disparo : player.disparos) {
            for (Americam a : inimigos) {
                Systeam_Collisons.collisons(disparo, a);
            }
        }
    }

    //Verefica se o player foi atingido por um inimigo da armada;
    protected static void collison_Armada_Player(List<Americam> inimigos, Player player) {
        for (Americam americanos : inimigos) {
            for (Projectile proj : americanos.disparos) {
                Systeam_Collisons.collisons(proj, player);
            }
        }
    }

    //Verefica se houve colisão entres inimigos da armada;
    //Isso é O(n)²
    protected static void collison_entre_Armada(List<Americam> inimigos) {
        for (int h = 0; h < inimigos.size(); h++) {
            for (int i = 0; i < inimigos.size(); i++) {
                Americam a = inimigos.get(h);
                Americam b = inimigos.get(i);

                if (a.getHitBox().isCollided(b.getHitBox())) {
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

    protected static void collison_Player_Asteroide(List<Asteroid> asteroide, Player jogador) {

    }

    protected static void collison_asteroide_Armada(List<Americam> inimigos, List<Asteroid> asteroide) {

    }

    protected static void collison_entre_asteroides(List<Asteroid> asteroide) {

    }

    //adicione as colisões que surgiram;
}
