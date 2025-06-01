package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JPanel;

public class CharacterGUI extends JPanel implements KeyListener, ActionListener {
    
    private int characterX = 510;
    private int characterY = 504;

    private List<Rectangle> arrows;

    public CharacterGUI(){
        setOpaque(false);
        setFocusable(true);
        addKeyListener(this);
        setPreferredSize(new Dimension(AssetBank.getForegroundImage().getWidth() * 3, AssetBank.getForegroundImage().getHeight() * 3));
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(AssetBank.getForegroundImage(), 0, 0,getWidth(), getHeight(), null);
        g.drawImage(AssetBank.getCharacterImages()[0], characterX, characterY, AssetBank.getCharacterImages()[0].getWidth() * 3, AssetBank.getCharacterImages()[0].getHeight() * 3, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // not used yet, will be implemented
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT){
            characterX -= 10;
        }else if (key == KeyEvent.VK_RIGHT){
            characterX += 10;
        }
        
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }
}
