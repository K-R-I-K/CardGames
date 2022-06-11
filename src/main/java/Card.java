import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Comparable{
    private Rank rank;
    private Suit suit;

    @Override
    public String toString() {
        return suit.toString().toLowerCase(Locale.ROOT) + "_" + rank.toString().toLowerCase(Locale.ROOT);
    }

    @Override
    public int compareTo(Object o) {
        Card o1 = (Card) o;
        return (this.getSuit().compareTo(o1.getSuit())==0)?
                (this.getRank().compareTo(o1.getRank())):this.getSuit().compareTo(o1.getSuit());
    }
}
