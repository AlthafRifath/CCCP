package main.java.com.syos.cli;

import main.java.com.syos.dto.WebShopInventoryDTO;
import main.java.com.syos.request.InsertWebShopItemRequest;
import main.java.com.syos.request.UpdateWebShopItemRequest;
import main.java.com.syos.service.WebShopInventoryService;
import main.java.com.syos.service.interfaces.IWebShopInventoryService;

import java.util.List;
import java.util.Scanner;

public class WebShopInventoryCLI {
    private final IWebShopInventoryService inventoryService;

    public WebShopInventoryCLI() {
        this.inventoryService = new WebShopInventoryService();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Web Shop Inventory Management ===");
            System.out.println("1. Add Web Shop Item");
            System.out.println("2. View All Items");
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
                case 1 -> addItem(scanner);
                case 2 -> viewAllItems();
                case 3 -> updateItem(scanner);
                case 4 -> deleteItem(scanner);
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addItem(Scanner scanner) {
        System.out.print("Enter WebShop ID: ");
        int webShopId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Item Code: ");
        String itemCode = scanner.nextLine();

        System.out.print("Enter Batch Code: ");
        String batchCode = scanner.nextLine();

        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter Quantity Online: ");
        int quantityOnline = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Image URL (local path or full URL): ");
        String imageUrl = scanner.nextLine();

        InsertWebShopItemRequest request = new InsertWebShopItemRequest(
                webShopId, itemCode, batchCode, itemName, quantityOnline, imageUrl
        );

        inventoryService.insertItem(request);
        System.out.println("Web shop item added successfully!");
    }

    private void viewAllItems() {
        List<WebShopInventoryDTO> items = inventoryService.getAllItems();
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        System.out.println("\n=== Web Shop Inventory ===");
        for (WebShopInventoryDTO item : items) {
            System.out.println("WebShopID: " + item.getWebShopId());
            System.out.println("ItemCode: " + item.getItemCode());
            System.out.println("BatchCode: " + item.getBatchCode());
            System.out.println("ItemName: " + item.getItemName());
            System.out.println("QuantityOnline: " + item.getQuantityOnline());
            System.out.println("Price: " + item.getPrice());
            System.out.println("ImageURL: " + item.getImageUrl());
            System.out.println("-----------------------------------");
        }
    }

    private void updateItem(Scanner scanner) {
        System.out.print("Enter WebShop ID: ");
        int webShopId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Item Code: ");
        String itemCode = scanner.nextLine();

        System.out.print("Enter Batch Code: ");
        String batchCode = scanner.nextLine();

        System.out.print("Enter New Item Name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter New Quantity Online: ");
        int quantityOnline = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter New Image URL: ");
        String imageUrl = scanner.nextLine();

        UpdateWebShopItemRequest request = new UpdateWebShopItemRequest(
                webShopId, itemCode, batchCode, itemName, quantityOnline, imageUrl
        );

        try {
            inventoryService.updateItem(request);
            System.out.println("Item updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void deleteItem(Scanner scanner) {
        System.out.print("Enter WebShop ID: ");
        int webShopId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Item Code: ");
        String itemCode = scanner.nextLine();

        System.out.print("Enter Batch Code: ");
        String batchCode = scanner.nextLine();

        try {
            inventoryService.deleteItem(webShopId, itemCode, batchCode);
            System.out.println("Item deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
