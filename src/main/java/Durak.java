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
        giveCardsFromDeck();
    }
    public boolean giveCardsFromDeck(){
        boolean res = true;
        for (Player player : players) {
            res = givePlayerCardsFromDeck(player);
        }
        return res;
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
    private int moveBot(Player player, int indexOfCardField){
        for(int i=0;i<player.getCards().size();++i){
            if(field.setList(player, i, indexOfCardField))
                return i;
        }
        return -1;
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
        game.players.get(1).setIsDefend(true);
        while(true) {
            if (window.getCardIndex() != -1 && window.getFieldIndex() != -1) {
                game.move(game.players.get(0), window.getCardIndex(), window.getFieldIndex());
                int card = game.moveBot(game.players.get(1), window.getFieldIndex());
                window.drawCard(game.players.get(1),1, card,  window.getFieldIndex());
                game.players.get(1).removeCard(card);
                window.cardsDeal(game.players, game.field);
                window.setFieldIndex(-1);
                window.setCardIndex(-1);
            }if(window.isPass()){
                window.drawDiscarded(game.field.clearField().size());
                window.clearField();
                game.giveCardsFromDeck();
                window.cardsDeal(game.players, game.field);
                window.setPass(false);
            }if(window.isTake()){

            }
        }

    }
}