import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Stack;

/**
 * Class which represents deck in our card game
 */
@Getter
@Setter
public class Deck implements Serializable {
    @Serial
    private static final long serialVersionUID = 6518685098267757690L;
    private Stack<Card> cards;
    private Card trump;
    {
        cards = new Stack<>();
        for(Rank rank: Rank.values()){
            for(Suit suit: Suit.values()){
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
        trump = cards.firstElement();
        Card.setTrump(trump.getSuit());
    }

    /**
     * Getter for Card from Deck
     * @return Card which giving deck
     */
    public Card getCard(){
        return cards.pop();
    }

    /**
     * Getter for size of deck
     * @return Size of deck
     */
    public int getSize(){return cards.size();}

    /**
     * The method to find out if the deck is empty
     * @return True if deck is empty, and false if not
     */
    public boolean isEmpty(){return cards.size()==0;}
}
