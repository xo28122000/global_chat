package DTO;

public class BroadcastDTO implements DTO {
    public final String typeOfMessage;
    public final DTO payload;

    public BroadcastDTO(String typeOfMessage, DTO payload) {
        this.typeOfMessage = typeOfMessage;
        this.payload = payload;
    }
}
