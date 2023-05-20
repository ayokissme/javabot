package tg.bot.crypto.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
public interface ITelegramCommandHandler {
    void execute(Update update);
    SendMessage getMessage(Long chatId);
    ReplyKeyboard generateReplyKeyboard();
    BotCommand getCommand();
}
