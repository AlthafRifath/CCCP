package main.java.com.syos.data.dao.interfaces;

import main.java.com.syos.data.model.Customer;

public interface ICustomerDAO {
    boolean isValidLogin(String email, String password);
    boolean isEmailTaken(String email);
    boolean save(Customer customer);
    Customer getByEmailAndPassword(String email, String password);
    Customer findByEmailOnly(String email);
}
