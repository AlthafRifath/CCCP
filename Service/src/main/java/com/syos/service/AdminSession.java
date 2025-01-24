package main.java.com.syos.service;

public class AdminSession {
    private static AdminSession instance;
    private String loggedInUser;

    private AdminSession() {
        // Private constructor
    }

    public static synchronized AdminSession getInstance() {
        if (instance == null) {
            instance = new AdminSession();
        }
        return instance;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void logout() {
        loggedInUser = null;
    }
}
