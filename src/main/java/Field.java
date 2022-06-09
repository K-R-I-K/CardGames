import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {
    private List<Card> attackList;
    private List<Card> defendList;
    private int attackListSize;
    private final Suit trump;
    public Field(Suit trump){
        attackList = new ArrayList<>(Arrays.asList(null, null, null, null, null, null));
        defendList = new ArrayList<>(Arrays.asList(null, null, null, null, null, null));
        attackListSize = 0;
        this.trump = trump;
    }

    public boolean setAttackList(Card card) {
        if(attackListSize >=6){
            System.out.println("so many attack cards");
            return false;
        }
        if(attackListSize == 0 || attackCheck(card)){
            attackList.add(card);
            ++attackListSize;
            return true;
        }
        return false;
    }

    public boolean setDefendList(Card card, int i) {
        if(i<0 || i > 5) {
            System.out.println("incorrect index");
            return false;
        }
        else if(defendList.get(i) != null){
            System.out.println("card was beaten");
            return false;
        }
        else if(attackList.get(i) == null){
            System.out.println("you can`t beat non-existent attack card");
            return false;
        }
        if(defendCheck(card, defendList.get(i))){
            defendList.set(i, card);
            return true;
        }
        return false;
    }

    private boolean attackCheck(Card card){
        //attackListSize must be > 0
        for (Card element: attackList) {
            if(element == null)
                continue;
            if(element.getRank() == card.getRank())
                return true;
        }
        return false;
    }

    private boolean defendCheck(Card attackCard, Card defendCard){
        if(attackCard == null || defendCard == null){
            System.out.println("invalid argument");
            return false;
        }
        return attackCard.getSuit().equals(defendCard.getSuit())
                && attackCard.getRank().compareTo(defendCard.getRank()) > 0
                || attackCard.getSuit().equals(trump)
                && !defendCard.getSuit().equals(trump);
    }

    public List<Card> clearField(){
        List<Card> res = new ArrayList<>();
        for(Card card:attackList)
            if(card!=null)
                res.add(card);
        for(Card card:defendList)
            if(card!=null)
                res.add(card);
        attackList = new ArrayList<>(Arrays.asList(null, null, null, null, null, null));
        defendList = new ArrayList<>(Arrays.asList(null, null, null, null, null, null));
        attackListSize=0;
        return res;
    }
}
