package tg.bot.crypto.services;

import java.util.List;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
public interface BotCommandsService {
    void addCommand(BotCommand command);
    List<BotCommand> listCommands();
}
