import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Durak implements Serializable {
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
    private int giveCardsFromDeck(){
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
    public void startGame(MyGraphics window){
        window.drawDeck(this.deck);
        window.cardsDeal(this.players, this.field);
        window.drawActionButton(this.players.get(0));
        this.players.get(1).setIsDefend(true);
        this.players.get(0).setPlayerTurn(true);
        int player = -1;
        Card buf = null;
        List<Player> playerList = this.players;
        for (int i = 0; i < playerList.size(); i++) {
            Player pl = playerList.get(i);
            for (Card card : pl.getCards()) {
               if(card.isTrump()) {
                    player = (card.compareTo(buf) > 0) ?i:player;
                    buf = (card.compareTo(buf) > 0) ?buf:card;
                    break;
                }
            }
        }
        if(player!=-1){
            this.players.get(1).setIsDefend(false);
            this.players.get(0).setPlayerTurn(false);
            this.players.get(player).setPlayerTurn(true);
            this.players.get(1-player).setIsDefend(true);
        }
    }
    private int isGameOver(){
        if (this.deck.isEmpty()) {//win case
            List<Player> playerList = this.players;
            for (int i = 0; i < playerList.size(); i++) {
                Player pl = playerList.get(i);
                if (pl.isEmpty()) {
                    return i;
                }
            }
        }
        return -1;
    }
    public void gameVsBot(MyGraphics window){
        startGame(window);
        boolean isBotMoved = false;
        boolean isOver=false;
        while(!isOver) {
            if(isGameOver()>0){
                window.drawResult(players.get(isGameOver()).getName() + " has won!");
                isOver = true;
            }
            if (this.players.get(1).getIsDefend()) {//player attack case
                window.setAttack(true);
                if (window.getCardIndex() != -1 && window.getFieldIndex() != -1) {
                    this.move(this.players.get(0), window.getCardIndex(), window.getFieldIndex());

                    //bot
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
                    ////
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
                    //bot
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
                    ////
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
    public void gameVsPlayer(MyGraphics window, Server server){
        startGame(window);
        boolean isOver=false;
        while(!isOver) {
            if(isGameOver()>0){
                window.drawResult(players.get(isGameOver()).getName() + " has won!");
                isOver = true;
            }
            if (this.players.get(1-User.getUserId()).getIsDefend()) {//player attack case
                window.setAttack(true);
                if(this.players.get(User.getUserId()).isPlayerTurn()){
                    if (window.getCardIndex() != -1 && window.getFieldIndex() != -1) {
                        this.move(this.players.get(User.getUserId()), window.getCardIndex(), window.getFieldIndex());

                        byte[] data = new byte[0];
                        data = SerializationUtils.serialize(this);
                        try {
                            server.getDos().write(data);
                            server.getDos().flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        players.get(User.getUserId()).setPlayerTurn(false);
                        players.get(1-User.getUserId()).setPlayerTurn(true);
                        ////
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
                            this.players.get(1-User.getUserId()).setIsDefend(false);
                            this.players.get(User.getUserId()).setIsDefend(true);
                            players.get(User.getUserId()).setPlayerTurn(false);
                            players.get(1-User.getUserId()).setPlayerTurn(true);
                        }
                        window.setPass(false);
                    }
                }else {
                    byte[] data = new byte[0];
                    try {
                        if(server.getDis().read(data)>0){
                            Durak buf = SerializationUtils.deserialize(data);
                            this.players = buf.players;
                            this.field = buf.field;
                            this.deck = buf.deck;
                            players.get(User.getUserId()).setPlayerTurn(true);
                            players.get(1-User.getUserId()).setPlayerTurn(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {    //player defend case
                window.setAttack(false);

                if(this.players.get(User.getUserId()).isPlayerTurn()) {
                    if (window.getCardIndex() != -1 && window.getFieldIndex() != -1) {
                        this.move(this.players.get(User.getUserId()), window.getCardIndex(), window.getFieldIndex());
                        //bot
                        byte[] data = new byte[0];
                        try {
                            if(server.getDis().read(data)>0){
                                Durak buf = SerializationUtils.deserialize(data);
                                this.players = buf.players;
                                this.field = buf.field;
                                this.deck = buf.deck;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ////
                        window.cardsDeal(this.players, this.field);
                        window.setFieldIndex(-1);
                        window.setCardIndex(-1);
                    }
                    if (window.isTake()) {
                        this.players.get(User.getUserId()).setCard(this.field.clearField());
                        window.getCardsFromDeck(this.giveCardsFromDeck());
                        window.cardsDeal(this.players, this.field);
                        window.clearField();
                        this.players.get(User.getUserId()).setIsDefend(true);//the same
                        window.setTake(false);
                        players.get(User.getUserId()).setPlayerTurn(false);
                        players.get(1-User.getUserId()).setPlayerTurn(true);
                    }
                }else {
                    byte[] data = new byte[0];
                    try {
                        if(server.getDis().read(data)>0){
                            Durak buf = SerializationUtils.deserialize(data);
                            this.players = buf.players;
                            this.field = buf.field;
                            this.deck = buf.deck;
                            players.get(User.getUserId()).setPlayerTurn(true);
                            players.get(1-User.getUserId()).setPlayerTurn(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}