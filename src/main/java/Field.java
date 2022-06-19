import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Field implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529575098267757690L;
    @Getter
    private List<Card> attackList;
    @Getter
    private List<Card> defendList;
    @Getter
    private int attackListSize;
    @Getter
    private int defendListSize;
    private final Suit trump;

    public Field(Suit trump){
        attackList = new ArrayList<>(Collections.nCopies(6, null));
        defendList = new ArrayList<>(Collections.nCopies(6, null));
        attackListSize = 0;
        defendListSize = 0;
        this.trump = trump;
    }
    public boolean isEmpty(){
        for(Card element:attackList){
            if(element!=null)
                return false;
        }
        return true;
    }
    public boolean setList(Player player, int indexOfCard, int indexOfFieldEdge) {
        Card card = player.getCard(indexOfCard);
        if(indexOfFieldEdge <0 || indexOfFieldEdge > 5) {
            System.out.println("incorrect index");
            return false;
        }
        if(player.isDefend()){
            if(defendList.get(indexOfFieldEdge) != null){
                System.out.println("card was beaten");
                return false;
            }
            else if(attackList.get(indexOfFieldEdge) == null){
                System.out.println("you can`t beat non-existent attack card");
                return false;
            }
            if(defendCheck(attackList.get(indexOfFieldEdge),card)){
                defendList.set(indexOfFieldEdge, card);
                ++defendListSize;
                return true;
            }
            return false;
        }else{
            if(attackList.get(indexOfFieldEdge) != null){
                System.out.println("card was already there");
                return false;
            }
            if(moveCheck(player.isDefend(), card)) {
                attackList.set(indexOfFieldEdge, card);
                ++attackListSize;
            }
            return moveCheck(player.isDefend(), card);
        }
    }
    public boolean moveCheck(boolean isDefend, Card card){
        if(isDefend){
            for (int i = 0; i < attackList.size(); i++) {
                Card attackCard = attackList.get(i);
                Card defendCard = defendList.get(i);
                if (attackCard != null && defendCard == null && defendCheck(attackCard, card))
                    return true;
            }
        }else {
            if(attackListSize >=6){
                System.out.println("so many attack cards");
                return false;
            }
            if(attackListSize == 0){
                return true;
            }
            for (Card element: attackList) {
                if(element == null)
                    continue;
                if(element.getRank().equals(card.getRank()))
                    return true;
            }
            for (Card element: defendList) {
                if(element == null)
                    continue;
                if(element.getRank().equals(card.getRank()))
                    return true;
            }
        }
        return false;
    }

    private boolean defendCheck(Card attackCard, Card defendCard){
        if(attackCard == null || defendCard == null){
            System.out.println("invalid argument");
            return false;
        }
        return attackCard.getSuit().equals(defendCard.getSuit())
                && attackCard.getRank().compareTo(defendCard.getRank()) < 0
                || !attackCard.getSuit().equals(trump)
                && defendCard.getSuit().equals(trump);
    }

    public List<Card> clearField(){
        List<Card> res = new ArrayList<>();
        for(Card card:attackList)
            if(card!=null)
                res.add(card);
        for(Card card:defendList)
            if(card!=null)
                res.add(card);
        attackList = new ArrayList<>(Collections.nCopies(6, null));
        defendList = new ArrayList<>(Collections.nCopies(6, null));
        attackListSize = 0;
        defendListSize = 0;
        return res;
    }
}
