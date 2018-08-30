import java.util.Objects;

public class PatternUtils {

    private static final String SEPARATOR = "ç";

    private static final String SALE_CODE_PATTERN = "(003)";
    private static final String NAME_PATTERN = "([\\p{L} .'-]+)";
    private static final String MONEY_PATTERN = "(\\d+(?:\\.\\d{2})?)";
    private static final String DETAIL_ITEM_PATTERN = "\\d+-\\d+-".concat(MONEY_PATTERN);
    private static final String ITEMS_PATTERN = "(?:\\[((?:".concat(DETAIL_ITEM_PATTERN).concat(",)*")
            .concat(DETAIL_ITEM_PATTERN).concat(")\\])");

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

    public static String getSaleRowPattern() {
        return Objects.isNull(saleRowPattern)
                ? saleRowPattern = concat(SALE_CODE_PATTERN, "(\\d+)", ITEMS_PATTERN, NAME_PATTERN)
                : saleRowPattern;
    }
}
