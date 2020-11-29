package com.example.demo.bot.handler;

import com.example.demo.domain.Subscriber;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public interface Handler {
    List<PartialBotApiMethod<? extends Serializable>> handle(Subscriber subscriber, Update update);

    String operatedBotState();

    String operatedCommand();

    List<String> operatedCallBackQuery();
}
