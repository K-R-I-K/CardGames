import javax.swing.*;

public class MyGraphics extends JFrame /*JWindow*/{
    MyPanel panel;

    MyGraphics(){
        panel = new MyPanel();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(false);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
