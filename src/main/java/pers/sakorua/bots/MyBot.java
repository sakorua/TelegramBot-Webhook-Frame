package pers.sakorua.bots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pers.sakorua.config.TelegramBotConfig;

/**
 * @author SaKoRua
 * @date 2022-01-21 12:32 AM
 * @Description //TODO
 */
@Configuration
public class MyBot extends TelegramWebhookBot {


    private static final Logger logger = LoggerFactory.getLogger(MyBot.class);

    private final TelegramBotConfig telegramBotConfig;

    public MyBot(TelegramBotConfig telegramBotConfig) {
        logger.info("TelegramBot created, telegramBotConfig = {}", telegramBotConfig);
        this.telegramBotConfig = telegramBotConfig;
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getUserName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getBotToken();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        logger.info("onUpdateReceived");

        if (update.hasMessage() && update.getMessage().hasText()) {
            logger.info("onUpdateReceived from user {} the text message: {}",
                    update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getText());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Well, all information looks like noise until you break the code.");
            return sendMessage;
        }
        return null;
    }

    @Override
    public String getBotPath() {
        return telegramBotConfig.getWebHookPath();
    }
}
