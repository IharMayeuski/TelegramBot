import org.glassfish.jersey.internal.util.collection.ImmutableMultivaluedMap;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static org.telegram.telegrambots.logging.BotLogger.log;

public class Bot extends TelegramLongPollingBot {

    /**
     * Метод для приема сообщений.
     *
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

    /**
     * Метод для настройки сообщения и его отправки.
     *
     * @param chatId id чата
     * @param s      Строка, которую необходимот отправить в качестве сообщения.
     */
    private synchronized void sendMsg(String chatId, String s) {
        String answer = getAnswer(s);

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(answer);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    private String getAnswer(String question) {
        Map<String, String> variants = new HashMap<>();
        variants.put("/Привет", "Привет");
        variants.put("/Как дела?", "Хорошо. А у тебя?");
        variants.put("/Пока", "До завтра!");
        return variants.getOrDefault(question, "Данная команда не определена ни разу ;) ");
    }


    /**
     * Метод возвращает имя бота, указанное при регистрации.
     *
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "botborodachbot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     *
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return "1074818589:AAE9Ye-UT4DDYAu2UGofcFswnwk8sE79hKc";
    }
}