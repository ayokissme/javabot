package tg.bot.crypto.services.coinprice;

import com.google.gson.Gson;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tg.bot.crypto.config.ApiProperties;
import tg.bot.crypto.currencies.Currency;
import tg.bot.crypto.handlers.CoinPriceHandler;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
@Service
@RequiredArgsConstructor
public class CoinPriceServiceImpl implements CoinPriceService {

    private final ApiProperties apiProperties;
    private final CoinPriceHandler coinPriceHandler;

    @Override
    public String getMessage(String data) {
        Currency currency = Currency.parseCurrency(data);
        String json = getJsonFromApi(currency);
        return formatMessage(json);
    }

    @Override
    public InlineKeyboardMarkup generateKeyboard(String data) {
        Currency currency = Currency.parseCurrency(data);
        List<List<InlineKeyboardButton>> keyboard = coinPriceHandler.generateReplyKeyboard().getKeyboard();
        keyboard.add(0, List.of(refreshButton(currency.callbackName())));
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    private String formatMessage(String json) {
        Gson gson = new Gson();
        CurrencyModel model = gson.fromJson(json, CurrencyModel.class);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.###");
        String price = decimalFormat.format(model.getPrice());
        String marketCap = model.getMarketCap().toString().equals("0") ? "unknown" : decimalFormat.format(model.getMarketCap());
        return String.format("""
            Symbol: %s
            
            Name: %s
            
            Price: %s $
            
            Percent Change 24h: %s%%
            
            Marketcap: %s
            """, model.getSymbol(), model.getName(), price, model.getPercentChange24h(), marketCap);
    }

    private String getJsonFromApi(Currency currency) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String url = apiProperties.getCoinpaprika() + currency.getId();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return responseEntity.getBody();
    }

    private InlineKeyboardButton refreshButton(String data) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(Currency.REFRESH.name() + " \uD83D\uDD04");
        button.setCallbackData(data);
        return button;
    }
}
