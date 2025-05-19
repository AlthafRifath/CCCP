package test.java.com.syos.tests;

import main.java.com.syos.data.dao.interfaces.IAdminDAO;
import main.java.com.syos.dto.LoginDTO;
import main.java.com.syos.request.LoginRequest;
import main.java.com.syos.service.AdminSession;
import main.java.com.syos.service.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AuthenticationServiceTest {
    private AuthenticationService authenticationService;
    private IAdminDAO mockAdminDAO;
    private AdminSession mockSession;

    @BeforeEach
    void setUp() {
        // Mock DAO and AdminSession
        mockAdminDAO = Mockito.mock(IAdminDAO.class);
        mockSession = Mockito.mock(AdminSession.class);

        // Inject mocks into AuthenticationService
//        authenticationService = new AuthenticationService(mockAdminDAO);
    }

    @Test
    public void testLogin_Successful() {
        // Mock valid admin credentials
        LoginRequest request = new LoginRequest("adminUser", "securePassword");
        Mockito.when(mockAdminDAO.getIdByUsernameAndPassword("adminUser", "securePassword")).thenReturn(1);

        // Perform login
        LoginDTO response = authenticationService.login(request);

        // Verify login success
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Login successful!", response.getMessage());

        // Ensure session update occurs
        Mockito.verify(mockAdminDAO, Mockito.times(1)).getIdByUsernameAndPassword("adminUser", "securePassword");
    }

    @Test
    public void testLogin_InvalidCredentials() {
        // Mock invalid credentials
        LoginRequest request = new LoginRequest("adminUser", "wrongPassword");
        Mockito.when(mockAdminDAO.getIdByUsernameAndPassword("adminUser", "wrongPassword")).thenReturn(null);

        // Perform login
        LoginDTO response = authenticationService.login(request);

        // Verify failure response
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Invalid username or password.", response.getMessage());

        // Ensure DAO was called once
        Mockito.verify(mockAdminDAO, Mockito.times(1)).getIdByUsernameAndPassword("adminUser", "wrongPassword");
    }

    @Test
    public void testLogin_EmptyCredentials() {
        // Attempt login with empty credentials
        LoginRequest request = new LoginRequest("", "");
        Mockito.when(mockAdminDAO.getIdByUsernameAndPassword("", "")).thenReturn(null);

        // Perform login
        LoginDTO response = authenticationService.login(request);

        // Verify failure response
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Invalid username or password.", response.getMessage());

        // Ensure DAO was called once
        Mockito.verify(mockAdminDAO, Mockito.times(1)).getIdByUsernameAndPassword("", "");
    }
}