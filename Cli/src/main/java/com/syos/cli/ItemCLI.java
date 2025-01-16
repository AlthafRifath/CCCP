package main.java.com.syos.cli;

import main.java.com.syos.data.model.Item;
import main.java.com.syos.service.ItemService;

import java.util.Date;
import java.util.Scanner;

import static jakarta.xml.bind.DatatypeConverter.parseDate;

public class ItemCLI {
    private final ItemService itemService;

    public ItemCLI() {
        this.itemService = new ItemService();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Item");
            System.out.println("2. View Item");
            System.out.println("3. List All Items");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addItem(scanner);
                case 2 -> viewItem(scanner);
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
        System.out.println("Enter Item Details:");

        System.out.print("Enter Item Code: ");
        String itemCode = scanner.next();

        System.out.print("Enter Batch Code: ");
        String batchCode = scanner.next();

        System.out.print("Enter Item Name: ");
        String itemName = scanner.next();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Purchase Date (yyyy-MM-dd): ");
        String purchaseDateInput = scanner.next();
        Date purchaseDate = parseDate(purchaseDateInput).getTime();

        System.out.print("Enter Expiry Date (yyyy-MM-dd): ");
        String expiryDateInput = scanner.next();
        Date expiryDate = parseDate(expiryDateInput).getTime();

        System.out.print("Enter Initial Quantity: ");
        int initialQuantity = scanner.nextInt();

        System.out.print("Enter Current Quantity: ");
        int currentQuantity = scanner.nextInt();

        System.out.print("Is Active? (true/false): ");
        boolean isActive = scanner.nextBoolean();

        System.out.print("Is Deleted? (true/false): ");
        boolean isDeleted = scanner.nextBoolean();

        System.out.print("Enter Updated By (User ID): ");
        Integer updatedBy = scanner.hasNextInt() ? scanner.nextInt() : null;

        System.out.print("Enter Updated DateTime (yyyy-MM-dd): ");
        String updatedDateTimeInput = scanner.next();
        Date updatedDateTime = parseDate(updatedDateTimeInput).getTime();

        // Create Item instance
        Item item = new Item(
                itemCode, batchCode, itemName, price, purchaseDate, expiryDate,
                initialQuantity, currentQuantity, isActive, isDeleted,
                updatedBy, updatedDateTime
        );

        // Save the Item
        itemService.saveItem(item);
        System.out.println("Item saved successfully.");
    }

    private void viewItem(Scanner scanner) {
        System.out.print("Enter Item Code: ");
        String itemCode = scanner.next();
        System.out.print("Enter Batch Code: ");
        String batchCode = scanner.next();

        Item item = itemService.getItem(itemCode, batchCode);
        if (item != null) {
            System.out.println(item);
        } else {
            System.out.println("Item not found.");
        }
    }

    private void listAllItems() {
        itemService.getAllItems().forEach(System.out::println);
    }
}
