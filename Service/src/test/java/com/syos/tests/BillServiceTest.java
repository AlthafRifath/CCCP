package test.java.com.syos.tests;

import main.java.com.syos.data.dao.interfaces.IBillDAO;
import main.java.com.syos.data.dao.interfaces.IBillItemDAO;
import main.java.com.syos.data.model.Bill;
import main.java.com.syos.dto.GetBillDTO;
import main.java.com.syos.request.CreateBillRequest;
import main.java.com.syos.service.AdminSession;
import main.java.com.syos.service.BillService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class BillServiceTest {

    private BillService billService;
    private IBillDAO mockBillDAO;
    private IBillItemDAO mockBillItemDAO;
    private AdminSession mockSession;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockBillDAO = Mockito.mock(IBillDAO.class);
        mockBillItemDAO = Mockito.mock(IBillItemDAO.class);
        mockSession = Mockito.mock(AdminSession.class);

        // Ensure user is logged in
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(1);

        // Inject mocks into service
//        billService = new BillService(mockBillDAO, mockBillItemDAO, mockSession);
    }

    @Test
    public void testCreateBill_SuccessfulCreation() {
        CreateBillRequest request = new CreateBillRequest();
        request.setCashTendered(new BigDecimal("100.00"));

        // Mock Bill object to return
        Bill mockBill = new Bill();
        mockBill.setBillID(1);
        mockBill.setSerialNumber("BILL-123");
        mockBill.setBillDate(LocalDateTime.now());
        mockBill.setTotalAmount(new BigDecimal("100.00"));
        mockBill.setCashTendered(new BigDecimal("100.00"));
        mockBill.setChange(new BigDecimal("0.00"));
        mockBill.setUpdatedBy(1);
        mockBill.setUpdatedDateTime(LocalDateTime.now());

        // Mock DAO behavior
        Mockito.when(mockBillDAO.save(Mockito.any(Bill.class))).thenReturn(mockBill.getBillID());

        // Execute method and verify success
        int billId = billService.createBill(request);
        Assertions.assertEquals(1, billId); // Expect mocked ID 1

        // Ensure save() was called once
        Mockito.verify(mockBillDAO, Mockito.times(1)).save(Mockito.any(Bill.class));
    }

    @Test
    public void testCreateBill_UserNotLoggedIn_ThrowsException() {
        // Simulate user not logged in
        Mockito.when(mockSession.getLoggedInUserId()).thenReturn(null);

        CreateBillRequest request = new CreateBillRequest();
        request.setCashTendered(new BigDecimal("100.00"));

        // Expect an IllegalStateException
        Assertions.assertThrows(IllegalStateException.class, () -> billService.createBill(request));

        // Ensure save() was never called
        Mockito.verifyNoInteractions(mockBillDAO);
    }

    @Test
    public void testGetBillByID_BillFound() {
        // Mock Bill entity
        Bill bill = new Bill();
        bill.setBillID(1);
        bill.setSerialNumber("BILL-123");
        bill.setBillDate(LocalDateTime.now());
        bill.setTotalAmount(new BigDecimal("150.00"));
        bill.setCashTendered(new BigDecimal("200.00"));
        bill.setChange(new BigDecimal("50.00"));

        // Mock DAO behavior
        Mockito.when(mockBillDAO.findByBillID(1)).thenReturn(bill);

        // Execute method and verify result
        GetBillDTO billDTO = billService.getBillByID(1);
        Assertions.assertNotNull(billDTO);
        Assertions.assertEquals("BILL-123", billDTO.getSerialNumber());
    }

    @Test
    public void testGetBillByID_BillNotFound_ThrowsException() {
        Mockito.when(mockBillDAO.findByBillID(1)).thenReturn(null);

        // Expect IllegalArgumentException
        Assertions.assertThrows(IllegalArgumentException.class, () -> billService.getBillByID(1));
    }

    @Test
    public void testGetBillBySerialNumber_BillFound() {
        Bill bill = new Bill();
        bill.setBillID(1);
        bill.setSerialNumber("BILL-123");

        Mockito.when(mockBillDAO.findBySerialNumber("BILL-123")).thenReturn(bill);

        GetBillDTO billDTO = billService.getBillBySerialNumber("BILL-123");
        Assertions.assertNotNull(billDTO);
        Assertions.assertEquals("BILL-123", billDTO.getSerialNumber());
    }

    @Test
    public void testGetBillBySerialNumber_BillNotFound_ThrowsException() {
        Mockito.when(mockBillDAO.findBySerialNumber("BILL-123")).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> billService.getBillBySerialNumber("BILL-123"));
    }
}
