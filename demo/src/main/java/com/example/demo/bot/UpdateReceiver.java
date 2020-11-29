package com.example.demo.bot;

import com.example.demo.bot.handler.Handler;
import com.example.demo.domain.Subscriber;
import com.example.demo.service.SubscriberService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
public class UpdateReceiver {
    // Storing available handlers in a list (stolen from Miroha)
    private final List<Handler> handlers;
    // Achieving access to user storage
    private final SubscriberService subscriberService;

    public UpdateReceiver(List<Handler> handlers, SubscriberService subscriberService) {
        this.handlers = handlers;
        this.subscriberService = subscriberService;
    }

    // Analyzing received update
    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        // try-catch in order to return empty list on unsupported command
        try {
            // Checking if Update is a message with text
            Subscriber subscriber;
            if (isMessageWithText(update)) {
                // Getting Message from Update
                final Message message = update.getMessage();
                // Getting chatId
                final int chatId = message.getFrom().getId();

                // Getting user from repository. If user is not presented in repository - create new and return him
                // For this reason we have a one arg constructor in User.class
                if(subscriberService.getAllByChatId(chatId).isEmpty()){
                    subscriber = subscriberService.saveNewSubscriber(new Subscriber(update.getMessage().getFrom()
                            .getUserName(),
                            chatId));
                }
                else{
                    subscriber = subscriberService.getAllByChatId(chatId).get(0);
                }
                // Looking for suitable handler
                return getHandlerByState(subscriber.getBotState()).handle(subscriber, message.getText());
                // Same workflow but for CallBackQuery
            } else if (update.hasCallbackQuery()) {
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                final int chatId = callbackQuery.getFrom().getId();
                if(subscriberService.getAllByChatId(chatId).isEmpty()){
                    subscriber = subscriberService.saveNewSubscriber(new Subscriber(update.getMessage().getFrom()
                            .getUserName(),
                            chatId));
                }
                else{
                    subscriber = subscriberService.getAllByChatId(chatId).get(0);
                }

                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(subscriber, callbackQuery.getData());
            }

            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            return Collections.emptyList();
        }
    }

    private Handler getHandlerByState(String state) {
        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private Handler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery().stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }
}
