package ru.spk.donor_bot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.spk.donor_bot.manager.info.InfoManager;
import ru.spk.donor_bot.manager.start.StartManager;

import static ru.spk.donor_bot.data.Command.INFO_COMMAND;
import static ru.spk.donor_bot.data.Command.START_COMMAND;

@Service
public class CommandHandler {
    private final InfoManager infoManager;
    private final StartManager startManager;
    private final TelegramBot telegramBot;

    public CommandHandler(InfoManager infoManager,
                          StartManager startManager,
                          TelegramBot telegramBot) {
        this.infoManager = infoManager;
        this.startManager = startManager;
        this.telegramBot = telegramBot;
    }

    public void answer(Update update) {
        String command = update.message().text();
        switch (command){
            case START_COMMAND -> startManager.answerCommand(update);
            case INFO_COMMAND -> infoManager.answerCommand(update);
            default -> defaultAnswer(update);
        }
    }

    private void defaultAnswer(Update update) {
        String answerMessage = """
                Неподдерживаемая команда
                """;
        SendMessage sendMessage = new SendMessage(update.message().chat().id(), answerMessage);
        telegramBot.execute(sendMessage);
    }

}
