package tg.bot.crypto.handlers;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tg.bot.crypto.currencies.Currency;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
@Service
@RequiredArgsConstructor
public class CoinPriceHandler extends BaseCommandHandler {

    public static String COMMAND = "/coinprice";

    @Override
    public void execute(Update update) {
    }

    @Override
    public SendMessage getMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setText("Select any currency from the list below to see the current price");
        message.setChatId(chatId);
        message.setReplyMarkup(generateReplyKeyboard());
        return message;
    }

    @Override
    public InlineKeyboardMarkup generateReplyKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<List<Currency>> keyboardRows = Currency.keyboardCurrencyRows();
        for (List<Currency> row : keyboardRows) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            for (Currency currency : row) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(currency.name());
                button.setCallbackData(currency.callbackName());
                buttons.add(button);
            }
            keyboard.add(buttons);
        }
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    @Override
    public BotCommand getCommand() {
        return new BotCommand(COMMAND, "Find out currency price");
    }
}
