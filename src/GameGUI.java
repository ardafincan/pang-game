package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

// this class is the game screen the real thing is going on here 

public class GameGUI extends JPanel{
    
    public GameGUI(){
        super();

        BackgroundPanel bgPanel = new BackgroundPanel();
        setLayout(new BorderLayout());
        add(bgPanel, BorderLayout.NORTH);
        add(new FooterMenu(), BorderLayout.CENTER);
        setBackground(Color.BLACK);
    }

   

    public void showGameScreenAt(JFrame frame){
        frame.add(this);
    }

    private class FooterMenu extends JPanel {
        // setting two JPanel to use them as container for different JLabels
        private JPanel upperPanel = new JPanel(); 
        private JPanel bottomPanel = new JPanel();  

        // creating labels
        private JLabel usernameLabel = new JLabel("PLAYER 1", JLabel.CENTER);
        private JLabel episodeLabel = new JLabel("ATHENS", JLabel.CENTER);
        private JLabel scoreLabel = new JLabel("SCORE: 1000", JLabel.CENTER);
        private JLabel healthLabel = new JLabel("3", JLabel.CENTER);
        private JLabel highScoreLabel = new JLabel("HI: 2100", JLabel.CENTER);

        public FooterMenu (){
            super();
            setOpaque(false);

            setLayout(new BorderLayout());

            usernameLabel.setForeground(Color.WHITE);
            usernameLabel.setPreferredSize(new Dimension(384, HEIGHT));
            episodeLabel.setForeground(Color.WHITE);
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setPreferredSize(new Dimension(384, HEIGHT));
            healthLabel.setForeground(Color.WHITE);
            highScoreLabel.setForeground(Color.WHITE);

            usernameLabel.setFont(assetBank.getRetroFont());
            episodeLabel.setFont(assetBank.getRetroFont());
            scoreLabel.setFont(assetBank.getRetroFont());
            healthLabel.setFont(assetBank.getRetroFont());
            highScoreLabel.setFont(assetBank.getRetroFont());

            upperPanel.setLayout(new BorderLayout());
            upperPanel.setBackground(Color.BLACK);
            bottomPanel.setLayout(new BorderLayout());
            bottomPanel.setBackground(Color.BLACK);

            upperPanel.add(usernameLabel, BorderLayout.WEST);
            upperPanel.add(episodeLabel, BorderLayout.CENTER);
            upperPanel.add(scoreLabel, BorderLayout.EAST);

            bottomPanel.add(healthLabel, BorderLayout.WEST);
            bottomPanel.add(highScoreLabel, BorderLayout.CENTER);

            add(upperPanel, BorderLayout.NORTH);
            add(bottomPanel, BorderLayout.SOUTH);
        }
    }

    private class BackgroundPanel extends JPanel{

        public BackgroundPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            g.drawImage(assetBank.getBackgroundImage(), 0, 0, getWidth(), getHeight(), null);
        }

        @Override
        public java.awt.Dimension getPreferredSize() {
            if (assetBank.getBackgroundImage() != null) {
                return new java.awt.Dimension(assetBank.getBackgroundImage().getWidth() * Main.ratioConsotant, assetBank.getBackgroundImage().getHeight() * Main.ratioConsotant);
            }
            return new java.awt.Dimension(800, 600); // Default size if image is null
        }
    }
}
