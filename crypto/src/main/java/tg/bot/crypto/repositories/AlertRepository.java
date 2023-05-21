package tg.bot.crypto.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tg.bot.crypto.entities.Alert;
import tg.bot.crypto.entities.User;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    Optional<Alert> findByUserAndRequiredPriceIsNull(User user);

    List<Alert> findAllByUser(Pageable pageable, User user);

    int countByUser(User user);

    List<Alert> findAllByRequiredPriceIsNotNull();
}
