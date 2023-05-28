package tg.bot.crypto.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import tg.bot.crypto.services.FearAndGreedService;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Service
@RequiredArgsConstructor
public class FearAndGreedHandler extends BaseCommandHandler implements ImageCommandHandler {

    public static String COMMAND = "/fearandgreed";

    private final FearAndGreedService fearAndGreedService;

    @Override
    public SendPhoto getPhoto(long chatId) {
        InputFile photo = fearAndGreedService.getPhoto();
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(photo);
        return sendPhoto;
    }

    @Override
    public BotCommand getCommand() {
        return new BotCommand(COMMAND, "Индекс страха и жадности");
    }
}
