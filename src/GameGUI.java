package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

// this class is the game screen the real thing is going on here 

public class GameGUI extends JPanel{
    int difficulty;
    
    public GameGUI(int difficulty){
        super();

        BackgroundPanel bgPanel = new BackgroundPanel(difficulty);
        setLayout(new BorderLayout());
        add(bgPanel, BorderLayout.NORTH);
        add(new StatsGUI(difficulty), BorderLayout.CENTER);
        setBackground(Color.BLACK);
    }

    // below class is for the game background, I am adding this to gameGUI
    private class BackgroundPanel extends JPanel{
        // setting this to transparent to show image
        public BackgroundPanel(int difficulty) {

            setOpaque(false);
            setLayout(new BorderLayout());
            try {
                CharacterGUI character = new CharacterGUI(difficulty);
                character.characterLives = 3;
                add(character, BorderLayout.NORTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // overriding paint component to paint background image
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            g.drawImage(AssetBank.getBackgroundImage(), 0, 0, getWidth(), getHeight(), null);
        }

        // overriding getPrefferedSize to set size true
        @Override
        public java.awt.Dimension getPreferredSize() {
            if (AssetBank.getBackgroundImage() != null) {
                return new java.awt.Dimension(AssetBank.getBackgroundImage().getWidth() * Main.ratioConsotant, AssetBank.getBackgroundImage().getHeight() * Main.ratioConsotant);
            }
            return new java.awt.Dimension(800, 600); 
        }
    }
}
