package tg.bot.crypto.services.handler;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tg.bot.crypto.handlers.ErrorHandler;
import tg.bot.crypto.handlers.ITelegramCommandHandler;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
@Service
@RequiredArgsConstructor
public class CommandHandler implements ICommandHandler {

    private final Map<String, ITelegramCommandHandler> handlers = new HashMap<>();
    private final ErrorHandler errorHandler;

    @Override
    public void addHandler(String commandName, ITelegramCommandHandler command) {
        handlers.put(commandName, command);
    }

    @Override
    public ITelegramCommandHandler handle(String commandName) {
        if (!commandName.startsWith("/")) {
            return errorHandler;
        }
        return handlers.getOrDefault(commandName, errorHandler);
    }
}
