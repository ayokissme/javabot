package tg.bot.crypto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class BotApplication {
	public static void main(String[] args) {
		log.info("Bot started");
		SpringApplication.run(BotApplication.class, args);
	}
}
