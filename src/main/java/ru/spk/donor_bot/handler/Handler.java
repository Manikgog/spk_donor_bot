package ru.spk.donor_bot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.spk.donor_bot.data.MessageData;
import java.io.IOException;

@Service
public class Handler {
    private final Logger logger = LoggerFactory.getLogger(Handler.class);
    private final CallbackQueryHandler callbackQueryHandler;
    private final CommandHandler commandHandler;
    private final MessageHandler messageHandler;


    public Handler(CallbackQueryHandler callbackQueryHandler,
                   CommandHandler commandHandler,
                   MessageHandler messageHandler,
                   MessageData messageData) {
        this.callbackQueryHandler = callbackQueryHandler;
        this.commandHandler = commandHandler;
        this.messageHandler = messageHandler;
    }

    public void answer(Update update) throws IOException {
        logger.info("Processing update in method answer of Handler class: {}", update);
        if (update.callbackQuery() != null) {
            callbackQueryHandler.answer(update);
            return;
        }
        if (update.message() != null) {
            Message message = update.message();
            if (message.text() != null) {
                if (message.text().startsWith("/")) {
                    commandHandler.answer(update);
                    return;
                }
            }
            messageHandler.answer(update);
        }

        logger.info("Неподдерживаемый update: " + update);
    }
}

