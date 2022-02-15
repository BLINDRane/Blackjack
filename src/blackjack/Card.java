package blackjack;

/**
This class is representative of card DATA but not imagery.
 */

public class Card {
    
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;
    }
    
    public enum Face {
        DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
    }
    
    private final Face face;
    private final Suit suit;
    private boolean faceUp;
    
    public Card(Face face, Suit suit){
        this.face =face;
        this.suit = suit;
        faceUp = true;
    }

    public Face getFace() {
        return face;
    }

    public Suit getSuit() {
        return suit;
    }
    
    public int getValue(){
        switch(face){
            case ACE:
                return 11;
            case KING:
            case QUEEN:
            case JACK:
                return 10;
            default:
                return face.ordinal()+2;
        }
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
    
    @Override
    public String toString(){
        return face + " OF " + suit;
    }
    
}
