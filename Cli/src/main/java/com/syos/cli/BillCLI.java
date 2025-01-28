package main.java.com.syos.cli;

import main.java.com.syos.request.BillItemRequest;
import main.java.com.syos.request.CreateBillRequest;
import main.java.com.syos.service.BillItemService;
import main.java.com.syos.service.BillService;
import main.java.com.syos.service.interfaces.IBillItemService;
import main.java.com.syos.service.interfaces.IBillService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BillCLI {
    private final IBillService billService;
    private final IBillItemService billItemService;

    public BillCLI() {
        this.billService = new BillService();
        this.billItemService = new BillItemService();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Billing System ===");
            System.out.println("1. Create Bill");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createBill(scanner);
                case 2 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void createBill(Scanner scanner) {
        CreateBillRequest billRequest = new CreateBillRequest();

        System.out.print("Enter Customer ID (or leave empty for walk-in): ");
        String customerIdInput = scanner.nextLine();
        billRequest.setCustomerID(customerIdInput.isEmpty() ? null : Integer.parseInt(customerIdInput));

        System.out.print("Enter Discount ID (or leave empty if none): ");
        String discountIdInput = scanner.nextLine();
        billRequest.setDiscountID(discountIdInput.isEmpty() ? null : Integer.parseInt(discountIdInput));

        List<BillItemRequest> billItems = new ArrayList<>();
        BigDecimal totalBillAmount = BigDecimal.ZERO;

        while (true) {
            System.out.println("\n--- Adding Items to Bill ---");
            System.out.print("Enter Item Code: ");
            String itemCode = scanner.nextLine();

            System.out.print("Enter Batch Code: ");
            String batchCode = scanner.nextLine();

            System.out.print("Enter Quantity: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid quantity.");
                scanner.next();
            }
            int quantity = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Price Per Item: ");
            while (!scanner.hasNextBigDecimal()) {
                System.out.println("Invalid input. Please enter a valid price.");
                scanner.next();
            }
            BigDecimal pricePerItem = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.print("Enter Discount ID (or leave empty if none): ");
            String itemDiscountInput = scanner.nextLine();
            Integer itemDiscountId = itemDiscountInput.isEmpty() ? null : Integer.parseInt(itemDiscountInput);

            BillItemRequest billItem = new BillItemRequest(itemCode, batchCode, quantity, pricePerItem, itemDiscountId);
            billItems.add(billItem);

            totalBillAmount = totalBillAmount.add(billItem.getTotalItemPrice());

            System.out.print("Do you want to add another item? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) break;
        }

        billRequest.setBillItems(billItems);

        // Display bill summary BEFORE asking for cash
        System.out.println("\n=== Bill Summary ===");
        System.out.println("Customer ID: " + (billRequest.getCustomerID() == null ? "Walk-in Customer" : billRequest.getCustomerID()));
        System.out.println("Discount ID: " + (billRequest.getDiscountID() == null ? "No Discount" : billRequest.getDiscountID()));
        System.out.println("Total Bill Amount: " + totalBillAmount);
        System.out.println("\n--- Bill Items ---");

        for (BillItemRequest item : billItems) {
            System.out.println("Item: " + item.getItemCode() + " | Batch: " + item.getBatchCode() +
                    " | Qty: " + item.getQuantity() + " | Price: " + item.getPricePerItem() +
                    " | Total: " + item.getTotalItemPrice() +
                    (item.getDiscountID() == null ? "" : " | Discount ID: " + item.getDiscountID()));
        }

        System.out.print("\nEnter Cash Tendered: ");
        while (!scanner.hasNextBigDecimal()) {
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.next();
        }
        billRequest.setCashTendered(scanner.nextBigDecimal());
        scanner.nextLine();

        // Step 1: Create Bill and Get BillID
        int billID = billService.createBill(billRequest);

        // Step 2: Add Items to Bill
        billItemService.addBillItems(billID, billItems);

        System.out.println("Bill created successfully! Bill ID: " + billID);
    }

}
