package com.jogo.model.jogadores;

import com.jogo.regras.estados.Estado_Direcao;
import com.jogo.model.projetil.Projetil;
import com.jogo.input.InputKeyboard;
import com.jogo.model.Entity;
import com.jogo.regras.Atirador;
import com.jogo.sonoro.Sons;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Player extends Entity implements Atirador {

    public int points = 0;
    public List<Projetil> disparos;

    public Player() {
        super(130, 0, (26 * 2), (32 * 2), 26, 32);
        setSprite("/personagens/jogador/sovietico_norte.png");
        direction = Estado_Direcao.NORTE;
        lifePoints = 225;
        speed = 4;
        disparos = new ArrayList<>();
        damege = 5;
        escala_width = width / 26;
        escala_height = height / 32;
        //essas constantes são os valores dos Sprites X = 26 / Y = 32
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    //---------------------O uptade faz todos os movimentos do jogador--------//
    public void uptade(InputKeyboard input, int X_tela, int Y_tela) {
        reniciaCooldown();

        if ((posY - speed) >= (Y_tela * 0.5) && ((posX - speed) >= 0)) {
            if (input.cima && input.esquerda) {
                direction = Estado_Direcao.NOROESTE;
            }
        }

        if ((posY + speed) + height <= Y_tela && ((posX - speed) >= 0)) {
            if (input.baixo && input.esquerda) {
                direction = Estado_Direcao.SUDOESTE;
            }
        }

        if ((posY - speed) >= (Y_tela * 0.5) && (posX + speed) + width <= X_tela) {
            if (input.cima && input.direita) {
                direction = Estado_Direcao.NORDESTE;
            }
        }

        if ((posY + speed) + height <= Y_tela && (posX + speed) + width <= X_tela) {
            if (input.baixo && input.direita) {
                direction = Estado_Direcao.SUDESTE;
            }
        }

        if ((posY - speed) >= (Y_tela * 0.5)) {
            if (input.cima) {
                posY -= speed;
                setSprite("/personagens/jogador/sovietico_norte.png");
                direction = Estado_Direcao.NORTE;
            }
        }

        if ((posY + speed) + height <= Y_tela) {
            if (input.baixo) {
                posY += speed;
                setSprite("/personagens/jogador/sovietico_sul.png");
                direction = Estado_Direcao.SUL;
            }
        }

        if ((posX + speed) + width <= X_tela) {
            if (input.direita) {
                posX += speed;
                setSprite("/personagens/jogador/sovietico_leste.png");
                direction = Estado_Direcao.LESTE;
            }
        }

        if ((posX - speed) >= 0) {
            if (input.esquerda) {
                posX -= speed;
                setSprite("/personagens/jogador/sovietico_oeste.png");
                direction = Estado_Direcao.OESTE;
            }
        }

        //------------------Retira os projeteis do jogador--------------------//
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

            if (p.posY + p.height < 0 || p.acertou || p.posY >= Y_tela) {
                it.remove(); // morte limpa, sem exceção
            }
        }
        //--------------------------------------------------------------------//
        if (input.space) {
            shot();
        }

        reformular_Posicoes();
    }

    @Override
    public void atualizacaoPos_colisao(int dano) {
        lifePoints -= dano;
    }

    public void ganharPontos(int ganho) {
        points += ganho;
    }

    //------------------------------Lógica de atirar---------------------------//
    private int cooldownTiro = 0;

    private void reniciaCooldown() {
        if (cooldownTiro > 0) {
            cooldownTiro -= 10;
        }
    }

    @Override
    public void shot() {
        if (cooldownTiro <= 0) {
            addList();
            Sons.disparo();
            cooldownTiro = TEMPO_TIRO; //renicia o cooldown do tiro, que dispara a cada 500 milesegundos
        }
    }

    private static final int OFESET_ALTURA_ATE_CANHAO = 5 * 2;
    private static final int OFESET_DISTANCIA_X_DOS_CANHOES = 15;

    private void addList() {
        Random r = new Random();
        if (null != direction) {
            switch (direction) {
                case NORTE -> {
                    if (r.nextBoolean()) {
                        disparos.add(new Projetil(posX, posY, escala_width, escala_height, direction));
                    } else {
                        disparos.add(new Projetil((posX + Math.round(OFESET_DISTANCIA_X_DOS_CANHOES * escala_width)), posY, escala_width, escala_height, direction));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
                case SUL -> {
                    //É somado a altura à posY, pois a posição x ainda é a mesma, porém a posição Y muda de perto do inicio do sprite para o final dele, sendo assim necéssario somar a Y a altura do sprite
                    if (r.nextBoolean()) {
                        disparos.add(new Projetil(posX, (posY + (height - OFESET_ALTURA_ATE_CANHAO)), escala_width, escala_height, direction));
                    } else {
                        disparos.add(new Projetil((posX + Math.round(OFESET_DISTANCIA_X_DOS_CANHOES * escala_width)), (posY + (height - OFESET_ALTURA_ATE_CANHAO)), escala_width, escala_height, direction));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
                case LESTE -> {
                    if (r.nextBoolean()) {
                        disparos.add(new Projetil(posX, posY, escala_height, escala_width, direction));
                    } else {
                        disparos.add(new Projetil(posY, (posX + Math.round(OFESET_DISTANCIA_X_DOS_CANHOES * escala_height)), escala_height, escala_width, direction));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
                case OESTE -> {
                    if (r.nextBoolean()) {
                        disparos.add(new Projetil(posX, posY, escala_height, escala_width, direction));
                    } else {
                        disparos.add(new Projetil(posY, posX + (Math.round(OFESET_DISTANCIA_X_DOS_CANHOES * escala_height)), escala_height, escala_width, direction));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
            }
        }
    }
    //------------------------------------------------------------------------//
}
