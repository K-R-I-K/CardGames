import lombok.Getter;
import lombok.Setter;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Class which help identify user in list of players
 */
public class User {
    @Getter
    @Setter
    private static int userId = 0;
    @Getter
    @Setter
    private static Player player = null;
    @Getter
    @Setter
    private static boolean isCreatedFile = false;
}
