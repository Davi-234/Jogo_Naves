package com.jogo.sonoro;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sons {

    private static Clip trilha;

    private static void tocar(String caminho) {
        try {
            URL url = Sons.class.getResource(caminho);
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);

            Clip tocador = AudioSystem.getClip();
            tocador.open(audio);
            tocador.start();
        } catch (Exception ex) {
            Logger.getLogger(Sons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void disparo() {
        tocar("/sons/nave/disparo.wav");
    }

    public static void morte() {
        tocar("/sons/nave/morte.wav");
    }

    public static void sofrerDano() {
        tocar("/sons/nave/colisao.wav");
    }

    public static void trilhaSonora() {
        pararTrilha();

        try {
            if (trilha != null && trilha.isRunning()) {
                return;
            }

            URL url = Sons.class.getResource("/sons/trilhas/musica1.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);

            trilha = AudioSystem.getClip();
            trilha.open(audio);
            trilha.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception ex) {
            Logger.getLogger(Sons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void trilhaSonora2() {
        pararTrilha();

        try {
            if (trilha != null && trilha.isRunning()) {
                return;
            }

            URL url = Sons.class.getResource("/sons/trilhas/musica2.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);

            trilha = AudioSystem.getClip();
            trilha.open(audio);
            trilha.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception ex) {
            Logger.getLogger(Sons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pararTrilha() {
        if (trilha != null) {
            trilha.stop();
            trilha.close();
        }
    }

}
