package ru.spk.donor_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.spk.donor_bot.handler.Handler;
import java.io.IOException;
import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final Handler handler;

    private static final Path USERS_FILE = Paths.get("telegram_users_info.txt");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ConcurrentHashMap<Long, Boolean> processedUsers = new ConcurrentHashMap<>();

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      Handler handler){
        this.telegramBot = telegramBot;
        this.handler = handler;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
        // Создаем файл при инициализации, если он не существует
        createUsersFileIfNotExists();
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if(update != null) {
                try {
                    handler.answer(update);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
                // Записываем информацию о пользователе в файл
                writeUserInfoToFile(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Метод для записи информации о пользователе в файл
     * @param update объект Update из Telegram
     */
    private void writeUserInfoToFile(Update update) {
        try {
            Long chatId = null;
            String firstName = "N/A";
            String lastName = "N/A";
            String username = "N/A";

            // Обрабатываем callback query
            if (update.callbackQuery() != null && update.callbackQuery().from() != null) {
                chatId = (long) update.callbackQuery().from().id();
                firstName = update.callbackQuery().from().firstName() != null ?
                        update.callbackQuery().from().firstName() : "N/A";
                lastName = update.callbackQuery().from().lastName() != null ?
                        update.callbackQuery().from().lastName() : "N/A";
                username = update.callbackQuery().from().username() != null ?
                        update.callbackQuery().from().username() : "N/A";
            }
            // Обрабатываем обычное сообщение (на всякий случай)
            else if (update.message() != null && update.message().from() != null) {
                chatId = update.message().from().id();
                firstName = update.message().from().firstName() != null ?
                        update.message().from().firstName() : "N/A";
                lastName = update.message().from().lastName() != null ?
                        update.message().from().lastName() : "N/A";
                username = update.message().from().username() != null ?
                        update.message().from().username() : "N/A";
            }

            // Если удалось получить chatId, записываем информацию
            if (chatId != null && !processedUsers.containsKey(chatId)) {
                String userInfo = String.format("%d %s %s %s %s%n",
                        chatId,
                        firstName,
                        lastName,
                        username,
                        LocalDateTime.now().format(DATE_FORMATTER));

                // Записываем в файл
                Files.write(USERS_FILE, userInfo.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);

                // Добавляем пользователя в map обработанных
                processedUsers.put(chatId, true);

                logger.info("Информация о пользователе {} записана в файл", chatId);
            }

        } catch (Exception e) {
            logger.error("Ошибка при записи информации о пользователе в файл: {}", e.getMessage());
        }
    }

    /**
     * Метод для создания файла, если он не существует
     */
    private void createUsersFileIfNotExists() {
        try {
            if (!Files.exists(USERS_FILE)) {
                Files.createFile(USERS_FILE);
                // Добавляем заголовок при создании файла
                String header = "chat_id first_name last_name username timestamp\n";
                Files.write(USERS_FILE, header.getBytes(), StandardOpenOption.WRITE);
                logger.info("Файл {} создан успешно", USERS_FILE.getFileName());
            }
        } catch (IOException e) {
            logger.error("Ошибка при создании файла: {}", e.getMessage());
        }
    }

}
