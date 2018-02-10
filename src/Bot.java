import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Bot extends TelegramLongPollingBot{
    final String BOT_USERNAME = "instaFAQ_bot";
    final String BOT_TOKEN = "514827275:AAFlyESPn8KiWuLYGjIAbf6-9ZHzfW5HMfw";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(new Bot());
        } catch (TelegramApiRequestException e) { e.printStackTrace(); }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if(message!=null&&message.hasText()){
            if(message.getText().equals("/start")){
                sendMsg(message,"Задайте свой вопрос. И в одном из следующих выпусков, мы его обязательно рассмотрим!");
            }else {
                if (message.getText().equals("/help")) {
                    sendMsg(message, "Бот канала Инстаграмотность." +
                            " Здесь вы можете задать свои вопросы по Instagram, и в одном из выпусков получить ответ на них");
                } else {
                    sendMsg(message, "Спасибо за вопрос , мы обязательно его рассмотрим!");
                    sendMsgToMe(message);
                }
            }
        }

    }

    public void sendMsgToMe(Message message){
        ForwardMessage forwardMessage = new ForwardMessage();
        forwardMessage.setChatId("245217438");
        forwardMessage.setMessageId(message.getMessageId());
        forwardMessage.setFromChatId(message.getChatId());
        try {
            execute(forwardMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message , String text){
        SendMessage sendMessage = new SendMessage();
        long chat_id = message.getChatId();
        sendMessage.setText(text);
        sendMessage.setChatId(chat_id);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
