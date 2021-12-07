import com.ut.sm41.dto.UserDTO;
import com.ut.sm41.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    /**
     *
     * @param username
     * @param pwd
     * @return
     */

    public JSONObject loginAuthentication(String username, String pwd);

    /**
     *Generar un registro nuevo en la tabla de usuario
     *
     * @param user
     * @return
     */

    public UserDTO createUser(User user);
}