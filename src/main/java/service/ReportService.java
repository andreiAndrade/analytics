package service;

import model.Report;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

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
                report.setMostExpansiveSaleId(SaleService.findMostExpansiveSaleId(report.getSaleList()));
                report.setWorstSalesman(SaleService.findWorstSalesman(report.getSaleList()));

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
}
