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

    private boolean move(Player player, int indexOfCardPlayer, int indexOfCardField){
        boolean res = field.setList(player, indexOfCardPlayer, indexOfCardField);
        if(res)
            player.removeCard(indexOfCardPlayer);
        return res;
    }
    private void moveBot(Player player, int indexOfCardField){
        for(int i=0;i<player.getCards().size();++i){
            if(move(player, i, indexOfCardField))
                break;
        }
    }
    public void startGame(MyGraphics window){

        window.drawDeck(this.deck);
        window.cardsDeal(this.players, this.field);

        int indexOfPlayer = 0;
        players.get(indexOfPlayer).setIsDefend(true);
        //move(players.get(indexOfPlayer), indexOfCardPlayer, 0);
        while (true){
            if (window.getCardIndex() != -1 && window.getFieldIndex() != -1) {
                this.field.setList(this.players.get(indexOfPlayer), window.getCardIndex(), window.getFieldIndex());
                moveBot(this.players.get(indexOfPlayer), window.getFieldIndex());
                window.setFieldIndex(-1);
                window.setCardIndex(-1);

                //window.drawAttackCard(this.players.get(1), 1, 1,  1);
            }
            //field.clearField();//
        }


    }

    public static void main(String[] args) {
        Durak game = new Durak();

        MyGraphics window = new MyGraphics();
        //game.startGame(window);
        window.drawDeck(game.deck);
        window.cardsDeal(game.players, game.field);
        window.drawActionButton(game.players.get(0));
        //window.getCardsFromDeck(24);
        while(true) {
            if (window.getCardIndex() != -1 && window.getFieldIndex() != -1) {
                game.field.setList(game.players.get(0), window.getCardIndex(), window.getFieldIndex());
                window.setFieldIndex(-1);
                window.setCardIndex(-1);
                window.drawCard(game.players.get(1), 1, 1,  1);
            }
        }
        //window.drawDiscarded(10);
    }
}