import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyGraphics extends JLayeredPane{
    private JFrame frame;
    private String pathFronts;
    private String pathBacks;
    private String backName;
    private int cardWidth;
    private int cardHeight;
    private int panelWidth;
    private int panelHeight;
    private int buttonWidth;
    private int buttonHeight;
    private Color bgColor = Color.red;
    private List<JLayeredPane> playersPanelList;
    private List<JLayeredPane> battlePanels;
    private JLayeredPane deckPanel;
    private JLayeredPane discardedPanel;
    private JLayeredPane exitPanel;
    private JLayeredPane buttonPanel;
    private List<List<JLabel>> labelsLists;
    private List<Boolean> playerChoose;
    private List<MouseListener> playerMouseListeners;
    private List<JLabel> deckLabels;
    private int discardedCount;
    private volatile int cardIndex;
    private volatile int fieldIndex;

    MyGraphics(){
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
        setLayout();
        drawExitButton();
        drawActionButton();
        //drawDeck(deck);
        //cardsDeal(players, field);
        //drawDiscarded(10);
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(this);
        //this.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setBounds(0, 0, panelWidth, panelHeight);
        frame.setVisible(true);
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public int getFieldIndex() {
        return fieldIndex;
    }

    public void setFieldIndex(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public void setCardIndex(int cardIndex){
        this.cardIndex = cardIndex;
    }

    public void cardsDeal(List<Player> players, Field field){
        labelsLists = new ArrayList<>();
        playerChoose = new ArrayList<>(Collections.nCopies(players.get(0).getCards().size(), false));
        playerMouseListeners = new ArrayList<>();
        labelsLists.add(new ArrayList<>());
        playersPanelList.get(0).removeAll();
        cardsGraphic(players, labelsLists, 0, field);
        labelsLists.add(new ArrayList<>());
        playersPanelList.get(1).removeAll();
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
            stepLength = (playersPanelList.get(numberOfPlayer).getWidth() - 20 - cardWidth) / (players.get(0).getCards().size() - 1);
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
                        if(field.moveCheck(players.get(0).getIsDefend(),players.get(0).getCard(playersPanelList.get(0).getLayer(label)))) {
                            mousePress(playersPanelList.get(0).getLayer(label), field, players.get(0));
                            cardIndex = playersPanelList.get(0).getLayer(label);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if(!playerChoose.get(playersPanelList.get(0).getLayer(label)) &&
                                field.moveCheck(players.get(0).getIsDefend(), players.get(0).getCard(playersPanelList.get(0).getLayer(label))))
                            label.setLocation(label.getX(), label.getY() - 40);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if(!playerChoose.get(playersPanelList.get(0).getLayer(label))&&
                                field.moveCheck(players.get(0).getIsDefend(), players.get(0).getCard(playersPanelList.get(0).getLayer(label))))
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
                if(field.moveCheck(player.getIsDefend(),player.getCard(i)))
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
        panel.setBackground(Color.red);
        panel.setBounds(x, y, w, h);
        if(battleFieldIndex != -1)
            panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < playerChoose.size(); ++i) {
                    if(playerChoose.get(i)){
                        JLabel label= labelsLists.get(0).get(i);
                        label.setLocation((panel.getWidth() - cardWidth) / 2, 0);
                        label.removeMouseListener(playerMouseListeners.get(i));
                        panel.removeMouseListener(this);
                        playersPanelList.get(0).setLayer(label, 0);
                        playerChoose.set(i, false);
                        label.setLocation(labelsLists.get(0).get(i).getX(), labelsLists.get(0).get(i).getY() + 40);
                        panel.add(label);
                        fieldIndex = battleFieldIndex;
                    }
                }
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

        battlePanels = new ArrayList<>();
        battlePanels.add(createPanel(panelWidth * 27 / 32 / 3, panelHeight * 4 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 0));
        battlePanels.add(createPanel(panelWidth * 41 / 32 / 3, panelHeight * 4 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 1));
        battlePanels.add(createPanel(panelWidth * 55 / 32 / 3, panelHeight * 4 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 2));
        battlePanels.add(createPanel(panelWidth * 27 / 32 / 3, panelHeight * 9 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 3));
        battlePanels.add(createPanel(panelWidth * 41 / 32 / 3, panelHeight * 9 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 4));
        battlePanels.add(createPanel(panelWidth * 55 / 32 / 3, panelHeight * 9 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18, 5));

        this.add(deckPanel);
        this.add(playersPanelList.get(1));
        this.add(discardedPanel);
        this.add(playersPanelList.get(2));
        //this.add(centerPanel);
        this.add(playersPanelList.get(3));
        this.add(exitPanel);
        this.add(playersPanelList.get(0));
        this.add(buttonPanel);
        for(int i = 0; i < battlePanels.size(); ++i){
            this.add(battlePanels.get(i));
        }

        this.revalidate();
    }

    public void drawDeck(Deck deck){
        int upDeck = 50;
        deckLabels = new ArrayList<>();
        deckPanel.setLayout(null);
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(pathFronts +  deck.getTrump().toString() + ".png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        label.setBounds((deckPanel.getWidth() - cardWidth) / 2, (deckPanel.getHeight() - cardHeight) / 2 - upDeck, cardWidth, cardHeight);
        deckLabels.add(label);
        deckPanel.add(label);
        deckPanel.setLayer(label, 0);
        for(int i = 0; i < deck.getSize(); ++i) {
            label = new JLabel(new ImageIcon(new ImageIcon(pathBacks +  backName + "_h.png")
                    .getImage().getScaledInstance(cardHeight, cardWidth, Image.SCALE_SMOOTH)));
            label.setBounds((deckPanel.getWidth() - cardHeight) / 2, (deckPanel.getHeight() - cardHeight) / 2 +  - i * 3/ 2 - upDeck, cardHeight, cardWidth);
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
        int y = player.getIsDefend()?40:100;
        JLabel label = labelsLists.get(PlayerIndex).get(cardIndex);
        label.setIcon(new ImageIcon(new ImageIcon(pathFronts + player.getCard(cardIndex).toString() + ".png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        label.setLocation((battlePanels.get(battleFieldIndex).getWidth() - cardWidth) / 2, y);
        if(y == 40)
            battlePanels.get(battleFieldIndex).add(label, 0);
        else
            battlePanels.get(battleFieldIndex).add(label, 1);
    }

    private void drawExitButton(){
        JButton exitButton = createButton("Close", "blue", (exitPanel.getWidth() - buttonWidth) / 2,
        (exitPanel.getHeight() - buttonHeight) / 2, buttonWidth, buttonHeight, 0, 255, 255);
        exitButton.addActionListener(e -> System.exit(0));
        exitPanel.add(exitButton);
    }

    private void drawActionButton(){
        JButton actionButton = createButton("Pass", "blue", (buttonPanel.getWidth() - buttonWidth) / 2,
                (buttonPanel.getHeight() - buttonHeight) / 2, buttonWidth, buttonHeight, 0, 255, 255);
        //actionButton.addActionListener();
        buttonPanel.add(actionButton);
    }

    private JButton createButton(String name, String textColor, int x, int y, int w, int h, int r, int g, int b){
        JButton button = new JButton("<html><h2><font color=\"" + textColor +"\">" + name);
        button.setBounds(x, y, w, h);
        button.setBorderPainted(false);
        button.setBackground(new Color(r, g, b));
        button.setFocusPainted(false);
        return button;
    }
}
