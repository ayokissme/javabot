package tg.bot.crypto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tg.bot.crypto")
public class BotConfigProps {
    private String token;
    private String username;
}
