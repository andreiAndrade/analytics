import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(ReportService.generateReport());

        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path path = FileService.getSourcePath();

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            key.pollEvents();
            System.out.println(ReportService.generateReport());
            key.reset();
        }
    }
}
