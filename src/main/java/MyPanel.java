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
    Panel emptyPanel;
    Panel playerPanel;
    Panel firstEnemyPanel;
    Panel secondEnemyPanel;
    Panel thirdEnemyPanel;
    Panel centerPanel;
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
    private static GridBagConstraints constraint = new GridBagConstraints();

    private static GridBagConstraints constraints(int gx, int gy, int wx, int wy){
        constraint.weightx = (double)wx/32;
        constraint.weighty = (double)wy/18;
        //constraint.ipadx = wx;
        //constraint.ipady = wy;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.gridx = gx;
        constraint.gridy = gy;
        constraint.gridheight = wx;
        constraint.gridwidth = wy;
        return constraint;
    }

    private static Panel createPanel(int w, int h){
        Panel panel = new Panel();
        panel.setBackground(Color.red);
        panel.setSize(w, h);
        panel.setVisible(true);
        return panel;
    }

    public void myLayout(){
        this.setLayout(new GridBagLayout());
        firstEnemyPanel = new Panel();
        firstEnemyPanel.setBackground(Color.green);
        firstEnemyPanel.setSize(22, 4);
        secondEnemyPanel = new Panel();
        secondEnemyPanel.setBackground(Color.blue);
        secondEnemyPanel.setSize(5, 10);
        thirdEnemyPanel = new Panel();
        thirdEnemyPanel.setBackground(Color.pink);
        thirdEnemyPanel.setSize(5, 10);
        centerPanel = new Panel();
        centerPanel.setBackground(Color.orange);
        centerPanel.setSize(22, 10);
        centerPanel.setVisible(true);
        playerPanel = new Panel();
        playerPanel.setBackground(Color.yellow);
        playerPanel.setSize(22, 4);
        this.add(createPanel(5,4),      constraints(0, 0, 5, 4));
        /*this.add(firstEnemyPanel,             constraints(5, 0, 22, 4));
        this.add(createPanel(5,4),      constraints(27, 0, 5, 4));
        this.add(secondEnemyPanel,            constraints(0, 4, 5, 10));
        this.add(centerPanel,                 constraints(5, 4, 22, 10));
        this.add(thirdEnemyPanel,             constraints(27, 4, 5, 10));
        this.add(createPanel(5,4),      constraints(0, 14, 5, 4));
        this.add(playerPanel,                 constraints(5, 14, 22, 4));
        this.add(createPanel(5,4),      constraints(27, 14, 5, 4));*/
        this.revalidate();
    }

   /*public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
    }*/

    public void cardDeck(){
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(pathBacks + "blue.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        label.setBackground(bgColor);
        //label.setBounds(panelWidth/3, (panelHeight - cardHeight)/2, cardWidth, cardHeight);
        //label.addMouseListener(this);
        //firstEnemyPanel.add(label);
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
