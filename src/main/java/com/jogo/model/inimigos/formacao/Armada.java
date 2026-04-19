package com.jogo.model.inimigos.formacao;

import com.jogo.model.inimigos.Americam;
import com.jogo.model.jogadores.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Armada {
    public List<Americam> armada;
    
    public Armada(int X_tela, int Y_tela) {
        armada = new ArrayList<>();
        //as formações são chamadas no construtor, sendo escolhido
        
        //deve ter um algoritmo que calcula quantas naves podem ser distrbuidas pelo eixo X da tela
        int areaUtil = X_tela - (ESP * 2);
        int limite = areaUtil / LRG_NAV;
        
        for (int a = 0;a < limite; a++)
             addA_Lista(formation_line(a));
    }
    
    private static final int ESP = 35;
    private static final int LRG_NAV = 90;
    
    //As formações são feitas por funções, ou metódos complexos
    private int formation_line(int indicie) {
        /*
        * esp (espaçamento)
        * lrg_Nav (largura da nave)
        * indice (x, variavel)
        */
        return ESP + (LRG_NAV * indicie); //A função que calcula a posição 'x' da nave;
    }

    private void addA_Lista(int x) {
        armada.add(new Americam(x, 47)); // 47 é o espaçamento do inicio da tela;
    }

    //------------------------------------------------------------------------//
    public void uptade(Player player) {
        //elemina a nave que não tem mais vida
        Iterator<Americam> it = armada.iterator();

        while (it.hasNext()) {
            Americam a = it.next();
            
            if (a.lifePoints <= 0) {
                player.ganharPontos(a.valorEmPts);
                it.remove();
            }
        }

        //verefica se o inimigo foi acertado por um projétil do jogador, e tira a vida dele
        for (Americam americano : armada) {
            Americam prox;
            
            if (it.hasNext()) {
                prox = it.next();    
            }
            
            americano.uptade(player);
        }
    }
    
    public int getArmadaSize(){
        return armada.size();
    }
}
