package src;

import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

// this class is going to be used detecting collisions
public class CollisionDetector {
    // setting walls 
    private Wall wall1 = new Wall(184, 48, 16, 40); 
    private Wall wall2 = new Wall(184, 128, 16, 40);
    private Wall wall3 = new Wall(64, 96, 64, 16);
    private Wall wall4 = new Wall(256, 96, 64, 16);

    private Wall screenBounds = new Wall(8, 8, 368, 192);

    private Wall[] walls = {wall1, wall2, wall3, wall4};

    // this function is checking if a point is colliding  with wall (used in other methods)
    public boolean isPointCollidingWithWalls(Point p){
        int x = p.x;
        int y = p.y;

        for(Wall wall : walls){ // iterate over walls and check if point is between borders of any wall
            if (wall.x <= x && x <= (wall.x + wall.width) && wall.y <= y && y <= (wall.y + wall.height)){
                return true;
            }
        }
        // also checking if point colliding with game boundaries
        if (x <= screenBounds.x || x >= (screenBounds.x + screenBounds.width) || y <= screenBounds.y || y >= (screenBounds.y + screenBounds.height)){
            return true;
        }
        return false; // if none of above is true then return false 
    }

    // this one is checking if a rectangle is colliding with walls 
    public boolean isRectCollidingWithWalls(Rectangle rect){
        int x = (int) rect.getX();
        int y = (int) rect.getY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();

        // checking each border (top-bottom-left-right)
        for(int i = 0; i < width; i++){ // checking top side
            Point tempPoint = new Point((x + i), y);
            return isPointCollidingWithWalls(tempPoint);
        }
        
        for(int i = 0; i < width; i++){ // checking bottom side
            Point tempPoint = new Point((x + i), (y + height));
            return isPointCollidingWithWalls(tempPoint);
        }

        for(int i = 0; i < height; i++){ // checking left side
            Point tempPoint = new Point(x, (y + i));
            return isPointCollidingWithWalls(tempPoint);
        }

        for(int i = 0; i < height; i++){ // checking right side
            Point tempPoint = new Point((x + width), (y + i));
            return isPointCollidingWithWalls(tempPoint);
        }

        return false;
    }

    // this one is checking if a circle is colliding with walls
    public boolean isCircleCollidingWithWalls(Point center, int radius){
        int x = center.x;
        int y = center.y;
        
        Circle circ = new Circle(x, y, radius); // creating a circle to compute which points to look
        Point[] pts = circ.circlePoints; // creating an array of points 

        // checking each point to look if they collide
        for (Point p : pts){
            if (isPointCollidingWithWalls(p)) return true;
        }

        return false;
    }

    // this one is checking if given rectangle and given circle are colliding with each other
    public boolean isRectCollidingWithCircle(Rectangle rect, int circleCenterX, int circleCenterY, int radius){
        // taking x, y, width and height of rectangle to compute collision conditions
        int rectX = (int) rect.getX();
        int rectY = (int) rect.getY();
        int rectWidth = (int) rect.getWidth();
        int rectHeight = (int) rect.getHeight();

        Circle circ = new Circle(circleCenterX, circleCenterY, radius);
        Point[] circPts = circ.circlePoints;

        // taking each point in circPts to check if that point of circle is colliding the rectangle
        for(Point circP : circPts){
            int pointX = circP.x;
            int pointY = circP.y;

            // checking if given point is in (or on) the boundaries of rectangle
            if (rectX <= pointX && pointX <= (rectX + rectWidth) && rectY <= pointY && pointY <= (rectY + rectHeight)) return true;
        }

        return false;
    }

    // this is an inner class used for defnining circles
    private class Circle{
        public int x, y, radius;
        public int pointCount = 100; // deciding how many points going to be used when defining circle boundaries
        public Point[] circlePoints = new Point[pointCount]; 

        public Circle(int x, int y, int radius){
            this.x = x;
            this.y = y;
            this.radius = radius;

            // assigning 100 points as boundaries of circle
            for(int i = 0; i < pointCount; i++){
                double angle = Math.toRadians(((double) i / pointCount) * 360d); // calculating the angle which will be used in computing

                circlePoints[i] = new Point(Math.cos(angle) * radius, Math.sin(angle) * radius); // assigning pts
            }
        }
    }

    // this is an inner class used for defining point (used like a c struct)
    private class Point{
        public int x ,y;

        public Point(double x, double y){
            this.x = (int) x;
            this.y = (int) y;
        }
    }
    
    // this is an inner class used for defining walls usefully
    private class Wall {
        public int x, y, width, height;

        public Wall(int x, int y, int width, int heigth) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = heigth;
        }
    }
}
