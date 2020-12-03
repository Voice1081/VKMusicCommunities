package com.example.demo.bot.handler;

import com.example.demo.domain.Genre;
import com.example.demo.domain.Subscriber;
import com.example.demo.service.GenreService;
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
public class ChooseGenreHandler implements Handler {
    @Value("${bot.name}")
    private String botUsername;

    private final SubscriberService subscriberService;
    private final GenreService genreService;

    public ChooseGenreHandler(SubscriberService subscriberService, GenreService genreService) {
        this.genreService = genreService;
        this.subscriberService = subscriberService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Subscriber subscriber, Update update) {
        SendMessage genres = createMessageTemplate(subscriber);
        //здесь надо будет получать список жанров из базы
        List<Genre> listOfGenres = genreService.getAll();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for(Genre genre : listOfGenres){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(genre.getName());
            HashMap<String, String> callBackData = new HashMap<>();
            callBackData.put("Handler", "GENRE");
            callBackData.put("Genre", genre.getName());
            callBackData.put("Action", "none");
            button.setCallbackData(new Gson().toJson(callBackData));
            buttons.add(Arrays.asList(button));
        }
        inlineKeyboardMarkup.setKeyboard(buttons);
        genres.setText("Список доступных жанров:");
        genres.setReplyMarkup(inlineKeyboardMarkup);
        return Arrays.asList(genres);
    }

    @Override
    public String operatedCommand() {
        return "/choose_genre";
    }

    @Override
    public String operatedCallBackQuery() {
        return null;
    }
}