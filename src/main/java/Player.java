import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Player {
    private List<Card> cards;
    private String name;

    public Player(String name){
        this.name = name;
    }

    public void setCard(Card card){
        this.cards.add(card);
    }

    public void setCard(List<Card> cards){
        this.cards.addAll(cards);
    }

    public void attackTurn(Card card){
        Field.setAttackList(card);
    }
}
