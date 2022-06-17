import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter
    @Setter
    private static int userId = 0;
    @Getter
    @Setter
    private static Player player = null;
}
