import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player {
    private List<Card> cards;
    private String name;

    public Player(String name){
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public void setCard(Card card){
        this.cards.add(card);
    }

    public void setCard(List<Card> cards){
        this.cards.addAll(cards);
    }

    public Card getCard(int index){
        if(index < 0 || index >= cards.size()){
            System.out.println("invalid argument");
            return null;
        }
        return cards.get(index);
    }
    public Card removeCard(int index){
        if(index < 0 || index >= cards.size()){
            System.out.println("invalid argument");
            return null;
        }
        return cards.remove(index);
    }
}
