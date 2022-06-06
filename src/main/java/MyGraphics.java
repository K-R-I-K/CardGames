import javax.swing.*;

public class MyGraphics extends JFrame {
    MyPanel panel;

    MyGraphics(){
        panel = new MyPanel();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
