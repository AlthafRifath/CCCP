package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Item")
public class Item {

    @Id
    @Column(name = "ItemCode")
    private String itemCode;

    @Id
    @Column(name = "BatchCode")
    private String batchCode;

    @Column(name = "ItemName", nullable = false)
    private String itemName;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "PurchaseDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @Column(name = "ExpiryDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    @Column(name = "InitialQuantity", nullable = false)
    private int initialQuantity;

    @Column(name = "CurrentQuantity", nullable = false)
    private int currentQuantity;

    @Column(name = "IsActive", nullable = false)
    private boolean isActive;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "UpdatedDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDateTime;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;

    @OneToMany(mappedBy = "item")
    private List<Shelf> shelves;

    @OneToMany(mappedBy = "item")
    private List<MainStoreStock> mainStoreStocks;

    @OneToMany(mappedBy = "item")
    private List<WebShopInventory> webShopInventories;

    @OneToMany(mappedBy = "item")
    private List<BillItem> billItems;

    @OneToMany(mappedBy = "item")
    private List<Return> returns;

    // Getters and Setters
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public User getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(User updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public List<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(List<Shelf> shelves) {
        this.shelves = shelves;
    }

    public List<MainStoreStock> getMainStoreStocks() {
        return mainStoreStocks;
    }

    public void setMainStoreStocks(List<MainStoreStock> mainStoreStocks) {
        this.mainStoreStocks = mainStoreStocks;
    }

    public List<WebShopInventory> getWebShopInventories() {
        return webShopInventories;
    }

    public void setWebShopInventories(List<WebShopInventory> webShopInventories) {
        this.webShopInventories = webShopInventories;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public List<Return> getReturns() {
        return returns;
    }

    public void setReturns(List<Return> returns) {
        this.returns = returns;
    }

    // Constructors
    public Item() {
    }

    public Item(String itemCode, String batchCode) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
    }

    public Item(String itemCode, String batchCode, String itemName, double price, Date purchaseDate, Date expiryDate, int initialQuantity, int currentQuantity, boolean isActive, boolean isDeleted, Integer updatedBy, Date updatedDateTime) {
        this.itemCode = itemCode;
        this.batchCode = batchCode;
        this.itemName = itemName;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
    }
}