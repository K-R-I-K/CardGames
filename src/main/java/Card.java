import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;

/**
 * Class which represents card in our card game
 */
@Getter
@Setter
@NoArgsConstructor
public class Card implements Comparable<Card>, Serializable {
    @Serial
    private static final long serialVersionUID = 6529575087167757690L;
    private Rank rank;
    private Suit suit;
    private static Suit trump;//field for client and sorting card list
    Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Setter for trump
     * @param trump Trump for all cards
     */
    public static void setTrump(Suit trump) {
        if(trump!=null)
            Card.trump = trump;
    }

    /**
     * Check if card is trump
     * @return True if card is trump and false if not
     */
    public boolean isTrump(){
        return suit==trump;
    }

    /**
     * toString method
     * @return String in format (suit_rank)
     */
    @Override
    public String toString() {
        return suit.toString().toLowerCase(Locale.ROOT) + "_" + rank.toString().toLowerCase(Locale.ROOT);
    }

    /**
     * Method to compare cards
     * @param o Card to compare
     * @return a>0 if this card is more than o, a<0 if this card is less than o, a=0 if they are equal.
     */
    @Override
    public int compareTo(Card o) {
        if(o == null)
            return -1000;
        if(trump !=null){
            if(this.getSuit() == trump && o.getSuit() != trump)
                return 100;
            if(this.getSuit() != trump && o.getSuit() == trump)
                return -100;
        }
        return (this.getSuit().compareTo(o.getSuit())==0)?
                (this.getRank().compareTo(o.getRank())):this.getSuit().compareTo(o.getSuit());
    }
}
