package src;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class assetBank {
    public static BufferedImage liveIcon;
        {
            try {
                liveIcon = ImageIO.read(new File("../assets/playerLive.png"));
            } catch (IOException e) {
                e.printStackTrace();
                liveIcon = null;
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
                return ImageIO.read(assetBank.class.getResource("../assets/selectedBG.png"));
            }catch(IOException ioException){
                return null;
            }
    }
}
