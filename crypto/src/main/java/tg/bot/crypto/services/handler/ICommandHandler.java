package tg.bot.crypto.services.handler;

import tg.bot.crypto.handlers.ITelegramCommandHandler;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
public interface ICommandHandler {
    void addHandler(String commandName, ITelegramCommandHandler command);
    ITelegramCommandHandler handle(String commandName);
}
