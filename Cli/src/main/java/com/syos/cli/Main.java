package main.java.com.syos.cli;
import org.jboss.logging.Logger;

public class Main {
    public static void main(String[] args) {
        AuthenticationCLI authenticationCLI = new AuthenticationCLI();
        boolean isAuthenticated = authenticationCLI.start();

        if (isAuthenticated) {
            System.out.println("Welcome to the Inventory Management System!");
//            ItemCLI itemCLI = new ItemCLI();
//            itemCLI.start();
            MainStoreStockCLI mainStoreStockCLI = new MainStoreStockCLI();
            mainStoreStockCLI.start();
        } else {
            System.out.println("Exiting the application. Please try logging in again.");
        }
    }
}
