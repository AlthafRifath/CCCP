package main.java.com.syos.reports;

import main.java.com.syos.data.dao.ShelfDAO;
import main.java.com.syos.data.model.Shelf;

import java.util.List;

public class ReorderLevelReport extends AbstractReportGenerator{
    private final ShelfDAO shelfDAO;
    private List<Shelf> lowStockItems;
    private static final int REORDER_THRESHOLD = 50; // Define reorder level

    public ReorderLevelReport() {
        this.shelfDAO = new ShelfDAO();
    }

    @Override
    protected void fetchData() {
        System.out.println("Fetching low stock items...");
        lowStockItems = shelfDAO.getItemsBelowThreshold(REORDER_THRESHOLD);
    }

    @Override
    protected void processData() {
        System.out.println("Processing low stock items...");
    }

    @Override
    protected void formatReport() {
        System.out.println("\n=== Reorder Level Report ===");
        System.out.println("Item Name | Item Code | Batch Code | Quantity on Shelf");

        if (lowStockItems.isEmpty()) {
            System.out.println("All items are sufficiently stocked.");
        } else {
            for (Shelf shelf : lowStockItems) {
                System.out.println(shelf.getItem().getItemName() + " | " + shelf.getItemCode() + " | " + shelf.getBatchCode() + " | " + shelf.getQuantityOnShelf());
            }
        }
    }
}
