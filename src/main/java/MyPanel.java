import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JLayeredPane implements ActionListener{
    private String pathFronts;
    private String pathBacks;
    private String backName;
    private Timer timer;
    private int cardWidth;
    private int cardHeight;
    private int panelWidth;
    private int panelHeight;
    private Color bgColor = Color.red;
    private List<JLayeredPane> playersPanelList;
    private List<JLayeredPane> battlePanels;
    private JLayeredPane centerPanel;
    private JLayeredPane deckPanel;
    private JLayeredPane discardedPanel;
    private List<List<JLabel>> labelsLists;
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
        //fillListCardsBacks();
        //fillListCardsFronts(deck);
        setLayout();
        cardsDeal(players);
        drawDeck(deck);
        //timer = new Timer(10, this);
    }

    private void cardsDeal(List<Player> players){
        labelsLists = new ArrayList<>();
        labelsLists.add(new ArrayList<>());
        cardsGraphic(players, labelsLists, 0);
        labelsLists.add(new ArrayList<>());
        cardsGraphic(players, labelsLists, 1);
    }

    private void cardsGraphic(List<Player> players, List<List<JLabel>> labelsList, int numberOfPlayer) {
       /* if (players.get(numberOfPlayer).getCards().size() < 9) {
            playersPanelList.get(numberOfPlayer).setLayout(new FlowLayout());
            for (int i = 0; i < players.get(numberOfPlayer).getCards().size(); ++i) {
                labelsList.get(numberOfPlayer).add(new JLabel(new ImageIcon(
                        new ImageIcon(getPath(numberOfPlayer == 0, players.get(numberOfPlayer).getCards().get(i)))
                        .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
                playersPanelList.get(numberOfPlayer).add(labelsList.get(numberOfPlayer).get(i));
                labelsList.get(numberOfPlayer).get(i).addMouseListener(this);
            }
        } else {
            int currentPosition = 10;
            int stepLength;
            playersPanelList.get(numberOfPlayer).setLayout(null);
            stepLength = (playersPanelList.get(numberOfPlayer).getWidth() - 20 - cardWidth) / (players.get(0).getCards().size() - 1);
            for (int i = 0; i < players.get(numberOfPlayer).getCards().size(); ++i) {
                JLabel label = new JLabel(new ImageIcon(new ImageIcon(getPath(numberOfPlayer == 0, players.get(numberOfPlayer).getCards().get(i)))
                        .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
                label.setBounds(currentPosition, 10, cardWidth, cardHeight);
                labelsList.get(numberOfPlayer).add(label);
                playersPanelList.get(numberOfPlayer).add(label);
                playersPanelList.get(numberOfPlayer).setLayer(label, i);
                currentPosition += stepLength;
            }
        }*/

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
                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        label.setLocation((battlePanels.get(0).getWidth() - cardWidth) / 2, 0);
                        label.removeMouseListener(this);
                        playersPanelList.get(numberOfPlayer).setLayer(label, 0);
                        battlePanels.get(0).add(label);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        label.setLocation(label.getX(), label.getY() - 40);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        label.setLocation(label.getX(), label.getY() + 40);
                    }
                });
            }
            currentPosition += stepLength;
        }
    }

    private String getPath(boolean isPlayer, Card card){
        return isPlayer ? pathFronts + card.toString() + ".png" : pathBacks + backName + ".png";
    }

    private static JLayeredPane createPanel(int x, int y, int w, int h){
        JLayeredPane panel = new JLayeredPane();
        panel.setBackground(Color.red);
        panel.setBounds(x, y, w, h);
        panel.setVisible(true);
        panel.setOpaque(true);
        return panel;
    }

   /* private JLabel createFrontCards(int x, int y, String cardsName){
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(pathFronts + cardsName + ".png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        label.setBounds(x, y, cardWidth, cardHeight);
        label.setVisible(true);
        return label;
    }*/

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

        /*centerPanel = new JLayeredPane();
        centerPanel.setBackground(Color.orange);
        centerPanel.setBounds(panelWidth * 5 / 32, panelHeight * 4 / 18, panelWidth * 22 / 32, panelHeight * 10 / 18);
        centerPanel.setOpaque(true);*/

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
            ++discardedCount;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       /* x += xVelocity;
        y += yVelocity;
        repaint();*/
    }
}
