package DTO;

import java.util.ArrayList;

public class MessageResPayloadDTO implements DTO {
    public final String id;
    public final String username;
    public final String body;
    public int likeCount;
    public final String date;
    public ArrayList<String> likes;

    public MessageResPayloadDTO(String id, String username, String body, int likeCount, String date, ArrayList<String> likes) {
        this.id = id;
        this.username = username;
        this.body = body;
        this.likeCount = likeCount;
        this.date = date;
        this.likes = likes;
    }
}
