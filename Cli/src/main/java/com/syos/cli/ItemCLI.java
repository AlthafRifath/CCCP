package main.java.com.syos.cli;

import main.java.com.syos.data.model.Item;
import main.java.com.syos.dto.GetItemDTO;
import main.java.com.syos.service.ItemService;
import main.java.com.syos.request.InsertItemRequest;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ItemCLI {
    private final ItemService itemService;

    public ItemCLI() {
        this.itemService = new ItemService();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Inventory Management ===");
            System.out.println("1. Add Item");
            System.out.println("2. View Item");
            System.out.println("3. List All Items");
            System.out.println("4. Exit");
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
                case 2 -> getItemDetails();
                case 3 -> listAllItems();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addItem(Scanner scanner) {
        System.out.print("Enter Item Code: ");
        String itemCode = scanner.nextLine();

        System.out.print("Enter Batch Code: ");
        String batchCode = scanner.nextLine();

        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter Price: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid price.");
            scanner.next();
        }
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Purchase Date (yyyy-MM-dd): ");
        String purchaseDateInput = scanner.nextLine();
        LocalDateTime purchaseDate = LocalDateTime.parse(purchaseDateInput + "T00:00:00");

        System.out.print("Enter Expiry Date (yyyy-MM-dd): ");
        String expiryDateInput = scanner.nextLine();
        LocalDateTime expiryDate = LocalDateTime.parse(expiryDateInput + "T00:00:00");

        System.out.print("Enter Initial Quantity: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid quantity.");
            scanner.next();
        }
        int initialQuantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Current Quantity: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid quantity.");
            scanner.next();
        }
        int currentQuantity = scanner.nextInt();
        scanner.nextLine();

        InsertItemRequest request = new InsertItemRequest(
                itemCode, batchCode, itemName, price, purchaseDate, expiryDate, initialQuantity, currentQuantity
        );

        itemService.InsertItem(request);
        System.out.println("Item created successfully!");
    }

    public void getItemDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ItemCode:");
        String itemCode = scanner.nextLine();

        System.out.println("Enter BatchCode:");
        String batchCode = scanner.nextLine();

        try {
            GetItemDTO item = itemService.getItemByItemCodeAndBatchCode(itemCode, batchCode);
            printItemDetails(item);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private void printItemDetails(GetItemDTO item) {
        System.out.println("Item Details:");
        System.out.println("ItemCode: " + item.getItemCode());
        System.out.println("BatchCode: " + item.getBatchCode());
        System.out.println("ItemName: " + item.getItemName());
        System.out.println("Price: " + item.getPrice());
        System.out.println("PurchaseDate: " + item.getPurchaseDate());
        System.out.println("ExpiryDate: " + item.getExpiryDate());
        System.out.println("InitialQuantity: " + item.getInitialQuantity());
        System.out.println("CurrentQuantity: " + item.getCurrentQuantity());
    }

    private void listAllItems() {
        itemService.getAllItems().forEach(System.out::println);
    }
}
