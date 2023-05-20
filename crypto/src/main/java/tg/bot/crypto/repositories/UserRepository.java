package tg.bot.crypto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tg.bot.crypto.entities.User;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
