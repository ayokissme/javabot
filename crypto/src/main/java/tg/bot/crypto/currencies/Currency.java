package tg.bot.crypto.currencies;

import java.util.List;
import lombok.Getter;
import tg.bot.crypto.exceptions.CurrencyNotFoundException;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public enum Currency {

    REFRESH("refresh"),
    BTC("btc-bitcoin"),
    ETH("eth-ethereum"),
    TON("ton-tontoken"),
    XMR("xmr-monero"),
    BNB("bnb-binance-coin"),
    DASH("dash-dash"),
    BCH("bch-bitcoin-cash"),
    DOGE("doge-dogecoin"),
    LTC("ltc-litecoin");

    public static final String CALLBACK_END = "_coinprice";

    @Getter
    private final String id;

    Currency(String id) {
        this.id = id;
    }

    public String callbackName() {
        return this.name() + CALLBACK_END;
    }

    public static Currency parseCurrency(String data) {
        String currencyString = data.replace(CALLBACK_END, "");
        try {
            return Currency.valueOf(currencyString);
        } catch (IllegalArgumentException e) {
            throw new CurrencyNotFoundException(String.format("Currency type '%s' id not supported", currencyString));
        }
    }

    public static List<List<Currency>> keyboardCurrencyRows() {
        return List.of(
            List.of(BTC, ETH, TON),
            List.of(DASH, XMR, BNB),
            List.of(BCH, LTC, DOGE)
        );
    }
}
