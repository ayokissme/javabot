package tg.bot.crypto.callbacks;

import tg.bot.crypto.exceptions.CallbackNotFoundException;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
public class CallbackUtils {

    public static <T extends Enum<T> & ICallback> T parseCallback(Class<T> callbackType, String data, String callbackEnd) {
        String callback = data.replace(callbackEnd, "");
        try {
            return Enum.valueOf(callbackType, callback);
        } catch (IllegalArgumentException e) {
            throw new CallbackNotFoundException(String.format("Callback '%s' is not supported", callback));
        }
    }

    public static int parseListAlertPageNumber(String data) {
        if (!data.contains(".")) {
            return 0;
        }

        String[] s = data.split("\\.");
        return Integer.parseInt(s[1].split("==")[0]);
    }

    public static boolean isForDelete(String data) {
        return data.matches(".*\\.[0-9]+==DELETE\\..*");
    }
}
