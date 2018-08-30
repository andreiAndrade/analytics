import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {

    private static final String ROOT_PATH = "data";
    private static final String SOURCE_PATH = "in";

    private static final String FILE_EXT = ".dat";

    public static List<Path> readPath() throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), ROOT_PATH, SOURCE_PATH);

        if (Files.notExists(path)) {
            System.out.println("O caminho especificado não existe: " + path);
            return null;
        }

        return Files.walk(path).filter(FileReader::isDatFile).collect(Collectors.toList());
    }

    private static boolean isDatFile(Path path) {
        return Files.isRegularFile(path) && path.toString().endsWith(FILE_EXT);
    }
}
