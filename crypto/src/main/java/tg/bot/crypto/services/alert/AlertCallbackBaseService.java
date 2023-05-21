package tg.bot.crypto.services.alert;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import tg.bot.crypto.callbacks.Alert;
import tg.bot.crypto.callbacks.CallbackUtils;
import tg.bot.crypto.services.CallbackService;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public abstract class AlertCallbackBaseService implements CallbackService {
    @Override
    public InlineKeyboardMarkup generateKeyboard(CallbackQuery data) {
        return null;
    }

    protected boolean isAddAlert(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        if (data.matches(".*\\.[0-9]+.*\\..*")) {
            String[] dataSplit = data.split("\\.");
            data = dataSplit[0] + dataSplit[2];
        }
        Alert alert = CallbackUtils.parseCallback(Alert.class, data, Alert.CALLBACK_END);
        return alert.equals(Alert.ADD);
    }
}
