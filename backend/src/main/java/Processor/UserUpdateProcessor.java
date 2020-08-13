package Processor;

import DAO.UserDAO;
import DTO.DTO;
import DTO.ErrorDTO;
import DTO.UpdatedUserDTO;
import DTO.ResponseDTOhelper;
import DTO.ResponseDTO;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class UserUpdateProcessor implements Processor {
    Request req;
    Response res;

    public UserUpdateProcessor(Request req, Response res) {
        this.req = req;
        this.res = res;
    }

    @Override
    public ResponseDTO process() {
        DTO payload = null;
        DTO validUser = null;
        ResponseDTOhelper rbh = new ResponseDTOhelper();
        boolean success = false;
        Gson gson = new Gson();
        UserDAO userDao = UserDAO.getInstance();
        String bodyString = req.body();
        try {
            UpdatedUserDTO userDTO = gson.fromJson(bodyString, UpdatedUserDTO.class);
            validUser = userDao.validUser(userDTO.username, userDTO.password);
            if(validUser==null){
                payload = new ErrorDTO("You entered your current password incorrectly");
            }else {
                payload = userDao.updateUser(userDTO.username, userDTO.newPassword);

                if(payload!=null)
                    success = true;
            }
        }catch(Exception e){
            System.out.println("Error in User Updating");
        }
        rbh.setSuccess(success);
        rbh.setPayload(payload);
        return rbh.build();
    }
}
