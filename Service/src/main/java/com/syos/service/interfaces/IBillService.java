package main.java.com.syos.service.interfaces;

import main.java.com.syos.dto.GetBillDTO;
import main.java.com.syos.request.CreateBillRequest;

public interface IBillService {
    int createBill(CreateBillRequest createBillRequest);
    GetBillDTO getBillByID(int billID);
    GetBillDTO getBillBySerialNumber(String serialNumber);
}
