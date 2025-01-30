package test.java.com.syos.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

// ✅ Unique Mock Classes (Inside this test file)
class MockAdminSession_MainStoreStock {
    Integer getLoggedInUserId() { return 1; } // Simulated admin login
}

class MockMainStoreStockDAO_MainStoreStock {
    void save(MockMainStoreStock stock) {}
    Optional<MockMainStoreStock> findByStoreIdAndItemCodeAndBatchCode(int storeId, String itemCode, String batchCode) {
        return Optional.empty(); // Default: No stock found
    }
    void softDelete(MockMainStoreStock stock) {}
}

class MockItemDAO_MainStoreStock {
    Optional<MockItem_MainStoreStock> findByItemCodeAndBatchCode(String itemCode, String batchCode) {
        return Optional.empty(); // Default: Item not found
    }
    void update(MockItem_MainStoreStock item) {}
}

class MockMainStoreStock {
    int storeID;
    String itemCode, batchCode;
    int initialStock, currentStock;
    LocalDateTime lastRestockedDate;
    boolean isDeleted;
}

class MockItem_MainStoreStock {
    String itemCode, batchCode;
    int currentQuantity;
    LocalDateTime updatedDateTime;
}

// ✅ Unique Service class (Mocked with business logic only)
class MockMainStoreStockService {
    private final MockAdminSession_MainStoreStock adminSession;
    private final MockMainStoreStockDAO_MainStoreStock mainStoreStockDAO;
    private final MockItemDAO_MainStoreStock itemDAO;

    MockMainStoreStockService(MockAdminSession_MainStoreStock adminSession, MockMainStoreStockDAO_MainStoreStock mainStoreStockDAO, MockItemDAO_MainStoreStock itemDAO) {
        this.adminSession = adminSession;
        this.mainStoreStockDAO = mainStoreStockDAO;
        this.itemDAO = itemDAO;
    }

    void insertMainStoreStock(String itemCode, String batchCode, int initialStock, int currentStock) {
        if (itemCode == null || itemCode.isEmpty()) {
            throw new IllegalArgumentException("Item Code cannot be null or empty.");
        }
        if (batchCode == null || batchCode.isEmpty()) {
            throw new IllegalArgumentException("Batch Code cannot be null or empty.");
        }
        if (initialStock < 0) {
            throw new IllegalArgumentException("Initial Stock cannot be negative.");
        }
        if (currentStock < 0) {
            throw new IllegalArgumentException("Current Stock cannot be negative.");
        }

        MockMainStoreStock stock = new MockMainStoreStock();
        stock.itemCode = itemCode;
        stock.batchCode = batchCode;
        stock.initialStock = initialStock;
        stock.currentStock = currentStock;
        stock.lastRestockedDate = LocalDateTime.now();
        stock.isDeleted = false;

        mainStoreStockDAO.save(stock);

        // Check if the item exists
        Optional<MockItem_MainStoreStock> itemOptional = itemDAO.findByItemCodeAndBatchCode(itemCode, batchCode);
        if (itemOptional.isPresent()) {
            MockItem_MainStoreStock item = itemOptional.get();

            if (item.currentQuantity >= initialStock) {
                item.currentQuantity -= initialStock;
                item.updatedDateTime = LocalDateTime.now();
                itemDAO.update(item);
            } else {
                throw new IllegalStateException("Insufficient stock in Item table to add to MainStoreStock.");
            }
        } else {
            throw new IllegalArgumentException("Item not found with ItemCode: " + itemCode + " and BatchCode: " + batchCode);
        }
    }

    void deleteMainStoreStockItem(int storeId, String itemCode, String batchCode) {
        Optional<MockMainStoreStock> stockOptional = mainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(storeId, itemCode, batchCode);

        if (stockOptional.isEmpty()) {
            throw new IllegalArgumentException("Stock not found for the provided StoreId, ItemCode, and BatchCode.");
        }

        MockMainStoreStock stock = stockOptional.get();
        if (stock.currentStock != 0) {
            throw new IllegalStateException("Cannot delete stock. Current stock must be 0.");
        }

        mainStoreStockDAO.softDelete(stock);
    }
}

// ✅ Unique Test Class
public class MainStoreStockServiceTest {
    private MockMainStoreStockService stockService;
    private MockAdminSession_MainStoreStock mockSession;
    private MockMainStoreStockDAO_MainStoreStock mockStockDAO;
    private MockItemDAO_MainStoreStock mockItemDAO;

    @BeforeEach
    void setUp() {
        mockSession = Mockito.mock(MockAdminSession_MainStoreStock.class);
        mockStockDAO = Mockito.mock(MockMainStoreStockDAO_MainStoreStock.class);
        mockItemDAO = Mockito.mock(MockItemDAO_MainStoreStock.class);

        stockService = new MockMainStoreStockService(mockSession, mockStockDAO, mockItemDAO);
    }

    @Test
    public void testInsertMainStoreStock_SuccessfulInsertion() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);
        Mockito.when(mockItemDAO.findByItemCodeAndBatchCode("item123", "batch001"))
                .thenReturn(Optional.of(new MockItem_MainStoreStock()));

        Assertions.assertDoesNotThrow(() -> stockService.insertMainStoreStock("item123", "batch001", 10, 10));

        Mockito.verify(mockStockDAO, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testInsertMainStoreStock_InvalidItemCode_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> stockService.insertMainStoreStock("", "batch001", 10, 10));

        Mockito.verifyNoInteractions(mockStockDAO);
    }

    @Test
    public void testInsertMainStoreStock_ItemNotFound_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);
        Mockito.when(mockItemDAO.findByItemCodeAndBatchCode("item123", "batch001")).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> stockService.insertMainStoreStock("item123", "batch001", 10, 10));
    }

    @Test
    public void testDeleteMainStoreStockItem_SuccessfulDeletion() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        MockMainStoreStock mockStock = new MockMainStoreStock();
        mockStock.currentStock = 0;
        Mockito.when(mockStockDAO.findByStoreIdAndItemCodeAndBatchCode(1, "item123", "batch001"))
                .thenReturn(Optional.of(mockStock));

        Assertions.assertDoesNotThrow(() -> stockService.deleteMainStoreStockItem(1, "item123", "batch001"));

        Mockito.verify(mockStockDAO, Mockito.times(1)).softDelete(Mockito.any());
    }

    @Test
    public void testDeleteMainStoreStockItem_StockNotZero_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        MockMainStoreStock mockStock = new MockMainStoreStock();
        mockStock.currentStock = 5;
        Mockito.when(mockStockDAO.findByStoreIdAndItemCodeAndBatchCode(1, "item123", "batch001"))
                .thenReturn(Optional.of(mockStock));

        Assertions.assertThrows(IllegalStateException.class, () -> stockService.deleteMainStoreStockItem(1, "item123", "batch001"));
    }
}
