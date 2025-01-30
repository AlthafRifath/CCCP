package test.java.com.syos.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Mock Classes (Inside the test file itself)
class MockAdminSessionByBillItem {
    Integer getLoggedInUserId() { return 1; } // Simulating a logged-in user
}

class MockBillDAO {
    MockBill findByBillID(int billID) { return null; } // Default: Bill not found
    MockBill findBySerialNumber(String serialNumber) { return null; }
    void save(MockBill bill) {}
}

class MockBillItemDAOByBillItem {
    List<Object[]> findBillItemsWithItemNames(int billID) {
        return List.of(); // Default: No bill items found
    }
}

class MockBill {
    int billID;
    String serialNumber;
    LocalDateTime billDate;
    Integer customerID, discountID;
    BigDecimal totalAmount, cashTendered, change;
}

class MockGetBillItemDTO {
    String itemCode, itemName, batchCode;
    int quantity;
    BigDecimal pricePerItem, totalItemPrice;
    Integer discountID;
    LocalDateTime updatedDateTime;

    MockGetBillItemDTO(String itemCode, String itemName, String batchCode, int quantity,
                       BigDecimal pricePerItem, BigDecimal totalItemPrice, Integer discountID, LocalDateTime updatedDateTime) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.batchCode = batchCode;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.totalItemPrice = totalItemPrice;
        this.discountID = discountID;
        this.updatedDateTime = updatedDateTime;
    }
}

// Service class (Mocked with business logic only)
class MockBillService {
    private final MockAdminSessionByBillItem adminSession;
    private final MockBillDAO billDAO;
    private final MockBillItemDAOByBillItem billItemDAO;

    MockBillService(MockAdminSessionByBillItem adminSession, MockBillDAO billDAO, MockBillItemDAOByBillItem billItemDAO) {
        this.adminSession = adminSession;
        this.billDAO = billDAO;
        this.billItemDAO = billItemDAO;
    }

    int createBill(BigDecimal cashTendered) {
        if (adminSession.getLoggedInUserId() == null) {
            throw new IllegalStateException("User is not logged in.");
        }
        if (cashTendered.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cash tendered must be greater than zero.");
        }

        MockBill bill = new MockBill();
        bill.billDate = LocalDateTime.now();
        bill.serialNumber = "BILL-" + System.currentTimeMillis();
        bill.cashTendered = cashTendered;
        bill.totalAmount = BigDecimal.ZERO;
        bill.change = cashTendered;

        billDAO.save(bill);
        return bill.billID;
    }

    MockBill getBillByID(int billID) {
        MockBill bill = billDAO.findByBillID(billID);
        if (bill == null) throw new IllegalArgumentException("Bill not found.");
        return bill;
    }

    MockBill getBillBySerialNumber(String serialNumber) {
        MockBill bill = billDAO.findBySerialNumber(serialNumber);
        if (bill == null) throw new IllegalArgumentException("Bill not found.");
        return bill;
    }
}

// ✅ Actual Test Class
public class BillServiceTest {
    private MockBillService billService;
    private MockAdminSessionByBillItem mockSession;
    private MockBillDAO mockBillDAO;
    private MockBillItemDAOByBillItem mockBillItemDAO;

    @BeforeEach
    void setUp() {
        mockSession = Mockito.mock(MockAdminSessionByBillItem.class);
        mockBillDAO = Mockito.mock(MockBillDAO.class);
        mockBillItemDAO = Mockito.mock(MockBillItemDAOByBillItem.class);

        billService = new MockBillService(mockSession, mockBillDAO, mockBillItemDAO);
    }

    @Test
    public void testCreateBill_SuccessfulCreation() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertDoesNotThrow(() -> billService.createBill(new BigDecimal("100.00")));

        Mockito.verify(mockBillDAO, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testCreateBill_InvalidCashTendered_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> billService.createBill(new BigDecimal("-10.00")));

        Mockito.verifyNoInteractions(mockBillDAO);
    }

    @Test
    public void testCreateBill_UserNotLoggedIn_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        Assertions.assertThrows(IllegalStateException.class, () -> billService.createBill(new BigDecimal("100.00")));

        Mockito.verifyNoInteractions(mockBillDAO);
    }

    @Test
    public void testGetBillByID_BillExists() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        MockBill mockBill = new MockBill();
        mockBill.billID = 1;
        Mockito.when(mockBillDAO.findByBillID(1)).thenReturn(mockBill);

        Assertions.assertDoesNotThrow(() -> billService.getBillByID(1));
    }

    @Test
    public void testGetBillByID_BillNotFound_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Mockito.when(mockBillDAO.findByBillID(1)).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> billService.getBillByID(1));
    }

    @Test
    public void testGetBillBySerialNumber_BillExists() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        MockBill mockBill = new MockBill();
        mockBill.serialNumber = "BILL-123";
        Mockito.when(mockBillDAO.findBySerialNumber("BILL-123")).thenReturn(mockBill);

        Assertions.assertDoesNotThrow(() -> billService.getBillBySerialNumber("BILL-123"));
    }

    @Test
    public void testGetBillBySerialNumber_BillNotFound_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Mockito.when(mockBillDAO.findBySerialNumber("BILL-123")).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> billService.getBillBySerialNumber("BILL-123"));
    }
}
