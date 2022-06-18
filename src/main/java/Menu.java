import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Menu extends JLayeredPane {
    public static JFrame frame;
    private Color buttonColor;
    private int fontSize;
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
        int panelWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int panelHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int buttonWidth = 300;
        int buttonHeight = 100;
        buttonColor = new Color(0, 255, 255);
        fontSize = 24;
        int buttonNumber = 4;
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
        newGameVsBot.addActionListener(e -> {
            frame.setVisible(false);
            MyGraphics.frame.setVisible(true);
            //durak = new Durak();
            gameVsBot = true;
        });
        this.add(newGameVsBot);
        JButton host = createButton("Host Player", "blue");
        host.addActionListener(e -> {
            server = new Server();
            User.setUserId(0);
            User.setPlayer(durak.getPlayers().get(User.getUserId()));
            System.out.println("host1");
            while(!server.isAccepted()){
                System.out.println("host is waiting");
            }
            byte[] data;
            data = SerializationUtils.serialize(durak);
            System.out.println("host2");

            try {
                server.getDos().write(data);
                //server.getDos().writeInt(100);
                server.getDos().flush();
                System.out.println("a:");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            gameVsPlayer = true;
        });
        this.add(host);
        JButton client = createButton("Client Player", "blue");
        client.addActionListener(e -> {
            server = new Server();
            User.setUserId(1);
            User.setPlayer(durak.getPlayers().get(User.getUserId()));
            while(!server.isAccepted()){
                System.out.println("client is waiting");

            }

            byte[] data;
            System.out.println("client");
            try {
                while(server.getDis().available()<=0){
                    System.out.println("client waiting for durak object");
                }
                int count = server.getDis().available();
                data = new byte[count];
                server.getDis().read(data);
                Durak buf = SerializationUtils.deserialize(data);
                durak.setPlayers(buf.getPlayers());
                durak.setField(buf.getField());
                durak.setDeck(buf.getDeck());
                System.out.println("Taken durak");
                window.drawDeck(durak.getDeck());


            } catch (IOException ex) {
                ex.printStackTrace();
            }
            gameVsPlayer = true;
        });
        this.add(client);
        JButton exit = createButton("Exit", "blue");
        exit.addActionListener(e -> System.exit(0));
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