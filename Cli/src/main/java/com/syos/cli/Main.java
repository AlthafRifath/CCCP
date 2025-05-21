package main.java.com.syos.cli;

import main.java.com.syos.service.ReportService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthenticationCLI authenticationCLI = new AuthenticationCLI();
        boolean isAuthenticated = authenticationCLI.start();
        ReportService reportService = new ReportService();
        Scanner scanner = new Scanner(System.in);

        if (isAuthenticated) {
            System.out.println("Welcome to the Inventory Management System!");

            while (true) {
                System.out.println("\nSelect an option:");
                System.out.println("1. Manage Items");
                System.out.println("2. Manage Main Store Stock");
                System.out.println("3. Manage Shelf Stock");
                System.out.println("4. Manage Bills");
                System.out.println("5. Generate Reports");
                System.out.println("6. Manage Web Shop Inventory");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");

                int choice;
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // discard invalid input
                    continue;
                }

                switch (choice) {
                    case 1:
                        ItemCLI itemCLI = new ItemCLI();
                        itemCLI.start();
                        break;
                    case 2:
                        MainStoreStockCLI mainStoreStockCLI = new MainStoreStockCLI();
                        mainStoreStockCLI.start();
                        break;
                    case 3:
                        ShelfCLI shelfCLI = new ShelfCLI();
                        shelfCLI.start();
                        break;
                    case 4:
                        BillCLI billCLI = new BillCLI();
                        billCLI.start();
                        break;
                    case 5:
                        ReportCLI reportCLI = new ReportCLI(
                                reportService,  // Sales Report
                                reportService,  // Stock Report
                                reportService,  // Reorder Level Report
                                reportService   // Bill Report
                        );
                        reportCLI.start();
                        break;
                    case 6:
                        WebShopInventoryCLI webShopInventoryCLI = new WebShopInventoryCLI();
                        webShopInventoryCLI.start();
                        break;
                    case 7:
                        System.out.println("Exiting the application. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } else {
            System.out.println("Exiting the application. Please try logging in again.");
        }
    }
}