import java.util.ArrayList;
import java.util.List;

public class Field {
    private static List<Card> attackList;
    private static List<Card> defendList;
    static{
        attackList = new ArrayList<>(6);
        defendList = new ArrayList<>(6);
    }

    public static void setAttackList(Card card) {
        for (Card c: attackList) {
            if(c == null) {
                attackList.add(card);
                return;
            }
        }
        System.out.println("so many attack cards");
    }

    public static void setDefendList(Card card, int i) {
        if(defendList.get(i) != null){
            System.out.println("card was beaten");
        }
        else if(attackList.get(i) == null){
            System.out.println("you can`t beat non-existent attack card");
        }
        defendList.set(i, card);
    }
}
