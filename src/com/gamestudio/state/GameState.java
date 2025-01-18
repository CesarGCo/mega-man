package com.gamestudio.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

import com.gamestudio.elements.Camera;
import com.gamestudio.elements.MegaMan;
import com.gamestudio.interfaces.GameFrame;
import com.gamestudio.manager.DataLoader;
import com.gamestudio.manager.ProjectileManager;
import com.gamestudio.manager.RobotManager;
import com.gamestudio.manager.StateManager;
import com.gamestudio.physical.PhysicalMap;

public class GameState extends State {
    public PhysicalMap physicalMap;
    public MegaMan megaMan;
    public RobotManager robotManager;
    public ProjectileManager projectileManager;
    public Camera camera;
    private BufferedImage mapImage;

    public GameState(StateManager stateManager) {
       super(stateManager, new BufferedImage(GameFrame.width, GameFrame.height, BufferedImage.TYPE_INT_ARGB));
       this.robotManager = new RobotManager(this);
       this.physicalMap = new PhysicalMap(0, 0, this);
       this.mapImage = DataLoader.getInstance().getFrameImage("new_map_fall").getImage();
       this.megaMan = new MegaMan(100, 102, this);
       this.camera = new Camera(0, 0, 400, 240, this);
       robotManager.addObject(megaMan);
    }

    public BufferedImage getMapImage() {
        return mapImage;
    }

    public void update() {
        megaMan.update();
        camera.update();
    }

    public void render() {
        Graphics g = getBufferedImage().getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        drawMap(g2);
        physicalMap.draw(g);
        megaMan.draw(g2);
        
    }
    
    public void setPressedButton(int code) {
        switch(code){
                
            case KeyEvent.VK_RIGHT:
                megaMan.setDirection(megaMan.RIGHT);
                megaMan.run();
                break;
                
            case KeyEvent.VK_LEFT:
                megaMan.setDirection(megaMan.LEFT);
                megaMan.run();
                break;

            case KeyEvent.VK_SPACE:
                megaMan.jump();
                break;
        }
    }
    
    private void drawMap(Graphics2D g2d) {
        // Escala para ajustar o conteúdo da câmera à tela
        float scaleX = (float) GameFrame.width / camera.getWidthView();
        float scaleY = (float) GameFrame.height / camera.getHeightView();
        
    
        // Salvar o estado original do Graphics
        
        AffineTransform originalTransform = g2d.getTransform();
    
        // Aplicar a escala para desenhar proporcionalmente
        g2d.scale(scaleX, scaleY);
        

        // Ajustar o desenho com base na posição da câmera
        g2d.drawImage(
            mapImage,
            (int) (-camera.getPosX()), // Ajuste horizontal proporcional à escala
            (int) (-camera.getPosY()), // Ajuste vertical proporcional à escala
            null
        );
    }
    

    public void setReleasedButton(int code) {
        switch(code){
                
            case KeyEvent.VK_RIGHT:
                if(megaMan.getSpeedX() > 0)
                    megaMan.stopRun();
                break;
                
            case KeyEvent.VK_LEFT:
                if(megaMan.getSpeedX() < 0)
                    megaMan.stopRun();
                break;
                
            case KeyEvent.VK_SPACE:
                
                break;
        }
    }
}
