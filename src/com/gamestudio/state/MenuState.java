package com.gamestudio.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import com.gamestudio.effect.FrameImage;
import com.gamestudio.elements.ButtonMenu;
import com.gamestudio.interfaces.GameFrame;
import com.gamestudio.manager.DataLoader;
import com.gamestudio.manager.StateManager;

// A classe MenuState é responsável por armazenar, atualizar, e renderizar todos os componentes presentes 
// no menu inicial do jogo, cujo qual possui botões e background
public class MenuState extends State {
    private FrameImage backgroundImage; // Background 
    private ButtonMenu[] buttons; // Lista de botões 
    private Clip menuMusic; // Som de fundo 
    private Clip arrowSound; // Efeito Sonoro tocado na mudança de opções 

    public MenuState(StateManager stateManager) {
        super(stateManager, new BufferedImage(GameFrame.width, GameFrame.height, BufferedImage.TYPE_INT_ARGB));

        buttons = new ButtonMenu[2];
        buttons[0] = new ButtonMenu(220, 385);
        buttons[0].update();
        buttons[1] = new ButtonMenu(220, 430);

        backgroundImage = DataLoader.getInstance().getFrameImage("menu");
        menuMusic = DataLoader.getInstance().getSound("Menu");
        arrowSound = DataLoader.getInstance().getSound("sound_arrow_menu");
    }

    // Atualiza todos os componentes necessários no menu
    @Override
    public void update() {
        if(!menuMusic.isRunning()) {  
            menuMusic.setFramePosition(0); 
            menuMusic.start();
        }
    }

    // Renderiza todos os componentes do menu, sendo eles os botões e background
    @Override
    public void render() {
        Graphics g = getBufferedImage().getGraphics();
        g.drawImage(backgroundImage.getImage(), 0, 0, GameFrame.width, GameFrame.height, null);
        for (ButtonMenu bt : buttons) {
            bt.draw(g);
        }
    }

    // Realiza uma ação com base em qual tecla foi pressionada
    @Override
    public void setPressedButton(int keyCode) {
        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            arrowSound.stop();
            arrowSound.setFramePosition(0); 
            arrowSound.start();
            for (ButtonMenu bt : buttons) {
                bt.update();
            }
            update();
            
        } else if(keyCode == KeyEvent.VK_ENTER) {
            arrowSound.stop();
            arrowSound.setFramePosition(0); 
            arrowSound.start();
            if(buttons[0].isEnabled()) {
                menuMusic.stop();
                getStateManager().setCurrentState(StateManager.GAME);;
            } else {
                System.exit(0);
            }
        }
    }

     // Realiza uma ação com base em qual tecla foi solta
    @Override
    public void setReleasedButton(int keyCode) {}
}
