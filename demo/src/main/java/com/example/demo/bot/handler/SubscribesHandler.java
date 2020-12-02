package com.example.demo.bot.handler;

import com.example.demo.domain.Subscriber;
import com.example.demo.service.SubscriberService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.*;

import static com.example.demo.util.TelegramUtil.createMessageTemplate;

@Component
public class SubscribesHandler implements Handler {
    @Value("${bot.name}")
    private String botUsername;

    private final SubscriberService subscriberService;

    public SubscribesHandler(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Subscriber subscriber, Update update) {
        SendMessage subscribes = createMessageTemplate(subscriber);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        if(subscriber.getSubscribersGenres().size() == 0)
        {
            subscribes.setText("У вас еще нет подписок");
        }
        else {
            for (String genre : subscriber.getSubscribersGenres()) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(genre);
                HashMap<String, String> callBackData = new HashMap<>();
                callBackData.put("Handler", "GENRE");
                callBackData.put("Genre", genre);
                callBackData.put("Action", "none");
                button.setCallbackData(new Gson().toJson(callBackData));
                buttons.add(Arrays.asList(button));
            }

            inlineKeyboardMarkup.setKeyboard(buttons);
            subscribes.setText("Ваши подписки:");
            subscribes.setReplyMarkup(inlineKeyboardMarkup);
        }
        return Arrays.asList(subscribes);
    }

    @Override
    public String operatedCommand() {
        return "/subscribes";
    }

    @Override
    public String operatedCallBackQuery() {
        return null;
    }
}
