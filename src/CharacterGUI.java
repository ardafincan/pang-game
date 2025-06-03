package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

// this class is handling character drawing, moving and shooting
public class CharacterGUI extends JPanel implements Runnable {
    // declaring characterX and Y to specify spawn location
    private int characterX = 510;
    private int characterY = 504;
    private int shootedX = 0;
    private int currentChFrame = 0; // this is for character walking animation (0 is idle)
    private int totalChFrames = 5;  // how many frames there are in total
    private int currentArrowFrame = 0; // this is for shooting animation
    private int totalArrFrames = 72; // how many frames? again

    private boolean isLastLeft = false; // this is kept for declaring if idle state will look either left or right
    private volatile boolean movingLeft = false; // this and below are kept to move character at thread run method
    private volatile boolean movingRight = false; // I explained at above line
    private volatile boolean shooting = false;

    private CollisionDetector cDetector = new CollisionDetector();
    private Thread movementThread; // declaring the thread for handling character movement

    // below is the constructor of CharacterGUI
    public CharacterGUI(){
        setOpaque(false); // setting transparent to make background transparent obviously
        setPreferredSize(new Dimension(AssetBank.getForegroundImage().getWidth() * 3, AssetBank.getForegroundImage().getHeight() * 3)); // setting preferred size according to foreground image

        // below are key bindings, I use them to catch keyEvents, can't use keyListeners because this JPanel is too deeply nested for keyListeners
        // this one is to move character to left when pressed left arrow
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed LEFT"), "moveLeft"); // this says if pressed left then call moveLeft function briefly
        this.getActionMap().put("moveLeft", new AbstractAction() { // this is moveLeft function, actually I guess it is not a function it is a AbstractAction class which we implement its actionPerformed method
            @Override
            public void actionPerformed(ActionEvent e){
                movingLeft = true;
                isLastLeft = true;
            }
        });
        // this one is to stop character and make it at idle appereance
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "stopLeft");
        this.getActionMap().put("stopLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                currentChFrame = 0;
                movingLeft = false;
            }
        });
        // this one is to move character to right
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed RIGHT"), "moveRight");
        this.getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                movingRight = true;
                isLastLeft = false;
            }
        });
        // this one is to stop character and make it idle
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "stopRight");
        this.getActionMap().put("stopRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                currentChFrame = 0;
                movingRight = false;
            }
        });
        // this one is to shoot arrow when pressed to s
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed S"), "shoot");
        this.getActionMap().put("shoot", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                shooting = true;        
            }
        });


        movementThread = new Thread(this); // this is defining the movementThread as a new Thread object
        movementThread.start(); // this is actually starting the thread (calling run method - which is overriden below -)
        
    }
    // END OF CONSTRUCTOR

    // below function is to calles when character is walking and changing its frames to make it look like he is actually walking
    private void cycleCharacterAnimation(){
        currentChFrame = ((currentChFrame + 1) % totalChFrames) == 0 ? currentChFrame + 1 : (currentChFrame + 1) % totalChFrames; // if frame is 0 (idle) make it 1 when cycling animation
    }

    public void cycleShootAnimation(Rectangle rect){
        if(currentArrowFrame == 0 || currentArrowFrame == 2){ // this is for character staying at shooting appearance for 2 frames
            currentChFrame = 6;
        }else if(!movingLeft && !movingRight){
            currentChFrame = 0;
        }
        if(currentArrowFrame != 70){
            // check if colliding here if not continue and if colliding do the same thing as else block below
            if(cDetector.isRectCollidingWithWalls(rect)){
                shooting = false;
                currentArrowFrame = 0;
                return;
            }
            currentArrowFrame += 2;
            if(currentArrowFrame == 23 || currentArrowFrame == 47){
                currentArrowFrame++;
            }
        }else{
            shooting = false;
            currentArrowFrame = 0;
        }
   }

    // below is what movementThread will do when it started running
    @Override
    public void run(){
        while(true){ // always check if character should move either direction and if yes move and cycle animation
            if(movingLeft){
                if(characterX > 25){
                    characterX -= 15;
                }
                cycleCharacterAnimation();
            }
            if(movingRight){
                if(characterX < 1045)
                characterX += 15;
                cycleCharacterAnimation();
            }
            if(shooting){
                int characterWidth = AssetBank.getCharacterImages()[currentChFrame].getWidth() * 3; // taking width and heights of character to draw 
                int characterHeight = AssetBank.getCharacterImages()[currentChFrame].getHeight() * 3;

                int arrowWidth = AssetBank.getArrowImages()[currentArrowFrame].getWidth() * 3;
                int arrowHeight = AssetBank.getArrowImages()[currentArrowFrame].getHeight() * 3;
                
                shootedX = currentArrowFrame == 0 ? characterX : shootedX;

                Rectangle rect = new Rectangle(shootedX + (characterWidth/2), characterY - arrowHeight + characterHeight, arrowWidth, arrowHeight);
                cycleShootAnimation(rect);
            }
            SwingUtilities.invokeLater(() -> repaint()); //  this is a bit long to explain but if I got it right it is something like: OK, the job of this thread is done and we have to tell swing to repaint but it is on edt so it tells edt to repaint.
            try{
                Thread.sleep(50); // sleep 75 ms so it doesnt seem like in light speed
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }

    }

    // this is actually called in background when we call repaint() 
    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int characterWidth = AssetBank.getCharacterImages()[currentChFrame].getWidth() * 3; // taking width and heights of character to draw 
        int characterHeight = AssetBank.getCharacterImages()[currentChFrame].getHeight() * 3;

        int arrowWidth = AssetBank.getArrowImages()[currentArrowFrame].getWidth() * 3;
        int arrowHeight = AssetBank.getArrowImages()[currentArrowFrame].getHeight() * 3;
        
        shootedX = currentArrowFrame == 0 ? characterX : shootedX;

        g.drawImage(AssetBank.getForegroundImage(), 0, 0,getWidth(), getHeight(), null); // draw to foreground without any condition
        if(shooting){
            g.drawImage(AssetBank.getArrowImages()[currentArrowFrame], shootedX + (characterWidth/2), characterY - arrowHeight + characterHeight, arrowWidth, arrowHeight, null);
        }
        if(movingLeft || isLastLeft){ // this is checking if character is moving left or last move was towards left, if it is then we will draw him horizontally flipped
             g.drawImage(AssetBank.getCharacterImages()[currentChFrame], characterX + characterWidth, characterY, -characterWidth, characterHeight, null); // just draw to character
        }else{
             g.drawImage(AssetBank.getCharacterImages()[currentChFrame], characterX, characterY, characterWidth, characterHeight, null);
        }
        // g.setColor(Color.MAGENTA);
        // //Rectangle wall = new Rectangle(184 * 3, 48 * 3, 16 * 3, 40 * 3);
        // g.drawRect(184 * 3, 48 * 3, 16 * 3, 40 * 3);
        // g.drawRect(184 * 3, 128 * 3, 16 * 3, 40 * 3);
        // g.drawRect(64 * 3, 96 * 3, 64 * 3, 16 * 3);
        // g.drawRect((128 + 128) * 3, 96 * 3, 64 * 3, 16 * 3);
        // g.drawRect(8 * 3, 8 * 3, 368 * 3, 192 * 3);
        // //g.fillRect(characterWidth, characterHeight, arrowWidth, arrowHeight);
        // // BELOW WILL BE DELETED BEFORE COMPILE
        // g.drawArc(arrowHeight, arrowHeight, characterWidth, characterHeight, arrowWidth, arrowHeight); // THIS WILL BE DELETED BEFORE COMPILE 
    }
}