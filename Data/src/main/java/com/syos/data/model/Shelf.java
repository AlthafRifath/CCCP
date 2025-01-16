package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Shelf")
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShelfID")
    private int shelfID;

    @Column(name = "ItemCode", nullable = false)
    private String itemCode;

    @Column(name = "BatchCode", nullable = false)
    private String batchCode;

    @Column(name = "QuantityOnShelf", nullable = false)
    private int quantityOnShelf;

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
