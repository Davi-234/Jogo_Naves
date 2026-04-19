package com.jogo;

import com.jogo.hub.HUB;
import com.jogo.input.InputKeyboard;
import com.jogo.model.jogadores.Player;
import com.jogo.model.inimigos.Americam;
import com.jogo.model.inimigos.formacao.Armada;
import com.jogo.model.objetos.Asteroid;
import com.jogo.model.objetos.Systeam_Asteroid;
import com.jogo.model.projetil.Projectile;
import com.jogo.regras.colisao.Systeam_Collisons;
import com.jogo.sonoro.Sounds;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JFrame {

    Timer timer; //este timer atualiza tudo com exceção do jogador
    JPanel entidades;
    Player player = new Player();
    Armada armada;
    InputKeyboard input = new InputKeyboard();
    HUB objetosHUB;
    StatesMain stateGame = StatesMain.JOGANDO;
    Systeam_Asteroid asteroides;

    Image fundo = new ImageIcon(getClass().getResource("/cenario/fundo.png")).getImage();

    public Main() throws HeadlessException, LineUnavailableException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fechao o jframe só se for pedido pelo java
        this.setTitle("Jogo");

        //------------------Responsável pela tela cheia-----------------------//
        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        setUndecorated(true);
        setResizable(false);

        gd.setFullScreenWindow(this);
        //--------------------------------------------------------------------//
        entidades = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                //imagem de fundo
                g2.drawImage(fundo, 0, 0, (1604 * 2), (1320 * 2), this);

                //---------------------Contador de pontos---------------------//
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.PLAIN, 29));
                g2.drawString(("Pontuação: " + player.points), 0, 36);
                //------------------------------------------------------------//

                //-------------------------Corações do player-----------------//
                objetosHUB.getLife_HUB(g2, this);
                //------------------------------------------------------------//

                //--------------------------Player---------------------------//
                if (player.lifePoints > 0) {
                    player.desenharSprite(g2); // Desenha o "personagem
                }
                for (Projectile p : player.disparos) {
                    p.desenharSprite(g2);
                }
                //------------------------------------------------------------//

                //--------------------------Armada----------------------------//
                if (armada != null) {
                    for (Americam americam : armada.armada) {
                        americam.desenharSprite(g2);

                        for (Projectile projtl : americam.disparos) {
                            projtl.desenharSprite(g2);
                        }
                    }
                }
                //------------------------------------------------------------//

                //--------------------Sistemas dos asteroides-----------------//
                if (asteroides != null) {
                    for (Asteroid m : asteroides.asteroides) {
                        m.desenharSprite(g2);
                    }
                }
                //------------------------------------------------------------//

                //--------------------------Pause-----------------------------//
                if (stateGame == StatesMain.PAUSADO && objetosHUB != null) {
                    objetosHUB.getHUB_Pause(g2, this);
                }
                //------------------------------------------------------------//
            }
        };

        entidades.addKeyListener(input);
        entidades.setFocusable(true);
        this.requestFocus();
        this.add(entidades);
        this.setVisible(true);
        entidades.requestFocusInWindow();

        entidades.requestFocus();

        /*
         * FOCO (FOCUS) EM SWING
         *
         * Em aplicações gráficas, o teclado não envia eventos para a janela inteira,
         * mas sim para um ÚNICO componente por vez: aquele que possui o foco.
         *
         * O foco representa o componente que está "ativo" para receber entrada do usuário.
         * Se um componente não possui foco, ele simplesmente não recebe eventos de teclado,
         * ainda que exista um KeyListener corretamente implementado.
         *
         * Por que o JPanel precisa ter o foco, e não o JFrame?
         *
         * 1) Em Swing, o JFrame é apenas um contêiner de alto nível.
         *    Ele serve para hospedar componentes, não para interagir diretamente com o usuário.
         *
         * 2) O conteúdo real da aplicação ocorre dentro dos componentes internos,
         *    como JPanel, que é onde o desenho, a lógica visual e a interação acontecem.
         *
         * 3) Quando o JPanel está focável e possui o foco, os eventos de teclado
         *    chegam diretamente a ele, de forma previsível e estável.
         *
         * 4) Confiar no JFrame para receber teclado é frágil:
         *    qualquer componente interno pode roubar o foco,
         *    fazendo o teclado simplesmente parar de funcionar.
         *
         * Por isso:
         * - O KeyListener deve ser adicionado ao JPanel principal do jogo.
         * - O JPanel deve ser tornável focável com setFocusable(true).
         * - O foco deve ser explicitamente solicitado com requestFocusInWindow().
         *
         * Em resumo:
         * Teclado fala apenas com quem tem foco.
         * Quem não tem foco, não ouve nada.
         */
        player.posY = ((getHeight() - player.height));
        objetosHUB = new HUB(player.lifePoints, getWidth(), getHeight());
        armada = new Armada(getWidth(), getHeight());
        asteroides = new Systeam_Asteroid(getWidth(), getHeight());

        Sounds.trilhaSonora();

        timer = new Timer(10, e -> {
            if (stateGame == StatesMain.PAUSADO) {
                //System.exit(0);

                if (input.esc) {
                    stateGame = StatesMain.JOGANDO;
                }

                if (player.lifePoints <= 0) {
                    System.exit(0);
                }

                //System.exit(0);//por enquanto o jogo não tem pause

                entidades.repaint();
            }

            if (stateGame == StatesMain.JOGANDO) {
                //player.vida--;
                if (player.lifePoints <= 0) {
                    System.exit(0);
                }

                if (armada.getArmadaSize() <= 0) {
                    armada = new Armada(getWidth(), getHeight());
                }

                player.uptade(input, getWidth(), getHeight());
                armada.uptade(player);

                Systeam_Collisons.allCollisons(player, armada.armada, asteroides.asteroides);

                asteroides.uptade();

                if (input.esc) {
                    stateGame = StatesMain.PAUSADO;
                }
            }
            objetosHUB = new HUB(player.lifePoints, getWidth(), getHeight());
            entidades.repaint();
        });
        timer.start();

    }

    public static void main(String[] args) throws LineUnavailableException {
        new Main();
    }
}
