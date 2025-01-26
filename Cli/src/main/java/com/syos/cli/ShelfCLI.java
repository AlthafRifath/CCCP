package main.java.com.syos.cli;

import main.java.com.syos.request.InsertShelfRequest;
import main.java.com.syos.service.ShelfService;
import main.java.com.syos.service.interfaces.IShelfService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ShelfCLI {
    private final IShelfService shelfService;

    public ShelfCLI() {
        this.shelfService = new ShelfService();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Shelf Management ===");
            System.out.println("1. Add Stock to Shelf");
            System.out.println("2. View Shelf");
            System.out.println("3. Update Shelf");
            System.out.println("4. Delete Shelf");
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
                case 1 -> handleAddShelf();
//                case 2 -> viewMainStoreStockDetails();
//                case 3 -> updateItem();
//                case 4 -> deleteMainStoreStock();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void handleAddShelf() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ShelfID:");
        int shelfId = scanner.nextInt();

        System.out.println("Enter ItemCode:");
        String itemCode = scanner.next();

        System.out.println("Enter BatchCode:");
        String batchCode = scanner.next();

        System.out.println("Enter Quantity On Shelf:");
        int quantityOnShelf = scanner.nextInt();

        InsertShelfRequest request = new InsertShelfRequest();
        request.setShelfId(shelfId);
        request.setItemCode(itemCode);
        request.setBatchCode(batchCode);
        request.setQuantityOnShelf(quantityOnShelf);
        request.setLastRestockedDate(LocalDateTime.now());

        shelfService.addShelf(request);
    }
}
