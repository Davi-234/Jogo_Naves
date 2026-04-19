package com.jogo.model.inimigos;

import com.jogo.model.jogadores.Player;
import com.jogo.model.projetil.Projectile;
import com.jogo.regras.estados.Direction;
import static com.jogo.regras.estados.Direction.LESTE;
import static com.jogo.regras.estados.Direction.NORTE;
import static com.jogo.regras.estados.Direction.OESTE;
import static com.jogo.regras.estados.Direction.SUL;
import com.jogo.regras.estados.Behavior;
import com.jogo.sonoro.Sounds;
import java.util.Iterator;
import java.util.Random;

public class Americam extends Enemy{
    public Americam(int x, int y) {
        super(x, y, (32*2), (32*2), 32, 32); 
        setSprite("/personagens/inimigos/americanos.png");
        valorEmPts = 100;
        lifePoints  = 50;
        escala_width = width/32;
        escala_height = height/32; // o sptite é 32 * 32
        direction = Direction.SUL;
    }

    //-----------------------------Lógica de Atirar---------------------------//
    private final int cooldownTiro = 300;
    private int tempo = 0;
    
    private static final int OFESET_ALTURA_ATE_CANHAO = 5;
    private static final int OFESET_DISTANCIA_X_DOS_CANHOES = 18;
    
    private void addLista() {
        Random r = new Random();
        if (null != direction) {
            switch (direction) {
                case NORTE -> {
                    if (r.nextBoolean()) {
                        disparos.add(new Projectile(posX, posY, escala_width, escala_height, direction));
                    } else {
                        disparos.add(new Projectile((posX + Math.round(OFESET_DISTANCIA_X_DOS_CANHOES * escala_width)), posY, escala_width, escala_height, direction));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
                case SUL -> {
                    //É somado a altura à posY, pois a posição x ainda é a mesma, porém a posição Y muda de perto do inicio do sprite para o final dele, sendo assim necéssario somar a Y a altura do sprite
                    if (r.nextBoolean()) {
                        disparos.add(new Projectile(posX, (posY + (height - OFESET_ALTURA_ATE_CANHAO)), escala_width, escala_height, direction));
                    } else {
                        disparos.add(new Projectile((posX + Math.round(OFESET_DISTANCIA_X_DOS_CANHOES * escala_width)), (posY + (height - OFESET_ALTURA_ATE_CANHAO)), escala_width, escala_height, direction));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
                case LESTE -> {
                    if (r.nextBoolean()) {
                        disparos.add(new Projectile(posX, posY, escala_height, escala_width, direction));
                    } else {
                        disparos.add(new Projectile(posY, (posX + Math.round(OFESET_DISTANCIA_X_DOS_CANHOES * escala_height)), escala_height, escala_width, direction));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
                case OESTE -> {
                    if (r.nextBoolean()) {
                        disparos.add(new Projectile(posX, posY, escala_height, escala_width, direction));
                    } else {
                        disparos.add(new Projectile(posX , posY + (Math.round(OFESET_DISTANCIA_X_DOS_CANHOES * escala_height)), escala_height, escala_width, direction));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }

            }
        }
    }
    
    @Override
    public void shot(){        
        if (tempo <= 0) {
            addLista();
            Sounds.disparo();
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
        Iterator<Projectile> it = disparos.iterator();

        // Enquanto ainda existir pelo menos um elemento
        // que não foi visitado pelo iterador...
        while (it.hasNext()) {
            // Avança o iterador para o próximo elemento
            // e devolve esse elemento.
            Projectile p = it.next();
            p.uptade();
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
            state_behavier = Behavior.ATACANDO;
        }
        
        if (!player.disparos.isEmpty()&& isSeeing(player)) {
            state_behavier = Behavior.FUGINDO;
        }
        //Adicionar algoritmo inimigo uptade-se para uma posição distante do player para assim evitar ser acertado; o que ocorrerá após que pelo menos um inimigo tenha morrido.
        //Se um inimigo estiver fugindo do jogador; o inimigo mais próximo irá para o campo de visão do jogador e atacará;
    }
    
    //------------------------------------------------------------------------//
    private boolean isSeeing(Player player){
        return player.posY > this.posY && Math.abs(player.posX - this.posX) < 50;
    }

    @Override
    public void uptade_collison(int damege){
        lifePoints -= damege;
    }   
}