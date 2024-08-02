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
public class InfoForBloodDonors extends AbstractManager{
    private final AnswerMethodFactory answerMethodFactory;
    private final KeyboardFactory keyboardFactory;
    private final String folderName = "dataFiles";
    private final String donorNutrition = "donorNutrition.txt";
    private final String frequencyOfBloodDonation = "frequency-of-blood-donation.txt";
    private final String whereDonatedBloodIsUsed = "where-donated-blood-is-used.txt";
    private final String contraindicationsToDonation = "contraindications-to-donation.txt";
    private final String documentsForDonor = "documents-for-donor.txt";
    private final String tradeRegulation = "trade-regulation.txt";
    private final String privilegesAndTradeCodex = "privileges-and-trade-codex.txt";
    private final String answersToFrequentQuestions = "answers-to-frequent-questions.txt";

    public InfoForBloodDonors(AnswerMethodFactory answerMethodFactory,
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
                "Информация для доноров крови",
                keyboardFactory.getInlineKeyboard(
                        List.of("Питание донора",
                                "Как часто можно сдавать кровь",
                                "Где применяется донорская кровь",
                                "Противопоказания к донорству",
                                "Документы выдаваемые донору",
                                "Права донора и Трудовой кодекс",
                                "Льготы и привилегии",
                                "Ответы на часто задаваемые вопросы",
                                "Назад"),
                        List.of(1, 1, 1, 1, 1, 1, 1, 1, 1),
                        List.of(DONOR_NUTRITION_BLOOD,
                                FREQUENCY_OF_BLOOD_DONATION,
                                WHERE_DONATED_BLOOD_IS_USED,
                                CONTRAINDICATIONS_TO_DONATION,
                                DOCUMENTS_FOR_DONOR,
                                TRADE_REGULATION,
                                PRIVILEGES_AND_TRADE_CODEX,
                                ANSWERS_TO_FREQUENT_QUESTIONS,
                                DONOR_INFORMATION)
                ));
        telegramBot.execute(sendMessage);
    }

    public void answerCallbackQuery(CallbackQuery callbackQuery) {
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                "Информация для доноров крови",
                keyboardFactory.getInlineKeyboard(
                        List.of("Питание донора",
                                "Как часто можно сдавать кровь",
                                "Где применяется донорская кровь",
                                "Противопоказания к донорству",
                                "Документы выдаваемые донору",
                                "Права донора и Трудовой кодекс",
                                "Льготы и привилегии",
                                "Ответы на часто задаваемые вопросы",
                                "Назад"),
                        List.of(1, 1, 1, 1, 1, 1, 1, 1, 1),
                        List.of(DONOR_NUTRITION_BLOOD,
                                FREQUENCY_OF_BLOOD_DONATION,
                                WHERE_DONATED_BLOOD_IS_USED,
                                CONTRAINDICATIONS_TO_DONATION,
                                DOCUMENTS_FOR_DONOR,
                                TRADE_REGULATION,
                                PRIVILEGES_AND_TRADE_CODEX,
                                ANSWERS_TO_FREQUENT_QUESTIONS,
                                DONOR_INFORMATION)
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
                        List.of(BLOOD_DONORS)
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
                        List.of(BLOOD_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

    public void privilegesAndTradeCodex(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, privilegesAndTradeCodex);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(BLOOD_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

    public void tradeRegulation(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, tradeRegulation);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(BLOOD_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

    public void documentsForDonor(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, documentsForDonor);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(BLOOD_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

    public void contraindicationsForDonation(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, contraindicationsToDonation);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(BLOOD_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

    public void whereDonatedBloodIsUsed(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, whereDonatedBloodIsUsed);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(BLOOD_DONORS)
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
                        List.of(BLOOD_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }
}
