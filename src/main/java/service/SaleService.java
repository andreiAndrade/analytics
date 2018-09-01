package service;

import model.Sale;

import java.math.BigDecimal;
import java.util.AbstractMap;
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

                sale.setId(Integer.parseInt(matcher.group(2)));
                sale.setItemList(ItemService.mapToItemList(matcher.group(4)));
                sale.setSalesmanName(matcher.group(5));

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
                salesmanSales.put(sale.getSalesmanName(), totalSaleValue);
            }
        }

        Map.Entry<String, BigDecimal> worstSalesman = salesmanSales.entrySet().stream()
                .min(Map.Entry.comparingByValue()).orElse(null);

        return Objects.nonNull(worstSalesman) ? worstSalesman.getKey() : null;
    }

    public static Integer findMostExpansiveSaleId(List<Sale> saleList) {
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
