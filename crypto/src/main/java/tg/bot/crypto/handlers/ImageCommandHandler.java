package tg.bot.crypto.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

/**
 * @author nnikolaev
 * @since 28.05.2023
 */
public interface ImageCommandHandler {
    SendPhoto getPhoto(long chatId);
}
