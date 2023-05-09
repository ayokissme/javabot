package tg.bot.crypto.config;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String username;

    public TelegramBot(BotConfigProps botProps) {
        super(botProps.getToken());
        this.username = botProps.getUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
