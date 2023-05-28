package tg.bot.crypto.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
public abstract class BaseCommandHandler implements ITelegramCommandHandler {

    @Override
    public ReplyKeyboard generateReplyKeyboard() {
        return null;
    }

    @Override
    public void execute(Update update) {}

    @Override
    public SendMessage getMessage(Long chatId) {
        return null;
    }
}
