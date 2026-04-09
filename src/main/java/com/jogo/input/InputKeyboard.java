package com.jogo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputKeyboard  implements KeyListener{    
    public boolean cima;
    public boolean baixo;
    public boolean esquerda;
    public boolean direita;
    public boolean space;
    public boolean esc;

    @Override
    public void keyTyped(KeyEvent e) { //não funciona com as setas, apenas com carcteres;
    }

    @Override
    public void keyPressed(KeyEvent e) { // quando é pressionado aciona ocorpo        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> cima = true;
            case KeyEvent.VK_DOWN -> baixo = true;
            case KeyEvent.VK_LEFT -> esquerda = true;
            case KeyEvent.VK_RIGHT -> direita = true;
            case KeyEvent.VK_SPACE -> space = true;
            case KeyEvent.VK_ESCAPE -> esc = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) { // quando é solto aciona
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> cima = false;
            case KeyEvent.VK_DOWN -> baixo = false;
            case KeyEvent.VK_LEFT -> esquerda = false;
            case KeyEvent.VK_RIGHT -> direita = false;
            case KeyEvent.VK_SPACE -> space = false;
            case KeyEvent.VK_ESCAPE -> esc = false;
        }
    }

}
