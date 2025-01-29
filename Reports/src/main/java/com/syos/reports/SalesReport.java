package main.java.com.syos.reports;

import main.java.com.syos.data.dao.BillDAO;
import main.java.com.syos.data.model.BillItem;

import java.math.BigDecimal;
import java.util.List;

public class SalesReport extends AbstractReportGenerator{
    private final BillDAO billDAO;
    private List<BillItem> billItems;
    private BigDecimal totalRevenue = BigDecimal.ZERO;

    public SalesReport() {
        this.billDAO = new BillDAO();
    }

    @Override
    protected void fetchData() {
        System.out.println("Fetching sales data...");
        billItems = billDAO.getSalesForToday();
    }

    @Override
    protected void processData() {
        System.out.println("Processing sales data...");
        for (BillItem item : billItems) {
            totalRevenue = totalRevenue.add(item.getTotalItemPrice());
        }
    }

    @Override
    protected void formatReport() {
        System.out.println("\n=== Sales Report ===");
        System.out.println("Item Name | Quantity Sold | Revenue");
        for (BillItem item : billItems) {
            System.out.println(item.getItem().getItemName() + " | " + item.getQuantity() + " | " + item.getTotalItemPrice());
        }
        System.out.println("Total Revenue: " + totalRevenue);
    }
}
