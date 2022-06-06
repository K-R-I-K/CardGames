import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class MyPanel extends JPanel implements ActionListener, MouseListener {
    private ImageIcon image;
    private String path = "src/main/resources/png/fronts/";
    Timer timer;
    int cardWidth;
    int cardHeight;
    int panelWidth;
    int panelHeight;
    int x = 0;
    int y = 0;
    int xVelocity = 1;
    int yVelocity = 1;
    MyPanel(){
        panelWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        panelHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.red);
        timer = new Timer(10, this);
        cardWidth = 156;
        cardHeight = 222;
        timer.start();

        JLabel label = new JLabel(new ImageIcon(new ImageIcon(path + "clubs_6.png")
                .getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH)));
        label.setBackground(Color.red);
        label.setOpaque(true);
        label.addMouseListener(this);
        this.add(label);
        this.setVisible(true);
    }

    /*public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
        image = new ImageIcon(path + "clubs_6.png").getImage();


        //label.addMouseListener(this);

        //graphics.drawImage(image, x, y, null);
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        //x += xVelocity;
        //y += yVelocity;
        //repaint();
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
