import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Storage {
    private static Storage storage;

    private HashMap<String, List<String>> datas;

    private Integer clientCount;

    private Integer salesmanCount;

    private BigDecimal mostExpansiveSale;

    private String worstSalesman;

    private Storage() {
        this.datas = new HashMap<>();
    }

    public static Storage getInstance() {
        if (Objects.isNull(storage)) {
            storage = new Storage();
        }

        return storage;
    }

    public HashMap<String, List<String>> getDatas() {
        return datas;
    }

    public Integer getClientCount() {
        return clientCount;
    }

    public void setClientCount(Integer clientCount) {
        this.clientCount = clientCount;
    }

    public Integer getSalesmanCount() {
        return salesmanCount;
    }

    public void setSalesmanCount(Integer salesmanCount) {
        this.salesmanCount = salesmanCount;
    }

    public BigDecimal getMostExpansiveSale() {
        return mostExpansiveSale;
    }

    public void setMostExpansiveSale(BigDecimal mostExpansiveSale) {
        this.mostExpansiveSale = mostExpansiveSale;
    }

    public String getWorstSalesman() {
        return worstSalesman;
    }

    public void setWorstSalesman(String worstSalesman) {
        this.worstSalesman = worstSalesman;
    }
}
