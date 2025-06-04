package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuItem;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

// this class is the main frame of program

public class FrameGUI extends JFrame{
    int difficulty = 0;// this is to select difficulty
    private GameGUI gameScreen; // the game screen

    public FrameGUI(){
        super("PANG"); // constructor creates a frame with title PANG
    }

    public void showFrame(){
        // below is for the upper menu bar 
        JMenuBar menuBar = new JMenuBar(); 
        JMenu menuGame = new JMenu("Game");
        JMenu menuOption = new JMenu("Options");
        JMenu menuHelp = new JMenu("Help");
        JMenu difficultyItem = new JMenu("Difficulty");
        JMenuItem loginItem = new JMenuItem("Login");
        JMenuItem registerItem = new JMenuItem("Register");
        JMenuItem quitItem = new JMenuItem("Quit");
        JMenuItem easyDiff = new JMenuItem("Novice");
        JMenuItem mediumDiff = new JMenuItem("Intermediate");
        JMenuItem hardDiff = new JMenuItem("Advanced");
        JMenuItem historyItem = new JMenuItem("History");
        JMenuItem hScoreItem = new JMenuItem("High Score");
        JMenuItem aboutItem = new JMenuItem("About");

        // adding actionListener to quit button
        quitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(ABORT);
            }
        });

        // adding actionListener to about button
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, String.format("Ali Arda Fincan\n20230702099\nardafincan@icloud.com"), "About", JOptionPane.DEFAULT_OPTION);
            }
        });

        easyDiff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                difficulty = 0;
            }
        });

        mediumDiff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                difficulty = 1;
            }
        });

        hardDiff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                difficulty = 2;
            }
        });

        loginItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                LoginManager.Login();
            }
        });

        registerItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                LoginManager.Register();
            }
        });

        historyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                HistoryManager.showHistory();
            }
        });

        hScoreItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                HistoryManager.showHighScores();
            }
        });



        menuBar.add(menuGame);
        menuBar.add(menuOption);
        menuBar.add(menuHelp);

        menuGame.add(loginItem);
        menuGame.add(registerItem);
        menuGame.add(quitItem);

        menuOption.add(difficultyItem);
        menuOption.add(historyItem);
        menuOption.add(hScoreItem);

        menuHelp.add(aboutItem);

        difficultyItem.add(easyDiff);
        difficultyItem.add(mediumDiff);
        difficultyItem.add(hardDiff);

        this.setJMenuBar(menuBar);
        
        this.setLayout(new GridBagLayout()); // setting GridBagLayout in order to put start button to center of frame without occupating every blank space

        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(120, 50));
        startButton.addActionListener(new ActionListener() { // adding an action listener to listen if user clicked start
            public void actionPerformed(ActionEvent e){
                if(LoginManager.isUserLoggedIn){
                    remove(startButton); // remove button before starting game
                    setLayout(new BorderLayout(0, 0));
                    gameScreen = new GameGUI(difficulty);
                    add(gameScreen, BorderLayout.CENTER);
                    revalidate(); // check if everything is alright
                    repaint(); // update screen
                }else{
                    JOptionPane.showMessageDialog(FrameGUI.this, String.format("Please login first! \nGame -> Register / Game -> Login"), "PANG", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // below is the final constraints of JFrame
        this.add(startButton, new GridBagConstraints());
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(384 * Main.ratioConsotant, 260 * Main.ratioConsotant));
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void endGame() {
        if (gameScreen != null) {
            HistoryManager.writeToFile(new String[]{LoginManager.userName, String.valueOf(CharacterGUI.score), LocalDateTime.now().toString()});
            if (!CharacterGUI.isWin){
                remove(gameScreen);
                gameScreen = null;

                JOptionPane.showMessageDialog(this, "Game Over!", "PANG", JOptionPane.INFORMATION_MESSAGE);
            }else{
                remove(gameScreen);
                gameScreen = null;

                JOptionPane.showMessageDialog(this, "YOU WIN!", "PANG", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        
        
        setLayout(new GridBagLayout());
        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(120, 50));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CharacterGUI.score = 0;
                remove(startButton);
                setLayout(new BorderLayout(0, 0));
                gameScreen = new GameGUI(difficulty);
                add(gameScreen, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        add(startButton, new GridBagConstraints());

        revalidate();
        repaint();
    }
}
