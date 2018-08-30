import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Serviço que aplica as regras de geração de relatórios de acordo com a especificação.
 *
 * @author <a href="mailto:andreirsandrade@gmail.com.br">Andrei Andrade</a>
 * @since 30/08/2018 16:36:00
 */
public class ReportService {

    /**
     * Gera o relatório dos arquivos .dat encontrado no caminho de origem.
     *
     * @return um relatório com as informações especificadas
     */
    public static Report generateReport() {
        List<Path> fileList = FileService.readPath();

        if (Objects.nonNull(fileList)) {
            List<String> rowList = fileList.stream().flatMap(FileService::readFile).collect(Collectors.toList());

            Report report = new Report();

            report.setClientCount(countClients(rowList));
            report.setSalesmenCount(countSalesmen(rowList));
            report.setMostExpansiveSaleId(findMostExpansiveSaleId(rowList));
            report.setWorstSalesman(findWorstSalesman(rowList));

            FileService.createReportFile(report);

            return report;
        }

        return null;
    }

    private static long countSalesmen(List<String> rowList) {
        return rowList.stream().filter(row -> row.startsWith("001")).distinct().count();
    }

    private static long countClients(List<String> rowList) {
        return rowList.stream().filter(row -> row.startsWith("002")).distinct().count();
    }

    private static String findWorstSalesman(List<String> rowList) {
        List<String> saleList = rowList.stream().filter(row -> row.startsWith("003")).collect(Collectors.toList());

        Pattern pattern = Pattern.compile(PatternUtils.getSaleRowPattern());

        Map<String, Double> salesmanSales = new HashMap<>();

        for (String sale : saleList) {

            Matcher matcher = pattern.matcher(sale);

            if (matcher.matches()) {
                double totalSaleValue = calculatePurchaseValue(matcher.group(3));

                String salesmanName = matcher.group(6);

                if (salesmanSales.containsKey(salesmanName)) {
                    salesmanSales.put(salesmanName, Double.sum(totalSaleValue, salesmanSales.get(salesmanName)));
                } else {
                    salesmanSales.put(salesmanName, totalSaleValue);
                }
            }
        }

        Map.Entry<String, Double> worstSalesman = salesmanSales.entrySet().stream()
                .min(Map.Entry.comparingByValue()).orElse(null);

        return Objects.nonNull(worstSalesman) ? worstSalesman.getKey() : null;
    }

    private static Integer findMostExpansiveSaleId(List<String> rowList) {
        List<String> saleList = rowList.stream().filter(row -> row.startsWith("003")).collect(Collectors.toList());

        Pattern pattern = Pattern.compile(PatternUtils.getSaleRowPattern());

        Map.Entry<Integer, Double> mostExpansiveSale = null;

        for (String sale : saleList) {
            Matcher matcher = pattern.matcher(sale);

            if (matcher.matches()) {
                double totalSaleValue = calculatePurchaseValue(matcher.group(3));

                if (Objects.isNull(mostExpansiveSale) || mostExpansiveSale.getValue() < totalSaleValue) {
                    mostExpansiveSale = new AbstractMap
                            .SimpleEntry<>(Integer.parseInt(matcher.group(2)), totalSaleValue);
                }
            }
        }

        return Objects.nonNull(mostExpansiveSale) ? mostExpansiveSale.getKey() : null;
    }

    private static double calculatePurchaseValue(String sale) {
        return Arrays.stream(sale.split(",")).mapToDouble(item -> {
            String[] itemDetail = item.split("-");

            Double amount = new Double(itemDetail[1]);
            Double price = new Double(itemDetail[2]);

            return amount * price;
        }).sum();
    }
}
