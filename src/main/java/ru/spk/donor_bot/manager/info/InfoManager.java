package ru.spk.donor_bot.manager.info;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
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
public class InfoManager extends AbstractManager {
    private final AnswerMethodFactory answerMethodFactory;
    private final KeyboardFactory keyboardFactory;
    private final String folderName = "dataFiles";
    private final String generalInformation = "general-information.txt";
    private final String registrationForPlasmaDonors = "registration-of-plasma-donors.txt";

    public InfoManager(AnswerMethodFactory answerMethodFactory,
                       KeyboardFactory keyboardFactory,
                       TelegramBot telegramBot) {
        super(telegramBot);
        this.answerMethodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
    }


    /**
     * Метод для отправки меню для получения информации
     * @param update - объект класса Update
     */
    @Override
    public void answerCommand(Update update) {
        logger.info("the answerCommand method of the InfoManager class works. Parameter: Update -> {}", update);
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(update,
                "Общая информация",
                keyboardFactory.getInlineKeyboard(
                        List.of("Адрес и телефон станции переливания крови",
                                "Информация для доноров",
                                "Запись на сдачу плазмы",
                                "Назад"),
                        List.of(1, 1, 1, 1),
                        List.of(ADDRESS_AND_PHONE,
                                DONOR_INFORMATION,
                                REGISTRATION_FOR_PLASMA_DONATION,
                                START)
                ));

        telegramBot.execute(sendMessage);
    }


    /**
     * Метод для отправки меню для получения информации
     * @param callbackQuery - запрос обратного вызова
     */
    @Override
    public void answerCallbackQuery(CallbackQuery callbackQuery){
        logger.info("the answerCallbackQuery method of the InfoManager class works. Parameter: CallbackQuery -> {}", callbackQuery);
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                "Информация для доноров",
                keyboardFactory.getInlineKeyboard(
                        List.of("Адрес и телефон станции переливания крови",
                                "Информация для доноров",
                                "Запись на сдачу плазмы",
                                "Назад"),
                        List.of(1, 1, 1, 1),
                        List.of(ADDRESS_AND_PHONE,
                                DONOR_INFORMATION,
                                REGISTRATION_FOR_PLASMA_DONATION,
                                START)
                ));
        telegramBot.execute(sendMessage);
    }

    /**
     * Метод для отправки меню для получения информации о станции переливания крови
     * @param callbackQuery - запрос обратного вызова
     */
    public void addressAndPhone(CallbackQuery callbackQuery) {
        logger.info("The addressAndPhoneNursery method of the InfoManager class works. Parameter: CallbackQuery -> {}", callbackQuery);
        Path filePath = Paths.get(folderName, generalInformation);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(INFO)
                ));
        telegramBot.execute(sendMessage);
    }

    /**
     * Метод для отправки меню для получения информации о приютах
     * @param callbackQuery - запрос обратного вызова
     */
    public void informationForDonors(CallbackQuery callbackQuery) {
        logger.info("The informationForDonors method of the InfoManager class works. Parameter: CallbackQuery -> {}", callbackQuery);
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                "Меню информации для доноров",
                keyboardFactory.getInlineKeyboard(
                        List.of("Для доноров крови", "Для доноров плазмы", "Для доноров костного мозга", "Назад"),
                        List.of(1, 1, 1, 1),
                        List.of(BLOOD_DONORS, PLAZMA_DONORS, BONE_MARROW_DONORS, INFO)
                ));
        telegramBot.execute(sendMessage);
    }

    /**
    Метод для возвращения ссылки для записи на сдачу плазмы крови
    @param callbackQuery - запрос обратного вызова
     */
    public void informationAboutRegistrationPlasmaDonors(CallbackQuery callbackQuery) {
        logger.info("The informationAboutRegistrationPlasmaDonors method of the InfoManager class works. Parameter: CallbackQuery -> {}", callbackQuery);
        Path filePath = Paths.get(folderName, registrationForPlasmaDonors);
        String message = filePath.toFile().exists() ? FileManager.readFile(filePath) : "Файл не найден";
        EditMessageText sendMessage = answerMethodFactory.getEditMessageText(
                callbackQuery,
                message,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(INFO)
                ));
        telegramBot.execute(sendMessage);
    }
}
