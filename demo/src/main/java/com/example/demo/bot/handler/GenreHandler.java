package com.example.demo.bot.handler;

import com.example.demo.domain.Subscriber;
import com.example.demo.dto.enumeration.PostRange;
import com.example.demo.service.GenreService;
import com.example.demo.service.PostsService;
import com.example.demo.service.SubscriberService;
import com.example.demo.dto.PostDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.util.TelegramUtil.createMessageTemplate;

@Component
public class GenreHandler implements Handler {
    @Value("${bot.name}")
    private String botUsername;

    private final SubscriberService subscriberService;
    private final PostsService postsService;
    private final GenreService genreService;
    private final Gson gson = new Gson();

    public GenreHandler(SubscriberService subscriberService, PostsService postsService, GenreService genreService) {
        this.subscriberService = subscriberService;
        this.postsService = postsService;
        this.genreService = genreService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PartialBotApiMethod<? extends Serializable>> handle(Subscriber subscriber, Update update) {
        if (!update.hasCallbackQuery()) return null;
        Type strStrHashMapType = new TypeToken<HashMap<String, String>>() {
        }.getType();
        HashMap<String, String> callBack = gson.fromJson(update.getCallbackQuery().getData(), strStrHashMapType);

        if(!(callBack.containsKey("Genre") && callBack.containsKey("Action")))
            throw new UnsupportedOperationException();

        String genre = callBack.get("Genre");
        String action = callBack.get("Action");
        String genreName = null;
        try {
            genreName = genreService.getById(genre).getName();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        SendMessage response = createMessageTemplate(subscriber);

        if (action.equals("Subscribe")) {
            if(!subscriber.getSubscribersGenres().contains(genre)) {
                subscriber.getSubscribersGenres().add(genre);
                try {
                    subscriberService.updateSubscriber(subscriber);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                response.setText(String.format("Вы подписались на уведомления по жанру %s", genreName));
            }
        } else if (action.equals("Unsubscribe")) {
            if(subscriber.getSubscribersGenres().contains(genre)) {
                subscriber.getSubscribersGenres().remove(genre);
                try {
                    subscriberService.updateSubscriber(subscriber);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                response.setText(String.format("Вы отписались от уведомлений по жанру %s", genreName));
            }
        } else if (action.equals("Top day")) {
            response.setText(getTopStr(genre, genreName, PostRange.DAY));
        } else if (action.equals("Top week")) {
            response.setText(getTopStr(genre, genreName, PostRange.WEEK));
        } else if (action.equals("Top month")) {
            response.setText(getTopStr(genre, genreName, PostRange.MONTH));
        } else {
            response.setText(genreName);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        buttons.addAll(Arrays.asList(MakeTopCallback(genre, PostRange.DAY),
                MakeTopCallback(genre, PostRange.WEEK),
                MakeTopCallback(genre, PostRange.MONTH)));

        InlineKeyboardButton subscribed = new InlineKeyboardButton();
        HashMap<String, String> subscsribedCallback = new HashMap<>();
        subscsribedCallback.put("Handler", "GENRE");
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
        response.enableMarkdown(false);
        return Arrays.asList(response);
    }

    private List<InlineKeyboardButton> MakeTopCallback(String genre, PostRange time){
        InlineKeyboardButton top = new InlineKeyboardButton();
        HashMap<String, String> topCallback = new HashMap<>();
        topCallback.put("Handler", "GENRE");
        topCallback.put("Genre", genre);
        String action;
        String text;
        switch (time){
            case DAY:
                action = "Top day";
                text = "Посмотреть топ за день";
                break;
            case WEEK:
                action = "Top week";
                text = "Посмотреть топ за неделю";
                break;
            case MONTH:
                action = "Top month";
                text = "Посмотреть топ за месяц";
                break;
            default:
                action = null;
                text = null;
        }
        topCallback.put("Action", action);
        top.setCallbackData(gson.toJson(topCallback));
        top.setText(text);
        return Arrays.asList(top);


    }

    private String getTopStr(String genre, String genreName, PostRange time){
        List<PostDto> top = postsService.getRecordsForGenreList(Arrays.asList(genre), time);
        String topStr = String.join("\n\n", top.stream().map(PostDto::toBeautyString)
                .collect(Collectors.toList()));
        switch (time){
            case DAY:
                topStr = String.format("Топ за день по жанру %s:\n %s", genreName, topStr);
                break;
            case WEEK:
                topStr = String.format("Топ за неделю по жанру %s:\n %s", genreName, topStr);
                break;
            case MONTH:
                topStr = String.format("Топ за месяц по жанру %s:\n %s", genreName, topStr);
                break;
        }
        return topStr;
    }

    @Override
    public String operatedCommand() {
        return null;
    }

    @Override
    public String operatedCallBackQuery() {
        return "GENRE";
    }
}