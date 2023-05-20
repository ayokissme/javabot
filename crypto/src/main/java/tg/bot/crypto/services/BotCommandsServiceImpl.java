package tg.bot.crypto.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
@Service(BotCommandsServiceImpl.NAME)
public class BotCommandsServiceImpl implements BotCommandsService {
    public static final String NAME = "BotCommandsService";

    private final List<BotCommand> commands = new ArrayList<>();

    @Override
    public void addCommand(BotCommand command) {
        this.commands.add(command);
    }

    @Override
    public List<BotCommand> listCommands() {
        return new ArrayList<>(commands);
    }
}
