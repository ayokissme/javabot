package tg.bot.crypto.callback;


import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public interface ICallbacksHandler {
    EditMessageText execute(CallbackQuery callbackQuery);
}
