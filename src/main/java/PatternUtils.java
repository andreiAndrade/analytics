import java.util.Objects;

public class PatternUtils {

    private static final String SEPARATOR = "ç";

    private static final String SALESMAN_CODE_PATTERN = "(001)";
    private static final String CLIENT_CODE_PATTERN = "(002)";
    private static final String SALE_CODE_PATTERN = "(003)";

    private static final String CPF_PATTERN = "(\\d{11})";
    private static final String CNPJ_PATTERN = "(\\d{14})";

    private static final String NAME_PATTERN = "([\\p{L} .'-]+)";
    private static final String BUSINESS_AREA_PATTERN = "([\\p{L} ]+)";
    private static final String MONEY_PATTERN = "(\\d+(?:\\.\\d{2})?)";

    private static final String DETAIL_ITEM_PATTERN = "((\\d+)-(\\d+)-".concat(MONEY_PATTERN).concat(")");
    private static final String ITEMS_PATTERN = "(?:\\[((?:".concat(DETAIL_ITEM_PATTERN).concat(",)*")
            .concat(DETAIL_ITEM_PATTERN).concat(")\\])");

    private static String salesmanRowPattern;
    private static String clientRowPattern;
    private static String saleRowPattern;

    private static String concat(String... strings) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (String str : strings) {
            if (first) {
                first = false;
            } else {
                builder.append(SEPARATOR);
            }

            builder.append(str);
        }

        return builder.toString();
    }

    public static String getSalesmanRowPattern() {
        return Objects.isNull(salesmanRowPattern)
                ? salesmanRowPattern = concat(SALESMAN_CODE_PATTERN, CPF_PATTERN, NAME_PATTERN, MONEY_PATTERN)
                : salesmanRowPattern;
    }

    public static String getClientRowPattern() {
        return Objects.isNull(clientRowPattern)
                ? clientRowPattern = concat(CLIENT_CODE_PATTERN, CNPJ_PATTERN, NAME_PATTERN, BUSINESS_AREA_PATTERN)
                : clientRowPattern;
    }

    public static String getSaleRowPattern() {
        return Objects.isNull(saleRowPattern)
                ? saleRowPattern = concat(SALE_CODE_PATTERN, "(\\d+)", ITEMS_PATTERN, NAME_PATTERN)
                : saleRowPattern;
    }
}
