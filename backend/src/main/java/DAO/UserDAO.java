package DAO;

import DTO.DTO;
import DTO.UserDTO;
import Database.DatabaseConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO {

    private static UserDAO userDao;

    private static MongoClient mongoClient = DatabaseConnection.getInstance();
    private static MongoDatabase db = mongoClient.getDatabase("finalProjectDB");
    private static MongoCollection<Document> user_collection = db.getCollection("users");

    private UserDAO(){
        // Empty Constructor
    }

    public static UserDAO getInstance(){
        if(userDao == null){
            userDao = new UserDAO();
            Document user = new Document("id", UUID.randomUUID().toString())
                    .append("username", "default").append("password", 123456);
            user_collection.insertOne(user);
        }

        return userDao;
    }

    public DTO validUser(String username, String password){
        DTO user;
        try{
            MongoCursor<Document> cursor = user_collection.find(new BasicDBObject("username", username)).iterator();
            Document doc;
            if(cursor.hasNext()){
                doc = cursor.next();
                String returnedPassword = doc.get("password").toString();
                if(returnedPassword.equals(password)) {
                    user = new UserDTO(
                            (String) doc.get("id"),
                            null,
                            (String) doc.get("username"));
                }else{
                    user = null;
                }
            }else{
                user = null;
            }
            cursor.close();
        }catch(Exception e){
            System.out.println("ERROR IN USER DAO Valid User method");
            user = null;
        }
        return user;
    }

    public DTO userSignUp(String username, String password){
        DTO newUser = null;
        boolean invalidUser = false;
        String id = UUID.randomUUID().toString();
        MongoCursor<Document> cursor = user_collection.find(new BasicDBObject("username", username)).iterator();
        if(cursor.hasNext())
            invalidUser = true;

        if(!invalidUser && !username.equals("default")) {
            try {
                Document user = new Document("id", id)
                        .append("username", username).append("password", password);
                user_collection.insertOne(user);
                newUser = new UserDTO(id, null, null);
            } catch (Exception e) {
                System.out.println("Failed to insert user to database");
            }
        }

        return newUser;
    }

    public DTO updateUser(String username, String password){
        DTO user;
        Document updatedData = new Document();
        updatedData.append("username", username).append("password", password);
        try{
            MongoCursor<Document> cursor = user_collection.find(new BasicDBObject("username", username)).iterator();
            Document doc;
            if(cursor.hasNext()){
                doc = cursor.next();
                Document update = new Document();
                update.append("$set", updatedData);
                user_collection.updateOne(doc, update);
                    user = new UserDTO(
                            (String) doc.get("id"),
                            null,
                            (String) doc.get("username"));

            }else{
                System.out.println("Reached here in else of update user");
                user = null;
            }
            cursor.close();
        }catch(Exception e){
            System.out.println("ERROR IN USER DAO Updated user method");
            user = null;
    }
        return user;
    }

    public List<DTO> getAllUsers(){
        ArrayList<DTO> usersList = new ArrayList<>();
        MongoCursor<Document> cursor = user_collection.find().iterator();
        Document doc;

        try{
            while (cursor.hasNext()) {
                doc = cursor.next();
                usersList.add(new UserDTO(
                        (String) doc.get("id"),
                        null,
                        (String) (doc.get("username"))));
            }
        } catch (Exception e) {
            System.out.println("GetAllUsers Failed: " + e.toString());
        }finally{
            cursor.close();
        }
        return usersList;
    }
}
