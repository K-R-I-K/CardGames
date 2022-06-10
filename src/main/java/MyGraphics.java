import javax.swing.*;
import java.util.List;

public class MyGraphics extends JFrame /*JWindow*/{
    MyPanel panel;

    MyGraphics(List<Player> players){
        panel = new MyPanel(players);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.add(panel);
        //this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
