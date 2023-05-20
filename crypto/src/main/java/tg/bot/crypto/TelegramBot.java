package tg.bot.crypto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tg.bot.crypto.callback.ICallbacksHandler;
import tg.bot.crypto.config.BotConfigProps;
import tg.bot.crypto.exceptions.EmptyMessageException;
import tg.bot.crypto.exceptions.MessageNotHandledException;
import tg.bot.crypto.exceptions.UnknownMessageTypeException;
import tg.bot.crypto.handlers.ITelegramCommandHandler;
import tg.bot.crypto.services.handler.ICommandHandler;
import tg.bot.crypto.services.user.UserService;

/**
 * @author nnikolaev
 * @since 15.05.2023
 */
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final ICommandHandler commandHandler;
    private final ICallbacksHandler callbacksHandler;
    private final UserService userService;

    private final String botUsername;
    private final String channel;

    public TelegramBot(BotConfigProps botProps, ICommandHandler commandHandler, ICallbacksHandler callbacksHandler, UserService userService) {
        super(botProps.getToken());
        this.botUsername = botProps.getUsername();
        this.channel = botProps.getChannel();
        this.commandHandler = commandHandler;
        this.callbacksHandler = callbacksHandler;
        this.userService = userService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = message(update).getChatId();
        String userName = message(update).getFrom().getUserName();
        String text = message(update).getText();
        log.info("Received message from chat (id='{}'): '{}'", chatId, text);

        if (!userService.exists(chatId)) {
            userService.save(chatId, userName);
        }

        if (channel != null && isNotSubscribedOnChannel(chatId)) {
            log.info("User '{}' is not subscribed to the '{}'", chatId, channel);
            sendMessage(notSubscribedMessage(chatId));
            return;
        }

        if (update.hasMessage()) {
            if (!update.getMessage().hasText()) {
                throw new EmptyMessageException("Message does not contain any text");
            }

            String commandName = update.getMessage().getText();
            ITelegramCommandHandler handler = commandHandler.handle(commandName);
            handler.execute(update);
            sendMessage(handler.getMessage(chatId));
        } else if (update.hasCallbackQuery()) {
            EditMessageText editMessageText = callbacksHandler.execute(update.getCallbackQuery());
            editMessage(editMessageText, update.getCallbackQuery().getId());
        } else {
            throw new MessageNotHandledException("Message type is not supported");
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
            log.info("Message sent to chat (id='{}'): '{}'\n", message.getChatId(), message.getText());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessage(EditMessageText editMessageText, String id) {
        try {
            execute(editMessageText);
            log.info("Message in chat (id='{}') was updated", editMessageText.getChatId());
        } catch (TelegramApiException e) {
            sendUpToDateNotification(id);
            log.error("Message in chat (id='{}') is up to date", editMessageText.getChatId());
        }
    }

    private void sendUpToDateNotification(String id) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(id);
        answer.setText("The currency is up to date");
        answer.setCacheTime(2);
        try {
            execute(answer);
        } catch (TelegramApiException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean isNotSubscribedOnChannel(long chatId) {
        GetChatMember getChatMember = new GetChatMember(channel, chatId);
        try {
            ChatMember chatMember = execute(getChatMember);
            return chatMember.getStatus().equals("left");
        } catch (TelegramApiException e) {
            log.error("Failed to verify subscription for chatId: '{}'", chatId);
            return true;
        }
    }

    private SendMessage notSubscribedMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setText(String.format("To use the bot, you need to subscribe to this channel: %s", channel));
        message.setChatId(chatId);
        return message;
    }

    private Message message(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage();
        } else if (update.hasMessage()) {
            return update.getMessage();
        } else {
            throw new UnknownMessageTypeException("Message type is not supported");
        }
    }
}
