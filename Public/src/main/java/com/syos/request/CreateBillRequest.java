package main.java.com.syos.request;

import java.math.BigDecimal;
import java.util.List;

public class CreateBillRequest {
    private Integer customerID;
    private Integer discountID;
    private BigDecimal cashTendered;
    private List<BillItemRequest> billItems;

    // Getters and Setters
    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getDiscountID() {
        return discountID;
    }

    public void setDiscountID(Integer discountID) {
        this.discountID = discountID;
    }

    public BigDecimal getCashTendered() {
        return cashTendered;
    }

    public void setCashTendered(BigDecimal cashTendered) {
        this.cashTendered = cashTendered;
    }

    public List<BillItemRequest> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItemRequest> billItems) {
        this.billItems = billItems;
    }
}
