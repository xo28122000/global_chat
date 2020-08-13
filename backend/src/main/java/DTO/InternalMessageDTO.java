package DTO;

import java.util.ArrayList;

public class InternalMessageDTO implements DTO {
    public final String id;
    public final String username;
    public final String body;
    public final int likeFlag;
    public int likeCount;
    public String date;
    public ArrayList<String> likes;

    public InternalMessageDTO(String id, String username, String body, int likeFlag, int likeCount, String date, ArrayList<String> likes) {
        this.id = id;
        this.username = username;
        this.body = body;
        this.likeFlag = likeFlag;
        this.likeCount = likeCount;
        this.date = date;
        this.likes = likes;
    }
}
