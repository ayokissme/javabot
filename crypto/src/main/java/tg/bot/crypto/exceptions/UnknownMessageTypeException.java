package tg.bot.crypto.exceptions;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public class UnknownMessageTypeException extends RuntimeException {
    public UnknownMessageTypeException(String message) {
        super(message);
    }
}
