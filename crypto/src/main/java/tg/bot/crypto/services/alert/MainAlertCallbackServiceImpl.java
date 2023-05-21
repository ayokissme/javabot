package tg.bot.crypto.services.alert;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tg.bot.crypto.callbacks.CallbackUtils;
import tg.bot.crypto.entities.Alert;
import tg.bot.crypto.entities.User;
import tg.bot.crypto.keyboards.InlineKeyboardUtils;
import tg.bot.crypto.services.user.UserService;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Service
@RequiredArgsConstructor
public class MainAlertCallbackServiceImpl extends AlertCallbackBaseService {

    private final AlertService alertService;
    private final UserService userService;

    private User user;
    private List<Alert> alertsOnPage;
    private int alertsForUserCount;

    @Override
    public String getMessage(CallbackQuery callbackQuery) {
        return isAddAlert(callbackQuery) ? "Выберите одну из предложенных валют" : listAlertsMessage(callbackQuery);
    }

    @Override
    public InlineKeyboardMarkup generateKeyboard(CallbackQuery callbackQuery) {
        return isAddAlert(callbackQuery) ?
            InlineKeyboardUtils.currenciesKeyboard(AddAlertCallbackService.CALLBACK_END) :
            listAlertsKeyboard(callbackQuery);
    }

    @Override
    public void execute(CallbackQuery callbackQuery, String data) {
        if (CallbackUtils.isForDelete(data)) {
            int pageNumber = CallbackUtils.parseListAlertPageNumber(data);
            alertsOnPage = alertService.findByUser(user, pageNumber);
            alertService.deleteAlert(alertsOnPage.get(0));
        }
    }

    private InlineKeyboardMarkup listAlertsKeyboard(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        int pageNumber = CallbackUtils.parseListAlertPageNumber(data);

        if (CallbackUtils.isForDelete(data) && alertsForUserCount == pageNumber) {
            pageNumber--;
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        List<List<InlineKeyboardButton>> listOfButtons = new ArrayList<>();

        if (!alertsOnPage.isEmpty()) {
            listOfButtons.add(List.of(getDeleteButton(pageNumber)));
        } else {
            return InlineKeyboardUtils.alertsMainKeyboard();
        }

        if (pageNumber > 0) {
            buttons.add(getButton("<<", pageNumber - 1));
        }
        if (alertsForUserCount > pageNumber + 1) {
            buttons.add(getButton(">>", pageNumber + 1));
        }
        listOfButtons.add(buttons);
        inlineKeyboardMarkup.setKeyboard(listOfButtons);
        return inlineKeyboardMarkup;
    }

    private String listAlertsMessage(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getFrom().getId();
        user = userService.getUser(chatId).orElseThrow();

        String data = callbackQuery.getData();
        int pageNumber = CallbackUtils.parseListAlertPageNumber(data);

        alertsForUserCount = alertService.count(user);
        if (CallbackUtils.isForDelete(data) && alertsForUserCount == pageNumber) {
            pageNumber--;
        }

        alertsOnPage = alertService.findByUser(user, pageNumber);
        if (alertsOnPage.isEmpty()) {
            return "Список уведомлений пуст";
        }

        Alert alert = alertsOnPage.get(0);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.###");
        String price = decimalFormat.format(alert.getRequiredPrice());
        return String.format("""
            <b>Символ</b>: %s
                        
            <b>Цена</b>: $%s
            """, alert.getCurrency(), price);
    }

    private InlineKeyboardButton getButton(String navSymbol, int pageNumber) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(navSymbol);
        String callbackData = tg.bot.crypto.callbacks.Alert.LIST.callbackEndWithId(pageNumber);
        button.setCallbackData(callbackData);
        return button;
    }

    private InlineKeyboardButton getDeleteButton(int pageNumber) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("\uD83D\uDDD1 Удалить");
        String callbackData = tg.bot.crypto.callbacks.Alert.LIST.callbackEndForDeletion(pageNumber);
        button.setCallbackData(callbackData);
        return button;
    }
}
