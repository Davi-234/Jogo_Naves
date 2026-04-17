package com.jogo.model.jogadores;

import com.jogo.regras.estados.Estado_Direcao;
import com.jogo.model.projetil.Projetil;
import com.jogo.input.InputKeyboard;
import com.jogo.model.Entidade;
import com.jogo.regras.Atirador;
import com.jogo.sonoro.Sons;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;

public class Jogador extends Entidade implements Atirador {

    public int pontuacao = 0;
    public List<Projetil> disparos;

    public Jogador() {
        super(130, 0, (26 * 2), (32 * 2));
        sprite = new ImageIcon(getClass().getResource("/personagens/jogador/sovietico_norte.png")).getImage();
        direcao = Estado_Direcao.NORTE;
        vida = 225;
        velocidade = 4;
        disparos = new ArrayList<>();
        dano = 5;
        ajuste_referente_a_escala_X = largura / 26;
        ajuste_referente_a_escala_Y = altura / 32;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    //---------------------O uptade faz todos os movimentos do jogador--------//
    public void uptade(InputKeyboard input, int X_tela, int Y_tela) {
        reniciaCooldown();

        if ((posY - velocidade) >= (Y_tela * 0.5) && ((posX - velocidade) >= 0)) {
            if (input.cima && input.esquerda) {
                direcao = Estado_Direcao.NOROESTE;
            }
        }

        if ((posY + velocidade) + altura <= Y_tela && ((posX - velocidade) >= 0)) {
            if (input.baixo && input.esquerda) {
                direcao = Estado_Direcao.SUDOESTE;
            }
        }

        if ((posY - velocidade) >= (Y_tela * 0.5) && (posX + velocidade) + largura <= X_tela) {
            if (input.cima && input.direita) {
                direcao = Estado_Direcao.NORDESTE;
            }
        }

        if ((posY + velocidade) + altura <= Y_tela && (posX + velocidade) + largura <= X_tela) {
            if (input.baixo && input.direita) {
                direcao = Estado_Direcao.SUDESTE;
            }
        }

        if ((posY - velocidade) >= (Y_tela * 0.5)) {
            if (input.cima) {
                posY -= velocidade;
                setSprite("/personagens/jogador/sovietico_norte.png");
                direcao = Estado_Direcao.NORTE;
            }
        }

        if ((posY + velocidade) + altura <= Y_tela) {
            if (input.baixo) {
                posY += velocidade;
                setSprite("/personagens/jogador/sovietico_sul.png");
                direcao = Estado_Direcao.SUL;
            }
        }

        if ((posX + velocidade) + largura <= X_tela) {
            if (input.direita) {
                posX += velocidade;
                setSprite("/personagens/jogador/sovietico_leste.png");
                direcao = Estado_Direcao.LESTE;
            }
        }

        if ((posX - velocidade) >= 0) {
            if (input.esquerda) {
                posX -= velocidade;
                setSprite("/personagens/jogador/sovietico_oeste.png");
                direcao = Estado_Direcao.OESTE;
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

            if (p.posY + p.altura < 0 || p.acertou || p.posY >= Y_tela) {
                it.remove(); // morte limpa, sem exceção
            }
        }
        //--------------------------------------------------------------------//
        if (input.space) {
            atirar();
        }
    }

    @Override
    public void atualizacaoPos_colisao(int dano) {
        vida -= dano;
    }

    public void ganharPontos(int ganho) {
        pontuacao += ganho;
    }

    //------------------------------Lógica de atirar---------------------------//
    private int cooldownTiro = 0;

    private void reniciaCooldown() {
        if (cooldownTiro > 0) {
            cooldownTiro -= 10;
        }
    }

    @Override
    public void atirar() {
        if (cooldownTiro <= 0) {
            addLista();
            Sons.disparo();
            cooldownTiro = TEMPO_TIRO; //renicia o cooldown do tiro, que dispara a cada 500 milesegundos
        }
    }

    private static final int OFESET_ALTURA_ATE_CANHAO = 5 * 2;
    private static final int OFESET_DISTANCIA_DOS_CANHOES = 15;

    //deus tenha micericordia deste trecho
    private void addLista() {
        Random r = new Random();
        if (null != direcao) {
            switch (direcao) {
                case NORTE -> {
                    if (r.nextBoolean()) {
                        disparos.add(new Projetil(posX, posY, ajuste_referente_a_escala_X, ajuste_referente_a_escala_Y, direcao));
                    } else {
                        disparos.add(new Projetil((posX + Math.round(OFESET_DISTANCIA_DOS_CANHOES * ajuste_referente_a_escala_X)), posY, ajuste_referente_a_escala_X, ajuste_referente_a_escala_Y, direcao));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
                case SUL -> {
                    //É somado a altura à posY, pois a posição x ainda é a mesma, porém a posição Y muda de perto do inicio do sprite para o final dele, sendo assim necéssario somar a Y a altura do sprite
                    if (r.nextBoolean()) {
                        disparos.add(new Projetil(posX, (posY + (altura - OFESET_ALTURA_ATE_CANHAO)), ajuste_referente_a_escala_X, ajuste_referente_a_escala_Y, direcao));
                    } else {
                        disparos.add(new Projetil((posX + Math.round(OFESET_DISTANCIA_DOS_CANHOES * ajuste_referente_a_escala_X)), (posY + (altura - OFESET_ALTURA_ATE_CANHAO)), ajuste_referente_a_escala_X, ajuste_referente_a_escala_Y, direcao));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
                case LESTE -> {
                    if (r.nextBoolean()) {
                        disparos.add(new Projetil(posX, posY, ajuste_referente_a_escala_Y, ajuste_referente_a_escala_X, direcao));
                    } else {
                        disparos.add(new Projetil((posX + Math.round(OFESET_DISTANCIA_DOS_CANHOES * ajuste_referente_a_escala_Y)), posY, ajuste_referente_a_escala_Y, ajuste_referente_a_escala_X, direcao));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
                case OESTE -> {
                    if (r.nextBoolean()) {
                        disparos.add(new Projetil(posX, posY, ajuste_referente_a_escala_Y, ajuste_referente_a_escala_X, direcao));
                    } else {
                        disparos.add(new Projetil((posX + Math.round(OFESET_DISTANCIA_DOS_CANHOES * ajuste_referente_a_escala_Y)), posY, ajuste_referente_a_escala_Y, ajuste_referente_a_escala_X, direcao));//16 é a distancia entre os canhões, ou seja os tiros varia de canhão
                    }
                }
            }
        }
    }
    //------------------------------------------------------------------------//

    public void colisoes(Projetil projetl) {
        if (getHitBox().colide(projetl.getHitBox())) {
            atualizacaoPos_colisao(projetl.dano);
            projetl.acertou = true;
            Sons.sofrerDano();
        }
    }
}
