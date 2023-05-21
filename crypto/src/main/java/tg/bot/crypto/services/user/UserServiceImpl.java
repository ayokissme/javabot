package tg.bot.crypto.services.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import tg.bot.crypto.entities.User;
import tg.bot.crypto.repositories.UserRepository;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(Long chatId, String userName) {
        User user = new User();
        user.setUsername(userName);
        user.setId(chatId);
        log.info("Saving user '{}'", chatId);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteLastState(User user) {
        user.setLastState(null);
        update(user);
        log.info("Last state was deleted for user: {}", user.getId());
    }
}
