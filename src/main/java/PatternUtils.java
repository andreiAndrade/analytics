import java.util.Objects;

/**
 * Utilitário responsável por fornecer padrões (REGEX) para identificar e extrair informações de strings de acordo
 * com a especificação.
 *
 * @author <a href="mailto:andreirsandrade@gmail.com.br">Andrei Andrade</a>
 * @since 30/08/2018 16:36:00
 */
public class PatternUtils {

    private static final String SEPARATOR = "ç";

    private static final String SALE_CODE_PATTERN = "(003)";
    private static final String NAME_PATTERN = "([\\p{L} .'-]+)";
    private static final String MONEY_PATTERN = "(\\d+(?:\\.\\d{2})?)";
    private static final String DETAIL_ITEM_PATTERN = "\\d+-\\d+-" .concat(MONEY_PATTERN);
    private static final String ITEMS_PATTERN = "(?:\\[((?:" .concat(DETAIL_ITEM_PATTERN).concat(",)*")
            .concat(DETAIL_ITEM_PATTERN).concat(")\\])");

    private static String saleRowPattern;

    public static String getSaleRowPattern() {
        return Objects.isNull(saleRowPattern)
                ? saleRowPattern = concat(SALE_CODE_PATTERN, "(\\d+)", ITEMS_PATTERN, NAME_PATTERN)
                : saleRowPattern;
    }

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
}
