package com.example.demo.bot.handler;

import com.example.demo.domain.Subscriber;
import com.example.demo.service.SubscriberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.demo.util.TelegramUtil.createMessageTemplate;

@Component
public class HelpHandler implements Handler {
    @Value("${bot.name}")
    private String botUsername;

    private final SubscriberService subscriberService;

    public HelpHandler(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Subscriber subscriber, Update update) {
        SendMessage helpMessage = createMessageTemplate(subscriber);
        helpMessage.setText("хелп");
        return Arrays.asList(helpMessage);
    }

    @Override
    public String operatedBotState() {
        return null;
    }

    @Override
    public String operatedCommand() {
        return "/help";
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
