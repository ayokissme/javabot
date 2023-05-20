package tg.bot.crypto.exceptions;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public class CallbackNotFoundException extends RuntimeException {
    public CallbackNotFoundException(String message) {
        super(message);
    }
}
