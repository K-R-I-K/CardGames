import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Durak {
    private List<Player> players;
    private Deck deck;
    private Field field;
    public Durak(){
        players = new ArrayList<>();
        deck = new Deck();
        field = new Field(deck.getTrump().getSuit());
        addPlayers();
        //startGame();
    }

    private void addPlayers(){
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        if(players.size() > 4){
            System.out.println("So many players");
            return;
        }
        for (Player player : players) {
            givePlayerCardsFromDeck(player);
        }
    }
    private boolean givePlayerCardsFromDeck(Player player){
        if(deck.getSize()==0)
            return false;
        for(int i = player.getCards().size(); i < 6; ++i){
            player.setCard(deck.getCard());
            if(deck.getSize()==0)
                return false;
        }
        return true;
    }

    private boolean attackMove(Player player, int indexOfCardPlayer, int indexOfCardField){
        boolean res = field.setAttackList(player.getCard(indexOfCardPlayer), indexOfCardField);
        if(res)
            player.removeCard(indexOfCardPlayer);
        return res;
    }
    private boolean defenseMove(Player player, int indexOfCardPlayer, int indexOfCardField){
        boolean res = field.setDefendList(player.getCard(indexOfCardPlayer), indexOfCardField);
        if(res)
            player.removeCard(indexOfCardPlayer);
        return res;
    }
    public void startGame(){
        int indexOfPlayer = 0;
        Scanner scanner = new Scanner(System.in);
        int indexOfCardPlayer = scanner.nextInt();
        attackMove(players.get(indexOfPlayer), indexOfCardPlayer, 0);
        for(int i=0;i<players.get(indexOfPlayer+1).getCards().size();++i){
            if(defenseMove(players.get(indexOfPlayer+1), i, 0))
                break;
        }

        players.get(1).setCard(field.clearField());//the player takes the cards
        field.clearField();//
    }

    public static void main(String[] args) {
        Durak game = new Durak();
        MyGraphics window = new MyGraphics();
        window.drawDeck(game.deck);
        window.cardsDeal(game.players, game.field);
        while(true) {
            if (window.getCardIndex() != -1 && window.getFieldIndex() != -1) {
                game.field.setAttackList(game.players.get(0).getCard(window.getCardIndex()), window.getFieldIndex());
                window.setFieldIndex(-1);
                window.setCardIndex(-1);
                window.drawAttackCard(game.players.get(1), 1, 1,  1);
            }
        }
        //window.drawDiscarded(10);
    }
}