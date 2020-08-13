package DAO;

import DTO.*;
import Database.DatabaseConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class MessageDAO {
    private static MongoClient mongoClient = DatabaseConnection.getInstance();
    private static MongoCollection messagesCollection = null;
    private static final String DBName = "finalProjectDB";
    private static final String CollectionName = "Messages";
    private static MessageDAO messageDAO = null;

    private MessageDAO(){

    }

    public static MessageDAO getInstance(){
        if(messageDAO == null){
            messageDAO = new MessageDAO();
            messageDAO.insertMessage(new InternalMessageDTO(null, "default", "Hi I'm a template message from the default user.", 0, 0, Calendar.getInstance().getTime().toString(), null));
        }

        return messageDAO;
    }


    //This will return the message ID of the message created for inc and dec likeCount later.
    public String insertMessage(InternalMessageDTO message){
        MongoClient mongoClient = DatabaseConnection.getInstance();

        //For lazy loading
        if(messagesCollection == null) messagesCollection = mongoClient.getDatabase(DBName).getCollection(CollectionName);

        Document messageDoc = new Document();

        //Build the message that will be inserted into the database.
        messageDoc
                .append("username", message.username)
                .append("body", message.body)
                .append("likeCount", message.likeCount)
                .append("likes", message.likes == null ? new ArrayList<>() : message.likes)
                .append("date", message.date);
        try {
            messagesCollection.insertOne(messageDoc);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Iterable<Document> messagesFound = messagesCollection.find(messageDoc);

        return messagesFound.iterator().next().get("_id").toString();
    }


    //Updates likeCount.
    public DTO updateLikeCount(String messageId, String username, int likeFlag){
        MongoClient mongoClient = DatabaseConnection.getInstance();

        //For lazy loading
        if(messagesCollection == null) messagesCollection = mongoClient.getDatabase(DBName).getCollection(CollectionName);

        //This will be the document used to return the Res dto.
        MongoCursor messageFound = messagesCollection.find(new Document( "_id", new ObjectId(messageId))).iterator();
        Document message = (Document) messageFound.next();

        if((int) message.get("likeCount") <= 0 && likeFlag == -1) return new ErrorDTO("The count is already 0 you cannot go any lower than that!");

        //updating the message to increment or decrement by the amount specified in the flag passed in.
        messagesCollection.updateOne(eq("_id", new ObjectId(messageId)), new Document("$inc", new Document("likeCount", likeFlag)));

        //remove or add to the likes array of username's.
        if(likeFlag == 1){
            messagesCollection.updateOne(eq("_id", new ObjectId(messageId)), new Document("$push", new Document("likes", username)));
        } else {
            messagesCollection.updateOne(eq("_id", new ObjectId(messageId)), new Document("$pull", new Document("likes", username)));
        }

        messageFound = messagesCollection.find(new Document( "_id", new ObjectId(messageId))).iterator();
        message = (Document) messageFound.next();
        MessageResPayloadDTO responseDTO = new MessageResPayloadDTO(
                message.get("_id").toString(),
                message.get("username").toString(),
                message.get("body").toString(),
                (int) message.get("likeCount"),
                message.get("date").toString(),
                (ArrayList<String>) message.get("likes"));

        return responseDTO;
    }


    public List<DTO> getAllMessages(){
        MongoClient mongoClient = DatabaseConnection.getInstance();

        //For lazy loading
        if(messagesCollection == null) messagesCollection = mongoClient.getDatabase(DBName).getCollection(CollectionName);

        //The list to be returned.
        List<DTO> messageDTOs = new ArrayList<DTO>();

        //Document Iterator used to go over each document.
        MongoCursor messagesIterator = messagesCollection.find().iterator();

        Document message;

        while (messagesIterator.hasNext()){
            message = (Document) messagesIterator.next();

            MessageResPayloadDTO messageResDTO = new MessageResPayloadDTO(
                    message.get("_id").toString(),
                    message.get("username").toString(),
                    message.get("body").toString(),
                    (Integer) message.get("likeCount"),
                    message.get("date").toString(),
                    (ArrayList<String>) message.get("likes")
            );

            messageDTOs.add(messageResDTO);
        }


        return messageDTOs;
    }
}
