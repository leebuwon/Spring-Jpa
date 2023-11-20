package jpabook.JpaShop.api.order.controller;

import jpabook.JpaShop.api.order.dto.SimpleOrderDto;
import jpabook.JpaShop.api.order.dto.SimpleOrderQueryDto;
import jpabook.JpaShop.domain.order.entity.Order;
import jpabook.JpaShop.domain.order.entity.OrderSearch;
import jpabook.JpaShop.domain.order.repository.OrderRepository;
import jpabook.JpaShop.domain.order.repository.orderSimpleQuery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            // Lazy 강제 초기화
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    // N + 1 문제 발생
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        return orderRepository.findAllByCriteria(new OrderSearch()).stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    // 지린다.. ( N + 1 문제 해결 fetch join 사용 )
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> allWithMemberAndDelivery = orderRepository.findAllWithMemberAndDelivery();
        return allWithMemberAndDelivery.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    // 성능하고 쿼리는 이게 보기 좋지만..query 부분이 직관적이지 않고 구현이 어렵다.
    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDto> orderV4() {
        return orderSimpleQueryRepository.findOrders();
    }
}
