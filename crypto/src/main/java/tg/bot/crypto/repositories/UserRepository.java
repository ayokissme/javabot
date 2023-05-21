package tg.bot.crypto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.bot.crypto.entities.User;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
public interface UserRepository extends JpaRepository<User, Long> {}
