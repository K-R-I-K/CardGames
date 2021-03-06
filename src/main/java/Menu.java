import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.SerializationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Class which represents menu for our card game
 */
public class Menu extends JLayeredPane {
    /**
     * Current menu frame
     */
    public static JFrame frame;
    private Color buttonColor;
    private int fontSize;
    private Durak durak;
    private MyGraphics window;
    private Server server;
    @Getter
    @Setter
    private volatile boolean gameVsBot;
    @Getter
    @Setter
    private volatile boolean gameVsPlayer;

    /**
     * Constructor for menu object
     */
    Menu() {
        gameVsBot = false;
        gameVsPlayer = false;
        durak = new Durak();
        //window = new MyGraphics();
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
            //frame.setVisible(false);
            frame.dispose();
            //MyGraphics.frame.setVisible(true);
            window = new MyGraphics();
            //durak = new Durak();
            gameVsBot = true;
        });
        this.add(newGameVsBot);
        JButton host = createButton("Host Player", "blue");
        host.addActionListener(e -> {
            User.setUserId(0);
            server = new Server();

            User.setPlayer(durak.getPlayers().get(User.getUserId()));
            System.out.println("host1");
            while(!server.isAccepted()){
                System.out.println("host is waiting");
            }
            byte[] data;
            data = SerializationUtils.serialize(durak);
            System.out.println("host2");

            try {
                window = new MyGraphics();
                server.getDos().writeInt(data.length);
                server.getDos().write(data, 0, data.length);
                //server.getDos().writeInt(100);
                server.getDos().flush();
                System.out.println("a:");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            gameVsPlayer = true;
            frame.dispose();
            //MyGraphics.frame.setVisible(true);
        });
        this.add(host);
        JButton client = createButton("Client Player", "blue");
        client.addActionListener(e -> {
            User.setUserId(1);
            server = new Server();


            User.setPlayer(durak.getPlayers().get(User.getUserId()));
            int i1=0;
            while(!server.isAccepted()){
                i1=(i1+1)%10;
                if(i1==0)
                System.out.println("client is waiting");
            }

            byte[] data;
            System.out.println("client");
            try {
                int i2=0;
                while(server.getDis().available()<=0){
                    i2=(i2+1)%10;
                    if(i2==0)
                        System.out.println("client waiting for durak object");
                }
                //int count = server.getDis().available();
                int count = server.getDis().readInt();
                data = new byte[count];
                server.getDis().read(data,0, count);
                Durak buf = SerializationUtils.deserialize(data);
                durak.setPlayers(buf.getPlayers());
                durak.setField(buf.getField());
                durak.setDeck(buf.getDeck());
                System.out.println("Taken durak");
                frame.dispose();
                //MyGraphics.frame.setVisible(true);
                window = new MyGraphics();
                window.drawDeck(durak.getDeck());
                server.setClientConnected(true);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            gameVsPlayer = true;
        });
        this.add(client);
        JButton exit = createButton("Exit", "blue");
        exit.addActionListener(e -> {
            if(server.getPrintWriter()!=null)
                server.getPrintWriter().close();
            System.exit(0);
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