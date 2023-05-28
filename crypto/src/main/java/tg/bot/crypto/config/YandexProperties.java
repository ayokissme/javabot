package tg.bot.crypto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "yc")
public class YandexProperties {
    private String bucket;
    private String accessKey;
}
