package tg.bot.crypto.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import tg.bot.crypto.callbacks.Currency;
import tg.bot.crypto.keyboards.InlineKeyboardUtils;

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
        message.setText("Выберите любую валюту из списка ниже, чтобы узнать актуальную цену валюты");
        message.setChatId(chatId);
        message.setReplyMarkup(generateReplyKeyboard());
        return message;
    }

    @Override
    public InlineKeyboardMarkup generateReplyKeyboard() {
        return InlineKeyboardUtils.currenciesKeyboard(Currency.CALLBACK_END);
    }

    @Override
    public BotCommand getCommand() {
        return new BotCommand(COMMAND, "Узнать текущий курс валюты");
    }
}
