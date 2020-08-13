package Processor;
import DAO.UserDAO;
import DTO.DTO;
import DTO.ResponseDTO_withDTOList;
import DTO.ResponseDTO_withDTOList_helper;

import java.util.List;

public class UsersProcessor implements ProcessorMessages {
    boolean success = false;
    List<DTO> payload = null;
    UserDAO userDao = UserDAO.getInstance();

    @Override
    public ResponseDTO_withDTOList process() {
        ResponseDTO_withDTOList_helper rbh = new ResponseDTO_withDTOList_helper();

        payload = userDao.getAllUsers();
        if(payload!=null){
            success = true;
        }
        rbh.setSuccess(success);
        rbh.setPayload(payload);
        return rbh.build();
    }
}
