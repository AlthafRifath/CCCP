package test.java.com.syos.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// ✅ Unique Mock Classes for ShelfServiceTest
class MockAdminSession_ShelfService {
    Integer getLoggedInUserId() { return 1; }
}

class MockShelfDAO_ShelfService {
    void save(MockShelf_ShelfService shelf) {}
    List<MockShelf_ShelfService> findByShelfIdAndStoreId(int shelfId, int storeId) { return new ArrayList<>(); }
}

class MockMainStoreStockDAO_ShelfService {
    MockMainStoreStock_ShelfService findByStoreIdAndItemCodeAndBatchCode(int storeId, String itemCode, String batchCode) {
        return new MockMainStoreStock_ShelfService(storeId, itemCode, batchCode, 100); // Mocked stock
    }

    void adjustStock(int storeId, String itemCode, String batchCode, int quantity, MockStockAdjustmentStrategy_ShelfService strategy) {}
}

class MockStockAdjustmentStrategy_ShelfService {}

class MockShelf_ShelfService {
    int shelfID, storeID, quantityOnShelf;
    String itemCode, batchCode;
    boolean isDeleted;
    LocalDateTime lastRestockedDate, updatedDateTime;
    MockStore_ShelfService store;

    MockShelf_ShelfService(int shelfID, int storeID, String itemCode, String batchCode, int quantityOnShelf) {
        this.shelfID = shelfID;
        this.storeID = storeID;
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.quantityOnShelf = quantityOnShelf;
        this.isDeleted = false;
        this.lastRestockedDate = LocalDateTime.now();
        this.updatedDateTime = LocalDateTime.now();
    }
}

class MockMainStoreStock_ShelfService {
    int storeID, currentStock;
    String itemCode, batchCode;

    MockMainStoreStock_ShelfService(int storeID, String itemCode, String batchCode, int currentStock) {
        this.storeID = storeID;
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.currentStock = currentStock;
    }
}

class MockStore_ShelfService {
    int storeID;
    String storeName;
}

// ✅ Unique Service Class
class MockShelfService {
    private final MockAdminSession_ShelfService adminSession;
    private final MockShelfDAO_ShelfService shelfDAO;
    private final MockMainStoreStockDAO_ShelfService mainStoreStockDAO;

    MockShelfService(MockAdminSession_ShelfService adminSession, MockShelfDAO_ShelfService shelfDAO, MockMainStoreStockDAO_ShelfService mainStoreStockDAO) {
        this.adminSession = adminSession;
        this.shelfDAO = shelfDAO;
        this.mainStoreStockDAO = mainStoreStockDAO;
    }

    void addShelf(int shelfID, int storeID, String itemCode, String batchCode, int quantityOnShelf) {
        if (adminSession.getLoggedInUserId() == null) {
            throw new IllegalStateException("User is not logged in.");
        }
        if (quantityOnShelf <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        MockMainStoreStock_ShelfService stock = mainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(storeID, itemCode, batchCode);
        if (stock == null || stock.currentStock < quantityOnShelf) {
            throw new IllegalArgumentException("Insufficient stock in MainStoreStock.");
        }

        shelfDAO.save(new MockShelf_ShelfService(shelfID, storeID, itemCode, batchCode, quantityOnShelf));
    }
}

// ✅ Unique Test Class
public class ShelfServiceTest {
    private MockShelfService shelfService;
    private MockAdminSession_ShelfService mockSession;
    private MockShelfDAO_ShelfService mockShelfDAO;
    private MockMainStoreStockDAO_ShelfService mockMainStoreStockDAO;

    @BeforeEach
    void setUp() {
        mockSession = Mockito.mock(MockAdminSession_ShelfService.class);
        mockShelfDAO = Mockito.mock(MockShelfDAO_ShelfService.class);
        mockMainStoreStockDAO = Mockito.mock(MockMainStoreStockDAO_ShelfService.class);

        shelfService = new MockShelfService(mockSession, mockShelfDAO, mockMainStoreStockDAO);
    }

    @Test
    public void testAddShelf_Success() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);
        Mockito.when(mockMainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(1, "ITEM123", "BATCH001"))
                .thenReturn(new MockMainStoreStock_ShelfService(1, "ITEM123", "BATCH001", 50));

        Assertions.assertDoesNotThrow(() -> shelfService.addShelf(1, 1, "ITEM123", "BATCH001", 10));
        Mockito.verify(mockShelfDAO, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testAddShelf_UserNotLoggedIn_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        Assertions.assertThrows(IllegalStateException.class, () ->
                shelfService.addShelf(1, 1, "ITEM123", "BATCH001", 10));

        Mockito.verifyNoInteractions(mockShelfDAO);
    }

    @Test
    public void testAddShelf_InsufficientStock_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);
        Mockito.when(mockMainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(1, "ITEM123", "BATCH001"))
                .thenReturn(new MockMainStoreStock_ShelfService(1, "ITEM123", "BATCH001", 5));

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                shelfService.addShelf(1, 1, "ITEM123", "BATCH001", 10));

        Mockito.verifyNoInteractions(mockShelfDAO);
    }

    @Test
    public void testAddShelf_InvalidQuantity_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                shelfService.addShelf(1, 1, "ITEM123", "BATCH001", 0));

        Mockito.verifyNoInteractions(mockShelfDAO);
    }
}
