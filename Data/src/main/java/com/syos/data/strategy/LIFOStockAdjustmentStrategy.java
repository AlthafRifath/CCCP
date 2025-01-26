package main.java.com.syos.data.strategy;

import main.java.com.syos.data.strategy.interfaces.IStockAdjustmentStrategy;

public class LIFOStockAdjustmentStrategy implements IStockAdjustmentStrategy {
    @Override
    public int adjustStock(int currentStock, int adjustmentQuantity) {
        return currentStock + adjustmentQuantity;
    }
}
