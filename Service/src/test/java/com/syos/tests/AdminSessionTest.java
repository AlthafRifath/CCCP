package test.java.com.syos.tests;

import main.java.com.syos.service.AdminSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminSessionTest {

    @BeforeEach
    void setUp() {
        // Reset AdminSession before each test to ensure test isolation
        AdminSession.getInstance().logout();
    }

    @Test
    public void testSingletonInstance() {
        // Ensure multiple calls return the same instance
        AdminSession instance1 = AdminSession.getInstance();
        AdminSession instance2 = AdminSession.getInstance();

        Assertions.assertSame(instance1, instance2, "Instances should be the same (Singleton pattern).");
    }

    @Test
    public void testSetAndGetLoggedInUserId() {
        AdminSession session = AdminSession.getInstance();
        session.setLoggedInUserId(1001);

        // Verify stored user ID
        Assertions.assertEquals(1001, session.getLoggedInUserId(), "User ID should match the set value.");
    }

    @Test
    public void testLogout() {
        AdminSession session = AdminSession.getInstance();
        session.setLoggedInUserId(1001);

        // Perform logout
        session.logout();

        // Ensure user ID is cleared
        Assertions.assertNull(session.getLoggedInUserId(), "Logged-in user should be null after logout.");
    }

    @Test
    public void testThreadSafety_SingleInstance() throws InterruptedException {
        final AdminSession[] session1 = new AdminSession[1];
        final AdminSession[] session2 = new AdminSession[1];

        Thread thread1 = new Thread(() -> session1[0] = AdminSession.getInstance());
        Thread thread2 = new Thread(() -> session2[0] = AdminSession.getInstance());

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Assertions.assertSame(session1[0], session2[0], "Instances from different threads should be the same.");
    }
}