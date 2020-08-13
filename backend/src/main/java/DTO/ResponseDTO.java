package DTO;

public class ResponseDTO {
    private final boolean success;
    private final DTO payload;

    public ResponseDTO(boolean success, DTO payload){
        this.success = success;
        this.payload = payload;
    }
}
