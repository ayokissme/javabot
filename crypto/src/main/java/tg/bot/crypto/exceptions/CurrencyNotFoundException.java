package tg.bot.crypto.exceptions;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public class CurrencyNotFoundException  extends RuntimeException {
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
