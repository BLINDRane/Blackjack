package blackjack;

import acm.graphics.GCompound;

/**
 * A visual representation of a hand (two cards worth of data)
 */
public class GHand extends GCompound {

    private Hand hand;

    //TODO: Store GCards for later access
    private GCard[] cards;

    public GHand(Hand hand) {
        this.hand = hand;
        cards = new GCard[7];

        for (int i = 0; i < hand.getCount(); ++i) {
            Card card = hand.getCard(i);
            GCard gCard = new GCard(card);
            add(gCard, i * (gCard.getWidth() + gCard.getWidth() / 4), 0);
            cards[i] = gCard;
        }

    }

    public int getTotal() {
        return hand.getTotal();
    }

    //TODO: Make a method to flip over the selected card
    public void flipCard(int index){
        cards[index].setFaceUp(!cards[index].isFaceUp());
    }

    public void hit() {
        hand.hit();
        Card card = hand.getCard(hand.getCount()-1);
        GCard gcard = new GCard(card);
        add(gcard, (hand.getCount()-1) * (gcard.getWidth()+gcard.getWidth()/4),0);
    }

}
