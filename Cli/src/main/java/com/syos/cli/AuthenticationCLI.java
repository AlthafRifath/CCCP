package main.java.com.syos.cli;

import main.java.com.syos.dto.LoginDTO;
import main.java.com.syos.request.LoginRequest;
import main.java.com.syos.service.AdminSession;
import main.java.com.syos.service.AuthenticationService;
import main.java.com.syos.service.interfaces.IAuthenticationService;

import java.util.Scanner;

public class AuthenticationCLI {
    private final IAuthenticationService authenticationService;

    public AuthenticationCLI() {
        this.authenticationService = new AuthenticationService();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Admin Login");
            System.out.println("Enter username: ");
            String username = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            LoginRequest loginRequest = new LoginRequest(username, password);
            LoginDTO response = authenticationService.login(loginRequest);

            if (response.isSuccess()) {
                System.out.println(response.getMessage());
                postLoginMenu();
                break;
            } else {
                System.out.println(response.getMessage());
            }
        }
    }

    private void postLoginMenu() {
        System.out.println("Welcome, " + AdminSession.getInstance().getLoggedInUser());
        System.out.println("1. Logout");
        System.out.println("2. Exit");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            AdminSession.getInstance().logout();
            System.out.println("Logged out successfully.");
            start();
        } else {
            System.out.println("Exiting...");
        }
    }
}
