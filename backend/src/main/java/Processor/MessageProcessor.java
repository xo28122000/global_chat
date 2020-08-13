package Processor;

import DAO.MessageDAO;
import DTO.*;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;

public class MessageProcessor implements ProcessorBroadcast {
    Gson gson = new Gson();
    String rawMessage = null;
    InternalMessageDTO messageDto = null;
    MessageResPayloadDTO messageResPayloadDto = null;
    BroadcastDTO_helper broadcastDTOhelper = new BroadcastDTO_helper();

    public MessageProcessor(String message){
        this.rawMessage = message;
        messageDto = gson.fromJson(message, InternalMessageDTO.class);
        messageDto.date = Calendar.getInstance().getTime().toString();
        messageDto.likes = new ArrayList<>();
    }

    public BroadcastDTO process() {

        if(messageDto.likeFlag == 0){

            //if no like flag passed in then process the message as a new one.
            if(messageDto.username == null || messageDto.body == null) {
                System.out.println("** username or message have not been passed in properly! **");
                broadcastDTOhelper.setTypeOfMessage("ERROR");
                broadcastDTOhelper.setPayload(new ErrorDTO("username or message have not been passed in properly!"));
            }else{
                //else process it as a message update for the like.

                //insert new message into the DB and get messageId to be returned in response.
                String messageId = MessageDAO.getInstance().insertMessage(messageDto);

                broadcastDTOhelper.setTypeOfMessage("MESSAGE");
                broadcastDTOhelper.setPayload(new MessageResPayloadDTO(messageId, messageDto.username, messageDto.body, messageDto.likeCount, messageDto.date, messageDto.likes));
            }

        }else{
            if(messageDto.id == null || messageDto.username == null) {
                System.out.println("** messageId and/or username have not been provided properly! **");
                broadcastDTOhelper.setTypeOfMessage("ERROR");
                broadcastDTOhelper.setPayload(new ErrorDTO("messageId and/or username have not been provided properly!"));
            }else {
                broadcastDTOhelper.setTypeOfMessage("LIKE");
                broadcastDTOhelper.setPayload(MessageDAO.getInstance().updateLikeCount(messageDto.id, messageDto.username, messageDto.likeFlag));
            }

        }
        return broadcastDTOhelper.build();
    }
}
