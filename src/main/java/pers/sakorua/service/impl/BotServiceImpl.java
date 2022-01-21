package pers.sakorua.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import pers.sakorua.bots.MyBot;
import pers.sakorua.config.TelegramBotConfig;
import pers.sakorua.pojo.Bin;
import pers.sakorua.service.BotService;

/**
 * @author SaKoRua
 * @date 2022-01-21 10:47 AM
 * @Description //TODO
 */
@Service
public class BotServiceImpl implements BotService {

    private final WebhookBot telegramBot;

    @Autowired
    public BotServiceImpl(TelegramBotConfig telegramBotConfig) {
        this.telegramBot = new MyBot(telegramBotConfig);
    }


    /**
     * @author SaKoRua
     * @Description //TODO checkBin
     * @Date 10:47 AM 2022/1/21
     * @Param
     * @return
     **/
    @Override
    public BotApiMethod<?> checkBin(Update update) {

        Integer messageId = update.getMessage().getMessageId();
        String textReq = update.getMessage().getText();
        String bin4check = textReq.replace("/bin ", "");
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject("https://lookup.binlist.net/" + bin4check, String.class);

        Bin binObj = JSONObject.parseObject(forObject, Bin.class);
        String binInfo =
                "发卡组织" + binObj.getScheme() + "\r\n" +
                "卡等级" + binObj.getBrand() + "\r\n" +
                "卡类型" + binObj.getType() + "\r\n" +
                "国家" + binObj.getCountry().getEmoji() + binObj.getCountry().getName() + "\r\n" +
                "银行" + binObj.getBank().getName();


        // TODO checkBin功能待完成

        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage sendMessage = new SendMessage(chatId, binInfo);
        sendMessage.setReplyToMessageId(messageId);
        return sendMessage;
    }
}
