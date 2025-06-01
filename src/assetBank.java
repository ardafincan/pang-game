package src;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

import javax.imageio.ImageIO;

// this is where I keep the assets, just to make code more readable
public class AssetBank {
    public static BufferedImage getLiveIcon()
        {
            try {
                return ImageIO.read(new File("../assets/playerLive.png"));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    public static Font getRetroFont()
    {
        Font retroFont;
        try {
                retroFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/PressStart2P-Regular.ttf"))
                                    .deriveFont(Font.PLAIN, 23);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(retroFont);
            } catch (Exception e) {
                retroFont = new Font("Courier New", Font.PLAIN, 23);
            }
        return retroFont;
    }
    
    public static BufferedImage getBackgroundImage(){
        try{
                return ImageIO.read(AssetBank.class.getResource("../assets/selectedBG.png"));
            }catch(IOException ioException){
                return null;
            }
    }

    public static BufferedImage[] getCharacterImages(){
        BufferedImage characterArray[] = new BufferedImage[6];

        try{
            for (int i = 0; i < 6; i++){
                if (i == 0){
                    characterArray[0] = ImageIO.read(AssetBank.class.getResource("../assets/playerStanding.png"));
                }else{
                    BufferedImage chWalk = ImageIO.read(AssetBank.class.getResource(String.format("../assets/playerWalking0%d.png", i)));

                    characterArray[i] = chWalk; 
                }
            }
        }catch(IOException ioException){
            ioException.printStackTrace();
        }

        return characterArray;
    }

    public static BufferedImage getForegroundImage(){
        try{
            return ImageIO.read(AssetBank.class.getResource("../assets/selectedFG.png"));
        }catch(IOException ioException){
            return null;
        }
    }
}
