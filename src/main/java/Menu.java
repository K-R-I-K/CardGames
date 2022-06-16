import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends JLayeredPane {
    public static JFrame frame;
    private int panelWidth;
    private int panelHeight;
    private int buttonWidth;
    private int buttonHeight;
    private Color buttonColor;
    private int fontSize;
    private int buttonNumber;
    private Durak durak;
    private MyGraphics window;
    private Server server;
    @Getter
    @Setter
    private boolean gameVsBot;
    @Getter
    @Setter
    private boolean gameVsPlayer;
    Menu() {
        gameVsBot = false;
        gameVsPlayer = false;
        durak = new Durak();
        window = new MyGraphics();
        panelWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        panelHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        buttonWidth = 300;
        buttonHeight = 100;
        buttonColor = new Color(0, 255, 255);
        fontSize = 24;
        buttonNumber = 4;
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
        newGameVsBot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                MyGraphics.frame.setVisible(true);
                //durak = new Durak();
                gameVsBot = true;
            }
        });
        this.add(newGameVsBot);
        JButton host = createButton("Host Player", "blue");
        host.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Server server = new Server(durak);
                User.setUserId(0);
            }
        });
        this.add(host);
        JButton client = createButton("Client Player", "blue");
        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Server server = new Server(durak);
                User.setUserId(1);
            }
        });
        this.add(client);
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

    public static void main(String[] args) {
        Menu menu = new Menu();
        while (true){
            if(menu.gameVsBot){
                //menu.durak = new Durak();
                menu.durak.gameVsBot(menu.window);
                menu.setGameVsBot(false);
            }else if(menu.gameVsPlayer){
                menu.durak.gameVsPlayer(menu.window, menu.server);
                menu.setGameVsPlayer(false);
            }
        }
        //menu.durak.gameVsBot(menu.window);
    }
}