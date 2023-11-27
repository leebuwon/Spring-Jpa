package jpabook.JpaShop.api.order.dto;

import jpabook.JpaShop.api.orderItem.dto.OrderItemDto;
import jpabook.JpaShop.domain.address.entity.Address;
import jpabook.JpaShop.domain.order.entity.Order;
import jpabook.JpaShop.domain.order.entity.OrderStatus;
import jpabook.JpaShop.domain.orderItem.entity.OrderItem;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.address = order.getDelivery().getAddress();
        // 이렇게 하면 OrderItem 의 entity 스팩이 바로 외부에 노출되기 때문에 이런식으로 활용하면 안됨 -> dto로 변환해서 사용해야함
//        order.getOrderItemList()
//                .stream()
//                .forEach(o -> o.getItem().getName());
//        this.orderItems = order.getOrderItemList();
        orderItems = order.getOrderItemList()
                .stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
    }
}
