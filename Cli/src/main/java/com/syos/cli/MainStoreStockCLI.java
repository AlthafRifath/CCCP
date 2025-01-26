package main.java.com.syos.cli;

import main.java.com.syos.dto.GetMainStoreStockDetailsDTO;
import main.java.com.syos.request.InsertMainStoreStockRequest;
import main.java.com.syos.service.MainStoreStockService;
import main.java.com.syos.service.interfaces.IMainStoreStockService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

public class MainStoreStockCLI {
    private final IMainStoreStockService mainStoreStockService;

    public MainStoreStockCLI() {
        this.mainStoreStockService = new MainStoreStockService();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Inventory Management ===");
            System.out.println("1. Add Main Store Stock");
            System.out.println("2. View Main Store Stock Item");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> insertMainStoreStock();
                case 2 -> viewMainStoreStockDetails();
//                case 3 -> updateItem();
//                case 4 -> deleteItem();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void insertMainStoreStock() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Store ID:");
        int storeId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Item Code:");
        String itemCode = scanner.nextLine();

        System.out.println("Enter Batch Code:");
        String batchCode = scanner.nextLine();

        System.out.println("Enter Initial Stock:");
        int initialStock = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Current Stock:");
        int currentStock = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Last Restocked Date (yyyy-MM-dd):");
        String lastRestockedDate = scanner.nextLine();
        LocalDateTime lastRestockedDateTime = LocalDateTime.parse(lastRestockedDate + "T00:00:00");

        InsertMainStoreStockRequest request = new InsertMainStoreStockRequest(
                storeId, itemCode, batchCode, initialStock, currentStock, lastRestockedDateTime
        );

        mainStoreStockService.insertMainStoreStock(request);
        System.out.println("Main Store Stock created successfully!");
    }

    public void viewMainStoreStockDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Store ID:");
        int storeId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Item Code:");
        String itemCode = scanner.nextLine();

        System.out.println("Enter Batch Code:");
        String batchCode = scanner.nextLine();

        Optional<GetMainStoreStockDetailsDTO> stockDetails = mainStoreStockService.getMainStoreStockDetails(storeId, itemCode, batchCode);

        if (stockDetails.isPresent()) {
            System.out.println("Stock Details:");
            System.out.println(stockDetails.get());
        } else {
            System.out.println("No stock found for the provided Store ID, Item Code, and Batch Code.");
        }
    }
}
