import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class Game extends JFrame {
    public static void main(String[] args) {
        MyGraphics window = new MyGraphics();
        Menu menu = new Menu();
        Durak durak = new Durak();
        durak.gameVsBot(window);
    }
}
