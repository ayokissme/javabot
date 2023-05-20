package tg.bot.crypto.handlers;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Service
public class SettingsHandler extends BaseCommandHandler {

    public static String COMMAND = "/settings";

    @Override
    public SendMessage getMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setText("Settings?");
        message.setChatId(chatId);
        return message;
    }

    @Override
    public BotCommand getCommand() {
        return new BotCommand(COMMAND, "Settings");
    }
}
