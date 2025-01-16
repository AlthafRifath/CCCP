package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MainStoreStock")
public class MainStoreStock {

    @Id
    @Column(name = "StoreID")
    private int storeID;

    @Id
    @Column(name = "ItemCode")
    private String itemCode;

    @Id
    @Column(name = "BatchCode")
    private String batchCode;

    @Column(name = "InitialStock", nullable = false)
    private int initialStock;

    @Column(name = "CurrentStock", nullable = false)
    private int currentStock;

    @Column(name = "LastRestockedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRestockedDate;

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
}
