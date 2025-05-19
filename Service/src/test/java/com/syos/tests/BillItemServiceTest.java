package test.java.com.syos.tests;

import main.java.com.syos.data.dao.interfaces.IBillItemDAO;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.BillItem;
import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.request.BillItemRequest;
import main.java.com.syos.service.AdminSession;
import main.java.com.syos.service.BillItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BillItemServiceTest {

    private BillItemService billItemService;
    private IBillItemDAO mockBillItemDAO;
    private IShelfDAO mockShelfDAO;
    private AdminSession mockSession;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockBillItemDAO = Mockito.mock(IBillItemDAO.class);
        mockShelfDAO = Mockito.mock(IShelfDAO.class);
        mockSession = Mockito.mock(AdminSession.class);

        // Ensure user is logged in
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        // Inject mocks into service
//        billItemService = new BillItemService(mockBillItemDAO, mockShelfDAO, mockSession);
    }

    @Test
    public void testAddBillItems_SuccessfulInsertion() {
        int billID = 101;
        List<BillItemRequest> billItems = Arrays.asList(
                new BillItemRequest("ITEM001", "BATCH01", 2, new BigDecimal("50.00"), null)
        );

        // Mock shelf availability
        Shelf mockShelf = new Shelf();
        mockShelf.setQuantityOnShelf(10);
        Mockito.when(mockShelfDAO.findByItemAndBatch("ITEM001", "BATCH01")).thenReturn(mockShelf);

        // Execute method
        Assertions.assertDoesNotThrow(() -> billItemService.addBillItems(billID, billItems));

        // Capture and verify BillItem
        ArgumentCaptor<BillItem> billItemCaptor = ArgumentCaptor.forClass(BillItem.class);
        Mockito.verify(mockBillItemDAO, Mockito.times(1)).save(billItemCaptor.capture());
        BillItem savedBillItem = billItemCaptor.getValue();

        Assertions.assertEquals("ITEM001", savedBillItem.getItemCode());
        Assertions.assertEquals("BATCH01", savedBillItem.getBatchCode());
        Assertions.assertEquals(2, savedBillItem.getQuantity());
        Assertions.assertEquals(new BigDecimal("50.00"), savedBillItem.getPricePerItem());

        // Ensure shelf update
        Assertions.assertEquals(8, mockShelf.getQuantityOnShelf());
        Mockito.verify(mockShelfDAO, Mockito.times(1)).findByItemAndBatch("ITEM001", "BATCH01");
    }

    @Test
    public void testAddBillItems_InsufficientStock_ThrowsException() {
        int billID = 101;
        List<BillItemRequest> billItems = Arrays.asList(
                new BillItemRequest("ITEM001", "BATCH01", 5, new BigDecimal("50.00"), null)
        );

        // Mock shelf with insufficient stock
        Shelf mockShelf = new Shelf();
        mockShelf.setQuantityOnShelf(3);
        Mockito.when(mockShelfDAO.findByItemAndBatch("ITEM001", "BATCH01")).thenReturn(mockShelf);

        // Expect an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> billItemService.addBillItems(billID, billItems));

        // Ensure save() was never called
        Mockito.verifyNoInteractions(mockBillItemDAO);
    }

    @Test
    public void testAddBillItems_ShelfNotFound_ThrowsException() {
        int billID = 101;
        List<BillItemRequest> billItems = Arrays.asList(
                new BillItemRequest("ITEM001", "BATCH01", 2, new BigDecimal("50.00"), null)
        );

        // Mock shelf as non-existent
        Mockito.when(mockShelfDAO.findByItemAndBatch("ITEM001", "BATCH01")).thenReturn(null);

        // Expect an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> billItemService.addBillItems(billID, billItems));

        // Ensure save() was never called
        Mockito.verifyNoInteractions(mockBillItemDAO);
    }

    @Test
    public void testAddBillItems_UserNotLoggedIn_ThrowsException() {
        int billID = 101;
        List<BillItemRequest> billItems = Arrays.asList(
                new BillItemRequest("ITEM001", "BATCH01", 2, new BigDecimal("50.00"), null)
        );

        // Simulate user not logged in
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        // Expect an exception
        Assertions.assertThrows(IllegalStateException.class, () -> billItemService.addBillItems(billID, billItems));

        // Ensure save() was never called
        Mockito.verifyNoInteractions(mockBillItemDAO);
    }
}