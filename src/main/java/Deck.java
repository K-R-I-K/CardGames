import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Stack;

@Getter
@Setter
public class Deck {
    private static Stack<Card> cards;
    private static Suit trump;
    static{
        cards = new Stack<>();
        for(Rank rank: Rank.values()){
            for(Suit suit: Suit.values()){
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
        trump = cards.firstElement().getSuit();
    }

    public static Card getCard(){
        return cards.pop();
    }
}
