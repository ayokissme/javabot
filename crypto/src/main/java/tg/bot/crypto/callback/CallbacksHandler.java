package tg.bot.crypto.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import tg.bot.crypto.currencies.Currency;
import tg.bot.crypto.exceptions.CallbackNotFoundException;
import tg.bot.crypto.services.coinprice.CoinPriceService;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Service
@RequiredArgsConstructor
public class CallbacksHandler implements ICallbacksHandler {

    private final CoinPriceService coinPriceService;

    @Override
    public EditMessageText execute(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();

        String message;
        InlineKeyboardMarkup replyMarkup;
        if (data.endsWith(Currency.CALLBACK_END)) {
            message = coinPriceService.getMessage(data);
            replyMarkup = coinPriceService.generateKeyboard(data);
        } else {
            throw new CallbackNotFoundException(String.format("Callback '%s' not found", data));
        }

        EditMessageText sendMessage = new EditMessageText();
        sendMessage.setText(message);
        sendMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        sendMessage.setReplyMarkup(replyMarkup);
        return sendMessage;
    }
}
