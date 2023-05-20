package tg.bot.crypto.handlers.keyboards;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
public class ReplyKeyboards {

    public static ReplyKeyboardMarkup getCancelKeyboard() {
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Cancel");
        keyboard.add(row);
        replyMarkup.setKeyboard(keyboard);
        replyMarkup.setResizeKeyboard(true);
        return replyMarkup;
    }
}
