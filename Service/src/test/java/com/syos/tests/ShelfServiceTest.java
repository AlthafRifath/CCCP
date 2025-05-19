package test.java.com.syos.tests;

import main.java.com.syos.data.dao.interfaces.IMainStoreStockDAO;
import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.MainStoreStock;
import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.request.DeleteShelfRequest;
import main.java.com.syos.request.InsertShelfRequest;
import main.java.com.syos.request.UpdateShelfRequest;
import main.java.com.syos.service.AdminSession;
import main.java.com.syos.service.ShelfService;
import main.java.com.syos.data.dao.SoftDeleteShelfDAO;
import main.java.com.syos.service.notifications.interfaces.IStockNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ShelfServiceTest {
    private ShelfService shelfService;
    private IShelfDAO mockShelfDAO;
    private IMainStoreStockDAO mockMainStoreStockDAO;
    private SoftDeleteShelfDAO mockSoftDeleteShelfDAO;
    private IStockNotifier mockStockNotifier;
    private AdminSession mockSession;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockShelfDAO = Mockito.mock(IShelfDAO.class);
        mockMainStoreStockDAO = Mockito.mock(IMainStoreStockDAO.class);
        mockSoftDeleteShelfDAO = Mockito.mock(SoftDeleteShelfDAO.class);
        mockStockNotifier = Mockito.mock(IStockNotifier.class);
        mockSession = Mockito.mock(AdminSession.class);

        // Ensure user is logged in
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        // Inject mocks into ShelfService
//        shelfService = new ShelfService(mockShelfDAO, mockMainStoreStockDAO, mockStockNotifier, mockSoftDeleteShelfDAO, mockSession);
    }

    @Test
    public void testAddShelf_Successful() {
        InsertShelfRequest request = new InsertShelfRequest();
        request.setShelfId(1);
        request.setStoreIdFromMainStoreStock(1);
        request.setStoreIdFromStore(1);
        request.setItemCode("item123");
        request.setBatchCode("batch001");
        request.setQuantityOnShelf(10);

        MainStoreStock mockMainStoreStock = new MainStoreStock();
        mockMainStoreStock.setCurrentStock(20);

        Mockito.when(mockMainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(
                        request.getStoreIdFromMainStoreStock(), request.getItemCode(), request.getBatchCode()))
                .thenReturn(Optional.of(mockMainStoreStock));

        Mockito.doNothing().when(mockShelfDAO).save(Mockito.any());

        assertDoesNotThrow(() -> shelfService.addShelf(request));
        Mockito.verify(mockShelfDAO, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testAddShelf_InsufficientStock_ThrowsException() {
        InsertShelfRequest request = new InsertShelfRequest();
        request.setStoreIdFromMainStoreStock(1);
        request.setItemCode("item123");
        request.setBatchCode("batch001");
        request.setQuantityOnShelf(50); // More than available

        MainStoreStock mockMainStoreStock = new MainStoreStock();
        mockMainStoreStock.setCurrentStock(10); // Not enough stock

        Mockito.when(mockMainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(
                        request.getStoreIdFromMainStoreStock(), request.getItemCode(), request.getBatchCode()))
                .thenReturn(Optional.of(mockMainStoreStock));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> shelfService.addShelf(request));
        assertEquals("Insufficient stock in MainStoreStock for StoreID: 1", exception.getMessage());

        Mockito.verify(mockShelfDAO, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void testUpdateShelf_Successful() {
        UpdateShelfRequest request = new UpdateShelfRequest();
        request.setStoreId(1);
        request.setShelfId(1);
        request.setItemCode("item123");
        request.setBatchCode("batch001");
        request.setQuantityToAdd(5);

        Shelf mockShelf = new Shelf();
        mockShelf.setQuantityOnShelf(10);

        MainStoreStock mockMainStoreStock = new MainStoreStock();
        mockMainStoreStock.setCurrentStock(10);

        Mockito.when(mockShelfDAO.findByCompositeKey(
                        request.getStoreId(), request.getShelfId(), request.getItemCode(), request.getBatchCode()))
                .thenReturn(Optional.of(mockShelf));

        Mockito.when(mockMainStoreStockDAO.findByStoreIdAndItemCodeAndBatchCode(
                        request.getStoreIdFromMainStoreStock(), request.getItemCode(), request.getBatchCode()))
                .thenReturn(Optional.of(mockMainStoreStock));

        assertDoesNotThrow(() -> shelfService.updateShelf(request));
        Mockito.verify(mockShelfDAO, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    public void testUpdateShelf_StockNotFound_ThrowsException() {
        UpdateShelfRequest request = new UpdateShelfRequest();
        request.setStoreId(1);
        request.setShelfId(1);
        request.setItemCode("item123");
        request.setBatchCode("batch001");

        Mockito.when(mockShelfDAO.findByCompositeKey(
                        request.getStoreId(), request.getShelfId(), request.getItemCode(), request.getBatchCode()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> shelfService.updateShelf(request));
        assertEquals("Shelf not found for StoreID: 1, ShelfID: 1, ItemCode: item123, BatchCode: batch001", exception.getMessage());

        Mockito.verify(mockShelfDAO, Mockito.never()).update(Mockito.any());
    }

    @Test
    public void testDeleteShelf_Successful() {
        DeleteShelfRequest request = new DeleteShelfRequest();
        request.setStoreId(1);
        request.setShelfId(1);
        request.setItemCode("item123");
        request.setBatchCode("batch001");

        Mockito.doNothing().when(mockSoftDeleteShelfDAO).softDeleteShelf(
                request.getStoreId(), request.getShelfId(), request.getItemCode(), request.getBatchCode(), 1);

        assertDoesNotThrow(() -> shelfService.deleteShelf(request));
        Mockito.verify(mockSoftDeleteShelfDAO, Mockito.times(1)).softDeleteShelf(
                request.getStoreId(), request.getShelfId(), request.getItemCode(), request.getBatchCode(), 1);
    }

    @Test
    public void testDeleteShelf_UserNotLoggedIn_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        DeleteShelfRequest request = new DeleteShelfRequest();
        request.setStoreId(1);
        request.setShelfId(1);
        request.setItemCode("item123");
        request.setBatchCode("batch001");

        Exception exception = assertThrows(IllegalStateException.class, () -> shelfService.deleteShelf(request));
        assertEquals("User must be logged in to perform this operation", exception.getMessage());

        Mockito.verify(mockSoftDeleteShelfDAO, Mockito.never()).softDeleteShelf(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt());
    }
}
