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
public class ErrorHandler extends BaseCommandHandler {

    @Override
    public BotCommand getCommand() {
        return null;
    }

    @Override
    public SendMessage getMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setText("Команда не найдена!");
        message.setChatId(chatId);
        return message;
    }
}
