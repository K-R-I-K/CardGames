import java.util.ArrayList;
import java.util.List;

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
        for(int i = 0; i < 6; ++i){
            for (Player player : players) {
                player.setCard(deck.getCard());
            }
        }
    }
    private void attackMove(Player player, int indexOfCardPlayer){
        if(field.setAttackList(player.getCard(indexOfCardPlayer)))
            player.removeCard(indexOfCardPlayer);
    }
    private void defenseMove(Player player, int indexOfCardPlayer, int indexOfCardField){
        if(field.setDefendList(player.getCard(indexOfCardPlayer), indexOfCardField))
            player.removeCard(indexOfCardPlayer);
    }
    public void startGame(){
        int indexOfPlayer = 0;
        int indexOfCardPlayer = 1;
        attackMove(players.get(indexOfPlayer), indexOfCardPlayer);
        defenseMove(players.get(++indexOfPlayer), indexOfCardPlayer,0);
        players.get(1).setCard(field.clearField());//the player takes the cards
        field.clearField();//
    }

    public static void main(String[] args) {
        Durak game = new Durak();
        MyGraphics window = new MyGraphics(game.players);
    }
}