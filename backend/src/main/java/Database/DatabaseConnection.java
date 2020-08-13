package Database;
import com.mongodb.MongoClient;

public class DatabaseConnection {
    private static MongoClient mongoClient;

    private DatabaseConnection() {
        //Private Empty Constructor
    }

    public static MongoClient getInstance() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
        }
        return mongoClient;
    }
}
