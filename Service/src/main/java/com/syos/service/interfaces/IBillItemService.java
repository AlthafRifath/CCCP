package main.java.com.syos.service.interfaces;

import main.java.com.syos.data.model.BillItem;
import main.java.com.syos.request.BillItemRequest;

import java.util.List;

public interface IBillItemService {
    void addBillItems(int billID, List<BillItemRequest> billItems);
}
