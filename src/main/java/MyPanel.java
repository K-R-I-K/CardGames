import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyPanel extends JLayeredPane{
    private String pathFronts;
    private String pathBacks;
    private String backName;
    private int cardWidth;
    private int cardHeight;
    private int panelWidth;
    private int panelHeight;
    private Color bgColor = Color.red;
    private List<JLayeredPane> playersPanelList;
    private List<JLayeredPane> battlePanels;
    private JLayeredPane deckPanel;
    private JLayeredPane discardedPanel;
    private List<List<JLabel>> labelsLists;
    private List<Boolean> playerChoose;
    private List<MouseListener> playerMouseListeners;
    private List<JLabel> deckLabels;
    private int discardedCount;

    MyPanel(List<Player> players, Deck deck){
        this.setBounds(0, 0, panelWidth, panelHeight);
        this.setVisible(true);
        pathFronts = "src/main/resources/png/fronts/";
        pathBacks = "src/main/resources/png/backs/";
        backName = "blue2";
        cardWidth = 234 * 6 / 10;
        cardHeight = 333 * 6 / 10;
        panelWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        panelHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        discardedCount = 0;
        setLayout();
        cardsDeal(players);
        drawDeck(deck);
        //drawDiscarded(10);
    }

    private void cardsDeal(List<Player> players){
        labelsLists = new ArrayList<>();
        playerChoose = new ArrayList<>(Collections.nCopies(players.get(0).getCards().size(), false));
        playerMouseListeners = new ArrayList<>();
        labelsLists.add(new ArrayList<>());
        cardsGraphic(players, labelsLists, 0);
        labelsLists.add(new ArrayList<>());
        cardsGraphic(players, labelsLists, 1);
    }

    private void cardsGraphic(List<Player> players, List<List<JLabel>> labelsList, int numberOfPlayer) {
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
                        mousePress(playersPanelList.get(0).getLayer(label));
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if(!playerChoose.get(playersPanelList.get(0).getLayer(label)))
                            label.setLocation(label.getX(), label.getY() - 40);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if(!playerChoose.get(playersPanelList.get(0).getLayer(label)))
                            label.setLocation(label.getX(), label.getY() + 40);
                    }
                });
                label.addMouseListener(playerMouseListeners.get(i));
            }
            currentPosition += stepLength;
        }
    }

    private void mousePress(int index){
        for (int i = 0; i < playerChoose.size(); ++i) {
            if(playerChoose.get(i) && i != index){
                playerChoose.set(i, false);
                labelsLists.get(0).get(i).setLocation(labelsLists.get(0).get(i).getX(), labelsLists.get(0).get(i).getY() + 40);
            }
        }
        playerChoose.set(index, true);
    }

    private String getPath(boolean isPlayer, Card card){
        return isPlayer ? pathFronts + card.toString() + ".png" : pathBacks + backName + ".png";
    }

    private JLayeredPane createPanel(int x, int y, int w, int h){
        JLayeredPane panel = new JLayeredPane();
        panel.setBackground(Color.red);
        panel.setBounds(x, y, w, h);
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

        playersPanelList.add(createPanel(panelWidth * 5 / 32, panelHeight * 14 / 18, panelWidth * 22 / 32, panelHeight * 4 / 18));
        playersPanelList.add(createPanel(panelWidth * 5 / 32, 0, panelWidth * 22 / 32, panelHeight * 4 / 18));
        playersPanelList.add(createPanel(0, panelHeight * 4 / 18, panelWidth * 5 / 32, panelHeight * 10 / 18));
        playersPanelList.add(createPanel(panelWidth * 27 / 32, panelWidth * 4 / 32, panelWidth * 5 / 32, panelHeight * 10 / 18));

        deckPanel = new JLayeredPane();
        deckPanel.setBackground(Color.black);
        deckPanel.setBounds(0,0,panelWidth * 5 / 32,panelHeight * 4 / 18);
        deckPanel.setOpaque(true);

        discardedPanel = new JLayeredPane();
        discardedPanel.setBackground(Color.black);
        discardedPanel.setBounds(panelWidth * 27 / 32,0,panelWidth * 5 / 32,panelHeight * 4 / 18);
        discardedPanel.setOpaque(true);

        battlePanels = new ArrayList<>();
        battlePanels.add(createPanel(panelWidth * 27 / 32 / 3, panelHeight * 4 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18));
        battlePanels.add(createPanel(panelWidth * 41 / 32 / 3, panelHeight * 4 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18));
        battlePanels.add(createPanel(panelWidth * 55 / 32 / 3, panelHeight * 4 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18));
        battlePanels.add(createPanel(panelWidth * 27 / 32 / 3, panelHeight * 9 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18));
        battlePanels.add(createPanel(panelWidth * 41 / 32 / 3, panelHeight * 9 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18));
        battlePanels.add(createPanel(panelWidth * 55 / 32 / 3, panelHeight * 9 / 18, panelWidth * 14 / 32 / 3, panelHeight * 5 / 18));

        this.add(deckPanel);
        this.add(playersPanelList.get(1));
        this.add(discardedPanel);
        this.add(playersPanelList.get(2));
        //this.add(centerPanel);
        this.add(playersPanelList.get(3));
        this.add(createPanel(0,panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight * 4 / 18));
        this.add(playersPanelList.get(0));
        this.add(createPanel(panelWidth * 27 / 32, panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight * 4 / 18));
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

    private void drawDiscarded(int number) {
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
}
