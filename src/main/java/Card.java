import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
public class Card implements Comparable<Card>, Serializable {
    private Rank rank;
    private Suit suit;
    private static Suit trump;//field for client and sorting card list
    Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
    }

    public static Suit getTrump() {
        return trump;
    }

    public static void setTrump(Suit trump) {
        if(trump!=null)
            Card.trump = trump;
    }
    public boolean isTrump(){
        return suit==trump;
    }
    @Override
    public String toString() {
        return suit.toString().toLowerCase(Locale.ROOT) + "_" + rank.toString().toLowerCase(Locale.ROOT);
    }

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
