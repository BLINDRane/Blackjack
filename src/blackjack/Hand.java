package blackjack;

/**
 * This class represents 2 cards worth of data
 */
public class Hand {

    private final Card[] cards;
    private int count;
    private final Deck deck;

    public Hand(Deck deck, boolean isDealer) {
        this.deck = deck;
        cards = new Card[7];
        cards[0] = deck.deal();
        cards[1] = deck.deal();
        count = 2;

        if(isDealer){
            cards[0].setFaceUp(false);
        }
    }

    public int getTotal() {
        int sum = 0;
        int aces = 0;
        for (int i = 0; i < count; ++i) {
            sum += cards[i].getValue();
            if(cards[i].getFace()==Card.Face.ACE){
                ++aces;
            }
        }
        while(sum > 21 && aces > 0){
            sum -= 10;
            --aces;
        }
        return sum;
    }
    
    public void hit(){
        cards[count++] = deck.deal();
    }
    
    public Card getCard(int pos){
        return cards[pos];
    }
    
    public int getCount(){
        return count;
    }

}
