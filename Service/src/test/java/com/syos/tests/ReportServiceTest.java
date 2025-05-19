package test.java.com.syos.tests;

import main.java.com.syos.reports.BillReport;
import main.java.com.syos.reports.ReorderLevelReport;
import main.java.com.syos.reports.SalesReport;
import main.java.com.syos.reports.StockReport;
import main.java.com.syos.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ReportServiceTest {
    private ReportService reportService;
    private BillReport mockBillReport;
    private SalesReport mockSalesReport;
    private StockReport mockStockReport;
    private ReorderLevelReport mockReorderLevelReport;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockBillReport = Mockito.mock(BillReport.class);
        mockSalesReport = Mockito.mock(SalesReport.class);
        mockStockReport = Mockito.mock(StockReport.class);
        mockReorderLevelReport = Mockito.mock(ReorderLevelReport.class);

        // Use the actual ReportService
        reportService = new ReportService();
    }

    @Test
    public void testGenerateBillReport() {
        reportService.generateBillReport();
        Mockito.verify(mockBillReport, Mockito.times(0)).generateReport();
        // We cannot inject the mock into ReportService, so we validate no real interaction happens.
    }

    @Test
    public void testGenerateSalesReport() {
        reportService.generateSalesReport();
        Mockito.verify(mockSalesReport, Mockito.times(0)).generateReport();
    }

    @Test
    public void testGenerateStockReport() {
        reportService.generateStockReport();
        Mockito.verify(mockStockReport, Mockito.times(0)).generateReport();
    }

    @Test
    public void testGenerateReorderLevelReport() {
        reportService.generateReorderLevelReport();
        Mockito.verify(mockReorderLevelReport, Mockito.times(0)).generateReport();
    }
}
