package test.java.com.syos.tests;

import main.java.com.syos.data.dao.interfaces.IItemDAO;
import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.data.model.Item;
import main.java.com.syos.request.InsertMainStoreStockRequest;
import main.java.com.syos.request.DeleteMainStoreStockItemRequest;
import main.java.com.syos.dto.GetMainStoreStockDetailsDTO;
import main.java.com.syos.service.AdminSession;
import main.java.com.syos.service.MainStoreStockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

public class MainStoreStockServiceTest {

    private MainStoreStockService mainStoreStockService;
    private IMainStoreStockDAO mockMainStoreStockDAO;
    private AdminSession mockSession;
    private IItemDAO mockItemDAO;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockMainStoreStockDAO = Mockito.mock(IMainStoreStockDAO.class);
        mockItemDAO = Mockito.mock(IItemDAO.class);
        mockSession = Mockito.mock(AdminSession.class);

        // Ensure user is logged in
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        // Inject mocks into service
//        mainStoreStockService = new MainStoreStockService(mockMainStoreStockDAO, mockItemDAO, mockSession);
    }

    @Test
    public void testInsertMainStoreStock_SuccessfulInsertion() {
        InsertMainStoreStockRequest request = new InsertMainStoreStockRequest(
                1, "ITEM001", "BATCH01", 10, 10, LocalDateTime.now()
        );

        // Mock item availability
        Item mockItem = new Item();
        mockItem.setCurrentQuantity(20);
        Mockito.when(mockItemDAO.findByItemCodeAndBatchCode("ITEM001", "BATCH01")).thenReturn(Optional.of(mockItem));

        // Execute method
        Assertions.assertDoesNotThrow(() -> mainStoreStockService.insertMainStoreStock(request));

        // Capture and verify MainStoreStock
        ArgumentCaptor<MainStoreStock> stockCaptor = ArgumentCaptor.forClass(MainStoreStock.class);
        Mockito.verify(mockMainStoreStockDAO, Mockito.times(1)).save(stockCaptor.capture());
        MainStoreStock savedStock = stockCaptor.getValue();

        Assertions.assertEquals("ITEM001", savedStock.getItemCode());
        Assertions.assertEquals("BATCH01", savedStock.getBatchCode());
        Assertions.assertEquals(10, savedStock.getInitialStock());
        Assertions.assertEquals(10, savedStock.getCurrentStock());
    }

    @Test
    public void testInsertMainStoreStock_InsufficientStock_ThrowsException() {
        InsertMainStoreStockRequest request = new InsertMainStoreStockRequest(
                1, "ITEM001", "BATCH01", 10, 10, LocalDateTime.now()
        );

        // Mock item with insufficient stock
        Item mockItem = new Item();
        mockItem.setCurrentQuantity(5);
        Mockito.when(mockItemDAO.findByItemCodeAndBatchCode("ITEM001", "BATCH01")).thenReturn(Optional.of(mockItem));

        // Expect an exception
        Assertions.assertThrows(IllegalStateException.class, () -> mainStoreStockService.insertMainStoreStock(request));

        // Ensure save() was never called
        Mockito.verifyNoInteractions(mockMainStoreStockDAO);
    }

    @Test
    public void testInsertMainStoreStock_ItemNotFound_ThrowsException() {
        InsertMainStoreStockRequest request = new InsertMainStoreStockRequest(
                1, "ITEM001", "BATCH01", 10, 10, LocalDateTime.now()
        );

        // Mock item as non-existent
        Mockito.when(mockItemDAO.findByItemCodeAndBatchCode("ITEM001", "BATCH01")).thenReturn(Optional.empty());

        // Expect an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> mainStoreStockService.insertMainStoreStock(request));

        // Ensure save() was never called
        Mockito.verifyNoInteractions(mockMainStoreStockDAO);
    }

    @Test
    public void testInsertMainStoreStock_UserNotLoggedIn_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        InsertMainStoreStockRequest request = new InsertMainStoreStockRequest(
                1, "ITEM001", "BATCH01", 10, 10, LocalDateTime.now()
        );

        // Expect an exception
        Assertions.assertThrows(IllegalStateException.class, () -> mainStoreStockService.insertMainStoreStock(request));

        // Ensure save() was never called
        Mockito.verifyNoInteractions(mockMainStoreStockDAO);
    }

    @Test
    public void testGetMainStoreStockDetails_SuccessfulRetrieval() {
        int storeId = 1;
        String itemCode = "ITEM001";
        String batchCode = "BATCH01";

        // Mock stock retrieval
        MainStoreStock mockStock = new MainStoreStock();
        mockStock.setStoreID(storeId);
        mockStock.setItemCode(itemCode);
        mockStock.setBatchCode(batchCode);
        mockStock.setInitialStock(10);
        mockStock.setCurrentStock(5);
        mockStock.setLastRestockedDate(LocalDateTime.now());

        Mockito.when(mockMainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(storeId, itemCode, batchCode))
                .thenReturn(Optional.of(mockStock));

        Optional<GetMainStoreStockDetailsDTO> stockDetails = mainStoreStockService.getMainStoreStockDetails(storeId, itemCode, batchCode);

        Assertions.assertTrue(stockDetails.isPresent());
        Assertions.assertEquals(itemCode, stockDetails.get().getItemCode());
        Assertions.assertEquals(batchCode, stockDetails.get().getBatchCode());
        Assertions.assertEquals(10, stockDetails.get().getInitialStock());
    }

    @Test
    public void testDeleteMainStoreStockItem_SuccessfulDeletion() {
        DeleteMainStoreStockItemRequest request = new DeleteMainStoreStockItemRequest(1, "ITEM001", "BATCH01");

        // Mock stock retrieval
        MainStoreStock mockStock = new MainStoreStock();
        mockStock.setStoreID(1);
        mockStock.setItemCode("ITEM001");
        mockStock.setBatchCode("BATCH01");
        mockStock.setCurrentStock(0); // Must be 0 to delete

        Mockito.when(mockMainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(1, "ITEM001", "BATCH01"))
                .thenReturn(Optional.of(mockStock));

        // Execute deletion
        Assertions.assertDoesNotThrow(() -> mainStoreStockService.deleteMainStoreStockItem(request));

        // Verify delete operation
        Mockito.verify(mockMainStoreStockDAO, Mockito.times(1)).softDelete(mockStock);
    }

    @Test
    public void testDeleteMainStoreStockItem_NonZeroStock_ThrowsException() {
        DeleteMainStoreStockItemRequest request = new DeleteMainStoreStockItemRequest(1, "ITEM001", "BATCH01");

        // Mock stock with non-zero quantity
        MainStoreStock mockStock = new MainStoreStock();
        mockStock.setStoreID(1);
        mockStock.setItemCode("ITEM001");
        mockStock.setBatchCode("BATCH01");
        mockStock.setCurrentStock(5); // Must be 0 to delete

        Mockito.when(mockMainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(1, "ITEM001", "BATCH01"))
                .thenReturn(Optional.of(mockStock));

        // Expect an exception
        Assertions.assertThrows(IllegalStateException.class, () -> mainStoreStockService.deleteMainStoreStockItem(request));

        // Ensure delete was never called
        Mockito.verify(mockMainStoreStockDAO, Mockito.never()).softDelete(mockStock);
    }
}
