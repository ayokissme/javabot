package tg.bot.crypto.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
@Service
@RequiredArgsConstructor
public class StartHandler extends BaseCommandHandler {

    public static String COMMAND = "/start";

    @Override
    public SendMessage getMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setText("Привет! Я CryptoBot. С моей помощью, ты сможешь быстрее найти информацию об интересующей тебя криптовалюте.");
        message.setChatId(chatId);
        return message;
    }

    @Override
    public BotCommand getCommand() {
        return null;
    }
}
