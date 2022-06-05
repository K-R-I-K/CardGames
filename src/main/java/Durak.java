import java.util.ArrayList;
import java.util.List;

public class Durak {
    List<Player> players = new ArrayList<>();

    public Durak(){
        addPlayers();
        game();
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
                player.setCard(Deck.getCard());
            }
        }
    }

    public void game(){
        players.get(0).attackTurn(players.get(0).getCards().get(0));
    }
}
