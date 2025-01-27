package main.java.com.syos.cli;

import main.java.com.syos.dto.GetShelfDetailsDTO;
import main.java.com.syos.request.DeleteShelfRequest;
import main.java.com.syos.request.InsertShelfRequest;
import main.java.com.syos.request.UpdateShelfRequest;
import main.java.com.syos.service.ShelfService;
import main.java.com.syos.service.interfaces.IShelfService;

import java.time.LocalDateTime;
import java.util.List;
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
                case 2 -> handleViewShelfByShelfIdAndStoreId();
                case 3 -> handleUpdateShelf();
                case 4 -> handleDeleteShelf();
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

        System.out.println("Enter StoreID for MainStoreStock:");
        int storeIdFromMainStoreStock = scanner.nextInt();

        System.out.println("Enter StoreID for the Shelf:");
        int storeIdFromStore = scanner.nextInt();

        System.out.println("Enter ShelfID:");
        int shelfId = scanner.nextInt();

        System.out.println("Enter ItemCode:");
        String itemCode = scanner.next();

        System.out.println("Enter BatchCode:");
        String batchCode = scanner.next();

        System.out.println("Enter Quantity On Shelf:");
        int quantityOnShelf = scanner.nextInt();

        InsertShelfRequest request = new InsertShelfRequest();
        request.setStoreIdFromMainStoreStock(storeIdFromMainStoreStock);
        request.setStoreIdFromStore(storeIdFromStore);
        request.setShelfId(shelfId);
        request.setItemCode(itemCode);
        request.setBatchCode(batchCode);
        request.setQuantityOnShelf(quantityOnShelf);

        shelfService.addShelf(request);
    }

    private void handleViewShelfByShelfIdAndStoreId() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ShelfID:");
        int shelfId = scanner.nextInt();

        System.out.println("Enter StoreID:");
        int storeId = scanner.nextInt();

        List<GetShelfDetailsDTO> shelves = shelfService.getShelfDetailsByShelfIdAndStoreId(shelfId, storeId);
        if (shelves.isEmpty()) {
            System.out.println("No shelves found for ShelfID: " + shelfId + " and StoreID: " + storeId);
        } else {
            System.out.println("Shelf Details for ShelfID: " + shelfId + " and StoreID: " + storeId);
            for (GetShelfDetailsDTO shelf : shelves) {
                System.out.println("StoreID: " + storeId);
                System.out.println("  ItemName: " + shelf.getItemName());
                System.out.println("  ItemCode: " + shelf.getItemCode());
                System.out.println("  BatchCode: " + shelf.getBatchCode());
                System.out.println("  Quantity On Shelf: " + shelf.getQuantityOnShelf());
                System.out.println("  Last Restocked Date: " + shelf.getLastRestockedDate());
                System.out.println("-----------------------------");
            }
        }
    }

    private void handleUpdateShelf() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter StoreID for Shelf:");
        int shelfStoreId = scanner.nextInt();

        System.out.println("Enter ShelfID:");
        int shelfId = scanner.nextInt();

        System.out.println("Enter ItemCode:");
        String itemCode = scanner.next();

        System.out.println("Enter BatchCode:");
        String batchCode = scanner.next();

        System.out.println("Enter StoreID for MainStoreStock:");
        int mainStoreStockStoreId = scanner.nextInt();

        System.out.println("Enter Quantity to Add to Shelf:");
        int quantityToAdd = scanner.nextInt();

        UpdateShelfRequest request = new UpdateShelfRequest();
        request.setStoreId(shelfStoreId);
        request.setShelfId(shelfId);
        request.setItemCode(itemCode);
        request.setBatchCode(batchCode);
        request.setStoreIdFromMainStoreStock(mainStoreStockStoreId);
        request.setQuantityToAdd(quantityToAdd);

        shelfService.updateShelf(request);
    }

    private void handleDeleteShelf() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter StoreID:");
        int storeId = scanner.nextInt();

        System.out.println("Enter ShelfID:");
        int shelfId = scanner.nextInt();

        System.out.println("Enter ItemCode:");
        String itemCode = scanner.next();

        System.out.println("Enter BatchCode:");
        String batchCode = scanner.next();

        DeleteShelfRequest request = new DeleteShelfRequest();
        request.setStoreId(storeId);
        request.setShelfId(shelfId);
        request.setItemCode(itemCode);
        request.setBatchCode(batchCode);

        shelfService.deleteShelf(request);
    }

}
