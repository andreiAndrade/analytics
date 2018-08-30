package service;

import model.Sale;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SaleService {

    public static List<Sale> mapToSale(List<String> sSaleList) {
        return Objects.nonNull(sSaleList)
                ? sSaleList.stream().filter(sSale -> sSale.startsWith(Sale.SALE_CODE))
                .map(SaleService::mapToSale).collect(Collectors.toList())
                : null;
    }

    public static Sale mapToSale(String sSale) {
        if (Objects.nonNull(sSale)) {
            Pattern pattern = Pattern.compile(PatternService.getSaleRowPattern());
            Matcher matcher = pattern.matcher(sSale);

            if (matcher.matches()) {
                Sale sale = new Sale();

                sale.setId(Integer.parseInt(matcher.group(1)));
                sale.setItemList(ItemService.mapToItemList(matcher.group(2)));
                sale.setSalesmanName(matcher.group(3));

                return sale;
            }
        }

        return null;
    }

    public static String findWorstSalesman(List<Sale> saleList) {
        Map<String, BigDecimal> salesmanSales = new HashMap<>();

        for (Sale sale : saleList) {

            BigDecimal totalSaleValue = ItemService.calculatePurchaseValue(sale.getItemList());

            if (salesmanSales.containsKey(sale.getSalesmanName())) {
                salesmanSales.put(sale.getSalesmanName(), totalSaleValue.add(salesmanSales.get(sale.getSalesmanName())));
            } else {
                salesmanSales.put(salesmanName, totalSaleValue);
            }
        }

        Map.Entry<String, Double> worstSalesman = salesmanSales.entrySet().stream()
                .min(Map.Entry.comparingByValue()).orElse(null);

        return Objects.nonNull(worstSalesman) ? worstSalesman.getKey() : null;
    }
}
