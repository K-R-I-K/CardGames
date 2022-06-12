import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Comparator;
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
    //where to put this impl
    //this comparator need to sort player cards
    public Comparator<Card> getComparator(){
        return (o1, o2) -> {
            if (o1.getSuit() == trump.getSuit() && o2.getSuit() != trump.getSuit()) {
                return 1;
            } else if (o1.getSuit() != trump.getSuit() && o2.getSuit() == trump.getSuit()) {
                return -1;
            } else {
                return o1.compareTo(o2);
            }
        };
    }

}
