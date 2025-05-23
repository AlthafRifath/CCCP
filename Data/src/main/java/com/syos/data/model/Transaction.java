package main.java.com.syos.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tblTransaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private int transactionID;

    @Column(name = "BillID", nullable = false)
    private int billID;

    @Column(name = "TransactionType", nullable = false)
    private String transactionType;

    @Column(name = "TransactionDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "UpdatedDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime  updatedDateTime;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "BillID", insertable = false, updatable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;

    // Getters and Setters
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime  getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime  transactionDate) {
        this.transactionDate = transactionDate;
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

    public LocalDateTime  getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime  updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public User getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(User updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    //Override equals() and hashCode() in all composite key classes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        return transactionID != 0 && transactionID == (((Transaction) o).transactionID);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
