package com.example.demo.bot.handler;

import com.example.demo.domain.Subscriber;
import com.example.demo.service.SubscriberService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

import static com.example.demo.util.TelegramUtil.createMessageTemplate;

@Component
public class GenreHandler implements Handler {
    @Value("${bot.name}")
    private String botUsername;

    private final SubscriberService subscriberService;

    public GenreHandler(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Subscriber subscriber, Update update) {
        if (!update.hasCallbackQuery()) return null;
        Gson gson = new Gson();
        Type strStrMapType = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> callBack = gson.fromJson(update.getCallbackQuery().getData(), strStrMapType);

        String genre = callBack.get("Genre");
        String action = callBack.get("Action");

        SendMessage response = createMessageTemplate(subscriber);

        if (action.equals("Subscribe")) {
            subscriber.getSubscribersGenres().add(genre);
            try {
                subscriberService.updateSubscriber(subscriber);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            response.setText(String.format("Вы подписались на уведомления по жанру %s", genre));
        } else if (action.equals("Unsubscribe")) {
            subscriber.getSubscribersGenres().remove(genre);
            try {
                subscriberService.updateSubscriber(subscriber);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            response.setText(String.format("Вы отписались от уведомлений по жанру %s", genre));
        } else if (action.equals("Top day")) {
            response.setText(String.format("Топ за день по жанру %s", genre));
        } else if (action.equals("Top week")) {
            response.setText(String.format("Топ за неделю по жанру %s", genre));
        } else if (action.equals("Top month")) {
            response.setText(String.format("Топ за месяц по жанру %s", genre));
        } else {
            response.setText(genre);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        InlineKeyboardButton topDay = new InlineKeyboardButton();
        HashMap<String, String> topDayCallback = new HashMap<>();
        topDayCallback.put("Genre", genre);
        topDayCallback.put("Action", "Top day");
        topDay.setCallbackData(gson.toJson(topDayCallback));
        topDay.setText("Посмотреть топ за день");
        buttons.add(Arrays.asList(topDay));

        InlineKeyboardButton topWeek = new InlineKeyboardButton();
        HashMap<String, String> topWeekCallback = new HashMap<>();
        topWeekCallback.put("Genre", genre);
        topWeekCallback.put("Action", "Top week");
        topWeek.setCallbackData(gson.toJson(topWeekCallback));
        topWeek.setText("Посмотреть топ за неделю");
        buttons.add(Arrays.asList(topWeek));

        InlineKeyboardButton topMonth = new InlineKeyboardButton();
        HashMap<String, String> topMonthCallback = new HashMap<>();
        topMonthCallback.put("Genre", genre);
        topMonthCallback.put("Action", "Top month");
        topMonth.setCallbackData(gson.toJson(topMonthCallback));
        topMonth.setText("Посмотреть топ за месяц");
        buttons.add(Arrays.asList(topMonth));

        InlineKeyboardButton subscribed = new InlineKeyboardButton();
        HashMap<String, String> subscsribedCallback = new HashMap<>();
        subscsribedCallback.put("Genre", genre);
        if (subscriber.getSubscribersGenres().contains(genre)) {
            subscsribedCallback.put("Action", "Unsubscribe");
            subscribed.setText("Отписаться");
        } else {
            subscsribedCallback.put("Action", "Subscribe");
            subscribed.setText("Подписаться");
        }
        subscribed.setCallbackData(gson.toJson(subscsribedCallback));
        buttons.add(Arrays.asList(subscribed));

        inlineKeyboardMarkup.setKeyboard(buttons);
        response.setReplyMarkup(inlineKeyboardMarkup);
        return Arrays.asList(response);
    }

    @Override
    public String operatedBotState() {
        return "CHOOSING GENRE";
    }

    @Override
    public String operatedCommand() {
        return null;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}