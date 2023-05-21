package tg.bot.crypto.services;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public interface CallbackService {
    default void execute(CallbackQuery callbackQuery, String data) {}
    String getMessage(CallbackQuery data);
    InlineKeyboardMarkup generateKeyboard(CallbackQuery data);
}
