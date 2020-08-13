package Processor;

import DAO.UserDAO;
import DTO.DTO;
import DTO.ErrorDTO;
import DTO.ResponseDTO;
import DTO.ResponseDTOhelper;
import DTO.UserDTO;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class SignInProcessor implements Processor {
    Request req;
    Response res;

    public SignInProcessor(Request req, Response res) {
        this.req = req;
        this.res = res;
    }

    @Override
    public ResponseDTO process() {
        DTO payload = null;
        ResponseDTOhelper rbh = new ResponseDTOhelper();
        boolean success = false;
        Gson gson = new Gson();
        UserDAO userDao = UserDAO.getInstance();
        String bodyString = req.body();
        try{
            UserDTO userDTO = gson.fromJson(bodyString, UserDTO.class);
            payload = userDao.validUser(userDTO.username, userDTO.password);
            if(payload!=null){
                success = true;
            }
            else{
                payload = new ErrorDTO("Wrong username or password");
            }
        }catch(Exception e){
            System.out.println("Failed To Execute LoginProcessor class");
        }

        rbh.setSuccess(success);
        rbh.setPayload(payload);
        return rbh.build();
    }
}
