package tg.bot.crypto.services.coinprice;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Data
public class CurrencyModel {

    private String symbol;
    private String name;
    private Quotes quotes;

    @Data
    public static class Quotes {
        private USD USD;

        @Data
        public static class USD {
            private float price;

            @SerializedName("market_cap")
            private BigDecimal marketCap;

            @SerializedName("percent_change_24h")
            private float percentChange24h;
        }
    }

    public float getPrice() {
        return quotes.getUSD().getPrice();
    }

    public BigDecimal getMarketCap() {
        return quotes.getUSD().getMarketCap();
    }

    public float getPercentChange24h() {
        return quotes.getUSD().getPercentChange24h();
    }
}
