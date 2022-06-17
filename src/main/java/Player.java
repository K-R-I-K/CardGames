import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

@Getter
@Setter
public class Player implements Serializable {
    private List<Card> cards;
    private String name;
    private boolean isDefend;
    private boolean isPassTake;
    public boolean getIsDefend() {
        return isDefend;
    }

    public void setIsDefend(boolean defend) {
        isDefend = defend;
    }

    public boolean isEmpty(){return cards.size()==0;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Player(String name){
        this.name = name;
        this.cards = new ArrayList<>();
        this.isDefend = false;
        this.isPassTake =false;
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
