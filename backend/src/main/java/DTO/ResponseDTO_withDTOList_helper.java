package DTO;

import java.util.List;

public class ResponseDTO_withDTOList_helper {
    private boolean success;
    private List<DTO> payload;

    public ResponseDTO_withDTOList_helper(){
        //Empty Constructor
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public void setPayload(List<DTO> payload){
        this.payload = payload;
    }

    public ResponseDTO_withDTOList build(){
        return new ResponseDTO_withDTOList(success, payload);
    }
}
