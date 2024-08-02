package ru.spk.donor_bot.manager.start;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.spk.donor_bot.FileManager;
import ru.spk.donor_bot.factory.AnswerMethodFactory;
import ru.spk.donor_bot.factory.KeyboardFactory;
import ru.spk.donor_bot.manager.AbstractManager;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static ru.spk.donor_bot.data.CallbackData.INFO;

@Component
public class StartManager extends AbstractManager {
    private final AnswerMethodFactory answerMethodFactory;
    private final KeyboardFactory keyboardFactory;
    private final String folderName = "dataFiles";
    private final String titleInformation = "title-information.txt";

    public StartManager(AnswerMethodFactory answerMethodFactory,
                        KeyboardFactory keyboardFactory,
                        TelegramBot telegramBot) {
        super(telegramBot);
        this.answerMethodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
    }


    /**
     * Метод для отправки пользователю основного меню бота
     * @param update - объект класса Update
     */
    public void answerCommand(Update update) {
        firstGrittings(update);
    }

    /**
     * Метод для отправки пользователю основного меню бота
     *
     * @param callbackQuery - объект класса CallbackQuery
     */
    @Override
    public void answerCallbackQuery(CallbackQuery callbackQuery) {
        firstGrittings(callbackQuery);
    }

    //приветствие update

    /**
     * Метод для отправки пользователю основного меню бота
     *
     * @param update - объект класса Update
     */
    private void firstGrittings(Update update) {
        Path filePath = Paths.get(folderName, titleInformation);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardFactory.getInlineKeyboard(
                List.of("информация"),
                List.of(1),
                List.of(INFO)
        );
        SendMessage sendMessage = answerMethodFactory.getSendMessage(update.message().chat().id(), message, inlineKeyboardMarkup);

        telegramBot.execute(sendMessage);
    }

    //приветствие callbackquery

    /**
     * Метод для отправки пользователю основного меню бота
     *
     * @param callbackQuery - объект класса CallbackQuery
     */
    private void firstGrittings(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, titleInformation);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboardFactory.getInlineKeyboard(
                List.of("информация"),
                List.of(1),
                List.of(INFO)
        );
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(callbackQuery, message, inlineKeyboardMarkup);

        telegramBot.execute(sendMessage);
    }
}
