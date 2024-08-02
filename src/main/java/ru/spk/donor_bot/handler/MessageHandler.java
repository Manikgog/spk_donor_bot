package ru.spk.donor_bot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import static ru.spk.donor_bot.data.MessageData.ERROR_MESSAGE;


@Service
public class MessageHandler {
    private final TelegramBot telegramBot;

    public MessageHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void answer(Update update) {
        if(update.message().text() != null) {
            String text = update.message().text();
            switch (text) {
                case ERROR_MESSAGE -> defaultAnswer(update);
                default -> defaultAnswer(update);
            }
        }
    }

    private void defaultAnswer(Update update) {
        String answerMessage = """
                Неподдерживаемое сообщение.
                """;
        SendMessage sendMessage = new SendMessage(update.message().chat().id(), answerMessage);
        telegramBot.execute(sendMessage);
    }
}
