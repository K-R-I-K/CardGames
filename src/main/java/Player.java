import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Class which represents player in our card game
 */
@Getter
@Setter
public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 5429685098267757690L;
    private List<Card> cards;
    private String name;
    private boolean isDefend;
    private boolean isPassTake;

    /**
     * IsEmpty method for player card list
     * @return
     */
    public boolean isEmpty(){return cards.isEmpty();}

    /**
     * Equals method
     * @param o Object to compare
     * @return True if objects are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    /**
     * Hash code method
     * @return Hash code of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Constructor for Player
     * @param name Name of player
     */
    public Player(String name){
        this.name = name;
        this.cards = new ArrayList<>();
        this.isDefend = false;
        this.isPassTake = false;
    }

    /**
     * Setter for card
     * @param card Card to set
     */
    public void setCard(Card card){
        this.cards.add(card);
        this.cards.sort(Comparator.naturalOrder());
    }

    /**
     * Setter for cards
     * @param cards List of cards to set
     */
    public void setCard(List<Card> cards){
        this.cards.addAll(cards);
        this.cards.sort(Comparator.naturalOrder());
    }

    /**
     * Getter for card
     * @param index Index of card to get
     * @return Card in index position
     */
    public Card getCard(int index){
        if(index < 0 || index >= cards.size()){
            System.out.println("invalid argument");
            return null;
        }
        return cards.get(index);
    }

    /**
     * Remover of card
     * @param index Index of card to remove
     * @return Card which we remove
     */
    public Card removeCard(int index){
        if(index < 0 || index >= cards.size()){
            System.out.println("invalid argument");
            return null;
        }
        return cards.remove(index);
    }
}
