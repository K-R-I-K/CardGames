import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Graphics class for our card game
 */
public class MyGraphics extends JLayeredPane{
    /**
     * Current game frame
     */
    public static JFrame frame;
    private String pathFronts;
    private String pathBacks;
    private String backName;
    private int cardWidth;
    private int cardHeight;
    private int panelWidth;
    private int panelHeight;
    private int buttonWidth;
    private int buttonHeight;
    private List<JLayeredPane> playersPanelList;
    private List<JLayeredPane> battlePanels;
    private JLayeredPane deckPanel;
    private JLayeredPane discardedPanel;
    private JLayeredPane exitPanel;
    private JLayeredPane buttonPanel;
    private JLayeredPane resultPanel;
    private JButton actionButton;
    private List<List<JLabel>> labelsLists;
    private List<Boolean> playerChoose;
    private List<MouseListener> playerMouseListeners;
    private List<JLabel> deckLabels;
    private int discardedCount;
    @Getter
    @Setter
    private volatile int cardIndex;
    @Getter
    @Setter
    private volatile int fieldIndex;
    @Getter
    @Setter
    private volatile boolean isTake;
    @Getter
    @Setter
    private volatile boolean isPass;
    private int offsetY1 = 20;
    private int offsetY2 = 80;
    private JLabel info;

    /**
     * Setter for isAttack variable, which mean if now player is attacker
     * @param attack Boolean variable to set
     */
    public void setAttack(boolean attack) {
        isAttack = attack;
        if(isAttack)
            actionButton.setText("<html><h3><font color=\"blue\">Pass");
        else
            actionButton.setText("<html><h3><font color=\"blue\">Take");
    }

    private boolean isAttack;
    @Getter
    @Setter
    private List<Boolean> isCardOnField;

    /**
     * Constructor
     */
    MyGraphics(){
        isAttack = false;
        pathFronts = "src/main/resources/png/fronts/";
        pathBacks = "src/main/resources/png/backs/";
        backName = "blue2";
        cardWidth = 234 * 6 / 10;
        cardHeight = 333 * 6 / 10;
        buttonWidth = 100;
        buttonHeight = 50;
        panelWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        panelHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        discardedCount = 0;
        cardIndex = -1;
        fieldIndex = -1;
        isTake = false;
        isPass = false;
        setLayout();
        drawExitButton();
        this.setBounds(0, 0, panelWidth, panelHeight);
        this.setBackground(new Color(204, 204, 204));
        info = new JLabel();
        info.setBounds(20, panelHeight * 3 / 18, panelWidth * 9 / 32, panelHeight * 5 / 18);
        this.setLayer(info,100);

        this.add(info);
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(this);
        //this.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        frame.setBounds(0, 0, panelWidth, panelHeight);
        frame.setVisible(true);
    }

    /**
     * Draw method for field
     * @param field Field to draw
     */
    public void redrawField(Field field){
        clearField();
        for(int i = 0; i < field.getAttackList().size(); ++i){
            if(field.getAttackList().get(i) != null){
                battleCard(true, field.getAttackList().get(i), i);
            }
        }
        for(int i = 0; i < field.getDefendList().size(); ++i){
            if(field.getDefendList().get(i) != null){
                battleCard(false, field.getDefendList().get(i), i);
            }
        }
    }

    private void battleCard(boolean isAttack, Card card, int battleFieldIndex){
        int y = isAttack?offsetY1:offsetY2;
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(pathFronts + card.toString() + ".png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        label.setBounds((battlePanels.get(battleFieldIndex).getWidth() - cardWidth) / 2, y, cardWidth, cardHeight);
        if(y == offsetY1) {
            battlePanels.get(battleFieldIndex).add(label, 0);
            battlePanels.get(battleFieldIndex).setLayer(label, 0);
        }else {
            battlePanels.get(battleFieldIndex).add(label, 1);
            battlePanels.get(battleFieldIndex).setLayer(label, 1);
        }
    }

    public void cardsDeal(List<Player> players, Field field){
        labelsLists = new ArrayList<>();
        playerChoose = new ArrayList<>(Collections.nCopies(players.get(0).getCards().size(), false));
        playerMouseListeners = new ArrayList<>();
        labelsLists.add(new ArrayList<>());
        playersPanelList.get(0).removeAll();
        playersPanelList.get(0).repaint();
        cardsGraphic(players, labelsLists, 0, field);
        labelsLists.add(new ArrayList<>());
        playersPanelList.get(1).removeAll();
        playersPanelList.get(1).repaint();
        cardsGraphic(players, labelsLists, 1, field);

    }

    private void cardsGraphic(List<Player> players, List<List<JLabel>> labelsList, int numberOfPlayer, Field field) {
        int currentPosition;
        int stepLength;
        playersPanelList.get(numberOfPlayer).setLayout(null);
        if (players.get(numberOfPlayer).getCards().size() < 9) {
            stepLength = cardWidth + 10;
            currentPosition = (playersPanelList.get(numberOfPlayer).getWidth() - (players.get(numberOfPlayer).getCards().size() * stepLength)) / 2;
        } else {
            currentPosition = 10;
            stepLength = (playersPanelList.get(numberOfPlayer).getWidth() - 20 - cardWidth) / (players.get(numberOfPlayer).getCards().size() - 1);
        }

        int y = (numberOfPlayer == 0) ? 40 : 10;

        for (int i = 0; i < players.get(numberOfPlayer).getCards().size(); ++i) {
            JLabel label = new JLabel(new ImageIcon(new ImageIcon(getPath(numberOfPlayer == 0, players.get(numberOfPlayer).getCards().get(i)))
                    .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
            label.setBounds(currentPosition, y, cardWidth, cardHeight);
            labelsList.get(numberOfPlayer).add(label);
            playersPanelList.get(numberOfPlayer).add(label);
            playersPanelList.get(numberOfPlayer).setLayer(label, i);
            if(numberOfPlayer == 0) {
                playerMouseListeners.add(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {}

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(!players.get(0).isPassTake() && field.moveCheck(players.get(0).isDefend(),players.get(0).getCard(getLayer(label)))) {
                            mousePress(getLayer(label), field, players.get(0));
                            cardIndex = getLayer(label);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if(!players.get(0).isPassTake() && !playerChoose.get(getLayer(label)) &&
                                field.moveCheck(players.get(0).isDefend(), players.get(0).getCard(getLayer(label))))
                            label.setLocation(label.getX(), label.getY() - 40);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if(!players.get(0).isPassTake() && !playerChoose.get(getLayer(label))&&
                                field.moveCheck(players.get(0).isDefend(), players.get(0).getCard(getLayer(label))))
                            label.setLocation(label.getX(), label.getY() + 40);
                    }
                });
                label.addMouseListener(playerMouseListeners.get(i));
            }
            currentPosition += stepLength;
        }
    }

    private void mousePress(int index, Field field, Player player){
        for (int i = 0; i < playerChoose.size(); ++i) {
            if(playerChoose.get(i) && i != index){
                playerChoose.set(i, false);
                if(field.moveCheck(player.isDefend(),player.getCard(i)))
                    labelsLists.get(0).get(i).setLocation(labelsLists.get(0).get(i).getX(), labelsLists.get(0).get(i).getY() + 40);
            }
        }
        playerChoose.set(index, true);
    }

    private String getPath(boolean isPlayer, Card card){
        return isPlayer ? pathFronts + card.toString() + ".png" : pathBacks + backName + ".png";
    }

    private JLayeredPane createPanel(int x, int y, int w, int h, int battleFieldIndex){
        JLayeredPane panel = new JLayeredPane();
        //panel.setBackground(Color.red);
        panel.setBounds(x, y, w, h);
        if(battleFieldIndex != -1)
            panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                fieldIndex = battleFieldIndex;
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        panel.setVisible(true);
        panel.setOpaque(true);
        return panel;
    }
   /* private void drawCardOnField(JLayeredPane panel){
        for (int i = 0; i < playerChoose.size(); ++i) {
            if(playerChoose.get(i) && !isCardOnField.get(battlePanels.indexOf(panel))){
                isCardOnField.set(battlePanels.indexOf(panel), true);
                JLabel label= labelsLists.get(0).get(i);
                label.setLocation((panel.getWidth() - cardWidth) / 2, 0);
                label.removeMouseListener(playerMouseListeners.get(i));
                //panel.removeMouseListener(this);
                playersPanelList.get(0).setLayer(label, 0);
                playerChoose.set(i, false);
                int y = (isAttack)?offsetY1:offsetY2;
                label.setLocation(labelsLists.get(0).get(i).getX(), labelsLists.get(0).get(i).getY() + y);
                panel.setLayer(label, isAttack?0:1);
                panel.add(label);

            }
        }
    }*/
    public void setLayout(){
        this.setLayout(null);
        playersPanelList = new ArrayList<>();

        playersPanelList.add(createPanel(panelWidth * 5 / 32, panelHeight * 14 / 18, panelWidth * 22 / 32, panelHeight * 4 / 18, -1));
        playersPanelList.add(createPanel(panelWidth * 5 / 32, 0, panelWidth * 22 / 32, panelHeight * 4 / 18, -1));
        playersPanelList.add(createPanel(0, panelHeight * 4 / 18, panelWidth * 5 / 32, panelHeight * 10 / 18, -1));
        playersPanelList.add(createPanel(panelWidth * 27 / 32, panelWidth * 4 / 32, panelWidth * 5 / 32, panelHeight * 10 / 18, -1));

        deckPanel = createPanel(0,0,panelWidth * 5 / 32,panelHeight * 4 / 18, -1);
        discardedPanel = createPanel(panelWidth * 27 / 32,0,panelWidth * 5 / 32,panelHeight * 4 / 18, -1);
        exitPanel = createPanel(0,panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight * 4 / 18, -1);
        buttonPanel = createPanel(panelWidth * 27 / 32, panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight * 4 / 18, -1);

        isCardOnField = new ArrayList<>(Collections.nCopies(6, false));
        battlePanels = new ArrayList<>();
        Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
        battlePanels.add(createPanel(panelWidth * 27 / 32 / 3, panelHeight * 4 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 0));
        battlePanels.add(createPanel(panelWidth * 41 / 32 / 3, panelHeight * 4 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 1));
        battlePanels.add(createPanel(panelWidth * 55 / 32 / 3, panelHeight * 4 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 2));
        battlePanels.add(createPanel(panelWidth * 27 / 32 / 3, panelHeight * 9 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 3));
        battlePanels.add(createPanel(panelWidth * 41 / 32 / 3, panelHeight * 9 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 4));
        battlePanels.add(createPanel(panelWidth * 55 / 32 / 3, panelHeight * 9 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 5));
        for (JLayeredPane panel : battlePanels)
            panel.setBorder(border);
        resultPanel = createPanel(0, 0, panelWidth, panelHeight, -1);

        this.add(deckPanel);
        this.add(playersPanelList.get(1));
        this.add(discardedPanel);
        this.add(playersPanelList.get(2));
        this.add(createPanel(panelWidth * 5 / 32, panelHeight * 4 / 18, panelWidth * 4 / 32, panelHeight * 5 / 9, -1));
        this.add(createPanel(panelWidth * 23 / 32, panelHeight * 4 / 18, panelWidth * 4 / 32, panelHeight * 5 / 9, -1));
        this.add(playersPanelList.get(3));
        this.add(exitPanel);
        this.add(playersPanelList.get(0));
        this.add(buttonPanel);
        for (JLayeredPane battlePanel : battlePanels) {
            this.add(battlePanel);
        }
        this.revalidate();
    }

    public void drawDeck(Deck deck){
        int upDeck = 50;
        deckPanel.removeAll();
        deckPanel.repaint();
        deckLabels = new ArrayList<>();
        deckPanel.setLayout(null);
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(pathFronts +  deck.getTrump().toString() + ".png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        label.setBounds((deckPanel.getWidth() - cardWidth) / 2, (deckPanel.getHeight() - cardHeight) / 2 - upDeck, cardWidth, cardHeight);
        deckLabels.add(label);
        deckPanel.add(label);
        deckPanel.setLayer(label, 0);
        for(int i = 0; i < deck.getSize() - 1; ++i) {
            label = new JLabel(new ImageIcon(new ImageIcon(pathBacks +  backName + "_h.png")
                    .getImage().getScaledInstance(cardHeight, cardWidth, Image.SCALE_SMOOTH)));
            label.setBounds((deckPanel.getWidth() - cardHeight) / 2, (deckPanel.getHeight() - cardHeight) / 2 +  - i * 3/ 2 - upDeck, cardHeight, cardWidth);
            deckLabels.add(label);
            deckPanel.add(label);
            deckPanel.setLayer(label, i + 1);
        }
    }

    public void drawDiscarded(int number) {
        int upDiscarded = 25;
        for (int i = discardedCount; i < discardedCount + number; ++i) {
            JLabel label = new JLabel(new ImageIcon(new ImageIcon(pathBacks + backName + "_h.png")
                    .getImage().getScaledInstance(cardHeight, cardWidth, Image.SCALE_SMOOTH)));
            label.setBounds((discardedPanel.getWidth() - cardHeight) / 2, (discardedPanel.getHeight() - cardHeight) / 2 - i * 3 / 2 - upDiscarded, cardHeight, cardWidth);
            discardedPanel.add(label);
            discardedPanel.setLayer(label, i);
        }
        discardedCount += number;
    }

    public void drawCard(Player player, int PlayerIndex, int cardIndex, int battleFieldIndex){
        int y = player.isDefend()?offsetY2:offsetY1;
        JLabel label = labelsLists.get(PlayerIndex).get(cardIndex);
        label.setIcon(new ImageIcon(new ImageIcon(pathFronts + player.getCard(cardIndex).toString() + ".png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        label.setLocation((battlePanels.get(battleFieldIndex).getWidth() - cardWidth) / 2, y);
        if(y == offsetY1) {
            battlePanels.get(battleFieldIndex).add(label, 0);
            battlePanels.get(battleFieldIndex).setLayer(label, 0);
        }else {
            battlePanels.get(battleFieldIndex).add(label, 1);
            battlePanels.get(battleFieldIndex).setLayer(label, 1);
        }
    }

    private void drawExitButton(){
        JButton exitButton = createButton("Close", "blue", (exitPanel.getWidth() - buttonWidth) / 2,
        (exitPanel.getHeight() - buttonHeight) / 2, buttonWidth, buttonHeight, 0, 255, 255);
        exitButton.addActionListener(e -> System.exit(0));
        exitPanel.add(exitButton);
    }

    public void drawActionButton(){
        String name = isAttack?"Pass":"Take";
        actionButton = createButton(name, "blue", (buttonPanel.getWidth() - buttonWidth) / 2,
                (buttonPanel.getHeight() - buttonHeight) / 2, buttonWidth, buttonHeight, 0, 255, 255);
        actionButton.addActionListener(e -> {
            if(isAttack){
                actionButton.setText("<html><h3><font color=\"blue\">Pass");
                isPass = true;

            }else{
                actionButton.setText("<html><h3><font color=\"blue\">Take");
                isTake = true;
            }
            buttonPanel.repaint();
        });
        buttonPanel.add(actionButton);
    }

    private JButton createButton(String name, String textColor, int x, int y, int w, int h, int r, int g, int b){
        JButton button = new JButton("<html><h3><font color=\"" + textColor +"\">" + name);
        button.setBounds(x, y, w, h);
        button.setBorderPainted(false);
        button.setBackground(new Color(r, g, b));
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Method to redraw deck
     * @param number Number of cards which we pop from deck
     */
    public void getCardsFromDeck(int number){
        for(int i = 0; i < number; ++i){
            deckPanel.remove(deckLabels.get(deckLabels.size() - 1));
            deckLabels.remove(deckLabels.size() - 1);
            deckPanel.repaint();
        }
    }

    /**
     * Method to clear field
     */
    public void clearField(){
        for(int i = 0; i < battlePanels.size(); ++i){
            battlePanels.get(i).removeAll();
            battlePanels.get(i).repaint();
            isCardOnField.set(i, false);
        }
    }

    /**
     * Method to draw results of game
     * @param text Text of result
     */
    public void drawResult(String text){
        this.removeAll();
        resultPanel.setLayout(null);
        int x = text.length() * 18;
        JLabel label = new JLabel("<html><h3><font color=\"blue\"><font size = 24>" + text);

        label.setBounds((panelWidth - x) / 2, (panelHeight - 100) / 2 - buttonHeight, 2*x, 50);
        resultPanel.add(label);
        JButton button = createButton("Close", "blue", (resultPanel.getWidth() - buttonWidth) / 2,
                (resultPanel.getHeight() - buttonHeight) / 2, buttonWidth, buttonHeight, 0, 255, 255);
        button.addActionListener(e -> System.exit(0));
        resultPanel.add(button);
        this.add(resultPanel);
        this.repaint();
    }

    /**
     * Method to show one card of player
     * @param card Card to show
     * @param playerIndex Index of player
     * @param cardIndex Index of card to show in player list of cards
     */
    public void showTrump(Card card, int playerIndex, int cardIndex){
        labelsLists.get(playerIndex).get(cardIndex).setIcon(new ImageIcon(new ImageIcon(pathFronts + card.toString() + ".png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
    }

    /**
     * method to hide one card of player
     * @param playerIndex Index of player
     * @param cardIndex Index of card to hide in player list of cards
     */
    public void hideTrump(int playerIndex, int cardIndex){
        try{
            labelsLists.get(playerIndex).get(cardIndex).setIcon(new ImageIcon(new ImageIcon(pathBacks + backName + ".png")
                    .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        }catch (IndexOutOfBoundsException ignored){
        }

    }

    /**
     * Method to draw text
     * @param text Text to draw
     */
    public void drawText(String text){
        String textColor = "black";
        int fontSize = 24;
        info.setText("<html><h3><font color=\"" + textColor + "\"><font size =" + fontSize + ">" + text);
    }
}
