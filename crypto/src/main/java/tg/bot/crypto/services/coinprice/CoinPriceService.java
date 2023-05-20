package tg.bot.crypto.services.coinprice;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
public interface CoinPriceService {
    String getMessage(String data);
    InlineKeyboardMarkup generateKeyboard(String data);
}
