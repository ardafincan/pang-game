package src;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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

    public static BufferedImage getForegroundImage(){
        try{
            return ImageIO.read(AssetBank.class.getResource("../assets/selectedFG.png"));
        }catch(IOException ioException){
            return null;
        }
    }

    public static BufferedImage[] getCharacterImages(){
        BufferedImage characterArray[] = new BufferedImage[7];

        try{
            for (int i = 0; i < 6; i++){
                if (i == 0){
                    characterArray[0] = ImageIO.read(AssetBank.class.getResource("../assets/playerStanding.png"));
                }
                else{
                    BufferedImage chWalk = ImageIO.read(AssetBank.class.getResource(String.format("../assets/playerWalking0%d.png", i)));

                    characterArray[i] = chWalk; 
                }
            }
            characterArray[6] = ImageIO.read(AssetBank.class.getResource("../assets/playerShooting.png"));
        }catch(IOException ioException){
            ioException.printStackTrace();
        }

        return characterArray;
    }

    public static BufferedImage[] getArrowImages(){
        BufferedImage weaponArray[] = new BufferedImage[72];

        try{
            for (int i = 0; i < 72; i++){
                BufferedImage weapon = ImageIO.read(AssetBank.class.getResource(String.format("../assets/frame_0%02d.png", i)));

                weaponArray[i] = weapon;
            }
        }catch(IOException ioException){
            ioException.printStackTrace();
        }

        return weaponArray;
    }

    public static BufferedImage[] getBubbleImages(){
        BufferedImage bubbleArray[] = new BufferedImage[4];

        try {
            bubbleArray[0] = ImageIO.read(AssetBank.class.getResource("../assets/selectedBaloonXL.png"));
            bubbleArray[1] = ImageIO.read(AssetBank.class.getResource("../assets/selectedBaloonL.png"));
            bubbleArray[2] = ImageIO.read(AssetBank.class.getResource("../assets/selectedBaloonM.png"));
            bubbleArray[3] = ImageIO.read(AssetBank.class.getResource("../assets/selectedBaloonS.png"));

        } catch (IOException ioException) {
           ioException.printStackTrace();
        }
        return bubbleArray;
    }

    public static AudioInputStream getPopEffect() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        AudioInputStream pop_effect;
        
        InputStream audioSrc = CharacterGUI.class.getResourceAsStream("/assets/pop_sound.wav");
        BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
        pop_effect = AudioSystem.getAudioInputStream(bufferedIn);
        return pop_effect;
    }

    public static AudioInputStream getGameMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        AudioInputStream gameMusic;
        
        InputStream audioSrc = CharacterGUI.class.getResourceAsStream("/assets/game_music.wav");
        BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
        gameMusic = AudioSystem.getAudioInputStream(bufferedIn);
        return gameMusic;
    }
}
