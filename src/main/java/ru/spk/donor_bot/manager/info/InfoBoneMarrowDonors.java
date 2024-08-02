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
public class InfoBoneMarrowDonors extends AbstractManager{

    private final AnswerMethodFactory answerMethodFactory;
    private final KeyboardFactory keyboardFactory;
    private final String folderName = "dataFiles";
    private final String howToGetIntoTheDonorRegister = "how-to-get-into-the-donor-register.txt";
    private final String howIsTheProcedureGoing = "how-is-the-procedure-going.txt";
    private final String whoCanBecomeMarrowDonor = "who-can-become-marrow-donor.txt";
    private final String answersToFrequentQuestions = "answers-to-frequent-questions-marrow-donors.txt";

    public InfoBoneMarrowDonors(TelegramBot telegramBot, AnswerMethodFactory answerMethodFactory, KeyboardFactory keyboardFactory) {
        super(telegramBot);
        this.answerMethodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public void answerCommand(Update update) throws JsonProcessingException {
        SendMessage sendMessage = answerMethodFactory.getSendMessage(
                update.message().chat().id(),
                "Информация для доноров костного мозга",
                keyboardFactory.getInlineKeyboard(
                        List.of("Как попасть в регистр доноров",
                                "Как проходит процедура",
                                "Кто может стать донором",
                                "Ответы на часто задаваемые вопросы",
                                "Назад"),
                        List.of(1, 1, 1, 1, 1),
                        List.of(HOW_TO_GET_INTO_THE_DONOR_REGISTER,
                                HOW_IS_THE_PROCEDURE_GOING,
                                WHO_CAN_BECOME_A_DONOR,
                                ANSWERS_TO_FREQUENT_QUESTIONS_BONE_MARROW_DONORS,
                                DONOR_INFORMATION)
                ));
        telegramBot.execute(sendMessage);
    }

    @Override
    public void answerCallbackQuery(CallbackQuery callbackQuery) {
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                "Информация для доноров костного мозга",
                keyboardFactory.getInlineKeyboard(
                        List.of("Как попасть в регистр доноров",
                                "Как проходит процедура",
                                "Кто может стать донором",
                                "Ответы на часто задаваемые вопросы",
                                "Назад"),
                        List.of(1, 1, 1, 1, 1),
                        List.of(HOW_TO_GET_INTO_THE_DONOR_REGISTER,
                                HOW_IS_THE_PROCEDURE_GOING,
                                WHO_CAN_BECOME_A_DONOR,
                                ANSWERS_TO_FREQUENT_QUESTIONS_BONE_MARROW_DONORS,
                                DONOR_INFORMATION)
                ));
        telegramBot.execute(sendMessage);
    }


    public void howToGetIntoTheDonorRegister(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, howToGetIntoTheDonorRegister);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(BONE_MARROW_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

    public void howIsTheProcedureGoing(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, howIsTheProcedureGoing);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(BONE_MARROW_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }

    public void whoCanBecomeMarrowDonor(CallbackQuery callbackQuery) {
        Path filePath = Paths.get(folderName, whoCanBecomeMarrowDonor);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(BONE_MARROW_DONORS)
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
                        List.of(BONE_MARROW_DONORS)
                ));
        telegramBot.execute(sendMessage);
    }
}
