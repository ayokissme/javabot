package tg.bot.crypto.exceptions;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public class EmptyMessageException extends RuntimeException {
    public EmptyMessageException(String message) {
        super(message);
    }
}
