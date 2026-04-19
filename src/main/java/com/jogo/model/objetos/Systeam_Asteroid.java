package com.jogo.model.objetos;

import com.jogo.regras.estados.Direction;
import java.util.ArrayList;
import java.util.List;

public class Systeam_Asteroid {
    public List<Asteroid> asteroides = new ArrayList();

    public Systeam_Asteroid(int x_tela, int y_tela) {
        asteroides.add(new Asteroid(20, 20, 38/2, 29/2, Direction.NORTE));
    }
    
    public void uptade() {
        for (Asteroid asteroide : asteroides) {
            asteroide.uptade();
        }
    }
    
    //--------------------------------------------------------------------------
    private static final short TIMER_COOLDOWN = 300;
    
    //--------------------------------------------------------------------------
    
}
