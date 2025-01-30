package test.java.com.syos.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

// Mock Classes (These are inside the test file itself)
class MockAdminSession {
    Integer getLoggedInUserId() { return 1; } // Default to a logged-in user
}

class MockItemDAO {
    void save(MockItem item) {
        // Simulated DB save operation (does nothing)
    }
}

class MockItem {
    String itemCode, batchCode, itemName;
    double price;
    LocalDateTime manufactureDate, expiryDate;
    int initialQuantity, minimumQuantity;

    MockItem(String itemCode, String batchCode, String itemName, double price,
             LocalDateTime manufactureDate, LocalDateTime expiryDate,
             int initialQuantity, int minimumQuantity) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.itemName = itemName;
        this.price = price;
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
        this.initialQuantity = initialQuantity;
        this.minimumQuantity = minimumQuantity;
    }
}

// Service class (Mocked with business logic only)
class MockItemService {
    private final MockAdminSession adminSession;
    private final MockItemDAO itemDAO;

    MockItemService(MockAdminSession adminSession, MockItemDAO itemDAO) {
        this.adminSession = adminSession;
        this.itemDAO = itemDAO;
    }

    void insertItem(String itemCode, String batchCode, String itemName, double price,
                    LocalDateTime manufactureDate, LocalDateTime expiryDate,
                    int initialQuantity, int minimumQuantity) {

        if (adminSession.getLoggedInUserId() == null) {
            throw new IllegalStateException("User is not logged in.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        if (initialQuantity <= 0) {
            throw new IllegalArgumentException("Initial quantity must be greater than zero.");
        }

        MockItem item = new MockItem(itemCode, batchCode, itemName, price, manufactureDate, expiryDate, initialQuantity, minimumQuantity);
        itemDAO.save(item);
    }
}

// ✅ Actual Test Class
public class ItemServiceTest {
    private MockItemService itemService;
    private MockAdminSession mockSession;
    private MockItemDAO mockItemDAO;

    @BeforeEach
    void setUp() {
        mockSession = Mockito.mock(MockAdminSession.class);
        mockItemDAO = Mockito.mock(MockItemDAO.class);

        itemService = new MockItemService(mockSession, mockItemDAO);
    }

    @Test
    public void testInsertItem_SuccessfulInsertion() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertDoesNotThrow(() -> itemService.insertItem(
                "item123", "batch001", "Test Item", 50.0,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                10, 10
        ));

        Mockito.verify(mockItemDAO, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testInsertItem_InvalidPrice_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> itemService.insertItem(
                "item123", "batch001", "Test Item", -10.0,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                10, 10
        ));

        Mockito.verifyNoInteractions(mockItemDAO);
    }

    @Test
    public void testInsertItem_InvalidInitialQuantity_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> itemService.insertItem(
                "item123", "batch001", "Test Item", 50.0,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                0, 10
        ));

        Mockito.verifyNoInteractions(mockItemDAO);
    }

    @Test
    public void testInsertItem_UserNotLoggedIn_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        Assertions.assertThrows(IllegalStateException.class, () -> itemService.insertItem(
                "item123", "batch001", "Test Item", 50.0,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                10, 10
        ));

        Mockito.verifyNoInteractions(mockItemDAO);
    }
}
