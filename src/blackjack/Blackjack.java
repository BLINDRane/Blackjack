package blackjack;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import svu.csc213.Dialog;

/**
 * Allows you to play the game of Blackjack
 */

public class Blackjack extends GraphicsProgram {

    private GLabel bankLabel;
    private GLabel wagerLabel;
    private GLabel balanceLabel;
    private JButton wagerButton;
    private JButton playButton;
    private JButton hitButton;
    private JButton stayButton;
    private JButton quitButton;
    private int wager = 0;
    private int balance = 10000;
    private int bank = 10000;
    private Deck deck;
    private GHand player;
    private GHand dealer;
    private GLabel blackjack;

    //puts all buttons and labels on screen.
    public void init() {

        deck = new Deck();
        this.setBackground(Color.green);

        wagerButton = new JButton("Wager");
        add(wagerButton, SOUTH);
        playButton = new JButton("Play");
        add(playButton, SOUTH);
        hitButton = new JButton("Hit");
        add(hitButton, SOUTH);
        stayButton = new JButton("Stay");
        add(stayButton, SOUTH);
        quitButton = new JButton("Quit");
        add(quitButton, SOUTH);

        //playButton.setVisible(false);
        //hitButton.setVisible(false);
        //stayButton.setVisible(false);

        addActionListeners();

        wagerLabel = new GLabel("Current Wager: $" + wager);
        wagerLabel.setFont("Times-bold-24");
        add(wagerLabel, (getWidth() - wagerLabel.getWidth()) / 2.0, 300);

        balanceLabel = new GLabel("Current Balance: $" + balance);
        balanceLabel.setFont("Times-bold-24");
        add(balanceLabel, (getWidth() - balanceLabel.getWidth()) / 2.0, 350);

        bankLabel = new GLabel("Bank: $" + bank);
        bankLabel.setFont("Times-bold-24");
        add(bankLabel, (getWidth() - bankLabel.getWidth()) / 2.0, 400);

        blackjack = new GLabel("BlackJack");
        blackjack.setFont("Times-bold-30");

        add(blackjack, (getWidth() - blackjack.getWidth()) / 2.0, 100);

    }

    //detects button presses and activates the relevant logic
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Wager":
                wager();
                break;
            case "Play":
                play();
                bank();
                balance();
                break;
            case "Hit":
                hit();
                break;
            case "Stay":
                stay();
                break;
            case "Quit":
                quit();
                break;
        }
    }

    public void play() {
        wagerLabel.setVisible(false);
        balanceLabel.setVisible(false);
        bankLabel.setVisible(false);
        blackjack.setVisible(false);

        if (dealer != null) {
            remove(dealer);
        }

        dealer = new GHand(new Hand(deck, true));
        add(dealer, 25, 50);

        if (player != null) {
            remove(player);
        }

        player = new GHand(new Hand(deck, false));
        add(player, 25, 200);
    }

    //Hit will cause the player to draw a card, unless they are already at 21 or higher. Then they will lose.
    private void hit() {
        if (player.getTotal() <= 21) {
            player.hit();
        }

        if (player.getTotal() >= 21) {
            Dialog.showMessage(this, "You bust. Hold back buddy!");
            lose();
        }
    }

    //Stay will begin the process of evaluating who won and who lost.
    private void stay() {

        dealer.flipCard(0);

       //Check to see if the player or dealer has already won
        if (dealer.getTotal() == 21) {
            Dialog.showMessage(this, "Sorry mate, you lose. Dealer has a blackjack.");
            lose();
        } else if (player.getTotal() == 21) {
            Dialog.showMessage(this, "You have a blackjack! You win!");
            win();
        } else {

            //see if the dealer has to draw.
            while (dealer.getTotal() <= 16) {

                dealer.hit();

                if (dealer.getTotal() >= 21) {
                    Dialog.showMessage(this, "The dealer busted. You win!(dealer has over 21)");
                    win();
                    break;
                }
            }

            //If after all that, there is no winner, check this logic.
            if (dealer.getTotal() == player.getTotal()) {
                Dialog.showMessage(this, "Tie. No money won, no money lost.");
            } else if (dealer.getTotal() >= player.getTotal()) {
                Dialog.showMessage(this, "Oh dear. Dealer wins.(the dealer got closer to 21 than you");
                lose();
            } else if (player.getTotal() >= dealer.getTotal()) {
                Dialog.showMessage(this, "You snatched victory from the dealer! Why not try for a perfect Blackjack?(you got closer to 21 than the dealer)");
                win();
            }

        }
    }

    private void quit() {
        Dialog.showMessage(this, "Goodbye, gamblin man.");
        System.exit(0);
    }

    public void wager() {
        int newWager = -1;
        while (newWager < 1 || newWager > balance) {
            newWager = Dialog.getInteger(this, "Enter New Wager:");
            if (newWager < 1 || newWager > balance) {
                Dialog.showMessage(this, "Please Enter A Wager \n Between 1 and " + balance);
            }
        }
        wagerLabel.setLabel("Current Wager:$" + newWager);
        wager = newWager;
    }

    //like wager but allows cheat code type behavior
    public void makeWager(){
        String s = "";
        boolean valid = false;
        while(!valid){

            try{
                s = Dialog.getString("Enter your wager. A number, or perhaps...something more....");
                int x = Integer.parseInt(s);
                wagerLabel.setLabel("Current Wager:$" + x);
                wager = x;
            } catch (Exception e) {

                switch(s.toLowerCase()){
                    case "my soul" :
                        Dialog.showMessage("BAHAHAHA. DEAL.");
                        valid = true;
                        wagerLabel.setLabel("Current Wager: " + "YOUR SOUL");
                        wager = 10000;
                        break;
                    default:
                        Dialog.showMessage("I can't do anything with " + s.toLowerCase() + " I reject your wager.");
                }
            }

        }
    }

    public void bank() {
        if (bank == 0) {
            Dialog.showMessage(this, "You bankrupted the dealer.");
            System.exit(0);
        }
    }

    public void balance() {
        if (balance == 0) {
            Dialog.showMessage(this, "You are out of money. Goodbye!");
            System.exit(0);
        }
    }

    public void win() {
        dealer.removeAll();
        player.removeAll();
        wagerLabel.setVisible(true);
        balanceLabel.setVisible(true);
        bankLabel.setVisible(true);
        blackjack.setVisible(true);
        balanceLabel.setLabel("Current Balance: $" + (balance + (wager * 2)));
        bankLabel.setLabel("Bank: $" + (bank - (wager)));
        balance = (balance + (wager * 2));
        bank = (bank - (wager));
    }

    public void lose() {
        dealer.removeAll();
        player.removeAll();
        wagerLabel.setVisible(true);
        balanceLabel.setVisible(true);
        bankLabel.setVisible(true);
        blackjack.setVisible(true);
        balanceLabel.setLabel("Current Balance: $" + (balance - wager));
        bankLabel.setLabel("Bank: $" + (bank + wager));
        balance = (balance - wager);
        bank = (bank + wager);
    }

    public void run() {
//        Deck deck = new Deck();
//        GHand hand = new GHand(new Hand(deck));
//        add(hand, 100, 100);
//        hand.hit();
    }

    public static void main(String[] args) {
        new Blackjack().start();
    }

}
