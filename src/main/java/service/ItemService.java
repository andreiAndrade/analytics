package service;

import model.Item;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemService {

    public static List<Item> mapToItemList(String sItems) {
        if (Objects.nonNull(sItems)) {
            String[] itemList = sItems.split(",");

            return Arrays.stream(itemList).map(sItem -> {
                String[] itemDetail = sItem.split("-");
                Item item = new Item();

                item.setId(Integer.parseInt(itemDetail[0]));
                item.setQuantity(Integer.parseInt(itemDetail[1]));
                item.setPrice(new BigDecimal(itemDetail[2]));

                return item;
            }).collect(Collectors.toList());
        }

        return null;
    }

    public static BigDecimal calculatePurchaseValue(List<Item> itemList) {
        return itemList.stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
