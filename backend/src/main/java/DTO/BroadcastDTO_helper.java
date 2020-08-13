package DTO;

public class BroadcastDTO_helper {
    public String typeOfMessage;
    public DTO payload;

    public BroadcastDTO_helper(){
        //Empty Constructor
    }

    public void setTypeOfMessage(String typeOfMessage){
        this.typeOfMessage = typeOfMessage;
    }

    public void setPayload(DTO payload){
        this.payload = payload;
    }

    public BroadcastDTO build(){
        return new BroadcastDTO(typeOfMessage, payload);
    }
}
