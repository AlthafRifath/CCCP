package main.java.com.syos.reports;

import main.java.com.syos.data.dao.ShelfDAO;
import main.java.com.syos.data.model.Shelf;

import java.util.List;

public class StockReport extends AbstractReportGenerator{
    private final ShelfDAO shelfDAO;
    private List<Shelf> stockList;

    public StockReport() {
        this.shelfDAO = new ShelfDAO();
    }

    @Override
    protected void fetchData() {
        System.out.println("Fetching stock data...");
        stockList = shelfDAO.getCurrentStock();
    }

    @Override
    protected void processData() {
        System.out.println("Processing stock data...");
    }

    @Override
    protected void formatReport() {
        System.out.println("\n=== Stock Report ===");
        System.out.println("Item Name | Item Code | Batch Code | Quantity");
        for (Shelf shelf : stockList) {
            System.out.println(shelf.getItem().getItemName() + " | " + shelf.getItemCode() + " | " + shelf.getBatchCode() + " | " + shelf.getQuantityOnShelf());
        }
    }
}
