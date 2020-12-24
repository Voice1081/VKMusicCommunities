package ru.fiasco.vkmusic.bot;

import ru.fiasco.vkmusic.bot.handler.Handler;
import ru.fiasco.vkmusic.domain.Subscriber;
import ru.fiasco.vkmusic.service.SubscriberService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Component
public class UpdateReceiver {
    private final List<Handler> handlers;
    private final SubscriberService subscriberService;

    public UpdateReceiver(List<Handler> handlers, SubscriberService subscriberService) {
        this.handlers = handlers;
        this.subscriberService = subscriberService;
    }

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        if(!isMessageWithText(update) && !update.hasCallbackQuery()) return null;
        try {
            Subscriber subscriber = getOrRegisterNew(update);
            if (isMessageWithText(update)) {
                final Message message = update.getMessage();
                    return getHandlerByCommand(message.getText()).handle(subscriber, update);
            }
            else if (update.hasCallbackQuery()) {
                return getHandlerByCallBackQuery(update.getCallbackQuery().getData()).handle(subscriber, update);
            }
            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            return Collections.emptyList();
        }
    }

    private Handler getHandlerByCallBackQuery(String query) {
        Gson gson = new Gson();
        Type strStrHashMapType = new TypeToken<HashMap<String, String>>() {
        }.getType();
        HashMap<String, String> callBackData = gson.fromJson(query, strStrHashMapType);
        if(!callBackData.containsKey("Handler"))
            throw new UnsupportedOperationException();
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery() != null)
                .filter(h -> h.operatedCallBackQuery().equals(callBackData.get("Handler")))
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
