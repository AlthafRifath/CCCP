package main.java.com.syos.data.dao;

import main.java.com.syos.data.dao.interfaces.IShelfDAO;
import main.java.com.syos.data.model.Shelf;

import java.util.List;
import java.util.Optional;

public class SoftDeleteShelfDAO implements IShelfDAO{
    private final IShelfDAO decoratedDAO;

    public SoftDeleteShelfDAO(IShelfDAO decoratedDAO) {
        this.decoratedDAO = new ShelfDAO();
    }

    @Override
    public void save(Shelf shelf) {
        decoratedDAO.save(shelf);
    }

    @Override
    public List<Shelf> findByShelfIdAndStoreId(int shelfId, int storeId) {
        return List.of();
    }

    @Override
    public Optional<Shelf> findByCompositeKey(int storeId, int shelfId, String itemCode, String batchCode) {
        return decoratedDAO.findByCompositeKey(storeId, shelfId, itemCode, batchCode);
    }

    @Override
    public void update(Shelf shelf) {
        decoratedDAO.update(shelf);
    }

    public void softDeleteShelf(int storeId, int shelfId, String itemCode, String batchCode, int updatedBy) {
        Optional<Shelf> optionalShelf = decoratedDAO.findByCompositeKey(storeId, shelfId, itemCode, batchCode);
        if (optionalShelf.isEmpty()) {
            throw new IllegalArgumentException("Shelf not found with StoreID: " + storeId + ", ShelfID: " + shelfId +
                    ", ItemCode: " + itemCode + ", BatchCode: " + batchCode);
        }

        Shelf shelf = optionalShelf.get();
        shelf.setDeleted(true);
        shelf.setUpdatedBy(updatedBy);
        shelf.setUpdatedDateTime(java.time.LocalDateTime.now());
        decoratedDAO.update(shelf);
        System.out.println("Shelf marked as deleted successfully.");
    }
}
