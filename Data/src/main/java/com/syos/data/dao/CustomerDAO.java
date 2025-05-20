package main.java.com.syos.data.dao;

import com.syos.util.HibernateUtil;
import main.java.com.syos.data.dao.interfaces.ICustomerDAO;
import main.java.com.syos.data.model.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class CustomerDAO implements ICustomerDAO {

    @Override
    public boolean isValidLogin(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Customer WHERE email = : email AND passwordHash = :password";
            Query<Customer> query = session.createQuery(hql, Customer.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult() != null;
        }
    }

    @Override
    public boolean isEmailTaken(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT 1 FROM Customer WHERE email = :email";
            Query<?> query = session.createQuery(hql);
            query.setParameter("email", email);
            return  query.uniqueResult() != null;
        }
    }

    @Override
    public boolean save(Customer customer) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Customer getByEmailAndPassword(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Customer> query = session.createQuery(
                    "FROM Customer c WHERE c.email = :email AND c.passwordHash = :password", Customer.class
            );
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult();
        }
    }

}
