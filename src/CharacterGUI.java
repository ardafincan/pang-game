package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

// this class is handling character drawing, moving and shooting
public class CharacterGUI extends JPanel implements Runnable {
    // declaring characterX and Y to specify spawn location
    public static boolean isWin = false;
    public static int characterLives = 3;
    public static int score = 0;

    private int characterX = 510;
    private int characterY = 504;
    private int characterWidth = AssetBank.getCharacterImages()[0].getWidth() * 3;
    private int characterHeight = AssetBank.getCharacterImages()[0].getHeight() * 3;
    private int shootedX = 0;
    private int currentChFrame = 0; // this is for character walking animation (0 is idle)
    private int totalChFrames = 5;  // how many frames there are in total
    private int currentArrowFrame = 0; // this is for shooting animation

    private boolean isNowTouched = false;
    private boolean isLastLeft = false; // this is kept for declaring if idle state will look either left or right
    private volatile boolean movingLeft = false; // this and below are kept to move character at thread run method
    private volatile boolean movingRight = false; // I explained at above line
    private volatile boolean shooting = false;

    private ArrayList<Bubble> bubbles = new ArrayList<Bubble>();

    private CollisionDetector cDetector = new CollisionDetector();
    private Thread movementThread; // declaring the thread for handling character movement

    private AudioInputStream popEffect;
    private AudioInputStream gameMusic;

    Clip musicClip = AudioSystem.getClip();
    Clip popClip = AudioSystem.getClip();

    // below is the constructor of CharacterGUI
    public CharacterGUI(int difficulty) throws Exception {
        // Initialize audio
        popEffect = AssetBank.getPopEffect();
        gameMusic = AssetBank.getGameMusic();
        

        musicClip.open(gameMusic); 
        popClip.open(popEffect);

        musicClip.setFramePosition(0);
        musicClip.start();

        setOpaque(false); // setting transparent to make background transparent obviously
        setPreferredSize(new Dimension(AssetBank.getForegroundImage().getWidth() * 3, AssetBank.getForegroundImage().getHeight() * 3)); // setting preferred size according to foreground image

        // create bubbles
        for (Bubble bubble : BubbleFactory.initializeBubbles(difficulty)){
            bubbles.add(bubble);
        }

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
                if(!shooting){
                    shootedX = characterX;
                    shooting = true;
                }        
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
        if(currentArrowFrame == 0 || currentArrowFrame == 3){ // this is for character staying at shooting appearance for 2 frames
            currentChFrame = 6;
        }else if(!movingLeft && !movingRight){
            currentChFrame = 0;
        }
        if(currentArrowFrame != 69){
            // check if colliding here if not continue and if colliding do the same thing as else block below
            if(cDetector.isRectCollidingWithWalls(rect)){ // if arrow collides with a wall then stop shooting
                shooting = false;
                currentArrowFrame = 0;
                return;
            }
            
            // Check collision with bubbles
            checkArrowBubbleCollision(rect);
            
            currentArrowFrame += 3; // increasing 3 by 3 in order to make it faster without changing thread sleep
            if(currentArrowFrame == 23 || currentArrowFrame == 47){ // these frames are blank
                currentArrowFrame++;
            }
        }else{ // if currentArrowFrame == 69 then stop shooting
            shooting = false;
            currentArrowFrame = 0;
        }
   }
   
   private void checkArrowBubbleCollision(Rectangle arrowRect) {
       Iterator<Bubble> iterator = bubbles.iterator();
       while (iterator.hasNext()) {
           Bubble bubble = iterator.next();
           if (bubble.isActive() && bubble.checkArrowCollision(arrowRect)) {
                popClip.setFramePosition(0);
                popClip.start();
            
               if(bubble.size == Bubble.XL){
                score += 50;
               }else if(bubble.size == Bubble.L){
                score += 100;
               }else if(bubble.size == Bubble.M){
                score += 150;
               }else{
                score += 200;
               }
               Bubble[] newBubbles = bubble.split();
               iterator.remove(); // Remove the hit bubble
               
               // Add new bubbles if any
               for (Bubble newBubble : newBubbles) {
                   bubbles.add(newBubble);
               }
               
               // Stop shooting
               currentArrowFrame = 0;
               shooting = false;
               break; // Only hit one bubble per arrow
           }
       }
   }

    // below is what movementThread will do when it started running
    @Override
    public void run(){
        while(true){ // always check if character should move either direction and if yes move and cycle animation
            if(movingLeft){
                if(characterX > 25){ // checking game border
                    characterX -= 15;
                }
                cycleCharacterAnimation(); // the animation function to make it character seem like walking
            }
            if(movingRight){
                if(characterX < 1045) // again checking game border
                characterX += 15;
                cycleCharacterAnimation();
            }
            if(shooting){ // if shooting then cycle shoot animation
                int characterWidth = AssetBank.getCharacterImages()[currentChFrame].getWidth() * 3; // taking width and heights of character to draw 
                int characterHeight = AssetBank.getCharacterImages()[currentChFrame].getHeight() * 3;

                int arrowWidth = AssetBank.getArrowImages()[currentArrowFrame].getWidth() * 3;
                int arrowHeight = AssetBank.getArrowImages()[currentArrowFrame].getHeight() * 3;
                
                Rectangle rect = new Rectangle(shootedX + (characterWidth/2), characterY - arrowHeight + characterHeight, arrowWidth, arrowHeight);
                cycleShootAnimation(rect);
            }
            
            for (Bubble bubble : bubbles) {
                bubble.update();
                if(!bubble.isActive){
                    bubbles.remove(bubble);
                }

                if(bubble.checkCharacterCollision(new Rectangle(characterX, characterY, characterWidth, characterHeight)) && !isNowTouched){
                        characterLives--;
                        isNowTouched = true;
                    if(characterLives == 0){
                        ((FrameGUI) SwingUtilities.getWindowAncestor(CharacterGUI.this)).endGame();
                    }
                }
                if(bubble.checkCharacterCollision(new Rectangle(characterX, characterY, characterWidth, characterHeight)) && isNowTouched){
                    isNowTouched = false;
                }
            }

            int activeBubbleCnt = 0;
            for (Bubble bubble : bubbles){
                if(bubble.isActive) activeBubbleCnt++;
            }
            if(activeBubbleCnt == 0){
                isWin = true;
                ((FrameGUI) SwingUtilities.getWindowAncestor(CharacterGUI.this)).endGame();
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

        int arrowWidth = AssetBank.getArrowImages()[currentArrowFrame].getWidth() * 3;
        int arrowHeight = AssetBank.getArrowImages()[currentArrowFrame].getHeight() * 3;
        
        g.drawImage(AssetBank.getForegroundImage(), 0, 0,getWidth(), getHeight(), null); // draw to foreground without any condition
        
        // draw each bubble
        for (Bubble bubble : bubbles) {
            bubble.draw(g);
        }
        
        if(shooting){
            g.drawImage(AssetBank.getArrowImages()[currentArrowFrame], shootedX + (characterWidth/2), characterY - arrowHeight + characterHeight, arrowWidth, arrowHeight, null);
        }
        if(movingLeft || isLastLeft){ // this is checking if character is moving left or last move was towards left, if it is then we will draw him horizontally flipped
             g.drawImage(AssetBank.getCharacterImages()[currentChFrame], characterX + characterWidth, characterY, -characterWidth, characterHeight, null); // just draw the character
        }else{
             g.drawImage(AssetBank.getCharacterImages()[currentChFrame], characterX, characterY, characterWidth, characterHeight, null);
        }
    }
}