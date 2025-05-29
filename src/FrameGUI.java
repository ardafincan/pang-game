package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// this class is the main frame of program

public class FrameGUI extends JFrame{
    public FrameGUI(){
        super("PANG"); // constructor creates a frame with title PANG
    }

    public void showFrame(){
        // below is for the upper menu bar 
        JMenuBar menuBar = new JMenuBar(); 
        JMenu menuGame = new JMenu("Game");
        JMenu menuOption = new JMenu("Options");
        JMenu menuHelp = new JMenu("Help");
        JMenuItem registerItem = new JMenuItem("Register");
        JMenuItem quitItem = new JMenuItem("Quit");
        JMenuItem difficultyItem = new JMenuItem("Difficulty");
        JMenuItem historyItem = new JMenuItem("History");
        JMenuItem hScoreItem = new JMenuItem("High Score");
        JMenuItem aboutItem = new JMenuItem("About");

        menuBar.add(menuGame);
        menuBar.add(menuOption);
        menuBar.add(menuHelp);

        menuGame.add(registerItem);
        menuGame.add(quitItem);

        menuOption.add(difficultyItem);
        menuOption.add(historyItem);
        menuOption.add(hScoreItem);

        menuHelp.add(aboutItem);

        this.setJMenuBar(menuBar);
        
        this.setLayout(new GridBagLayout()); // setting GridBagLayout in order to put start button to center of frame without occupating every blank space

        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(120, 50));
        startButton.addActionListener(new ActionListener() { // adding an action listener to listen if user clicked start
            public void actionPerformed(ActionEvent e){
                // remove button and show game screen if button is clicked
                remove(startButton);
                setLayout(new BorderLayout(0, 0));

                GameGUI gameScreen = new GameGUI();

                add(gameScreen, BorderLayout.CENTER);

                revalidate(); // checking if everything is still alright
                repaint(); // update screen to show new form of screen
            }
        });

        this.add(startButton, new GridBagConstraints());
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(384 * Main.ratioConsotant, 260 * Main.ratioConsotant));
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}
