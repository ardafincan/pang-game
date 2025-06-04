package src;

public class BubbleFactory {
    public static Bubble[] initializeBubbles(int difficulty){
        Bubble[] bubbles = new Bubble[2];
        double speedMultiplier = 1;
    
        if(difficulty == 1){
            speedMultiplier = 1.5;
        }else if(difficulty == 2){
            speedMultiplier = 2.5;
        }
        bubbles[0] = new Bubble(95 * 3, 45 * 3, speedMultiplier, Bubble.base_speedsY[Bubble.XL], Bubble.XL);
        bubbles[1] = new Bubble(290 * 3, 45 * 3, speedMultiplier, Bubble.base_speedsY[Bubble.XL], Bubble.XL);

        return bubbles;
    }
}

