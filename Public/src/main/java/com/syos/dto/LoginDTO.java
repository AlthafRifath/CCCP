package main.java.com.syos.dto;

public class LoginDTO {
    private boolean success;
    private String message;

    public LoginDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
