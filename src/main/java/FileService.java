import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {

    private static final String ROOT_PATH = "data";
    private static final String SOURCE_PATH = "in";
    private static final String OUTCOME_PATH = "out";

    private static final String FILE_EXT = ".dat";

    public static List<Path> readPath() throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), ROOT_PATH, SOURCE_PATH);

        if (Files.notExists(path)) {
            System.out.println("O caminho especificado não existe: " + path);
            return null;
        }

        return Files.walk(path).filter(FileService::isDatFile).collect(Collectors.toList());
    }

    public static void createFile(Report report) throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), ROOT_PATH, OUTCOME_PATH);

        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path.toString(), "outcome.done.dat"))) {
            writer.write("Quantidade de clientes: " + report.getClientCount());
            writer.newLine();
            writer.write("Quantidade de vendedores: " + report.getSalesmenCount());
            writer.newLine();
            writer.write("ID da venda mais cara: " + report.getMostExpansiveSaleId());
            writer.newLine();
            writer.write("O pior vendedor: " + report.getWorstSalesman());
        }
    }

    private static boolean isDatFile(Path path) {
        return Files.isRegularFile(path) && path.toString().endsWith(FILE_EXT);
    }
}
