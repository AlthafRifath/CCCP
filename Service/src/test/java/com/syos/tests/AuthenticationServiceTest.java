package test.java.com.syos.tests;

import main.java.com.syos.data.dao.interfaces.IAdminDAO;
import main.java.com.syos.dto.LoginDTO;
import main.java.com.syos.request.LoginRequest;
import main.java.com.syos.service.AuthenticationService;
import main.java.com.syos.service.interfaces.IAuthenticationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AuthenticationServiceTest {

    /**
     * Unit tests for the `login` method in the `AuthenticationService` class.
     * The `login` method validates user login credentials by communicating with the `IAdminDAO`
     * to check the presence of the user and manages session when authentication is successful.
     */

    @Test
    public void testLoginSuccess() {
        IAdminDAO adminDAOMock = mock(IAdminDAO.class);
        IAuthenticationService authenticationService = new AuthenticationService() {
            @Override
            public LoginDTO login(LoginRequest loginRequest) {
                return super.login(loginRequest);
            }
        };
    };
}