package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IAdminDAO;

public class AdminDAO implements IAdminDAO {
    @Override
    public Integer getIdByUsernameAndPassword(String username, String password) {
        return TransactionManager.execute(session -> {
            String hql = "SELECT a.id FROM User a WHERE a.username = :username AND a.passwordHash = :password";
            return session.createQuery(hql, Integer.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
        });
    }
}