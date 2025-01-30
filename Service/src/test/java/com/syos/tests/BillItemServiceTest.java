package test.java.com.syos.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Mock Classes (Inside the test file itself)
class MockAdminSessionForBillItem {
    Integer getLoggedInUserId() { return 1; } // Simulating a logged-in user
}

class MockShelfDAO {
    MockShelf findByItemAndBatch(String itemCode, String batchCode) {
        return null; // Default behavior: No item found
    }
}

class MockBillItemDAO {
    void save(MockBillItem billItem) {
        // Simulated DB save operation (does nothing)
    }
}

class MockShelf {
    String itemCode, batchCode;
    int quantityOnShelf;
    Integer updatedBy;
    LocalDateTime updatedDateTime;

    MockShelf(String itemCode, String batchCode, int quantityOnShelf) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.quantityOnShelf = quantityOnShelf;
    }
}

class MockBillItem {
    int billID;
    String itemCode, batchCode;
    int quantity;
    BigDecimal pricePerItem, totalItemPrice;
    Integer updatedBy;
    LocalDateTime updatedDateTime;
}

// Service class (Mocked with business logic only)
class MockBillItemService {
    private final MockAdminSessionForBillItem adminSession;
    private final MockShelfDAO shelfDAO;
    private final MockBillItemDAO billItemDAO;

    MockBillItemService(MockAdminSessionForBillItem adminSession, MockShelfDAO shelfDAO, MockBillItemDAO billItemDAO) {
        this.adminSession = adminSession;
        this.shelfDAO = shelfDAO;
        this.billItemDAO = billItemDAO;
    }

    void addBillItems(int billID, List<MockBillItemRequest> billItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (MockBillItemRequest itemRequest : billItems) {
            // Fetch Shelf
            MockShelf shelf = shelfDAO.findByItemAndBatch(itemRequest.itemCode, itemRequest.batchCode);

            if (shelf == null || shelf.quantityOnShelf < itemRequest.quantity) {
                throw new IllegalArgumentException("Insufficient stock for ItemCode: " + itemRequest.itemCode);
            }

            // Deduct stock from shelf
            shelf.quantityOnShelf -= itemRequest.quantity;
            shelf.updatedBy = adminSession.getLoggedInUserId();
            shelf.updatedDateTime = LocalDateTime.now();

            // Create BillItem
            MockBillItem billItem = new MockBillItem();
            billItem.billID = billID;
            billItem.itemCode = itemRequest.itemCode;
            billItem.batchCode = itemRequest.batchCode;
            billItem.quantity = itemRequest.quantity;
            billItem.pricePerItem = itemRequest.pricePerItem;
            billItem.totalItemPrice = itemRequest.totalItemPrice;
            billItem.updatedBy = adminSession.getLoggedInUserId();
            billItem.updatedDateTime = LocalDateTime.now();

            billItemDAO.save(billItem);
            totalAmount = totalAmount.add(billItem.totalItemPrice);
        }
    }
}

// Request class
class MockBillItemRequest {
    String itemCode, batchCode;
    int quantity;
    BigDecimal pricePerItem, totalItemPrice;

    MockBillItemRequest(String itemCode, String batchCode, int quantity, BigDecimal pricePerItem) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.totalItemPrice = pricePerItem.multiply(BigDecimal.valueOf(quantity));
    }
}

// ✅ Actual Test Class
public class BillItemServiceTest {
    private MockBillItemService billItemService;
    private MockAdminSessionForBillItem mockSession;
    private MockShelfDAO mockShelfDAO;
    private MockBillItemDAO mockBillItemDAO;

    @BeforeEach
    void setUp() {
        mockSession = Mockito.mock(MockAdminSessionForBillItem.class);
        mockShelfDAO = Mockito.mock(MockShelfDAO.class);
        mockBillItemDAO = Mockito.mock(MockBillItemDAO.class);

        billItemService = new MockBillItemService(mockSession, mockShelfDAO, mockBillItemDAO);
    }

    @Test
    public void testAddBillItems_SuccessfulInsertion() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        MockShelf mockShelf = new MockShelf("ITEM001", "BATCH01", 20);
        Mockito.when(mockShelfDAO.findByItemAndBatch("ITEM001", "BATCH01")).thenReturn(mockShelf);

        MockBillItemRequest request = new MockBillItemRequest("ITEM001", "BATCH01", 5, new BigDecimal("10.00"));
        List<MockBillItemRequest> requests = List.of(request);

        Assertions.assertDoesNotThrow(() -> billItemService.addBillItems(1, requests));

        Mockito.verify(mockBillItemDAO, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testAddBillItems_InsufficientStock_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        MockShelf mockShelf = new MockShelf("ITEM001", "BATCH01", 2);
        Mockito.when(mockShelfDAO.findByItemAndBatch("ITEM001", "BATCH01")).thenReturn(mockShelf);

        MockBillItemRequest request = new MockBillItemRequest("ITEM001", "BATCH01", 5, new BigDecimal("10.00"));
        List<MockBillItemRequest> requests = List.of(request);

        Assertions.assertThrows(IllegalArgumentException.class, () -> billItemService.addBillItems(1, requests));

        Mockito.verifyNoInteractions(mockBillItemDAO);
    }

    @Test
    public void testAddBillItems_ShelfNotFound_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Mockito.when(mockShelfDAO.findByItemAndBatch("ITEM002", "BATCH02")).thenReturn(null);

        MockBillItemRequest request = new MockBillItemRequest("ITEM002", "BATCH02", 5, new BigDecimal("10.00"));
        List<MockBillItemRequest> requests = List.of(request);

        Assertions.assertThrows(IllegalArgumentException.class, () -> billItemService.addBillItems(1, requests));

        Mockito.verifyNoInteractions(mockBillItemDAO);
    }

    @Test
    public void testAddBillItems_UserNotLoggedIn_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        MockBillItemRequest request = new MockBillItemRequest("ITEM001", "BATCH01", 5, new BigDecimal("10.00"));
        List<MockBillItemRequest> requests = List.of(request);

        Assertions.assertThrows(IllegalStateException.class, () -> billItemService.addBillItems(1, requests));

        Mockito.verifyNoInteractions(mockBillItemDAO);
    }
}
