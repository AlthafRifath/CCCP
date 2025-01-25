package main.java.com.syos.data.dao.interfaces;

public interface IAdminDAO {
    Integer getIdByUsernameAndPassword(String username, String password);
}
