import javax.swing.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
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
    private JPanel emptyPanel;
    private JPanel playerPanel;
    private JPanel firstEnemyPanel;
    private JPanel secondEnemyPanel;
    private JPanel thirdEnemyPanel;
    private JPanel centerPanel;
    private List<JLabel> firstEnemyCards;
    private List<JLabel> playerCards;
    private List<Player> players;

    MyPanel(Deck deck, List<Player> players){
        this.setBounds(0, 0, panelWidth, panelHeight);
        this.setVisible(true);
        pathFronts = "src/main/resources/png/fronts/";
        pathBacks = "src/main/resources/png/backs/";
        backName = "blue";
        cardWidth = 234 / 2;
        cardHeight = 333 / 2;
        panelWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        panelHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        //fillListCardsBacks();
        //fillListCardsFronts(deck);
        myLayout();
        cardsDeal(players);
        //cardDeck();

        //timer = new Timer(10, this);
    }

    private void cardsDeal(List<Player> players){
        playerCards = new ArrayList<>();
        for(int i = 0; i < players.get(0).getCards().size(); ++i){
            playerCards.add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + players.get(0).getCards().get(i).toString() + ".png")
                    .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
            playerPanel.add(playerCards.get(i));
        }

        firstEnemyCards = new ArrayList<>();
        for(int i = 0; i < players.get(1).getCards().size(); ++i){
            firstEnemyCards.add(new JLabel(new ImageIcon(new ImageIcon(pathBacks + backName + ".png")
                    .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
            firstEnemyPanel.add(firstEnemyCards.get(i));
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

    public void myLayout(){
        this.setLayout(null);

        firstEnemyPanel = new JPanel();
        firstEnemyPanel.setBackground(Color.green);
        firstEnemyPanel.setBounds(panelWidth * 5 / 32, 0, panelWidth * 22 / 32, panelHeight * 4 / 18);

        secondEnemyPanel = new JPanel();
        secondEnemyPanel.setBackground(Color.blue);
        secondEnemyPanel.setBounds(0, panelHeight * 4 / 18, panelWidth * 5 / 32, panelHeight * 10 / 18);

        thirdEnemyPanel = new JPanel();
        thirdEnemyPanel.setBackground(Color.lightGray);
        thirdEnemyPanel.setBounds(panelWidth * 27 / 32, panelWidth * 4 / 32, panelWidth * 5 / 32, panelHeight * 10 / 18);

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.orange);
        centerPanel.setBounds(panelWidth * 5 / 32, panelHeight * 4 / 18, panelWidth * 22 / 32, panelHeight * 10 / 18);

        playerPanel = new JPanel();
        playerPanel.setBackground(Color.yellow);
        playerPanel.setBounds(panelWidth * 5 / 32, panelHeight * 14 / 18, panelWidth * 22 / 32, panelHeight * 4 / 18);

        this.add(createPanel(0,0,panelWidth * 5 / 32,panelHeight * 4 / 18));
        this.add(firstEnemyPanel);
        this.add(createPanel(panelWidth - panelWidth * 5 / 32,0,panelWidth * 5 / 32,panelHeight * 4 / 18));
        this.add(secondEnemyPanel);
        this.add(centerPanel);
        this.add(thirdEnemyPanel);
        this.add(createPanel(0,panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight * 4 / 18));
        this.add(playerPanel);
        this.add(createPanel(panelWidth * 27 / 32, panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight * 4 / 18));
        this.revalidate();
    }

   /*public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
    }*/

    public void cardDeck(){
        playerPanel.add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playerPanel.add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playerPanel.add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playerPanel.add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playerPanel.add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
        playerPanel.add(new JLabel(new ImageIcon(new ImageIcon(pathFronts + "spades_ace.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH))));
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
