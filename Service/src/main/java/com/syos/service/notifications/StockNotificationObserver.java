package main.java.com.syos.service.notifications;

import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.service.notifications.interfaces.IStockNotifier;

public class StockNotificationObserver implements IStockNotifier {
    private static final int LOW_STOCK_THRESHOLD = 5; // Example threshold

    @Override
    public void notifyStockLevel(Shelf shelf) {
        if (shelf.getQuantityOnShelf() <= LOW_STOCK_THRESHOLD) {
            System.out.println("Warning: Low stock for ItemCode: " + shelf.getItemCode() +
                    ", BatchCode: " + shelf.getBatchCode() + ", ShelfID: " + shelf.getShelfID());
        }
    }
}
