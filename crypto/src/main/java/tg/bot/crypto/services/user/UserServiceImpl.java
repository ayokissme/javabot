package tg.bot.crypto.services.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tg.bot.crypto.entities.User;
import tg.bot.crypto.repositories.UserRepository;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(Long chatId, String userName) {
        User user = new User();
        user.setUsername(userName);
        user.setId(chatId);
        userRepository.save(user);
        log.info("Saving user '{}'", chatId);
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.findById(id).isPresent();
    }
}
