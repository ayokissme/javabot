package tg.bot.crypto.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
@Slf4j
@Component
public class BotInitializer {

    public BotInitializer(TelegramBotsApi botApi, TelegramBot bot) throws TelegramApiException {
        botApi.registerBot(bot);
    }
}
