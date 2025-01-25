package main.java.com.syos.service;

import main.java.com.syos.data.builder.MainStoreStockBuilder;
import main.java.com.syos.data.dao.MainStoreStockDAO;
import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.request.InsertMainStoreStockRequest;
import main.java.com.syos.service.interfaces.IMainStoreStockService;

import java.time.LocalDateTime;

public class MainStoreStockService implements IMainStoreStockService {
    private final IMainStoreStockDAO mainStoreStockDAO;

    public MainStoreStockService() {
        this.mainStoreStockDAO = new MainStoreStockDAO();
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
    }
}
