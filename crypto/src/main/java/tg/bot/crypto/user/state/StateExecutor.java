package tg.bot.crypto.user.state;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import tg.bot.crypto.entities.Alert;
import tg.bot.crypto.entities.User;
import tg.bot.crypto.entities.UserLastState;
import tg.bot.crypto.exceptions.NotSupportedLastState;
import tg.bot.crypto.keyboards.InlineKeyboardUtils;
import tg.bot.crypto.services.alert.AlertService;
import tg.bot.crypto.services.api.parser.ApiParserService;
import tg.bot.crypto.services.user.UserService;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StateExecutor implements IStateExecutor {

    private final UserService userService;
    private final AlertService alertService;
    private final ApiParserService apiParserService;

    @Override
    @Transactional
    public SendMessage executeAndGetMessage(Update update, User user) {
        UserLastState lastState = user.getLastState();
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (lastState.equals(UserLastState.ADD_ALERT)) {
            String text = update.getMessage().getText();
            if (isDigit(text)) {
                Alert alert = alertService.findByUserAndPriceIsNull(user).orElseThrow();
                alert.setRequiredPrice(Float.parseFloat(text));
                float price = apiParserService.getModel(apiParserService.getJsonFromApi(alert.getCurrency())).getPrice();
                alert.setOnSetPrice(price);
                alertService.save(alert);

                user.setLastState(null);
                userService.update(user);
                log.info("Last state for user {} set: {}", user.getId(), null);

                sendMessage.setText(String.format("Оповещение о достижении цены <b>%s</b> для <b>%s</b> успешно установлено",
                    alert.getRequiredPrice(),
                    alert.getCurrency()));
                sendMessage.setReplyMarkup(InlineKeyboardUtils.alertsMainKeyboard());
                sendMessage.setParseMode(ParseMode.HTML);
                log.info("Alert was set: {}", alert);
            } else {
                log.info("Incorrect format for alert's price: {}", text);
                sendMessage.setText("Введённое сообщение не является числом");
                sendMessage.setReplyMarkup(InlineKeyboardUtils.cancelAlertKeyboard());
            }
            return sendMessage;
        } else {
            throw new NotSupportedLastState(String.format("'%s' is not supported", lastState));
        }
    }

    @Override
    public void cleanUserAndAlert(User user) {
        Alert alert = alertService.findByUserAndPriceIsNull(user).orElseThrow();
        alertService.deleteAlert(alert);
        user.setLastState(null);
        userService.update(user);
    }

    @Override
    public EditMessageText cancelAndGetMessage(CallbackQuery callbackQuery, User user) {
        cleanUserAndAlert(user);

        EditMessageText sendMessage = new EditMessageText();
        sendMessage.setText("Транзакция была отменена");
        sendMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());

        return sendMessage;
    }

    private boolean isDigit(String string) {
        try {
            Float.parseFloat(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
