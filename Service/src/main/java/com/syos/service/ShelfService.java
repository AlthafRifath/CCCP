package main.java.com.syos.service;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.MainStoreStockDAO;
import main.java.com.syos.data.dao.ShelfDAO;
import main.java.com.syos.data.dao.SoftDeleteShelfDAO;
import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.data.strategy.FIFOStockAdjustmentStrategy;
import main.java.com.syos.data.strategy.interfaces.IStockAdjustmentStrategy;
import main.java.com.syos.dto.GetShelfDetailsDTO;
import main.java.com.syos.request.DeleteShelfRequest;
import main.java.com.syos.request.InsertShelfRequest;
import main.java.com.syos.request.UpdateShelfRequest;
import main.java.com.syos.service.interfaces.IShelfService;
import main.java.com.syos.data.model.Store;
import main.java.com.syos.service.notifications.StockNotificationObserver;
import main.java.com.syos.service.notifications.interfaces.IStockNotifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ShelfService implements IShelfService {
    private final IShelfDAO shelfDAO;
    private final IMainStoreStockDAO mainStoreStockDAO;
    private final IStockNotifier stockNotifier;
    private final SoftDeleteShelfDAO softDeleteShelfDAO;

    public ShelfService() {
        this.shelfDAO = new ShelfDAO();
        this.mainStoreStockDAO = new MainStoreStockDAO();
        this.stockNotifier = new StockNotificationObserver();
        this.softDeleteShelfDAO = new SoftDeleteShelfDAO(shelfDAO);
    }

    @Override
    public void addShelf(InsertShelfRequest request) {
        MainStoreStock mainStoreStock = TransactionManager.execute(session -> {
            return session.createQuery(
                            "FROM MainStoreStock WHERE storeID = :storeId AND itemCode = :itemCode AND batchCode = :batchCode AND isDeleted = false",
                            MainStoreStock.class)
                    .setParameter("storeId", request.getStoreIdFromMainStoreStock())
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

        IStockAdjustmentStrategy strategy = new FIFOStockAdjustmentStrategy();
        mainStoreStockDAO.adjustStock(
                request.getStoreIdFromMainStoreStock(),
                request.getItemCode(),
                request.getBatchCode(),
                request.getQuantityOnShelf(),
                strategy
        );

        Store store = TransactionManager.execute(session -> {
            return session.get(Store.class, request.getStoreIdFromStore());
        });

        if (store == null) {
            throw new IllegalArgumentException("Store with ID " + request.getStoreIdFromStore() + " does not exist.");
        }

        Shelf shelf = new Shelf();
        shelf.setShelfID(request.getShelfId());
        shelf.setStore(store);
        shelf.setStoreID(store.getStoreID());
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
    public List<GetShelfDetailsDTO> getShelfDetailsByShelfIdAndStoreId(int shelfId, int storeId) {
        List<Shelf> shelves = shelfDAO.findByShelfIdAndStoreId(shelfId, storeId);

        return shelves.stream()
                .map(shelf -> new GetShelfDetailsDTO(
                        shelf.getShelfID(),
                        shelf.getStore().getStoreName(),
                        shelf.getItemCode(),
                        shelf.getBatchCode(),
                        shelf.getQuantityOnShelf(),
                        shelf.getLastRestockedDate(),
                        shelf.getItem().getItemName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void updateShelf(UpdateShelfRequest request) {
        TransactionManager.execute(session -> {
            Shelf shelf = session.createQuery(
                            "FROM Shelf WHERE storeID = :storeId AND shelfID = :shelfId AND itemCode = :itemCode AND batchCode = :batchCode AND isDeleted = false",
                            Shelf.class)
                    .setParameter("storeId", request.getStoreId()) // tblShelf's StoreID
                    .setParameter("shelfId", request.getShelfId())
                    .setParameter("itemCode", request.getItemCode())
                    .setParameter("batchCode", request.getBatchCode())
                    .uniqueResult();

            if (shelf == null) {
                throw new IllegalArgumentException(
                        "Shelf not found for StoreID: " + request.getStoreId() +
                                ", ShelfID: " + request.getShelfId() +
                                ", ItemCode: " + request.getItemCode() +
                                ", BatchCode: " + request.getBatchCode()
                );
            }

            MainStoreStock mainStoreStock = session.createQuery(
                            "FROM MainStoreStock WHERE storeID = :storeId AND itemCode = :itemCode AND batchCode = :batchCode AND isDeleted = false",
                            MainStoreStock.class)
                    .setParameter("storeId", request.getStoreIdFromMainStoreStock()) // MainStoreStock's StoreID
                    .setParameter("itemCode", request.getItemCode())
                    .setParameter("batchCode", request.getBatchCode())
                    .uniqueResult();

            if (mainStoreStock == null) {
                throw new IllegalArgumentException(
                        "MainStoreStock entry not found for StoreID: " + request.getStoreIdFromMainStoreStock() +
                                ", ItemCode: " + request.getItemCode() +
                                ", BatchCode: " + request.getBatchCode()
                );
            }

            if (mainStoreStock.getCurrentStock() < request.getQuantityToAdd()) {
                throw new IllegalArgumentException(
                        "Insufficient stock in MainStoreStock for StoreID: " + request.getStoreIdFromMainStoreStock()
                );
            }

            IStockAdjustmentStrategy strategy = new FIFOStockAdjustmentStrategy();
            mainStoreStockDAO.adjustStock(
                    request.getStoreIdFromMainStoreStock(),
                    request.getItemCode(),
                    request.getBatchCode(),
                    request.getQuantityToAdd(),
                    strategy
            );

            shelf.setQuantityOnShelf(shelf.getQuantityOnShelf() + request.getQuantityToAdd());
            shelf.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
            shelf.setUpdatedDateTime(LocalDateTime.now());
            session.update(shelf);

            System.out.println("Shelf updated successfully.");
            return null;
        });
    }


    @Override
    public void deleteShelf(DeleteShelfRequest request) {
        int updatedBy = AdminSession.getInstance().getLoggedInUserId();
        softDeleteShelfDAO.softDeleteShelf(
                request.getStoreId(),
                request.getShelfId(),
                request.getItemCode(),
                request.getBatchCode(),
                updatedBy
        );
    }

    @Override
    public List<Shelf> getAllShelves() {
        return TransactionManager.execute(session ->
                session.createQuery("FROM Shelf WHERE isDeleted = false", Shelf.class).list()
        );
    }
}
