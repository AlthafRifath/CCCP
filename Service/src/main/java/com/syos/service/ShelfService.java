package main.java.com.syos.service;

import main.java.com.syos.data.dao.MainStoreStockDAO;
import main.java.com.syos.data.dao.ShelfDAO;
import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.data.strategy.FIFOStockAdjustmentStrategy;
import main.java.com.syos.data.strategy.interfaces.IStockAdjustmentStrategy;
import main.java.com.syos.request.InsertShelfRequest;
import main.java.com.syos.service.interfaces.IShelfService;

import java.time.LocalDateTime;

public class ShelfService implements IShelfService {
    private final IShelfDAO shelfDAO;
    private final IMainStoreStockDAO mainStoreStockDAO;

    public ShelfService() {
        this.shelfDAO = new ShelfDAO();
        this.mainStoreStockDAO = new MainStoreStockDAO();
    }

    @Override
    public void addShelf(InsertShelfRequest request) {
        IStockAdjustmentStrategy strategy = new FIFOStockAdjustmentStrategy();
        mainStoreStockDAO.adjustStock(1, request.getItemCode(), request.getBatchCode(), request.getQuantityOnShelf(), strategy);

        Shelf shelf = new Shelf();
        shelf.setShelfID(request.getShelfId());
        shelf.setItemCode(request.getItemCode());
        shelf.setBatchCode(request.getBatchCode());
        shelf.setQuantityOnShelf(request.getQuantityOnShelf());
        shelf.setLastRestockedDate(LocalDateTime.now());
        shelf.setDeleted(false);
        shelf.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
        shelf.setUpdatedDateTime(LocalDateTime.now());

        shelfDAO.save(shelf);
        System.out.println("Shelf added successfully.");
    }
}
