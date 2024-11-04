package ru.spk.donor_bot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.spk.donor_bot.manager.info.InfoBoneMarrowDonors;
import ru.spk.donor_bot.manager.info.InfoForBloodDonors;
import ru.spk.donor_bot.manager.info.InfoForPlazmaDonors;
import ru.spk.donor_bot.manager.info.InfoManager;
import ru.spk.donor_bot.manager.start.StartManager;

import static ru.spk.donor_bot.data.CallbackData.*;


@Service
public class CallbackQueryHandler {
    private final Logger logger = LoggerFactory.getLogger(CallbackQueryHandler.class);
    private final InfoManager infoManager;
    private final StartManager startManager;
    private final TelegramBot telegramBot;

    private final InfoForBloodDonors infoForBloodDonors;
    private final InfoForPlazmaDonors infoForPlazmaDonors;

    private final InfoBoneMarrowDonors infoBoneMarrowDonors;

    public CallbackQueryHandler(InfoManager infoManager,
                                StartManager startManager,
                                TelegramBot telegramBot,
                                InfoForBloodDonors infoForBloodDonors,
                                InfoForPlazmaDonors infoForPlazmaDonors,
                                InfoBoneMarrowDonors infoBoneMarrowDonors) {
        this.infoManager = infoManager;
        this.startManager = startManager;
        this.telegramBot = telegramBot;
        this.infoForBloodDonors = infoForBloodDonors;
        this.infoForPlazmaDonors = infoForPlazmaDonors;
        this.infoBoneMarrowDonors = infoBoneMarrowDonors;
    }

    public void answer(Update update) {
        logger.info("Processing update in method answer of CallbackQueryHandler class: {}", update);

        switch (update.callbackQuery().data()) {
            case INFO -> infoManager.answerCallbackQuery(update.callbackQuery());
            case ADDRESS_AND_PHONE -> infoManager.addressAndPhone(update.callbackQuery());
            case REGISTRATION_FOR_PLASMA_DONATION -> infoManager.informationAboutRegistrationPlasmaDonors(update.callbackQuery());
            case START -> startManager.answerCallbackQuery(update.callbackQuery());
            case DONOR_INFORMATION -> infoManager.informationForDonors(update.callbackQuery());
            case BLOOD_DONORS -> infoForBloodDonors.answerCallbackQuery(update.callbackQuery());
            case PLAZMA_DONORS -> infoForPlazmaDonors.answerCallbackQuery(update.callbackQuery());
            case BONE_MARROW_DONORS -> infoBoneMarrowDonors.answerCallbackQuery(update.callbackQuery());

            case DONOR_NUTRITION_BLOOD -> infoForBloodDonors.donorNutrition(update.callbackQuery());
            case FREQUENCY_OF_BLOOD_DONATION -> infoForBloodDonors.frequencyOfBloodDonation(update.callbackQuery());
            case WHERE_DONATED_BLOOD_IS_USED -> infoForBloodDonors.whereDonatedBloodIsUsed(update.callbackQuery());
            case CONTRAINDICATIONS_TO_DONATION -> infoForBloodDonors.contraindicationsForDonation(update.callbackQuery());
            case DOCUMENTS_FOR_DONOR -> infoForBloodDonors.documentsForDonor(update.callbackQuery());
            case TRADE_REGULATION -> infoForBloodDonors.tradeRegulation(update.callbackQuery());
            case PRIVILEGES_AND_TRADE_CODEX -> infoForBloodDonors.privilegesAndTradeCodex(update.callbackQuery());
            case ANSWERS_TO_FREQUENT_QUESTIONS -> infoForBloodDonors.answersToFrequentQuestions(update.callbackQuery());

            case WHERE_DONATED_PLASMA_IS_USED -> infoForPlazmaDonors.whereDonatedPlasmaIsUsed(update.callbackQuery());
            case ANSWERS_TO_FREQUENT_QUESTIONS_PLASMA_DONORS -> infoForPlazmaDonors.answersToFrequentQuestions(update.callbackQuery());
            case DONOR_NUTRITION_PLASMA -> infoForPlazmaDonors.donorNutrition(update.callbackQuery());
            case FREQUENCY_OF_PLASMA_DONATION -> infoForPlazmaDonors.frequencyOfBloodDonation(update.callbackQuery());

            case HOW_TO_GET_INTO_THE_DONOR_REGISTER -> infoBoneMarrowDonors.howToGetIntoTheDonorRegister(update.callbackQuery());
            case HOW_IS_THE_PROCEDURE_GOING -> infoBoneMarrowDonors.howIsTheProcedureGoing(update.callbackQuery());
            case WHO_CAN_BECOME_A_DONOR -> infoBoneMarrowDonors.whoCanBecomeMarrowDonor(update.callbackQuery());
            case ANSWERS_TO_FREQUENT_QUESTIONS_BONE_MARROW_DONORS -> infoBoneMarrowDonors.answersToFrequentQuestions(update.callbackQuery());

            default -> defaultAnswer(update.callbackQuery());
        }

    }

    private void defaultAnswer(CallbackQuery callbackQuery) {
        String answerMessage = """
                Неподдерживаемая команда
                """;
        SendMessage sendMessage = new SendMessage(callbackQuery.message().chat().id(), answerMessage);
        telegramBot.execute(sendMessage);
    }
}
