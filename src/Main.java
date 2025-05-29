package src;

// this file is going to be a some kind of facade 
// I am going to use this file only to initialize program

public class Main {
    public static final int ratioConsotant = 3; // this is a constant for resize purposes 
    public static void main(String[] args) {
        FrameGUI gameFrame = new FrameGUI(); // main frame of application

        gameFrame.showFrame(); // initializing frame

    }
}
