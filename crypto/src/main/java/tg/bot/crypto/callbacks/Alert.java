package tg.bot.crypto.callbacks;

import lombok.Getter;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public enum Alert implements ICallback {

    ADD("➕ Добавить уведомление"),
    LIST("\uD83D\uDDC2 Список уведомлений");

    public static final String CALLBACK_END = "_alert";

    @Getter
    private final String text;

    Alert(String text) {
        this.text = text;
    }

    @Override
    public String callbackName(String callbackEnd) {
        return this.name() + callbackEnd;
    }

    public String callbackEndWithId(int id) {
        return this.name() + String.format(".%s.", id) + CALLBACK_END;
    }

    public String callbackEndForDeletion(int id) {
        return this.name() + String.format(".%s==DELETE.", id) + CALLBACK_END;
    }
}
