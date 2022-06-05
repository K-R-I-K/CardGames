import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Stack;

@Getter
@Setter
public class Deck {
    private Stack<Card> cards;
    private Suit trump;
    {
        cards = new Stack<>();
        for(Rank rank: Rank.values()){
            for(Suit suit: Suit.values()){
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
        trump = cards.firstElement().getSuit();
    }

    public Card getCard(){
        return cards.pop();
    }
}
