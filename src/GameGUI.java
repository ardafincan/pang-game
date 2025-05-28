package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGUI extends JPanel{
    
    public GameGUI(){
        super();

        BackgroundPanel bgPanel = new BackgroundPanel();
        setLayout(new BorderLayout());
        add(bgPanel, BorderLayout.NORTH);
        setBackground(Color.BLACK);
    }

   

    public void showGameScreenAt(JFrame frame){
        frame.add(this);
    }

    private class BackgroundPanel extends JPanel{
        private BufferedImage background;

        public BackgroundPanel() {
            try{
                background = ImageIO.read(getClass().getResource("../assets/selectedBG.png"));
            }catch(IOException ioException){
                background = null;
            }
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }

        @Override
        public java.awt.Dimension getPreferredSize() {
            if (background != null) {
                return new java.awt.Dimension(background.getWidth() * Main.ratioConsotant, background.getHeight() * Main.ratioConsotant);
            }
            return new java.awt.Dimension(800, 600); // Default size if image is null
        }
    }
}
