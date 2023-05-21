package tg.bot.crypto.keyboards;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tg.bot.crypto.callbacks.Alert;
import tg.bot.crypto.callbacks.Currency;

/**
 * @author nnikolaev
 * @since 17.05.2023
 */
public class InlineKeyboardUtils {

    public static final String CANCEL = "cancel_alert";

    public static InlineKeyboardMarkup currenciesKeyboard(String callbackEnd) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<List<Currency>> keyboardRows = Currency.keyboardCurrencyRows();
        for (List<Currency> row : keyboardRows) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            for (Currency currency : row) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(currency.name());
                button.setCallbackData(currency.callbackName(callbackEnd));
                buttons.add(button);
            }
            keyboard.add(buttons);
        }
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup alertsMainKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (Alert alert : Alert.values()) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(alert.getText());
            button.setCallbackData(alert.callbackName(Alert.CALLBACK_END));
            buttons.add(List.of(button));
        }
        keyboardMarkup.setKeyboard(buttons);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup cancelAlertKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("❌ Отменить");
        button.setCallbackData(CANCEL);
        keyboardMarkup.setKeyboard(List.of(List.of(button)));

        return keyboardMarkup;
    }
}
