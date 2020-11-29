package com.example.demo.util;

import com.example.demo.domain.Subscriber;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TelegramUtil {
    public static SendMessage createMessageTemplate(Subscriber subscriber) {
        return createMessageTemplate(String.valueOf(subscriber.getChatId()));
    }

    // Creating template of SendMessage with enabled Markdown
    public static SendMessage createMessageTemplate(String chatId) {
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.enableMarkdown(true);
        return sm;
    }

    // Creating button
    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command) {
        InlineKeyboardButton ikb = new InlineKeyboardButton();
        ikb.setText(text);
        ikb.setCallbackData(command);
        return ikb;
    }
}