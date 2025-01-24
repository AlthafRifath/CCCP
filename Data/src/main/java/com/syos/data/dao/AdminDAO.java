package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.IAdminDAO;

public class AdminDAO implements IAdminDAO {
    @Override
    public boolean validateCredentials(String username, String password) {
        return TransactionManager.execute(session -> {
            String hql = "SELECT COUNT(a) FROM User a WHERE a.username = :username AND a.passwordHash = :password";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            return count != null && count > 0;
        });
    }
}