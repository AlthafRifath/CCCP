package main.java.com.syos.service;

public class AdminSession {
    private static AdminSession instance;
    private Integer loggedInUserId;

    private AdminSession() {
        // Private constructor
    }

    public static synchronized AdminSession getInstance() {
        if (instance == null) {
            instance = new AdminSession();
        }
        return instance;
    }

    public Integer getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(Integer loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public void logout() {
        loggedInUserId = null;
    }
}
