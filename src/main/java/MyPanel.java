import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class MyPanel extends JPanel implements ActionListener, MouseListener {
    private String pathFronts;
    private String pathBacks;
    Timer timer;
    int cardWidth;
    int cardHeight;
    int panelWidth;
    int panelHeight;
    Color bgColor = Color.red;
    JPanel emptyPanel;
    JPanel playerPanel;
    JPanel firstEnemyPanel;
    JPanel secondEnemyPanel;
    JPanel thirdEnemyPanel;
    JPanel centerPanel;
    MyPanel(){
        pathFronts = "src/main/resources/png/fronts/";
        pathBacks = "src/main/resources/png/backs/";
        cardWidth = 234 / 2;
        cardHeight = 333 / 2;
        panelWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        panelHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setBounds(0, 0, panelWidth, panelHeight);
        //this.setBackground(bgColor);
        myLayout();
        cardDeck();
        this.setVisible(true);
        //timer = new Timer(10, this);
    }

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
        thirdEnemyPanel.setBackground(Color.pink);
        thirdEnemyPanel.setBounds(panelWidth * 27 / 32, panelWidth * 4 / 32, panelWidth * 5 / 32, panelHeight * 10 / 18);
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.orange);
        centerPanel.setBounds(panelWidth * 5 / 32, panelHeight * 4 / 18, panelWidth * 22 / 32, panelHeight * 10 / 18);
        centerPanel.setVisible(true);
        playerPanel = new JPanel();
        playerPanel.setBackground(Color.yellow);
        playerPanel.setBounds(panelWidth * 5 / 32, panelHeight * 14 / 18, panelWidth * 22 / 32, panelHeight * 4 / 18);
        this.add(createPanel(0,0,panelWidth * 5 / 32,panelHeight*4/18));
        this.add(firstEnemyPanel);
        this.add(createPanel(panelWidth - panelWidth * 5 / 32,0,panelWidth*5/32,panelHeight*4/18));
        this.add(secondEnemyPanel);
        this.add(centerPanel);
        this.add(thirdEnemyPanel);
        this.add(createPanel(0,panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight*4/18));
        this.add(playerPanel);
        this.add(createPanel(panelWidth * 27 / 32, panelHeight * 14 / 18,panelWidth * 5 / 32,panelHeight*4/18));
        this.revalidate();
    }

   /*public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
    }*/

    public void cardDeck(){
        firstEnemyPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(pathBacks + "blue.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        //label.setBackground(bgColor);
        //label.setBounds(panelWidth/3, (panelHeight - cardHeight)/2, cardWidth, cardHeight);
        //label.addMouseListener(this);
        JLabel label1 = new JLabel(new ImageIcon(new ImageIcon(pathBacks + "blue.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        JLabel label2 = new JLabel(new ImageIcon(new ImageIcon(pathBacks + "blue.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        firstEnemyPanel.add(label);
        firstEnemyPanel.add(label);
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
