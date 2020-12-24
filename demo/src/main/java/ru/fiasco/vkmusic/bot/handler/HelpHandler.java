package ru.fiasco.vkmusic.bot.handler;

import ru.fiasco.vkmusic.domain.Subscriber;
import ru.fiasco.vkmusic.service.SubscriberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static ru.fiasco.vkmusic.util.TelegramUtil.createMessageTemplate;

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
        helpMessage.setText("Введите команду /choose_genre, чтобы посмотреть список доступных жанров\n" +
                "Введите команду /subscribes, чтобы посмотреть список текущих подписок");
        helpMessage.enableMarkdown(false);
        return Arrays.asList(helpMessage);
    }

    @Override
    public String operatedCommand() {
        return "/help";
    }

    @Override
    public String operatedCallBackQuery() {
        return null;
    }
}
