package main.java.com.syos.service;

import main.java.com.syos.data.dao.CustomerDAO;
import main.java.com.syos.data.dao.interfaces.ICustomerDAO;
import main.java.com.syos.data.model.Customer;
import main.java.com.syos.dto.LoginDTO;
import main.java.com.syos.request.CustomerLoginRequest;
import main.java.com.syos.request.CustomerSignupRequest;
import main.java.com.syos.service.interfaces.ICustomerAuthService;

import java.util.Date;

public class CustomerAuthService implements ICustomerAuthService {
    private final ICustomerDAO customerDAO;

    public CustomerAuthService() {
        this.customerDAO = new CustomerDAO();
    }

    @Override
    public LoginDTO login(CustomerLoginRequest loginRequest) {
        boolean isValid = customerDAO.isValidLogin(
                loginRequest.getEmail(), loginRequest.getPassword()
        );

        if (isValid) {
            return new LoginDTO(true, "Login successful!");
        } else {
            return new LoginDTO(false, "Invalid email or password.");
        }
    }

    @Override
    public LoginDTO register(CustomerSignupRequest request) {
        if (customerDAO.isEmailTaken(request.getEmail())) {
            return new LoginDTO(false, "Email already exists.");
        }

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setUsername(request.getUsername());
        customer.setAddress(request.getAddress());
        customer.setPasswordHash(request.getPassword());
        customer.setRegistrationDate(request.getRegistrationDate() != null ? request.getRegistrationDate() : new Date());
        customer.setUpdatedDateTime(new Date());
        customer.setDeleted(false);

        boolean success = customerDAO.save(customer);

        return success
                ? new LoginDTO(true, "Registration successful.")
                : new LoginDTO(false, "Registration failed.");
    }

}
