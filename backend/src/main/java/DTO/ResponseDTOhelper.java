package DTO;

public class ResponseDTOhelper {
    private boolean success;
    private DTO payload;

    public ResponseDTOhelper(){
        //Empty Constructor
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public void setPayload(DTO payload){
        this.payload = payload;
    }

    public ResponseDTO build(){
        return new ResponseDTO(success, payload);
    }
}
