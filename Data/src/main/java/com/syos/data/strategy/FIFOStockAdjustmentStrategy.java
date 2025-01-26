package main.java.com.syos.data.strategy;

import main.java.com.syos.data.strategy.interfaces.IStockAdjustmentStrategy;

public class FIFOStockAdjustmentStrategy implements IStockAdjustmentStrategy {
    @Override
    public int adjustStock(int currentStock, int adjustmentQuantity) {
        return currentStock - adjustmentQuantity;
    }
}
