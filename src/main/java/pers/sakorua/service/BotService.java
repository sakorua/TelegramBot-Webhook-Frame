package pers.sakorua.service;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotService {
    public BotApiMethod<?> checkBin(Update update);
}
