package main.java.com.syos.service;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.MainStoreStockDAO;
import main.java.com.syos.data.dao.ShelfDAO;
import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.data.strategy.FIFOStockAdjustmentStrategy;
import main.java.com.syos.data.strategy.interfaces.IStockAdjustmentStrategy;
import main.java.com.syos.dto.GetShelfDetailsDTO;
import main.java.com.syos.request.InsertShelfRequest;
import main.java.com.syos.service.interfaces.IShelfService;
import main.java.com.syos.data.model.Store;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ShelfService implements IShelfService {
    private final IShelfDAO shelfDAO;
    private final IMainStoreStockDAO mainStoreStockDAO;

    public ShelfService() {
        this.shelfDAO = new ShelfDAO();
        this.mainStoreStockDAO = new MainStoreStockDAO();
    }

    @Override
    public void addShelf(InsertShelfRequest request) {
        // Step 1: Validate and Adjust Stock in tblMainStoreStock
        MainStoreStock mainStoreStock = TransactionManager.execute(session -> {
            return session.createQuery(
                            "FROM MainStoreStock WHERE storeID = :storeId AND itemCode = :itemCode AND batchCode = :batchCode AND isDeleted = false",
                            MainStoreStock.class)
                    .setParameter("storeId", request.getStoreIdFromMainStoreStock()) // From tblMainStoreStock
                    .setParameter("itemCode", request.getItemCode())
                    .setParameter("batchCode", request.getBatchCode())
                    .uniqueResult();
        });

        if (mainStoreStock == null) {
            throw new IllegalArgumentException("MainStoreStock entry not found for StoreID: " + request.getStoreIdFromMainStoreStock() +
                    ", ItemCode: " + request.getItemCode() + ", BatchCode: " + request.getBatchCode());
        }

        if (mainStoreStock.getCurrentStock() < request.getQuantityOnShelf()) {
            throw new IllegalArgumentException("Insufficient stock in MainStoreStock for StoreID: " +
                    request.getStoreIdFromMainStoreStock() + ", ItemCode: " + request.getItemCode() +
                    ", BatchCode: " + request.getBatchCode());
        }

        // Adjust stock in tblMainStoreStock
        IStockAdjustmentStrategy strategy = new FIFOStockAdjustmentStrategy();
        mainStoreStockDAO.adjustStock(
                request.getStoreIdFromMainStoreStock(),
                request.getItemCode(),
                request.getBatchCode(),
                request.getQuantityOnShelf(),
                strategy
        );

        // Step 2: Validate Store in tblStore
        Store store = TransactionManager.execute(session -> {
            return session.get(Store.class, request.getStoreIdFromStore()); // From tblStore
        });

        if (store == null) {
            throw new IllegalArgumentException("Store with ID " + request.getStoreIdFromStore() + " does not exist.");
        }

        // Step 3: Save Shelf to tblShelf
        Shelf shelf = new Shelf();
        shelf.setShelfID(request.getShelfId());
        shelf.setStore(store); // Associated StoreID from tblStore
        shelf.setStoreID(store.getStoreID()); // Explicitly set StoreID for tblShelf
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

    @Override
    public List<GetShelfDetailsDTO> getShelfDetailsByShelfId(int shelfId) {
        List<Shelf> shelves = shelfDAO.findByShelfId(shelfId);
        return shelves.stream()
                .map(shelf -> new GetShelfDetailsDTO(
                        shelf.getShelfID(),
                        shelf.getStore().getStoreName(),
                        shelf.getItemCode(),
                        shelf.getBatchCode(),
                        shelf.getQuantityOnShelf(),
                        shelf.getLastRestockedDate()
                ))
                .collect(Collectors.toList());
    }
}
