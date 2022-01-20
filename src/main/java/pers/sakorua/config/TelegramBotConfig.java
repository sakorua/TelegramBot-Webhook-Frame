package pers.sakorua.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author SaKoRua
 * @date 2022-01-21 12:31 AM
 * @Description //TODO
 */
@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBotConfig {
    @Value("${telegrambot.webHookPath}")
    String webHookPath;
    @Value("${telegrambot.userName}")
    String userName;
    @Value("${telegrambot.botToken}")
    String botToken;
}