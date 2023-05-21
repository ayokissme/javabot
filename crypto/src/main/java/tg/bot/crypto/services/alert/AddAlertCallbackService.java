package tg.bot.crypto.services.alert;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import tg.bot.crypto.callbacks.CallbackUtils;
import tg.bot.crypto.callbacks.Currency;
import tg.bot.crypto.entities.Alert;
import tg.bot.crypto.entities.User;
import tg.bot.crypto.entities.UserLastState;
import tg.bot.crypto.exceptions.UserNotFoundException;
import tg.bot.crypto.keyboards.InlineKeyboardUtils;
import tg.bot.crypto.repositories.AlertRepository;
import tg.bot.crypto.services.user.UserService;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AddAlertCallbackService extends AlertCallbackBaseService {

    private final UserService userService;
    private final AlertRepository alertRepository;

    public static final String CALLBACK_END = "_add_alert";

    @Override
    @Transactional
    public void execute(CallbackQuery callbackQuery, String data) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Optional<User> optionalUser = userService.getUser(chatId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with if='%s' not found", chatId));
        }
        User user = optionalUser.get();

        Currency currency = CallbackUtils.parseCallback(Currency.class, data, CALLBACK_END);
        Alert alert = new Alert();
        alert.setUser(user);
        alert.setCurrency(currency);
        Alert savedAlert = alertRepository.save(alert);
        log.info("Alert saved: {}", savedAlert.getId());

        user.setLastState(UserLastState.ADD_ALERT);
        userService.update(user);
        log.info("User updated last state: {}", UserLastState.ADD_ALERT);
    }

    @Override
    public String getMessage(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Currency currency = CallbackUtils.parseCallback(Currency.class, data, CALLBACK_END);
        return String.format("""
            Вы выбрали %s. Введите цену, при достижении которой бот должен оповестить Вас
            """, currency.name());
    }

    @Override
    public InlineKeyboardMarkup generateKeyboard(CallbackQuery data) {
        return InlineKeyboardUtils.cancelAlertKeyboard();
    }
}
