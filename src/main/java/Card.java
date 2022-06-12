import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Comparable<Card>{
    private Rank rank;
    private Suit suit;

    @Override
    public String toString() {
        return suit.toString().toLowerCase(Locale.ROOT) + "_" + rank.toString().toLowerCase(Locale.ROOT);
    }

    @Override
    public int compareTo(Card o) {
        return (this.getSuit().compareTo(o.getSuit())==0)?
                (this.getRank().compareTo(o.getRank())):this.getSuit().compareTo(o.getSuit());
    }
}
