package jpabook.JpaShop.api.orderItem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.JpaShop.domain.orderItem.entity.OrderItem;
import lombok.Data;
import lombok.Getter;

@Getter
public class OrderItemDto {
    @JsonIgnore
    private Long orderItemId;
    private String name;
    private int price;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        orderItemId = orderItem.getId();
        name = orderItem.getItem().getName();
        price = orderItem.getItem().getPrice();
        count = orderItem.getCount();
    }
}
