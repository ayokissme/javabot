package tg.bot.crypto.services.api.parser;

import com.google.gson.Gson;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tg.bot.crypto.callbacks.Currency;
import tg.bot.crypto.config.ApiProperties;
import tg.bot.crypto.services.coinprice.CurrencyModel;

/**
 * @author nnikolaev
 * @since 21.05.2023
 */
@Service
@RequiredArgsConstructor
public class ApiParserService {

    private final ApiProperties apiProperties;

    public String getJsonFromApi(Currency currency) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String url = apiProperties.getCoinpaprika() + currency.getId();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return responseEntity.getBody();
    }

    public CurrencyModel getModel(String json) {
        return new Gson().fromJson(json, CurrencyModel.class);
    }
}
