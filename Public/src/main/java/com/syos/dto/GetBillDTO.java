package main.java.com.syos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GetBillDTO {
    private int billID;
    private Integer customerID;
    private Integer discountID;
    private String serialNumber;
    private LocalDateTime billDate;
    private BigDecimal totalAmount;
    private BigDecimal cashTendered;
    private BigDecimal change;
    private List<GetBillItemDTO> billItems;

    public GetBillDTO(int billID, Integer customerID, Integer discountID, String serialNumber, LocalDateTime billDate, BigDecimal totalAmount, BigDecimal cashTendered, BigDecimal change, List<GetBillItemDTO> billItems) {
        this.billID = billID;
        this.customerID = customerID;
        this.discountID = discountID;
        this.serialNumber = serialNumber;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
        this.cashTendered = cashTendered;
        this.change = change;
        this.billItems = billItems;
    }

    // Getters
    public int getBillID() {
        return billID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public Integer getDiscountID() {
        return discountID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getCashTendered() {
        return cashTendered;
    }

    public BigDecimal getChange() {
        return change;
    }

    public List<GetBillItemDTO> getBillItems() {
        return billItems;
    }
}
