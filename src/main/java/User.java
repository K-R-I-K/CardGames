import lombok.Getter;
import lombok.Setter;

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
}
