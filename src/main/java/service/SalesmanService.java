package service;

import model.Salesman;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SalesmanService {

    public static List<Salesman> mapToSalesman(List<String> salesmanList) {
        return Objects.nonNull(salesmanList)
                ? salesmanList.stream().filter(sSalesman -> sSalesman.startsWith(Salesman.SALESMAN_CODE))
                .map(SalesmanService::mapToSalesman).collect(Collectors.toList())
                : null;
    }

    public static Salesman mapToSalesman(String sSalesman) {
        if (Objects.nonNull(sSalesman)) {
            Pattern pattern = Pattern.compile(PatternService.getSalesmanRowPattern());
            Matcher matcher = pattern.matcher(sSalesman);

            if (matcher.matches()) {
                Salesman salesman = new Salesman();

                salesman.setCpf(matcher.group(1));
                salesman.setNome(matcher.group(2));
                salesman.setSalary(new BigDecimal(matcher.group(3)));

                return salesman;
            }
        }

        return null;
    }
}
