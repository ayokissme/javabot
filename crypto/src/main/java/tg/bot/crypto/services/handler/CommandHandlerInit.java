package tg.bot.crypto.services.handler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tg.bot.crypto.handlers.AlertHandler;
import tg.bot.crypto.handlers.CoinPriceHandler;
import tg.bot.crypto.handlers.SettingsHandler;
import tg.bot.crypto.handlers.StartHandler;
import tg.bot.crypto.services.BotCommandsService;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
@Component(CommandHandlerInit.NAME)
@RequiredArgsConstructor
public class CommandHandlerInit {

    public static final String NAME = "commandInitializer";

    private final BotCommandsService botCommandsService;
    private final ICommandHandler commandHandler;

    private final StartHandler startHandler;
    private final CoinPriceHandler coinPriceHandler;
    private final SettingsHandler settingsHandler;
    private final AlertHandler alertHandler;

    @PostConstruct
    public void init() {
        botCommandsService.addCommand(coinPriceHandler.getCommand());
        botCommandsService.addCommand(alertHandler.getCommand());
        botCommandsService.addCommand(settingsHandler.getCommand());

        commandHandler.addHandler(StartHandler.COMMAND, startHandler);
        commandHandler.addHandler(CoinPriceHandler.COMMAND, coinPriceHandler);
        commandHandler.addHandler(AlertHandler.COMMAND, alertHandler);
        commandHandler.addHandler(SettingsHandler.COMMAND, settingsHandler);
    }
}
