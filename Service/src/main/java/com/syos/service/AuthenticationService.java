package main.java.com.syos.service;

import main.java.com.syos.data.dao.AdminDAO;
import main.java.com.syos.data.dao.interfaces.IAdminDAO;
import main.java.com.syos.dto.LoginDTO;
import main.java.com.syos.request.LoginRequest;
import main.java.com.syos.service.interfaces.IAuthenticationService;

public class AuthenticationService implements IAuthenticationService {
    private final IAdminDAO adminDAO;

    public AuthenticationService() {
        this.adminDAO = new AdminDAO();
    }

    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        boolean isValid = adminDAO.validateCredentials(loginRequest.getUsername(), loginRequest.getPassword());

        if (isValid) {
            AdminSession.getInstance().setLoggedInUser(loginRequest.getUsername());
            return new LoginDTO(true, "Login successful!");
        } else {
            return new LoginDTO(false, "Invalid username or password.");
        }
    }
}
