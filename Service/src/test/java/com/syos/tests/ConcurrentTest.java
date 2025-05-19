package test.java.com.syos.tests;

import com.syos.util.TransactionManager;
import org.hibernate.Session;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentTest {

    public static void main(String[] args) {
        int THREAD_COUNT = 5;

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            int threadId = i + 1;
            executor.submit(() -> {
                try {
                    String result = TransactionManager.execute(session -> simulateDatabaseOperation(session, threadId));
                    System.out.println("Thread " + threadId + ": " + result);
                } catch (Exception e) {
                    System.err.println("Thread " + threadId + " failed: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
    }

    private static String simulateDatabaseOperation(Session session, int threadId) {
        // Replace this with a real query if needed
        // Example: List<Client> clients = session.createQuery("FROM Client", Client.class).list();
        System.out.println("Thread " + threadId + " is using Session: " + session.hashCode());

        // Simulate some work
        try {
            Thread.sleep(1000); // simulate latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return "Operation completed by thread " + threadId;
    }
}
