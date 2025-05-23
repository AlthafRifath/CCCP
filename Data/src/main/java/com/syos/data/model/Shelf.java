package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tblShelf")
public class Shelf {

    @Id
    @Column(name = "StoreID", nullable = false)
    private int storeID;

    @Id
    @Column(name = "ShelfID", nullable = false)
    private int shelfID;

    @Id
    @Column(name = "ItemCode", nullable = false)
    private String itemCode;

    @Id
    @Column(name = "BatchCode", nullable = false)
    private String batchCode;

    @Column(name = "QuantityOnShelf", nullable = false)
    private int quantityOnShelf;

    @Column(name = "LastRestockedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastRestockedDate;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "UpdatedDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedDateTime;

    // Relationships
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ItemCode", referencedColumnName = "ItemCode", insertable = false, updatable = false),
            @JoinColumn(name = "BatchCode", referencedColumnName = "BatchCode", insertable = false, updatable = false)
    })
    private Item item;

    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;

    @ManyToOne
    @JoinColumn(name = "StoreID", referencedColumnName = "StoreID", insertable = false, updatable = false)
    private Store store;

    // Getters and Setters
    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getShelfID() {
        return shelfID;
    }

    public void setShelfID(int shelfID) {
        this.shelfID = shelfID;
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

    public int getQuantityOnShelf() {
        return quantityOnShelf;
    }

    public void setQuantityOnShelf(int quantityOnShelf) {
        this.quantityOnShelf = quantityOnShelf;
    }

    public LocalDateTime getLastRestockedDate() {
        return lastRestockedDate;
    }

    public void setLastRestockedDate(LocalDateTime lastRestockedDate) {
        this.lastRestockedDate = lastRestockedDate;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    // Override equals() and hashCode() in all composite key classes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shelf)) return false;
        Shelf shelf = (Shelf) o;
        return getStoreID() == shelf.getStoreID() &&
                getShelfID() == shelf.getShelfID() &&
                Objects.equals(getItemCode(), shelf.getItemCode()) &&
                Objects.equals(getBatchCode(), shelf.getBatchCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStoreID(), getShelfID(), getItemCode(), getBatchCode());
    }
}
