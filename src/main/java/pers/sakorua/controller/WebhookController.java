package pers.sakorua.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import pers.sakorua.bots.MyBot;
import pers.sakorua.config.TelegramBotConfig;
import pers.sakorua.service.BotService;

/**
 * @author SaKoRua
 * @date 2022-01-21 12:35 AM
 * @Description //TODO
 */
@RestController
public class WebhookController {
    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);
    private final WebhookBot telegramBot;

    @Autowired
    public WebhookController(TelegramBotConfig telegramBotConfig) {
        this.telegramBot = new MyBot(telegramBotConfig);
    }

    @Autowired
    private BotService botService;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        /**
         * @author SaKoRua
         * @Description //TODO Bot收到信息之后 先被此方法接收 再被Bot中的onWebhookUpdateReceived接收
         * @Date 10:39 AM 2022/1/21
         * @Param
         * @return
         **/

        String msgText = update.getMessage().getText();
        if (msgText.matches("/bin \\d{6}\\b")) {
            return botService.checkBin(update);
        }

        return null;
    }

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok().build();
    }
}
