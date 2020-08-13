package DTO;

public class ErrorDTO implements DTO{
    public final String message;

    public ErrorDTO(String message) {
        this.message = message;
    }
}
