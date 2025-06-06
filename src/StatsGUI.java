package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// this is the bottom part of game screen where stats are shown
public class StatsGUI extends JPanel {
        private int timeRemaining; // countdown time

        // setting two JPanel to use them as container for different JLabels
        private JPanel upperPanel = new JPanel(); 
        private JPanel bottomPanel = new JPanel();  

        // setting up these extra panels to manage layout as I want
        private JPanel livesContainer = new JPanel(); // this will be in bottomPanel
        private JPanel livesPanel = new JPanel(); // this lives in livesContainer

        // setting icon00 to playerLive.png to show remaining lives of player
        Image liveIcon = new ImageIcon("assets/playerLive.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon icon00 = new ImageIcon(liveIcon); // this will be icon of three discrete labels to show lives

        // creating labels
        private JLabel usernameLabel = new JLabel("PLAYER 1", JLabel.CENTER);
        private JLabel episodeLabel = new JLabel("ATHENS", JLabel.CENTER);
        private JLabel scoreLabel = new JLabel(String.format("SCORE: %d", 0000), JLabel.CENTER);
        private JLabel highScoreLabel = new JLabel("HI: 2100", JLabel.CENTER);
        private JLabel countdownLabel = new JLabel("TIME: ", JLabel.CENTER);

        // below three are used for showing remaining lives
        private JLabel liveIcon1 = new JLabel(icon00);
        private JLabel liveIcon2 = new JLabel(icon00);
        private JLabel liveIcon3 = new JLabel(icon00);

        // this is constructor
        public StatsGUI (int difficulty){
            super();
            setOpaque(false);

            // setting layout to BorderLayout because I want something like below
            //  PLAYER      ATHENS      SCORE: 
            //
            //  * * *       HI:
            setLayout(new BorderLayout());
            
            livesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
            livesContainer.setLayout(new BorderLayout());

            // setting sizes of below items 
            /*
             * ! these are all either east or west in BorderLayout because centers are invading as much space as they can
             * ! and I need to specify widths of these components so all components widths are equal.
             */
            livesContainer.setPreferredSize(new Dimension(384, HEIGHT)); // bottom left (container for live icons)
            countdownLabel.setPreferredSize(new Dimension(384,HEIGHT)); // bottom right (countdown label)
            usernameLabel.setPreferredSize(new Dimension(384, HEIGHT)); // upper left (user name)
            scoreLabel.setPreferredSize(new Dimension(384, HEIGHT)); // upper right (score)

            // setting text colors of text labels
            usernameLabel.setForeground(Color.WHITE);
            scoreLabel.setForeground(Color.WHITE);
            highScoreLabel.setForeground(Color.WHITE);
            episodeLabel.setForeground(Color.WHITE);
            countdownLabel.setForeground(Color.WHITE);

            // setting fonts of text labels
            usernameLabel.setFont(AssetBank.getRetroFont());
            episodeLabel.setFont(AssetBank.getRetroFont());
            scoreLabel.setFont(AssetBank.getRetroFont());
            highScoreLabel.setFont(AssetBank.getRetroFont());
            countdownLabel.setFont(AssetBank.getRetroFont());

            // setting backgrounds of panels to black 
            upperPanel.setBackground(Color.BLACK);
            bottomPanel.setBackground(Color.BLACK);
            livesPanel.setBackground(Color.BLACK);
            
            // setting layouts of two sub-components of FooterMenu
            upperPanel.setLayout(new BorderLayout());
            bottomPanel.setLayout(new BorderLayout());

            // adding icons to livesPanel and then adding it to livesContainer
            livesPanel.add(liveIcon1, BorderLayout.WEST);
            livesPanel.add(liveIcon2, BorderLayout.CENTER);
            livesPanel.add(liveIcon3, BorderLayout.EAST);
            livesPanel.setPreferredSize(new Dimension(170, 50));
            livesContainer.add(livesPanel, BorderLayout.CENTER);

            // adding sub-components to upper and bottom panels
            upperPanel.add(usernameLabel, BorderLayout.WEST);
            upperPanel.add(episodeLabel, BorderLayout.CENTER);
            upperPanel.add(countdownLabel, BorderLayout.EAST);

            bottomPanel.add(livesContainer, BorderLayout.WEST);
            bottomPanel.add(highScoreLabel, BorderLayout.CENTER);
            bottomPanel.add(scoreLabel, BorderLayout.EAST);
            bottomPanel.setPreferredSize(new Dimension(WIDTH, getHeight() * 3)); // setting this higher than normal because I don't want it at full south

            // finally adding upper and bottom panels to FooterMenu
            add(upperPanel, BorderLayout.NORTH);
            add(bottomPanel, BorderLayout.CENTER);

            Timer timer = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scoreLabel.setText(String.format("SCORE: %d", CharacterGUI.score));
                }
            });
            timer.start();
            
            if(difficulty == 0){
                timeRemaining = 120;
            }else if(difficulty == 1){
                timeRemaining = 90;
            }else{
                timeRemaining = 45;
            }

            Timer countdownTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(timeRemaining > 0) {
                        timeRemaining--;
                        countdownLabel.setText("TIME: " + timeRemaining);
                    } else {
                        ((Timer)e.getSource()).stop();
                        ((FrameGUI) SwingUtilities.getWindowAncestor(StatsGUI.this)).endGame();
                    }
                }
            });
            countdownTimer.start();
        }
    }
