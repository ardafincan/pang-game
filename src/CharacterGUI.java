package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class CharacterGUI extends JPanel implements ActionListener, Runnable {
    private int characterX = 510;
    private int characterY = 504;
    private int currentFrame = 0;
    private int totalFrames = 5;

    private boolean isLastLeft = false;
    private volatile boolean movingLeft = false;
    private volatile boolean movingRight = false;

    private Thread movementThread;

    private List<Rectangle> arrows;

    public CharacterGUI(){
        setOpaque(false);
        setFocusable(true);
        setPreferredSize(new Dimension(AssetBank.getForegroundImage().getWidth() * 3, AssetBank.getForegroundImage().getHeight() * 3));

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed LEFT"), "moveLeft");
        this.getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                movingLeft = true;
                isLastLeft = true;
            }
        });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "stopLeft");
        this.getActionMap().put("stopLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                currentFrame = 0;
                movingLeft = false;
            }
        });

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed RIGHT"), "moveRight");
        this.getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                movingRight = true;
                isLastLeft = false;
            }
        });
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "stopRight");
        this.getActionMap().put("stopRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                currentFrame = 0;
                movingRight = false;
            }
        });


        movementThread = new Thread(this);
        movementThread.start();
        
    }

    private void cycleAnimation(){
        currentFrame = ((currentFrame + 1) % totalFrames) == 0 ? currentFrame + 1 : (currentFrame + 1) % totalFrames;
    }

    @Override
    public void run(){
        while(true){
            if(movingLeft){
                if(characterX > 25){
                    characterX -= 15;
                }
                cycleAnimation();
            }
            if(movingRight){
                if(characterX < 1045)
                characterX += 15;
                cycleAnimation();
            }
            SwingUtilities.invokeLater(() -> repaint());
            try{
                Thread.sleep(75);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int characterWidth = AssetBank.getCharacterImages()[currentFrame].getWidth() * 3;
        int characterHeight = AssetBank.getCharacterImages()[currentFrame].getHeight() * 3;

        g.drawImage(AssetBank.getForegroundImage(), 0, 0,getWidth(), getHeight(), null);
        if(movingLeft || isLastLeft){
             g.drawImage(AssetBank.getCharacterImages()[currentFrame], characterX + characterWidth, characterY, -characterWidth, characterHeight, null);
        }else{
             g.drawImage(AssetBank.getCharacterImages()[currentFrame], characterX, characterY, characterWidth, characterHeight, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // not used yet
    }
}
