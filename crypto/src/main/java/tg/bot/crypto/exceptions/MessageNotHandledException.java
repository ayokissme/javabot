package tg.bot.crypto.exceptions;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public class MessageNotHandledException extends RuntimeException {
    public MessageNotHandledException(String message) {
        super(message);
    }
}
