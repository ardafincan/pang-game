package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.ImageIcon;
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

        private JPanel livesContainer = new JPanel();
        private JPanel livesPanel = new JPanel();

        // creating labels
        private JLabel usernameLabel = new JLabel("PLAYER 1", JLabel.CENTER);
        private JLabel episodeLabel = new JLabel("ATHENS", JLabel.CENTER);
        private JLabel scoreLabel = new JLabel("SCORE: 1000", JLabel.CENTER);
        private JLabel highScoreLabel = new JLabel("HI: 2100", JLabel.CENTER);
        private JLabel dummyLabel = new JLabel();

        Image liveIcon = new ImageIcon("assets/playerLive.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon icon00 = new ImageIcon(liveIcon);

        private JLabel liveIcon1 = new JLabel(icon00);
        private JLabel liveIcon2 = new JLabel(icon00);
        private JLabel liveIcon3 = new JLabel(icon00);

        public FooterMenu (){
            super();
            setOpaque(false);

            setLayout(new BorderLayout());

            livesContainer.setPreferredSize(new Dimension(382, HEIGHT));
            livesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
            livesContainer.setLayout(new BorderLayout());

            dummyLabel.setPreferredSize(new Dimension(384,HEIGHT));
            usernameLabel.setForeground(Color.WHITE);
            usernameLabel.setPreferredSize(new Dimension(384, HEIGHT));
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setPreferredSize(new Dimension(384, HEIGHT));
            highScoreLabel.setForeground(Color.WHITE);
            episodeLabel.setForeground(Color.WHITE);

            usernameLabel.setFont(assetBank.getRetroFont());
            episodeLabel.setFont(assetBank.getRetroFont());
            scoreLabel.setFont(assetBank.getRetroFont());
            highScoreLabel.setFont(assetBank.getRetroFont());

            upperPanel.setLayout(new BorderLayout());
            upperPanel.setBackground(Color.BLACK);
            bottomPanel.setLayout(new BorderLayout());
            bottomPanel.setBackground(Color.BLACK);
            livesPanel.setBackground(Color.BLACK);
            

            livesPanel.add(liveIcon1, BorderLayout.WEST);
            livesPanel.add(liveIcon2, BorderLayout.CENTER);
            livesPanel.add(liveIcon3, BorderLayout.EAST);
            livesPanel.setPreferredSize(new Dimension(170, 50));
            livesContainer.add(livesPanel, BorderLayout.CENTER);

            upperPanel.add(usernameLabel, BorderLayout.WEST);
            upperPanel.add(episodeLabel, BorderLayout.CENTER);
            upperPanel.add(scoreLabel, BorderLayout.EAST);

            bottomPanel.add(livesContainer, BorderLayout.WEST);
            bottomPanel.add(highScoreLabel, BorderLayout.CENTER);
            bottomPanel.add(dummyLabel, BorderLayout.EAST);
            bottomPanel.setPreferredSize(new Dimension(WIDTH, getHeight() * 3));

            add(upperPanel, BorderLayout.NORTH);
            add(bottomPanel, BorderLayout.CENTER);
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
