package com.example.demo.bot.handler;

import com.example.demo.domain.Subscriber;
import com.example.demo.service.SubscriberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.demo.util.TelegramUtil.createMessageTemplate;

@Component
public class StartHandler implements Handler {
    @Value("${bot.name}")
    private String botUsername;

    private final SubscriberService subscriberService;

    public StartHandler(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Subscriber subscriber, String message) {
        // Welcoming user
        SendMessage welcomeMessage = createMessageTemplate(subscriber);
        welcomeMessage.setText("ну здорова");
        subscriber.setBotState("REGISTERED");
        //subscriberService.saveNewSubscriber(subscriber);
        List<PartialBotApiMethod<? extends Serializable>> blabla = new ArrayList<>();
        blabla.add(welcomeMessage);
        return blabla;
    }

    @Override
    public String operatedBotState() {
        return "START";
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
