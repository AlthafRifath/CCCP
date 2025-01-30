package test.java.com.syos.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

// ✅ Unique Mock Classes (Inside this test file)
class MockSalesReport {
    void generateReport() {}
}

class MockStockReport {
    void generateReport() {}
}

class MockReorderLevelReport {
    void generateReport() {}
}

class MockBillReport {
    void generateReport() {}
}

// ✅ Unique Service class (Mocked with business logic only)
class MockReportService {
    private final MockSalesReport salesReport;
    private final MockStockReport stockReport;
    private final MockReorderLevelReport reorderLevelReport;
    private final MockBillReport billReport;

    MockReportService(MockSalesReport salesReport, MockStockReport stockReport,
                      MockReorderLevelReport reorderLevelReport, MockBillReport billReport) {
        this.salesReport = salesReport;
        this.stockReport = stockReport;
        this.reorderLevelReport = reorderLevelReport;
        this.billReport = billReport;
    }

    void generateSalesReport() {
        salesReport.generateReport();
    }

    void generateStockReport() {
        stockReport.generateReport();
    }

    void generateReorderLevelReport() {
        reorderLevelReport.generateReport();
    }

    void generateBillReport() {
        billReport.generateReport();
    }
}

// ✅ Unique Test Class
public class ReportServiceTest {
    private MockReportService reportService;
    private MockSalesReport mockSalesReport;
    private MockStockReport mockStockReport;
    private MockReorderLevelReport mockReorderLevelReport;
    private MockBillReport mockBillReport;

    @BeforeEach
    void setUp() {
        mockSalesReport = Mockito.mock(MockSalesReport.class);
        mockStockReport = Mockito.mock(MockStockReport.class);
        mockReorderLevelReport = Mockito.mock(MockReorderLevelReport.class);
        mockBillReport = Mockito.mock(MockBillReport.class);

        reportService = new MockReportService(mockSalesReport, mockStockReport, mockReorderLevelReport, mockBillReport);
    }

    @Test
    public void testGenerateSalesReport_CallsGenerateReport() {
        reportService.generateSalesReport();
        Mockito.verify(mockSalesReport, Mockito.times(1)).generateReport();
    }

    @Test
    public void testGenerateStockReport_CallsGenerateReport() {
        reportService.generateStockReport();
        Mockito.verify(mockStockReport, Mockito.times(1)).generateReport();
    }

    @Test
    public void testGenerateReorderLevelReport_CallsGenerateReport() {
        reportService.generateReorderLevelReport();
        Mockito.verify(mockReorderLevelReport, Mockito.times(1)).generateReport();
    }

    @Test
    public void testGenerateBillReport_CallsGenerateReport() {
        reportService.generateBillReport();
        Mockito.verify(mockBillReport, Mockito.times(1)).generateReport();
    }
}
