import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel implements ActionListener, MouseListener {
    private String pathFronts;
    private String pathBacks;
    private String backName;
    private Timer timer;
    private int cardWidth;
    private int cardHeight;
    private int panelWidth;
    private int panelHeight;
    private Color bgColor = Color.red;
    private List<JPanel> playersPanelList;
    private List<JPanel> battleCardsPanel;
    private JPanel centerPanel;
    private JPanel deckPanel;
    private JPanel discardedPanel;
    private List<JLabel> firstEnemyCards;
    private List<JLabel> playerCards;

    MyPanel(List<Player> players){
        this.setBounds(0, 0, panelWidth, panelHeight);
        this.setVisible(true);
        pathFronts = "src/main/resources/png/fronts/";
        pathBacks = "src/main/resources/png/backs/";
        backName = "blue";
        cardWidth = 234 * 6 / 10;
        cardHeight = 333 * 6 / 10;
        panelWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        panelHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        //fillListCardsBacks();
        //fillListCardsFronts(deck);
        myLayout();
        //cardsDeal(players);
        cardDeck();

        //timer = new Timer(10, this);
    }

    private void cardsDeal(List<Player> players){
        playerCards = new ArrayList<>();
        int currentPosition = 10;
        int stepLength;

        playersPanelList.get(0).setLayout(null);
        if(players.get(0).getCards().size() < 9)
            for(int i = 0; i < players.get(0).getCards().size(); ++i){
                playerCards.add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + players.get(0).getCards().get(i).toString() + ".png")
                        .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
                playersPanelList.get(0).add(playerCards.get(i));
            }
        else {
            stepLength = (playersPanelList.get(0).getWidth() - 20) / players.get(0).getCards().size();
            for (int i = 0; i < players.get(0).getCards().size(); ++i) {
                playerCards.add(createFrontCards(currentPosition, 10, players.get(0).getCards().get(i).toString()));
                currentPosition += stepLength;
                playersPanelList.get(0).add(playerCards.get(i));
            }
        }

        firstEnemyCards = new ArrayList<>();
        for(int i = 0; i < players.get(1).getCards().size(); ++i){
            firstEnemyCards.add(new JLabel(new ImageIcon(new ImageIcon(pathBacks + backName + ".png")
                    .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
            playersPanelList.get(1).add(firstEnemyCards.get(i));
        }
    }

    /*private void fillListCardsBacks(){
        cardsBacks = new ArrayList<>();
        for(int i = 0; i < 36; ++i){
            firstEnemyPanel.add(new JLabel(new ImageIcon(new ImageIcon(pathBacks + "blue.png")
                    .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        }
    }*/

    /*private void fillListCardsFronts(Deck deck){
        cardsFronts = new ArrayList<>();
        for(int i = 0; i < 36; ++i){
            cardsFronts.add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + deck.getCard().toString() +".png")
                    .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        }
    }*/

    private static JPanel createPanel(int x, int y, int w, int h){
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        panel.setBounds(x, y, w, h);
        panel.setVisible(true);
        return panel;
    }

    private JLabel createFrontCards(int x, int y, String cardsName){
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(pathFronts + cardsName + ".png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        label.setBounds(x, y, cardWidth, cardHeight);
        label.setVisible(true);
        return label;
    }

    public void myLayout(){
        this.setLayout(null);
        playersPanelList = new ArrayList<>();

/*        JPanel playerPanel;
        JPanel firstEnemyPanel;
        JPanel secondEnemyPanel;
        JPanel thirdEnemyPanel;

        firstEnemyPanel = new JPanel();
        firstEnemyPanel.setBackground(Color.green);
        firstEnemyPanel.setBounds(panelWidth * 5 / 32, 0, panelWidth * 22 / 32, panelHeight * 4 / 18);

        secondEnemyPanel = new JPanel();
        secondEnemyPanel.setBackground(Color.blue);
        secondEnemyPanel.setBounds(0, panelHeight * 4 / 18, panelWidth * 5 / 32, panelHeight * 10 / 18);

        thirdEnemyPanel = new JPanel();
        thirdEnemyPanel.setBackground(Color.lightGray);
        thirdEnemyPanel.setBounds(panelWidth * 27 / 32, panelWidth * 4 / 32, panelWidth * 5 / 32, panelHeight * 10 / 18);

        playerPanel = new JPanel();
        playerPanel.setBackground(Color.yellow);
        playerPanel.setBounds(panelWidth * 5 / 32, panelHeight * 14 / 18, panelWidth * 22 / 32, panelHeight * 4 / 18);*/

        playersPanelList.add(createPanel(panelWidth * 5 / 32, panelHeight * 14 / 18, panelWidth * 22 / 32, panelHeight * 4 / 18));
        playersPanelList.add(createPanel(panelWidth * 5 / 32, 0, panelWidth * 22 / 32, panelHeight * 4 / 18));
        playersPanelList.add(createPanel(0, panelHeight * 4 / 18, panelWidth * 5 / 32, panelHeight * 10 / 18));
        playersPanelList.add(createPanel(panelWidth * 27 / 32, panelWidth * 4 / 32, panelWidth * 5 / 32, panelHeight * 10 / 18));

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.orange);
        centerPanel.setBounds(panelWidth * 5 / 32, panelHeight * 4 / 18, panelWidth * 22 / 32, panelHeight * 10 / 18);

        deckPanel = new JPanel();
        deckPanel.setBackground(Color.black);
        deckPanel.setBounds(0,0,panelWidth * 5 / 32,panelHeight * 4 / 18);

        discardedPanel = new JPanel();
        discardedPanel.setBackground(Color.black);
        discardedPanel.setBounds(panelWidth * 27 / 32,0,panelWidth * 5 / 32,panelHeight * 4 / 18);

        battleCardsPanel = new ArrayList<>();
        //battleCardsPanel.add();


        this.add(deckPanel);
        this.add(playersPanelList.get(1));
        this.add(discardedPanel);
        this.add(playersPanelList.get(2));
        //this.add(centerPanel);
        this.add(playersPanelList.get(3));
        this.add(createPanel(0,panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight * 4 / 18));
        this.add(playersPanelList.get(0));
        this.add(createPanel(panelWidth * 27 / 32, panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight * 4 / 18));
        this.revalidate();
    }

   /*public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
    }*/

    public void cardDeck(){
        List<Card> playerss = new ArrayList<>();
        int currentPosition = 10;
        int stepLength;

        for(int i = 0; i < 10; ++i){
            playerss.add(new Card(Rank.ACE, Suit.SPADES));
        }

        playersPanelList.get(0).setLayout(null);
        //playersPanelList.get(0).setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        playersPanelList.get(0).setBackground(Color.green);
        stepLength = (playersPanelList.get(0).getWidth() - 30) / playerss.size();
        for (int i = playerss.size() - 1; i >= 0; --i) {
            JLabel label = createFrontCards(currentPosition, 10, playerss.get(i).toString());
            playersPanelList.get(0).add(label);
            currentPosition += stepLength;
        }
       /* playersPanelList.get(0).add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playersPanelList.get(0).add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playersPanelList.get(0).add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playersPanelList.get(0).add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playersPanelList.get(0).add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playersPanelList.get(0).add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playersPanelList.get(0).add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playersPanelList.get(0).add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playersPanelList.get(0).add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       /* x += xVelocity;
        y += yVelocity;
        repaint();*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("click");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("press");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("release");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("enter");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("exited");
    }
}
