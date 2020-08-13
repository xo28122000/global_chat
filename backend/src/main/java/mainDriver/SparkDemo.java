package mainDriver;

import Processor.*;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class SparkDemo {
    public static void main(String[] args) {
        port(1235);
        webSocket("/ws", WebSocketHandler.class);
        post("/auth/signin", SparkDemo::signin);
        post("/auth/signup", SparkDemo::signup);
        post("/auth/updateUser", SparkDemo::updateUser);
        get("/getMessages", SparkDemo::getMessages);
        get("/allUsers", SparkDemo::getAllUsers);
    }

    private static Object signin(Request req, Response res) {
      return new Gson().toJson(new SignInProcessor(req, res).process());
    }

    private static Object signup(Request req, Response res) {
      return new Gson().toJson(new SignUpProcessor(req, res).process());
    }

    private static Object updateUser(Request req, Response res) {
        return new Gson().toJson(new UserUpdateProcessor(req, res).process());
    }

    private static Object getMessages(Request req, Response res) {
        return new Gson().toJson(new getMessagesProcessor().process());
    }

    private static Object getAllUsers(Request req, Response res){
        return new Gson().toJson(new UsersProcessor().process());
    }
}
