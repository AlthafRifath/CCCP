package test.java.com.syos.tests;

import main.java.com.syos.data.dao.interfaces.IItemDAO;
import main.java.com.syos.data.model.Item;
import main.java.com.syos.request.InsertItemRequest;
import main.java.com.syos.service.AdminSession;
import main.java.com.syos.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

public class ItemServiceTest {
    private ItemService itemService;
    private IItemDAO mockItemDAO;
    private AdminSession mockSession;

    @BeforeEach
    void setUp() {
        mockItemDAO = Mockito.mock(IItemDAO.class);

        mockSession = Mockito.mock(AdminSession.class);
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

//        itemService = new ItemService(mockItemDAO, mockSession);
    }

    @Test
    public void testInsertItem_SuccessfulInsertion() {
        InsertItemRequest request = new InsertItemRequest(
                "item123", "batch001", "Test Item", 50.0,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                10, 10
        );

        Mockito.doNothing().when(mockItemDAO).save(Mockito.any(Item.class));
        Mockito.when(mockItemDAO.findByItemCodeAndBatchCode("item123", "batch001"))
                .thenReturn(Optional.of(new Item())); // Fake return value

        Assertions.assertDoesNotThrow(() -> itemService.InsertItem(request));

        Mockito.verify(mockItemDAO, Mockito.times(1)).save(Mockito.any(Item.class));
    }

    @Test
    public void testInsertItem_InvalidPrice_ThrowsException() {
        InsertItemRequest request = new InsertItemRequest(
                "item123", "batch001", "Test Item", -10.0,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                10, 10
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> itemService.InsertItem(request));

        Mockito.verifyNoInteractions(mockItemDAO);
    }

    @Test
    public void testInsertItem_InvalidInitialQuantity_ThrowsException() {
        InsertItemRequest request = new InsertItemRequest(
                "item123", "batch001", "Test Item", 50.0,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                0, 10
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> itemService.InsertItem(request));

        Mockito.verifyNoInteractions(mockItemDAO);
    }

    @Test
    public void testInsertItem_UserNotLoggedIn_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        InsertItemRequest request = new InsertItemRequest(
                "item123", "batch001", "Test Item", 50.0,
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(30),
                10, 10
        );

        Assertions.assertThrows(IllegalStateException.class, () -> itemService.InsertItem(request));

        Mockito.verifyNoInteractions(mockItemDAO);
    }
}
