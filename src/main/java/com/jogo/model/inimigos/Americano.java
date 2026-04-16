package com.jogo.model.inimigos;

import com.jogo.model.jogadores.Jogador;
import com.jogo.model.projetil.Projetil;
import com.jogo.regras.estados.Estado_Direcao;
import com.jogo.sonoro.Sons;
import java.util.Iterator;
import java.util.Random;

public class Americano extends Inimigo{
    public Americano(int x, int y) {
        super(x, y, (32*2), (32*2)); 
        setSprite("/personagens/inimigos/americanos.png");
        valorEmPts = 100;
        vida  = 50;
        ajuste_referente_a_escala_X = largura/32;
        ajuste_referente_a_escala_Y = altura/32; // o sptite é 32 * 32
        direcao = Estado_Direcao.SUL;
    }

    //-----------------------------Lógica de Atirar---------------------------//
    private final int cooldownTiro = 300;
    private int tempo = 0;
    
    private void addLista() {
        if (new Random().nextBoolean()){
            disparos.add(new Projetil(posX, (posY + altura), ajuste_referente_a_escala_X, ajuste_referente_a_escala_Y, direcao));
        } else {
            disparos.add(new Projetil((posX + Math.round(16 * ajuste_referente_a_escala_X)), (posY + altura), ajuste_referente_a_escala_X, ajuste_referente_a_escala_Y, direcao));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão        
        }
    }
    
    @Override
    public void atirar(){        
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
    public void uptade(Jogador player) {
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
    
    private void IA(Jogador player){
        if (campoDvisao(player)) {
            atirar();
        }
    }
    
    //------------------------------------------------------------------------//
    private boolean campoDvisao(Jogador player){
        return player.posY > this.posY && Math.abs(player.posX - this.posX) < 50;
    }

    @Override
    public void atualizacaoPos_colisao(int dano){
        vida -= dano;
    }
    
}
