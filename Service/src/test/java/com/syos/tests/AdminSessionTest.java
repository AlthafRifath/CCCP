package test.java.com.syos.tests;

import main.java.com.syos.service.AdminSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminSessionTest {

    /**
     * Test description:
     * This method tests the singleton behavior of the getInstance() method.
     * It ensures that multiple calls to getInstance() return the same instance.
     */
    @Test
    public void testGetInstance_ReturnsSameInstance() {
        // Act
        AdminSession instance1 = AdminSession.getInstance();
        AdminSession instance2 = AdminSession.getInstance();

        // Assert
        assertNotNull(instance1, "getInstance() should not return null.");
        assertSame(instance1, instance2, "getInstance() should always return the same instance.");
    }

    /**
     * Test description:
     * This method tests if a retrieved AdminSession instance allows updating the loggedInUserId
     * and verifies that the same instance persists the updated state.
     */
    @Test
    public void testGetInstance_ModifyAndPersistState() {
        // Arrange
        AdminSession instance = AdminSession.getInstance();
        Integer testUserId = 101;

        // Act
        instance.setLoggedInUserId(testUserId);

        // Assert
        assertEquals(testUserId, instance.getLoggedInUserId(), "The loggedInUserId should be set and retrievable.");
    }

    /**
     * Test description:
     * This method tests if calling the logout() method resets the loggedInUserId to null.
     */
    @Test
    public void testLogout_ResetsLoggedInUserId() {
        // Arrange
        AdminSession instance = AdminSession.getInstance();
        instance.setLoggedInUserId(202);

        // Act
        instance.logout();

        // Assert
        assertNull(instance.getLoggedInUserId(), "After logout, loggedInUserId should be null.");
    }
}