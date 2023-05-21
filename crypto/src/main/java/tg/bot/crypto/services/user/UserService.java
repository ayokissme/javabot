package tg.bot.crypto.services.user;

import java.util.Optional;
import tg.bot.crypto.entities.User;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
public interface UserService {

    User save(Long chatId, String userName);

    User update(User user);

    boolean exists(Long id);

    Optional<User> getUser(Long id);

    void deleteLastState(User user);
}
