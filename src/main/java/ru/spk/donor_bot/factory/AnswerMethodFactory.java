package ru.spk.donor_bot.factory;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.stereotype.Component;

@Component
public class AnswerMethodFactory {
    public SendMessage getSendMessage(Long chatId,
                                      String text,
                                      InlineKeyboardMarkup keyboard){
        if(keyboard == null){
            return new SendMessage(chatId, text);
        }
        return new SendMessage(chatId, text).replyMarkup(keyboard);
    }

    public SendPhoto getSendPhoto(Long chatId,
                                   byte[] photoArray,
                                      InlineKeyboardMarkup keyboard) {
        if(keyboard == null) {
            return new SendPhoto(chatId, photoArray);
        }
        return new SendPhoto(chatId, photoArray).replyMarkup(keyboard);
    }

    public EditMessageText getEditMessageText(Update update,
                                              String text,
                                              InlineKeyboardMarkup keyboard){
        if(keyboard == null){
            return new EditMessageText(update.message().chat().id(),
                    update.message().messageId(),
                    text);
        }
        return new EditMessageText(update.message().chat().id(),
                update.message().messageId(),
                text)
                .replyMarkup(keyboard);
    }

    public EditMessageText getEditMessageText(CallbackQuery callbackQuery,
                                              String text,
                                              InlineKeyboardMarkup keyboard){
        if(keyboard == null){
            return new EditMessageText(callbackQuery.message().chat().id(),
                    callbackQuery.message().messageId(),
                    text);
        }
        return new EditMessageText(callbackQuery.message().chat().id(),
                callbackQuery.message().messageId(),
                text)
                .replyMarkup(keyboard);
    }

    public DeleteMessage getDeleteMessage(Long chatId, Integer messageId){
        return new DeleteMessage(chatId, messageId);
    }

}
