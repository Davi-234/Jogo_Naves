package com.jogo.model.inimigos;

import com.jogo.model.jogadores.Player;
import com.jogo.model.projetil.Projetil;
import com.jogo.regras.estados.Estado_Direcao;
import com.jogo.regras.estados.Estado_comportamento;
import com.jogo.sonoro.Sons;
import java.util.Iterator;
import java.util.Random;

public class Americano extends Inimigo{
    public Americano(int x, int y) {
        super(x, y, (32*2), (32*2), 32, 32); 
        setSprite("/personagens/inimigos/americanos.png");
        valorEmPts = 100;
        lifePoints  = 50;
        escala_width = width/32;
        escala_height = height/32; // o sptite é 32 * 32
        direction = Estado_Direcao.SUL;
    }

    //-----------------------------Lógica de Atirar---------------------------//
    private final int cooldownTiro = 300;
    private int tempo = 0;
    
    private void addLista() {
        if (new Random().nextBoolean()){
            disparos.add(new Projetil(posX, (posY + height), escala_width, escala_height, direction));
        } else {
            disparos.add(new Projetil((posX + Math.round(16 * escala_width)), (posY + height), escala_width, escala_height, direction));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão        
        }
    }
    
    @Override
    public void shot(){        
        if (tempo <= 0) {
            addLista();
            Sons.disparo();
            tempo = cooldownTiro;
        }
    }
    
    private void reniciaCooldown(){
        if (tempo > 0){
            tempo -= 10;
        }
    }
    //------------------------------------------------------------------------//
    
    @Override
    public void uptade(Player player) {
        reniciaCooldown();

        //IA do americano
        IA(player);
        
        //----------Elemina os disparos que estão como lixo de mémoria--------//
        // Pede à lista de projéteis um iterador.
        // O iterador é um objeto que sabe percorrer a lista
        // sem perder a referência interna dos elementos.
        Iterator<Projetil> it = disparos.iterator();

        // Enquanto ainda existir pelo menos um elemento
        // que não foi visitado pelo iterador...
        while (it.hasNext()) {
            // Avança o iterador para o próximo elemento
            // e devolve esse elemento.
            Projetil p = it.next();
            p.mover();
            //p.moverCima();

            if (p.acertou) {
                it.remove(); // morte limpa, sem exceção
            }
        }
        //--------------------------------------------------------------------//
    }
    
    private void IA(Player player){
        if (isSeeing(player)) {
            shot();
            state_behavier = Estado_comportamento.ATACANDO;
        }
        
        if (!player.disparos.isEmpty()&& isSeeing(player)) {
            state_behavier = Estado_comportamento.FUGINDO;
        }
        //Adicionar algoritmo inimigo mover-se para uma posição distante do player para assim evitar ser acertado; o que ocorrerá após que pelo menos um inimigo tenha morrido.
        //Se um inimigo estiver fugindo do jogador; o inimigo mais próximo irá para o campo de visão do jogador e atacará;
    }
    
    //------------------------------------------------------------------------//
    private boolean isSeeing(Player player){
        return player.posY > this.posY && Math.abs(player.posX - this.posX) < 50;
    }

    @Override
    public void atualizacaoPos_colisao(int dano){
        lifePoints -= dano;
    }
    
}
