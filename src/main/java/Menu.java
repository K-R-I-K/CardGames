import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends JLayeredPane {
    public static JFrame frame;
    private int panelWidth;
    private int panelHeight;
    private int buttonWidth;
    private int buttonHeight;
    private Color buttonColor;
    private int fontSize;
    private int buttonNumber;

    Menu() {
        panelWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        panelHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        buttonWidth = 300;
        buttonHeight = 150;
        buttonColor = new Color(0, 255, 255);
        fontSize = 24;
        buttonNumber = 2;
        this.setBounds(0, 0, panelWidth, panelHeight);
        this.setBackground(new Color(204, 204, 204));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder((panelHeight - buttonHeight * buttonNumber) / 2,
                (panelWidth - buttonWidth) / 2, (panelHeight - buttonHeight * buttonNumber) / 2, (panelWidth - buttonWidth) / 2));
        setButtons();
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(this);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setBounds(0, 0, panelWidth, panelHeight);
        frame.setVisible(true);
    }

    private void setButtons() {
        JButton newGameVsBot = createButton("Player vs Bot", "blue");
        newGameVsBot.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                frame.setVisible(false);
                MyGraphics.frame.setVisible(true);
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        this.add(newGameVsBot);
        JButton exit = createButton("Exit", "blue");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(exit);
    }

    private JButton createButton(String name, String textColor) {
        JButton button = new JButton("<html><h3><font color=\"" + textColor + "\"><font size =" + fontSize + ">" + name);
        button.setBorderPainted(true);
        button.setBackground(buttonColor);
        button.setFocusPainted(false);
        return button;
    }
}