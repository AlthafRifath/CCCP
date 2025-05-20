package main.java.com.syos.request;

public class CustomerLoginRequest {
    private String email;
    private String password;

    public CustomerLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
