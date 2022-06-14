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
    public int giveCardsFromDeck(){
        int res = 0;
        for (Player player : players) {
            res += givePlayerCardsFromDeck(player);
        }
        return res;
    }
    private int givePlayerCardsFromDeck(Player player){
        int res = 0;
        if(deck.getSize()==0)
            return res;

        for(int i = player.getCards().size(); i < 6; ++i){
            player.setCard(deck.getCard());
            ++res;
            if(deck.getSize()==0)
                return res;
        }
        return res;
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
    public void gameVsBot(MyGraphics window){
        window.drawDeck(this.deck);
        window.cardsDeal(this.players, this.field);
        window.drawActionButton(this.players.get(0));
        this.players.get(1).setIsDefend(true);
        boolean isBotMoved = false;
        while(true) {
            if (this.deck.isEmpty()) {//win case
                for (Player pl : this.players) {
                    if (pl.isEmpty()) {
                        System.out.println(pl.getName() + " is win!");
                        return;
                    }
                }
            }
            if (this.players.get(1).getIsDefend()) {//player attack case
                window.setAttack(true);
                if (window.getCardIndex() != -1 && window.getFieldIndex() != -1) {
                    this.move(this.players.get(0), window.getCardIndex(), window.getFieldIndex());
                    int card = this.moveBot(this.players.get(1), window.getFieldIndex());
                    if (card == -1) {
                        this.players.get(1).setCard(this.field.clearField());
                        window.getCardsFromDeck(this.giveCardsFromDeck());
                        window.clearField();
                        this.players.get(1).setIsDefend(true);//the same
                        isBotMoved = false;
                    } else {
                        window.drawCard(this.players.get(1), 1, card, window.getFieldIndex());
                        this.players.get(1).removeCard(card);
                    }

                    window.cardsDeal(this.players, this.field);
                    window.setFieldIndex(-1);
                    window.setCardIndex(-1);
                }
                if (window.isPass()) {
                    if (this.field.getAttackListSize() != 0) {
                        window.drawDiscarded(this.field.clearField().size());
                        window.clearField();
                        window.getCardsFromDeck(this.giveCardsFromDeck());
                        window.cardsDeal(this.players, this.field);
                        this.players.get(1).setIsDefend(false);
                        this.players.get(0).setIsDefend(true);
                        isBotMoved = false;
                    }
                    window.setPass(false);
                }
            } else {
                window.setAttack(false);
                if (!isBotMoved) {
                    int card = this.moveBot(this.players.get(1), window.getFieldIndex() + 1);
                    if (card != -1) {
                        window.drawCard(this.players.get(1), 1, card, window.getFieldIndex() + 1);
                        this.players.get(1).removeCard(card);
                    }
                    isBotMoved = true;
                }

                if (window.getCardIndex() != -1 && window.getFieldIndex() != -1) {
                    this.move(this.players.get(0), window.getCardIndex(), window.getFieldIndex());
                    int card = this.moveBot(this.players.get(1), window.getFieldIndex() + 1);
                    if (card == -1) {
                        window.drawDiscarded(this.field.clearField().size());
                        window.clearField();
                        window.getCardsFromDeck(this.giveCardsFromDeck());
                        this.players.get(1).setIsDefend(true);
                        this.players.get(0).setIsDefend(false);
                        window.setTake(false);
                        isBotMoved = false;
                    } else {
                        window.drawCard(this.players.get(1), 1, card, window.getFieldIndex() + 1);
                        this.players.get(1).removeCard(card);
                    }

                    window.cardsDeal(this.players, this.field);
                    window.setFieldIndex(-1);
                    window.setCardIndex(-1);
                }
                if (window.isTake()) {
                    this.players.get(0).setCard(this.field.clearField());
                    window.getCardsFromDeck(this.giveCardsFromDeck());
                    window.cardsDeal(this.players, this.field);
                    window.clearField();
                    this.players.get(0).setIsDefend(true);//the same
                    window.setTake(false);
                    isBotMoved = false;
                }
            }
        }
    }

    public static void main(String[] args) {
        Durak game = new Durak();
        MyGraphics window = new MyGraphics();
        game.gameVsBot(window);


    }
}