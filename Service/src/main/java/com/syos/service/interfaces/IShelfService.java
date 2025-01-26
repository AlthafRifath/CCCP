package main.java.com.syos.service.interfaces;

import main.java.com.syos.data.model.Shelf;
import main.java.com.syos.request.InsertShelfRequest;

public interface IShelfService {
    void addShelf(InsertShelfRequest request);
}
