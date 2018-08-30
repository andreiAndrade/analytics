package model;

import java.util.List;

public class Sale {

    public static final String SALE_CODE = "003";

    private Integer id;
    private List<Item> itemList;
    private String salesmanName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    @Override
    public String toString() {
        return "Sale{"
                + "id=" + id
                + ", itemList=" + itemList
                + ", salesmanName='" + salesmanName + '\''
                + '}';
    }
}