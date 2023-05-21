package tg.bot.crypto.services.alert;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import tg.bot.crypto.entities.Alert;
import tg.bot.crypto.entities.User;
import tg.bot.crypto.repositories.AlertRepository;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Repository
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    @Override
    public void save(Alert alert) {
        alertRepository.save(alert);
    }

    public Optional<Alert> findByUserAndPriceIsNull(User user) {
        return alertRepository.findByUserAndRequiredPriceIsNull(user);
    }

    @Override
    public List<Alert> findAllByPriceIsNotNull() {
        return alertRepository.findAllByRequiredPriceIsNotNull();
    }

    @Override
    public void deleteAlert(Alert alert) {
        alertRepository.delete(alert);
    }

    @Override
    public List<Alert> findByUser(User user, int pageNumber) {
        return alertRepository.findAllByUser(PageRequest.of(pageNumber, 1), user);
    }

    @Override
    public int count(User user) {
        return alertRepository.countByUser(user);
    }
}
