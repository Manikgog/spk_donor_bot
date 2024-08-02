package ru.spk.donor_bot.manager.info;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
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

import static ru.spk.donor_bot.data.CallbackData.*;

@Component
public class InfoForPlazmaDonors extends AbstractManager {
    private final AnswerMethodFactory answerMethodFactory;
    private final KeyboardFactory keyboardFactory;
    private final String folderName = "dataFiles";
    private final String whereDonatedPlazmaIsUsed = "where-donated-plazma-is-used.txt";
    private final String answersToFrequentQuestions = "answers-to-frequent-questions-plasma-donors.txt";
    private final String donorNutrition = "donorNutrition.txt";
    private final String frequencyOfBloodDonation = "frequency-of-blood-donation.txt";


    public InfoForPlazmaDonors(AnswerMethodFactory answerMethodFactory,
                              KeyboardFactory keyboardFactory,
                              TelegramBot telegramBot) {
        super(telegramBot);
        this.answerMethodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public void answerCommand(Update update) throws JsonProcessingException {
        SendMessage sendMessage = answerMethodFactory.getSendMessage(
                update.message().chat().id(),
                "Информация для доноров плазмы",
                keyboardFactory.getInlineKeyboard(
                        List.of("Питание донора",
                                "Как часто можно сдавать кровь",
                                "Где применяется донорская кровь",
                                "Ответы на часто задаваемые вопросы",
                                "Назад"),
                        List.of(1, 1, 1, 1, 1),
                        List.of(DONOR_NUTRITION_PLASMA,
                                FREQUENCY_OF_PLASMA_DONATION,
                                WHERE_DONATED_PLASMA_IS_USED,
                                ANSWERS_TO_FREQUENT_QUESTIONS_PLASMA_DONORS,
                                DONOR_INFORMATION)
                ));
        telegramBot.execute(sendMessage);
    }

    public void answerCallbackQuery(CallbackQuery callbackQuery) {
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                "Информация для доноров плазмы",
                keyboardFactory.getInlineKeyboard(
                        List.of("Питание донора",
                                "Как часто можно сдавать кровь",
                                "Где применяется донорская плазма",
                                "Ответы на часто задаваемые вопросы",
                                "Назад"),
                        List.of(1, 1, 1, 1, 1),
                        List.of(DONOR_NUTRITION_PLASMA,
                                FREQUENCY_OF_PLASMA_DONATION,
                                WHERE_DONATED_PLASMA_IS_USED,
                                ANSWERS_TO_FREQUENT_QUESTIONS_PLASMA_DONORS,
                                DONOR_INFORMATION)
                ));
        telegramBot.execute(sendMessage);
    }

    public void answersToFrequentQuestions(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, answersToFrequentQuestions);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(PLAZMA_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

    public void donorNutrition(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, donorNutrition);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(PLAZMA_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }


    public void whereDonatedPlasmaIsUsed(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, whereDonatedPlazmaIsUsed);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(PLAZMA_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

    public void frequencyOfBloodDonation(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, frequencyOfBloodDonation);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(PLAZMA_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

}
