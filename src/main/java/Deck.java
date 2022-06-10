import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Getter
@Setter
public class Deck {
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
    }

    public Card getCard(){
        return cards.pop();
    }
    public int getSize(){return cards.size();}
}
