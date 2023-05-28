package tg.bot.crypto.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

/**
 * @author nnikolaev
 * @since 28.05.2023
 */
@Service
@RequiredArgsConstructor
public class HelpHandler extends BaseCommandHandler {

    public static String COMMAND = "/help";

    @Override
    public SendMessage getMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setText(String.format("""
            ”Курс криптовалюты” - %s - покажет текущий курс выбранной криптовалюты.
            ”Оповещение”  - %s - оповещение о цене позволяет следить за изменениями на рынке.
            ”Индекс страха и жадности” - %s - индикатор, показывающий настроения инвесторов, получаемый с сайта<a href='https://alternative.me/crypto/fear-and-greed-index/'>alternative</a>.
            """, CoinPriceHandler.COMMAND, AlertHandler.COMMAND, FearAndGreedHandler.COMMAND));
        message.setChatId(chatId);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(generateReplyKeyboard());
        return message;
    }

    @Override
    public BotCommand getCommand() {
        return new BotCommand(COMMAND, "Помощь");
    }
}
