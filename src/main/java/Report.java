public class Report {
    private long  clientCount;
    private long salesmenCount;
    private Integer mostExpansiveSaleId;
    private String worstSalesman;

    public long getClientCount() {
        return clientCount;
    }

    public void setClientCount(long clientCount) {
        this.clientCount = clientCount;
    }

    public long getSalesmenCount() {
        return salesmenCount;
    }

    public void setSalesmenCount(long salesmenCount) {
        this.salesmenCount = salesmenCount;
    }

    public Integer getMostExpansiveSaleId() {
        return mostExpansiveSaleId;
    }

    public void setMostExpansiveSaleId(Integer mostExpansiveSaleId) {
        this.mostExpansiveSaleId = mostExpansiveSaleId;
    }

    public String getWorstSalesman() {
        return worstSalesman;
    }

    public void setWorstSalesman(String worstSalesman) {
        this.worstSalesman = worstSalesman;
    }

    @Override
    public String toString() {
        return "Report{"
                + "clientCount=" + clientCount
                + ", salesmenCount=" + salesmenCount
                + ", mostExpansiveSaleId=" + mostExpansiveSaleId
                + ", worstSalesman='" + worstSalesman + '\''
                + '}';
    }
}
