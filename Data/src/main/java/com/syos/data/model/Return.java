package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tblReturn")
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReturnID")
    private int returnID;

    @Column(name = "BillID", nullable = false)
    private int billID;

    @Column(name = "ItemCode", nullable = false)
    private String itemCode;

    @Column(name = "BatchCode", nullable = false)
    private String batchCode;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "ReturnDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @Column(name = "Reason", nullable = false)
    private String reason;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "UpdatedDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDateTime;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "BillID", insertable = false, updatable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ItemCode", referencedColumnName = "ItemCode", insertable = false, updatable = false),
            @JoinColumn(name = "BatchCode", referencedColumnName = "BatchCode", insertable = false, updatable = false)
    })
    private Item item;

    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;

    // Getters and Setters
    public int getReturnID() {
        return returnID;
    }

    public void setReturnID(int returnID) {
        this.returnID = returnID;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(User updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    // Override equals() and hashCode() in all composite key classes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Return)) return false;
        Return aReturn = (Return) o;
        return getReturnID() == aReturn.getReturnID() && getBillID() == aReturn.getBillID() && getQuantity() == aReturn.getQuantity() && isDeleted() == aReturn.isDeleted() && getItemCode().equals(aReturn.getItemCode()) && getBatchCode().equals(aReturn.getBatchCode()) && getReturnDate().equals(aReturn.getReturnDate()) && getReason().equals(aReturn.getReason()) && getUpdatedBy().equals(aReturn.getUpdatedBy()) && getUpdatedDateTime().equals(aReturn.getUpdatedDateTime()) && getBill().equals(aReturn.getBill()) && getItem().equals(aReturn.getItem()) && getUpdatedByUser().equals(aReturn.getUpdatedByUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReturnID(), getBillID(), getItemCode(), getBatchCode(), getQuantity(), getReturnDate(), getReason(), isDeleted(), getUpdatedBy(), getUpdatedDateTime(), getBill(), getItem(), getUpdatedByUser());
    }
}
