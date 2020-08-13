package Processor;

import DAO.MessageDAO;
import DTO.ResponseDTO_withDTOList_helper;
import DTO.ResponseDTO_withDTOList;

public class getMessagesProcessor implements ProcessorMessages {
    @Override
    public ResponseDTO_withDTOList process() {
        ResponseDTO_withDTOList_helper resHelper = new ResponseDTO_withDTOList_helper();
        resHelper.setSuccess(true);
        resHelper.setPayload(MessageDAO.getInstance().getAllMessages());
        return resHelper.build();
    }
}
