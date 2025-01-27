package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.Shelf;

import java.util.List;

public interface IShelfDAO {
    void save(Shelf shelf);
    List<Shelf> findByShelfId(int shelfId);
}
