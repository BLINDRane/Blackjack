package blackjack;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import java.awt.Color;

/**
 * A visual representation of Card Data
 */
public class GCard extends GCompound {
    
    private Card card;
    private GRect back;

    public GCard(Card card) {
        this.card = card;
        String imageFileName = "cardgifs/" + 
                card.getSuit().toString().substring(0, 1).toLowerCase() +
                (card.getFace().ordinal() + 2) +
                ".gif";
        GImage image = new GImage(imageFileName);
        add(image,1,1);
        GRect border = new GRect(109, 152);
        add(border);
        back= new GRect(107, 150);
        back.setFillColor(Color.blue);
        back.setFilled(true);
        add(back,1,1);
        back.setVisible(!card.isFaceUp());
        this.scale(.75);
    }
    
    public void setFaceUp(boolean flag){
        card.setFaceUp(flag);
        back.setVisible(!flag);
    }
    
    public boolean isFaceUp(){
        return card.isFaceUp();
    }
    
    public int getValue(){
        return card.getValue();
    }

}
