package tg.bot.crypto.callback;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import tg.bot.crypto.callbacks.Alert;
import tg.bot.crypto.callbacks.Currency;
import tg.bot.crypto.exceptions.CallbackNotFoundException;
import tg.bot.crypto.services.CallbackService;
import tg.bot.crypto.services.alert.AddAlertCallbackService;
import tg.bot.crypto.services.alert.MainAlertCallbackServiceImpl;
import tg.bot.crypto.services.coinprice.CoinPriceServiceImpl;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Service
@RequiredArgsConstructor
public class CallbacksHandler implements ICallbacksHandler {

    private final CoinPriceServiceImpl coinPriceService;
    private final MainAlertCallbackServiceImpl alertService;
    private final AddAlertCallbackService addAlertCallbackService;

    private final Map<String, CallbackService> callbackHandler = new HashMap<>();

    @PostConstruct
    public void init() {
        callbackHandler.put(Currency.CALLBACK_END, coinPriceService);
        callbackHandler.put(Alert.CALLBACK_END, alertService);
        callbackHandler.put(AddAlertCallbackService.CALLBACK_END, addAlertCallbackService);
    }

    @Override
    public EditMessageText execute(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        String endString = data.substring(data.indexOf("_"));

        String message;
        InlineKeyboardMarkup replyMarkup;

        CallbackService callbackService;
        if (callbackHandler.containsKey(endString)) {
            callbackService = callbackHandler.get(endString);
        } else {
            throw new CallbackNotFoundException(String.format("Callback '%s' not found", data));
        }

        callbackService.execute(callbackQuery, data);
        message = callbackService.getMessage(callbackQuery);
        replyMarkup = callbackService.generateKeyboard(callbackQuery);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText(message);
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setChatId(callbackQuery.getMessage().getChatId());
        editMessageText.setReplyMarkup(replyMarkup);
        editMessageText.setParseMode(ParseMode.HTML);

        return editMessageText;
    }
}
