package tg.bot.crypto.callbacks;

import java.util.List;
import lombok.Getter;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public enum Currency implements ICallback {

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

    @Override
    public String callbackName(String callbackEnd) {
        return this.name() + callbackEnd;
    }

    public static List<List<Currency>> keyboardCurrencyRows() {
        return List.of(
            List.of(BTC, ETH, TON),
            List.of(DASH, XMR, BNB),
            List.of(BCH, LTC, DOGE)
        );
    }
}
