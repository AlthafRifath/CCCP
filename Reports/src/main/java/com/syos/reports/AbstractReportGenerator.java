package main.java.com.syos.reports;

public class AbstractReportGenerator {
    public final void generateReport() {
        fetchData();
        processData();
        formatReport();
        exportReport();
    }

    // Each report will define its own fetching logic
    protected void fetchData() {
        System.out.println("Fetching data...");
    }

    // Process raw data into meaningful format
    protected void processData() {
        System.out.println("Processing data...");
    }

    // Convert data into report format (Table, JSON, PDF, etc.)
    protected void formatReport() {
        System.out.println("Formatting report...");
    }

    // Export report (print, save to file, etc.)
    protected void exportReport() {
        System.out.println("Exporting report...");
    }
}
