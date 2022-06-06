import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Rank rank;
    private Suit suit;

    @Override
    public String toString() {
        return suit.toString().toLowerCase(Locale.ROOT) + "_" + rank.toString().toLowerCase(Locale.ROOT);
    }
}
