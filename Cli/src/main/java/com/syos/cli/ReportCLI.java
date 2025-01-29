package main.java.com.syos.cli;

import main.java.com.syos.service.interfaces.IBillReportService;
import main.java.com.syos.service.interfaces.IReorderLevelReportService;
import main.java.com.syos.service.interfaces.ISalesReportService;
import main.java.com.syos.service.interfaces.IStockReportService;

import java.util.Scanner;

public class ReportCLI {
    private final ISalesReportService salesReportService;
    private final IStockReportService stockReportService;
    private final IReorderLevelReportService reorderLevelReportService;
    private final IBillReportService billReportService;

    public ReportCLI(
            ISalesReportService salesReportService,
            IStockReportService stockReportService,
            IReorderLevelReportService reorderLevelReportService,
            IBillReportService billReportService) {
        this.salesReportService = salesReportService;
        this.stockReportService = stockReportService;
        this.reorderLevelReportService = reorderLevelReportService;
        this.billReportService = billReportService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Reports ===");
            System.out.println("1. Sales Report");
            System.out.println("2. Stock Report");
            System.out.println("3. Reorder Level Report");
            System.out.println("4. Bill Report");
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
                case 1 -> salesReportService.generateSalesReport();
                case 2 -> stockReportService.generateStockReport();
                case 3 -> reorderLevelReportService.generateReorderLevelReport();
                case 4 -> billReportService.generateBillReport();
                case 5 -> {
                    System.out.println("Exiting Reports...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
