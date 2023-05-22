package tg.bot.crypto.services.alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import tg.bot.crypto.TelegramBot;
import tg.bot.crypto.callbacks.Currency;
import tg.bot.crypto.entities.Alert;
import tg.bot.crypto.services.api.parser.ApiParserService;
import tg.bot.crypto.services.coinprice.CurrencyModel;

/**
 * @author nnikolaev
 * @since 21.05.2023
 */
@Service
@RequiredArgsConstructor
public class AlertScheduleService {

    private final TelegramBot bot;
    private final AlertService alertService;
    private final ApiParserService apiParserService;

    @Scheduled(fixedRate = 120000)
    public void checkAlerts() {
        List<Alert> allAlerts = alertService.findAllByPriceIsNotNull();
        Set<Currency> currencies = allAlerts.stream().map(Alert::getCurrency).collect(Collectors.toSet());
        List<CurrencyModel> models = new ArrayList<>();
        for (Currency currency : currencies) {
            String json = apiParserService.getJsonFromApi(currency);
            CurrencyModel model = apiParserService.getModel(json);
            models.add(model);
        }

        for (CurrencyModel model : models) {
            String symbol = model.getSymbol();
            for (Alert alert : allAlerts) {
                Currency currency = alert.getCurrency();
                if (symbol.equals(currency.name())) {
                    boolean priceDecreased = model.getPrice() < alert.getRequiredPrice() && alert.getRequiredPrice() < alert.getOnSetPrice();
                    boolean priceIncreased = model.getPrice() > alert.getRequiredPrice() && alert.getRequiredPrice() > alert.getOnSetPrice();
                    if (priceIncreased || priceDecreased) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText(String.format("Цена для <b>%s</b> достигла <b>%s</b>", alert.getCurrency(), alert.getRequiredPrice()));
                        sendMessage.setParseMode(ParseMode.HTML);
                        sendMessage.setChatId(alert.getUser().getId());
                        bot.sendMessage(sendMessage);
                    }
                }
            }
        }
    }
}
