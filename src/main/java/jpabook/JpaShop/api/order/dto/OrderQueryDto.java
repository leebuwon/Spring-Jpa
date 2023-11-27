package jpabook.JpaShop.api.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jpabook.JpaShop.api.orderItem.dto.OrderItemQueryDto;
import jpabook.JpaShop.domain.address.entity.Address;
import jpabook.JpaShop.domain.order.entity.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Slf4j
@EqualsAndHashCode(of = "orderId") // orderId 기준으로 묶어주기 위하여 선언
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItemQueryDtoList;


    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate,
                         OrderStatus orderStatus, Address address, List<OrderItemQueryDto> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItemQueryDtoList = orderItems;
    }

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    /**
     * @Transactional 이 들어가지 않더라도 update가 잘 실행됨
     */
    public void updateOrderItems(List<OrderItemQueryDto> orderItems) {
        log.info("update Query 실행");
        this.orderItemQueryDtoList = orderItems;
    }
}
