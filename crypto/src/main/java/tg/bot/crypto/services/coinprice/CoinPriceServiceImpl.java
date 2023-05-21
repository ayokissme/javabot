package tg.bot.crypto.services.coinprice;

import com.google.gson.Gson;
import java.text.DecimalFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tg.bot.crypto.callbacks.CallbackUtils;
import tg.bot.crypto.callbacks.Currency;
import tg.bot.crypto.keyboards.InlineKeyboardUtils;
import tg.bot.crypto.services.api.parser.ApiParserService;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
@Service
@RequiredArgsConstructor
public class CoinPriceServiceImpl implements CoinPriceService {

    private final ApiParserService apiParserService;

    @Override
    public String getMessage(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Currency currency = CallbackUtils.parseCallback(Currency.class, data, Currency.CALLBACK_END);
        String json = apiParserService.getJsonFromApi(currency);
        return formatMessage(json);
    }

    @Override
    public InlineKeyboardMarkup generateKeyboard(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Currency currency = CallbackUtils.parseCallback(Currency.class, data, Currency.CALLBACK_END);
        var keyboard = InlineKeyboardUtils.currenciesKeyboard(Currency.CALLBACK_END).getKeyboard();
        keyboard.add(0, List.of(refreshButton(currency.callbackName(Currency.CALLBACK_END))));
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    private String formatMessage(String json) {
        CurrencyModel model = apiParserService.getModel(json);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.###");
        String price = decimalFormat.format(model.getPrice());
        String marketCap = model.getMarketCap().toString().equals("0") ? "unknown" : decimalFormat.format(model.getMarketCap());
        return String.format("""
            Символ: %s
            
            Название: %s
            
            Цена: %s $
            
            Изменение за день: %s%%
            
            Капитализация: %s
            """, model.getSymbol(), model.getName(), price, model.getPercentChange24h(), marketCap);
    }

    private InlineKeyboardButton refreshButton(String data) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(Currency.REFRESH.name() + " \uD83D\uDD04");
        button.setCallbackData(data);
        return button;
    }
}
