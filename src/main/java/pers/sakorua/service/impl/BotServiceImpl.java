package pers.sakorua.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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
     * @return
     * @author SaKoRua
     * @Description //TODO checkBin
     * @Date 10:47 AM 2022/1/21
     * @Param
     **/
    @Override
    public BotApiMethod<?> checkBin(Update update) {

        try {
            SendMessage sendMessage = null;

            Integer messageId = update.getMessage().getMessageId();
            String textReq = update.getMessage().getText();
            String bin4check = textReq.replace("/bin ", "");
            RestTemplate restTemplate = new RestTemplate();
            String forObject = restTemplate.getForObject("https://lookup.binlist.net/" + bin4check, String.class);

            Bin binObj = JSONObject.parseObject(forObject, Bin.class);

            String prepaid = binObj.isPrepaid() ? "是" : "否";

            String binInfo = "<b>BIN</b> : <strong>[" + bin4check + "]</strong>\n" +
                    "<b>发卡组织</b> : <strong>[" + binObj.getScheme() + "]</strong>\n" +
                    "<b>卡等级</b> : <strong>[" + binObj.getBrand() + "]</strong>\n" +
                    "<b>卡类型</b> : <strong>[" + binObj.getType() + "]</strong>\n" +
                    "<b>预付</b> : <strong>[" + prepaid + "]</strong>\n" +
                    "<b>国家</b> : <strong>[" + binObj.getCountry().getEmoji() + binObj.getCountry().getName() + "]</strong>\n" +
                    "<b>银行</b> : <strong>[" + binObj.getBank().getName() + "]</strong>\n" +
                    "<b>官网</b> : <strong>[" + binObj.getBank().getUrl() + "]</strong>\n" +
                    "<b>电话</b> : <strong>[" + binObj.getBank().getPhone() + "]</strong>";


            // TODO checkBin功能待完成

            String chatId = String.valueOf(update.getMessage().getChatId());
            sendMessage = new SendMessage(chatId, binInfo);
            sendMessage.setReplyToMessageId(messageId);
            sendMessage.setParseMode("html");

            return sendMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return new SendMessage(String.valueOf(update.getMessage().getChatId()),"请确认您发送的指令");
        }
    }
}
