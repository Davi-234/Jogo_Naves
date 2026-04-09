package com.jogo.model.objetos;

import com.jogo.regras.estados.Estado_Direcao;
import java.util.ArrayList;
import java.util.List;

public class SistemaDeAsteroides {
    public List<Asteroide> asteroides = new ArrayList();

    public SistemaDeAsteroides(int x_tela, int y_tela) {
        asteroides.add(new Asteroide(20, 20, 38/2, 29/2, Estado_Direcao.NORTE));
    }
    
}
