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
}