package tg.bot.crypto.services.user;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
public interface UserService {
    void save(Long chatId, String userName);
    boolean exists(Long id);
}
