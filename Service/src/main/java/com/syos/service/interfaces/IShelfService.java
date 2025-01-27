package main.java.com.syos.service.interfaces;

import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.dto.GetShelfDetailsDTO;
import main.java.com.syos.request.InsertShelfRequest;

import java.util.List;

public interface IShelfService {
    void addShelf(InsertShelfRequest request);
    List<GetShelfDetailsDTO> getShelfDetailsByShelfIdAndStoreId(int shelfId, int storeId);
}
