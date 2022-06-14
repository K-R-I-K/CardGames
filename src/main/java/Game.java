import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class Game extends JFrame {
    public static Durak durak;

    Game(){
        durak = new Durak();
    }
}
