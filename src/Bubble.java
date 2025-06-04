package src;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bubble {
    public static final int XL = 0;
    public static final int L = 1;
    public static final int M = 2;
    public static final int S = 3;

    public static final int[] bubble_radius = {24, 16, 8, 4};
    public static final int[] base_speedsY = {30, 25, 23, 20};
    public boolean isActive;

    public int bubbleX;
    public int bubbleY;
    public int speedX = 30;
    public int speedY;
    public int size;
    public int radius;

    public CollisionDetector cDetector = new CollisionDetector();


    public Bubble(int x, int y, double xSpeedMultiplier, int speedY, int size){
        this.bubbleX = x;
        this.bubbleY = y;
        this.speedX = (int)(5 * xSpeedMultiplier);
        this.speedY = speedY;
        this.size = size;
        this.radius = AssetBank.getBubbleImages()[size].getWidth()/ 2;

        this.isActive = true; // only turn false when got hit
    }

    public void update() {
        if (!isActive) return;

        // Apply gravity
        speedY += 1; // Add gravity acceleration
        
        bubbleX += speedX;
        bubbleY += speedY;

        // Check screen boundaries first
        checkScreenBoundaries();
        
        // Then check wall collisions
        checkWallCollisions();
    }

    private void checkScreenBoundaries() {
               
        // Left boundary
        if (bubbleX <= 24) {
            bubbleX = 24 + radius;
            speedX = -speedX;
        }
        
        // Right boundary  
        if (bubbleX + radius >= 1080) {
            bubbleX = 1080 - radius;
            speedX = -speedX;
        }
        
        // Ground boundary
        if (bubbleY + radius >= 590) {
            bubbleY = 590 - radius;
            speedY = -speedY; // Bounce up
        }
        
        // Ceiling boundary
        if (bubbleY <= 24) {
            bubbleY = 24 + radius;
            speedY = -speedY;
        }
    }

    public boolean checkWallCollisions(){
        BufferedImage[] bubbleImages = AssetBank.getBubbleImages();
        int imageWidth = bubbleImages[size].getWidth() * 3;
        int imageHeight = bubbleImages[size].getHeight() * 3;
        
        // Calculate actual center of the bubble image
        int centerX = bubbleX + (imageWidth / 2);
        int centerY = bubbleY + (imageHeight / 2);
        
        // Pass the center coordinates, not center + radius
        if (cDetector.isCircleCollidingWithWalls(centerX, centerY, this.radius)){
            //speedX = -speedX;
            speedY = -speedY;
            
            // Move bubble away from wall
            //bubbleX += speedX;
            bubbleY += speedY;
            
            return true;
        }
        return false;
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

        // Get the actual drawn image dimensions
        BufferedImage[] bubbleImages = AssetBank.getBubbleImages();
        int imageWidth = bubbleImages[size].getWidth() * 3;
        int imageHeight = bubbleImages[size].getHeight() * 3;
        
        // Calculate center based on image position + half image size
        int centerX = bubbleX + (imageWidth / 2);
        int centerY = bubbleY + (imageHeight / 2);
        
        return cDetector.isRectCollidingWithCircle(arrow, centerX, centerY, this.radius);
    }

        public boolean checkCharacterCollision(Rectangle character) {
        if (!isActive) return false;

        BufferedImage[] bubbleImages = AssetBank.getBubbleImages();
        int imageWidth = bubbleImages[size].getWidth() * 3;
        int imageHeight = bubbleImages[size].getHeight() * 3;
        
        // Calculate center of the bubble image
        int centerX = bubbleX + (imageWidth / 2);
        int centerY = bubbleY + (imageHeight / 2);

        return cDetector.isRectCollidingWithCircle(character, centerX, centerY, this.radius);
    }

    public Bubble[] split(){
        if(!isActive || size >= S){
            isActive = false;
            return new Bubble[0];
        }

        isActive = false;
        
        int newSize = size + 1;
        Bubble[] newBubbles = new Bubble[2];

        // Create bubbles moving in opposite directions
        newBubbles[0] = new Bubble(bubbleX - 30, bubbleY, -1.0, -base_speedsY[newSize], newSize);
        newBubbles[1] = new Bubble(bubbleX + 30, bubbleY, 1.0, -base_speedsY[newSize], newSize);

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

