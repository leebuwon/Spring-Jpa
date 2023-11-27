package jpabook.JpaShop.domain.order.service.query;

import jpabook.JpaShop.api.order.dto.OrderDto;
import jpabook.JpaShop.domain.order.entity.Order;
import jpabook.JpaShop.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        return orders.stream()
                .map(OrderDto::new)
                .collect(toList());
    }
}
