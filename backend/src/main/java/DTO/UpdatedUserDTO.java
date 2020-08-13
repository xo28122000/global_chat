package DTO;

public class UpdatedUserDTO implements DTO {
    public String id;
    public final String username;
    public String password;
    public final String newPassword;

    public UpdatedUserDTO(String id, String password, String username, String newPassword){
        this.id = id;
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
    }
}
