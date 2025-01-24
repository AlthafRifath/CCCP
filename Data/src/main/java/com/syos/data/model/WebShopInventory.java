package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tblWebShopInventory")
public class WebShopInventory {

    @Id
    @Column(name = "WebShopID")
    private int webShopID;

    @Id
    @Column(name = "ItemCode")
    private String itemCode;

    @Id
    @Column(name = "BatchCode")
    private String batchCode;

    @Column(name = "QuantityOnline", nullable = false)
    private int quantityOnline;

    @Column(name = "LastUpdatedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "UpdatedDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDateTime;

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

    // Getters and Setters
    public int getWebShopID() {
        return webShopID;
    }

    public void setWebShopID(int webShopID) {
        this.webShopID = webShopID;
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

    public int getQuantityOnline() {
        return quantityOnline;
    }

    public void setQuantityOnline(int quantityOnline) {
        this.quantityOnline = quantityOnline;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
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
        if (!(o instanceof WebShopInventory)) return false;
        WebShopInventory that = (WebShopInventory) o;
        return getWebShopID() == that.getWebShopID() && getItemCode().equals(that.getItemCode()) && getBatchCode().equals(that.getBatchCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWebShopID(), getItemCode(), getBatchCode());
    }
}
