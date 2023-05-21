package tg.bot.crypto.exceptions;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public class NotSupportedLastState extends RuntimeException {
    public NotSupportedLastState(String message) {
        super(message);
    }
}
