package service;

import model.Item;
import model.Report;
import model.Sale;

import java.math.BigDecimal;
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
            List<String> rowList = FileService.readFile(fileList);

            Report report = mapToReport(rowList);

            if (Objects.nonNull(report)) {
                report.setClientCount(report.getClientList().size());
                report.setSalesmenCount(report.getSalesmanList().size());
                report.setMostExpansiveSaleId(findMostExpansiveSaleId(report.getSaleList()));
                report.setWorstSalesman(findWorstSalesman(report.getSaleList()));

                FileService.createReportFile(report);
            }

            return report;
        }

        return null;
    }

    private static Report mapToReport(List<String> rowList) {
        Report report = new Report();

        report.setClientList(ClientService.mapToClient(rowList));
        report.setSaleList(SaleService.mapToSale(rowList));
        report.setSalesmanList(SalesmanService.mapToSalesman(rowList));

        return report;
    }

    private static Integer findMostExpansiveSaleId(List<Sale> saleList) {
        if (Objects.isNull(saleList)) {
            return null;
        }

        Map.Entry<Integer, BigDecimal> mostExpansiveSale = null;

        for (Sale sale : saleList) {
            BigDecimal totalSaleValue = ItemService.calculatePurchaseValue(sale.getItemList());

            if (Objects.isNull(mostExpansiveSale) || mostExpansiveSale.getValue().compareTo(totalSaleValue) < 0) {
                mostExpansiveSale = new AbstractMap.SimpleEntry<>(sale.getId(), totalSaleValue);
            }
        }

        return Objects.nonNull(mostExpansiveSale) ? mostExpansiveSale.getKey() : null;
    }
}
