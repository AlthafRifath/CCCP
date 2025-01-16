package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BillItem")
public class BillItem {

    @Id
    @Column(name = "BillID")
    private int billID;

    @Id
    @Column(name = "ItemCode")
    private String itemCode;

    @Id
    @Column(name = "BatchCode")
    private String batchCode;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "PricePerItem", nullable = false)
    private double pricePerItem;

    @Column(name = "TotalItemPrice", nullable = false)
    private double totalItemPrice;

    @Column(name = "DiscountID")
    private Integer discountID;

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
    @JoinColumn(name = "DiscountID", insertable = false, updatable = false)
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;
}
