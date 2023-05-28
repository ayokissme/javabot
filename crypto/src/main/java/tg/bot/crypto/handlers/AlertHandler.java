package tg.bot.crypto.handlers;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import tg.bot.crypto.keyboards.InlineKeyboardUtils;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Service
public class AlertHandler extends BaseCommandHandler {

    public static String COMMAND = "/alert";

    @Override
    public SendMessage getMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setText("Вы можете создать оповещение об изменении курса выбранной криптовалюты или удалить существующее");
        message.setChatId(chatId);
        message.setReplyMarkup(generateReplyKeyboard());
        return message;
    }

    @Override
    public ReplyKeyboard generateReplyKeyboard() {
        return InlineKeyboardUtils.alertsMainKeyboard();
    }

    @Override
    public BotCommand getCommand() {
        return new BotCommand(COMMAND, "Оповещение");
    }
}
