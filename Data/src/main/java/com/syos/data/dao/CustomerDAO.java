package main.java.com.syos.data.dao;

import com.syos.util.TransactionManager;
import main.java.com.syos.data.dao.interfaces.ICustomerDAO;
import main.java.com.syos.data.model.Customer;
import org.hibernate.query.Query;

public class CustomerDAO implements ICustomerDAO {

    @Override
    public boolean isValidLogin(String email, String password) {
        return TransactionManager.execute(session -> {
            String hql = "FROM Customer WHERE email = :email AND passwordHash = :password";
            Query<Customer> query = session.createQuery(hql, Customer.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult() != null;
        });
    }

    @Override
    public boolean isEmailTaken(String email) {
        return TransactionManager.execute(session -> {
            String hql = "SELECT 1 FROM Customer WHERE email = :email AND isDeleted = false";
            Query<?> query = session.createQuery(hql);
            query.setParameter("email", email);
            return query.uniqueResult() != null;
        });
    }

    @Override
    public boolean save(Customer customer) {
        return TransactionManager.execute(session -> {
            try {
                session.save(customer);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    public Customer getByEmailAndPassword(String email, String password) {
        return TransactionManager.execute(session -> {
            Query<Customer> query = session.createQuery(
                    "FROM Customer c WHERE c.email = :email AND c.passwordHash = :password AND c.isDeleted = false",
                    Customer.class
            );
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult();
        });
    }

    @Override
    public Customer findByEmailOnly(String email) {
        return TransactionManager.execute(session ->
                session.createQuery("FROM Customer WHERE email = :email AND isDeleted = false", Customer.class)
                        .setParameter("email", email)
                        .uniqueResult()
        );
    }

}
