package src;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bubble {
    public static final int XL = 0;
    public static final int L = 1;
    public static final int M = 2;
    public static final int S = 3;

    private static final int[] bubble_radius = {24, 16, 8, 4};
    private static final int[] base_speedsY = {5, 4, 3, 2};
    public boolean isActive;

    private int bubbleX;
    private int bubbleY;
    private int speedX;
    private int speedY;
    private int size;

    public CollisionDetector cDetector = new CollisionDetector();


    public Bubble(int x, int y, int speedX, int speedY, int size){
        this.bubbleX = x;
        this.bubbleY = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.size = size;

        this.isActive = true; // only turn false when got hit
    }

    public void update() {
        if (!isActive) return;

        bubbleX += speedX;
        bubbleY += speedY;

        checkWallCollisions();
    }

    public boolean checkWallCollisions(){
        if (cDetector.isCircleCollidingWithWalls(bubbleX, bubbleY, bubble_radius[size] * 3)){
            speedX = -speedX;
            speedY = -speedY;

            bubbleX += speedX * 2;
            bubbleY += speedY * 2;

            return true;
        }else{
            return false;
        }
    }
    
    public void draw(Graphics g){
        if (!isActive) return;

        BufferedImage[] bubbleImages = AssetBank.getBubbleImages();
        
        int width = bubbleImages[size].getWidth() * 3;
        int height = bubbleImages[size].getHeight() * 3;
        g.drawImage(bubbleImages[size], bubbleX, bubbleY, width, height, null);
    }

    public boolean checkArrowCollision(Rectangle arrow){
        if (!isActive) return false;

        return cDetector.isRectCollidingWithCircle(arrow, bubbleX, bubbleY, bubble_radius[size] * 3);
    }

    public Bubble[] split(){
        if(!isActive || size >= S){
            return new Bubble[0];
        }

        isActive = false;
        
        int newSize = size + 1;
        Bubble[] newBubbles = new Bubble[2];

        newBubbles[0] = new Bubble(bubbleX - 20, bubbleY, -speedX, base_speedsY[newSize], newSize);
        newBubbles[1] = new Bubble(bubbleX + 20, bubbleY, speedX, base_speedsY[newSize], newSize);

        return newBubbles;
    }

    public static int[] getBubbleRadius() {
        return bubble_radius;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getBubbleX() {
        return bubbleX;
    }

    public int getBubbleY() {
        return bubbleY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getSize() {
        return size;
    }
}

