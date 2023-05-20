package tg.bot.crypto.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tg.bot.crypto.TelegramBot;
import tg.bot.crypto.services.handler.CommandHandlerInit;
import tg.bot.crypto.services.BotCommandsService;
import tg.bot.crypto.services.BotCommandsServiceImpl;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
@Slf4j
@Component
@DependsOn({ BotCommandsServiceImpl.NAME, CommandHandlerInit.NAME })
public class BotInitializer {

    public BotInitializer(TelegramBotsApi botApi, TelegramBot bot, BotCommandsService commandsService) throws TelegramApiException {
        botApi.registerBot(bot);
        SetMyCommands setCommands = new SetMyCommands(commandsService.listCommands(), new BotCommandScopeDefault(), null);
        bot.execute(setCommands);
    }
}
