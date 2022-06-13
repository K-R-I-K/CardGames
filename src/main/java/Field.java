import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Field {
    private List<Card> attackList;
    private List<Card> defendList;
    private int attackListSize;
    private final Suit trump;

    public Field(Suit trump){
        attackList = new ArrayList<>(Collections.nCopies(6, (Card) null));
        defendList = new ArrayList<>(Collections.nCopies(6, (Card) null));
        attackListSize = 0;
        this.trump = trump;
    }

    public boolean setList(Player player, int indexOfCard, int indexOfFieldEdge) {
        Card card = player.getCard(indexOfCard);
        if(indexOfFieldEdge <0 || indexOfFieldEdge > 5) {
            System.out.println("incorrect index");
            return false;
        }
        if(player.getIsDefend()){
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
                return true;
            }
            return false;
        }else{
            if(attackList.get(indexOfFieldEdge) != null){
                System.out.println("card was already there");
                return false;
            }
            if(moveCheck(player.getIsDefend(), card)) {
                attackList.set(attackListSize, card);
                ++attackListSize;
            }
            return moveCheck(player.getIsDefend(), card);
        }
    }

    public boolean moveCheck(boolean isDefend, Card card){
        if(isDefend){
            for(Card attackCard: attackList){
                if(defendCheck(attackCard, card))
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
        attackList = new ArrayList<>(Collections.nCopies(6, (Card) null));
        defendList = new ArrayList<>(Collections.nCopies(6, (Card) null));
        attackListSize=0;
        return res;
    }
}
