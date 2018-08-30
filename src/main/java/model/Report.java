package model;

import java.util.List;

public class Report {

    private List<Salesman> salesmanList;
    private List<Client> clientList;
    private List<Sale> saleList;

    private long clientCount;
    private long salesmenCount;
    private Integer mostExpansiveSaleId;
    private String worstSalesman;

    public List<Salesman> getSalesmanList() {
        return salesmanList;
    }

    public void setSalesmanList(List<Salesman> salesmanList) {
        this.salesmanList = salesmanList;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }

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
        return "model.Report{"
                + "clientCount=" + clientCount
                + ", salesmenCount=" + salesmenCount
                + ", mostExpansiveSaleId=" + mostExpansiveSaleId
                + ", worstSalesman='" + worstSalesman + '\''
                + '}';
    }
}
