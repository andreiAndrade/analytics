import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Application {

    private static final String ROOT_NAME = "data";
    private static final String SOURCE_NAME = "in";
    private static final String OUTCOME_NAME = "out";

    private static final String FILE_EXT = ".dat";

    private static final String SALESMAN_CODE = "001";
    private static final String CLIENT_CODE = "002";
    private static final String SALE_CODE = "003";

    public static void main(String[] args) throws IOException {
        createSource();

        try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.home"), ROOT_NAME, SOURCE_NAME))) {
            paths
                    .filter(Application::isDatFile)
                    .forEach(Application::processFile);
        }

        Integer clientCount = new HashSet<>(Storage.getInstance().getDatas().get(CLIENT_CODE)).size();
        Integer salesmanCount = new HashSet<>(Storage.getInstance().getDatas().get(SALESMAN_CODE)).size();
        BigDecimal mostExpansiveSale = findMostExpansiveSale();
        String bestSalesman = findBestSalesman();

        System.out.println(Storage.getInstance().getDatas());
        System.out.print("clients count: ");
        System.out.println(clientCount);
        System.out.print("salesmen count: ");
        System.out.println(salesmanCount);
        System.out.print("most expansive sale: ");
        System.out.println(mostExpansiveSale);
        System.out.print("best salesman: ");
        System.out.println(bestSalesman);
    }

    private static String findBestSalesman() {
        List<String> saleList = Storage.getInstance().getDatas().get(SALE_CODE);
        Map<String, BigDecimal> salesmanSales = new HashMap<>();

        Pattern pattern = Pattern.compile(PatternUtils.getSaleRowPattern());

        for (String sale : saleList) {

            Matcher matcher = pattern.matcher(sale);

            if (matcher.matches()) {
                List<BigDecimal> salePriceList = new ArrayList<>();

                String[] itemList = matcher.group(3).split(",");

                for (String item : itemList) {
                    String price = item.split("-")[2];
                    salePriceList.add(new BigDecimal(price));
                }

                if (salesmanSales.containsKey(matcher.group(12))) {
                    salesmanSales.put(matcher.group(12), salePriceList.stream().reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO).add(salesmanSales.get(matcher.group(12))));
                } else {
                    salesmanSales.put(matcher.group(12), salePriceList.stream().reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO));
                }
            }
        }

        Map.Entry<String, BigDecimal> maxEntry = null;

        for (Map.Entry<String, BigDecimal> entry : salesmanSales.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return Objects.nonNull(maxEntry) ? maxEntry.getKey() : null;
    }

    private static BigDecimal findMostExpansiveSale() {
        List<String> saleList = Storage.getInstance().getDatas().get(SALE_CODE);
        List<BigDecimal> salePriceList = new ArrayList<>();

        Pattern pattern = Pattern.compile(PatternUtils.getSaleRowPattern());

        for (String sale : saleList) {
            Matcher matcher = pattern.matcher(sale);

            if (matcher.matches()) {
                String[] itemList = matcher.group(3).split(",");

                for (String item : itemList) {
                    String price = item.split("-")[2];
                    salePriceList.add(new BigDecimal(price));
                }
            }
        }

        salePriceList.sort(Comparator.reverseOrder());

        return salePriceList.get(0);
    }

    private static void processFile(Path path) {
        try {
            Scanner sc = new Scanner(path);

            while (sc.hasNextLine())
                processRow(sc.nextLine());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private static void processRow(String row) {
        if (row != null) {
            if (row.matches(PatternUtils.getSalesmanRowPattern()))
                storeRow(row, SALESMAN_CODE);
            else if (row.matches(PatternUtils.getClientRowPattern()))
                storeRow(row, CLIENT_CODE);
            else if (row.matches(PatternUtils.getSaleRowPattern()))
                storeRow(row, SALE_CODE);
        }
    }

    private static void storeRow(String row, String code) {
        if (Objects.nonNull(Storage.getInstance().getDatas().get(code))) {
            List<String> valueList = new ArrayList<>(Storage.getInstance().getDatas().get(code));
            valueList.add(row);
            Storage.getInstance().getDatas().put(code, valueList);
        } else {
            Storage.getInstance().getDatas().put(code, Collections.singletonList(row));
        }
    }

    private static boolean isDatFile(Path path) {
        return Files.isRegularFile(path) && path.toString().endsWith(FILE_EXT);
    }

    private static void createSource() {
        Path path = Paths.get(System.getProperty("user.home"), ROOT_NAME, SOURCE_NAME);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }
    }
}
