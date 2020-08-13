package DTO;

import java.util.List;

public class ResponseDTO_withDTOList {
    private final boolean success;
    private final List<DTO> payload;

    public ResponseDTO_withDTOList(boolean success, List<DTO> payload){
        this.success = success;
        this.payload = payload;
    }
}
