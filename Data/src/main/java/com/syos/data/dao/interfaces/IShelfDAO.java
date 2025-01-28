package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.Shelf;

import java.util.List;
import java.util.Optional;

public interface IShelfDAO {
    void save(Shelf shelf);
    List<Shelf> findByShelfIdAndStoreId(int shelfId, int storeId);
    Optional<Shelf> findByCompositeKey(int storeId, int shelfId, String itemCode, String batchCode);
    void update(Shelf shelf);
    Shelf findByItemAndBatch(String itemCode, String batchCode);
}
