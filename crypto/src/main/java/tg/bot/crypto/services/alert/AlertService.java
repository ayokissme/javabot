package tg.bot.crypto.services.alert;

import java.util.List;
import java.util.Optional;
import tg.bot.crypto.entities.Alert;
import tg.bot.crypto.entities.User;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public interface AlertService {

    void save(Alert alert);

    Optional<Alert> findByUserAndPriceIsNull(User user);

    List<Alert> findAllByPriceIsNotNull();

    void deleteAlert(Alert alert);

    List<Alert> findByUser(User user, int pageNumber);

    int count(User user);
}
