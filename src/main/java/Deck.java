import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

@Getter
@Setter
public class Deck implements Serializable {
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

    public Card getCard(){
        return cards.pop();
    }
    public int getSize(){return cards.size();}
    public boolean isEmpty(){return cards.size()==0;}
}
