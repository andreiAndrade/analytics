import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Serviço de manipulação de arquivos que aplica as regras de processamento de arquivos .dat.
 *
 * @author <a href="mailto:andreirsandrade@gmail.com.br">Andrei Andrade</a>
 * @since 30/08/2018 16:36:00
 */
public class FileService {

    private static final String ROOT_PATH = "data";
    private static final String SOURCE_PATH = "in";
    private static final String OUTCOME_PATH = "out";

    private static final String FILE_EXT = ".dat";
    private static final String OUTCOME_FILE_NAME = "outcome.done.dat";

    /**
     * Busca todos os arquivos .dat do diretório de origem: %HOME%/data/in
     *
     * @return uma lista de arquivos .dat
     */
    public static List<Path> readPath() {
        try {
            Path path = getSourcePath();

            if (Files.notExists(path)) {
                System.out.println("O caminho especificado não existe: " + path);
            } else {
                return Files.walk(path).filter(FileService::isDatFile).collect(Collectors.toList());
            }
        } catch (IOException ioe) {
            System.out.println("Houve um erro ao tentar ler o diretório: " + getSourcePath());
        }

        return null;
    }

    /**
     * Criado o arquivo do relatório do processamento.
     *
     * @param report relatório base para a criação do arquivo
     */
    public static void createReportFile(Report report) {
        try {
            Path path = getOutcomePath();

            if (Files.notExists(path)) {
                Files.createDirectory(path);
            }

            writeReportFile(report, path);
        } catch (IOException ioe) {
            System.out.println("Houve um erro ao criar o arquivo do relatório.");
        }
    }

    /**
     * Lê todas as linhas do arquivo.
     *
     * @param path arquivo para leitura
     * @return uma lista com todas as linhas do arquivo
     */
    public static Stream<String> readFile(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler este arquivo: " + e.getMessage());
        }

        return Stream.empty();
    }

    public static Path getSourcePath() {
        return Paths.get(System.getProperty("user.home"), ROOT_PATH, SOURCE_PATH);
    }

    public static Path getOutcomePath() {
        return Paths.get(System.getProperty("user.home"), ROOT_PATH, OUTCOME_PATH);
    }

    private static void writeReportFile(Report report, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path.toString(), OUTCOME_FILE_NAME))) {
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
