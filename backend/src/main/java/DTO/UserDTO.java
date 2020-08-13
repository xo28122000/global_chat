package DTO;

public class UserDTO implements DTO {
    public String id;
    public final String username;
    public String password;

    public UserDTO(String id, String password, String username){
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
