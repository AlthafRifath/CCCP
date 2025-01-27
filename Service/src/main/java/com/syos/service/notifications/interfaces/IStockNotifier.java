package main.java.com.syos.service.notifications.interfaces;

import main.java.com.syos.data.model.Shelf;

public interface IStockNotifier {
    void notifyStockLevel(Shelf shelf);
}
