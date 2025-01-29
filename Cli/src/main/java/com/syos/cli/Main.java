package main.java.com.syos.cli;
import main.java.com.syos.service.ReportService;
import org.jboss.logging.Logger;

public class Main {
    public static void main(String[] args) {
        AuthenticationCLI authenticationCLI = new AuthenticationCLI();
        boolean isAuthenticated = authenticationCLI.start();
        ReportService reportService = new ReportService();

        if (isAuthenticated) {
            System.out.println("Welcome to the Inventory Management System!");
//            ItemCLI itemCLI = new ItemCLI();
//            itemCLI.start();
//            MainStoreStockCLI mainStoreStockCLI = new MainStoreStockCLI();
//            mainStoreStockCLI.start();
//            ShelfCLI shelfCLI = new ShelfCLI();
//            shelfCLI.start();
//            BillCLI billCLI = new BillCLI();
//            billCLI.start();
            ReportCLI reportCLI = new ReportCLI(
                    reportService,  // Sales Report
                    reportService,  // Stock Report
                    reportService,  // Reorder Level Report
                    reportService   // Bill Report
            );
            reportCLI.start();
        } else {
            System.out.println("Exiting the application. Please try logging in again.");
        }
    }
}
