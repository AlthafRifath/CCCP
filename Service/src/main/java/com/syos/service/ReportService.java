package main.java.com.syos.service;

import main.java.com.syos.reports.BillReport;
import main.java.com.syos.reports.ReorderLevelReport;
import main.java.com.syos.reports.SalesReport;
import main.java.com.syos.reports.StockReport;
import main.java.com.syos.service.interfaces.IBillReportService;
import main.java.com.syos.service.interfaces.IReorderLevelReportService;
import main.java.com.syos.service.interfaces.ISalesReportService;
import main.java.com.syos.service.interfaces.IStockReportService;

public class ReportService implements IBillReportService, IReorderLevelReportService, IStockReportService, ISalesReportService {
    @Override
    public void generateSalesReport() {
        new SalesReport().generateReport();
    }

    @Override
    public void generateStockReport() {
        new StockReport().generateReport();
    }

    @Override
    public void generateReorderLevelReport() {
        new ReorderLevelReport().generateReport();
    }

    @Override
    public void generateBillReport() {
        new BillReport().generateReport();
    }
}
