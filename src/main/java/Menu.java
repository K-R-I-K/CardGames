import javax.swing.*;
import java.awt.*;
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
    private Game game;

    Menu() {
        panelWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        panelHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        buttonWidth = 300;
        buttonHeight = 150;
        buttonColor = new Color(0, 255, 255);
        fontSize = 24;
        this.setBounds(0, 0, panelWidth, panelHeight);
        this.setBackground(new Color(204, 204, 204));
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
        JButton newGameVsBot = createButton("Player vs Bot", "blue", (panelWidth - buttonWidth) / 2, (panelHeight - buttonHeight) / 2);
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
    }

    private JButton createButton(String name, String textColor, int x, int y) {
        JButton button = new JButton("<html><h3><font color=\"" + textColor + "\"><font size =" + fontSize + ">" + name);
        button.setBounds(x, y, buttonWidth, buttonHeight);
        button.setBorderPainted(false);
        button.setBackground(buttonColor);
        button.setFocusPainted(false);
        return button;
    }
}