package main.java.com.syos.data.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tblBill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillID")
    private int billID;

    @Column(name = "CustomerID")
    private Integer customerID;

    @Column(name = "DiscountID")
    private Integer discountID;

    @Column(name = "SerialNumber", nullable = false)
    private String serialNumber;

    @Column(name = "BillDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime billDate;

    @Column(name = "TotalAmount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "CashTendered", nullable = false)
    private BigDecimal cashTendered;

    @Column(name = "Change", nullable = false)
    private BigDecimal change;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "UpdatedDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedDateTime;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "CustomerID", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "DiscountID", insertable = false, updatable = false)
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BillItem> billItems = new ArrayList<>();

    @OneToMany(mappedBy = "bill")
    private List<Transaction> transactions;

    // Getters and Setters
    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getCashTendered() {
        return cashTendered;
    }

    public void setCashTendered(BigDecimal cashTendered) {
        this.cashTendered = cashTendered;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public User getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(User updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    // Override equals() and hashCode() in all composite key classes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill)) return false;
        Bill bill = (Bill) o;
        return getBillID() == bill.getBillID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBillID());
    }
}
