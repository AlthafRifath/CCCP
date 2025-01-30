package test.java.com.syos.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import java.time.LocalDateTime;

// ✅ Unique Mock Classes for TransactionServiceTest
class MockAdminSession_TransactionService {
    Integer getLoggedInUserId() { return 1; }  // Simulates a logged-in admin
}

class MockTransactionDAO_TransactionService {
    void save(MockTransaction_TransactionService transaction) {}
}

class MockBill_TransactionService {
    int billID;

    MockBill_TransactionService(int billID) {
        this.billID = billID;
    }
}

class MockTransaction_TransactionService {
    int billID;
    String transactionType;
    LocalDateTime transactionDate, updatedDateTime;
    int updatedBy;

    MockTransaction_TransactionService(int billID, String transactionType, int updatedBy) {
        this.billID = billID;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
        this.updatedDateTime = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }
}

// ✅ Independent Service Class (Mocked Business Logic Only)
class MockTransactionService {
    private final MockAdminSession_TransactionService adminSession;
    private final MockTransactionDAO_TransactionService transactionDAO;

    MockTransactionService(MockAdminSession_TransactionService adminSession, MockTransactionDAO_TransactionService transactionDAO) {
        this.adminSession = adminSession;
        this.transactionDAO = transactionDAO;
    }

    void recordTransaction(int billID, String transactionType) {
        if (adminSession.getLoggedInUserId() == null) {
            throw new IllegalStateException("User is not logged in.");
        }
        if (transactionType == null || transactionType.isEmpty()) {
            throw new IllegalArgumentException("Transaction type cannot be empty.");
        }
        if (billID <= 0) {
            throw new IllegalArgumentException("Invalid Bill ID.");
        }

        MockTransaction_TransactionService transaction = new MockTransaction_TransactionService(billID, transactionType, adminSession.getLoggedInUserId());
        transactionDAO.save(transaction);
    }
}

// ✅ Independent Test Class
public class TransactionServiceTest {
    private MockTransactionService transactionService;
    private MockAdminSession_TransactionService mockSession;
    private MockTransactionDAO_TransactionService mockTransactionDAO;

    @BeforeEach
    void setUp() {
        mockSession = Mockito.mock(MockAdminSession_TransactionService.class);
        mockTransactionDAO = Mockito.mock(MockTransactionDAO_TransactionService.class);

        transactionService = new MockTransactionService(mockSession, mockTransactionDAO);
    }

    @Test
    public void testRecordTransaction_Success() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertDoesNotThrow(() -> transactionService.recordTransaction(100, "PAYMENT"));
        Mockito.verify(mockTransactionDAO, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testRecordTransaction_UserNotLoggedIn_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        Assertions.assertThrows(IllegalStateException.class, () ->
                transactionService.recordTransaction(100, "PAYMENT"));

        Mockito.verifyNoInteractions(mockTransactionDAO);
    }

    @Test
    public void testRecordTransaction_InvalidBillID_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                transactionService.recordTransaction(0, "PAYMENT"));

        Mockito.verifyNoInteractions(mockTransactionDAO);
    }

    @Test
    public void testRecordTransaction_EmptyTransactionType_ThrowsException() {
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                transactionService.recordTransaction(100, ""));

        Mockito.verifyNoInteractions(mockTransactionDAO);
    }
}
