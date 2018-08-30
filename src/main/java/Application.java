import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        Report report = ReportService.generateReport();
        System.out.println(report);
    }
}
