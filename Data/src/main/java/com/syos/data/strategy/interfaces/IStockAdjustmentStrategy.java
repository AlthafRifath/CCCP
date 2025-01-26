package main.java.com.syos.data.strategy.interfaces;

public interface IStockAdjustmentStrategy {
    int adjustStock(int currentStock, int adjustmentQuantity);
}
