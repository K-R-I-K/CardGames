import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class Player {
    private List<Card> cards;
    private String name;
    private boolean isDefend;

    public boolean getIsDefend() {
        return isDefend;
    }

    public void setIsDefend(boolean defend) {
        isDefend = defend;
    }



    public Player(String name){
        this.name = name;
        this.cards = new ArrayList<>();
        this.isDefend = false;
    }

    public void setCard(Card card){
        for (int i = 0; i < cards.size(); i++) {
            Card el = cards.get(i);
            if (el == null) {
                this.cards.add(i, card);
                this.cards.sort(Comparator.naturalOrder());
                return;
            }
        }
        this.cards.add(card);
        this.cards.sort(Comparator.naturalOrder());
    }

    public void setCard(List<Card> cards){
        for(Card el:cards){
            setCard(el);
        }
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
        Card res = cards.remove(index);
        cards.add(index, null);
        return res;
    }
}
