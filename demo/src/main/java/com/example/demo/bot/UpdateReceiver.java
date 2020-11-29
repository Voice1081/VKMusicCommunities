package com.example.demo.bot;

import com.example.demo.bot.handler.Handler;
import com.example.demo.domain.Subscriber;
import com.example.demo.service.SubscriberService;
import javassist.NotFoundException;
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
        if(!isMessageWithText(update) && !update.hasCallbackQuery()) return null;
        try {
            // Checking if Update is a message with text
            Subscriber subscriber = getOrRegisterNew(update);
            if (isMessageWithText(update)) {
                // Getting Message from Update
                final Message message = update.getMessage();
                // Looking for suitable handler
                if(message.getText().startsWith("/")){
                    if(!subscriber.getBotState().equals("START"))
                    {
                        subscriber.setBotState("START");
                        try {
                            subscriberService.updateSubscriber(subscriber);
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    return getHandlerByCommand(message.getText()).handle(subscriber, update);
                }
                else {
                    return getHandlerByState(subscriber.getBotState()).handle(subscriber, update);
                }
                // Same workflow but for CallBackQuery
            } else if (update.hasCallbackQuery()) {
                return getHandlerByState(subscriber.getBotState()).handle(subscriber, update);
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

    private Handler getHandlerByCommand(String command) {
        return handlers.stream()
                .filter(h -> h.operatedCommand() != null)
                .filter(h -> h.operatedCommand().equals(command))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

    private Subscriber getOrRegisterNew(Update update){
        int chatId;
        String username;
        if(isMessageWithText(update)){
            chatId = update.getMessage().getFrom().getId();
            username = update.getMessage().getFrom().getUserName();
        }
        else if(update.hasCallbackQuery()){
            chatId = update.getCallbackQuery().getFrom().getId();
            username = update.getCallbackQuery().getFrom().getUserName();
        }
        else { return null; }
        return getOrRegisterNew(chatId, username);
    }

    private Subscriber getOrRegisterNew(int chatId, String username){
        Subscriber subscriber;
        if(subscriberService.getAllByChatId(chatId).isEmpty()){
            subscriber = subscriberService.saveNewSubscriber(new Subscriber(username, chatId));
        }
        else{
            subscriber = subscriberService.getAllByChatId(chatId).get(0);
        }
        return subscriber;
    }
}
