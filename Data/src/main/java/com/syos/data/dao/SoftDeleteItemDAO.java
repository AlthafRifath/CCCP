package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IItemDAO;
import main.java.com.syos.data.model.Item;
import main.java.com.syos.service.AdminSession;

import java.time.LocalDateTime;
import java.util.Optional;

public class SoftDeleteItemDAO implements IItemDAO {
    private final IItemDAO decoratedDAO;

    public SoftDeleteItemDAO(IItemDAO decoratedDAO) {
        this.decoratedDAO = decoratedDAO;
    }

    @Override
    public void save(Item item) {
        decoratedDAO.save(item);
    }

    @Override
    public Optional<Item> findByItemCodeAndBatchCode(String itemCode, String batchCode) {
        return decoratedDAO.findByItemCodeAndBatchCode(itemCode, batchCode);
    }

    @Override
    public void update(Item item) {
        decoratedDAO.update(item);
    }

    public void softDelete(String itemCode, String batchCode) {
        TransactionManager.execute(session -> {
            Optional<Item> itemOptional = decoratedDAO.findByItemCodeAndBatchCode(itemCode, batchCode);
            if (itemOptional.isEmpty()) {
                throw new IllegalArgumentException("Item not found for ItemCode: " + itemCode +
                        " and BatchCode: " + batchCode);
            }

            Item item = itemOptional.get();
            item.setIsDeleted(true);
            item.setUpdatedBy(AdminSession.getInstance().getLoggedInUserId());
            item.setUpdatedDateTime(LocalDateTime.now());
            session.update(item);
            System.out.println("Item deleted successfully.");
            return null;
        });
    }
}
