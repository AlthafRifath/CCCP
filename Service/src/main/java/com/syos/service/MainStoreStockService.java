package main.java.com.syos.service;

import main.java.com.syos.data.builder.MainStoreStockBuilder;
import main.java.com.syos.data.dao.ItemDAO;
import main.java.com.syos.data.dao.MainStoreStockDAO;
import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.model.Item;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.dto.GetMainStoreStockDetailsDTO;
import main.java.com.syos.request.InsertMainStoreStockRequest;
import main.java.com.syos.service.interfaces.IMainStoreStockService;

import java.time.LocalDateTime;
import java.util.Optional;

public class MainStoreStockService implements IMainStoreStockService {
    private final IMainStoreStockDAO mainStoreStockDAO;
    private final ItemDAO itemDAO;

    public MainStoreStockService() {
        this.mainStoreStockDAO = new MainStoreStockDAO();
        this.itemDAO = new ItemDAO();
    }

    @Override
    public void insertMainStoreStock(InsertMainStoreStockRequest request) {
        if (request.getItemCode() == null || request.getItemCode().isEmpty()) {
            throw new IllegalArgumentException("Item Code cannot be null or empty.");
        }

        if (request.getBatchCode() == null || request.getBatchCode().isEmpty()) {
            throw new IllegalArgumentException("Batch Code cannot be null or empty.");
        }

        if (request.getInitialStock() < 0) {
            throw new IllegalArgumentException("Initial Stock cannot be negative.");
        }

        if (request.getCurrentStock() < 0) {
            throw new IllegalArgumentException("Current Stock cannot be negative.");
        }

        MainStoreStock mainStoreStock = new MainStoreStockBuilder()
                .setStoreId(request.getStoreId())
                .setItemCode(request.getItemCode())
                .setBatchCode(request.getBatchCode())
                .setInitialStock(request.getInitialStock())
                .setCurrentStock(request.getCurrentStock())
                .setLastRestockedDate(request.getLastRestockedDate())
                .setIsDeleted(false)
                .setUpdatedBy(AdminSession.getInstance().getLoggedInUserId())
                .setUpdatedDateTime(LocalDateTime.now())
                .build();

        mainStoreStockDAO.save(mainStoreStock);

        // Update Item table with the new stock quantity after adding to MainStoreStock.
        Optional<Item> itemOptional = itemDAO.findByItemCodeAndBatchCode(request.getItemCode(), request.getBatchCode());

        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();

            if (item.getCurrentQuantity() >= request.getInitialStock()) {
                item.setCurrentQuantity(item.getCurrentQuantity() - request.getInitialStock());
                item.setUpdatedDateTime(LocalDateTime.now());
                item.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
                itemDAO.update(item);
                System.out.println("Item stock updated successfully.");
            } else {
                throw new IllegalStateException("Insufficient stock in Item table to add to MainStoreStock.");
            }
        } else {
            throw new IllegalArgumentException("Item not found with ItemCode: " + request.getItemCode() +
                    " and BatchCode: " + request.getBatchCode());
        }
    }

    @Override
    public Optional<GetMainStoreStockDetailsDTO> getMainStoreStockDetails(int storeId, String itemCode, String batchCode) {
        return mainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(storeId, itemCode, batchCode);
    }
}
