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
    private boolean isPlayerTurn;
    public boolean getIsDefend() {
        return isDefend;
    }

    public void setIsDefend(boolean defend) {
        isDefend = defend;
    }

    public boolean isEmpty(){return cards.size()==0;}


    public Player(String name){
        this.name = name;
        this.cards = new ArrayList<>();
        this.isDefend = false;
        this.isPlayerTurn =false;
    }

    public void setCard(Card card){
        this.cards.add(card);
        this.cards.sort(Comparator.naturalOrder());
    }

    public void setCard(List<Card> cards){
        this.cards.addAll(cards);
        this.cards.sort(Comparator.naturalOrder());
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
