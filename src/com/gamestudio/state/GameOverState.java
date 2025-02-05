package com.gamestudio.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import com.gamestudio.effect.FrameImage;
import com.gamestudio.elements.ButtonGameOver;
import com.gamestudio.interfaces.GameFrame;
import com.gamestudio.manager.DataLoader;
import com.gamestudio.manager.StateManager;


// A calsse Game Over representa a tela de Game Over do Jogo
// cujo qual possui apenas botões e um BackGround
public class GameOverState extends State {
    private FrameImage backgroundImage; // Background
    private ButtonGameOver[] buttons; // Botões
    private Clip gameOverMusic; // Música de Game Over
    private Clip arrowSound; // Efeito sonoro de troca de opções
    private boolean soundPlayed;
    
    public GameOverState(StateManager stateManager) {
       super(stateManager, new BufferedImage(GameFrame.width, GameFrame.height, BufferedImage.TYPE_INT_ARGB));
        
       buttons = new ButtonGameOver[3];
       buttons[0] = new ButtonGameOver(358, 280);
       buttons[0].update();
       buttons[1] = new ButtonGameOver(358, 320);
       buttons[2] = new ButtonGameOver(358, 360);
       backgroundImage = DataLoader.getInstance().getFrameImage("game_over");

       this.gameOverMusic = DataLoader.getInstance().getSound("Game-over");
       this.arrowSound = DataLoader.getInstance().getSound("sound_arrow_menu");
       this.soundPlayed = false;
    }

    // Atualiza todos os elemetos do estado
    @Override
    public void update() {
        if(!soundPlayed) {  
            gameOverMusic.setFramePosition(0); 
            gameOverMusic.start();
            soundPlayed = true;
        }
    }

    // Renseriza todos os elemtos do estado
    @Override
    public void render() {
        Graphics g = getBufferedImage().getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, GameFrame.width, GameFrame.height);
        int width = (int) (0.7 * (GameFrame.width));
        g.drawImage(backgroundImage.getImage(), (GameFrame.width / 2) - (width / 2), 0, width, GameFrame.height, null);
        for (ButtonGameOver bt : buttons) {
            bt.draw(g);
        }
    }
    
    @Override
    public void setPressedButton(int code) {
        switch (code) {
            case KeyEvent.VK_ENTER:
                arrowSound.stop();
                arrowSound.setFramePosition(0); 
                arrowSound.start();
                soundPlayed = false;
                gameOverMusic.stop();
                if(buttons[0].isEnabled()) {
                    getStateManager().setCurrentState(StateManager.GAME);

                } else if(buttons[1].isEnabled()) {
                    buttons[0].update();
                    buttons[1].update();
                    getStateManager().setCurrentState(StateManager.MENU);

                } else if(buttons[2].isEnabled()) {
                    System.exit(0);
                } 
                break;
            case KeyEvent.VK_UP:
                arrowSound.stop();
                arrowSound.setFramePosition(0); 
                arrowSound.start();
                if(buttons[1].isEnabled()) {
                    buttons[0].update();
                    buttons[1].update();
                } else if(buttons[2].isEnabled()) {
                    buttons[1].update();
                    buttons[2].update();
                } 
                break;
            case KeyEvent.VK_DOWN:
                arrowSound.stop();
                arrowSound.setFramePosition(0); 
                arrowSound.start();
                if(buttons[0].isEnabled()) {
                    buttons[0].update();
                    buttons[1].update();
                } else if(buttons[1].isEnabled()) {
                    buttons[1].update();
                    buttons[2].update();
                } 
                break;
            default:
                break;
        }
    }
    
    @Override
    public void setReleasedButton(int code) {}
}
