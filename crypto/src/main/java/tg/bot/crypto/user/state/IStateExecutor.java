package tg.bot.crypto.user.state;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import tg.bot.crypto.entities.User;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public interface IStateExecutor {
    SendMessage executeAndGetMessage(Update update, User user);
    EditMessageText cancelAndGetMessage(CallbackQuery update, User user);
    void cleanUserAndAlert(User user);
}
